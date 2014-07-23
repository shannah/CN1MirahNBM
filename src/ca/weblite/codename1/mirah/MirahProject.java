/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.weblite.codename1.mirah;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.SourcesHelper;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author shannah
 */
public class MirahProject {
    FileObject projectRoot;
    Project parent;
    
    
    public MirahProject(Project parent, FileObject root){
        this.projectRoot = root;
        this.parent = parent;
    }
    
    
    public void generateProject() throws IOException{
        
        
        refreshBuildFile();
        refreshSampleSourceFile();
        copyLibs(true);
        addSourceDirectory();
        
    }
    
    public static MirahProject getMirahSubproject(Project p) throws IOException {
        FileObject projectDir = p.getProjectDirectory();
        if ( projectDir.getFileObject("mirah") != null){
            return new MirahProject(p, projectDir.getFileObject("mirah"));
        } else {
            return new MirahProject(p, projectDir.createFolder("mirah"));
        }
    }
    
    public FileObject getProjectRoot(){
        return projectRoot;
    }
    
    public Project getParent(){
        return parent;
    }
    
    public FileObject getSrcDir() throws IOException{
        FileObject srcDir = projectRoot.getFileObject("src");
        if ( srcDir == null ){
            srcDir = projectRoot.createFolder("src");
        }
        return srcDir;
    }
    
    public FileObject refreshSampleSourceFile() throws IOException {
        OutputStream os = null;
        InputStream is = null;
        
        try {
            FileObject sourceFile = projectRoot.getFileObject("Hello.mirah");
            if ( sourceFile != null ){
                sourceFile.delete();
            }
            os = getSrcDir().createAndOpen("Hello.mirah");
            is = CreateMirahProject.class.getResourceAsStream("Hello.mirah");
            FileUtil.copy(is, os);

            os.close();
            is.close();


        } finally {
            try {
                os.close();
            } catch ( Exception ex){}
            try {
                is.close();

            } catch ( Exception ex){}
        }
        
        return projectRoot.getFileObject("Hello.mirah");
    }
    
    private void copyLibs(boolean overwrite) throws IOException {
        String[] libs = new String[]{"mirah.jar", "mirahc.jar", "MirahAnt.jar"};
        for ( String lib : libs ){
            copyLib(lib, overwrite);
        }
    }
    
    private void copyLib(String name, boolean overwrite) throws IOException {
        OutputStream os = null;
        InputStream is = null;
        try {
            is = CreateMirahProject.class.getResourceAsStream("libs/"+name);
            FileObject libFolder = projectRoot.getFileObject("lib");
            if ( libFolder == null ){
                projectRoot.createFolder("lib");
                libFolder = projectRoot.getFileObject("lib");
            }
            FileObject libFile = libFolder.getFileObject(name);
            if ( libFile != null && overwrite ){
                libFile.delete();
            } else if ( libFile != null && !overwrite ){
                return;
            }
            
            os = libFolder.createAndOpen(name);
            FileUtil.copy(is, os);
            
        } finally {
            try { is.close();} catch ( Exception ex){}
            try {os.close();} catch (Exception ex){}
        }
    }
    
    public FileObject refreshBuildFile() throws IOException {
        OutputStream os = null;
        InputStream is = null;
        
        try {
            FileObject buildFile = projectRoot.getFileObject("build.xml");
            if ( buildFile != null ){
                buildFile.delete();
            }
            os = projectRoot.createAndOpen("build.xml");
            is = CreateMirahProject.class.getResourceAsStream("build.xml");
            FileUtil.copy(is, os);
            os.close();
            is.close();




        } finally {
            try {
                os.close();
            } catch ( Exception ex){}
            try {
                is.close();

            } catch ( Exception ex){}
        }
        
        return projectRoot.getFileObject("build.xml");
    }
    
    public FileObject getBuildFile() throws IOException {
        FileObject buildFile = projectRoot.getFileObject("build.xml");
        if ( buildFile == null ){
             buildFile = refreshBuildFile();
        }
        return buildFile;
    }
    
    public FileObject getSampleSourceFile() throws IOException {
        FileObject sourceFile = getSrcDir().getFileObject("Hello.mirah");
        if ( sourceFile == null ){
            sourceFile = refreshSampleSourceFile();
        }
        return sourceFile;
    }
    
    
    protected final boolean addSourceDirectory() {
        OutputStream os = null;
        try {
            
            
            //System.out.println("Helper is "+helper);
            
            FileObject rootDir = parent.getProjectDirectory();
            FileObject projectXML = rootDir.getFileObject("nbproject/project.xml");
            String contents = projectXML.asText();
            if ( contents.indexOf("<root id=\"src.src.dir\">") == -1 ){
                contents = contents.replace("<source-roots>", "<source-roots><root id=\"src.src.dir\"/>");
                os = projectXML.getOutputStream();
                Writer w = new PrintWriter(os);
                w.write(contents);
                w.close();
            }
            
            os = null;
            
            FileObject projectProps = rootDir.getFileObject("nbproject/project.properties");
            contents = projectProps.asText();
            if ( contents.indexOf("src.src.dir") == -1 ){
                contents += "\nfile.reference.mirah-src=mirah/src\nsrc.src.dir=${file.reference.mirah-src}";
                os = projectProps.getOutputStream();
                Writer w = new PrintWriter(os);
                w.write(contents);
                w.close();
            }
            
            
            return true;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (UnsupportedOperationException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            try {
                os.close();
            } catch (Exception ex){}
        }
        
        return false;
    }
    
    
   
}

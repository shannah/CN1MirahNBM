/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.weblite.codename1.mirah;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

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
    
    
    
   
}

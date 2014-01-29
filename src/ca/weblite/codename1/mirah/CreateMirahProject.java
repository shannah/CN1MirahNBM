/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.codename1.mirah;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

import org.openide.util.NbBundle.Messages;


@ActionID(
        category = "Project",
        id = "ca.weblite.codename1.mirah.CreateMirahProject"
)
@ActionRegistration(
        displayName = "#CTL_CreateMirahProject"
)
@ActionReference(path="Projects/Actions")
@Messages("CTL_CreateMirahProject=Create Mirah Project")
public class CreateMirahProject extends AbstractAction implements ContextAwareAction {
    private static final Logger LOG =
    Logger.getLogger("org.netbeans.modules.foo");
    public @Override void actionPerformed(ActionEvent e) {assert false;}
    public @Override Action createContextAwareInstance(Lookup context) {
        return new ContextAction(context);
    }
    private static final class ContextAction extends AbstractAction {
        
        private final Project p;
        public ContextAction(Lookup context) {
            p = context.lookup(Project.class);
            
            String name = ProjectUtils.getInformation(p).getDisplayName();
            
            setEnabled(true);
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            // TODO menu item label with optional mnemonics
            putValue(NAME, "Generate Mirah Project");
        }
        public @Override void actionPerformed(ActionEvent e) {
            // TODO what to do when run
           try {
                FileObject dir = createMirahDirectory();
                Desktop.getDesktop().open(new File(dir.toURI().getPath()));
            } catch ( Exception ex){
                
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(ex.getMessage()));
                throw new RuntimeException(ex);
            }
        }
        
        private FileObject createMirahDirectory() throws IOException{
            FileObject projectDir = p.getProjectDirectory();
            if ( projectDir.getFileObject("mirah") != null){
                return projectDir.getFileObject("mirah");
            } else {
                FileObject mirah = projectDir.createFolder("mirah");
                FileObject srcFolder = mirah.createFolder("src");
                OutputStream os = null;
                InputStream is = null;
                try {
                    os = mirah.createAndOpen("build.xml");
                    is = CreateMirahProject.class.getResourceAsStream("build.xml");
                    FileUtil.copy(is, os);
                    os.close();
                    is.close();


                    os = srcFolder.createAndOpen("Hello.mirah");
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


            }
            return projectDir.getFileObject("mirah");
        }
    }
}


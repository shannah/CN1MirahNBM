/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.codename1.mirah;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.filesystems.FileObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;

import org.openide.util.NbBundle.Messages;


@ActionID(
        category = "Project",
        id = "ca.weblite.codename1.mirah.OpenMirahProjectDirectory"
)
@ActionRegistration(
        displayName = "#CTL_OpenMirahProjectDirectory"
)
@ActionReference(path="Projects/org-netbeans-modules-java-j2seproject/Actions",  position=791)
@Messages("CTL_OpenMirahProjectDirectory=Open Mirah Project Folder")
public class OpenMirahProjectDirectory extends AbstractAction implements ContextAwareAction {
    private static final Logger LOG =
    Logger.getLogger("ca.weblite.codename1.mirah.CreateMirahProject");
    public @Override void actionPerformed(ActionEvent e) {assert false;}
    public @Override Action createContextAwareInstance(Lookup context) {
        return new ContextAction(context);
    }
    private static final class ContextAction extends AbstractAction {
        
        private final Project p;
        
        public ContextAction(Lookup context) {
            p = context.lookup(Project.class);
            FileObject cn1PropertiesFile = p.getProjectDirectory().getFileObject("codenameone_settings.properties");
            if ( cn1PropertiesFile != null ){
                setEnabled(true);
            } else {
                setEnabled(false);
            }
            
            FileObject mirahProject = p.getProjectDirectory().getFileObject("mirah");
            if ( mirahProject != null ){
                setEnabled(true);
            }
            /*
            String projectType = p.getClass().getName();
            LOG.warning( projectType);
            String name = ProjectUtils.getInformation(p).getDisplayName();
            try {
                String[] keys = ProjectUtils.getPreferences(p, Project.class, enabled).keys();
                for ( int i=0; i< keys.length; i++){
                    LOG.warning(keys[i]);
                }
            } catch (BackingStoreException ex) {
                Exceptions.printStackTrace(ex);
            }
            
            setEnabled(true);
                    */
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            // TODO menu item label with optional mnemonics
            putValue(NAME, "Open Mirah Project Folder");
        }
        public @Override void actionPerformed(ActionEvent e) {
            LOG.warning( "Hello world");
            // TODO what to do when run
           try {
                MirahProject mp = MirahProject.getMirahSubproject(p);
                //mp.generateProject();
                FileObject dir = mp.getProjectRoot();
                Desktop.getDesktop().open(new File(dir.toURI().getPath()));
            } catch ( Exception ex){
                
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(ex.getMessage()));
                throw new RuntimeException(ex);
            }
        }
        
        
    }
}


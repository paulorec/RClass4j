package rclass.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import rclass.util.ResourceScannerImpl;

public class GenerateRClassProjectAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell shell = HandlerUtil.getActiveShell(event);
	    ISelection sel = HandlerUtil.getActiveMenuSelection(event);
	    IStructuredSelection selection = (IStructuredSelection) sel;

	    Object firstElement = selection.getFirstElement();
	   
	    IProject iProject = null;
	    
	    if(firstElement instanceof JavaProject) {
	    	
	    	IJavaProject iJavaProject = (JavaProject) firstElement;
	    	
	    	iProject = iJavaProject.getProject();
	    			
	    }else if(firstElement instanceof IProject) {
	    	
	    	iProject = (IProject) firstElement; 
	    			
	    } else if(firstElement instanceof IResource) {
	    	
	    	iProject = ((IResource) firstElement).getProject();	
	    	
	    }
	    
	    if(iProject != null) {
	    	
	    	Job resourceScanner = new ResourceScannerImpl(iProject, "Language Resource Scanner - " + iProject.getName());
	    	
	    	resourceScanner.schedule();
	    	
	    } else {
	    	
	    	MessageDialog.openError(shell, "Please select a valid Java project", "I was unable to find a valid Java project, please right click on your root project and try it again.");
	    }
	    
	    
	    return null;
	}
	
}
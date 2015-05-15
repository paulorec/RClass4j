package br.com.facilit.eclipse.ext.rclass.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import br.com.facilit.eclipse.ext.rclass.util.ResourceScannerImpl;

public class GenerateRClassProjectAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		for (Object element : selection.toList()) {

			IJavaProject iProject = null;

			if (element instanceof JavaProject) {

				iProject = (JavaProject) element;

				Job resourceScanner = new ResourceScannerImpl(iProject,
						"Language Resource Scanner - "
								+ iProject.getElementName());

				resourceScanner.schedule();

			}
		}

		return null;
	}

}
package rclass.actions;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.wst.jsdt.web.core.internal.IncrementalBuilder;

import rclass.util.ResourceScannerImpl;

public class GenerateRClassWorkspaceBuilder extends IncrementalBuilder {

	@Override
	public IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {

		for (IProject iProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

			Job resourceScanner = new ResourceScannerImpl(JavaCore.create(iProject),
					"Workspace Language Resource Scanner - " + iProject.getName());

			resourceScanner.schedule();
		}
		
		return null;
	}

	@Override
	protected void startupOnInitialize() {
	}

	@Override
	protected void clean(IProgressMonitor monitor) {
	}

	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		
		for (IProject iProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

			Job resourceScanner = new ResourceScannerImpl(JavaCore.create(iProject),
					"Workspace Language Resource Scanner - " + iProject.getName());

			resourceScanner.schedule();
		}
	}
}

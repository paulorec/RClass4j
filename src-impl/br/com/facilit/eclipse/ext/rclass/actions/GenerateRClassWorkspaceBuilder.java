package br.com.facilit.eclipse.ext.rclass.actions;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.wst.jsdt.web.core.internal.IncrementalBuilder;

import br.com.facilit.eclipse.ext.rclass.Activator;
import br.com.facilit.eclipse.ext.rclass.service.util.StringPool;
import br.com.facilit.eclipse.ext.rclass.util.ResourceScannerImpl;

public class GenerateRClassWorkspaceBuilder extends IncrementalBuilder {

	public GenerateRClassWorkspaceBuilder() {
		super();
	}

	@Override
	public IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

		if (preferenceStore.getBoolean(StringPool.PREF_AUTO_BUILD_KEY)) {

			IProject iProject = getProject();

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

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		
		if (preferenceStore.getBoolean(StringPool.PREF_AUTO_BUILD_KEY)) {

			IProject iProject = getProject();

			Job resourceScanner = new ResourceScannerImpl(JavaCore.create(iProject),
					"Workspace Language Resource Scanner - " + iProject.getName());

			resourceScanner.schedule();
		}
	}
}

package br.com.facilit.eclipse.ext.rclass.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.cleanup.CleanUpContext;
import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.CleanUpRequirements;
import org.eclipse.jdt.ui.cleanup.ICleanUp;
import org.eclipse.jdt.ui.cleanup.ICleanUpFix;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class SaveActionCleanUp implements ICleanUp {

	@Override
	public RefactoringStatus checkPostConditions(IProgressMonitor arg0)
			throws CoreException {
		System.out.println("calling Check Post conditions");

		return null;
	}

	@Override
	public RefactoringStatus checkPreConditions(IJavaProject arg0,
			ICompilationUnit[] arg1, IProgressMonitor arg2)
			throws CoreException {

		System.out.println("calling Check Pre conditions");

		return null;
	}

	@Override
	public ICleanUpFix createFix(CleanUpContext arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CleanUpRequirements getRequirements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getStepDescriptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOptions(CleanUpOptions arg0) {
		// TODO Auto-generated method stub

	}

}

package br.com.facilit.eclipse.ext.rclass.extensions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.team.FileModificationValidationContext;
import org.eclipse.core.runtime.IStatus;

public class FileModificationValidator extends org.eclipse.core.resources.team.FileModificationValidator{

	@Override
	public IStatus validateEdit(IFile[] arg0,
			FileModificationValidationContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}

package br.com.facilit.eclipse.ext.rclass.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.preference.IPreferenceStore;

import br.com.facilit.eclipse.ext.rclass.Activator;
import br.com.facilit.eclipse.ext.rclass.service.util.StringPool;

public class ResourceVisitorUtil implements IResourceVisitor {

	private Map<IContainer, List<IFile>> matchedLanguageFileResources = new HashMap<>();
	private IFile existentIFile;

	private Pattern languageFilePattern;
	private String rClassName;

	public ResourceVisitorUtil(Pattern languageFilePattern, String rClassName) {
		this.languageFilePattern = languageFilePattern;
		this.rClassName = rClassName;
	}

	@Override
	public boolean visit(IResource resource) throws CoreException {

		if (resource instanceof IFile) {

			Matcher languageFileMatcher = languageFilePattern.matcher(resource.getName());
			if (languageFileMatcher.matches()) {

				IFile iFile = (IFile) resource;

				IContainer key = iFile.getParent();
				IProject project = iFile.getProject();
				IJavaProject javaProject = JavaCore.create(project);

				IPackageFragment packageFragment = javaProject.findPackageFragment(key.getFullPath());

				if (packageFragment != null && isValidParent(key)) {

					if (matchedLanguageFileResources.get(key) == null) {

						matchedLanguageFileResources.put(key, new ArrayList<IFile>());
					}

					matchedLanguageFileResources.get(key).add(iFile);
				}
			}

			if (resource.getName().equals(rClassName)) {

				existentIFile = ((IFile) resource);
			}
		}

		return true;
	}

	private boolean isValidParent(IContainer iContainer) {

		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();

		String prefSkipDirectoryPattern = prefStore.getString(StringPool.PREF_SKIP_DIRECTORY_KEY);

		if (prefSkipDirectoryPattern == null || prefSkipDirectoryPattern.equals(""))
			return true;

		try {

			Pattern pattern = Pattern.compile(prefSkipDirectoryPattern);
			Matcher mather = pattern.matcher(iContainer.getName());

			return !mather.matches();

		} catch (Exception e) {

			prefStore.putValue(StringPool.PREF_SKIP_DIRECTORY_KEY, StringPool.PREF_SKIP_DIRECTORY_VALUE);
			
			return true;
			
		}
		

	}

	public IFile getExistentRClassFile() {

		return existentIFile;
	}

	public Map<IContainer, List<IFile>> getMatchedResources() {

		return matchedLanguageFileResources;
	}
}

package rclass.util;

import java.util.ArrayList;
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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;

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

			Matcher languageFileMatcher = languageFilePattern.matcher(resource
					.getName());
			if (languageFileMatcher.matches()) {

				IFile iFile = (IFile) resource;

				IContainer key = iFile.getParent();
				IProject project = iFile.getProject();
				IJavaProject javaProject = JavaCore.create(project);

				IPackageFragment packageFragment = javaProject
						.findPackageFragment(key.getFullPath());

				if (packageFragment != null) {

					if (matchedLanguageFileResources.get(key) == null) {

						matchedLanguageFileResources.put(key,
								new ArrayList<IFile>());
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

	public IFile getExistentRClassFile() {

		return existentIFile;
	}

	public Map<IContainer, List<IFile>> getMatchedResources() {

		return matchedLanguageFileResources;
	}
}

package rclass.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

public class ResourceVisitorUtil implements IResourceVisitor {

	private List<IFile> matchedLanguageFileResources = new ArrayList<>();
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

			System.out.println("visiting " + resource.getName());

			Matcher languageFileMatcher = languageFilePattern.matcher(resource
					.getName());

			if (languageFileMatcher.matches()) {

				matchedLanguageFileResources.add((IFile) resource);
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

	public List<IFile> getMatchedResources() {

		return matchedLanguageFileResources;
	}
}

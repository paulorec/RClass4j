package rclass.util;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;

import rclass.Activator;
import rclass.models.LanguageEntry;

public class ResourceScannerImpl extends Job {

	private IJavaProject root;

	public ResourceScannerImpl(IJavaProject root, String name) {
		super(name);
		this.root = root;
	}

	@Override
	protected IStatus run(IProgressMonitor iProgressMonitor) {

		iProgressMonitor.beginTask(getName(), 3);

		try {

			IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);

			ResourceVisitorUtil resourceVisitor = new ResourceVisitorUtil(Pattern.compile(REGEX_LANGUAGE_PATTERN),
					R_CLASS_NAME);

			root.getResource().accept(resourceVisitor);

			Map<IContainer, List<IFile>> matchedResources = resourceVisitor.getMatchedResources();

			for (Map.Entry<IContainer, List<IFile>> map : matchedResources.entrySet()) {

				IContainer iContainer = map.getKey();

				String lastCheckSum = prefs.get(iContainer.getLocation().toString(), null);
				String currentCheckSum = ResourceUtil.getResourceCheckSum(map.getValue());

				if (lastCheckSum == null || !currentCheckSum.equals(lastCheckSum)) {

					Set<LanguageEntry> languageEntryByContext = new HashSet<LanguageEntry>();

					for (IFile iFile : map.getValue()) {

						List<LanguageEntry> languageEntryList = LanguageEntryProcessorUtil.getEntryLanguageEntry(iFile);

						ClassWriterUtil.writeOutPutLanguageFile(languageEntryList, createLanguageOutputFile(iFile));

						languageEntryByContext.addAll(languageEntryList);
					}

					IPackageFragment packageFragment = root.findPackageFragment(iContainer.getFullPath());

					ClassWriterUtil.writeRClass(createRClassFile(iContainer), packageFragment, languageEntryByContext);
					
					prefs.put(iContainer.getLocation().toString(), currentCheckSum);

				}
			}

			refreshContainers(matchedResources.keySet(), iProgressMonitor);

			return Status.OK_STATUS;

		} catch (Throwable e) {

			e.printStackTrace();

			return Status.CANCEL_STATUS;

		} finally {

			iProgressMonitor.worked(STEP.SCANNING_RESOURCES.getStepNumber());
		}
	}

	private File createRClassFile(IContainer iContainer) {

		return new File(iContainer.getLocation() + File.separator + R_CLASS_NAME);
	}

	private File createLanguageOutputFile(IFile iFile) {

		return new File(iFile.getLocation().removeFileExtension().toString() + OUTPUT_LANGUAGE_FILE_SUFFIX);
	}

	private void refreshContainers(Set<IContainer> setIcontainer, IProgressMonitor iProgressMonitor)
			throws CoreException {

		for (IContainer iContainer : setIcontainer) {

			iContainer.refreshLocal(IContainer.DEPTH_INFINITE, iProgressMonitor);
		}
	}

	private static enum STEP {
		// @formatter:off
		SCANNING_RESOURCES("scanning resources", 1), READING_KEYS(
				"reading valid keys", 2), WRITING_FILE("writing file", 3);
		// @formatter:on

		private String key;
		private int stepNumber;

		STEP(String key, int stepNumber) {
			this.key = key;
			this.stepNumber = stepNumber;
		}

		public String getKey() {
			return key;
		}

		public int getStepNumber() {
			return stepNumber;
		}
	}

	private String REGEX_LANGUAGE_PATTERN = "^Language_[\\w]*.properties.native";
	private String R_CLASS_NAME = "R.java";
	private String OUTPUT_LANGUAGE_FILE_SUFFIX = "encoded";

}

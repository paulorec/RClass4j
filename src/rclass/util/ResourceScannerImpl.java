package rclass.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class ResourceScannerImpl extends Job {

	public ResourceScannerImpl(String name) {
		super(name);
	}

	private String REGEX_LANGUAGE_PATTERN = "^Language_[a-z_]*.properties.native";
	private String R_CLASS_NAME = "RClass.java";
	private String LINE_TOKEN = "=";

	@Override
	protected IStatus run(IProgressMonitor iProgressMonitor) {

		iProgressMonitor.beginTask(getName(), 3);

		try {

			ResourceVisitorUtil resourceVisitor = new ResourceVisitorUtil(
					Pattern.compile(REGEX_LANGUAGE_PATTERN), R_CLASS_NAME);

			iProgressMonitor.subTask(STEP.SCANNING_RESOURCES.getKey());

			ResourcesPlugin.getWorkspace().getRoot().accept(resourceVisitor);

			List<IFile> iFileList = resourceVisitor.getMatchedResources();

			iProgressMonitor.subTask(STEP.READING_KEYS.getKey());

			Set<String> setKeys = getKeys(iFileList);

			iProgressMonitor.worked(STEP.READING_KEYS.getStepNumber());

			ClassWriterUtil.writeClass(setKeys, file);

			return Status.OK_STATUS;

		} catch (Throwable e) {

			e.printStackTrace();

			return Status.CANCEL_STATUS;

		} finally {

			iProgressMonitor.worked(STEP.SCANNING_RESOURCES.getStepNumber());
		}
	}

	private Set<String> getKeys(List<IFile> iFileList) {

		Set<String> key = new HashSet<>();

		for (IFile iFile : iFileList) {

			try {

				BufferedReader fileInputStream = new BufferedReader(
						new InputStreamReader(iFile.getContents()));

				for (String line; (line = fileInputStream.readLine()) != null;) {

					if (line.contains(LINE_TOKEN)) {

						String prefix = line.substring(0,
								line.indexOf(LINE_TOKEN));

						if (!prefix.trim().equals("")) {

							key.add(prefix);

						}
					}
				}

			} catch (CoreException e) {

				System.out.println("failed to read file " + iFile.getFullPath()
						+ File.separator + iFile.getName());

			} catch (IOException e) {

				System.out.println("failed to read file " + iFile.getFullPath()
						+ File.separator + iFile.getName());
			}
		}

		return key;
	}

	private static enum STEP {
		// @formatter:off
		SCANNING_RESOURCES("scanning resources", 1), READING_KEYS(
				"reading valid keys", 2), WRITING_FILE("writing class", 3);
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
}

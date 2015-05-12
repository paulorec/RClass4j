package rclass.util;

import java.util.List;

import org.eclipse.core.resources.IFile;

import rclass.models.LanguageEntry;

public class LanguageEntryProcessorUtil {

	public static List<LanguageEntry> getEntryLanguageEntry(List<IFile> listIFile) {

		return getInstance().getEntryLanguageEntry(listIFile);
	}
	
	public static List<LanguageEntry> getEntryLanguageEntry(IFile iFile) {
		
		return getInstance().getEntryLanguageEntry(iFile);
	}

	private static LanguageEntryProcessor getInstance() {

		if (_instance == null) {

			_instance = new LanguageEntryProcessorImpl();
		}
		return _instance;
	}

	private static LanguageEntryProcessor _instance;
}

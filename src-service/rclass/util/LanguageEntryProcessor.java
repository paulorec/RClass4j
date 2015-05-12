package rclass.util;

import java.util.List;

import org.eclipse.core.resources.IFile;

import rclass.models.LanguageEntry;

public interface LanguageEntryProcessor {

	public List<LanguageEntry> getEntryLanguageEntry(List<IFile> iFile);

	public List<LanguageEntry> getEntryLanguageEntry(IFile iFile);
}

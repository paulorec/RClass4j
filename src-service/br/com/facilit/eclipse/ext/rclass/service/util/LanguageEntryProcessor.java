package br.com.facilit.eclipse.ext.rclass.service.util;

import java.util.List;

import org.eclipse.core.resources.IFile;

import br.com.facilit.eclipse.ext.rclass.service.models.LanguageEntry;

public interface LanguageEntryProcessor {

	public List<LanguageEntry> getEntryLanguageEntry(List<IFile> iFile);

	public List<LanguageEntry> getEntryLanguageEntry(IFile iFile);
}

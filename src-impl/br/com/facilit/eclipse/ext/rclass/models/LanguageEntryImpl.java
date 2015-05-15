package br.com.facilit.eclipse.ext.rclass.models;

import br.com.facilit.eclipse.ext.rclass.models.impl.LanguageEntryModelImpl;
import br.com.facilit.eclipse.ext.rclass.service.models.LanguageEntry;

public class LanguageEntryImpl extends LanguageEntryModelImpl implements LanguageEntry {

	@Override
	public String getValue(String localeKey) {
		
		return getValueMap().get(localeKey);
	}

}

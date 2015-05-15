package rclass.models;

import rclass.models.impl.LanguageEntryModelImpl;

public class LanguageEntryImpl extends LanguageEntryModelImpl implements LanguageEntry {

	@Override
	public String getValue(String localeKey) {
		
		return getValueMap().get(localeKey);
	}

}

package br.com.facilit.eclipse.ext.rclass.models.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.facilit.eclipse.ext.rclass.service.models.LanguageEntryModel;

public abstract class LanguageEntryModelImpl implements LanguageEntryModel {

	private String nativeKey;
	private String encodedKey;
	private String javaKey;
	private Map<String, String> localeMap = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#getNativeKey()
	 */
	@Override
	public String getNativeKey() {
		return nativeKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#setNativeKey(java.lang.String)
	 */
	@Override
	public void setNativeKey(String nativeKey) {
		this.nativeKey = nativeKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#getEncodedKey()
	 */
	@Override
	public String getEncodedKey() {
		return encodedKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#setEncodedKey(double)
	 */
	@Override
	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#getJavaKey()
	 */
	@Override
	public String getJavaKey() {
		return javaKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#setJavaKey(java.lang.String)
	 */
	@Override
	public void setJavaKey(String javaKey) {
		this.javaKey = javaKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#getLocaleMap()
	 */
	@Override
	public Map<String, String> getValueMap() {
		return localeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#setLocaleMap(java.util.Map)
	 */
	@Override
	public void setLocaleMap(Map<String, String> localeMap) {
		this.localeMap = localeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rclass.models.LanguageEntryModel#setLocaleMap(java.util.Map)
	 */
	@Override
	public void addScopedValue(String key, String value) {

		this.localeMap.put(key, value);
	}

	public int hashCode() {
		if(this.getEncodedKey() == null)
			return 0;
		
		return this.getEncodedKey().hashCode();
	}
	
	public boolean equals(Object object) {
		
		if(!(object instanceof LanguageEntryModel))
			return false;
		
		LanguageEntryModel le1 = (LanguageEntryModel) object;
		
		if(le1.getEncodedKey() == null && this.getEncodedKey() == null) {
			
			return true;
			
		}else if(le1.getEncodedKey() == null || this.getEncodedKey() == null) {
			
			return false;
		}
		
		return le1.getEncodedKey().equals(this.getEncodedKey());
		
	}
}
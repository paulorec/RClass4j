package rclass.models;

import java.util.Map;

public interface LanguageEntryModel {

	public String getNativeKey();

	public void setNativeKey(String nativeKey);

	public String getEncodedKey();

	public void setEncodedKey(String encodedKey);

	public String getJavaKey();

	public void setJavaKey(String javaKey);

	public Map<String, String> getValueMap();

	public void setLocaleMap(Map<String, String> localeMap);

	public void addScopedValue(String key, String value);

}
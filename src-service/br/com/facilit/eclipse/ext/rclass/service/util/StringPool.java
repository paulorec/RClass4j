package br.com.facilit.eclipse.ext.rclass.service.util;

public interface StringPool {

	String PREF_AUTO_BUILD_KEY = "auto-build-enabled";
	String PREF_LANGUAGE_PATTERN_KEY = "language-file-pattern";
	String PREF_SKIP_DIRECTORY_KEY = "skip-directory-pattern";
	
	String PREF_LANGUAGE_PATTERN_VALUE= "^Language_[\\w]*.properties.native";
	String PREF_SKIP_DIRECTORY_VALUE = "bin";
	
}

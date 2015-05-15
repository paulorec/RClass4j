package ${packageName};

/**
 * Auto-mapped language resource class
 * 
 * This class is auto-generated. Modification in this class will be overwritten.
 * 
 * @author Paulo Amorim
 */

public interface R {
	<#list languageEntryList as languageEntry>
		/**
		<#list languageEntry.valueMap?keys as prop>
	 	 * @see ${languageEntry.nativeKey}
		</#list>
		 */
		double ${languageEntry.javaKey} = ${languageEntry.encodedKey};
		
	</#list>
}
package br.com.facilit.eclipse.ext.rclass.ui;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import br.com.facilit.eclipse.ext.rclass.Activator;
import br.com.facilit.eclipse.ext.rclass.service.util.StringPool;
import br.com.facilit.eclipse.ext.rclass.util.GenerateRClassBuilderUtil;

public class RClassPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private BooleanFieldEditor autoBuildFieldEditor;
	private StringFieldEditor languagePatternFieldEditor;
	private StringFieldEditor skipDirectoryFieldEditor;

	public RClassPreferencePage() {

		super(GRID);
	}

	@Override
	public void init(IWorkbench arg0) {
		
		IPreferenceStore iPreferenceStore = Activator.getDefault().getPreferenceStore();
		
		setDefaultValues(iPreferenceStore);
		setPreferenceStore(iPreferenceStore);

	}

	private void initAutoBuildField(IEclipsePreferences prefs) {

		autoBuildFieldEditor = new BooleanFieldEditor(StringPool.PREF_AUTO_BUILD_KEY,
				"&Auto generate R Class on build", getFieldEditorParent());

		addField(autoBuildFieldEditor);
	}

	private void initLanguagePatternField(IEclipsePreferences prefs) {

		languagePatternFieldEditor = new StringFieldEditor(StringPool.PREF_LANGUAGE_PATTERN_KEY,
				"&Language pattern file name:", getFieldEditorParent());

		String value = prefs.get(StringPool.PREF_LANGUAGE_PATTERN_KEY,
				StringPool.PREF_LANGUAGE_PATTERN_VALUE);
		
		if(value == null || value.equals(""))
			value = StringPool.PREF_LANGUAGE_PATTERN_VALUE;
		
		languagePatternFieldEditor.setStringValue(value);

		addField(languagePatternFieldEditor);
	}

	private void initSkipDirectoryPatternField(IEclipsePreferences prefs) {

		skipDirectoryFieldEditor = new StringFieldEditor(StringPool.PREF_SKIP_DIRECTORY_KEY, "Skip directory pattern:",
				getFieldEditorParent());

		String value = prefs.get(StringPool.PREF_SKIP_DIRECTORY_KEY,
				StringPool.PREF_SKIP_DIRECTORY_VALUE);
		
		if(value == null || value.equals(""))
			value = StringPool.PREF_SKIP_DIRECTORY_VALUE;
		
		skipDirectoryFieldEditor.setStringValue(value);

		addField(skipDirectoryFieldEditor);
	}

	@Override
	protected void createFieldEditors() {

		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);

		initAutoBuildField(prefs);

		initLanguagePatternField(prefs);

		initSkipDirectoryPatternField(prefs);
	}

	private void setDefaultValues(IPreferenceStore prefStore) {
		
		prefStore.setDefault(StringPool.PREF_SKIP_DIRECTORY_KEY, StringPool.PREF_SKIP_DIRECTORY_VALUE);
		prefStore.setDefault(StringPool.PREF_LANGUAGE_PATTERN_KEY, StringPool.PREF_LANGUAGE_PATTERN_VALUE);
	}
	
	@Override
	public void performApply() {
		save();
	}

	@Override
	public boolean performOk() {

		return save();

	}

	private boolean save() {

		try {

			autoBuildFieldEditor.store();
			languagePatternFieldEditor.store();
			skipDirectoryFieldEditor.store();

			GenerateRClassBuilderUtil.updateAllProjectBuilder();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}
}

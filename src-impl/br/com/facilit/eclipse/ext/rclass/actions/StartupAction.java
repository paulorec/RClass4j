package br.com.facilit.eclipse.ext.rclass.actions;

import br.com.facilit.eclipse.ext.rclass.util.GenerateRClassBuilderUtil;

public class StartupAction implements org.eclipse.ui.IStartup {

	@Override
	public void earlyStartup() {
		
		GenerateRClassBuilderUtil.updateAllProjectBuilder();
	}

}

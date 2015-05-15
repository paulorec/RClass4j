package br.com.facilit.eclipse.ext.rclass.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;

import br.com.facilit.eclipse.ext.rclass.Activator;
import br.com.facilit.eclipse.ext.rclass.service.util.StringPool;

public class GenerateRClassBuilderUtil {

	public static void updateAllProjectBuilder() {

		for (IProject iProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

			try {

				GenerateRClassBuilderUtil.updateProjectBuilder(iProject);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public static void updateProjectBuilder(IProject project) throws CoreException {

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

		IProjectDescription desc = project.getDescription();

		List<ICommand> listCommands = new ArrayList<ICommand>(Arrays.asList(desc.getBuildSpec()));

		ICommand pluginCommand = null;

		for (ICommand iCommand : listCommands) {

			if (iCommand.getBuilderName().equals(BUILDER_ID)) {

				pluginCommand = iCommand;
			}
		}

		if (pluginCommand == null && preferenceStore.getBoolean(StringPool.PREF_AUTO_BUILD_KEY)) {

			listCommands.add(createPluginCommand(desc));

		} else if (pluginCommand != null && !preferenceStore.getBoolean(StringPool.PREF_AUTO_BUILD_KEY)) {

			listCommands.remove(pluginCommand);
		}

		desc.setBuildSpec(listCommands.toArray(new ICommand[listCommands.size()]));

		project.setDescription(desc, null);
	}

	private static ICommand createPluginCommand(IProjectDescription desc) {

		ICommand command = desc.newCommand();

		command.setBuilderName(BUILDER_ID);

		return command;
	}

	public final static String BUILDER_ID = "RClass.rclassBuilder";

}

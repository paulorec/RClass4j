package rclass.actions;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class StartupAction implements org.eclipse.ui.IStartup {

	final String BUILDER_ID = "RClass.rclassBuilder";
	
	@Override
	public void earlyStartup() {
		addIncrementalProjectBuilder();
	}

	private void addIncrementalProjectBuilder() {

		for (IProject iProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

			try {

				addIncrementalProjectBuilder(iProject);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	private void addIncrementalProjectBuilder(IProject project) throws CoreException {

		IProjectDescription desc = project.getDescription();
		ICommand[] commands = desc.getBuildSpec();
		boolean found = false;

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(BUILDER_ID)) {
				found = true;
				break;
			}
		}
		if (!found) {
			ICommand command = desc.newCommand();
			command.setBuilderName(BUILDER_ID);
			ICommand[] newCommands = new ICommand[commands.length + 1];

			System.arraycopy(commands, 0, newCommands, 1, commands.length);
			newCommands[0] = command;
			desc.setBuildSpec(newCommands);
			project.setDescription(desc, null);
		}
	}
}

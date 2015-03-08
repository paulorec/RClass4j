package rclass.extensions;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;

public class ResourceChangeListenerImpl implements IResourceChangeListener{

	@Override
	public void resourceChanged(IResourceChangeEvent event) {

		if(event.getType() == IResourceChangeEvent.POST_CHANGE) {
			
			try {
				
				event.getDelta().accept(new DeltaPrinter());
			
			} catch (CoreException e) {
				
				e.printStackTrace();
			}
		}
	}

}

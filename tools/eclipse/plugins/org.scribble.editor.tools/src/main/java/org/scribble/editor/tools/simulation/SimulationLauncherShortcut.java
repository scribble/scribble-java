/*
 * Copyright 2009-14 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.editor.tools.simulation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * This class is responsible for launching a trace simulation.
 */
public class SimulationLauncherShortcut implements org.eclipse.debug.ui.ILaunchShortcut {

	/**
	 * {@inheritDoc}
	 */
	public void launch(IEditorPart editor, String mode) {
	}

	/**
	 * {@inheritDoc}
	 */
    public void launch(ISelection selection, String mode) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            if (ss.size() == 1) {
            	Object element = ss.getFirstElement();
                if (element instanceof IFile || element instanceof IFolder
                    					|| element instanceof IProject) {
                    IResource res=(IResource)element;
                    ILaunchConfiguration configuration = getConfiguration(res);
                    if (configuration != null) {
                    	DebugUITools.launch(configuration, mode);
                    }
                }
            }
        }
    }

    /**
     * Returns a MIDI configuration to use for the given file or
     * <code>null</code> to cancel. Creates a new configuration
     * if required.
     *
     * @param res The resource
     * @return associated launch configuration or <code>null</code>
     */
    private ILaunchConfiguration getConfiguration(IResource res) {
            List<ILaunchConfiguration> candiates = new ArrayList<ILaunchConfiguration>();
            try {
                    ILaunchConfiguration[] configurations = getLaunchManager().getLaunchConfigurations(getLaunchType());
                    for (int i = 0; i < configurations.length; i++) {
                            ILaunchConfiguration configuration = configurations[i];
                            IResource[] resources = configuration.getMappedResources();
                            if (resources != null && resources.length == 1 &&
                                            resources[0].equals(res)) {
                                    candiates.add(configuration);
                            }
                    }
            } catch (CoreException e) {
            }
            if (!candiates.isEmpty()) {
                    return chooseConfiguration(candiates);
            }
            return newConfiguration(res);
    }

    /**
     * Returns the MIDI launch configuration type.
     *
     * @return the MIDI launch configuration type
     */
    private ILaunchConfigurationType getLaunchType() {
            ILaunchManager manager = getLaunchManager();
            ILaunchConfigurationType type = manager.getLaunchConfigurationType(SimulationLaunchConfigurationConstants.LAUNCH_CONFIG_TYPE);
            return type;
    }

    /**
     * Returns the launch manager.
     *
     * @return launch manager
     */
    private ILaunchManager getLaunchManager() {
            ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
            return manager;
    }
   
    /**
     * Returns a configuration from the given collection of configurations that should be launched,
     * or <code>null</code> to cancel.
     *
     * @param configList list of configurations to choose from
     * @return configuration to launch or <code>null</code> to cancel
     */
    private ILaunchConfiguration chooseConfiguration(List<ILaunchConfiguration> configList) {
            if (configList.size() == 1) {
                    return (ILaunchConfiguration) configList.get(0);
            }
            IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
            
            ElementListSelectionDialog dialog= new ElementListSelectionDialog(Display.getCurrent().getActiveShell(),
            				labelProvider);
            dialog.setElements(configList.toArray());
            dialog.setTitle("Select Configuraiton"); //$NON-NLS-1$
            dialog.setMessage("&Select an existing configuration:"); //$NON-NLS-1$
            dialog.setMultipleSelection(false);
            int result = dialog.open();
            labelProvider.dispose();
            if (result == Window.OK) {
                    return (ILaunchConfiguration) dialog.getFirstResult();
            }
            return null;            
    }
   
    /**
     * Creates and returns a new simulation launch configuration for the
     * given resource.
     *
     * @param res The resource
     * @return The new launch configuration
     */
    private ILaunchConfiguration newConfiguration(IResource res) {
            ILaunchConfigurationType type = getLaunchType();
            try {
                    ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null,
                    		getLaunchManager().generateLaunchConfigurationName(
                    			"Simulate [" + res.getProject().getName() + "] " + res.getName())); //$NON-NLS-1$ //$NON-NLS-2$
                    workingCopy.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME,
        					res.getProject().getName());
                    workingCopy.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PATH,
        					res.getProjectRelativePath().toString());
                    workingCopy.setMappedResources(new IResource[]{res});
                    return workingCopy.doSave();
            } catch (CoreException e) {
                    e.printStackTrace();
            }
            return null;
    }
}
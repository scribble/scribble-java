/*
 * Copyright 2009-14 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * Yolassu may obtain a copy of the License at
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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * This class represents the main tab within the tab group
 * associated with the simulation launch configuration.
 */
public class SimulationMainTab extends AbstractLaunchConfigurationTab {

	private static final String TRACE_EXTENSION = "trace";
	private Label _projectLabel=null;
	private Text _project=null;
	private Button _projectButton=null;
	private Label _pathLabel=null;
	private Text _path=null;
	private Button _pathButton=null;	
	
	/**
	 * @see ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {		
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);

		GridLayout topLayout = new GridLayout();
		topLayout.numColumns= 3;
		comp.setLayout(topLayout);		
		
		Label label = new Label(comp, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		label.setLayoutData(gd);
		
		createSimulationSection(comp);
		
		label = new Label(comp, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan = 3;
		label.setLayoutData(gd);
		
		Dialog.applyDialogFont(comp);
		validatePage();
	}
	
	/**
	 * This method creates the GUI components for the
	 * simulation tab.
	 * 
	 * @param comp The composite
	 */
	protected void createSimulationSection(Composite comp) {
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		
		_projectLabel = new Label(comp, SWT.NONE);
		_projectLabel.setText("Project");
		gd= new GridData();
		gd.horizontalIndent = 25;
		_projectLabel.setLayoutData(gd);
		
		_project= new Text(comp, SWT.SINGLE | SWT.BORDER);
		_project.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		_project.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				validatePage();
				updateLaunchConfigurationDialog();				
				_pathButton.setEnabled(_project.getText().length() > 0);
			}
		});
			
		_projectButton = new Button(comp, SWT.PUSH);
		_projectButton.setText("Browse");
		_projectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleProjectButtonSelected();
			}
		});
		setButtonGridData(_projectButton);
		
		_pathLabel = new Label(comp, SWT.NONE);
		gd = new GridData();
		gd.horizontalIndent = 25;
		_pathLabel.setLayoutData(gd);
		_pathLabel.setText("Simulation Path");
		
		_path = new Text(comp, SWT.SINGLE | SWT.BORDER);
		_path.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		_path.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				validatePage();
				updateLaunchConfigurationDialog();
			}
		});
		
		_pathButton = new Button(comp, SWT.PUSH);
		_pathButton.setEnabled(_project.getText().length() > 0);		
		_pathButton.setText("Search");
		_pathButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleSearchButtonSelected();
			}
		});
		setButtonGridData(_pathButton);
	}

	protected static Image createImage(String path) {
		return null;
	}


	/**
	 * @see ILaunchConfigurationTab#initializeFrom(ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration config) {
		String projectName= "";
		String scenario= "";

		try {
			projectName = config.getAttribute(SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""); //$NON-NLS-1$
		} catch (CoreException ce) {
		}
		_project.setText(projectName);
		
		try {
			scenario = config.getAttribute(SimulationLaunchConfigurationConstants.ATTR_PATH, ""); //$NON-NLS-1$
		} catch (CoreException ce) {			
		}
		_path.setText(scenario);
	}

	/**
	 * @see ILaunchConfigurationTab#performApply(ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy config) {
	}

	/**
	 * @see ILaunchConfigurationTab#dispose()
	 */
	public void dispose() {
		super.dispose();
	}

	/**
	 * @see AbstractLaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return(null);
	}

	/**
	 * This method sets the grid data for the button.
	 * 
	 * @param button The button
	 */
	protected void setButtonGridData(Button button) {
		GridData gridData= new GridData();
		button.setLayoutData(gridData);
		//SWTUtil.setButtonDimensionHint(button);
	}

	/**
	 * Show a dialog that lists all trace files within the
	 * selected project
	 */
	protected void handleSearchButtonSelected() {
		
		IProject project = getProject();

		ILabelProvider labelProvider=new LabelProvider() {
			public String getText(Object obj) {
				String ret="<unknown>";
				if (obj instanceof IResource) {
					String filename=((IResource)obj).getName();
					if (filename.endsWith(TRACE_EXTENSION)) {
						filename = filename.substring(0, filename.length()-
								TRACE_EXTENSION.length()-1);
					}
					ret = filename+" ["+
						((IResource)obj).getParent().
						getProjectRelativePath()+"]";
				}
				return(ret);
			}
		};
		
		IResource[] traces=null;
		
		if (project.exists() == false) {
			traces = new IResource[0];
		} else {
			traces = getTraces(project);
		}

		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle("Traces");
		dialog.setMessage("Select the relevant trace");
		dialog.setElements(traces);
		
		if (dialog.open() == Window.OK) {			
			IResource file=(IResource)dialog.getFirstResult();
			_path.setText(file.getProjectRelativePath().toString());
		}
	}
	
	/**
	 * This method returns the list of scenario resource files within
	 * the supplied project.
	 * 
	 * @param project The project
	 * @return The list of scenario resource files
	 */
	protected IResource[] getTraces(IProject project) {
		IResource[] ret=null;
		final java.util.Vector<IResource> list=new java.util.Vector<IResource>();
		
		try {
			project.accept(new org.eclipse.core.resources.IResourceVisitor() {
				public boolean visit(IResource res) {

					if (res.getFileExtension() != null &&
							res.getFileExtension().equals(TRACE_EXTENSION)) {
						list.add(res);
					}

					return(true);
				}
			});
			
			ret = new IResource[list.size()];
			list.copyInto(ret);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return(ret);
	}
		
	/**
	 * Show a dialog that lets the user select a project.  This in turn provides
	 * context for the main type, allowing the user to key a main type name, or
	 * constraining the search for main types to the specified project.
	 */
	protected void handleProjectButtonSelected() {
		IProject project = chooseProject();
		if (project == null) {
			return;
		}
		
		String projectName = project.getName();
		_project.setText(projectName);		
	}
	
	/**
	 * Realize a Java Project selection dialog and return the first selected project,
	 * or null if there was none.
	 */
	protected IProject chooseProject() {
		IProject[] projects;
		try {
			projects= getWorkspaceRoot().getProjects();
		} catch (Exception e) {
			projects= new IProject[0];
		}
		
		ILabelProvider labelProvider=new LabelProvider() {
			public String getText(Object obj) {
				String ret="<unknown>";
				if (obj instanceof IResource) {
					ret = ((IResource)obj).getName();
				}
				return(ret);
			}
		};

		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle("Projects");
		dialog.setMessage("Select the relevant project");
		dialog.setElements(projects);
		
		IProject project = getProject();
		if (project != null) {
			dialog.setInitialSelections(new Object[] { project });
		}
		if (dialog.open() == Window.OK) {			
			return (IProject) dialog.getFirstResult();
		}			
		return null;		
	}
	
	/**
	 * Return the IProject corresponding to the project name in the project name
	 * text field, or null if the text does not match a project name.
	 */
	protected IProject getProject() {
		String projectName = _project.getText().trim();
		if (projectName.length() < 1) {
			return null;
		}
		return(getWorkspaceRoot().getProject(projectName));
	}
	
	/**
	 * Convenience method to get the workspace root.
	 */
	private IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	/**
	 * @see ILaunchConfigurationTab#isValid(ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration config) {		
		return getErrorMessage() == null;
	}
	
	/**
	 * This method validates the page.
	 *
	 */
	private void validatePage() {
		setErrorMessage(null);
		setMessage(null);
		
		String projectName = _project.getText().trim();
		if (projectName.length() == 0) {
			setErrorMessage("Project name not specified");
			return;
		}
			
		IProject project = getWorkspaceRoot().getProject(projectName);
		if (!project.exists()) {
			setErrorMessage("Project '"+projectName+"' does not exist");
			return;
		}
		
		try {
			String traceName = _path.getText().trim();
			if (traceName.length() == 0) {
				setErrorMessage("Trace has not been defined");
				return;
			}
			IResource resource = project.findMember(traceName);
			if (resource == null) {
				setErrorMessage("Could not find trace '"+traceName+"'");
			} else {
				
				// TODO: Check is valid trace model
			}
		} catch (Exception e) {
		}
	}

	/**
	 * @see ILaunchConfigurationTab#setDefaults(ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		
		IResource resource = getContext();
		if (resource != null) {
			initializeProject(resource, config);
		} else {
			config.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			config.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PATH, "");
		}
		initializeTestAttributes(resource, config);
	}

	/**
	 * This method identifies the context associated with the
	 * service test.
	 * 
	 * @return The context resource
	 */
	protected IResource getContext() {
		IResource ret=null;
		IWorkbenchPage page =
			org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				
		if (page != null) {
			ISelection selection = page.getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection)selection;
				if (!ss.isEmpty()) {
					Object obj = ss.getFirstElement();
					if (obj instanceof IResource) {
						ret = (IResource)obj;
					}
				}
			}
			
			if (ret == null) {
				IEditorPart part = page.getActiveEditor();
				if (part != null) {
					IEditorInput input = part.getEditorInput();
					ret =(IResource)input.getAdapter(IResource.class);
				}
			}
		}
		
		return(ret);
	}

	/**
	 * This method initializes the project details.
	 * 
	 * @param resource The resource
	 * @param config The configuration
	 */
	protected void initializeProject(IResource resource, ILaunchConfigurationWorkingCopy config) {
		IProject project = resource.getProject();
		String name = null;
		if (project != null && project.exists()) {
			name = project.getName();
		}
		config.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME, name);
	}
	
	/**
	 * This method initializes the trace details.
	 * 
	 * @param resource The selected resource
	 * @param config The configuration
	 */
	private void initializeTestAttributes(IResource resource, ILaunchConfigurationWorkingCopy config) {
		if (resource != null && (resource.getType() == IResource.FOLDER ||
				(resource.getType() == IResource.FILE &&
				resource.getFileExtension().equals(TRACE_EXTENSION)))) {
			
			config.setAttribute(SimulationLaunchConfigurationConstants.ATTR_PATH,
					resource.getProjectRelativePath().toString());
		
			initializeName(config, resource.getName());
		}
	}

	/**
	 * This method initializes the launch configuration name.
	 * 
	 * @param config The configuration
	 * @param name The name
	 */
	private void initializeName(ILaunchConfigurationWorkingCopy config, String name) {
		if (name == null) {
			name= "";
		}
		if (name.length() > 0) {
			
			int index = name.lastIndexOf('.');
			if (index > 0) {
				name = name.substring(0, index);
			}
			name= getLaunchConfigurationDialog().generateName(name);
			config.rename(name);
		}
	}

	/**
	 * @see ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return("Simulation");
	}
}

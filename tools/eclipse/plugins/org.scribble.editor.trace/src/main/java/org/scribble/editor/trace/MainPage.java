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
package org.scribble.editor.trace;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.editor.*;
import org.eclipse.ui.forms.widgets.*;
import org.scribble.editor.trace.commands.AbstractCommand;
import org.scribble.editor.trace.commands.AddToListCommand;
import org.scribble.editor.trace.commands.PropertyAccessor;
import org.scribble.editor.trace.commands.RemoveFromListCommand;
import org.scribble.editor.trace.commands.SelectCommand;
import org.scribble.editor.trace.commands.SetPropertyCommand;
import org.scribble.editor.trace.osgi.TraceEditorActivator;
import org.scribble.trace.model.MonitorRoleSimulator;
import org.scribble.trace.model.Role;
import org.scribble.trace.model.RoleSimulator;
import org.scribble.trace.model.Trace;

/**
 * Page providing the main details about a trace.
 */
public class MainPage extends FormPage {
	
	private Role _role;
	
	private Text _roleName;
	private Combo _simulatorTypeButton;
	private Text _monSimModuleName;
	private Text _monSimProtocolName;
	private Text _monSimRoleName;
	private Composite _monSim;
	private Button _roleRemoveButton;
	
	private TableViewer _viewer;
	
	/**
	 * @param id
	 * @param title
	 */
	public MainPage(TraceEditor editor) {
		super(editor, "main", Messages.getString("MainPage.label")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	protected TraceEditor getTraceEditor() {
		return ((TraceEditor)this.getEditor());
	}
	
	/**
	 * This method returns the trace.
	 * 
	 * @return The trace
	 */
	protected Trace getTrace() {
		return (getTraceEditor().getTrace());
	}
	
	protected void setRole(Role role) {
		boolean f_changed=(_role != role);
		
		_role = role;
		
		if (f_changed) {
			roleUpdated();
		}
	}
	
	protected Role getRole() {
		return (_role);
	}
	
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(Messages.getString("MainPage.title")); //$NON-NLS-1$
		form.setBackgroundImage(TraceEditorActivator.getDefault().getImage(TraceEditorActivator.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout);
		
		Composite header=toolkit.createComposite(form.getBody());
		GridData gd = new GridData(GridData.FILL);
		gd.horizontalSpan = 2;
		gd.heightHint = 100;
		header.setLayoutData(gd);

		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		header.setLayout(layout2);

		toolkit.createLabel(header, "Name");
		Text nameField=toolkit.createText(header, getTrace().getName(), SWT.BORDER);
		
		GridData gd0 = new GridData(GridData.FILL);
		gd0.widthHint = 300;
		nameField.setLayoutData(gd0);
		
		nameField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,Trace,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(value);
						}							
					},
					new PropertyAccessor<Trace,String>(getTrace()) {
						public String getPropertyValue() {
							return getModel().getName();
						}
						public void setPropertyValue(String value) {
							getModel().setName(value);
						}							
					}, null, null));
			}
		});
		
		toolkit.createLabel(header, "Description");
		Text descriptionField=toolkit.createText(header, getTrace().getDescription(), SWT.BORDER|SWT.MULTI);
		
		GridData gd1 = new GridData(GridData.FILL);
		gd1.widthHint = 500;
		gd1.heightHint = 50;		
		descriptionField.setLayoutData(gd1);
		
		descriptionField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,Trace,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(value);
						}							
					},
					new PropertyAccessor<Trace,String>(getTrace()) {
						public String getPropertyValue() {
							return getModel().getDescription();
						}
						public void setPropertyValue(String value) {
							getModel().setDescription(value);
						}							
					}, null, null));
			}
		});
		
		createRoleSection(form, toolkit);
		createDetailsSection(form, toolkit);
	}
	
	private void createRoleSection(final ScrolledForm form, FormToolkit toolkit) {
		Composite client = toolkit.createComposite(form.getBody(), SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		client.setLayout(layout);
		
		Label title=toolkit.createLabel(client, "Roles", SWT.BOLD);
		GridData titlegd=new GridData();
		titlegd.horizontalSpan = 2;
		title.setLayoutData(titlegd);
		
		Composite separator=toolkit.createCompositeSeparator(client);
		
		GridData gd2 = new GridData(GridData.FILL);
		gd2.heightHint = 2;
		gd2.widthHint = 200;
		gd2.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gd2.horizontalSpan = 2;
		separator.setLayoutData(gd2);	
		
		Table t = toolkit.createTable(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 100;
		t.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		_viewer = new TableViewer(t);
		_viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				
				execute(new SelectCommand<Void,MainPage,Role>(getTrace(),
						new PropertyAccessor<Void,Role>(null) {
							public Role getPropertyValue() {
								return (Role)((StructuredSelection)event.getSelection()).getFirstElement();
							}
							public void setPropertyValue(Role value) {
							}							
						},
						new PropertyAccessor<MainPage,Role>(MainPage.this) {
							public Role getPropertyValue() {
								return (getModel().getRole());
							}
							public void setPropertyValue(Role value) {
								getModel().setRole(value);
							}							
						}));

			}
		});
		
		_viewer.setContentProvider(new RoleContentProvider());
		_viewer.setLabelProvider(new RoleLabelProvider());
		_viewer.setInput(getTrace());
		
		Composite buttonComposite=toolkit.createComposite(client);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		buttonComposite.setLayoutData(gd);

		RowLayout rlayout=new RowLayout();
		rlayout.type = SWT.VERTICAL;
		buttonComposite.setLayout(rlayout);
		
		Button roleAddButton = toolkit.createButton(buttonComposite, Messages.getString("MainPage.add"), SWT.PUSH); //$NON-NLS-1$
		
		//gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		//roleAddButton.setLayoutData(gd);
		
		roleAddButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(SelectionEvent arg0) {
				execute(new AddToListCommand<Role>(getTrace(), getTrace().getRoles(),
						new Role().setName("<New>"),
						new Runnable() {
							public void run() {
								_viewer.refresh();
								
								// Comment out for now, as does not cause the detail fields
								// to be populated - might need to be done in a Command - but
								// then would effectively be inserting an additional command,
								// unless can 'attach' to primary command and then piggyback
								// undo/redo.
								//
								//_viewer.getTable().select(_viewer.getTable().getItemCount()-1);
							}
						}, new Runnable() {
							public void run() {
								_viewer.refresh();
							}
						}
				));
			}			
		});
		
		_roleRemoveButton = toolkit.createButton(buttonComposite, Messages.getString("MainPage.remove"), SWT.PUSH); //$NON-NLS-1$
		_roleRemoveButton.setEnabled(false);
		
		_roleRemoveButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(SelectionEvent arg0) {
				execute(new RemoveFromListCommand<Role>(getTrace(), getTrace().getRoles(), _role,
						new Runnable() {
							public void run() {
								_viewer.refresh();
							}
						}, new Runnable() {
							public void run() {
								_viewer.refresh();
								
								// TODO: Select undone removed entry - but only when can
								// also cause details to be focused on
							}
						}
				));
			}			
		});

		gd = new GridData(GridData.FILL_BOTH);
		client.setLayoutData(gd);
	}
	
	
	private void createDetailsSection(final ScrolledForm form, FormToolkit toolkit) {
		Composite client = toolkit.createComposite(form.getBody(), SWT.WRAP);
		
		RowLayout rlayout = new RowLayout();
		rlayout.type = SWT.VERTICAL;
		client.setLayout(rlayout);
		
		Composite simSelect = toolkit.createComposite(client, SWT.WRAP);
		
		GridLayout simLayout = new GridLayout();
		simLayout.numColumns = 2;

		simSelect.setLayout(simLayout);
		
		Label title=toolkit.createLabel(simSelect, "Details", SWT.BOLD);
		GridData titlegd=new GridData();
		titlegd.horizontalSpan = 2;
		title.setLayoutData(titlegd);
		
		Composite separator=toolkit.createCompositeSeparator(simSelect);
		
		GridData gd = new GridData(GridData.FILL);
		gd.heightHint = 2;
		gd.widthHint = 200;
		gd.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gd.horizontalSpan = 2;
		separator.setLayoutData(gd);	
		
		toolkit.createLabel(simSelect, "Name");
		
		_roleName=toolkit.createText(simSelect, "", SWT.BORDER);
		_roleName.setEnabled(false);
		
		GridData gd0 = new GridData(GridData.FILL_BOTH);
		gd0.widthHint = 300;
		_roleName.setLayoutData(gd0);
		
		_roleName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,Role,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(value);
						}							
					},
					new PropertyAccessor<Role,String>(_role) {
						public String getPropertyValue() {
							return getModel() == null ? null : getModel().getName();
						}
						public void setPropertyValue(String value) {
							if (getModel() != null) {
								getModel().setName(value);
							}
						}							
					}, new Runnable() {
						public void run() {
							_viewer.refresh();
						}
					}, new Runnable() {
						public void run() {
							_viewer.refresh();
						}
					}));
			}
		});

		toolkit.createLabel(simSelect, "Simulator");
		
		_simulatorTypeButton=new Combo(simSelect, SWT.DROP_DOWN|SWT.READ_ONLY);
		_simulatorTypeButton.setEnabled(false);

		_simulatorTypeButton.setItems(new String[]{"None", "Monitor"});
		_simulatorTypeButton.select(1);
		
		_simulatorTypeButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
			}
			public void widgetSelected(SelectionEvent event) {
				simulatorTypeChanged();
			}			
		});
		
		_monSim = toolkit.createComposite(client, SWT.WRAP);
		
		GridLayout simMonLayout = new GridLayout();
		simMonLayout.numColumns = 2;

		_monSim.setLayout(simMonLayout);
		_monSim.setVisible(false);
		
		toolkit.createLabel(_monSim, "Module");
		
		_monSimModuleName=toolkit.createText(_monSim, "", SWT.BORDER);
		
		GridData gd1 = new GridData(GridData.FILL_BOTH);
		gd1.widthHint = 300;
		_monSimModuleName.setLayoutData(gd1);
		
		_monSimModuleName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,Role,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(emptyStringIfNull(value));
						}							
					},
					new PropertyAccessor<Role,String>(_role) {
						public String getPropertyValue() {
							return (((MonitorRoleSimulator)getModel().getSimulator()).getModule());
						}
						public void setPropertyValue(String value) {
							((MonitorRoleSimulator)getModel().getSimulator()).setModule(value);								
						}							
					}, null, null));
			}
		});
		
		
		toolkit.createLabel(_monSim, "Protocol");
		
		_monSimProtocolName=toolkit.createText(_monSim, "", SWT.BORDER);
		
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		gd2.widthHint = 300;
		_monSimProtocolName.setLayoutData(gd2);
		
		_monSimProtocolName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,Role,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(emptyStringIfNull(value));
						}							
					},
					new PropertyAccessor<Role,String>(_role) {
						public String getPropertyValue() {
							return (((MonitorRoleSimulator)getModel().getSimulator()).getProtocol());
						}
						public void setPropertyValue(String value) {
							((MonitorRoleSimulator)getModel().getSimulator()).setProtocol(value);								
						}							
					}, null, null));
			}
		});
		
		
		toolkit.createLabel(_monSim, "Role");
		
		_monSimRoleName=toolkit.createText(_monSim, "", SWT.BORDER);
		
		GridData gd3 = new GridData(GridData.FILL_BOTH);
		gd3.widthHint = 300;
		_monSimRoleName.setLayoutData(gd3);
		
		_monSimRoleName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,Role,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(emptyStringIfNull(value));
						}							
					},
					new PropertyAccessor<Role,String>(_role) {
						public String getPropertyValue() {
							return (((MonitorRoleSimulator)getModel().getSimulator()).getRole());
						}
						public void setPropertyValue(String value) {
							((MonitorRoleSimulator)getModel().getSimulator()).setRole(value);								
						}							
					}, null, null));
			}
		});
		
		/*
		section.setText(title);
		section.setDescription(Messages.getString("MainPage.simulatorDesc")); //$NON-NLS-1$
		section.setClient(client);
		section.setExpanded(true);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
		*/
		
		GridData gd4 = new GridData(GridData.FILL_BOTH);
		//section.setLayoutData(gd);
		client.setLayoutData(gd4);
	}
	
	protected void roleUpdated() {
		_simulatorTypeButton.setEnabled(_role != null);
		_roleRemoveButton.setEnabled(_role != null);

		if (_role == null) {
			_monSim.setVisible(false);
			_roleName.setEnabled(false);
			_roleName.setText("");
			_simulatorTypeButton.select(0);
		} else {
			_roleName.setEnabled(true);
			if (_role.getSimulator() == null) {
				_simulatorTypeButton.select(0);
				_monSim.setVisible(false);
			} else if (_role.getSimulator() instanceof MonitorRoleSimulator) {
				_simulatorTypeButton.select(1);
				_monSim.setVisible(true);
			}
		}
		
		if (_viewer.getElementAt(_viewer.getTable().getSelectionIndex()) != _role) {
			boolean f_found=false;
			
			for (int i=0; _role != null && i < _viewer.getTable().getItemCount(); i++) {
				if (_viewer.getElementAt(i) == _role) {
					_viewer.getTable().select(i);
					f_found = true;
					break;
				}
			}
			
			if (!f_found) {
				_viewer.getTable().deselectAll();
			}
		}

		if (_role != null) {
			AbstractCommand init=new AbstractCommand("InitRoleAndSimulator", getTrace(), false, null, null) {
				public IStatus execute(IProgressMonitor arg0, IAdaptable arg1)
						throws ExecutionException {
					startProcessing();
					_roleName.setText(_role.getName());
					
					if (_role.getSimulator() instanceof MonitorRoleSimulator) {
						_monSimModuleName.setText(emptyStringIfNull(((MonitorRoleSimulator)_role.getSimulator()).getModule()));
						_monSimProtocolName.setText(emptyStringIfNull(((MonitorRoleSimulator)_role.getSimulator()).getProtocol()));
						_monSimRoleName.setText(emptyStringIfNull(((MonitorRoleSimulator)_role.getSimulator()).getRole()));
					}
					finishProcessing();
					return null;
				}
				public IStatus redo(IProgressMonitor arg0, IAdaptable arg1)
						throws ExecutionException {
					return null;
				}
				public IStatus undo(IProgressMonitor arg0, IAdaptable arg1)
						throws ExecutionException {
					return null;
				}
			};
			
			try {
				init.execute(null, null);
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected String emptyStringIfNull(String str) {
		return (str == null ? "" : str);
	}
	
	protected void simulatorTypeChanged() {
		AbstractCommand command=null;
		
		if (_simulatorTypeButton.getSelectionIndex() == 0) {			
			command = new SetPropertyCommand<Void,Role,RoleSimulator>(getTrace(),
				new PropertyAccessor<Void,RoleSimulator>(null) {
					public RoleSimulator getPropertyValue() {
						return null;
					}
					public void setPropertyValue(RoleSimulator value) {
						if (value == null) {
							_simulatorTypeButton.select(0);
						} else if (value instanceof MonitorRoleSimulator) {
							_simulatorTypeButton.select(1);
						}
						roleUpdated();
					}							
				},
				new PropertyAccessor<Role,RoleSimulator>(_role) {
					public RoleSimulator getPropertyValue() {
						return getModel().getSimulator();
					}
					public void setPropertyValue(RoleSimulator value) {
						getModel().setSimulator(value);
					}							
				}, null, null);

		} else if (_simulatorTypeButton.getSelectionIndex() == 1) {
			command = new SetPropertyCommand<Void,Role,RoleSimulator>(getTrace(),
				new PropertyAccessor<Void,RoleSimulator>(null) {
					public RoleSimulator getPropertyValue() {
						return new MonitorRoleSimulator();
					}
					public void setPropertyValue(RoleSimulator value) {
						if (value == null) {
							_simulatorTypeButton.select(0);
						} else if (value instanceof MonitorRoleSimulator) {
							_simulatorTypeButton.select(1);
						}
						roleUpdated();
					}							
				},
				new PropertyAccessor<Role,RoleSimulator>(_role) {
					public RoleSimulator getPropertyValue() {
						return getModel().getSimulator();
					}
					public void setPropertyValue(RoleSimulator value) {
						getModel().setSimulator(value);
					}							
				}, null, null);
		}
		
		if (command != null) {
			execute(command);
			
			roleUpdated();
		}
	}
	
	protected void execute(AbstractCommand operation) {
		if (operation.canSchedule()) {
			operation.addContext(getTraceEditor().UNDO_CONTEXT);
			
			try {
				OperationHistoryFactory.getOperationHistory().execute(operation, null, null);						
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param id
	 * @param title
	 */
	class RoleContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {			
			if (inputElement instanceof Trace) {
				return ((Trace)inputElement).getRoles().toArray();
			}
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	class RoleLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (obj instanceof Role) {
				return ((Role)obj).getName();
			}
			return obj.toString();
		}
		public Image getColumnImage(Object obj, int index) {
			/*
			if (obj instanceof TypeOne) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_ELEMENT);
			}
			if (obj instanceof TypeTwo) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_FILE);
			}
			*/
			return null;
		}
	}
}

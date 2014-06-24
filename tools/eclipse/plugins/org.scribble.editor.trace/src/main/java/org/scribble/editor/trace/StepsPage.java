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
import org.scribble.editor.trace.ext.MTPList;
import org.scribble.editor.trace.ext.MessageTransferParameter;
import org.scribble.editor.trace.osgi.TraceEditorActivator;
import org.scribble.monitor.Message;
import org.scribble.trace.model.MessageTransfer;
import org.scribble.trace.model.Role;
import org.scribble.trace.model.Step;
import org.scribble.trace.model.Trace;

/**
 * Page provide details about the steps.
 */
public class StepsPage extends FormPage {
	
	private Step _selected;
	
	private Text _mtOperator;
	private Combo _mtFromRole;
	private List _mtToRoles;
	private Button _mtpRemoveButton;
	private TableViewer _mtpViewer;
	private int _selectedMTParameter=-1;
	private Text _mtpType;
	private Text _mtpValue;

	private Composite _messageTransferConfig;
	private Composite _messageTransferParametersConfig;
	
	private Button _stepRemoveButton;
	
	private TableViewer _viewer;
	
	private MessageTransferParametersContentProvider _mtpContentProvider=new MessageTransferParametersContentProvider();
	
	/**
	 * @param id
	 * @param title
	 */
	public StepsPage(TraceEditor editor) {
		super(editor, "steps", Messages.getString("StepsPage.label")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * This method returns the trace.
	 * 
	 * @return The trace
	 */
	protected Trace getTrace() {
		return (getTraceEditor().getTrace());
	}
	
	protected TraceEditor getTraceEditor() {
		return ((TraceEditor)this.getEditor());
	}
	
	protected void setSelected(Step step) {
		boolean f_changed=(_selected != step);
		
		_selected = step;
		
		if (f_changed) {
			stepUpdated();
		}
	}
	
	protected Step getSelected() {
		return (_selected);
	}
	
	protected void setSelectedMTParameter(int pos) {
		boolean f_changed=(_selectedMTParameter != pos);
		
		_selectedMTParameter = pos;
		
		if (f_changed) {
			mtParameterUpdated();
		}
	}
	
	protected int getSelectedMTParameter() {
		return (_selectedMTParameter);
	}
	
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(Messages.getString("StepsPage.title")); //$NON-NLS-1$
		form.setBackgroundImage(TraceEditorActivator.getDefault().getImage(TraceEditorActivator.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout);
				
		createStepListSection(form, toolkit);
		createStepDetailsSection(form, toolkit);
	}
	
	private void createStepListSection(final ScrolledForm form, FormToolkit toolkit) {
		Composite client = toolkit.createComposite(form.getBody(), SWT.WRAP);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		client.setLayout(layout);
		
		Label title=toolkit.createLabel(client, "Steps", SWT.BOLD);
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
		gd.widthHint = 300;
		t.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		_viewer = new TableViewer(t);
		_viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				
				execute(new SelectCommand<Void,StepsPage,Step>(getTrace(),
						new PropertyAccessor<Void,Step>(null) {
							public Step getPropertyValue() {
								return (Step)((StructuredSelection)event.getSelection()).getFirstElement();
							}
							public void setPropertyValue(Step value) {
							}							
						},
						new PropertyAccessor<StepsPage,Step>(StepsPage.this) {
							public Step getPropertyValue() {
								return (getModel().getSelected());
							}
							public void setPropertyValue(Step value) {
								getModel().setSelected(value);
							}							
						}));

			}
		});
		
		_viewer.setContentProvider(new StepsContentProvider());
		_viewer.setLabelProvider(new StepsLabelProvider());
		_viewer.setInput(getTrace());
		
		Composite buttonComposite=toolkit.createComposite(client);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		buttonComposite.setLayoutData(gd);

		RowLayout rlayout=new RowLayout();
		rlayout.type = SWT.VERTICAL;
		buttonComposite.setLayout(rlayout);
		
		Button stepAddButton = toolkit.createButton(buttonComposite, Messages.getString("StepsPage.addMessageTransfer"), SWT.PUSH); //$NON-NLS-1$
		
		//gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		//roleAddButton.setLayoutData(gd);
		
		stepAddButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(SelectionEvent arg0) {
				execute(new AddToListCommand<Step>(getTrace(), getTrace().getSteps(),
						new MessageTransfer(),
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
						},
						new Runnable() {
							public void run() {
								_viewer.refresh();
							}
						}
				));
			}			
		});
		
		_stepRemoveButton = toolkit.createButton(buttonComposite, Messages.getString("StepsPage.remove"), SWT.PUSH); //$NON-NLS-1$
		_stepRemoveButton.setEnabled(false);
		
		_stepRemoveButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(SelectionEvent arg0) {
				execute(new RemoveFromListCommand<Step>(getTrace(), getTrace().getSteps(), getSelected(),
						new Runnable() {
							public void run() {
								_viewer.refresh();
							}
						},
						new Runnable() {
							public void run() {
								_viewer.refresh();
								// TODO: See about selecting re-added entry, but needs to focus
								// on detail fields
							}
						}
				));
			}			
		});

		gd = new GridData(GridData.FILL_BOTH);
		client.setLayoutData(gd);
	}
	
	
	private void createStepDetailsSection(final ScrolledForm form, FormToolkit toolkit) {
		Composite client = toolkit.createComposite(form.getBody(), SWT.WRAP);
		
		RowLayout rlayout = new RowLayout();
		rlayout.type = SWT.VERTICAL;
		client.setLayout(rlayout);
		
		Composite stepDetails = toolkit.createComposite(client, SWT.WRAP);
		
		GridLayout stepDetailsLayout = new GridLayout();
		stepDetailsLayout.numColumns = 2;

		stepDetails.setLayout(stepDetailsLayout);
		
		Label title=toolkit.createLabel(stepDetails, "Details", SWT.BOLD);
		GridData titlegd=new GridData();
		titlegd.horizontalSpan = 2;
		title.setLayoutData(titlegd);
		
		Composite separator=toolkit.createCompositeSeparator(stepDetails);
		
		GridData gd = new GridData(GridData.FILL);
		gd.heightHint = 2;
		gd.widthHint = 200;
		gd.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gd.horizontalSpan = 2;
		separator.setLayoutData(gd);	
		
		_messageTransferConfig = toolkit.createComposite(stepDetails, SWT.WRAP);
		
		GridLayout mtConfigLayout = new GridLayout();
		mtConfigLayout.numColumns = 4;

		_messageTransferConfig.setLayout(mtConfigLayout);
		_messageTransferConfig.setVisible(false);
		
		toolkit.createLabel(_messageTransferConfig, "'From' Role");
		
		_mtFromRole=new Combo(_messageTransferConfig, SWT.DROP_DOWN|SWT.READ_ONLY);

		GridData gd5 = new GridData(GridData.FILL_BOTH);
		gd5.widthHint = 200;
		gd5.horizontalSpan = 3;
		_mtFromRole.setLayoutData(gd5);
		
		_mtFromRole.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(final SelectionEvent event) {
				if (_mtFromRole.getSelectionIndex() > 0) {
					execute(new SetPropertyCommand<Combo,MessageTransfer,Role>(getTrace(),
							new PropertyAccessor<Combo,Role>((Combo)event.getSource()) {
								public Role getPropertyValue() {
									Role ret=null;
									if (_mtFromRole.getSelectionIndex() > 0) {
										String name=_mtFromRole.getItem(_mtFromRole.getSelectionIndex());
										
										ret = (Role)_mtFromRole.getData(name);
									}
									return ret;
								}
								public void setPropertyValue(Role value) {
									if (value != null) {
										int index=_mtFromRole.indexOf(value.getName());
										
										if (index != -1) {
											_mtFromRole.select(index);
										} else {
											System.err.println("ROLE '"+value.getName()+"; INDEX NOT FOUND");
										}
									} else {
										_mtFromRole.select(0);
									}
								}							
							},
							new PropertyAccessor<MessageTransfer,Role>((MessageTransfer)getSelected()) {
								public Role getPropertyValue() {
									if (getModel().getFromRole() != null) {
										return new Role().setName(getModel().getFromRole());
									}
									return null;
								}
								public void setPropertyValue(Role value) {
									getModel().setFromRole(value==null?null : value.getName());
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
			}
		});
		
		toolkit.createLabel(_messageTransferConfig, "'To' Roles");
		
		_mtToRoles=new List(_messageTransferConfig, SWT.CHECK|SWT.MULTI|SWT.V_SCROLL|SWT.BORDER);

		GridData gd6 = new GridData(GridData.FILL_BOTH);
		gd6.heightHint = 100;
		gd6.widthHint = 200;
		gd6.horizontalSpan = 3;
		_mtToRoles.setLayoutData(gd6);
		
		_mtToRoles.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(final SelectionEvent event) {
				execute(new SetPropertyCommand<List,MessageTransfer,java.util.List<Role>>(getTrace(),
						new PropertyAccessor<List,java.util.List<Role>>((List)event.getSource()) {
							public java.util.List<Role> getPropertyValue() {
								java.util.List<Role> ret=new java.util.ArrayList<Role>();
								
								int[] selected=_mtToRoles.getSelectionIndices();
								
								for (int i=0; i < selected.length; i++) {
									String name=_mtToRoles.getItem(selected[i]);
									ret.add((Role)_mtToRoles.getData(name));
								}
								return ret;
							}
							public void setPropertyValue(java.util.List<Role> value) {
								_mtToRoles.deselectAll();

								if (value != null) {
									for (int i=0; i < value.size(); i++) {
										int index=_mtToRoles.indexOf(value.get(i).getName());
										
										if (index != -1) {
											_mtToRoles.select(index);
										} else {
											System.err.println("ROLE '"+value.get(i).getName()+"; INDEX NOT FOUND");
										}
									}
								}
							}							
						},
						new PropertyAccessor<MessageTransfer,java.util.List<Role>>((MessageTransfer)getSelected()) {
							public java.util.List<Role> getPropertyValue() {
								java.util.List<Role> ret=new java.util.ArrayList<Role>();
								
								for (int i=0; i < getModel().getToRoles().size(); i++) {
									ret.add(new Role().setName(getModel().getToRoles().get(i)));
								}
								
								return ret;
							}
							public void setPropertyValue(java.util.List<Role> value) {
								getModel().getToRoles().clear();
								for (int i=0; i < value.size(); i++) {
									getModel().getToRoles().add(value.get(i).getName());
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
		
		toolkit.createLabel(_messageTransferConfig, "Operator");
		
		_mtOperator=toolkit.createText(_messageTransferConfig, "", SWT.BORDER);
		
		GridData gd1 = new GridData(GridData.FILL_BOTH);
		gd1.widthHint = 200;
		gd1.horizontalSpan = 3;
		_mtOperator.setLayoutData(gd1);
		
		_mtOperator.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,Step,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(emptyStringIfNull(value));
						}							
					},
					new PropertyAccessor<Step,String>(getSelected()) {
						public String getPropertyValue() {
							String ret=null;
							
							if (((MessageTransfer)getModel()).getMessage() != null) {
								ret = ((MessageTransfer)getModel()).getMessage().getOperator();
							}
							
							return (emptyStringIfNull(ret));
						}
						public void setPropertyValue(String value) {
							if (((MessageTransfer)getModel()).getMessage() == null) {
								((MessageTransfer)getModel()).setMessage(new Message());
							}
							((MessageTransfer)getModel()).getMessage().setOperator(value);								
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
		
		Composite buttonComposite=toolkit.createComposite(_messageTransferConfig);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		buttonComposite.setLayoutData(gd);

		RowLayout rlayout2=new RowLayout();
		rlayout2.type = SWT.VERTICAL;
		buttonComposite.setLayout(rlayout2);
		
		Button stepAddButton = toolkit.createButton(buttonComposite, Messages.getString("StepsPage.addMessageTransferParameter"), SWT.PUSH); //$NON-NLS-1$
		
		//gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		//roleAddButton.setLayoutData(gd);
		
		stepAddButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(SelectionEvent arg0) {
				execute(new AddToListCommand<MessageTransferParameter>(getTrace(), new MTPList((MessageTransfer)getSelected()),
						new MessageTransferParameter("<type>",""),
						new Runnable() {
							public void run() {
								_mtpContentProvider.update();
								
								_mtpViewer.refresh();
								
								// Comment out for now, as does not cause the detail fields
								// to be populated - might need to be done in a Command - but
								// then would effectively be inserting an additional command,
								// unless can 'attach' to primary command and then piggyback
								// undo/redo.
								//
								//_viewer.getTable().select(_viewer.getTable().getItemCount()-1);
							}
						},
						new Runnable() {
							public void run() {
								_mtpContentProvider.update();
								
								_mtpViewer.refresh();
							}
						}
				));
			}			
		});
		
		_mtpRemoveButton = toolkit.createButton(buttonComposite, Messages.getString("StepsPage.remove"), SWT.PUSH); //$NON-NLS-1$
		_mtpRemoveButton.setEnabled(false);
		
		_mtpRemoveButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			public void widgetSelected(SelectionEvent arg0) {
				execute(new RemoveFromListCommand<MessageTransferParameter>(getTrace(),
							new MTPList((MessageTransfer)getSelected()),getSelectedMTParameter(),
						new Runnable() {
							public void run() {
								_mtpContentProvider.update();
								
								_mtpViewer.refresh();

								setSelectedMTParameter(-1);
							}
						},
						new Runnable() {
							public void run() {
								_mtpContentProvider.update();
								
								_mtpViewer.refresh();
								// TODO: See about selecting re-added entry, but needs to focus
								// on detail fields
								
								setSelectedMTParameter(-1);
							}
						}
				));
			}			
		});
		
		Table t = toolkit.createTable(_messageTransferConfig, SWT.NULL|SWT.BORDER);
		GridData gd7 = new GridData(GridData.FILL_BOTH);
		gd7.heightHint = 100;
		gd7.widthHint = 200;
		gd7.horizontalSpan = 3;
		t.setLayoutData(gd7);
		toolkit.paintBordersFor(client);
		
		_mtpViewer = new TableViewer(t);
		_mtpViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				
				execute(new SelectCommand<Void,StepsPage,Integer>(getTrace(),
						new PropertyAccessor<Void,Integer>(null) {
							public Integer getPropertyValue() {
								return _mtpViewer.getTable().getSelectionIndex();
							}
							public void setPropertyValue(Integer value) {
							}							
						},
						new PropertyAccessor<StepsPage,Integer>(StepsPage.this) {
							public Integer getPropertyValue() {
								return (getModel().getSelectedMTParameter());
							}
							public void setPropertyValue(Integer value) {
								getModel().setSelectedMTParameter(value);
							}							
						}));
			}
		});
		
		_mtpViewer.setContentProvider(_mtpContentProvider);
		_mtpViewer.setLabelProvider(new MessageTransferParametersLabelProvider());
		
		
		// Parameter details composite
		_messageTransferParametersConfig = toolkit.createComposite(_messageTransferConfig, SWT.WRAP);
		
		GridLayout mtParameterLayout = new GridLayout();
		mtParameterLayout.numColumns = 4;

		_messageTransferParametersConfig.setLayout(mtParameterLayout);
		_messageTransferParametersConfig.setVisible(false);
		
		GridData gd9 = new GridData(GridData.FILL_BOTH);
		gd9.widthHint = 200;
		gd9.horizontalSpan = 4;
		_messageTransferParametersConfig.setLayoutData(gd9);
		
		toolkit.createLabel(_messageTransferParametersConfig, "Type");
		
		_mtpType=toolkit.createText(_messageTransferParametersConfig, "", SWT.BORDER);
		
		GridData gd8 = new GridData(GridData.FILL_BOTH);
		gd8.widthHint = 200;
		gd8.horizontalSpan = 3;
		_mtpType.setLayoutData(gd8);
		
		_mtpType.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,MessageTransferParameter,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(emptyStringIfNull(value));
						}							
					},
					new PropertyAccessor<MessageTransferParameter,String>(
							new MessageTransferParameter((MessageTransfer)getSelected(), getSelectedMTParameter())) {
						public String getPropertyValue() {
							return (emptyStringIfNull(getModel().getType()));
						}
						public void setPropertyValue(String value) {
							getModel().setType(value);								
						}							
					}, new Runnable() {
						public void run() {
							_viewer.refresh();
							_mtpViewer.refresh();
						}
					}, new Runnable() {
						public void run() {
							_viewer.refresh();
							_mtpViewer.refresh();
						}
					}));
			}
		});

		toolkit.createLabel(_messageTransferParametersConfig, "Value");
		
		_mtpValue=toolkit.createText(_messageTransferParametersConfig, "", SWT.BORDER|SWT.MULTI);
		
		GridData gd11 = new GridData(GridData.FILL_BOTH);
		gd11.widthHint = 200;
		gd11.heightHint = 100;		
		gd11.horizontalSpan = 3;
		_mtpValue.setLayoutData(gd11);
	
		_mtpValue.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent event) {
				execute(new SetPropertyCommand<Text,MessageTransferParameter,String>(getTrace(),
					new PropertyAccessor<Text,String>((Text)event.getSource()) {
						public String getPropertyValue() {
							return getModel().getText();
						}
						public void setPropertyValue(String value) {
							getModel().setText(emptyStringIfNull(value));
						}							
					},
					new PropertyAccessor<MessageTransferParameter,String>(
							new MessageTransferParameter((MessageTransfer)getSelected(), getSelectedMTParameter())) {
						public String getPropertyValue() {
							return (emptyStringIfNull(getModel().getValue()));
						}
						public void setPropertyValue(String value) {
							getModel().setValue(value);								
						}							
					}, new Runnable() {
						public void run() {
							_viewer.refresh();
							_mtpViewer.refresh();
						}
					}, new Runnable() {
						public void run() {
							_viewer.refresh();
							_mtpViewer.refresh();
						}
					}));
			}
		});
		
		GridData gd4 = new GridData(GridData.FILL_BOTH);
		//section.setLayoutData(gd);
		client.setLayoutData(gd4);
	}
	
	protected void stepUpdated() {
		_stepRemoveButton.setEnabled(_selected != null);

		if (_selected == null) {
			_messageTransferConfig.setVisible(false);
		} else if (_selected instanceof MessageTransfer) {
			_messageTransferConfig.setVisible(true);
		}
		
		// Reset message transfer parameter selected
		setSelectedMTParameter(-1);
		
		if (_viewer.getElementAt(_viewer.getTable().getSelectionIndex()) != _selected) {
			boolean f_found=false;
			
			for (int i=0; _selected != null && i < _viewer.getTable().getItemCount(); i++) {
				if (_viewer.getElementAt(i) == _selected) {
					_viewer.getTable().select(i);
					f_found = true;
					break;
				}
			}
			
			if (!f_found) {
				_viewer.getTable().deselectAll();
			}
		}

		if (_selected != null) {
			AbstractCommand init=new AbstractCommand("InitStepSelection", getTrace(), false, null, null) {
				public IStatus execute(IProgressMonitor arg0, IAdaptable arg1)
						throws ExecutionException {
					startProcessing();
					if (_selected instanceof MessageTransfer) {
						MessageTransfer mt=(MessageTransfer)_selected;
						
						if (mt.getMessage() != null) {
							_mtOperator.setText(emptyStringIfNull(mt.getMessage().getOperator()));
						} else {
							_mtOperator.setText("");
						}
						
						// Initialize the list of roles
						_mtFromRole.removeAll();
						_mtToRoles.removeAll();
						
						_mtFromRole.add("");
						
						int select=-1;
						
						for (int i=0; i < getTrace().getRoles().size(); i++) {
							Role r=getTrace().getRoles().get(i);
							
							_mtFromRole.add(r.getName());
							_mtFromRole.setData(r.getName(), r);
							
							_mtToRoles.add(r.getName());
							_mtToRoles.setData(r.getName(), r);
							
							if (mt.getFromRole() != null
									&& mt.getFromRole().equals(r.getName())) {
								select = i+1;
							}
							
							if (mt.getToRoles().contains(r.getName())) {
								_mtToRoles.select(i);
							}
						}
						
						if (select != -1) {
							_mtFromRole.select(select);
						} else {
							_mtFromRole.select(0);
						}
						
						_mtpViewer.setInput(mt);
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
	
	protected void mtParameterUpdated() {
		_mtpRemoveButton.setEnabled(_selectedMTParameter != -1);

		if (_selectedMTParameter == -1) {
			_messageTransferParametersConfig.setVisible(false);
		} else {
			_messageTransferParametersConfig.setVisible(true);
		}
		
		if (_selected != null) {
			AbstractCommand init=new AbstractCommand("InitParamSelection", getTrace(), false, null, null) {
				public IStatus execute(IProgressMonitor arg0, IAdaptable arg1)
						throws ExecutionException {
					startProcessing();
					if (_selectedMTParameter != -1 && _selected instanceof MessageTransfer) {						
						MessageTransfer mt=(MessageTransfer)_selected;
						
						if (mt.getMessage() != null) {							
							_mtpType.setText(mt.getMessage().getTypes().get(_selectedMTParameter));
							
							if (mt.getMessage().getValues().size() > _selectedMTParameter) {
								_mtpValue.setText((String)mt.getMessage().getValues().get(_selectedMTParameter));
							} else {
								_mtpValue.setText("");
							}
						}						
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
	
	/*
	protected void simulatorTypeChanged() {
		AbstractCommand command=null;
		
		if (_stepTypeButton.getSelectionIndex() == 0) {			
			command = new SetPropertyCommand<Void,Role,RoleSimulator>(getTrace(),
				new PropertyAccessor<Void,RoleSimulator>(null) {
					public RoleSimulator getPropertyValue() {
						return null;
					}
					public void setPropertyValue(RoleSimulator value) {
						if (value == null) {
							_stepTypeButton.select(0);
						} else if (value instanceof MonitorRoleSimulator) {
							_stepTypeButton.select(1);
						}
						stepUpdated();
					}							
				},
				new PropertyAccessor<Role,RoleSimulator>(_role) {
					public RoleSimulator getPropertyValue() {
						return getModel().getSimulator();
					}
					public void setPropertyValue(RoleSimulator value) {
						getModel().setSimulator(value);
					}							
				}, null);

		} else if (_stepTypeButton.getSelectionIndex() == 1) {
			command = new SetPropertyCommand<Void,Role,RoleSimulator>(getTrace(),
				new PropertyAccessor<Void,RoleSimulator>(null) {
					public RoleSimulator getPropertyValue() {
						return new MonitorRoleSimulator();
					}
					public void setPropertyValue(RoleSimulator value) {
						if (value == null) {
							_stepTypeButton.select(0);
						} else if (value instanceof MonitorRoleSimulator) {
							_stepTypeButton.select(1);
						}
						stepUpdated();
					}							
				},
				new PropertyAccessor<Role,RoleSimulator>(_role) {
					public RoleSimulator getPropertyValue() {
						return getModel().getSimulator();
					}
					public void setPropertyValue(RoleSimulator value) {
						getModel().setSimulator(value);
					}							
				}, null);
		}
		
		if (command != null) {
			execute(command);
			
			stepUpdated();
		}
	}
	*/
	
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
	class StepsContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {			
			if (inputElement instanceof Trace) {
				return ((Trace)inputElement).getSteps().toArray();
			}
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	class StepsLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (obj instanceof Step) {
				
				if (obj instanceof MessageTransfer) {
					StringBuffer buf=new StringBuffer();
					MessageTransfer mt=(MessageTransfer)obj;
					
					if (mt.getMessage() != null) {
						if (mt.getMessage().getOperator() != null) {
							buf.append(mt.getMessage().getOperator());
						}
						buf.append("(");
						for (int i=0; i < mt.getMessage().getTypes().size(); i++) {
							if (i > 0) {
								buf.append(",");
							}
							String type=mt.getMessage().getTypes().get(i);
							
							int pos=type.indexOf('}');
							if (type.indexOf('{') != -1 && pos != -1 && type.length() != pos+1) {
								type = type.substring(pos+1);
							}
							
							buf.append(type);
						}
						buf.append(")");
					} else {
						buf.append("()");
					}
					
					buf.append(" from ");
					
					if (mt.getFromRole() != null) {
						buf.append(mt.getFromRole());
					} else {
						buf.append("<role>");
					}
					
					buf.append(" to [");
					
					if (mt.getToRoles().size() > 0) {
						for (int i=0; i < mt.getToRoles().size(); i++) {
							if (i > 0) {
								buf.append(",");
							}
							buf.append(mt.getToRoles().get(i));
						}
					} else {
						buf.append("<role>");
					}
					
					buf.append("]");
					
					return (buf.toString());
				}
				
				return ((Step)obj).toString();
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

	class MessageTransferParametersContentProvider implements IStructuredContentProvider {
		
		private MessageTransfer _current;
		private Object[] _elements=new Object[0];
		
		public Object[] getElements(Object inputElement) {			
			if (inputElement instanceof MessageTransfer) {
				if (_current != inputElement) {
					if (((MessageTransfer)inputElement).getMessage() == null) {
						// Create empty message
						((MessageTransfer)inputElement).setMessage(new Message());
					}
				
					_elements = new Object[((MessageTransfer)inputElement).getMessage().getTypes().size()];
					for (int i=0; i < ((MessageTransfer)inputElement).getMessage().getTypes().size(); i++) {
						_elements[i] = new MessageTransferParameter((MessageTransfer)inputElement, i);
					}
					//_elements = ((MessageTransfer)inputElement).getMessage().getTypes().toArray();
					_current = (MessageTransfer)inputElement;
				}
			} else {
				_current = null;
				_elements = new Object[0];
			}
			return _elements;
		}
		
		public void update() {
			_current = null;
		}
		
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	class MessageTransferParametersLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
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

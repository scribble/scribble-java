/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.editor.tools.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.scribble.logging.ConsoleIssueLogger;
import org.scribble.resources.InputStreamResource;
import org.scribble.resources.Resource;
import org.scribble.resources.ResourceLocator;
import org.scribble.context.DefaultModuleContext;
import org.scribble.editor.tools.osgi.Activator;
import org.scribble.model.Module;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;
import org.scribble.projection.ProtocolProjector;

/**
* This class implements the action to project a global model.
*/
public class ProjectionAction implements IObjectActionDelegate {

	private ISelection _selection;
	private IWorkbenchPart _targetPart;

	/**
	 * Default constructor.
	 */
	public ProjectionAction() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void run(IAction action) {
		if (_selection instanceof StructuredSelection) {
			StructuredSelection sel=(StructuredSelection)_selection;
			final IResource res=(IResource)sel.getFirstElement();

			if (res instanceof IFile) {
		        ConsoleIssueLogger logger=
		                new ConsoleIssueLogger();
		        
		        try {
		            InputStreamResource isr = new InputStreamResource(res.getLocation().toPortableString(),
		            					((IFile)res).getContents());
		            
		            // Create a locator based on the Eclipse project root
		            ResourceLocator locator=new ResourceLocator() {

						public Resource getResource(String name) {
							String filename=name.replace('.', java.io.File.separatorChar)+".scr";
							
				            IFile file=res.getProject().getFile(filename);
				            
				            if (file != null) {
				            	try {
				            		return (new InputStreamResource(filename, file.getContents()));
				            	} catch (Exception e) {
				            		e.printStackTrace();
				            	}
				            }
				            
							return null;
						}
		            };
		            
		            ProtocolParser pp=new ProtocolParser();
		            
		            ProtocolModuleLoader loader=new ProtocolModuleLoader(pp, locator, logger);
		            
		            Module module=pp.parse(isr, loader, logger);
		            
		            if (module != null) {
		            	ProtocolProjector projector=new ProtocolProjector();
		            	
		            	DefaultModuleContext context=new DefaultModuleContext(isr, module, loader);
		            	
		            	java.util.Set<Module> projections=projector.project(context, module, logger);
		            	
		            	for (Module lm : projections) {
							String filename=lm.getName().replace('.',
									java.io.File.separatorChar)+".scr";
							
				            IFile file=res.getProject().getFile(filename);
				            
				            if (file.exists() == false) {
				            	file.create(null, true,
										new org.eclipse.core.runtime.NullProgressMonitor());
							}
				            
				            java.io.InputStream is=new java.io.ByteArrayInputStream(lm.toString().getBytes());
				            
							file.setContents(is, true, false,
									new org.eclipse.core.runtime.NullProgressMonitor());
							
							is.close();
							
							file.refreshLocal(IResource.DEPTH_ONE,
									new org.eclipse.core.runtime.NullProgressMonitor());
		            	}
		            }
		        } catch (Throwable e) {
		            Activator.logError("Failed to record issue on resource '"+res+"'", e);
		        }
			}
		}
	}
        
	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
		action.setEnabled(true);
                
		/*
		if (_selection instanceof StructuredSelection) {
			StructuredSelection sel=(StructuredSelection)_selection;
            IResource res=(IResource)sel.getFirstElement();
                        
            if (res instanceof IFile) {
            	IFile file=(IFile)res;
                                
            	// Check not a local scribble protocol
            	if (file.getName().indexOf('@') == -1) {
            		action.setEnabled(true);
            	}
            }
		}
		*/
	}

	/**
	 * {@inheritDoc}
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		_targetPart = targetPart;
	}
        
	/**
	 * This method is used to report a warning.
	 *
	 * @param mesg The warning message
	 */
	public void warn(String mesg) {
		MessageBox mbox=new MessageBox(_targetPart.getSite().getShell(),
									SWT.ICON_WARNING|SWT.OK);
		mbox.setMessage(mesg);
		mbox.open();
	}
}
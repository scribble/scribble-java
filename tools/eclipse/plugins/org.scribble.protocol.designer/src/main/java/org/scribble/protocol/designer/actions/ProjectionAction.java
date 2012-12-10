/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.designer.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.scribble.common.logging.CachedJournal;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.common.resource.FileContent;
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.designer.DesignerServices;
import org.scribble.protocol.designer.osgi.Activator;
import org.scribble.protocol.export.ProtocolExporter;
import org.scribble.protocol.export.text.TextProtocolExporter;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.Role;

/**
 * This class implements the projection action.
 *
 */
public class ProjectionAction implements IObjectActionDelegate {

    private ISelection _selection=null;
    private IWorkbenchPart _targetPart=null;

    /**
     * {@inheritDoc}
     */
    public void run(IAction arg0) {
        if (_selection instanceof StructuredSelection) {
            StructuredSelection sel=(StructuredSelection)_selection;
            
            IResource res=(IResource)sel.getFirstElement();
            
            // Obtain the protocol from the resource
            if (res instanceof IFile) {

                ProtocolModel model=null;
                
                try {
                    CachedJournal journal=new CachedJournal();
                    
                    FileContent content=new FileContent(((IFile)res).getRawLocation().toFile());
                    
                    model = DesignerServices.getParserManager().parse(null, content, journal);
            
                    if (model == null || journal.hasErrors()) {
                        error("Cannot project '"+((IFile)res).getName()+"' due to errors", null);
                    } else if (model.isLocated()) {
                        error("Cannot project '"+((IFile)res).getName()+"' as not a global model", null);
                    } else {
                        project(model, (IFile)res);
                    }
                } catch (Exception e) {
                    error("Failed to parse file '"+((IFile)res).getName()+"'", e);
                }
                
            }
        }
    }
    
    /**
     * This method reports an error.
     * 
     * @param mesg The message
     * @param exc The exception
     */
    protected void error(String mesg, Throwable exc) {
        Activator.logError(mesg, exc);

        MessageBox mbox=new MessageBox(_targetPart.getSite().getShell(),
                SWT.ICON_ERROR | SWT.OK);
        
        if (mesg == null) {
            mesg = "Null pointer exception has occurred";
        }

        mbox.setMessage(mesg);
        mbox.open();

    }
    
    /**
     * This method projects the protocol model to the supplied file.
     * 
     * @param pm The protocol model
     * @param file The file
     */
    protected void project(ProtocolModel pm, IFile file) {
        java.util.List<Role> roles=pm.getProtocol().getRoles();
        
        for (Role role : roles) {
            CachedJournal journal=new CachedJournal();
            
            ProtocolModel projection=DesignerServices.getProtocolProjector().project(
                    new DefaultProtocolContext(DesignerServices.getParserManager(),
                    new DefaultResourceLocator(file.getFullPath().toFile().getParentFile())),
                    pm, role, journal);
            
            if (projection != null || journal.hasErrors()) {
                // Get text exporter
                ProtocolExporter exporter=new TextProtocolExporter();
                
                String filename=file.getName().substring(0,
                        file.getName().length()-file.getFileExtension().length()-1)
                        +"@"+role.getName()+"."+file.getFileExtension();
                
                try {
                    IFile locatedFile=file.getParent().getFile(new Path(filename));
                    
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    
                    exporter.export(projection, journal, baos);
                    
                    baos.close();
                    
                    ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
                    
                    if (!locatedFile.exists()) {
                        locatedFile.create(bais, true, null);
                    } else {
                        
                        // Check that the user wishes to overwrite the
                        // existing file
                        MessageBox box=new MessageBox(_targetPart.getSite().getShell(),
                                SWT.YES | SWT.NO);
                        
                        box.setMessage("Overwrite existing Choreography Description '"
                                +filename+"'?");
                        box.setText("WARNING");
        
                        if (box.open() == SWT.YES) {                        
                            locatedFile.setContents(bais, true, false, null);
                        }
                    }
                    
                    bais.close();
                    
                    if (journal.hasErrors()) {
                        error("Failed to export text of '"+file.getName()+"' and role '"+role+"'", null);
                    }
                } catch (Exception e) {
                    error("Failed to export text of '"+file.getName()+"' and role '"+role+"'", e);
                }
            } else {
                error("Failed to project '"+file.getName()+"' to role '"+role+"'", null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void selectionChanged(IAction arg0, ISelection selection) {
        _selection = selection;
    }

    /**
     * {@inheritDoc}
     */
    public void setActivePart(IAction arg0, IWorkbenchPart targetPart) {
        _targetPart = targetPart;
    }

}

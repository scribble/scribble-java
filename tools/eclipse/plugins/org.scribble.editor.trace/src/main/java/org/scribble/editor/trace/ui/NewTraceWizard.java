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
package org.scribble.editor.trace.ui;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.scribble.trace.model.Trace;
import org.scribble.trace.util.TraceUtil;

/**
 * This class provides the wizard responsible for creating
 * new Trace definitions.
 */
public class NewTraceWizard extends Wizard implements INewWizard {
    
    private static final String TRACE_FILE_EXTENSION = "trace";
	private IWorkbench _workbench=null;
    private IStructuredSelection _selection=null;
    private ScribbleNewFileCreationPage _newFileCreationPage=null;

    /**
     * This method initializes the wizard.
     * 
     * @param workbench The workbench
     * @param selection The selected resource
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        _workbench = workbench;
        _selection = selection;
        setWindowTitle("New Trace Wizard");
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean performFinish() {
        try {
            // Remember the file.
            //
            final IFile modelFile = getModelFile();

            // Do the work within an operation.
            //
            WorkspaceModifyOperation operation =
                new WorkspaceModifyOperation() {
                    protected void execute(IProgressMonitor progressMonitor) {
                        try {                            
                            // Identify the model reference from the resource
                            org.eclipse.core.runtime.IPath path=modelFile.getFullPath();
                            org.eclipse.core.runtime.IPath fqnPath=path.removeFirstSegments(1);
                            
                            String[] segments=fqnPath.segments();
                            
                            String filename=segments[segments.length-1];
                            int ind=filename.lastIndexOf('.');
                            
                            if (ind != -1) {
                            	filename = filename.substring(0, ind);
                            }
                            
                            Trace trace=new Trace();
                            trace.setName(filename);
                            
                            byte[] b=TraceUtil.serializeTrace(trace);
                            
                            java.io.ByteArrayInputStream bis=new java.io.ByteArrayInputStream(b);
                            
                            modelFile.create(bis, true, progressMonitor);
                            
                            bis.close();
                            
                        } catch (Exception e) {
                            org.scribble.editor.trace.osgi.TraceEditorActivator.logError(e.getMessage(), e);
                        } finally {
                            progressMonitor.done();
                        }
                    }
                };

            getContainer().run(false, false, operation);

            // Select the new file resource in the current view.
            //
            IWorkbenchWindow workbenchWindow =
                _workbench.getActiveWorkbenchWindow();
            IWorkbenchPage page = workbenchWindow.getActivePage();
            final IWorkbenchPart activePart = page.getActivePart();
            if (activePart instanceof ISetSelectionTarget) {
                final ISelection targetSelection = new StructuredSelection(modelFile);
                getShell().getDisplay().asyncExec(
                        new Runnable() {
                         public void run() {
                             ((ISetSelectionTarget)activePart).selectReveal(targetSelection);
                         }
                     });
            }

            // Open an editor on the new file.
            //
            try {
                org.eclipse.ui.IEditorDescriptor ed=
                    _workbench.getEditorRegistry().getDefaultEditor(modelFile.getFullPath().toString());
                
                if (ed != null) {
                    page.openEditor(new FileEditorInput(modelFile),
                                        ed.getId());
                }
            } catch (PartInitException exception) {
                MessageDialog.openError(workbenchWindow.getShell(),
                        "Open Error", exception.getMessage());
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            org.scribble.editor.trace.osgi.TraceEditorActivator.logError(e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * This method returns the initial description.
     * 
     * @return The initial description
     */
    protected String getInitialDescription() {
        String ret="";
        
        // TODO: Add default description
        
        return (ret);
    }

    /**
     * Get the file from the page.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @return The model file
     */
    public IFile getModelFile() {
        return _newFileCreationPage.getModelFile();
    }

    /**
     * The framework calls this to create the contents of the wizard.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void addPages() {
        
        _newFileCreationPage = new ScribbleNewFileCreationPage("Whatever", _selection);
        _newFileCreationPage.setTitle("Protocol");
        _newFileCreationPage.setDescription("Create a new Protocol");

        addPage(_newFileCreationPage);
        
        initFileCreationPage();
    }
    
    /**
     * {@inheritDoc}
     */
    protected void initFileCreationPage() {
        String defaultModelBaseFilename = "My";
        
        String defaultModelFilenameExtension = TRACE_FILE_EXTENSION;
        String modelFilename = defaultModelBaseFilename + "." + defaultModelFilenameExtension;

        // Create a page, set the title, and the initial model file name.
        //
        _newFileCreationPage.setFileName(modelFilename);

        // Try and get the resource selection to determine a current directory for the file dialog.
        //
        if (_selection != null && !_selection.isEmpty()) {
            // Get the resource...
            //
            Object selectedElement = _selection.iterator().next();
            if (selectedElement instanceof IResource) {
                // Get the resource parent, if its a file.
                //
                IResource selectedResource = (IResource)selectedElement;
                if (selectedResource.getType() == IResource.FILE) {
                    selectedResource = selectedResource.getParent();
                }

                // This gives us a directory...
                //
                if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
                    // Set this for the container.
                    //
                    _newFileCreationPage.setContainerFullPath(selectedResource.getFullPath());

                    // Make up a unique new name here.
                    //
                    for (int i = 1; ((IContainer)selectedResource).findMember(modelFilename) != null; ++i) {
                        modelFilename = defaultModelBaseFilename + i + "." + defaultModelFilenameExtension;
                    }
                    _newFileCreationPage.setFileName(modelFilename);
                }
            }
        }
    }

    /**
     * This is the one page of the wizard.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public class ScribbleNewFileCreationPage extends WizardNewFileCreationPage {
        /**
         * Remember the model file.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        private IFile _modelFile;
    
        /**
         * Constructor.
         * 
         * @param pageId The page id
         * @param selection The selection
         */
        public ScribbleNewFileCreationPage(String pageId, IStructuredSelection selection) {
            super(pageId, selection);
        }
    
        /**
         * The framework calls this to see if the file is correct.
         * 
         * @return Whether the page is valid
         */
        protected boolean validatePage() {
            if (super.validatePage()) {
                String requiredExt = TRACE_FILE_EXTENSION;
                
                String enteredExt = new Path(getFileName()).getFileExtension();
                if (enteredExt == null || !enteredExt.equals(requiredExt)) {
                    setErrorMessage("The filename must end in: "+requiredExt);
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    
        /**
         * Store the dialog field settings upon completion.
         * 
         * @return Whether finished
         */
        public boolean performFinish() {
            _modelFile = getModelFile();
            return true;
        }
    
        /**
         * Return the model file.
         * 
         * @return The model file
         */
        public IFile getModelFile() {
            return _modelFile == null
                    ? ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()))
                           : _modelFile;
        }
    }

}

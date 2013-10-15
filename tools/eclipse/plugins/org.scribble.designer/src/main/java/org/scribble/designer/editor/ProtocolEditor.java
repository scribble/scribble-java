/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.designer.editor;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.TextEditorAction;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.scribble.designer.keywords.KeyWordProvider;
import org.scribble.designer.keywords.ProtocolKeyWordProvider;

/**
 * Scribble text editor.
 */
public class ProtocolEditor extends TextEditor {
    
    private static final Logger LOG=Logger.getLogger(ProtocolEditor.class.getName());

    private ScribbleSourceViewerConfiguration _sourceViewerConfiguration;
    
    /**
     * Define folding region action.
     *
     */
    private class DefineFoldingRegionAction extends TextEditorAction {

        /**
         * Constructor.
         * 
         * @param bundle Resource bundle
         * @param prefix Prefix
         * @param editor Text editor
         */
        public DefineFoldingRegionAction(ResourceBundle bundle, String prefix, ITextEditor editor) {
            super(bundle, prefix, editor);
        }
        
        /**
         * Get the annotation model.
         * 
         * @param editor Editor
         * @return The annotation model
         */
        private IAnnotationModel getAnnotationModel(ITextEditor editor) {
            return (IAnnotationModel) editor.getAdapter(ProjectionAnnotationModel.class);
        }
        
        /**
         * {@inheritDoc}
         */
        public void run() {
            ITextEditor editor= getTextEditor();
            ISelection selection= editor.getSelectionProvider().getSelection();
            if (selection instanceof ITextSelection) {
                ITextSelection textSelection= (ITextSelection) selection;
                if (!textSelection.isEmpty()) {
                    IAnnotationModel model= getAnnotationModel(editor);
                    if (model != null) {
                        
                        int start= textSelection.getStartLine();
                        int end= textSelection.getEndLine();
                        
                        try {
                            IDocument document= editor.getDocumentProvider().getDocument(editor.getEditorInput());
                            int offset= document.getLineOffset(start);
                            int endOffset= document.getLineOffset(end + 1);
                            Position position= new Position(offset, endOffset - offset);
                            model.addAnnotation(new ProjectionAnnotation(), position);
                        } catch (BadLocationException x) {
                            LOG.log(Level.SEVERE, "Error", x);
                        }
                    }
                }
            }
        }
    }

    private ScribbleContentOutlinePage _fOutlinePage;
    private ProjectionSupport _fProjectionSupport;

    /**
     * Default constructor.
     */
    public ProtocolEditor() {
        super();
    }
    
    /** The <code>JavaEditor</code> implementation of this 
     * <code>AbstractTextEditor</code> method extend the 
     * actions to add those specific to the receiver.
     */
    protected void createActions() {
        super.createActions();
        
        IAction a= new TextOperationAction(ProtocolEditorMessages.getResourceBundle(), "ContentAssistProposal.", this, ISourceViewer.CONTENTASSIST_PROPOSALS); //$NON-NLS-1$
        a.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
        setAction("ContentAssistProposal", a); //$NON-NLS-1$
        
        a= new TextOperationAction(ProtocolEditorMessages.getResourceBundle(), "ContentAssistTip.", this, ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION);  //$NON-NLS-1$
        a.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);
        setAction("ContentAssistTip", a); //$NON-NLS-1$
        
        a= new DefineFoldingRegionAction(ProtocolEditorMessages.getResourceBundle(), "DefineFoldingRegion.", this); //$NON-NLS-1$
        setAction("DefineFoldingRegion", a); //$NON-NLS-1$
    }
    
    /** The <code>JavaEditor</code> implementation of this 
     * <code>AbstractTextEditor</code> method performs any extra 
     * disposal actions required by the java editor.
     */
    public void dispose() {
        if (_fOutlinePage != null) {
            _fOutlinePage.setInput(null);           
        }
         super.dispose();
    }
    
    /** The <code>JavaEditor</code> implementation of this 
     * <code>AbstractTextEditor</code> method performs any extra 
     * revert behavior required by the java editor.
     */
    public void doRevertToSaved() {
        super.doRevertToSaved();
        if (_fOutlinePage != null) {
            _fOutlinePage.update();
        }
    }
    
    /** The <code>JavaEditor</code> implementation of this 
     * <code>AbstractTextEditor</code> method performs any extra 
     * save behavior required by the java editor.
     * 
     * @param monitor the progress monitor
     */
    public void doSave(IProgressMonitor monitor) {
        super.doSave(monitor);
        if (_fOutlinePage != null) {
            _fOutlinePage.update();
        }
    }
    
    /** The <code>JavaEditor</code> implementation of this 
     * <code>AbstractTextEditor</code> method performs any extra 
     * save as behavior required by the java editor.
     */
    public void doSaveAs() {
        super.doSaveAs();
        if (_fOutlinePage != null) {
            _fOutlinePage.update();
        }
    }
    
    /** The <code>JavaEditor</code> implementation of this 
     * <code>AbstractTextEditor</code> method performs sets the 
     * input of the outline page after AbstractTextEditor has set input.
     * 
     * @param input the editor input
     * @throws CoreException in case the input can not be set
     */ 
    public void doSetInput(IEditorInput input) throws CoreException {
        
        super.doSetInput(input);
        if (_fOutlinePage != null) {
            _fOutlinePage.setInput(input);
        }
        
        if (_sourceViewerConfiguration != null) {
            String name=input.getName();
            int index=name.indexOf('.');
            
            if (index != -1) {
                name = name.substring(index+1);
                
                // TODO: Need to select keyword provider based
                // on language notation
                
                // Find keyword provider
                KeyWordProvider provider=
                            new ProtocolKeyWordProvider();
                
                _sourceViewerConfiguration.setKeyWordProvider(provider);
                
                updateTitle();
                
                
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    protected void updateTitle() throws CoreException {
        IEditorInput input=getEditorInput();
        
        if (input instanceof IFileEditorInput) {
            
            // Change the icon to represent the status of the
            // description
            String imageName="scribble.png";
            
            IMarker[] markers=
                ((IFileEditorInput)input).getFile().findMarkers(
                    org.scribble.designer.logger.ScribbleMarker.SCRIBBLE_PROBLEM,
                            true, IResource.DEPTH_INFINITE);
            boolean error=false;
            boolean warning=false;
            
            for (int i=0; markers != null && i < markers.length; i++) {
                int severity=markers[i].getAttribute(IMarker.SEVERITY, 0);
                
                if (severity == IMarker.SEVERITY_ERROR) {
                    error = true;
                    markers = null;
                } else if (severity == IMarker.SEVERITY_WARNING) {
                    warning = true;
                }
            }
            
            if (error) {
                imageName = "scribble_error.png";
            } else if (warning) {
                imageName = "scribble_warning.png";
            }
            
            org.eclipse.swt.graphics.Image image=
                ScribbleImages.getImage(imageName);
            
            if (image != null) {
                setTitleImage(image);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    protected void editorContextMenuAboutToShow(IMenuManager menu) {
        super.editorContextMenuAboutToShow(menu);
        addAction(menu, "ContentAssistProposal"); //$NON-NLS-1$
        addAction(menu, "ContentAssistTip"); //$NON-NLS-1$
        addAction(menu, "DefineFoldingRegion");  //$NON-NLS-1$
    }
    
    /** The <code>JavaEditor</code> implementation of this 
     * <code>AbstractTextEditor</code> method performs gets
     * the java content outline page if request is for a an 
     * outline page.
     * 
     * @param required the required type
     * @return an adapter for the required type or <code>null</code>
     */ 
    public Object getAdapter(@SuppressWarnings("rawtypes") Class required) {
        if (IContentOutlinePage.class.equals(required)) {
            if (_fOutlinePage == null) {
                _fOutlinePage= new ScribbleContentOutlinePage(getDocumentProvider(), this);
                if (getEditorInput() != null) {
                    _fOutlinePage.setInput(getEditorInput());
                }
            }
            return _fOutlinePage;
        }
        
        if (_fProjectionSupport != null) {
            Object adapter= _fProjectionSupport.getAdapter(getSourceViewer(), required);
            if (adapter != null) {
                return adapter;
            }
        }
        
        return super.getAdapter(required);
    }
        
    /**
     * {@inheritDoc}
     */
    protected void initializeEditor() {
        super.initializeEditor();
        
        _sourceViewerConfiguration = new ScribbleSourceViewerConfiguration();
        
        setSourceViewerConfiguration(_sourceViewerConfiguration);
    }
    
    /**
     * {@inheritDoc}
     */
    protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
        
        fAnnotationAccess= createAnnotationAccess();
        fOverviewRuler= createOverviewRuler(getSharedColors());
        
        ISourceViewer viewer= new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);
        // ensure decoration support has been created and configured.
        getSourceViewerDecorationSupport(viewer);
        
        return viewer;
    }
    
    /**
     * {@inheritDoc}
     */
    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        ProjectionViewer viewer= (ProjectionViewer) getSourceViewer();
        _fProjectionSupport= new ProjectionSupport(viewer, getAnnotationAccess(), getSharedColors());
        _fProjectionSupport.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.error"); //$NON-NLS-1$
        _fProjectionSupport.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.warning"); //$NON-NLS-1$
        _fProjectionSupport.install();
        viewer.doOperation(ProjectionViewer.TOGGLE);
    }
    
    /**
     * {@inheritDoc}
     */
    protected void adjustHighlightRange(int offset, int length) {
        ISourceViewer viewer= getSourceViewer();
        if (viewer instanceof ITextViewerExtension5) {
            ITextViewerExtension5 extension= (ITextViewerExtension5) viewer;
            extension.exposeModelRange(new Region(offset, length));
        }
    }
    
    static {
        
        // Listen for changes to resource associated with this editor
        IResourceChangeListener rcl=
            new IResourceChangeListener() {
    
            public void resourceChanged(IResourceChangeEvent evt) {
        
                try {
                    evt.getDelta().accept(new IResourceDeltaVisitor() {
                        
                        public boolean visit(IResourceDelta delta) {
                            boolean ret=true;
                            final IResource res = delta.getResource();
                            
                            if (res instanceof IFile) {
                                final org.eclipse.ui.IWorkbenchWindow[] windows=
                                    org.eclipse.ui.PlatformUI.getWorkbench().getWorkbenchWindows();
    
                                if (org.eclipse.swt.widgets.Display.getCurrent() != null) {
                                    org.eclipse.swt.widgets.Display.getCurrent().syncExec(new Runnable() {
                                        public void run() {
    
                                            for (int i=0; i < windows.length; i++) {
                                                org.eclipse.ui.IWorkbenchPage[] pages=
                                                        windows[i].getPages();
            
                                                for (int j=0; j < pages.length; j++) {
                                                    org.eclipse.ui.IEditorReference[] refs=
                                                        pages[j].getEditorReferences();
                                                    
                                                    for (int k=0; k < refs.length; k++) {
                                                        
                                                        try {
                                                            if (refs[k].getEditorInput() instanceof IFileEditorInput
                                                                    && ((IFileEditorInput)refs[k].getEditorInput()).getFile().equals(res)) {
                                                                
                                                                Object editor=refs[k].getEditor(true);
                
                                                                if (editor instanceof ProtocolEditor) {                                                                        
                                                                    ((ProtocolEditor)editor).updateTitle();
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                            
                            return (ret);
                        }
                     });
                } catch (Exception e) {
                    LOG.log(Level.SEVERE,
                        "Failed to process resource change event", e);
                }
            }
        };

        ResourcesPlugin.getWorkspace().addResourceChangeListener(rcl,
                IResourceChangeEvent.POST_CHANGE);    
    }    
}

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
package org.scribble.protocol.designer.editor;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.scribble.protocol.designer.editor.outliner.DefaultModelOutliner;
import org.scribble.protocol.model.ModelProperties;

/**
 * A content outline page which always represents the content of the
 * connected editor in 10 segments.
 */
public class ScribbleContentOutlinePage extends ContentOutlinePage {

    private Object _fInput;
    //private IDocumentProvider _fDocumentProvider;
    private ITextEditor _fTextEditor;
    
    private org.scribble.protocol.model.Module _model=null;
    private org.scribble.protocol.designer.editor.outliner.ModelOutliner _outliner=null;

    /**
     * Label provider.
     *
     */
    protected class LabelProvider implements ILabelProvider {

        /**
         * {@inheritDoc}
         */
        public Image getImage(Object element) {
            Image ret=null;

            if (_outliner != null) {
                ret = _outliner.getImage(element);
            }

            return (ret);
        }

        /**
         * {@inheritDoc}
         */
        public String getText(Object element) {
            String ret=null;

            if (_outliner != null) {
                ret = _outliner.getLabel(element);
            }

            return (ret);
        }

        /**
         * {@inheritDoc}
         */
        public void addListener(ILabelProviderListener listener) {
        }

        /**
         * {@inheritDoc}
         */
        public void dispose() {
        }

        /**
         * {@inheritDoc}
         */
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public void removeListener(ILabelProviderListener listener) {
        }
        
    }

    /**
     * Divides the editor's document into ten segments and provides elements for them.
     */
    protected class ContentProvider implements ITreeContentProvider {

        /**
         * {@inheritDoc}
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            
        }

        /**
         * {@inheritDoc}
         */
        public void dispose() {
        }

        /**
         * {@inheritDoc}
         */
        public boolean isDeleted(Object element) {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public Object[] getElements(Object element) {
            Object[] ret=null;
            
            if (_outliner != null) {
                java.util.List<Object> list=_outliner.getChildren(element);
                
                if (list != null) {
                    ret = list.toArray();
                }
            } else {
                ret = new Object[0];
            }

            return (ret);
        }

        /**
         * {@inheritDoc}
         */
        public boolean hasChildren(Object element) {
            boolean ret=false;
            
            if (_outliner != null) {
                ret = _outliner.hasChildren(element);
            }
            
            return (ret);
        }

        /**
         * {@inheritDoc}
         */
        public Object getParent(Object element) {
            return (_model);
        }

        /**
         * {@inheritDoc}
         */
        public Object[] getChildren(Object element) {
            return (getElements(element));
        }
    }

    /**
     * Creates a content outline page using the given provider and the given editor.
     * 
     * @param provider the document provider
     * @param editor the editor
     */
    public ScribbleContentOutlinePage(IDocumentProvider provider, ITextEditor editor) {
        super();
        //_fDocumentProvider= provider;
        _fTextEditor= editor;
    }
    
    /**
     * {@inheritDoc}
     */
    public void createControl(Composite parent) {

        super.createControl(parent);

        TreeViewer viewer= getTreeViewer();
        viewer.setContentProvider(new ContentProvider());
        viewer.setLabelProvider(new LabelProvider());
        viewer.addSelectionChangedListener(this);

        if (_model != null) {
            viewer.setInput(_model);
        } else if (_fInput != null) {
            viewer.setInput(_fInput);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void selectionChanged(SelectionChangedEvent event) {

        super.selectionChanged(event);

        ISelection selection= event.getSelection();
        if (selection.isEmpty()) {
            _fTextEditor.resetHighlightRange();
        } else {
            
            if (((IStructuredSelection) selection).getFirstElement()
                    instanceof org.scribble.protocol.model.ModelObject) {
                org.scribble.protocol.model.ModelObject mobj=
                        (org.scribble.protocol.model.ModelObject)
                            ((IStructuredSelection) selection).getFirstElement();
                
                if (mobj.getProperties().containsKey(ModelProperties.START_LOCATION)
                        && mobj.getProperties().containsKey(ModelProperties.END_LOCATION)) {
                    int start=(Integer)mobj.getProperties().get(ModelProperties.START_LOCATION);
                    int length=(Integer)mobj.getProperties().get(ModelProperties.END_LOCATION);

                    try {
                        _fTextEditor.setHighlightRange(start, length, true);
                    } catch (IllegalArgumentException x) {
                        _fTextEditor.resetHighlightRange();
                    }
                } else {
                    _fTextEditor.resetHighlightRange();                    
                }
            } else {
                _fTextEditor.resetHighlightRange();                
            }
        }
    }
    
    /**
     * Sets the input of the outline page.
     * 
     * @param input the input of this outline page
     */
    public void setInput(Object input) {
        _fInput= input;
        update();
    }
    
    /**
     * Updates the outline page.
     */
    public void update() {
        TreeViewer viewer= getTreeViewer();

        _outliner = null;
        _model = null;
        
        if (_fInput instanceof org.eclipse.ui.IFileEditorInput) {
            //org.eclipse.ui.IFileEditorInput fi=
            //        (org.eclipse.ui.IFileEditorInput)fInput;
            
            // TODO: Need to parse model - will need to determine
            // which notation, and select the appropriate parser
            
            _outliner = new DefaultModelOutliner();            
        }
        
        if (viewer != null) {
            Control control= viewer.getControl();
            if (control != null && !control.isDisposed()) {
                
                control.setRedraw(false);

                if (_model != null) {
                    viewer.setInput(_model);
                } else {
                    
                    viewer.setInput(_fInput);
                }
                
                viewer.expandAll();
                control.setRedraw(true);
            }
        }
    }
}

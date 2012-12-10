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


import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

/**
 * A toolbar action which toggles the presentation model of the
 * connected text editor. The editor shows either the highlight range
 * only or always the whole document.
 */
public class PresentationAction extends TextEditorAction {

    /**
     * Constructs and updates the action.
     */
    public PresentationAction() {
        super(ProtocolEditorMessages.getResourceBundle(), "TogglePresentation.", null); //$NON-NLS-1$
        update();
    }
    
    /**
     * {@inheritDoc}
     */
    public void run() {

        ITextEditor editor= getTextEditor();

        editor.resetHighlightRange();
        boolean show= editor.showsHighlightRangeOnly();
        setChecked(!show);
        editor.showHighlightRangeOnly(!show);
    }
    
    /**
     * {@inheritDoc}
     */
    public void update() {
        setChecked(getTextEditor() != null && getTextEditor().showsHighlightRangeOnly());
        setEnabled(true);
    }
}

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


import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;
import org.eclipse.ui.texteditor.TextEditorAction;

/**
 * Contributes interesting Scribble actions to the desktop's Edit 
 * menu and the toolbar.
 */
public class ScribbleActionContributor extends TextEditorActionContributor {

    private RetargetTextEditorAction _fContentAssistProposal;
    private RetargetTextEditorAction _fContentAssistTip;
    private TextEditorAction _fTogglePresentation;

    /**
     * Default constructor.
     */
    public ScribbleActionContributor() {
        super();
        _fContentAssistProposal= new RetargetTextEditorAction(ProtocolEditorMessages.getResourceBundle(), "ContentAssistProposal."); //$NON-NLS-1$
        _fContentAssistProposal.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS); 
        _fContentAssistTip= new RetargetTextEditorAction(ProtocolEditorMessages.getResourceBundle(), "ContentAssistTip."); //$NON-NLS-1$
        _fContentAssistTip.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);
        _fTogglePresentation= new PresentationAction();
    }
    
    /**
     * {@inheritDoc}
     */
    public void init(IActionBars bars) {
        super.init(bars);
        
        IMenuManager menuManager= bars.getMenuManager();
        IMenuManager editMenu= menuManager.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
        if (editMenu != null) {
            editMenu.add(new Separator());
            editMenu.add(_fContentAssistProposal);
            editMenu.add(_fContentAssistTip);
        }    
        
        IToolBarManager toolBarManager= bars.getToolBarManager();
        if (toolBarManager != null) {
            toolBarManager.add(new Separator());
            toolBarManager.add(_fTogglePresentation);
        }
    }
    
    /**
     * Set active editor.
     * 
     * @param part The editor part
     */
    private void doSetActiveEditor(IEditorPart part) {
        super.setActiveEditor(part);

        ITextEditor editor= null;
        if (part instanceof ITextEditor) {
            editor= (ITextEditor) part;
        }

        _fContentAssistProposal.setAction(getAction(editor, "ContentAssistProposal")); //$NON-NLS-1$
        _fContentAssistTip.setAction(getAction(editor, "ContentAssistTip")); //$NON-NLS-1$

        _fTogglePresentation.setEditor(editor);
        _fTogglePresentation.update();
    }
    
    /**
     * {@inheritDoc}
     */
    public void setActiveEditor(IEditorPart part) {
        super.setActiveEditor(part);
        doSetActiveEditor(part);
    }
    
    /**
     * {@inheritDoc}
     */
    public void dispose() {
        doSetActiveEditor(null);
        super.dispose();
    }
}

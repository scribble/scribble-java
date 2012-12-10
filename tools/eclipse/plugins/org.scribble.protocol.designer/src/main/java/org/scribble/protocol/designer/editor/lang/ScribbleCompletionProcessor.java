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
package org.scribble.protocol.designer.editor.lang;


import java.text.MessageFormat;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.scribble.protocol.designer.editor.ProtocolEditorMessages;

/**
 * Scribble completion processor.
 */
public class ScribbleCompletionProcessor implements IContentAssistProcessor {
    
    private org.scribble.protocol.designer.keywords.KeyWordProvider _provider=null;

    /**
     * Simple content assist tip closer. The tip is valid in a range
     * of 5 characters around its popup location.
     */
    protected static class Validator implements IContextInformationValidator, IContextInformationPresenter {

        private int _fInstallOffset;

        /**
         * {@inheritDoc}
         */
        public boolean isContextInformationValid(int offset) {
            return Math.abs(_fInstallOffset - offset) < 5;
        }

        /**
         * {@inheritDoc}
         */
        public void install(IContextInformation info, ITextViewer viewer, int offset) {
            _fInstallOffset= offset;
        }
        
        /**
         * {@inheritDoc}
         */
        public boolean updatePresentation(int documentPosition, TextPresentation presentation) {
            return false;
        }
    }

    protected final static String[] FGPROPOSALS={
        "abstract", "boolean", "break", "byte", "case", "catch", "char",
        "class", "continue", "default", "do", "double", "else", "extends",
        "false", "final", "finally", "float", "for", "if", "implements",
        "import", "instanceof", "int", "interface", "long", "native", "new",
        "null", "package", "private", "protected", "public", "return",
        "short", "static", "super", "switch", "synchronized", "this",
        "throw", "throws", "transient", "true", "try", "void", "volatile", "while"
    };

    private IContextInformationValidator _fValidator= new Validator();

    /**
     * Constructor.
     * 
     * @param provider Keyword Provider
     */
    public ScribbleCompletionProcessor(org.scribble.protocol.designer.keywords.KeyWordProvider provider) {
        _provider = provider;
    }
    
    /**
     * {@inheritDoc}
     */
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int documentOffset) {
        String[] proposals=FGPROPOSALS;
        
        if (_provider != null) {
            proposals = _provider.getKeyWords();
        }
        
        ICompletionProposal[] result= new ICompletionProposal[proposals.length];
        for (int i= 0; i < proposals.length; i++) {
            IContextInformation info= new ContextInformation(proposals[i],
                    MessageFormat.format(ProtocolEditorMessages.getString("CompletionProcessor.Proposal.ContextInfo.pattern"),
                            new Object[] {
                        proposals[i]
            }));
            result[i]= new CompletionProposal(proposals[i], documentOffset, 0, proposals[i].length(), null, proposals[i], info, MessageFormat.format(ProtocolEditorMessages.getString("CompletionProcessor.Proposal.hoverinfo.pattern"),
                            new Object[] {
                proposals[i]
            }));
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    public IContextInformation[] computeContextInformation(ITextViewer viewer, int documentOffset) {
        IContextInformation[] result= new IContextInformation[5];
        for (int i= 0; i < result.length; i++) {
            result[i]= new ContextInformation(
                MessageFormat.format(ProtocolEditorMessages.getString(
                        "CompletionProcessor.ContextInfo.display.pattern"),
                        new Object[] {
                    new Integer(i), new Integer(documentOffset)
                }),
                MessageFormat.format(ProtocolEditorMessages.getString(
                        "CompletionProcessor.ContextInfo.value.pattern"), new Object[] {
                    new Integer(i), new Integer(documentOffset - 5),
                    new Integer(documentOffset + 5)
                }));
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    public char[] getCompletionProposalAutoActivationCharacters() {
        return new char[] {
                '.', '('
        };
    }
    
    /**
     * {@inheritDoc}
     */
    public char[] getContextInformationAutoActivationCharacters() {
        return new char[] {
                '#'
        };
    }
    
    /**
     * {@inheritDoc}
     */
    public IContextInformationValidator getContextInformationValidator() {
        return _fValidator;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getErrorMessage() {
        return null;
    }
}

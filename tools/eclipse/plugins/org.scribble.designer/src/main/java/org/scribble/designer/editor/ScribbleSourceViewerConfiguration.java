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


import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;
import org.scribble.designer.editor.lang.ScribbleAutoIndentStrategy;
import org.scribble.designer.editor.lang.ScribbleCompletionProcessor;
import org.scribble.designer.editor.lang.ScribbleDoubleClickSelector;
import org.scribble.designer.editor.util.ScribbleColorProvider;
import org.scribble.designer.osgi.Activator;

/**
 * Viewer configuration for a <code>SourceViewer</code> which
 * shows Scribble code.
 */
public class ScribbleSourceViewerConfiguration extends SourceViewerConfiguration {
    
    private org.scribble.designer.keywords.KeyWordProvider _provider=null;
    
    
    /**
     * Single token scanner.
     */
    static class SingleTokenScanner extends BufferedRuleBasedScanner {
        /**
         * Constructor.
         * 
         * @param attribute The attribute
         */
        public SingleTokenScanner(TextAttribute attribute) {
            setDefaultReturnToken(new Token(attribute));
        }
    }     

    /**
     * Default constructor.
     */
    public ScribbleSourceViewerConfiguration() {
    }
    
    /**
     * {@inheritDoc}
     */
    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        return new ScribbleAnnotationHover();
    }
        
    /**
     * {@inheritDoc}
     */
    public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
        IAutoEditStrategy strategy= (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType) ? new ScribbleAutoIndentStrategy() : new DefaultIndentLineAutoEditStrategy());
        return new IAutoEditStrategy[] {
                strategy
        };
    }
    
    /**
     * {@inheritDoc}
     */
    public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
        return Activator.SCRIBBLE_PARTITIONING;
    }
    
    /**
     * {@inheritDoc}
     */
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return new String[] {
                IDocument.DEFAULT_CONTENT_TYPE,
                ScribblePartitionScanner.JAVA_DOC,
                ScribblePartitionScanner.SCRIBBLE_MULTILINE_COMMENT
        };
    }
    
    /**
     * {@inheritDoc}
     */
    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
        ContentAssistant assistant= new ContentAssistant();
        assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
        assistant.setContentAssistProcessor(new ScribbleCompletionProcessor(_provider), IDocument.DEFAULT_CONTENT_TYPE);

        assistant.enableAutoActivation(true);
        assistant.setAutoActivationDelay(500);
        assistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
        assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
        assistant.setContextInformationPopupBackground(Activator.getDefault().getScribbleColorProvider().getColor(new RGB(150, 150, 0)));

        return assistant;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getDefaultPrefix(ISourceViewer sourceViewer, String contentType) {
        return (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType) ? "//" : null); //$NON-NLS-1$
    }
    
    /**
     * {@inheritDoc}
     */
    public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
        return new ScribbleDoubleClickSelector();
    }
    
    /**
     * {@inheritDoc}
     */
    public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
        return new String[] {
                "\t",
                "    "
        };
    }
    
    /**
     * {@inheritDoc}
     */
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

        ScribbleColorProvider provider= Activator.getDefault().getScribbleColorProvider();
        PresentationReconciler reconciler= new PresentationReconciler();
        reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
        
        DefaultDamagerRepairer dr= new DefaultDamagerRepairer(Activator.getDefault().getScribbleCodeScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        
        dr= new DefaultDamagerRepairer(new SingleTokenScanner(new TextAttribute(provider.getColor(ScribbleColorProvider.MULTI_LINE_COMMENT))));
        reconciler.setDamager(dr, ScribblePartitionScanner.SCRIBBLE_MULTILINE_COMMENT);
        reconciler.setRepairer(dr, ScribblePartitionScanner.SCRIBBLE_MULTILINE_COMMENT);

        return reconciler;
    }
    
    /**
     * {@inheritDoc}
     */
    public int getTabWidth(ISourceViewer sourceViewer) {
        return 4;
    }
    
    /**
     * {@inheritDoc}
     */
    public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
        return new ScribbleTextHover();
    }
    
    /**
     * This method sets the provider.
     * 
     *  @param provider The keyword provider
     */
    public void setKeyWordProvider(org.scribble.designer.keywords.KeyWordProvider provider) {
        _provider = provider;
    }
}

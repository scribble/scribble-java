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
package org.scribble.designer.editor.lang;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.scribble.designer.editor.util.ScribbleColorProvider;
import org.scribble.designer.editor.util.ScribbleWhitespaceDetector;
import org.scribble.designer.editor.util.ScribbleWordDetector;
import org.scribble.designer.keywords.ProtocolKeyWordProvider;

/**
 * A Java code scanner.
 */
public class ScribbleCodeScanner extends RuleBasedScanner {

    private static String[] fgTypes= {
        "void", "boolean", "char", "byte", 
        "short", "int", "long", "float", "double"
    };

    private static String[] fgConstants= {
        "false", "null", "true"
    };

    /**
     * Creates a Java code scanner with the given color provider.
     * 
     * @param provider the color provider
     */
    public ScribbleCodeScanner(ScribbleColorProvider provider) {

        IToken keyword= new Token(new TextAttribute(provider.getColor(ScribbleColorProvider.KEYWORD)));
        IToken type= new Token(new TextAttribute(provider.getColor(ScribbleColorProvider.TYPE)));
        IToken string= new Token(new TextAttribute(provider.getColor(ScribbleColorProvider.STRING)));
        IToken comment= new Token(new TextAttribute(provider.getColor(ScribbleColorProvider.SINGLE_LINE_COMMENT)));
        IToken other= new Token(new TextAttribute(provider.getColor(ScribbleColorProvider.DEFAULT)));

        List<Object> rules= new ArrayList<Object>();

        // Add rule for single line comments.
        rules.add(new EndOfLineRule("//", comment)); //$NON-NLS-1$

        // Add rule for strings and character constants.
        rules.add(new SingleLineRule("\"", "\"", string, '\\')); //$NON-NLS-2$ //$NON-NLS-1$
        rules.add(new SingleLineRule("'", "'", string, '\\')); //$NON-NLS-2$ //$NON-NLS-1$

        // Add generic whitespace rule.
        rules.add(new WhitespaceRule(new ScribbleWhitespaceDetector()));

        // Add word rule for keywords, types, and constants.
        WordRule wordRule= new WordRule(new ScribbleWordDetector(), other);
        
        // For now, use all keyword providers to work out keywords for the
        // editor. Validation rules will need to determine if they are the
        // correct keywords for the particular notation being edited. Later
        // it may be possible to customise the editor based on the specific
        // notation being edited.
        ProtocolKeyWordProvider pkp=new ProtocolKeyWordProvider();
        
        for (int i=0; i < pkp.getKeyWords().length; i++) {
            wordRule.addWord(pkp.getKeyWords()[i], keyword);                
        }
            
        for (int i= 0; i < fgTypes.length; i++) {
            wordRule.addWord(fgTypes[i], type);
        }
        for (int i= 0; i < fgConstants.length; i++) {
            wordRule.addWord(fgConstants[i], type);
        }
        rules.add(wordRule);

        IRule[] result= new IRule[rules.size()];
        rules.toArray(result);
        setRules(result);
    }
}

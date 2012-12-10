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


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

/**
 * This scanner recognizes the JavaDoc comments and Java multi line comments.
 */
public class ScribblePartitionScanner extends RuleBasedPartitionScanner {

    /**
     * Scribble multiline comment.
     */
    public final static String SCRIBBLE_MULTILINE_COMMENT= "__java_multiline_comment"; //$NON-NLS-1$
    
    /**
     * Javadoc.
     */
    public final static String JAVA_DOC= "__java_javadoc"; //$NON-NLS-1$
    
    /**
     * Java partition types.
     */
    public final static String[] JAVA_PARTITION_TYPES= new String[] {
        SCRIBBLE_MULTILINE_COMMENT, JAVA_DOC
    };

    /**
     * Detector for empty comments.
     */
    static class EmptyCommentDetector implements IWordDetector {

        /**
         * {@inheritDoc}
         */
        public boolean isWordStart(char c) {
            return (c == '/');
        }

        /**
         * {@inheritDoc}
         */
        public boolean isWordPart(char c) {
            return (c == '*' || c == '/');
        }
    }
    
    /**
     * 
     */
    static class WordPredicateRule extends WordRule implements IPredicateRule {
        
        private IToken _fSuccessToken;
        
        /**
         * Constructor.
         * 
         * @param successToken The token
         */
        public WordPredicateRule(IToken successToken) {
            super(new EmptyCommentDetector());
            _fSuccessToken= successToken;
            addWord("/**/", _fSuccessToken); //$NON-NLS-1$
        }
        
        /**
         * {@inheritDoc}
         */
        public IToken evaluate(ICharacterScanner scanner, boolean resume) {
            return super.evaluate(scanner);
        }

        /**
         * {@inheritDoc}
         */
        public IToken getSuccessToken() {
            return _fSuccessToken;
        }
    }

    /**
     * Creates the partitioner and sets up the appropriate rules.
     */
    public ScribblePartitionScanner() {
        super();

        IToken javaDoc= new Token(JAVA_DOC);
        IToken comment= new Token(SCRIBBLE_MULTILINE_COMMENT);

        List<Object> rules= new ArrayList<Object>();

        // Add rule for single line comments.
        rules.add(new EndOfLineRule("//", Token.UNDEFINED)); //$NON-NLS-1$

        // Add rule for strings and character constants.
        rules.add(new SingleLineRule("\"", "\"", Token.UNDEFINED, '\\')); //$NON-NLS-2$ //$NON-NLS-1$
        rules.add(new SingleLineRule("'", "'", Token.UNDEFINED, '\\')); //$NON-NLS-2$ //$NON-NLS-1$

        // Add special case word rule.
        rules.add(new WordPredicateRule(comment));

        // Add rules for multi-line comments and javadoc.
        rules.add(new MultiLineRule("/**", "*/", javaDoc, (char) 0, true)); //$NON-NLS-1$ //$NON-NLS-2$
        rules.add(new MultiLineRule("/*", "*/", comment, (char) 0, true)); //$NON-NLS-1$ //$NON-NLS-2$

        IPredicateRule[] result= new IPredicateRule[rules.size()];
        rules.toArray(result);
        setPredicateRules(result);
    }
}

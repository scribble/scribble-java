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


import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;

/**
 * Double click strategy aware of Java identifier syntax rules.
 */
public class ScribbleDoubleClickSelector implements ITextDoubleClickStrategy {

    private static final Logger LOG=Logger.getLogger(ScribbleDoubleClickSelector.class.getName());
    
    private ITextViewer _fText;
    private int _fPos;
    private int _fStartPos;
    private int _fEndPos;

    private static char[] fgBrackets= {
        '{', '}', '(', ')', '[', ']', '"', '"'
    };

    /**
     * Constructor.
     */
    public ScribbleDoubleClickSelector() {
        super();
    }
    
    /**
     * {@inheritDoc}
     */
    public void doubleClicked(ITextViewer text) {

        _fPos= text.getSelectedRange().x;

        if (_fPos < 0) {
            return;
        }

        _fText= text;

        if (!selectBracketBlock()) {
            selectWord();
        }
    }
    
    /**
     * Match the brackets at the current selection. Return <code>true</code> if successful,
     * <code>false</code> otherwise.
     * 
     * @return <code>true</code> if brackets match, <code>false</code> otherwise
     */
     protected boolean matchBracketsAt() {

        char prevChar, nextChar;

        int i;
        int bracketIndex1= fgBrackets.length;
        int bracketIndex2= fgBrackets.length;

        _fStartPos= -1;
        _fEndPos= -1;

        // get the chars preceding and following the start position
        try {

            IDocument doc= _fText.getDocument();

            prevChar= doc.getChar(_fPos - 1);
            nextChar= doc.getChar(_fPos);

            // is the char either an open or close bracket?
            for (i= 0; i < fgBrackets.length; i= i + 2) {
                if (prevChar == fgBrackets[i]) {
                    _fStartPos= _fPos - 1;
                    bracketIndex1= i;
                }
            }
            for (i= 1; i < fgBrackets.length; i= i + 2) {
                if (nextChar == fgBrackets[i]) {
                    _fEndPos= _fPos;
                    bracketIndex2= i;
                }
            }

            if (_fStartPos > -1 && bracketIndex1 < bracketIndex2) {
                _fEndPos= searchForClosingBracket(_fStartPos, prevChar, fgBrackets[bracketIndex1 + 1], doc);
                if (_fEndPos > -1) {
                    return true;
                }
                _fStartPos= -1;
            } else if (_fEndPos > -1) {
                _fStartPos= searchForOpenBracket(_fEndPos, fgBrackets[bracketIndex2 - 1], nextChar, doc);
                if (_fStartPos > -1) {
                    return true;
                }
                _fEndPos= -1;
            }

        } catch (BadLocationException x) {
            LOG.log(Level.SEVERE, "Bad location", x);
        }

        return false;
    }
    
    /**
     * Select the word at the current selection location. Return <code>true</code> if successful,
     * <code>false</code> otherwise.
     * 
     * @return <code>true</code> if a word can be found at the current selection location, <code>false</code> otherwise
     */
     protected boolean matchWord() {

        IDocument doc= _fText.getDocument();

        try {

            int pos= _fPos;
            char c;

            while (pos >= 0) {
                c= doc.getChar(pos);
                if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
                --pos;
            }

            _fStartPos= pos;

            pos= _fPos;
            int length= doc.getLength();

            while (pos < length) {
                c= doc.getChar(pos);
                if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
                ++pos;
            }

            _fEndPos= pos;

            return true;

        } catch (BadLocationException x) {
            LOG.log(Level.SEVERE, "Bad location", x);
        }

        return false;
    }
    
    /**
     * Returns the position of the closing bracket after <code>startPosition</code>.
     * 
     * @param startPosition - the beginning position
     * @param openBracket - the character that represents the open bracket
     * @param closeBracket - the character that represents the close bracket
     * @param document - the document being searched
     * @return the location of the closing bracket.
     * @throws BadLocationException in case <code>startPosition</code> is invalid in the document
     */
     protected int searchForClosingBracket(int startPosition, char openBracket, char closeBracket, IDocument document) throws BadLocationException {
        int stack= 1;
        int closePosition= startPosition + 1;
        int length= document.getLength();
        char nextChar;

        while (closePosition < length && stack > 0) {
            nextChar= document.getChar(closePosition);
            if (nextChar == openBracket && nextChar != closeBracket) {
                stack++;
            } else if (nextChar == closeBracket) {
                stack--;
            }
            closePosition++;
        }

        if (stack == 0) {
            return closePosition - 1;
        }
        return -1;

    }
    
    /**
     * Returns the position of the open bracket before <code>startPosition</code>.
     * 
     * @param startPosition - the beginning position
     * @param openBracket - the character that represents the open bracket
     * @param closeBracket - the character that represents the close bracket
     * @param document - the document being searched
     * @return the location of the starting bracket.
     * @throws BadLocationException in case <code>startPosition</code> is invalid in the document
     */
     protected int searchForOpenBracket(int startPosition, char openBracket, char closeBracket, IDocument document) throws BadLocationException {
        int stack= 1;
        int openPos= startPosition - 1;
        char nextChar;

        while (openPos >= 0 && stack > 0) {
            nextChar= document.getChar(openPos);
            if (nextChar == closeBracket && nextChar != openBracket) {
                stack++;
            } else if (nextChar == openBracket) {
                stack--;
            }
            openPos--;
        }

        if (stack == 0) {
            return openPos + 1;
        }
        return -1;
    }
    
    /**
     * Select the area between the selected bracket and the closing bracket.
     * 
     * @return <code>true</code> if selection was successful, <code>false</code> otherwise
     */
     protected boolean selectBracketBlock() {
        if (matchBracketsAt()) {

            if (_fStartPos == _fEndPos) {
                _fText.setSelectedRange(_fStartPos, 0);
            } else {
                _fText.setSelectedRange(_fStartPos + 1, _fEndPos - _fStartPos - 1);
            }

            return true;
        }
        return false;
    }
    
    /**
     * Select the word at the current selection. 
     */
     protected void selectWord() {
        if (matchWord()) {

            if (_fStartPos == _fEndPos) {
                _fText.setSelectedRange(_fStartPos, 0);
            } else {
                _fText.setSelectedRange(_fStartPos + 1, _fEndPos - _fStartPos - 1);
            }
        }
    }
}

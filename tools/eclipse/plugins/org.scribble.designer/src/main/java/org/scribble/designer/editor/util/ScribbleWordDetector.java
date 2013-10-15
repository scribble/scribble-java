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
package org.scribble.designer.editor.util;


import org.eclipse.jface.text.rules.IWordDetector;

/**
 * A Scribble aware word detector.
 */
public class ScribbleWordDetector implements IWordDetector {

    /**
     * {@inheritDoc}
     */
    public boolean isWordPart(char character) {
        return Character.isJavaIdentifierPart(character);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isWordStart(char character) {
        return Character.isJavaIdentifierStart(character);
    }
}

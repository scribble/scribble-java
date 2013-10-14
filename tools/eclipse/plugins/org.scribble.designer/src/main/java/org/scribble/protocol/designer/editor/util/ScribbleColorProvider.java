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
package org.scribble.protocol.designer.editor.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Manager for colors used in the Java editor.
 */
public class ScribbleColorProvider {

    /**
     * Multi line comment.
     */
    public static final RGB MULTI_LINE_COMMENT= new RGB(128, 0, 0);

    /**
     * Single line comment.
     */
    public static final RGB SINGLE_LINE_COMMENT= new RGB(128, 128, 0);

    /**
     * Keyword.
     */
    public static final RGB KEYWORD= new RGB(0, 0, 255);

    /**
     * Type.
     */
    public static final RGB TYPE= new RGB(0, 0, 128);
    
    /**
     * String.
     */
    public static final RGB STRING= new RGB(0, 128, 0);

    /**
     * Default.
     */
    public static final RGB DEFAULT= new RGB(0, 0, 0);
    
    /**
     * Javadoc keyword.
     */
    public static final RGB JAVADOC_KEYWORD= new RGB(0, 128, 0);
    
    /**
     * Javadoc tag.
     */
    public static final RGB JAVADOC_TAG= new RGB(128, 128, 128);
    
    /**
     * Javadoc link.
     */
    public static final RGB JAVADOC_LINK= new RGB(128, 128, 128);
    
    /**
     * Multi line comment.
     */
    public static final RGB JAVADOC_DEFAULT= new RGB(0, 128, 128);

    private Map<RGB,Color> _fColorTable= new HashMap<RGB,Color>(10);

    /**
     * Release all of the color resources held onto by the receiver.
     */    
    public void dispose() {
        Iterator<Color> e= _fColorTable.values().iterator();
        while (e.hasNext()) {
             ((Color) e.next()).dispose();
        }
    }
    
    /**
     * Return the color that is stored in the color table under the given RGB
     * value.
     * 
     * @param rgb the RGB value
     * @return the color stored in the color table for the given RGB value
     */
    public Color getColor(RGB rgb) {
        Color color= (Color) _fColorTable.get(rgb);
        if (color == null) {
            color= new Color(Display.getCurrent(), rgb);
            _fColorTable.put(rgb, color);
        }
        return color;
    }
}

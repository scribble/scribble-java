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


import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Point;

/**
 * Implementation for an <code>ITextHover</code> which hovers 
 * over Scribble code.
 */
public class ScribbleTextHover implements ITextHover {

    private static final Logger LOG=Logger.getLogger(ScribbleTextHover.class.getName());
    
    /**
     * {@inheritDoc}
     */
    public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
        if (hoverRegion != null) {
            try {
                if (hoverRegion.getLength() > -1) {
                    return textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
                }
            } catch (BadLocationException x) {
                LOG.log(Level.SEVERE, "Bad location", x);
            }
        }
        return ProtocolEditorMessages.getString("JavaTextHover.emptySelection"); //$NON-NLS-1$
    }
    
    /**
     * {@inheritDoc}
     */
    public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
        Point selection= textViewer.getSelectedRange();
        if (selection.x <= offset && offset < selection.x + selection.y) {
            return new Region(selection.x, selection.y);
        }
        return new Region(offset, 0);
    }
}

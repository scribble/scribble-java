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
package org.scribble.protocol.designer.editor.outliner;

import org.scribble.protocol.model.ProtocolReference;

/**
 * This interface represents the component responsible for providing
 * outline information related to model components.
 */
public interface ModelOutliner {

    /**
     * This method returns the label to be displayed
     * for the supplied object.
     * 
     * @param ref The model reference
     * @param obj The object
     * @return The label
     */
    public String getLabel(ProtocolReference ref, Object obj);
    
    /**
     * This method returns an optional image associated with
     * the supplied object.
     * 
     * @param ref The model reference
     * @param obj The object
     * @return The image, or null if no image to display
     */
    public org.eclipse.swt.graphics.Image getImage(ProtocolReference ref, Object obj);
    
    /**
     * This method returns the list of children associated
     * with the supplied object.
     * 
     * @param ref The model reference
     * @param obj The object
     * @return The list of child objects
     */
    public java.util.List<Object> getChildren(ProtocolReference ref, Object obj);
    
    /**
     * This method determines whether the supplied object has
     * children.
     * 
     * @param ref The model reference
     * @param obj The object
     * @return Whether the object has children
     */
    public boolean hasChildren(ProtocolReference ref, Object obj);
    
}

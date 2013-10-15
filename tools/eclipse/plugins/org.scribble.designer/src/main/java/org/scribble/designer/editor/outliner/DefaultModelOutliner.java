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
package org.scribble.designer.editor.outliner;

import org.eclipse.swt.graphics.Image;
import org.scribble.designer.editor.ScribbleImages;

/**
 * This is the abstract base implementation of the ModelOutliner
 * interface, responsible for the model objects in the generic
 * Scribble model package.
 */
public class DefaultModelOutliner implements ModelOutliner {

    /**
     * {@inheritDoc}
     */
    public String getLabel(Object obj) {
        String ret=null;
      
        //if (ret == null) {
            
            /* TODO:
             * Find way to extract name from protocol objects.
             * 
            if (obj instanceof org.scribble.model.Declaration) {
                ret = ((org.scribble.model.Declaration)obj).getName();
            }
            */
        //}
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public org.eclipse.swt.graphics.Image getImage(Object obj) {
        Image ret=null;

        // TODO: Check derived language specific outliners

        if (ret == null) {
            
            /*
             * TODO:
             * Find way to derive images from the relevant model types
             */
            if (obj instanceof org.scribble.model.global.GActivity
            		|| obj instanceof org.scribble.model.local.LActivity) {
                ret = ScribbleImages.getImage("activity.png");
            }
        }
        
        return (ret);        
    }
    
    /**
     * {@inheritDoc}
     */
    public java.util.List<Object> getChildren(Object obj) {
        java.util.List<Object> ret=null;

        // TODO: Check language specific outliners
        
        if (ret == null) {
            ret = new java.util.Vector<Object>();
        }
        
        return (ret);        
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasChildren(Object obj) {
        boolean ret=false;
        
        ret = getChildren(obj).size() > 0;
        
        return (ret);
    }
}

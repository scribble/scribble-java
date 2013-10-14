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

import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This class provides access to images configured within the
 * environment.
 */
public final class ScribbleImages {

    private static final String IMAGES_LOCATION = "images/";
    
    private static org.eclipse.jface.resource.ImageRegistry registry=
            new org.eclipse.jface.resource.ImageRegistry();

    /**
     * Private constructor.
     */
    private ScribbleImages() {
    }
    
    /**
     * This method returns the image associated with the
     * supplied name.
     * 
     * @param name The name
     * @return The image
     */
    public static Image getImage(String name) {
        Image ret=null;
        
        // Make sure the image descriptor is registered
        getImageDescriptor(name);
        
        ret = registry.get(name);
        
        return (ret);
    }
    
    /**
     * This method returns the image descriptor associated with the
     * supplied name.
     * 
     * @param name The name
     * @return The descriptor
     */
    public static ImageDescriptor getImageDescriptor(String name) {
        ImageDescriptor ret=registry.getDescriptor(name);
        
        if (ret == null) {
            ret = ImageDescriptor.createFromFile(ScribbleImages.class,
                    IMAGES_LOCATION+name);
            
            registry.put(name, ret);
        }
        
        return (ret);
    }
}

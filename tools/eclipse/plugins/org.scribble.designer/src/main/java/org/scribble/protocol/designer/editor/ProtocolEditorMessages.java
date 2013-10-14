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

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Protocol editor messages.
 *
 */
public final class ProtocolEditorMessages {

    private static final String RESOURCE_BUNDLE= "org.scribble.protocol.designer.editor.Messages";

    private static ResourceBundle fgResourceBundle=null;

    /**
     * Default constructor.
     */
    private ProtocolEditorMessages() {
    }

    /**
     * Get the property for the key.
     * 
     * @param key The key
     * @return The value
     */
    public static String getString(String key) {
        try {
            return fgResourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }
    
    /**
     * Get the resource bundle.
     * 
     * @return The resource bundle
     */
    public static ResourceBundle getResourceBundle() {
    	if (fgResourceBundle == null) {
    		try {
    			fgResourceBundle = java.util.PropertyResourceBundle.getBundle(RESOURCE_BUNDLE);
    		} catch (Throwable t) {
    			t.printStackTrace();
    			
    			java.io.StringReader reader=new java.io.StringReader("");
    			
    			try {
					fgResourceBundle = new java.util.PropertyResourceBundle(reader);
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
    			reader.close();
    		}
    	}
    	
        return fgResourceBundle;
    }
}

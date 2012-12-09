/*
 * Copyright 2010 www.scribble.org
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
package org.scribble.common.resource;

import java.net.URI;

/**
 * This class represents a default implement of the resource locator
 * interface, that can be used to load artifacts relative to a base
 * location.
 *
 */
public class DefaultResourceLocator implements ResourceLocator {

    private java.io.File _baseDir=null;

    /**
     * This constructor initializes the default resource locator
     * with the location of the base directory from which the
     * relative file paths should be derived.
     * 
     * @param baseDir The base directory
     */
    public DefaultResourceLocator(java.io.File baseDir) {
        _baseDir = baseDir;
    }
    
    /**
     * This method can be used to retrieve the URI of a resource which
     * is located at the specified URI, potentially relative to a resource
     * that is being processed.
     * 
     * @param uri The relative URI of the resource to load
     * @return The URI, or null if not found
     * @throws Exception Failed to get resource URI
     */
    public java.net.URI getResourceURI(String uri) throws Exception {
        java.net.URI ret=null;
        java.io.File file=new java.io.File(_baseDir, uri);

        if (!file.exists()) {
            ret = new URI(uri);                
        } else {
            ret = file.toURI();
        }
        
        return (ret);
    }

}

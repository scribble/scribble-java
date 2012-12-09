/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.common.model;

/**
 * This class represents the default implementation of the annotation
 * interface.
 *
 */
public class DefaultAnnotation implements Annotation {

    private String _annotation=null;
    private String _id=null;
    
    /**
     * This is the constructor, initialized with the
     * annotation text.
     * 
     * @param text The text
     */
    public DefaultAnnotation(String text) {
        _annotation = text;
    }
    
    /**
     * This is the constructor, initialized with the
     * optional id and annotation text.
     * 
     * @param id The optional id
     * @param text The text
     */
    public DefaultAnnotation(String id, String text) {
        _id = id;
        _annotation = text;
    }
    
    /**
     * Optional id for the annotation. If specified, it must be
     * unique within the scope of the protocol model in which
     * it is defined.
     * 
     * @return The unique annotation id, or null if undefined
     */
    public String getId() {
        return (_id);
    }
    
    /**
     * This method sets the annotation.
     * 
     * @param annotation The annotation
     */
    public void setAnnotation(String annotation) {
        _annotation = annotation;
    }
    
    /**
     * This method returns the textual representation
     * of the annotation.
     * 
     * @return The annotation text
     */
    public String toString() {
        return (_annotation);
    }
}

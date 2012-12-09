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
 * This interface represents an annotation associated with a protocol
 * model object.
 *
 */
public interface Annotation {

    /**
     * Optional id for the annotation. If specified, it must be
     * unique within the scope of the protocol model in which
     * it is defined.
     * 
     * @return The unique annotation id, or null if undefined
     */
    public String getId();
    
    /**
     * This method generates a text based representation of annotation,
     * that would be exported between [[ and ]] delimitors in the
     * text based scribble representation.
     * 
     * @return The textual representation of the annotation
     */
    public String toString();
    
}

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
package org.scribble.protocol.parser;

import org.scribble.common.logging.Journal;
import org.scribble.common.model.Annotation;

/**
 * This interface represents an annotation processor, used to process
 * free format text annotations.
 *
 */
public interface AnnotationProcessor {

    /**
     * This method is called when an annotation is found.
     * 
     * @param annotation The annotation text
     * @param properties The associated model object's properties
     * @param journal The journal
     * @return An implementation of the annotation, or null if cannot be processed
     */
    public Annotation getAnnotation(String annotation, java.util.Map<String,Object> properties,
                                Journal journal);
    
}

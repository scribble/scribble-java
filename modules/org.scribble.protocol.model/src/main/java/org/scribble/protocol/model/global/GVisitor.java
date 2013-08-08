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
package org.scribble.protocol.model.global;

import org.scribble.protocol.model.Visitor;
import org.scribble.protocol.model.global.GChoice;

/**
 * This interface represents a visitor which can be used
 * to traverse a model.
 */
public interface GVisitor extends Visitor {
    
    /**
     * This method indicates the start of a
     * block.
     * 
     * @param elem The block
     * @return Whether to process the contents
     */
    public boolean start(GBlock elem);
    
    /**
     * This method indicates the end of a
     * block.
     * 
     * @param elem The block
     */
    public void end(GBlock elem);
    
    /**
     * This method indicates the start of a
     * choice.
     * 
     * @param elem The choice
     * @return Whether to process the contents
     */
    public boolean start(GChoice elem);
    
    /**
     * This method indicates the end of a
     * choice.
     * 
     * @param elem The choice
     */
    public void end(GChoice elem);
    
    /**
     * This method indicates the start of a
     * parallel.
     * 
     * @param elem The parallel
     * @return Whether to process the contents
     */
    public boolean start(GParallel elem);
    
    /**
     * This method indicates the end of a
     * parallel.
     * 
     * @param elem The parallel
     */
    public void end(GParallel elem);
    
    /**
     * This method indicates the start of a
     * protocol.
     * 
     * @param elem The protocol
     * @return Whether to process the contents
     */
    public boolean start(GProtocol elem);
    
    /**
     * This method indicates the end of a
     * protocol.
     * 
     * @param elem The protocol
     */
    public void end(GProtocol elem);
    
    /**
     * This method indicates the start of a
     * labelled block.
     * 
     * @param elem The labelled block
     * @return Whether to process the contents
     */
    public boolean start(GRecursion elem);
    
    /**
     * This method indicates the end of a
     * labelled block.
     * 
     * @param elem The labelled block
     */
    public void end(GRecursion elem);
    
    /**
     * This method indicates the start of an
     * interruptible block.
     * 
     * @param elem The interruptible
     * @return Whether to process the contents
     */
    public boolean start(GInterruptible elem);
    
    /**
     * This method indicates the end of an
     * interruptible block.
     * 
     * @param elem The interruptible
     */
    public void end(GInterruptible elem);
    
    /**
     * This method visits an interaction component.
     * 
     * @param elem The interaction
     */
    public void accept(GMessage elem);
    
    /**
     * This method visits a recursion component.
     * 
     * @param elem The recursion
     */
    public void accept(GContinue elem);
    
    /**
     * This method visits a do component.
     * 
     * @param elem The do
     */
    public void accept(GDo elem);
    
    /**
     * This method visits a custom activity.
     * 
     * @param elem The custom activity
     */
    public void accept(GCustomActivity elem);
    
}

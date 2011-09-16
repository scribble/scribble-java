/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.model;


/**
 * This class represents a group of activities within
 * an interrupt block associated with a global escape.
 * 
 */
public class Interrupt extends ModelObject {

    private Block _contents=null;

    /**
     * This method returns the block of activities associated
     * with the definition.
     * 
     * @return The block of activities
     */
    public Block getBlock() {
        
        if (_contents == null) {
            _contents = new Block();
            _contents.setParent(this);
        }
        
        return (_contents);
    }
    
    /**
     * This method sets the block of activities associated
     * with the definition.
     * 
     * @param block The block of activities
     */
    public void setBlock(Block block) {
        if (_contents != null) {
            _contents.setParent(null);
        }
        
        _contents = block;
        
        if (_contents != null) {
            _contents.setParent(this);
        }
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        
        if (visitor.start(this)) {
        
            getBlock().visit(visitor);
        }
        
        visitor.end(this);
    }

}

/*
 * Copyright 2009-10 www.scribble.org
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
 * This class represents the Unordered construct.
 * 
 */
public class Unordered extends Activity {

    private Block _block=new Block();

    /**
     * This is the default constructor.
     * 
     */
    public Unordered() {
        _block.setParent(this);
    }
        
    /**
     * This method returns the activities.
     * 
     * @return The block of activities
     */
    public Block getBlock() {
        return (_block);
    }
    
    /**
     * This method sets the block.
     * 
     * @param block The block
     */
    public void setBlock(Block block) {
        if (_block != null) {
            _block.setParent(null);
        }
        
        _block = block;
        
        if (_block != null) {
            _block.setParent(this);
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
        
            if (getBlock() != null) {
                getBlock().visit(visitor);
            }
        }
        
        visitor.end(this);
    }
}

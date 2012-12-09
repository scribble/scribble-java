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
 * This class represents the Try/Escape construct.
 * 
 */
public class Do extends Activity {

    private Block _block=null;
    private java.util.List<Interrupt> _interrupts=new ContainmentList<Interrupt>(this, Interrupt.class);

    /**
     * This is the default constructor.
     * 
     */
    public Do() {
    }
    
    /**
     * This method returns the block of activities associated
     * with the definition.
     * 
     * @return The block of activities
     */
    public Block getBlock() {
        
        if (_block == null) {
            _block = new Block();
            _block.setParent(this);
        }
        
        return (_block);
    }
    
    /**
     * This method sets the block of activities associated
     * with the definition.
     * 
     * @param block The block of activities
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
     * This method returns the list of interrupt statements associated
     * with the global escape (do).
     * 
     * @return The list of interrupt statements
     */
    public java.util.List<Interrupt> getInterrupts() {
        return (_interrupts);
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
            
            for (Interrupt path : getInterrupts()) {
                path.visit(visitor);
            }
        }
            
        visitor.end(this);
    }

}

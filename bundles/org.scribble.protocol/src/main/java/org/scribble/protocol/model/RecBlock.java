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
 * This class represents the Recur construct.
 * 
 */
public class RecBlock extends Activity {

	private String m_label=null;
	private Block m_block=new Block();

	/**
	 * This is the default constructor.
	 * 
	 */
	public RecBlock() {
		m_block.setParent(this);
	}
	
	/**
	 * This method returns the label associated with the labelled block construct.
	 * 
	 * @return The label
	 */
	public String getLabel() {
		return(m_label);
	}
	
	/**
	 * This method sets the label associated with the labelled block construct.
	 * 
	 * @param label The label
	 */
	public void setLabel(String label) {
		m_label = label;
	}
		
	/**
	 * This method returns the activities.
	 * 
	 * @return The block of activities
	 */
	public Block getBlock() {
		return(m_block);
	}
	
	/**
	 * This method sets the block.
	 * 
	 * @param block The block
	 */
	public void setBlock(Block block) {
		if (m_block != null) {
			m_block.setParent(null);
		}
		
		m_block = block;
		
		if (m_block != null) {
			m_block.setParent(this);
		}
	}

	/**
	 * This method visits the model object using the supplied
	 * visitor.
	 * 
	 * @param visitor The visitor
	 */
	public void visit(Visitor visitor) {
		visitor.start(this);
		
		if (getBlock() != null) {
			getBlock().visit(visitor);
		}
		
		visitor.end(this);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecBlock that = (RecBlock) o;

        return !(m_label != null
                ? !m_label.equals(that.m_label)
                : that.m_label != null)
            && !(m_block != null
                ? !m_block.equals(that.m_block)
                : that.m_block != null);
    }

    @Override
    public int hashCode() {
        int result = m_label != null ? m_label.hashCode() : 0;
        return 31 * result + (m_block != null ? m_block.hashCode() : 0);
    }

	@Override
	public String toString() {
		return "rec "+m_label+" "+m_block;
	}
}

/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.model;


/**
 * This class represents a group of activities within
 * a when block associated with a choice.
 * 
 */
public class When extends ModelObject {

	private MessageSignature m_messageSignature=null;
	private Block m_block=null;

	/**
	 * This class returns the message signature.
	 * 
	 * @return The message signature
	 */
	public MessageSignature getMessageSignature() {
		return(m_messageSignature);
	}
	
	/**
	 * This method sets the message signature.
	 * 
	 * @param signature The message signature
	 */
	public void setMessageSignature(MessageSignature signature) {
		
		if (m_messageSignature != null) {
			m_messageSignature.setParent(null);
		}
		
		m_messageSignature = signature;
		
		if (m_messageSignature != null) {
			m_messageSignature.setParent(this);
		}
	}
	
	/**
	 * This method returns the block of activities associated
	 * with the definition.
	 * 
	 * @return The block of activities
	 */
	public Block getBlock() {
		
		if (m_block == null) {
			m_block = new Block();
			m_block.setParent(this);
		}
		
		return(m_block);
	}
	
	/**
	 * This method sets the block of activities associated
	 * with the definition.
	 * 
	 * @param block The block of activities
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
		
		if (visitor.start(this)) {
		
			getBlock().visit(visitor);
		}
		
		visitor.end(this);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        When that = (When) o;

        return !(m_block != null
                ? !m_block.equals(that.m_block)
                : that.m_block != null)
            && !(m_messageSignature != null
                ? !m_messageSignature.equals(that.m_messageSignature)
                : that.m_messageSignature != null);
    }

    @Override
    public int hashCode() {
        int result = m_messageSignature != null ? m_messageSignature.hashCode() : 0;
        return(result);
        //return 31 * result + (m_contents != null ? m_contents.hashCode() : 0);
    }

	@Override
	public String toString() {
		String ret=m_messageSignature + ":" + "\r\n";
		
		if (m_block != null) {
			for (Activity act: m_block.getContents()) ret += act + "\n";
		}

		return(ret);
	}

}

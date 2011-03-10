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
 * a catch specific 'escape' block of a
 * try/escape construct.
 * 
 */
public class Catch extends ModelObject {

	private java.util.List<Interaction> m_interactions=
		new ContainmentList<Interaction>(this, Interaction.class);
	private Block m_contents=null;

	/**
	 * This method returns the list of interactions.
	 * 
	 * @return The list of interactions
	 */
	public java.util.List<Interaction> getInteractions() {
		return(m_interactions);
	}
	
	/**
	 * This method returns the block of activities associated
	 * with the definition.
	 * 
	 * @return The block of activities
	 */
	public Block getBlock() {
		
		if (m_contents == null) {
			m_contents = new Block();
			m_contents.setParent(this);
		}
		
		return(m_contents);
	}
	
	/**
	 * This method sets the block of activities associated
	 * with the definition.
	 * 
	 * @param block The block of activities
	 */
	public void setBlock(Block block) {
		if (m_contents != null) {
			m_contents.setParent(null);
		}
		
		m_contents = block;
		
		if (m_contents != null) {
			m_contents.setParent(this);
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
		
			// The interaction visitor would need to check whether it was contained
			// within a catch block, to understand its context.
			/*
			for (Interaction p : getInteractions()) {
				p.visit(visitor);
			}
			*/
	
			getBlock().visit(visitor);
		}
		
		visitor.end(this);
	}

}

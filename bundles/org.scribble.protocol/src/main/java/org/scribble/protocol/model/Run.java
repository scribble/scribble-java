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
 * This class represents the Run construct.
 * 
 */
public class Run extends Activity {

	private ProtocolReference m_reference=null;
	private java.util.List<Parameter> m_parameters=new java.util.Vector<Parameter>();
	private Block m_block=null;

	/**
	 * This is the default constructor.
	 * 
	 */
	public Run() {
	}
	
	/**
	 * This method returns the parameters for the
	 * composition construct.
	 * 
	 * @return The list of parameters
	 */
	public java.util.List<Parameter> getParameters() {
		return(m_parameters);
	}
	
	/**
	 * This method returns the declaration binding associated
	 * with the supplied declaration.
	 * 
	 * @param decl The declaration
	 * @return The declaration binding, or null if not found
	 */
	public Parameter getParameter(String declName) {
		Parameter ret=null;
		
		java.util.Iterator<Parameter> iter=getParameters().iterator();
		
		while (ret == null && iter.hasNext()) {
			ret = iter.next();
			
			if (ret.getName().equals(declName) == false) {
				ret = null;
			}
		}
		
		return(ret);
	}
		
	/**
	 * This method returns the protocol reference associated
	 * with the run construct.
	 * 
	 * @return The protocol reference, or null if not defined
	 */
	public ProtocolReference getProtocolReference() {
		return(m_reference);
	}
	
	/**
	 * This method sets the protocol reference associated
	 * with the run construct.
	 * 
	 * @param ref The protocol reference
	 */
	public void setProtocolReference(ProtocolReference ref) {
		
		if (m_reference != null) {
			m_reference.setParent(null);
		}
		
		m_reference = ref;
		
		if (m_reference != null) {
			m_reference.setParent(this);
		}
	}
	
	/**
	 * This method returns the protocol, if available as an inner protocol within the
	 * enclosing protocol.
	 * 
	 * @return The protocol, or null if not found
	 */
	/*
	public Protocol getProtocol() {
		Protocol ret=null;
		
		Protocol parent=enclosingProtocol();
		
		if (parent != null) {
			for (int i=0; ret == null && i < parent.getBlock().getContents().size(); i++) {
				
				if (parent.getBlock().getContents().get(i) instanceof Protocol) {
					Protocol inner=(Protocol)parent.getBlock().getContents().get(i);
					
					if (m_reference.getName().equals(inner.getName())) {
						ret = inner;
					}
				}
			}
		}
		
		return(ret);
	}
	*/
	
	/**
	 * This method returns the block of activities associated
	 * with an inline definition.
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
	 * This method determines whether the run construct is inline.
	 * 
	 * @return Whether inline
	 */
	public boolean isInline() {
		return(m_reference == null || m_reference.getName() == null ||
						m_reference.getName().trim().length() == 0);
	}
	
	/**
	 * This method visits the model object using the supplied
	 * visitor.
	 * 
	 * @param visitor The visitor
	 */
	public void visit(Visitor visitor) {
		visitor.start(this);
		
		if (m_reference != null) {
			m_reference.visit(visitor);
		}
			
		for (Parameter db : getParameters()) {
			db.visit(visitor);
		}
		
		// Only visit block if an inline definition
		if (m_block != null && isInline()) {
			m_block.visit(visitor);
		}
		
		visitor.end(this);
	}
}

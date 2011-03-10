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
 * This class represents the Include construct.
 * 
 */
public class Include extends Activity {

	private ProtocolReference m_reference=null;
	private java.util.List<Parameter> m_parameters=new java.util.Vector<Parameter>();

	/**
	 * This is the default constructor.
	 * 
	 */
	public Include() {
	}
	
	/**
	 * This method returns the bindings for the
	 * composition construct.
	 * 
	 * @return The list of bindings
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
	public ProtocolReference getReference() {
		return(m_reference);
	}
	
	/**
	 * This method sets the protocol reference associated
	 * with the run construct.
	 * 
	 * @param ref The protocol reference
	 */
	public void setReference(ProtocolReference ref) {
		
		if (m_reference != null) {
			m_reference.setParent(null);
		}
		
		m_reference = ref;
		
		if (m_reference != null) {
			m_reference.setParent(this);
		}
	}
	
	/**
	 * This method visits the model object using the supplied
	 * visitor.
	 * 
	 * @param visitor The visitor
	 */
	public void visit(Visitor visitor) {
		visitor.accept(this);
		
		if (m_reference != null) {
			m_reference.visit(visitor);
		}
			
		for (Parameter db : getParameters()) {
			db.visit(visitor);
		}
	}
}

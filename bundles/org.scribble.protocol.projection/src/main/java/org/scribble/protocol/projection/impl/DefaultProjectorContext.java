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
package org.scribble.protocol.projection.impl;

import java.util.logging.Logger;

import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.model.*;
import org.scribble.common.logging.Journal;

/**
 * This class represents the default projection context.
 */
public class DefaultProjectorContext implements ProjectorContext {
	
	private static Logger logger = Logger.getLogger(DefaultProjectorContext.class.getName());
	
	private ProtocolContext m_context=null;
	private static java.util.List<ProjectorRule> m_rules=new java.util.Vector<ProjectorRule>();
	private Scope m_scope=new Scope();
	private java.util.List<Scope> m_scopeStack=new java.util.Vector<Scope>();
	private java.util.Map<Protocol,java.util.List<Role>> m_definitionRoleMap=
					new java.util.HashMap<Protocol,java.util.List<Role>>();

	static {
		m_rules.add(new BlockProjectorRule());
		m_rules.add(new InterruptProjectorRule());
		m_rules.add(new ChoiceProjectorRule());
		m_rules.add(new DirectedChoiceProjectorRule());
		m_rules.add(new OnMessageProjectorRule());
		m_rules.add(new ProtocolImportListProjectorRule());
		m_rules.add(new ProtocolImportProjectorRule());
		m_rules.add(new TypeImportListProjectorRule());
		m_rules.add(new TypeImportProjectorRule());
		m_rules.add(new InteractionProjectorRule());
		m_rules.add(new MessageSignatureProjectorRule());
		m_rules.add(new ParallelProjectorRule());
		m_rules.add(new IntroducesProjectorRule());
		m_rules.add(new RoleProjectorRule());
		m_rules.add(new ProtocolModelProjectorRule());
		m_rules.add(new ProtocolProjectorRule());
		m_rules.add(new ParameterProjectorRule());
		m_rules.add(new ProtocolReferenceProjectorRule());
		m_rules.add(new RepeatProjectorRule());
		m_rules.add(new RecBlockProjectorRule());
		m_rules.add(new RecursionProjectorRule());
		m_rules.add(new RunProjectorRule());
		m_rules.add(new UseProjectorRule());
		m_rules.add(new DoProjectorRule());
		m_rules.add(new DataTypeProjectorRule());
		m_rules.add(new TypeReferenceProjectorRule());
		m_rules.add(new UnorderedProjectorRule());
	}

	/**
	 * This is the default constructor for the projection context.
	 * 
	 * @param context The protocol context
	 */
	public DefaultProjectorContext(ProtocolContext context) {
		m_context = context;
	}
	
	/**
	 * This method returns the context in which the protocol
	 * is being processed.
	 * 
	 * @return The protocol context
	 */
	public ProtocolContext getProtocolContext() {
		return(m_context);
	}
	
	/**
	 * This method projects the supplied model object based on the
	 * specified role.
	 * 
	 * @param model The model object
	 * @param role The role
	 * @param l The model listener
	 * @return The projected model object
	 */
	public ModelObject project(ModelObject model, Role role,
						Journal l) {
		ModelObject ret=null;
		
		for (int i=0; model != null && ret == null && i < getRules().size(); i++) {
			if (getRules().get(i).isSupported(model)) {
				ret = getRules().get(i).project(this, model, role,
								l);
			}
		}
				
		return(ret);
	}
		
	/**
	 * This method returns a list of projection rules.
	 * 
	 * @return The list of projection rules
	 */
	public java.util.List<ProjectorRule> getRules() {
		return(m_rules);
	}
	
	/**
	 * This method returns the named state from the current
	 * scope.
	 * 
	 * @param name The state name
	 * @return The state value, or null if not found
	 */
	public Object getState(String name) {
		return(m_scope.getState(name));
	}
	
	/**
	 * This method sets the value associated with the supplied
	 * name in the current state scope.
	 * 
	 * @param name The state name
	 * @param value The state value
	 */
	public void setState(String name, Object value) {
		m_scope.setState(name, value);
	}

	/**
	 * This method pushes the current state onto a stack.
	 */
	public void pushState() {
		m_scope.pushState();
	}
	
	/**
	 * This method pops the current state from the stack.
	 */
	public void popState() {
		m_scope.popState();
	}
		
	/**
	 * This method pushes the current scope onto a stack.
	 */
	public void pushScope() {
		m_scopeStack.add(0, m_scope);
		m_scope = new Scope();
	}
	
	/**
	 * This method pops the current scope from the stack.
	 */
	public void popScope() {
		if (m_scopeStack.size() > 0) {
			m_scope = m_scopeStack.remove(0);
		} else {
			logger.severe("No state entry to pop from stack");
		}
	}
	
	/**
	 * This method determines whether the context is associated
	 * with the outer scope.
	 * 
	 * @return Whether the context is for the outer scope
	 */
	public boolean isOuterScope() {
		return(m_scopeStack.size() < 1);
	}

	/**
	 * This method registers an interest in the projected role
	 * associated with the supplied definition.
	 * 
	 * @param defn The definition
	 * @param role The projected role of interest
	 */
	public void registerInterest(Protocol defn, Role role) {
		java.util.List<Role> roles=m_definitionRoleMap.get(defn);
		
		if (roles == null) {
			roles = new java.util.Vector<Role>();
			m_definitionRoleMap.put(defn, roles);
		}
		
		if (roles.contains(role) == false) {
			roles.add(role);
		}
	}
	
	/**
	 * This method returns the list of roles of interest
	 * for the supplied definition.
	 * 
	 * @param defn The definition
	 * @return The list of roles, or null if no roles have
	 * 				registered interest in the definition
	 */
	public java.util.List<Role> getRolesOfInterestForDefinition(Protocol defn) {
		return(m_definitionRoleMap.get(defn));
	}
}

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
package org.scribble.protocol.validation.rules;

import java.text.MessageFormat;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.DirectedChoice;
import org.scribble.protocol.model.Do;
import org.scribble.protocol.model.End;
import org.scribble.protocol.model.Inline;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.Interrupt;
import org.scribble.protocol.model.Introduces;
import org.scribble.protocol.model.OnMessage;
import org.scribble.protocol.model.Parallel;
import org.scribble.protocol.model.Parameter;
import org.scribble.protocol.model.ParameterDefinition;
import org.scribble.protocol.model.ProtocolImport;
import org.scribble.protocol.model.ProtocolImportList;
import org.scribble.protocol.model.RecBlock;
import org.scribble.protocol.model.Recursion;
import org.scribble.protocol.model.Repeat;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.Run;
import org.scribble.protocol.model.TypeImport;
import org.scribble.protocol.model.TypeImportList;
import org.scribble.protocol.model.Unordered;
import org.scribble.protocol.model.Visitor;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;

/**
 * This class provides the validation rule for the Protocol
 * model component.
 *
 */
public class ProtocolValidatorRule implements ProtocolComponentValidatorRule {

	/**
	 * This method determines whether the rule is applicable
	 * for the supplied model object.
	 * 
	 * @return Whether the rule can be used to validate the
	 * 				supplied model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == Protocol.class);
	}
	
	/**
	 * This method validates the supplied model object.
	 * 
	 * @param obj The model object being validated
	 * @param logger The logger
	 */
	public void validate(ProtocolContext context, ModelObject obj,
					Journal logger) {
		Protocol elem=(Protocol)obj;
		
		elem.visit(new ConnectednessVisitor(elem, logger));
	}
	
	protected class ConnectednessVisitor implements Visitor {
		
		private Protocol m_protocol=null;
		private Journal m_logger=null;
		private java.util.Stack<java.util.Set<Role>> m_roleStack=
							new java.util.Stack<java.util.Set<Role>>();
		private java.util.Stack<java.util.Stack<java.util.Set<Role>>> m_saveStack=
				new java.util.Stack<java.util.Stack<java.util.Set<Role>>>();
		
		public ConnectednessVisitor(Protocol p, Journal logger) {
			m_protocol = p;
			m_logger = logger;
		}
		
		protected boolean isRoleKnown(Role r) {
			boolean ret=false;
			
			for (java.util.Set<Role> roles : m_roleStack) {
				if (roles.contains(r)) {
					ret = true;
					break;
				}
			}
			
			return(ret);
		}
		
		protected void validate(Activity act, Role r) {
			if (isRoleKnown(r) == false) {
				m_logger.error(MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						r.getName()), act.getProperties());
			}
		}
		
		protected void addRole(Role r) {
			if (isRoleKnown(r) == false) {
				java.util.Set<Role> roles=m_roleStack.peek();
	
				roles.add(r);
			}
		}
		
		protected void push() {
			if (m_roleStack == null) {
				m_roleStack = new java.util.Stack<java.util.Set<Role>>();
			}
			m_roleStack.push(new java.util.HashSet<Role>());
		}
		
		protected void pop() {
			m_roleStack.pop();
		}

		protected void saveStack() {
			m_saveStack.push(m_roleStack);
			m_roleStack = null;
			push();
		}
		
		protected void restoreStack() {
			m_roleStack = m_saveStack.pop();
		}

		public boolean start(Block elem) {
			push();
			return true;
		}

		public void end(Block elem) {
			pop();
		}

		public boolean start(Choice elem) {
			validate(elem, elem.getRole());
			saveStack();
			addRole(elem.getRole());
			return(true);
		}

		public void end(Choice elem) {
			restoreStack();
		}

		public boolean start(DirectedChoice elem) {
			// Validate that the from role is already known, but
			// add the from and to roles, so that the to roles
			// can subsequently be used for validation
			validate(elem, elem.getFromRole());

			saveStack();

			addRole(elem.getFromRole());
			for (Role r : elem.getToRoles()) {
				addRole(r);
			}
			return(true);
		}

		public void end(DirectedChoice elem) {
			restoreStack();
		}

		public boolean start(OnMessage elem) {
			return(true);
		}

		public void end(OnMessage elem) {
		}

		public boolean start(Parallel elem) {
			return(true);
		}

		public void end(Parallel elem) {
		}

		public boolean start(Protocol elem) {
			if (elem == m_protocol) {
				push();
				for (ParameterDefinition pd : elem.getParameterDefinitions()) {
					if (pd.getType() == null) {
						addRole(new Role(pd.getName()));
					}
				}
				return(true);
			} else {
				return(false);
			}
		}

		public void end(Protocol elem) {
			if (elem == m_protocol) {
				pop();
			}
		}

		public boolean start(Repeat elem) {
			for (Role r : elem.getRoles()) {
				validate(elem, r);
			}
			saveStack();
			for (Role r : elem.getRoles()) {
				addRole(r);
			}
			return(true);
		}

		public void end(Repeat elem) {
			restoreStack();
		}

		public boolean start(RecBlock elem) {
			push();
			return(true);
		}

		public void end(RecBlock elem) {
			pop();
		}

		public boolean start(Unordered elem) {
			return(true);
		}

		public void end(Unordered elem) {
			
		}

		public boolean start(Do elem) {
			return(true);
		}

		public void end(Do elem) {
			
		}

		public boolean start(Interrupt elem) {
			return(true);
		}

		public void end(Interrupt elem) {
		}

		public void accept(Run elem) {
			for (Parameter p : elem.getParameters()) {
				validate(elem, new Role(p.getName()));
			}
		}

		public void accept(TypeImportList elem) {
		}

		public void accept(ProtocolImportList elem) {
		}

		public void accept(Interaction elem) {
			// Validate that the from role is already known, but
			// add the from and to roles, so that the to roles
			// can subsequently be used for validation
			validate(elem, elem.getFromRole());
			
			addRole(elem.getFromRole());
			for (Role r : elem.getToRoles()) {
				addRole(r);
			}
		}

		public void accept(Introduces elem) {
			validate(elem, elem.getIntroducer());
			addRole(elem.getIntroducer());
			for (Role r : elem.getIntroducedRoles()) {
				addRole(r);
			}
		}

		public void accept(Recursion elem) {
		}

		public void accept(Inline elem) {
		}

		public void accept(TypeImport elem) {
		}

		public void accept(ProtocolImport elem) {
		}

		public void accept(End elem) {
		}
		
	}
}

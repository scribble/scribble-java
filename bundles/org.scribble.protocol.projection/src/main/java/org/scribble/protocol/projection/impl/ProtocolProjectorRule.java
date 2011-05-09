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

//import java.util.logging.Logger;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.*;

/**
 * This class provides the Protocol implementation of the
 * projector rule.
 */
public class ProtocolProjectorRule implements ProjectorRule {

	/**
	 * This method determines whether the projection rule is
	 * appropriate for the supplied model object.
	 * 
	 * @param obj The model object to be projected
	 * @return Whether the rule is relevant for the
	 * 				model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == Protocol.class);
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
	public ModelObject project(ProjectorContext context, ModelObject model,
					Role role, Journal l) {
		Activity ret=null;
		Protocol source=(Protocol)model;
		java.util.List<Role> roles=null;
		
		if (context.isOuterScope() ||
				(roles=context.getRolesOfInterestForDefinition(source)) != null) {

			if (roles == null) {
				roles = new java.util.Vector<Role>();
				roles.add(role);
			}
			
			for (int j=0; j < roles.size(); j++) {
				Protocol prot = new Protocol();

				// Set current role
				role = roles.get(j);
							
				prot.derivedFrom(source);
				
				prot.setName(source.getName());
			
				Role located=(Role)context.project(role, role, l);

				prot.setRole(located);
				
				context.pushScope();
				
				// Project parameters
				for (ParameterDefinition p : source.getParameterDefinitions()) {
					ParameterDefinition projp=(ParameterDefinition)context.project(p, role, l);
					
					if (projp != null) {
						prot.getParameterDefinitions().add(projp);
					}
				}
				
				prot.setBlock((Block)context.project(source.getBlock(),
						role, l));
				prot.getBlock().setParent(prot);
				
				context.popScope();
				
				// Clean up role lists, to ensure they don't include redundant roles
				cleanUpRoles(prot);
				
				if (ret == null) {
					ret = prot;
				} else if (ret instanceof Block) {
					((Block)ret).getContents().add(prot);
				} else {
					Block b=new Block();
					b.getContents().add(ret);
					b.getContents().add(prot);
					ret = b;
				}
			}
		}

		return(ret);
	}
	
	protected void cleanUpRoles(Protocol protocol) {
		
		// Visit protocol to locate role lists
		protocol.visit(new DefaultVisitor() {
			
			public void accept(RoleList list) {
				
				// Identify parent block
				Block parent=(Block)list.getParent();
				
				for (int i=list.getRoles().size()-1; i >= 0; i--) {
					final Role role=(Role)list.getRoles().get(i);
					final java.util.List<Activity> acts=new java.util.Vector<Activity>();
					
					// Find out if role is used in a relevant activity
					parent.visit(new DefaultVisitor() {
						
						public void accept(Interaction interaction) {
							if (role.equals(interaction.getFromRole()) ||
									interaction.getToRoles().contains(role)) {
								acts.add(interaction);
							}
						}
						
						public boolean start(Choice choice) {
							if (role.equals(choice.getFromRole()) ||
									role.equals(choice.getToRole())) {
								acts.add(choice);
							}
							return(true);
						}
						
						public boolean start(Run run) {
							if (run.getParameter(role.getName()) != null) {
								acts.add(run);
							}
							return(true);
						}
						
						public void accept(Include elem) {
							if (elem.getParameter(role.getName()) != null) {
								acts.add(elem);
							}
						}
					});
				
					if (acts.size() == 0) {
						list.getRoles().remove(role);
						
						if (list.getRoles().size() == 0) {
							parent.remove(list);
						}
					}
				}
			}
		});
	}
	
	//private static Logger logger = Logger.getLogger("org.scribble.protocol.projector");
}

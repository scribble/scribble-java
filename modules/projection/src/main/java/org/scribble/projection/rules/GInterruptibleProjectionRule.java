/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.projection.rules;

import org.scribble.common.logging.ScribbleLogger;
import org.scribble.common.module.ModuleContext;
import org.scribble.model.Message;
import org.scribble.model.ModelObject;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GInterruptible.Interrupt;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LInterruptible;

/**
 * This class implements the GRecursion projection rule.
 *
 */
public class GInterruptibleProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
								RoleDecl role, ScribbleLogger logger) {
		LInterruptible projected=null;
		GInterruptible source=(GInterruptible)mobj;
		
		if (source.isRoleInvolved(role)) {
			projected = new LInterruptible();
			
			projected.derivedFrom(source);
			
			projected.setScope(source.getScope());
	        
			ProjectionRule rule=ProjectionRuleFactory.getProjectionRule(source.getBlock());
			
			if (rule != null) {
				LBlock lb=(LBlock)rule.project(context, source.getBlock(), role, logger);
				
				if (lb != null) {
					projected.setBlock(lb);
				}
			}
			
			// Get list of roles involved in the interruptible
			java.util.Set<Role> roles=new java.util.HashSet<Role>();			
			source.identifyInvolvedRoles(roles);
			
			// Project throws
			for (Interrupt i : source.getInterrupts()) {
				if (role.isRole(i.getRole())) {
					LInterruptible.Throw lt=new LInterruptible.Throw();
					
					for (Message m : i.getMessages()) {
						Message lm=new Message(m);
						
						lt.getMessages().add(lm);
					}
					
					for (Role r : roles) {
						if (!role.isRole(r)) {
							lt.getToRoles().add(new Role(r));
						}
					}
					
					projected.setThrows(lt);
					
					// As only one throws is permitted
					break;
				}
			}
			
			// Project catches
			// Project throws
			for (Interrupt i : source.getInterrupts()) {
				if (!role.isRole(i.getRole())) {
					LInterruptible.Catch lc=new LInterruptible.Catch();
					
					for (Message m : i.getMessages()) {
						Message lm=new Message(m);
						
						lc.getMessages().add(lm);
					}
					
					lc.setRole(new Role(i.getRole()));
					
					projected.getCatches().add(lc);
				}
			}			
		}
        
        return (projected);
	}
}

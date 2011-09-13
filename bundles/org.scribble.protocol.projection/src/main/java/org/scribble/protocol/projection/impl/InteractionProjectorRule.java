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

import org.scribble.protocol.model.*;
import org.scribble.common.logging.Journal;

/**
 * This class provides the Interaction implementation of the
 * projector rule.
 */
public class InteractionProjectorRule implements ProjectorRule {

	/**
	 * This method determines whether the projection rule is
	 * appropriate for the supplied model object.
	 * 
	 * @param obj The model object to be projected
	 * @return Whether the rule is relevant for the
	 * 				model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == Interaction.class);
	}
	
	/**
	 * This method returns a new instance of the interaction model
	 * object.
	 * 
	 * @return The new interaction
	 */
	protected Interaction createInteraction() {
		return(new Interaction());
	}
	
	/**
	 * This method projects the supplied model object based on the
	 * specified role.<p>
	 * 
	 * @param model The model object
	 * @param role The role
	 * @param l The model listener
	 * @return The projected model object
	 */
	public Object project(ProjectorContext context, ModelObject model,
					Role role, Journal l) {
		Interaction ret=createInteraction();
		Interaction source=(Interaction)model;
		boolean f_roleFound=false;
		
		ret.derivedFrom(source);

		if (source.getFromRole() != null) {
			
			if (source.getFromRole().equals(role)) {				
				f_roleFound = true;
			} else {
				// Only set role if not projected role
				// Find role in state
				Object state=context.getState(source.getFromRole().getName());
				
				if (state instanceof Role) {
					Role r=new Role();
					r.setName(source.getFromRole().getName());
					
					r.derivedFrom(source.getFromRole());
					
					ret.setFromRole(r);
				}
			}
		}
		
		if (source.getToRoles().size() > 0) {
			
			if (source.getToRoles().contains(role)) {
				f_roleFound = true;
			} else {
				for (Role sr : source.getToRoles()) {
					// Only set role if not projected role
					// Find role in state
					Object state=context.getState(sr.getName());
					
					if (state instanceof Role) {
						Role r=new Role();
						r.setName(sr.getName());
						
						r.derivedFrom(sr);
						
						ret.getToRoles().add(r);
					}
				}
			}
		}
		
		// Check if role found
		if (f_roleFound) {
			ret.setMessageSignature((MessageSignature)
					context.project(source.getMessageSignature(),
							role, l));
		} else {
			// Role not associated with the interaction
			ret = null;
		}
		
		return(ret);
	}
}

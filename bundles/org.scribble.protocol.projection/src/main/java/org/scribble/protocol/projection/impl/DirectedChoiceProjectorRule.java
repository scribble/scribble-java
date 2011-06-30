/*
 * Copyright 2009-11 www.scribble.org
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

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.*;

/**
 * This class provides the DirectedChoice implementation of the
 * projector rule.
 */
public class DirectedChoiceProjectorRule implements ProjectorRule {

	/**
	 * This method determines whether the projection rule is
	 * appropriate for the supplied model object.
	 * 
	 * @param obj The model object to be projected
	 * @return Whether the rule is relevant for the
	 * 				model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == DirectedChoice.class);
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
		DirectedChoice ret=new DirectedChoice();
		DirectedChoice source=(DirectedChoice)model;
		
		ret.derivedFrom(source);
		
		if (source.getFromRole() != null && source.getFromRole().equals(role)) {
			for (Role r : source.getToRoles()) {
				ret.getToRoles().add(new Role(r.getName()));
			}
		} else if (source.getFromRole() != null && source.getToRoles().contains(role)) {
			ret.setFromRole(new Role(source.getFromRole()));
		} else {
			// Don't project
			ret = null;
		}

		if (ret != null) {
			for (int i=0; i < source.getOnMessages().size(); i++) {
				OnMessage om=(OnMessage)
						context.project(source.getOnMessages().get(i), role,
								l);
				
				if (om != null) {				
					ret.getOnMessages().add(om);
				}
			}
		}

		return(ret);
	}
}

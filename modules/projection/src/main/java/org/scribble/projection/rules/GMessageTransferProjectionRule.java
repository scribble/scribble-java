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

import org.scribble.context.ModuleContext;
import org.scribble.logging.IssueLogger;
import org.scribble.model.Message;
import org.scribble.model.ModelObject;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.local.LReceive;
import org.scribble.model.local.LSend;

/**
 * This class implements the GMessageTransfer projection rule.
 *
 */
public class GMessageTransferProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
							RoleDecl role, IssueLogger logger) {
		java.util.List<ModelObject> ret=new java.util.ArrayList<ModelObject>();
		GMessageTransfer source=(GMessageTransfer)mobj;
		
		if (role.isRole(source.getFromRole())) {
			LSend projected = new LSend();
			projected.setMessage(new Message(source.getMessage()));
			
			for (Role r : source.getToRoles()) {
				projected.getToRoles().add(new Role(r));
			}
			
			projected.derivedFrom(source);
			
			ret.add(projected);
		}
		
		for (Role r : source.getToRoles()) {
			if (role.isRole(r)) {
				LReceive projected = new LReceive();
				projected.setMessage(new Message(source.getMessage()));
				projected.setFromRole(new Role(source.getFromRole()));
				
				projected.derivedFrom(source);
				
				ret.add(projected);
				break;
			}
		}

		return (ret);
	}
	
}

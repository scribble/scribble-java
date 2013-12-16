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
import org.scribble.model.ParameterDecl;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.local.LProtocolDecl;

/**
 * This class is the abstract base class for the GProtocolDefinition and
 * GProtocolInstance projection rules.
 *
 */
public class AbstractProtocolDeclProjectionRule {

	/**
	 * This method projects the protocol declaration information common to protocol
	 * definitions and instances.
	 * 
	 * @param context The module context
	 * @param elem The global protocol declaration
	 * @param ret The local protocol declaration
	 * @param role The role
	 * @param logger The logger
	 */
	public void projectProtocolDecl(ModuleContext context,
						ProtocolDecl elem, LProtocolDecl ret, RoleDecl role, ScribbleLogger logger) {
		ret.setName(elem.getName());
		ret.setLocalRole(new Role(role.getName()));
		
		for (ParameterDecl pd : elem.getParameterDeclarations()) {
			ret.getParameterDeclarations().add(new ParameterDecl(pd));
		}
		
		for (RoleDecl rd : elem.getRoleDeclarations()) {
			ret.getRoleDeclarations().add(new RoleDecl(rd));
		}
	}
	
}

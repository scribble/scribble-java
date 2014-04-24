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
import org.scribble.model.Argument;
import org.scribble.model.ModelObject;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.RoleDecl;
import org.scribble.model.RoleInstantiation;
import org.scribble.model.global.GDo;
import org.scribble.model.local.LDo;

/**
 * This class implements the GDo projection rule.
 *
 */
public class GDoProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
						RoleDecl role, IssueLogger logger) {
		LDo projected=null;
		GDo source=(GDo)mobj;
		
		if (source.isRoleInvolved(role)) {
			projected = new LDo();
			
			projected.derivedFrom(source);
			
			String protocolName=source.getProtocol();
			
			if (protocolName.indexOf('.') != -1) {
				// Find the role of the target protocol
				RoleInstantiation targetri=source.getRoleInstantiation(role);
				
				if (targetri == null) {
					logger.error("Could not find role '"+role+"'", mobj);
					return null;
				}
				
				String otherRole=targetri.getAlias();
				
				if (otherRole == null) {
					// Find position
					int index=source.getRoleInstantiations().indexOf(targetri);
					
					if (index != -1) {
						ModelObject mo=context.getMember(source.getProtocol());
						
						if (mo instanceof ProtocolDecl) {
							ProtocolDecl pd=(ProtocolDecl)mo;
							
							if (pd.getRoleDeclarations().size() <= index) {
								logger.error("Could not determine target role for '"+role+"'", mobj);
							} else {
								otherRole = pd.getRoleDeclarations().get(index).getName();
							}
						}
					}
				}
				
				if (otherRole != null) {
					// Modify final module part to include role name
					int index=protocolName.lastIndexOf('.');
					
					protocolName = protocolName.substring(0, index) + "_" + otherRole
							+ protocolName.substring(index);
				}
			}
			
			// TODO: Modify the protocol name as described in section 5.3.6
			
			projected.setProtocol(protocolName);
			
			for (RoleInstantiation ri : source.getRoleInstantiations()) {
				projected.getRoleInstantiations().add(new RoleInstantiation(ri));
			}
			
			for (Argument arg : source.getArguments()) {
				projected.getArguments().add(new Argument(arg));
			}
			
			if (source.getScope() != null) {
				projected.setScope(source.getScope());
			} else {
				// TODO: Default scoping name???
			}
		}
		
		return (projected);
	}
	
}

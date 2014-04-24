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
import org.scribble.model.global.GProtocolInstance;
import org.scribble.model.local.LProtocolInstance;

/**
 * This class implements the GProtocolInstance projection rule.
 *
 */
public class GProtocolInstanceProjectionRule extends AbstractProtocolDeclProjectionRule
							implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj, RoleDecl role, IssueLogger logger) {
		LProtocolInstance projected=new LProtocolInstance();
		GProtocolInstance source=(GProtocolInstance)mobj;

		projected.derivedFrom(source);
        
		projectProtocolDecl(context, source, projected, role, logger);
		
		String protocolName=source.getMemberName();
		
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
					ModelObject mo=context.getMember(protocolName);
					
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
		
		projected.setMemberName(protocolName);
		
		for (Argument arg : source.getArguments()) {
			projected.getArguments().add(new Argument(arg));
		}

		for (RoleInstantiation ri : source.getRoleInstantiations()) {
			projected.getRoleInstantiations().add(new RoleInstantiation(ri));
		}
		
		return (projected);
	}
	
}

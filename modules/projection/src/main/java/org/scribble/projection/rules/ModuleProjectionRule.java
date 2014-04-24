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
import org.scribble.model.ImportDecl;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GProtocolInstance;

/**
 * This class implements the Module projection rule.
 *
 */
public class ModuleProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
								RoleDecl role, IssueLogger logger) {
		java.util.Set<Module> ret=new java.util.HashSet<Module>();
		Module source=(Module)mobj;

		java.util.Set<RoleDecl> roles=new java.util.HashSet<RoleDecl>();
		
		// Build list of roles
		for (ProtocolDecl pd : source.getProtocols()) {
			roles.addAll(pd.getRoleDeclarations());
		}
		
		for (RoleDecl rd : roles) {
			
			// Create new module
			Module projected = new Module();
			
			projected.derivedFrom(source);
			
			if (source.getName() != null) {
				projected.setName(source.getName()+"_"+rd.getName());
				
				// TODO: Need to investigate options for best pro
				//projected.getFullyQualifiedName().setName(projected.getFullyQualifiedName().getName()
				//							+"_"+rd.getName());
			}
			
			// Copy imports
			for (ImportDecl imp : source.getImports()) {
				projected.getImports().add(new ImportDecl(imp));
				
				// Modify the import statements
			}
			
			// Copy payload type declarations
			for (PayloadTypeDecl ptd : source.getPayloadTypeDeclarations()) {
				projected.getPayloadTypeDeclarations().add(new PayloadTypeDecl(ptd));
			}
			
			for (ProtocolDecl pd : source.getProtocols()) {
				
				if (pd instanceof GProtocolDefinition || pd instanceof GProtocolInstance) {
					ProtocolDecl gpd=(ProtocolDecl)pd;
					
					if (pd.getRoleDeclarations().contains(rd)) {
						
						ProjectionRule rule=ProjectionRuleFactory.getProjectionRule(gpd);
						
						if (rule != null) {
							ProtocolDecl lpd=(ProtocolDecl)rule.project(context, gpd, rd, logger);
							
							if (lpd != null) {
								projected.getProtocols().add(lpd);
							}
						}
					}
				}
			}
			
			ret.add(projected);
		}

		return (ret);
	}
	
}

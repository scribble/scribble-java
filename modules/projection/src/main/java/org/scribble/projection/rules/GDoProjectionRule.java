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
import org.scribble.model.Argument;
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.ModelObject;
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
						RoleDecl role, ScribbleLogger logger) {
		LDo projected=null;
		GDo source=(GDo)mobj;
		
		if (source.isRoleInvolved(role)) {
			projected = new LDo();
			
			projected.derivedFrom(source);
			
			// TODO: Modify the protocol name as described in section 5.3.6
			
			projected.setProtocol(new FullyQualifiedName(source.getProtocol()));
			
			for (RoleInstantiation ri : source.getRoleInstantiations()) {
				projected.getRoleInstantiations().add(new RoleInstantiation(ri));
			}
			
			for (Argument arg : source.getArguments()) {
				projected.getArguments().add(new Argument(arg));
			}
			
			if (source.getScopeName() != null) {
				projected.setScopeName(source.getScopeName());
			} else {
				// TODO: Default scoping name???
			}
		}
		
		return (projected);
	}
	
}

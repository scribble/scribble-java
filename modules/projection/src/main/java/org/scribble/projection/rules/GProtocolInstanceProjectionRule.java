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
import org.scribble.model.ModelObject;
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
	public Object project(ModuleContext context, ModelObject mobj, RoleDecl role, ScribbleLogger logger) {
		LProtocolInstance projected=new LProtocolInstance();
		GProtocolInstance source=(GProtocolInstance)mobj;

		projected.derivedFrom(source);
        
		projectProtocolDecl(context, source, projected, role, logger);
		
		projected.setMemberName(source.getMemberName());
		
		for (Argument arg : source.getArguments()) {
			projected.getArguments().add(new Argument(arg));
		}

		for (RoleInstantiation ri : source.getRoleInstantiations()) {
			projected.getRoleInstantiations().add(new RoleInstantiation(ri));
		}
		
		return (projected);
	}
	
}

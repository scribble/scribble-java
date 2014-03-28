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
import org.scribble.model.ModelObject;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GContinue;
import org.scribble.model.local.LContinue;

/**
 * This class implements the GContinue projection rule.
 *
 */
public class GContinueProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
							RoleDecl role, IssueLogger logger) {
		LContinue projected=new LContinue();
		GContinue source=(GContinue)mobj;
		
		projected.derivedFrom(source);
		
		projected.setLabel(source.getLabel());
		
		return (projected);
	}
	
}

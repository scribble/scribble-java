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
 * This class provides the Catch implementation of the
 * projector rule.
 */
public class InterruptProjectorRule implements ProjectorRule {

	/**
	 * This method determines whether the projection rule is
	 * appropriate for the supplied model object.
	 * 
	 * @param obj The model object to be projected
	 * @return Whether the rule is relevant for the
	 * 				model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == Interrupt.class);
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
		Interrupt ret=(Interrupt)new Interrupt();
		Interrupt source=(Interrupt)model;
		
		ret.derivedFrom(source);
		
		if (source.getBlock() != null) {
			
			// Project the block
			ret.setBlock((Block)context.project(source.getBlock(),
					role, l));
			ret.getBlock().setParent(ret);
		}
		
		if (ret.getBlock().getContents().size() == 0) {
			// Don't return interrupt, as the block
			// content is not relevant for the projected role
			ret = null;
		}
		
		return(ret);
	}
}

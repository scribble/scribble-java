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
 * This class provides the Block implementation of the
 * projector rule.
 */
public abstract class AbstractBlockProjectorRule implements ProjectorRule {

	/**
	 * This method creates a new block of the appropriate
	 * type.
	 * 
	 * @return The block
	 */
	protected abstract Block createBlock();
	
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
		Block ret=createBlock();
		Block source=(Block)model;
		
		ret.derivedFrom(source);
		
		context.pushState();
		
		for (int i=0; i < source.getContents().size(); i++) {
			Activity act=(Activity)
					context.project(source.getContents().get(i), role,
							l);
			
			if (act != null) {
				
				if (act instanceof Block) {
					// Copy contents
					ret.getContents().addAll(((Block)act).getContents());
				} else {
					ret.getContents().add(act);
				}
			}
		}

		context.popState();
		
		// Only return block if it contains atleast one activity
		if (isFilterOutEmptyContent() && ret.getContents().size() == 0) {
			ret = null;
		}
		
		return(ret);
	}
	
	/**
	 * This method determines whether a block with empty content should be
	 * filtered out.
	 * 
	 * @return Whether an empty block should be filtered out
	 */
	protected boolean isFilterOutEmptyContent() {
		return(true);
	}
}

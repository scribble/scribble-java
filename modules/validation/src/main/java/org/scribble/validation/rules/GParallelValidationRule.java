/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.validation.rules;

import java.text.MessageFormat;
import java.util.Collections;

import org.scribble.context.ModuleContext;
import org.scribble.logging.IssueLogger;
import org.scribble.model.ModelObject;
import org.scribble.model.global.DefaultGVisitor;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GParallel;

/**
 * This class implements the validation rule for the GParallel
 * component.
 *
 */
public class GParallelValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		GParallel elem=(GParallel)mobj;
		
		java.util.List<GMessageTransfer> mts=new java.util.ArrayList<GMessageTransfer>();
		
		for (GBlock subelem : elem.getPaths()) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(subelem);
			
			if (rule != null) {
				rule.validate(context, subelem, logger);
			}
			
			// Build up list of message transfers in this path
			// NOTE: This only checks for message transfers contained in this path,
			// and therefore would not deal with message transfers in an invoked protocol
			final java.util.List<GMessageTransfer> pathMTs=new java.util.ArrayList<GMessageTransfer>();
			
			subelem.visit(new DefaultGVisitor() {
				
			    /**
			     * {@inheritDoc}
			     */
			    public void accept(GMessageTransfer elem) {
			    	if (!pathMTs.contains(elem)) {
			    		pathMTs.add(elem);
			    	}
			    }
			});
			
			// Check for overlap with previously discovered message transfers
			if (!Collections.disjoint(pathMTs, mts)) {
				
				for (GMessageTransfer mt : pathMTs) {
					if (mts.contains(mt)) {
						// Report message transfer in multiple concurrent paths
						logger.error(MessageFormat.format(ValidationMessages.getMessage("INTERACTION_IN_CONCURRENT_PATHS"),
								mt), elem);
					}
				}
			}
			
			mts.addAll(pathMTs);
		}
	}

}

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

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.*;
import org.scribble.protocol.util.InteractionUtil;

/**
 * This class provides the Choice implementation of the
 * projector rule.
 */
public class ChoiceProjectorRule implements ProjectorRule {

	/**
	 * This method determines whether the projection rule is
	 * appropriate for the supplied model object.
	 * 
	 * @param obj The model object to be projected
	 * @return Whether the rule is relevant for the
	 * 				model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == Choice.class);
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
		Choice ret=new Choice();
		Choice source=(Choice)model;
		boolean f_merge=false;
		boolean f_rolesSet=false;
		Role fromRole=null;
		Role toRole=null;
		
		ret.derivedFrom(source);
		
		if (source.getFromRole() != null &&
				source.getFromRole().getName().equals(role.getName()) == false) {
			ret.setFromRole(new Role(source.getFromRole()));
		}
		
		if (source.getToRole() != null &&
				source.getToRole().getName().equals(role.getName()) == false) {
			ret.setToRole(new Role(source.getToRole()));
		}

		// If the roles are not relevant to this projection, then we need to merge the
		// paths to derive a new choice that represents the options valid for this role
		if (ret.getFromRole() != null && ret.getToRole() != null) {
			f_merge = true;
		}

		for (int i=0; i < source.getWhens().size(); i++) {
			When block=(When)
					context.project(source.getWhens().get(i), role,
							l);
			
			if (block != null) {
				ret.getWhens().add(block);

				// Check if block needs to be merged
				if (f_merge) {
					java.util.List<ModelObject> list=
						org.scribble.protocol.util.InteractionUtil.getInitialInteractions(block);
					
					// Remove block
					ret.getWhens().remove(block);
					
					for (ModelObject act : list) {
						MessageSignature ms=InteractionUtil.getMessageSignature(act);
						boolean f_add=true;
						
						for (When wb : ret.getWhens()) {
							MessageSignature wbms=InteractionUtil.getMessageSignature(wb);
							
							if (ms.equals(wbms)) {
								// TODO: Need to check paths for conformance.
								// If conforms, then merge, if not, then error
								l.error("MERGING NOT CURRENTLY SUPPORTED", null);
								
								f_add = false;
							}
						}
						
						if (f_add) {
							
							// Check if roles should be set or matched
							if (f_rolesSet == false) {
								fromRole = InteractionUtil.getFromRole(act);
								toRole = InteractionUtil.getToRole(act);
								
								f_rolesSet = true;
							} else {
								// TODO: Check that roles match
							}
							
							// Add path
							if (act instanceof When) {
								ret.getWhens().add((When)act);
							} else if (act instanceof Interaction) {
								When newwb=new When();
								
								newwb.derivedFrom(act);
								
								newwb.setMessageSignature(new MessageSignature(ms));
								
								// Copy contents of block except the interaction
								Block b=(Block)act.getParent();
								
								for (Activity a : b.getContents()) {
									if (a != act) {
										newwb.getBlock().add(a);
									}
								}
								
								ret.getWhens().add(newwb);
							}
						}
					}
				}
			}
		}
		
		if (f_merge) {
			ret.setFromRole(fromRole);
			ret.setToRole(toRole);
		}
		
		// Check if 'choice' can be simplified to an interaction, if only one path
		if (ret.getWhens().size() == 1) {
			
			// Transform to interaction
			Block b=new Block();
			
			Interaction interaction=new Interaction();
			
			interaction.derivedFrom(ret.getWhens().get(0));
			
			interaction.setMessageSignature(ret.getWhens().get(0).getMessageSignature());
			interaction.setFromRole(ret.getFromRole());
			
			if (ret.getToRole() != null) {
				interaction.getToRoles().add(ret.getToRole());
			}
			
			b.add(interaction);
			
			for (Activity act : ret.getWhens().get(0).getBlock().getContents()) {
				b.add(act);
			}
			
			return(b);
		} else if (ret.getWhens().size() == 0) {
			ret = null;
		}

		return(ret);
	}
}

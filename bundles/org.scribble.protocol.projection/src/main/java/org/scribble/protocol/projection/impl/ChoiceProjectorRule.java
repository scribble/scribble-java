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
		boolean f_optional=false;
		
		ret.derivedFrom(source);
		
		// Only project role if the same as the located role. Otherwise description
		// should just indicate that the decision is being made at another role
		if (source.getRole() != null && source.getRole().equals(role)) {
			ret.setRole(new Role(source.getRole()));
		}
		
		// If the roles are not relevant to this projection, then we need to merge the
		// paths to derive a new choice that represents the options valid for this role
/* TODO: MERGING		
		if ((ret.getRole() != null && ret.getToRole() != null) ||
				(ret.getRole() == null && ret.getToRole() == null)) {
			f_merge = true;
		}
*/

		for (int i=0; i < source.getBlocks().size(); i++) {
			Block block=(Block)
					context.project(source.getBlocks().get(i), role,
							l);
			
			if (block != null) {
				
				// TODO: Temporary fix to merge nested choice
				if (block.getContents().size() == 1 &&
						block.getContents().get(0) instanceof Choice &&
						isSameRole(ret, (Choice)block.getContents().get(0))) {
					ret.getBlocks().addAll(((Choice)block.getContents().get(0)).getBlocks());
				} else {
					ret.getBlocks().add(block);
				}
			} else {
				f_optional = true;
			}
		}
				
		// Check if block needs to be merged
		if (f_merge) {
			Role destination=null;
			
			// Check if initial interactions have same destination
			for (Block block : ret.getBlocks()) {
				
				java.util.List<ModelObject> list=
					org.scribble.protocol.util.InteractionUtil.getInitialInteractions(block);
				for (ModelObject act : list) {
					Role r=InteractionUtil.getToRole(act);
					
					if (destination == null) {
						destination = r;
					} else if (destination.equals(r) == false) {
						f_merge = false;
						break;
					}
				}
				
				if (f_merge == false) {
					 break;
				}
			}
		}
		
		if (f_merge) {
			java.util.List<Block> tmp=new java.util.Vector<Block>(ret.getBlocks());
			
			for (Block block : tmp) {
				java.util.List<ModelObject> list=
					org.scribble.protocol.util.InteractionUtil.getInitialInteractions(block);
				
				// Remove block
				ret.getBlocks().remove(block);
				
				for (ModelObject act : list) {
					MessageSignature ms=InteractionUtil.getMessageSignature(act);
					boolean f_add=true;
					
					for (Block wb : ret.getBlocks()) {
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
						
/* TODO: MERGING						
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
*/
					}
				}
			}
		}
		
		if (f_merge) {
			ret.setRole(fromRole);
		}
		
		// Check if 'choice' can be simplified to an interaction, if only one path
		/*
		if (ret.getBlocks().size() == 1) {
			
			// Check if projecting choice role
			if (ret.getRole() != null) {
				
				// Check if should be optional - if some blocks have
				// not been projected
				if (ret.getBlocks().size() < source.getBlocks().size()) {
					// Add optional block
					ret.getBlocks().add(new Block());
				}
			} else {
				return(ret.getBlocks().get(0));
			}
		} else*/ if (ret.getBlocks().size() == 0) {
			ret = null;
		} else if (f_optional) {
			// Add optional block
			ret.getBlocks().add(new Block());
		}

		return(ret);
	}
	
	protected boolean isSameRole(Choice c1, Choice c2) {
		if (c1.getRole() == null && c2.getRole() == null) {
			return(true);
		} else {
			return(c1.getRole().equals(c2.getRole()));
		}
	}
}

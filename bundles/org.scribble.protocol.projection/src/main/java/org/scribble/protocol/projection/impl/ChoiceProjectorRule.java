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

import java.text.MessageFormat;

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
		Choice projected=new Choice();
		ModelObject ret=projected;
		Choice source=(Choice)model;
		boolean f_merge=false;
		boolean f_rolesSet=false;
		Role fromRole=null;
		boolean f_optional=false;
		
		projected.derivedFrom(source);
		
		// Only project role if the same as the located role. Otherwise description
		// should just indicate that the decision is being made at another role
		if (source.getRole() != null && source.getRole().equals(role)) {
			projected.setRole(new Role(source.getRole()));
		}
		
		for (int i=0; i < source.getBlocks().size(); i++) {
			Block block=(Block)
					context.project(source.getBlocks().get(i), role,
							l);
			
			if (block != null) {
				
				// TODO: Temporary fix to merge nested choice
				if (block.getContents().size() == 1 &&
						block.getContents().get(0) instanceof Choice &&
						isSameRole(projected, (Choice)block.getContents().get(0))) {
					projected.getBlocks().addAll(((Choice)block.getContents().get(0)).getBlocks());
				} else {
					projected.getBlocks().add(block);
				}
			} else {
				f_optional = true;
			}
		}
				
		// Check if block needs to be merged
		if (f_merge) {
			Role destination=null;
			
			// Check if initial interactions have same destination
			for (Block block : projected.getBlocks()) {
				
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
			java.util.List<Block> tmp=new java.util.Vector<Block>(projected.getBlocks());
			
			for (Block block : tmp) {
				java.util.List<ModelObject> list=
					org.scribble.protocol.util.InteractionUtil.getInitialInteractions(block);
				
				// Remove block
				projected.getBlocks().remove(block);
				
				for (ModelObject act : list) {
					MessageSignature ms=InteractionUtil.getMessageSignature(act);
					boolean f_add=true;
					
					for (Block wb : projected.getBlocks()) {
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
							//toRole = InteractionUtil.getToRole(act);
							
							f_rolesSet = true;
						} else {
							// TODO: Check that roles match
						}
					}
				}
			}
		}
		
		if (f_merge) {
			projected.setRole(fromRole);
		}
		
		ret = extractCommonBehaviour(context, projected, role, l);
		
		// Remove all empty paths
		for (int i=projected.getBlocks().size()-1; i >= 0; i--) {
			Block b=projected.getBlocks().get(i);
			
			if (b.size() == 0) {
				projected.getBlocks().remove(i);
				f_optional = true;
			}
		}
		
		if (projected.getBlocks().size() == 0) {
			if (ret == projected) {
				ret = null;
			} else {
				((Block)ret).remove(projected);
			}
			projected = null;
		} else if (f_optional) {
			// Add optional block
			projected.getBlocks().add(new Block());
		}

		return(ret);
	}
	
	protected static ModelObject extractCommonBehaviour(ProjectorContext context, Choice projected,
							Role role, Journal l) {
		ModelObject ret=projected;
		
		// Check to see whether common interaction sentences can be extracted
		// out from each path to precede the choice
		boolean checkPaths=true;
		do {
			boolean allSame=projected.getBlocks().size() > 1;
			
			for (int i=1; allSame && i < projected.getBlocks().size(); i++) {
				Block b1=projected.getBlocks().get(0);
				Block b2=projected.getBlocks().get(i);
				
				if (b1.size() == 0 || b2.size() == 0) {
					allSame = false;
				} else if (b1.get(0).equals(b2.get(0)) == false) {
					allSame = false;
				}
			}
			
			if (allSame) {
				// Merge first elements from each path and place before the choice
				if ((ret instanceof Block) == false) {
					ret = new Block();
					((Block)ret).add(projected);
				}
				
				((Block)ret).getContents().add(((Block)ret).size()-1,
						projected.getBlocks().get(0).getContents().get(0));
				
				for (int i=0; i < projected.getBlocks().size(); i++) {
					// Remove first element
					projected.getBlocks().get(i).getContents().remove(0);
				}
			} else {
				// Check if two or more paths have same first element, and
				// therefore are invalid
				boolean f_invalid=false;
				
				for (int i=0; !f_invalid && i < projected.getBlocks().size(); i++) {
					
					for (int j=0; !f_invalid && j < projected.getBlocks().size(); j++) {
					
						if (i != j) {
							Block b1=projected.getBlocks().get(i);
							Block b2=projected.getBlocks().get(j);
							
							if (b1.size() > 0 && b2.size() > 0 &&
									b1.get(0).equals(b2.get(0))) {
								f_invalid = true;
							}
						}
					}
				}
				
				if (f_invalid) {
					l.error(MessageFormat.format(
							java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.projection.Messages").
								getString("_AMBIGUOUS_CHOICE"),
								role.getName()), null);
				}
				
				checkPaths = false;
			}
			
		} while(checkPaths);
		
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

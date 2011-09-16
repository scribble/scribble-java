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
import java.util.logging.Logger;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.util.InteractionUtil;

/**
 * This class provides the Choice implementation of the
 * projector rule.
 */
public class ChoiceProjectorRule implements ProjectorRule {

    private static final Logger LOG=Logger.getLogger(ChoiceProjectorRule.class.getName());
    
    /**
     * This method determines whether the projection rule is
     * appropriate for the supplied model object.
     * 
     * @param obj The model object to be projected
     * @return Whether the rule is relevant for the
     *                 model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Choice.class);
    }
    
    /**
     * This method projects the supplied model object based on the
     * specified role.
     * 
     * @param context The projector context
     * @param model The model object
     * @param role The role
     * @param l The model listener
     * @return The projected model object
     */
    public Object project(ProjectorContext context, ModelObject model,
                    Role role, Journal l) {
        Choice projected=new Choice();
        Object ret=projected;
        Choice source=(Choice)model;
        boolean merge=false;
        boolean rolesSet=false;
        Role fromRole=null;
        boolean optional=false;
        
        projected.derivedFrom(source);
        
        // Only project role if the same as the located role. Otherwise description
        // should just indicate that the decision is being made at another role
        if (source.getRole() != null && source.getRole().equals(role)) {
            projected.setRole(new Role(source.getRole()));
        }
        
        for (int i=0; i < source.getPaths().size(); i++) {
            Block block=(Block)
                    context.project(source.getPaths().get(i), role,
                            l);
            
            if (block != null) {
                
                // TODO: Temporary fix to merge nested choice
                if (block.getContents().size() == 1
                        && block.getContents().get(0) instanceof Choice
                        && isSameRole(projected, (Choice)block.getContents().get(0))) {
                    projected.getPaths().addAll(((Choice)block.getContents().get(0)).getPaths());
                } else {
                    projected.getPaths().add(block);
                }
            } else {
                optional = true;
            }
        }
                
        // Check if block needs to be merged
        if (merge) {
            Role destination=null;
            
            // Check if initial interactions have same destination
            for (Block block : projected.getPaths()) {
                
                java.util.List<ModelObject> list=
                    org.scribble.protocol.util.InteractionUtil.getInitialInteractions(block);
                for (ModelObject act : list) {
                    Role r=InteractionUtil.getToRole(act);
                    
                    if (destination == null) {
                        destination = r;
                    } else if (!destination.equals(r)) {
                        merge = false;
                        break;
                    }
                }
                
                if (!merge) {
                     break;
                }
            }
        }
        
        if (merge) {
            java.util.List<Block> tmp=new java.util.Vector<Block>(projected.getPaths());
            
            for (Block block : tmp) {
                java.util.List<ModelObject> list=
                    org.scribble.protocol.util.InteractionUtil.getInitialInteractions(block);
                
                // Remove block
                projected.getPaths().remove(block);
                
                for (ModelObject act : list) {
                    MessageSignature ms=InteractionUtil.getMessageSignature(act);
                    boolean add=true;
                    
                    for (Block wb : projected.getPaths()) {
                        MessageSignature wbms=InteractionUtil.getMessageSignature(wb);
                        
                        if (ms.equals(wbms)) {
                            // TODO: Need to check paths for conformance.
                            // If conforms, then merge, if not, then error
                            l.error("MERGING NOT CURRENTLY SUPPORTED", null);
                            
                            add = false;
                        }
                    }
                    
                    if (add) {
                        
                        // Check if roles should be set or matched
                        if (!rolesSet) {
                            fromRole = InteractionUtil.getFromRole(act);
                            //toRole = InteractionUtil.getToRole(act);
                            
                            rolesSet = true;
                        } else {
                            // TODO: Check that roles match
                            LOG.info("TODO: Check that roles match");
                        }
                    }
                }
            }
        }
        
        if (merge) {
            projected.setRole(fromRole);
        }
        
        ret = extractCommonBehaviour(context, projected, role, l);
        
        // Remove all empty paths
        for (int i=projected.getPaths().size()-1; i >= 0; i--) {
            Block b=projected.getPaths().get(i);
            
            if (b.size() == 0) {
                projected.getPaths().remove(i);
                optional = true;
            }
        }
        
        if (projected.getPaths().size() == 0) {
            if (ret == projected) {
                ret = null;
            } else {
                ((java.util.List<?>)ret).remove(projected);
            }
            projected = null;
        } else if (optional) {
            // Add optional block
            projected.getPaths().add(new Block());
        }

        return (ret);
    }
    
    /**
     * Extract common behaviour.
     * 
     * @param context Projector context
     * @param projected The projected choice
     * @param role The role
     * @param l The journal
     * @return Extracted common behaviour
     */
    @SuppressWarnings("unchecked")
    protected static Object extractCommonBehaviour(ProjectorContext context, Choice projected,
                            Role role, Journal l) {
        Object ret=projected;
        
        // Check to see whether common interaction sentences can be extracted
        // out from each path to precede the choice
        boolean checkPaths=true;
        do {
            boolean allSame=projected.getPaths().size() > 1;
            
            for (int i=1; allSame && i < projected.getPaths().size(); i++) {
                Block b1=projected.getPaths().get(0);
                Block b2=projected.getPaths().get(i);
                
                if (b1.size() == 0 || b2.size() == 0) {
                    allSame = false;
                } else if (!b1.get(0).equals(b2.get(0))) {
                    allSame = false;
                }
            }
            
            if (allSame) {
                // Merge first elements from each path and place before the choice
                if (!(ret instanceof java.util.List<?>)) {
                    ret = new java.util.Vector<Object>();
                    ((java.util.List<Object>)ret).add(projected);
                }
                
                ((java.util.List<Object>)ret).add(((java.util.List<Object>)ret).size()-1,
                        projected.getPaths().get(0).getContents().get(0));
                
                for (int i=0; i < projected.getPaths().size(); i++) {
                    // Remove first element
                    projected.getPaths().get(i).getContents().remove(0);
                }
            } else {
                // Check if two or more paths have same first element, and
                // therefore are invalid
                boolean invalid=false;
                
                for (int i=0; !invalid && i < projected.getPaths().size(); i++) {
                    
                    for (int j=0; !invalid && j < projected.getPaths().size(); j++) {
                    
                        if (i != j) {
                            Block b1=projected.getPaths().get(i);
                            Block b2=projected.getPaths().get(j);
                            
                            if (b1.size() > 0 && b2.size() > 0
                                    && b1.get(0).equals(b2.get(0))) {
                                invalid = true;
                            }
                        }
                    }
                }
                
                if (invalid) {
                    l.error(MessageFormat.format(
                            java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.projection.Messages").
                                getString("_AMBIGUOUS_CHOICE"),
                                role.getName()), null);
                }
                
                checkPaths = false;
            }
            
        } while(checkPaths);
        
        return (ret);
    }
    
    /**
     * Check whether roles are the same.
     * 
     * @param c1 First choice
     * @param c2 Second choice
     * @return Whether roles are same
     */
    protected boolean isSameRole(Choice c1, Choice c2) {
        if (c1.getRole() == null && c2.getRole() == null) {
            return (true);
        } else {
            return (c1.getRole().equals(c2.getRole()));
        }
    }
}

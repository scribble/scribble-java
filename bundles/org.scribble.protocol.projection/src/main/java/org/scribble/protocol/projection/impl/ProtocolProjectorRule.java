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

import java.util.logging.Logger;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.DefaultVisitor;
import org.scribble.protocol.model.Inline;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.Introduces;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.ParameterDefinition;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.Run;

/**
 * This class provides the Protocol implementation of the
 * projector rule.
 */
public class ProtocolProjectorRule implements ProjectorRule {

    private static final Logger LOG=Logger.getLogger(ProtocolProjectorRule.class.getName());
    
    /**
     * This method determines whether the projection rule is
     * appropriate for the supplied model object.
     * 
     * @param obj The model object to be projected
     * @return Whether the rule is relevant for the
     *                 model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Protocol.class);
    }
    
    /**
     * This method projects the supplied model object based on the
     * specified role.
     * 
     * @param context The context
     * @param model The model object
     * @param role The role
     * @param l The model listener
     * @return The projected model object
     */
    @SuppressWarnings("unchecked")
    public Object project(ProjectorContext context, ModelObject model,
                    Role role, Journal l) {
        Object ret=null;
        Protocol source=(Protocol)model;
        java.util.List<Role> roles=context.getRolesOfInterestForDefinition(source);
        
        if (context.isOuterScope()
                || roles != null) {

            if (roles == null) {
                roles = new java.util.Vector<Role>();
                roles.add(role);
            }
            
            for (int j=0; j < roles.size(); j++) {

                // Set current role
                role = roles.get(j);
                            
                Protocol prot = startProtocolProjection(context, source, role, l);
                
                prot.setBlock((Block)context.project(source.getBlock(),
                        role, l));
                prot.getBlock().setParent(prot);
                
                // Project nested protocols
                for (Protocol nested : source.getNestedProtocols()) {
                    Object pp=context.project(nested, role, l);
                    
                    if (pp instanceof Protocol) {
                        prot.getNestedProtocols().add((Protocol)pp);
                    } else if (pp instanceof java.util.List) {
                        for (Object obj : (java.util.List<?>)pp) {
                            if (obj instanceof Protocol) {
                                prot.getNestedProtocols().add((Protocol)obj);
                            } else {
                                LOG.severe("Projection of nested protocol returned unexpected component: "+obj);
                            }
                        }
                    }
                }
                
                endProtocolProjection(context, source, prot, role, l);
                
                // Clean up role lists, to ensure they don't include redundant roles
                cleanUpRoles(prot);
                
                if (ret == null) {
                    ret = prot;
                } else if (ret instanceof java.util.List<?>) {
                    ((java.util.List<ModelObject>)ret).add(prot);
                } else {
                    java.util.List<ModelObject> b=new java.util.Vector<ModelObject>();
                    b.add((ModelObject)ret);
                    b.add(prot);
                    ret = b;
                }
            }
        }

        return (ret);
    }
    
    /**
     * This method is called to start the projection of a protocol.
     * 
     * @param context The projection context
     * @param source The source protocol
     * @param role The role
     * @param l The journal
     * @return The projected protocol
     */
    public static Protocol startProtocolProjection(ProjectorContext context, Protocol source,
                            Role role, Journal l) {
        Protocol prot = new Protocol();

        prot.derivedFrom(source);
        
        prot.setName(source.getName());
    
        Role located=(Role)context.project(role, role, l);

        prot.setLocatedRole(located);
        
        context.pushScope();
        
        // Project parameters
        for (ParameterDefinition p : source.getParameterDefinitions()) {
            ParameterDefinition projp=(ParameterDefinition)context.project(p, role, l);
            
            if (projp != null) {
                prot.getParameterDefinitions().add(projp);
            }
        }

        return(prot);
    }
    
    /**
     * This method closes the projection of the protocol.
     * 
     * @param context The projector context
     * @param source The source protocol
     * @param projected The projected protocol
     * @param role The role
     * @param l The journal
     */
    public static void endProtocolProjection(ProjectorContext context, Protocol source,
                                Protocol projected, Role role, Journal l) {
        context.popScope();
    }
    
    /**
     * This method cleans up the roles associated with the supplied protocol.
     * 
     * @param protocol The protocol
     */
    protected void cleanUpRoles(Protocol protocol) {
        
        // Visit protocol to locate role lists
        protocol.visit(new DefaultVisitor() {
            
            public void accept(Introduces list) {
                
                // Identify parent block
                Block parent=(Block)list.getParent();
                
                for (int i=list.getIntroducedRoles().size()-1; i >= 0; i--) {
                    final Role role=(Role)list.getIntroducedRoles().get(i);
                    final java.util.List<Activity> acts=new java.util.Vector<Activity>();
                    
                    // Find out if role is used in a relevant activity
                    parent.visit(new DefaultVisitor() {
                        
                        public void accept(Interaction interaction) {
                            if (role.equals(interaction.getFromRole())
                                    || interaction.getToRoles().contains(role)) {
                                acts.add(interaction);
                            }
                        }
                        
                        public boolean start(Choice choice) {
                            if (role.equals(choice.getRole())) {
                                acts.add(choice);
                            }
                            return (true);
                        }
                        
                        public void accept(Run run) {
                            if (run.getParameter(role.getName()) != null) {
                                acts.add(run);
                            }
                        }
                        
                        public void accept(Inline elem) {
                            if (elem.getParameter(role.getName()) != null) {
                                acts.add(elem);
                            }
                        }
                    });
                
                    if (acts.size() == 0) {
                        list.getIntroducedRoles().remove(role);
                        
                        if (list.getIntroducedRoles().size() == 0) {
                            parent.remove(list);
                        }
                    }
                }
            }
        });
    }
    
    //private static Logger logger = Logger.getLogger("org.scribble.protocol.projector");
}

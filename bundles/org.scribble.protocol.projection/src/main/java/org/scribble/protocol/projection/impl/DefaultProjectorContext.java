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
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.validation.ProtocolValidationManager;

/**
 * This class represents the default projection context.
 */
public class DefaultProjectorContext implements ProtocolProjectorContext {
    
    private static Logger logger = Logger.getLogger(DefaultProjectorContext.class.getName());

    private final static java.util.List<ProjectorRule> RULES=new java.util.Vector<ProjectorRule>();
    
    private ProtocolContext _context=null;
    private ProtocolValidationManager _pvm=null;
    private Scope _scope=new Scope();
    private java.util.List<Scope> _scopeStack=new java.util.Vector<Scope>();
    private java.util.Map<Protocol,java.util.List<Role>> _definitionRoleMap=
                    new java.util.HashMap<Protocol,java.util.List<Role>>();

    static {
        RULES.add(new BlockProjectorRule());
        RULES.add(new InterruptProjectorRule());
        RULES.add(new ChoiceProjectorRule());
        RULES.add(new DirectedChoiceProjectorRule());
        RULES.add(new OnMessageProjectorRule());
        RULES.add(new ProtocolImportListProjectorRule());
        RULES.add(new ProtocolImportProjectorRule());
        RULES.add(new TypeImportListProjectorRule());
        RULES.add(new TypeImportProjectorRule());
        RULES.add(new InteractionProjectorRule());
        RULES.add(new MessageSignatureProjectorRule());
        RULES.add(new ParallelProjectorRule());
        RULES.add(new IntroducesProjectorRule());
        RULES.add(new RoleProjectorRule());
        RULES.add(new ProtocolModelProjectorRule());
        RULES.add(new ProtocolProjectorRule());
        RULES.add(new ParameterProjectorRule());
        RULES.add(new ProtocolReferenceProjectorRule());
        RULES.add(new RepeatProjectorRule());
        RULES.add(new RecBlockProjectorRule());
        RULES.add(new RecursionProjectorRule());
        RULES.add(new RunProjectorRule());
        RULES.add(new InlineProjectorRule());
        RULES.add(new DoProjectorRule());
        RULES.add(new DataTypeProjectorRule());
        RULES.add(new TypeReferenceProjectorRule());
        RULES.add(new UnorderedProjectorRule());
        RULES.add(new EndProjectorRule());
    }

    /**
     * This is the default constructor for the projection context.
     * 
     * @param context The protocol context
     * @param pvm The protocol validation manager
     */
    public DefaultProjectorContext(ProtocolContext context, ProtocolValidationManager pvm) {
        _context = context;
        _pvm = pvm;
    }
    
    /**
     * This method returns the protocol context.
     * 
     * @return The protocol context
     */
    public ProtocolContext getProtocolContext() {
        return (_context);
    }
    
    /**
     * This method returns the protocol validation manager.
     * 
     * @return The protocol validation manager
     */
    public ProtocolValidationManager getProtocolValidationManager() {
        return (_pvm);
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
    public Object project(ModelObject model, Role role,
                        Journal l) {
        Object ret=null;
        
        for (int i=0; model != null && ret == null && i < getRules().size(); i++) {
            if (getRules().get(i).isSupported(model)) {
                ret = getRules().get(i).project(this, model, role,
                                l);
            }
        }
                
        return (ret);
    }
        
    /**
     * This method returns a list of projection rules.
     * 
     * @return The list of projection rules
     */
    public java.util.List<ProjectorRule> getRules() {
        return (RULES);
    }
    
    /**
     * This method returns the named state from the current
     * scope.
     * 
     * @param name The state name
     * @return The state value, or null if not found
     */
    public Object getState(String name) {
        return (_scope.getState(name));
    }
    
    /**
     * This method sets the value associated with the supplied
     * name in the current state scope.
     * 
     * @param name The state name
     * @param value The state value
     */
    public void setState(String name, Object value) {
        _scope.setState(name, value);
    }

    /**
     * This method pushes the current state onto a stack.
     */
    public void pushState() {
        _scope.pushState();
    }
    
    /**
     * This method pops the current state from the stack.
     */
    public void popState() {
        _scope.popState();
    }
        
    /**
     * This method pushes the current scope onto a stack.
     */
    public void pushScope() {
        _scopeStack.add(0, _scope);
        _scope = new Scope();
    }
    
    /**
     * This method pops the current scope from the stack.
     */
    public void popScope() {
        if (_scopeStack.size() > 0) {
            _scope = _scopeStack.remove(0);
        } else {
            logger.severe("No state entry to pop from stack");
        }
    }
    
    /**
     * This method determines whether the context is associated
     * with the outer scope.
     * 
     * @return Whether the context is for the outer scope
     */
    public boolean isOuterScope() {
        return (_scopeStack.size() < 1);
    }

    /**
     * This method registers an interest in the projected role
     * associated with the supplied definition.
     * 
     * @param defn The definition
     * @param role The projected role of interest
     */
    public void registerInterest(Protocol defn, Role role) {
        java.util.List<Role> roles=_definitionRoleMap.get(defn);
        
        if (roles == null) {
            roles = new java.util.Vector<Role>();
            _definitionRoleMap.put(defn, roles);
        }
        
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }
    
    /**
     * This method returns the list of roles of interest
     * for the supplied definition.
     * 
     * @param defn The definition
     * @return The list of roles, or null if no roles have
     *                 registered interest in the definition
     */
    public java.util.List<Role> getRolesOfInterestForDefinition(Protocol defn) {
        return (_definitionRoleMap.get(defn));
    }
}

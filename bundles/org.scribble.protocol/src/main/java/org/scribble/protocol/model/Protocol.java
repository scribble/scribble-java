/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.model;

/**
 * This class represents the protocol notation.
 */
public class Protocol extends ModelObject {
    
    private String _name=null;
    private Role _locatedRole=null;
    private Block _block=null;
    private java.util.List<Protocol> _nestedProtocols=
            new ContainmentList<Protocol>(this, Protocol.class);
    private java.util.List<ParameterDefinition> _parameterDefs=
        new ContainmentList<ParameterDefinition>(this, ParameterDefinition.class);

    /**
     * The default constructor.
     */
    public Protocol() {
    }
    
    /**
     * This method returns the name.
     * 
     * @return The name
     */
    public String getName() {
        return (_name);
    }
    
    /**
     * This method sets the name.
     * 
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }
    
    /**
     * This method returns the located role. This
     * field is set when the protocol represents a local
     * model.
     * 
     * @return The located role
     */
    public Role getLocatedRole() {
        return (_locatedRole);
    }
    
    /**
     * This method sets the located role. This
     * field is set when the protocol represents a local
     * model.
     * 
     * @param role The located role
     */
    public void setLocatedRole(Role role) {
        
        if (_locatedRole != null) {
            _locatedRole.setParent(null);
        }
        
        _locatedRole = role;
        
        if (_locatedRole != null) {
            _locatedRole.setParent(this);
        }
    }
    
    /**
     * This method returns the parameters associated with
     * the protocol.
     * 
     * @return The parameter definitions
     */
    public java.util.List<ParameterDefinition> getParameterDefinitions() {
        return (_parameterDefs);
    }
    
    /**
     * This method returns the parameter definition associated with the
     * supplied name.
     * 
     * @param name The name
     * @return The parameter definition, or null if not found
     */
    public ParameterDefinition getParameterDefinition(String name) {
        ParameterDefinition ret=null;
        
        for (ParameterDefinition pd : getParameterDefinitions()) {
            if (pd.getName().equals(name)) {
                ret = pd;
                break;
            }
        }
        
        return (ret);
    }
    
    /**
     * This method returns the block of activities associated
     * with the definition.
     * 
     * @return The block of activities
     */
    public Block getBlock() {
        
        if (_block == null) {
            _block = new Block();
            _block.setParent(this);
        }
        
        return (_block);
    }
    
    /**
     * This method sets the block of activities associated
     * with the definition.
     * 
     * @param block The block of activities
     */
    public void setBlock(Block block) {
        if (_block != null) {
            _block.setParent(null);
        }
        
        _block = block;
        
        if (_block != null) {
            _block.setParent(this);
        }
    }
    
    /**
     * This method returns the list of nested protocols.
     * 
     * @return The nested protocols
     */
    public java.util.List<Protocol> getNestedProtocols() {
        return (_nestedProtocols);
    }
    
    /**
     * This method returns the model in which this definition
     * is contained.
     * 
     * @return The model, or null if not contained within
     *                     a model
     */
    public ProtocolModel getModel() {
        ProtocolModel ret=null;
        ModelObject cur=this;
        
        while (ret == null && cur != null) {
            if (cur instanceof ProtocolModel) {
                ret = (ProtocolModel) cur;
            } else {
                cur = cur.getParent();
            }
        }
        
        return (ret);
    }
    
    /**
     * This method returns the protocol in which this
     * activity is contained.
     * 
     * @return The protocol, or null if not found
     */
    public Protocol getEnclosingProtocol() {
        return (this);
    }
    
    /**
     * This method returns the top level protocol.
     * 
     * @return The top level protocol
     */
    public Protocol getTopLevelProtocol() {
        Protocol ret=this;
        
        if (getParent() instanceof Protocol) {
            ret = ((Protocol)getParent()).getTopLevelProtocol();
        }
        
        return (ret);
    }
    
    /**
     * This method returns the nested-protocol associated
     * with the supplied name.
     * 
     * @param name The name
     * @return The nested-protocol for the supplied name,
     *                 or null if not found
     */
    public Protocol getNestedProtocol(String name) {
    
        for (Protocol protocol : _nestedProtocols) {
            if (protocol.getName().equals(name)) {
                return (protocol);
            }
        }
        
        return (null);
    }
    
    /**
     * This method returns the list of roles defined within
     * the protocol definition.
     * 
     * @return The list of roles
     */
    public java.util.List<Role> getRoles() {
        final java.util.List<Role> ret=new java.util.Vector<Role>();
        
        for (ParameterDefinition p : getParameterDefinitions()) {
            if (p.isRole()) {
                ret.add(p.getRole());
            }
        }
        
        visit(new DefaultVisitor() {
            
            public boolean start(Protocol elem) {
                return (Protocol.this == elem);
            }
            
            public void accept(Introduces elem) {
                ret.addAll(elem.getIntroducedRoles());
            }

        });
        
        return (ret);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        
        if (visitor.start(this)) {
        
            if (getBlock() != null) {
                getBlock().visit(visitor);
            }
            
            for (Protocol p : getNestedProtocols()) {
                p.visit(visitor);
            }
        }
        
        visitor.end(this);
    }
}

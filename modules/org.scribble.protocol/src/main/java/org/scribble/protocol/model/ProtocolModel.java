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
 * This class represents the base class for models associated with
 * specific notations. The details associated with the notation are
 * contained within derived classes.
 *
 */
public class ProtocolModel extends ModelObject {
    
    private Protocol _definition=null;
    private java.util.List<ImportList> _imports=
                new ContainmentList<ImportList>(this, ImportList.class);

    /**
     * The default constructor for the model.
     */
    public ProtocolModel() {
    }
    
    /**
     * This method returns the list of import definitions.
     * 
     * @return The import definitions
     */
    public java.util.List<ImportList> getImports() {
        return (_imports);
    }
    
    /**
     * This method returns the definition associated with
     * this model.
     * 
     * @return The definition
     */
    public Protocol getProtocol() {
        return (_definition);
    }
    
    /**
     * This method set the definition associated with the
     * model.
     * 
     * @param defn The definition
     */
    public void setProtocol(Protocol defn) {
        if (_definition != null) {
            _definition.setParent(null);
        }
        
        _definition = defn;
        
        if (_definition != null) {
            _definition.setParent(this);
        }
    }
    
    /**
     * This method determines whether the model is located.
     * 
     * @return Whether the model is located
     */
    public boolean isLocated() {
        boolean ret=false;
        
        if (getProtocol() != null) {
            ret = (getProtocol().getLocatedRole() != null);
        }
        
        return (ret);
    }
    
    /**
     * This method returns the distinct list of roles defined
     * in the protocol and its nested-protocols.
     * 
     * @return The list of projectable roles
     */
    public java.util.List<Role> getRoles() {
        final java.util.List<Role> ret=new java.util.Vector<Role>();
        
        visit(new DefaultVisitor() {
            
            public boolean start(Protocol elem) {
                
                for (Role r : elem.getRoles()) {
                    if (!ret.contains(r)) {
                        ret.add(r);
                    }
                }
                
                return (true);
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
        
        for (ImportList imp : getImports()) {
            imp.visit(visitor);
        }
        
        if (getProtocol() != null) {
            getProtocol().visit(visitor);
        }
    }
}

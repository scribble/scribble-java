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
public abstract class ProtocolDecl extends ModelObject {
    
    private String _name=null;
    private java.util.List<RoleDecl> _roleDecls=new java.util.ArrayList<RoleDecl>();
    private java.util.List<ParameterDecl> _parameterDecls=new java.util.ArrayList<ParameterDecl>();

    /**
     * The default constructor.
     */
    public ProtocolDecl() {
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
     * This method returns the role declarations associated with
     * the protocol.
     * 
     * @return The role declarations
     */
    public java.util.List<RoleDecl> getRoleDeclarations() {
        return (_roleDecls);
    }
    
    /**
     * This method returns the parameter declarations associated with
     * the protocol.
     * 
     * @return The parameter declarations
     */
    public java.util.List<ParameterDecl> getParameterDeclarations() {
        return (_parameterDecls);
    }
    
    /**
     * This method returns the model in which this definition
     * is contained.
     * 
     * @return The model, or null if not contained within
     *                     a model
     */
    public Module getModule() {
        Module ret=null;
        ModelObject cur=this;
        
        while (ret == null && cur != null) {
            if (cur instanceof Module) {
                ret = (Module) cur;
            } else {
                cur = cur.getParent();
            }
        }
        
        return (ret);
    }
    
}

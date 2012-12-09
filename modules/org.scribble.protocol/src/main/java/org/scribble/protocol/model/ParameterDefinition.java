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
package org.scribble.protocol.model;

/**
 * This class represents a parameter.
 * 
 */
public class ParameterDefinition extends ModelObject {

    private String _name=null;    
    private TypeReference _type=null;

    /**
     * This is the default constructor.
     */
    public ParameterDefinition() {
    }
    
    /**
     * This constructor initializes the parameter as representing
     * a role with the supplied name.
     * 
     * @param name The name
     */
    public ParameterDefinition(String name) {
        _name = name;
    }
    
    /**
     * This method returns the name of the parameter.
     * 
     * @return The name
     */
    public String getName() {
        return (_name);
    }
    
    /**
     * This method sets the name of the parameter.
     * 
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }
    
    /**
     * This method returns the type of the parameter. If
     * the type is null, then it means the parameter
     * name represents a role.
     * 
     * @return The type
     */
    public TypeReference getType() {
        return (_type);
    }
    
    /**
     * This method sets the type of the parameter.
     * 
     * @param type The type
     */
    public void setType(TypeReference type) {
        _type = type;
    }
    
    /**
     * This method determines whether the parameter represents a role.
     * 
     * @return Whether the parameter represents a role
     */
    public boolean isRole() {
        return (_type == null);
    }
    
    /**
     * This method returns the role associated with the parameter,
     * if the parameter represents a role, otherwise null.
     * 
     * @return The role, or null if parameter does not represent a role
     */
    public Role getRole() {
        Role ret=null;
        
        if (isRole() && getName() != null) {
            ret = new Role(getName());
            ret.setParent(this);
        }
        
        return (ret);
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ret=false;
    
        if (obj instanceof ParameterDefinition) {
            ParameterDefinition other=(ParameterDefinition) obj;
            
            ret = true;
            
            if (other.getName() == null || !other.getName().equals(_name)) {
                ret = false;
            } else if (other.getType() == null) {
                if (getType() != null) {
                    ret = false;
                }
            } else if (getType() == null || !other.getType().equals(getType())) {
                ret = false;
            }
        }
        
        return (ret);
    }
    
    @Override
    public int hashCode() {
        int ret=super.hashCode();
        
        if (_name != null) {
            ret = _name.hashCode();
        }
        
        return (ret);
    }
    
    @Override
    public String toString() {
        String ret=getName();
        
        if (ret == null) {
            ret = "<Unnamed Role>";
        }
        
        return (ret);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
    }
}

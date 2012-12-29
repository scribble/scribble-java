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
 * This class represents a role.
 * 
 */
public class RoleDefn extends ModelObject {
    
    private String _name=null;    

    /**
     * This is the default constructor.
     */
    public RoleDefn() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param role The role
     */
    public RoleDefn(RoleDefn role) {
        super(role);
        _name = role.getName();
    }
    
    /**
     * This constructor initializes the role with a name.
     * 
     * @param roleName The role name
     */
    public RoleDefn(String roleName) {
        _name = roleName;
    }
    
    /**
     * This method returns the name of the role.
     * 
     * @return The name
     */
    public String getName() {
        return (_name);
    }
    
    /**
     * This method sets the name of the role.
     * 
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ret=false;
    
        if (obj instanceof RoleDefn) {
            RoleDefn other=(RoleDefn)obj;
            
            if (other.getName() != null && other.getName().equals(_name)) {
                ret = true;
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
    
	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		if (_name != null) {
			buf.append(_name);
		}
	}

}

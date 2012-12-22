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
 * This class represents a role instantiation.
 * 
 */
public class RoleInstantiation extends ModelObject {
    
    private Role _role=null;    
    private Role _as=null;    

    /**
     * This is the default constructor.
     */
    public RoleInstantiation() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param role The role
     */
    public RoleInstantiation(RoleInstantiation role) {
        super(role);
        _role = role.getRole();
        _as = role.getAs();
    }
    
    /**
     * This method sets the role.
     * 
     * @param role The role
     */
    public void setRole(Role role) {
        _role = role;
    }
    
    /**
     * This method returns the role.
     * 
     * @return The role
     */
    public Role getRole() {
        return (_role);
    }
    
    /**
     * This method sets the 'as' role.
     * 
     * @param as The 'as' role
     */
    public void setAs(Role as) {
        _as = as;
    }
    
    /**
     * This method returns the 'as' role.
     * 
     * @return The 'as' role
     */
    public Role getAs() {
        return (_as);
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ret=false;
    
        if (obj instanceof RoleInstantiation) {
            RoleInstantiation other=(RoleInstantiation)obj;
            
            if (other.getRole() != null && other.getRole().equals(_role)
            		&& other.getAs() != null && other.getAs().equals(_as)) {
                ret = true;
            }
        }
        
        return (ret);
    }
    
    @Override
    public int hashCode() {
        int ret=super.hashCode();
        
        if (_role != null) {
            ret = _role.hashCode();
        }
        
        return (ret);
    }
    
    @Override
    public String toString() {
        String ret=getRole()==null?null:getRole().getName();
        
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
		if (_role != null) {
			_role.toText(buf, level);
		}
		
		if (_as != null) {
			buf.append(" as ");
			_as.toText(buf, level);
		}
	}
}

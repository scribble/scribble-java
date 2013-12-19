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
package org.scribble.model.global;

import org.scribble.model.Role;
import org.scribble.model.RoleDecl;

/**
 * This class represents a custom activity.
 * 
 */
public class GCustomActivity extends GActivity {

    private java.util.List<String> _roles=new java.util.Vector<String>();
    
    /**
     * This is the default constructor.
     * 
     */
    public GCustomActivity() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param act The custom activity to copy
     */
    public GCustomActivity(GCustomActivity act) {
        super(act);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isRoleInvolved(RoleDecl role) {
    	boolean ret=false;
    	
    	for (int i=0; !ret && i < _roles.size(); i++) {
    		Role r=new Role(_roles.get(i));
    		ret = role.isRole(r);
    	}
    	
    	return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public void identifyInvolvedRoles(java.util.Set<Role> roles) {
    	for (int i=0; i < _roles.size(); i++) {
   			roles.add(new Role(_roles.get(i)));
    	}
    }

    /**
     * This method returns the roles associated with the custom activity.
     * 
     * @return The roles
     */
    public java.util.List<String> getRoles() {
        return(_roles);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(GVisitor visitor) {
    	visitor.accept(this);
    }
    
	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("custom at ");
    	
    	for (int i=0; i < _roles.size(); i++) {
    		if (i > 0) {
    			buf.append(",");
    		}
    		buf.append(_roles.get(i));
    	}
    	
		buf.append(";\n");
	}
}

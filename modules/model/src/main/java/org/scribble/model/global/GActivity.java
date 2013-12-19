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
package org.scribble.model.global;

import org.scribble.model.ModelObject;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.Visitor;

/**
 * This class represents the base class for all Scribble definition
 * context.
 */
public abstract class GActivity extends ModelObject {
    
    /**
     * The default constructor.
     */
    public GActivity() {
    }
    
    /**
     * The copy constructor.
     * 
     * @param act The activity
     */
    public GActivity(GActivity act) {
        super(act);
    }
    
    /**
     * This method determines whether the supplied role is 'involved'
     * in this global activity.
     * 
     * @param r The role
     * @return Whether the role is involved
     */
    public abstract boolean isRoleInvolved(RoleDecl role);
    
    /**
     * This method builds up the set of roles involved in the global
     * activity.
     * 
     * @param roles The set of involved roles
     */
    public abstract void identifyInvolvedRoles(java.util.Set<Role> roles);
    
    /**
     * {@inheritDoc}
     */
    public void visit(Visitor visitor) {
    	if (visitor instanceof GVisitor) {
    		visit((GVisitor)visitor);
    	}
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public abstract void visit(GVisitor visitor);
    
}

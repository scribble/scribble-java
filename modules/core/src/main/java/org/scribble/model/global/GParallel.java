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

import org.scribble.model.ContainmentList;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;

/**
 * This class represents the Parallel construct with
 * two or more concurrent paths.
 * 
 */
public class GParallel extends GMultiPathActivity {
    
    private java.util.List<GBlock> _blocks=new ContainmentList<GBlock>(this, GBlock.class);

    /**
     * This is the default constructor.
     * 
     */
    public GParallel() {
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isRoleInvolved(RoleDecl role) {
    	boolean ret=false;
    	
    	for (int i=0; !ret && i < _blocks.size(); i++) {
    		ret = _blocks.get(i).isRoleInvolved(role);
    	}
    	
    	return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public void identifyInvolvedRoles(java.util.Set<Role> roles) {
    	
    	for (GBlock b : _blocks) {
    		b.identifyInvolvedRoles(roles);
    	}
    }

    /**
     * This method returns the list of concurrent
     * activity blocks that comprise the multi-path construct.
     * 
     * @return The list of concurrent paths
     */
    public java.util.List<GBlock> getPaths() {
        return (_blocks);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(GVisitor visitor) {
        if (visitor.start(this)) {
        
            for (GBlock b : getPaths()) {
                b.visit(visitor);
            }
        }
        
        visitor.end(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GParallel that = (GParallel) o;

        return _blocks.equals(that._blocks);
    }

    @Override
    public int hashCode() {
        int result = _blocks.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String result =  "parallel ";
        for (GBlock b : _blocks) {
            if (_blocks.indexOf(b) > 0) {
                result += "and ";
            }
            result += b + "\n";
        }
        return result;
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("par ");
    	
    	for (int i=0; i < getPaths().size(); i++) {
    		if (i > 0) {
    			buf.append(" and ");
    		}
    		getPaths().get(i).toText(buf, level);
    	}
    	
		buf.append("\n");
	}
}

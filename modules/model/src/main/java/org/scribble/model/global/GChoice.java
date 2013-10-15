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

/**
 * This class represents the Choice construct between
 * two or more paths.
 * 
 */
public class GChoice extends GActivity {

    private Role _role=null;
    private java.util.List<GBlock> _blocks=new ContainmentList<GBlock>(this, GBlock.class);

    /**
     * This is the default constructor.
     * 
     */
    public GChoice() {
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
     * This method sets the role.
     * 
     * @param role The role
     */
    public void setRole(Role role) {
        _role = role;
    }
    
    /**
     * This method returns the list of mutually exclusive
     * activity blocks that comprise the multi-path construct.
     * 
     * @return The list of choice paths
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

        GChoice that = (GChoice) o;

        return !(_role != null
                ? !_role.equals(that._role)
                : that._role != null)
             && _blocks.equals(that._blocks);
    }

    @Override
    public int hashCode() {
        int result = _blocks.hashCode();
        result = 31 * result + (_role != null ? _role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String result =  "choice ";
        if (_role != null) {
            result += "at " + _role+" ";
        }
        for (GBlock b : _blocks) {
            if (_blocks.indexOf(b) > 0) {
                result += "or ";
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
    	
    	buf.append("choice at ");
    	
    	if (_role != null) {
    		buf.append(_role);
    		buf.append(" ");
    	}
    	
    	for (int i=0; i < getPaths().size(); i++) {
    		if (i > 0) {
    			buf.append(" or ");
    		}
    		getPaths().get(i).toText(buf, level);
    	}
    	
		buf.append("\n");
	}
}

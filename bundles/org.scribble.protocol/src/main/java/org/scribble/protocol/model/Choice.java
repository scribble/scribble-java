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
 * This class represents the Choice construct between
 * two or more paths.
 * 
 */
public class Choice extends Activity {

    private Role _role=null;
    private java.util.List<Block> _blocks=new ContainmentList<Block>(this, Block.class);

    /**
     * This is the default constructor.
     * 
     */
    public Choice() {
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
     * @param part The role
     */
    public void setRole(Role part) {
        _role = part;
    }
    
    /**
     * This method returns the list of mutually exclusive
     * activity blocks that comprise the multi-path construct.
     * 
     * @return The list of choice paths
     */
    public java.util.List<Block> getPaths() {
        return (_blocks);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        visitor.start(this);
        
        if (getRole() != null) {
            getRole().visit(visitor);
        }
        
        for (Block b : getPaths()) {
            b.visit(visitor);
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

        Choice that = (Choice) o;

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
        for (Block b : _blocks) {
            if (_blocks.indexOf(b) > 0) {
                result += "or ";
            }
            result += b + "\n";
        }
        return result;
    }
}

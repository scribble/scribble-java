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

	private Role m_role=null;
	private java.util.List<Block> m_blocks=new ContainmentList<Block>(this, Block.class);

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
		return(m_role);
	}
	
	/**
	 * This method sets the role.
	 * 
	 * @param part The role
	 */
	public void setRole(Role part) {
		m_role = part;
	}
	
	/**
	 * This method returns the list of mutually exclusive
	 * activity blocks that comprise the multi-path construct.
	 * 
	 * @return The list of choice paths
	 */
	public java.util.List<Block> getPaths() {
		return(m_blocks);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Choice that = (Choice) o;

        return !(m_role != null
                ? !m_role.equals(that.m_role)
                : that.m_role != null)
             && m_blocks.equals(that.m_blocks);
    }

    @Override
    public int hashCode() {
        int result = m_blocks.hashCode();
        result = 31 * result + (m_role != null ? m_role.hashCode() : 0);
        return result;
    }

	@Override
	public String toString() {
		String result =  "choice ";
		if (m_role != null) result += "at " + m_role+" ";
		for (Block b: m_blocks) {
			if (m_blocks.indexOf(b) > 0) {
				result += "or ";
			}
			result += b + "\n";
		}
		return result;
	}
}

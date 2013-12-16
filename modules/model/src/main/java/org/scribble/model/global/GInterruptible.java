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

import org.scribble.model.ContainmentList;
import org.scribble.model.Message;
import org.scribble.model.ModelObject;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.Visitor;

/**
 * This class represents the interruptible construct.
 * 
 */
public class GInterruptible extends GActivity {

    private String _scope=null;
    private GBlock _block=new GBlock();
    private java.util.List<Interrupt> _interrupts=new ContainmentList<Interrupt>(this, Interrupt.class);

    /**
     * This is the default constructor.
     * 
     */
    public GInterruptible() {
        _block.setParent(this);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isRoleInvolved(RoleDecl role) {
    	boolean ret=_block.isRoleInvolved(role);
    	
    	for (int i=0; !ret && i < _interrupts.size(); i++) {
    		if (_interrupts.get(i).getRole() != null) {
    			ret = role.isRole(_interrupts.get(i).getRole());
    		}
    	}
    	
    	return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public void identifyInvolvedRoles(java.util.List<Role> roles) {
    	_block.identifyInvolvedRoles(roles);

    	for (int i=0; i < _interrupts.size(); i++) {
    		if (_interrupts.get(i).getRole() != null &&
    				!roles.contains(_interrupts.get(i).getRole())) {
    			roles.add(_interrupts.get(i).getRole());
    		}
    	}
    }

    /**
     * This method returns the scope name.
     * 
     * @return The scope name
     */
    public String getScope() {
        return (_scope);
    }
    
    /**
     * This method sets the scope name.
     * 
     * @param scope The scope name
     */
    public void setScope(String scope) {
        _scope = scope;
    }
        
    /**
     * This method returns the activities.
     * 
     * @return The block of activities
     */
    public GBlock getBlock() {
        return (_block);
    }
    
    /**
     * This method sets the block.
     * 
     * @param block The block
     */
    public void setBlock(GBlock block) {
        if (_block != null) {
            _block.setParent(null);
        }
        
        _block = block;
        
        if (_block != null) {
            _block.setParent(this);
        }
    }

	/**
	 * This method sets the interrupts.
	 * 
	 * @param interrupts The interrupts
	 */
	public void setInterrupts(java.util.List<Interrupt> interrupts) {
		_interrupts = interrupts;
	}
	
	/**
	 * This method gets the interrupts.
	 * 
	 * @return The interrupts
	 */
	public java.util.List<Interrupt> getInterrupts() {
		return (_interrupts);
	}

	/**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(GVisitor visitor) {
	    if (visitor.start(this)) {
	    
	        if (getBlock() != null) {
	            getBlock().visit(visitor);
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

        GInterruptible that = (GInterruptible) o;

        return !(_scope != null
                ? !_scope.equals(that._scope)
                : that._scope != null)
            && !(_block != null
                ? !_block.equals(that._block)
                : that._block != null);
    }

    @Override
    public int hashCode() {
        int result = _scope != null ? _scope.hashCode() : 0;
        return 31 * result + (_block != null ? _block.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "interruptible "+_scope+" "+_block;
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("interruptible ");
    	
    	if (_scope != null) {
    		buf.append(_scope);
    		buf.append(": ");
    	}
    	
    	if (_block != null) {
    		_block.toText(buf, level);
    	}
    	
    	buf.append(" with {\n");
    	
    	for (Interrupt i : getInterrupts()) {
    		i.toText(buf, level+1);
    	}
    	
    	indent(buf, level);

    	buf.append("}\n");
	}
    
    /**
     * This class represents the interrupt definition.
     * 
     */
    public static class Interrupt extends ModelObject {
    	
    	private Role _role=null;
    	private java.util.List<Message> _messages=new ContainmentList<Message>(this, Message.class);
    	
    	public Interrupt() {
    	}
       	
    	/**
    	 * This method sets the 'by' role.
    	 * 
    	 * @param role The 'by' role
    	 */
    	public void setRole(Role role) {
    		_role = role;
    	}
       	
    	/**
    	 * This method gets the 'by' role.
    	 * 
    	 * @return The 'by' role
    	 */
    	public Role getRole() {
    		return (_role);
    	}
    	
    	/**
    	 * This method sets the interrupt messages.
    	 * 
    	 * @param mesgs The messages
    	 */
    	public void setMessages(java.util.List<Message> mesgs) {
    		_messages = mesgs;
    	}
    	
    	/**
    	 * This method gets the interrupt messages.
    	 * 
    	 * @return The messages
    	 */
    	public java.util.List<Message> getMessages() {
    		return (_messages);
    	}

		@Override
		public void visit(Visitor visitor) {
		}
    	
        public void toText(StringBuffer buf, int level) {
        	
        	GInterruptible.indent(buf, level);
    		
        	for (int i=0; i < _messages.size(); i++) {
        		if (i > 0) {
        			buf.append(",");
        		}
        		_messages.get(i).toText(buf, level);
        	}
        	
        	buf.append(" by ");
        	
        	_role.toText(buf, level);
        	
        	buf.append(";\n");
    	}
    }
}

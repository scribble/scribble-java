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
package org.scribble.model.local;

import org.scribble.model.Message;
import org.scribble.model.Role;

/**
 * This class represents the interruptible construct.
 * 
 */
public class LInterruptible extends LActivity {

    private String _scope=null;
    private LBlock _block=new LBlock();
    private Throw _throws=null;
    private java.util.List<Catch> _catches=new java.util.ArrayList<Catch>();

    /**
     * This is the default constructor.
     * 
     */
    public LInterruptible() {
        _block.setParent(this);
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
    public LBlock getBlock() {
        return (_block);
    }
    
    /**
     * This method sets the block.
     * 
     * @param block The block
     */
    public void setBlock(LBlock block) {
        if (_block != null) {
            _block.setParent(null);
        }
        
        _block = block;
        
        if (_block != null) {
            _block.setParent(this);
        }
    }

	/**
	 * This method sets the catches.
	 * 
	 * @param catches The catches
	 */
	public void setCatches(java.util.List<Catch> catches) {
		_catches = catches;
	}
	
	/**
	 * This method gets the catches.
	 * 
	 * @return The catches
	 */
	public java.util.List<Catch> getCatches() {
		return (_catches);
	}
	
	/**
	 * This method sets the throws clause.
	 * 
	 * @param t The throw
	 */
	public void setThrows(Throw t) {
		_throws = t;
	}

	/**
	 * This method gets the throws clause.
	 * 
	 * @return The throw
	 */
	public Throw getThrows() {
		return (_throws);
	}

	/**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(LVisitor visitor) {
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

        LInterruptible that = (LInterruptible) o;

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
    	
    	buf.append("\n");
    	
    	if (getThrows() != null) {
    		getThrows().toText(buf, level);
    	}
    	
    	for (Catch i : getCatches()) {
    		i.toText(buf, level);
    	}
	}
    
    /**
     * This class represents the throw definition.
     * 
     */
    public static class Throw {
    	
        private java.util.List<Role> _toRoles=new java.util.ArrayList<Role>();
    	private java.util.List<Message> _messages=new java.util.ArrayList<Message>();
    	
    	public Throw() {
    	}
       	
        /**
         * This method returns the 'to' roles.
         * 
         * @return The 'to' roles
         */
        public java.util.List<Role> getToRoles() {
            return (_toRoles);
        }
        
        /**
         * This method sets the 'to' roles.
         * 
         * @param part The 'to' roles
         */
        public void setToRoles(java.util.List<Role> part) {
        	_toRoles = part;
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
    	
        public void toText(StringBuffer buf, int level) {
        	
        	LInterruptible.indent(buf, level);
        	
        	buf.append("throws ");
    		
        	for (int i=0; i < _messages.size(); i++) {
        		if (i > 0) {
        			buf.append(",");
        		}
        		_messages.get(i).toText(buf, level);
        	}
        	
        	buf.append(" to ");
        	
        	for (int i=0; i < _toRoles.size(); i++) {
        		if (i > 0) {
        			buf.append(",");
        		}
        		Role r=_toRoles.get(i);
        		r.toText(buf, level);
        	}
        	
        	buf.append(";\n");
    	}
    }
    
    /**
     * This class represents the catch definition.
     * 
     */
    public static class Catch {
    	
    	private Role _role=null;
    	private java.util.List<Message> _messages=new java.util.ArrayList<Message>();
    	
    	public Catch() {
    	}
       	
    	/**
    	 * This method sets the 'from' role.
    	 * 
    	 * @param role The 'from' role
    	 */
    	public void setRole(Role role) {
    		_role = role;
    	}
       	
    	/**
    	 * This method gets the 'from' role.
    	 * 
    	 * @return The 'from' role
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
    	
        public void toText(StringBuffer buf, int level) {
        	
        	LInterruptible.indent(buf, level);
    		
        	buf.append("catches ");
        	
        	for (int i=0; i < _messages.size(); i++) {
        		if (i > 0) {
        			buf.append(",");
        		}
        		_messages.get(i).toText(buf, level);
        	}
        	
        	buf.append(" from ");
        	
        	_role.toText(buf, level);
        	
        	buf.append(";\n");
    	}
    }
}

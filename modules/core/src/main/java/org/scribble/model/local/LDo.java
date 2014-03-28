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
package org.scribble.model.local;

import org.scribble.model.Argument;
import org.scribble.model.ContainmentList;
import org.scribble.model.RoleInstantiation;

/**
 * This class represents the Run construct.
 * 
 */
public class LDo extends LActivity {
    private String _protocol=null;
    private String _scope=null;
    private java.util.List<Argument> _arguments=new ContainmentList<Argument>(this, Argument.class);
    private java.util.List<RoleInstantiation> _roleInstantiations=new ContainmentList<RoleInstantiation>(this,
    											RoleInstantiation.class);

    /**
     * This is the default constructor.
     * 
     */
    public LDo() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param copy The copy
     */
    public LDo(LDo copy) {
        super(copy);
        _protocol = copy.getProtocol();
        
        for (Argument arg : copy.getArguments()) {
            _arguments.add(new Argument(arg));
        }
        
        for (RoleInstantiation ri : copy.getRoleInstantiations()) {
            _roleInstantiations.add(new RoleInstantiation(ri));
        }
    }
    
    /**
     * This method returns the protocol.
     * 
     * @return The protocol
     */
    public String getProtocol() {
        return (_protocol);
    }
    
    /**
     * This method sets the protocol.
     * 
     * @param protocol The protocol
     */
    public void setProtocol(String protocol) {
    	_protocol = protocol;
    }
    
    /**
     * This method returns the optional scope name.
     * 
     * @return The scope name
     */
    public String getScope() {
    	return (_scope);
    }
    
    /**
     * This method sets the optional scope name.
     * 
     * @param scope The scope name
     */
    public void setScope(String scope) {
    	_scope = scope;
    }
    
    /**
     * This method returns the argument list.
     * 
     * @return The list of arguments
     */
    public java.util.List<Argument> getArguments() {
        return (_arguments);
    }
    
    /**
     * This method returns the argument list.
     * 
     * @return The list of arguments
     */
    public java.util.List<RoleInstantiation> getRoleInstantiations() {
        return (_roleInstantiations);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(LVisitor visitor) {
    	visitor.accept(this);
    }
    
	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("do ");
    	
    	if (_scope != null) {
    		buf.append(_scope);
    		buf.append(": ");
    	}
    	
    	if (_protocol != null) {
    		buf.append(_protocol);
    	}
    	
    	if (_arguments.size() > 0) {
    		buf.append('<');
	    	for (int i=0; i < _arguments.size(); i++) {
	    		if (i > 0) {
	    			buf.append(",");
	    		}
	    		buf.append(_arguments.get(i));
	    	}
	    	buf.append(">");
    	}
    	
    	buf.append("(");
    	for (int i=0; i < _roleInstantiations.size(); i++) {
    		if (i > 0) {
    			buf.append(",");
    		}
    		_roleInstantiations.get(i).toText(buf, level);
    	}
    	
		buf.append(");\n");
	}
}

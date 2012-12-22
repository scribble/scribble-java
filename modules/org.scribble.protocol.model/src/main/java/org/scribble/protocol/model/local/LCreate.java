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
package org.scribble.protocol.model.local;

import org.scribble.protocol.model.Parameter;
import org.scribble.protocol.model.RoleInstantiation;

/**
 * This class represents the Create construct.
 * 
 */
public class LCreate extends LActivity {

    private String _protocol=null;
    private java.util.List<Parameter> _parameters=new java.util.Vector<Parameter>();
    private java.util.List<RoleInstantiation> _roleInstantiations=new java.util.Vector<RoleInstantiation>();

    /**
     * This is the default constructor.
     * 
     */
    public LCreate() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param copy The copy
     */
    public LCreate(LCreate copy) {
        super(copy);
        _protocol = copy.getProtocol();
        
        for (Parameter p : copy.getParameters()) {
            _parameters.add(new Parameter(p));
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
     * This method returns the parameter list.
     * 
     * @return The list of parameters
     */
    public java.util.List<Parameter> getParameters() {
        return (_parameters);
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
    	
    	buf.append("create ");
    	
    	buf.append(_protocol);
    	
    	if (_parameters.size() > 0) {
    		buf.append('<');
	    	for (int i=0; i < _parameters.size(); i++) {
	    		if (i > 0) {
	    			buf.append(",");
	    		}
	    		buf.append(_parameters.get(i));
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

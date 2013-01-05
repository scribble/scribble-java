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
package org.scribble.protocol.model.global;

import org.scribble.protocol.model.FullyQualifiedName;
import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.RoleInstantiation;

/**
 * This class represents the Run construct.
 * 
 */
public class GDo extends GActivity {

    private String _protocol=null;
    private FullyQualifiedName _moduleName=null;
    private java.util.List<MessageSignature> _arguments=new java.util.Vector<MessageSignature>();
    private java.util.List<RoleInstantiation> _roleInstantiations=new java.util.Vector<RoleInstantiation>();

    /**
     * This is the default constructor.
     * 
     */
    public GDo() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param copy The copy
     */
    public GDo(GDo copy) {
        super(copy);
        _protocol = copy.getProtocol();
        
        for (MessageSignature arg : copy.getArguments()) {
            _arguments.add(new MessageSignature(arg));
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
     * This method returns the optional module name.
     * 
     * @return The module name
     */
    public FullyQualifiedName getModuleName() {
    	return (_moduleName);
    }
    
    /**
     * This method sets the optional module name.
     * 
     * @param module The module name
     */
    public void setModuleName(FullyQualifiedName module) {
    	_moduleName = module;
    }
    
    /**
     * This method returns the argument list.
     * 
     * @return The list of arguments
     */
    public java.util.List<MessageSignature> getArguments() {
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
    public void visit(GVisitor visitor) {
    	visitor.accept(this);
    }
    
	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("do ");
    	
    	if (_moduleName != null) {
    		_moduleName.toText(buf, level);
    		buf.append(".");
    	}
    	
    	buf.append(_protocol);
    	
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

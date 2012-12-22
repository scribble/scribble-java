/*
 * Copyright 2009 www.scribble.org
 *
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
 * This class represents an imported type associated with a model.
 * 
 */
public class PayloadType extends ModelObject {

	private String _variable=null;
	private String _type=null;
    
    /**
     * The default constructor.
     */
    public PayloadType() {
    }
    
    /**
     * The copy constructor.
     * 
     * @param copy The copy
     */
    public PayloadType(PayloadType copy) {
        _variable = copy.getVariable();
        _type = copy.getType();
    }
    
    /**
     * This method returns the optional variable.
     * 
     * @return The optional variable
     */
    public String getVariable() {
        return (_variable);
    }
    
    /**
     * This method sets the optional variable.
     * 
     * @param variable The variable
     */
    public void setVariable(String variable) {
        _variable = variable;
    }
    
    /**
     * This method returns the schema format.
     * 
     * @return The name
     */
    public String getType() {
        return (_type);
    }
    
    /**
     * This method sets the type.
     * 
     * @param type The type
     */
    public void setType(String type) {
        _type = type;
    }
    
    @Override
    public String toString() {
        String ret=getType();
        
        if (ret == null) {
            ret = "<Unnamed Type>";
        }
        
        if (_variable != null) {
        	ret += ":"+_variable;
        }
        
        return (ret);
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public void visit(Visitor visitor) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void toText(StringBuffer buf, int level) {
		buf.append(_type);
		if (_variable != null) {
			buf.append(':');
			buf.append(_variable);
		}
	}
}

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

	private String _annotation=null;
	private String _name=null;
    
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
        _annotation = copy.getAnnotation();
        _name = copy.getName();
    }
    
    /**
     * This method returns the optional annotation.
     * 
     * @return The optional annotation
     */
    public String getAnnotation() {
        return (_annotation);
    }
    
    /**
     * This method sets the optional annotation.
     * 
     * @param annotation The annotation
     */
    public void setAnnotation(String annotation) {
        _annotation = annotation;
    }
    
    /**
     * This method returns the payload type or
     * parameter name.
     * 
     * @return The name
     */
    public String getName() {
        return (_name);
    }
    
    /**
     * This method sets the payload type or
     * parameter name.
     * 
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }
    
    @Override
    public String toString() {
        String ret=getName();
        
        if (ret == null) {
            ret = "<Unnamed Type>";
        }
        
        if (_annotation != null) {
        	ret = _annotation+":"+ret;
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
		if (_annotation != null) {
			buf.append(_annotation);
			buf.append(':');
		}
		buf.append(_name);
	}
}

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
package org.scribble.model;

/**
 * This class represents a message.
 * Message signatures can be either a simple signature with
 * a unique TypeReference, or an operation name with several
 * TypeReferences as arguments.
 */
public class Argument extends ModelObject {

    private String _name=null;
    private MessageSignature _messageSignature=null;
    private String _alias=null;

    /**
     * The default constructor.
     */
    public Argument() {
    }
    
    public Argument(Argument copy) {
    	_name = copy._name;
    	
    	if (copy._messageSignature != null) {
    		_messageSignature = new MessageSignature(copy._messageSignature);
    	}
    }

    /**
     * This method returns the optional parameter or payload type name.
     * 
     * @return The optional name
     */
    public String getName() {
        return (_name);
    }
    
    /**
     * This method sets the payload type or parameter name. This property
     * is mutually exclusive with the message signature.
     * 
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }
    
    /**
     * This method returns the message signature.
     * 
     * @return The message signature
     */
    public MessageSignature getMessageSignature() {
        return (_messageSignature);
    }
    
    /**
     * This method sets the message signature.
     * 
     * @param sig The message signature
     */
    public void setMessageSignature(MessageSignature sig) {
    	_messageSignature = sig;
    }
    
    /**
     * This method returns the alias.
     * 
     * @return The alias
     */
    public String getAlias() {
        return (_alias);
    }
    
    /**
     * This method sets the alias.
     * 
     * @param alias The alias
     */
    public void setAlias(String alias) {
        _alias = alias;
    }
    
    @Override
    public String toString() {
        String ret="";
        
        if (getMessageSignature() != null) {
            ret += getMessageSignature();
        } else if (getName() != null &&
        		getName().trim().length() > 0) {
        	ret += getName();
        }
        
        if (ret.equals("")) {
            ret = "<No Signature>";
        }
        
        return (ret);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {

		if (_messageSignature != null) {
			_messageSignature.toText(buf, level);
		} else if (_name != null) {
			buf.append(_name);
		}
		
		if (_alias != null) {
			buf.append(" as ");
			buf.append(_alias);
		}
	}
}

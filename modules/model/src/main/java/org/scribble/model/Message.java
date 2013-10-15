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
public class Message extends ModelObject {

    private String _parameter=null;
    private MessageSignature _messageSignature=null;

    /**
     * The default constructor.
     */
    public Message() {
    }
    
    public Message(Message copy) {
    	_parameter = copy._parameter;
    	
    	if (copy._messageSignature != null) {
    		_messageSignature = new MessageSignature(copy._messageSignature);
    	}
    }

    /**
     * This method returns the optional parameter.
     * 
     * @return The optional parameter
     */
    public String getParameter() {
        return (_parameter);
    }
    
    /**
     * This method sets the parameter. This property
     * is mutually exclusive with the operator and
     * types list, which represent the message
     * signature.
     * 
     * @param parameter The parameter
     */
    public void setParameter(String parameter) {
        _parameter = parameter;
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
    
    @Override
    public String toString() {
        String ret="";
        
        if (getMessageSignature() != null) {
            ret += getMessageSignature();
        } else if (getParameter() != null &&
        		getParameter().trim().length() > 0) {
        	ret += getParameter();
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
		} else if (_parameter != null) {
			buf.append(_parameter);
		}
	}
}

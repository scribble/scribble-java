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
 * This class represents a message signature.
 * Message signatures can be either a simple signature with
 * a unique TypeReference, or an operation name with several
 * TypeReferences as arguments.
 */
public class MessageSignature extends ModelObject {

    private String _operator=null;
    private java.util.List<PayloadType> _types=new java.util.ArrayList<PayloadType>();

    /**
     * The default constructor.
     */
    public MessageSignature() {
    }

    /**
     * Constructor for MessageSignatures that comprise an operation.
     * @param operator The operation name.
     * @param types The arguments for the operation.
     */
    public MessageSignature(String operator, java.util.List<PayloadType> types) {
        _operator = operator;
        _types.addAll(types);
    }
    
    /**
     * The copy constructor.
     * 
     * @param msig The message signature
     */
    public MessageSignature(MessageSignature msig) {
        super(msig);
        
        _operator = msig.getOperator();
        
        for (PayloadType tref : msig.getTypes()) {
            _types.add(new PayloadType(tref));
        }
    }
    
    /**
     * This method returns the optional operator.
     * 
     * @return The optional operator
     */
    public String getOperator() {
        return (_operator);
    }
    
    /**
     * This method sets the operator.
     * 
     * @param operator The operator
     */
    public void setOperator(String operator) {
        _operator = operator;
    }
    
    /**
     * This method returns the list of types. If
     * no operation is defined, then only one type reference
     * should be defined.
     * 
     * @return The list of types
     */
    public java.util.List<PayloadType> getTypes() {
        return (_types);
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result + _types.hashCode();
        result = 31 * result + (_operator == null ? 0 : _operator.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean ret=false;
        
        if (obj instanceof MessageSignature) {
            MessageSignature other=(MessageSignature) obj;
            
            if (other.getTypes().size() == getTypes().size()) {
                if (other._operator != null && _operator != null) {
                    ret = other._operator.equals(_operator);
                } else if (other._operator == null && _operator == null) {
                    ret = true;
                }
                
                for (int i=0; ret && i < getTypes().size(); i++) {
                    ret = getTypes().get(i).equals(other.getTypes().get(i));
                }
            }
        }
        
        return (ret);
    }
    
    @Override
    public String toString() {
        String ret="";
        
        ret += getOperator() + "(";

        for (int i=0; i < _types.size(); i++) {
            if (i > 0) {
                ret += ",";
            }
            ret += _types.get(i).getName();    
        }
    
        ret += ")";
        
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

		buf.append(_operator);
		buf.append('(');
	
		for (int i=0; i < getTypes().size(); i++) {
			if (i > 0) {
				buf.append(',');
			}
			getTypes().get(i).toText(buf, level);
		}
	
		buf.append(')');
	}
}

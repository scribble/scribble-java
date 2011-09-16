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

import java.util.Collection;

/**
 * This class represents a message signature.
 * Message signatures can be either a simple signature with
 * a unique TypeReference, or an operation name with several
 * TypeReferences as arguments.
 */
public class MessageSignature extends ModelObject {

    private String _operation=null;
    private java.util.List<TypeReference> _typeReferences=
            new ContainmentList<TypeReference>(this, TypeReference.class);

    /**
     * The default constructor.
     */
    public MessageSignature() {
    }

    /**
     * No-operation constructor: only one TypeReference is allowed.
     * @param typeRef The TypeReference for this simply-typed message.
     */
    public MessageSignature(TypeReference typeRef) {
        _typeReferences.add(typeRef);
    }

    /**
     * Constructor for MessageSignatures that comprise an operation.
     * @param operation The operation name.
     * @param typeRefs The arguments for the operation.
     */
    public MessageSignature(String operation, Collection<TypeReference> typeRefs) {
        _operation = operation;
        _typeReferences.addAll(typeRefs);
    }
    
    /**
     * The copy constructor.
     * 
     * @param msig The message signature
     */
    public MessageSignature(MessageSignature msig) {
        super(msig);
        
        _operation = msig.getOperation();
        
        for (TypeReference tref : msig.getTypeReferences()) {
            _typeReferences.add(new TypeReference(tref));
        }
    }
    
    /**
     * This method returns the optional operation.
     * 
     * @return The optional operation
     */
    public String getOperation() {
        return (_operation);
    }
    
    /**
     * This method sets the operation.
     * 
     * @param operation The operation
     */
    public void setOperation(String operation) {
        _operation = operation;
    }
    
    // TODO: Need to think about actual type for this list
    // See how JDT DOM handles local details (e.g. short
    // name) versus fully qualified name identifying the
    // actual type. Issue also is if the model can be
    // updated, what happens to the fully qualified name?
    /**
     * This method returns the list of type references. If
     * no operation is defined, then only one type reference
     * should be defined.
     * 
     * @return The list of type references
     */
    public java.util.List<TypeReference> getTypeReferences() {
        return (_typeReferences);
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result + _typeReferences.hashCode();
        result = 31 * result + (_operation == null ? 0 : _operation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean ret=false;
        
        if (obj instanceof MessageSignature) {
            MessageSignature other=(MessageSignature) obj;
            
            if (other.getTypeReferences().size() == getTypeReferences().size()) {
                if (other._operation != null && _operation != null) {
                    ret = other._operation.equals(_operation);
                } else if (other._operation == null && _operation == null) {
                    ret = true;
                }
                
                for (int i=0; ret && i < getTypeReferences().size(); i++) {
                    ret = getTypeReferences().get(i).equals(other.getTypeReferences().get(i));
                }
            }
        }
        
        return (ret);
    }
    
    @Override
    public String toString() {
        String ret="";
        
        if (getOperation() != null
                && getOperation().trim().length() > 0) {
            ret += getOperation() + "(";
        }
        
        for (int i=0; i < _typeReferences.size(); i++) {
            if (i > 0) {
                ret += ",";
            }
            ret += _typeReferences.get(i).getName();    
        }
        
        if (getOperation() != null
                && getOperation().trim().length() > 0) {
            ret += ")";
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

}

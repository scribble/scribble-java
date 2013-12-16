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
 * This class represents a parameter declaration.
 * 
 */
public class ParameterDecl extends ModelObject {
    
    private String _name=null;
    private String _alias=null;
    private ParameterType _parameterType=null;

    /**
     * This is the default constructor.
     */
    public ParameterDecl() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param pd The role
     */
    public ParameterDecl(ParameterDecl pd) {
        super(pd);
        _name = pd.getName();
        _alias = pd.getAlias();
        _parameterType = pd.getType();
    }
    
    /**
     * This method returns the name of the parameter declaration.
     * 
     * @return The name
     */
    public String getName() {
        return (_name);
    }
    
    /**
     * This method sets the name of the parameter declaration.
     * 
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }
    
    /**
     * This method returns the alias of the parameter declaration.
     * 
     * @return The alias
     */
    public String getAlias() {
        return (_alias);
    }
    
    /**
     * This method sets the alias of the parameter declaration.
     * 
     * @param alias The alias
     */
    public void setAlias(String alias) {
    	_alias = alias;
    }
    
    /**
     * This method returns the type of the parameter declaration.
     * 
     * @return The type
     */
    public ParameterType getType() {
        return (_parameterType);
    }
    
    /**
     * This method sets the type of the parameter declaration.
     * 
     * @param type The type
     */
    public void setType(ParameterType type) {
    	_parameterType = type;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ret=false;
    
        if (obj instanceof ParameterDecl) {
            ParameterDecl other=(ParameterDecl)obj;
            
            if (other.getName() != null && other.getName().equals(_name)) {
                ret = true;
            }
        }
        
        return (ret);
    }
    
    @Override
    public int hashCode() {
        int ret=super.hashCode();
        
        if (_name != null) {
            ret = _name.hashCode();
        }
        
        return (ret);
    }
    
    @Override
    public String toString() {
        String ret=getName();
        
        if (ret == null) {
            ret = "<Unnamed Role>";
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
    	buf.append(getType().name().toLowerCase());
    	buf.append(" ");
    	
		if (_name != null) {
			buf.append(_name);
		}
		
		if (_alias != null) {
			buf.append(" as "+_alias);
		}
	}

    /**
     * Parameter declaration type.
     *
     */
    public enum ParameterType {
    	// Type
    	Type,
    	
    	//Signature
    	Sig
    }
}

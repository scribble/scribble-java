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
 * This class represents an imported protocol.
 * 
 */
public class ImportDecl extends ModelObject {

    private String _packageName=null;    
    private String _moduleName=null;
    private String _alias=null;

    /**
     * The default constructor.
     */
    public ImportDecl() {
    }
    
    /**
     * The copy constructor.
     * 
     * @param copy The copy
     */
    public ImportDecl(ImportDecl copy) {
        super(copy);
        _packageName = copy.getPackageName();
        _moduleName = copy.getModuleName();
        _alias = copy.getAlias();
    }
    
    /**
     * This method returns the name of the
     * type being imported.
     * 
     * @return The name
     */
    public String getPackageName() {
        return (_packageName);
    }
    
    /**
     * This method sets the name of the
     * type being imported.
     * 
     * @param name The name
     */
    public void setPackageName(String name) {
    	_packageName = name;
    }
    
    /**
     * This method returns the optional module name.
     * 
     * @return The optional module name
     */
    public String getModuleName() {
        return (_moduleName);
    }
    
    /**
     * This method sets the optional module name.
     * 
     * @param module The optional module name
     */
    public void setModuleName(String module) {
    	_moduleName = module;
    }
    
    /**
     * This method returns the optional alias.
     * 
     * @return The optional alias
     */
    public String getAlias() {
        return (_alias);
    }
    
    /**
     * This method sets the optional alias.
     * 
     * @param alias The optionalalias
     */
    public void setAlias(String alias) {
        _alias = alias;
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        visitor.accept(this);
    }
    
    @Override
    public String toString() {
        String ret=getPackageName();
        
        if (ret == null) {
            ret = "<Unnamed Package>";
        }
        
        return (ret);
    }
    
	/**
	 * {@inheritDoc}
	 */
	public void toText(StringBuffer buf, int level) {
		indent(buf, level);
		
		if (_moduleName != null) {
			buf.append("from ");
		} else {
			buf.append("import ");
		}
		
		buf.append(_packageName);
		
		if (_moduleName != null) {
			buf.append(" import ");
			
			buf.append(_moduleName);
			
			if (_alias != null) {
				buf.append(" as ");
				
				buf.append(_alias);
			}
		}

		buf.append(";\r\n");
	}
}

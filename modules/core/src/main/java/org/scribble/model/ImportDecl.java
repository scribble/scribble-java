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
package org.scribble.model;

/**
 * This class represents an imported protocol.
 * 
 */
public class ImportDecl extends ModelObject {

    private String _moduleName=null;
    private String _memberName=null;    
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
        _moduleName = copy.getModuleName();
        _memberName = copy.getMemberName();
        _alias = copy.getAlias();
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
     * This method returns the name of the
     * member being imported.
     * 
     * @return The member name
     */
    public String getMemberName() {
        return (_memberName);
    }
    
    /**
     * This method sets the name of the
     * member being imported.
     * 
     * @param name The member name
     */
    public void setMemberName(String name) {
    	_memberName = name;
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
     * This method returns the declaration name.
     * 
     * @return The declaration name
     */
    public String getDeclarationName() {
    	String ret=null;
    	
    	if (getAlias() != null) {
    		ret = getAlias();
    	} else if (getMemberName() != null) {
    		ret = getModuleName().toString()+"."+getMemberName();
    	} else {
    		ret = getModuleName().toString();
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
        visitor.accept(this);
    }
    
    @Override
    public String toString() {
        String ret=getMemberName();
        
        if (ret == null) {
            ret = "<Module Not Set>";
        }
        
        return (ret);
    }
    
	/**
	 * {@inheritDoc}
	 */
	public void toText(StringBuffer buf, int level) {
		indent(buf, level);
		
		/*
		buf.append("import ");

		if (_memberName != null) {
			buf.append(_memberName);
			
			buf.append(" from ");
		}
		
		buf.append(_moduleName);
		
		if (_memberName != null && _alias != null) {
			buf.append(" as ");
			
			buf.append(_alias);
		}
		*/
		
		if (_memberName != null) {
			buf.append("from ");
		} else {
			buf.append("import ");
		}
		
		buf.append(_moduleName);

		if (_memberName != null) {			
			buf.append(" import ");
			buf.append(_memberName);
		}
		
		if (_alias != null) {
			buf.append(" as ");
			
			buf.append(_alias);
		}

		buf.append(";\n");
	}
}

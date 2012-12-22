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
 * This class represents the base class for models associated with
 * specific notations. The details associated with the notation are
 * contained within derived classes.
 *
 */
public class Module extends ModelObject {
    
	private org.scribble.protocol.model.FullyQualifiedName _package=null;
    private Protocol _protocol=null;
    private java.util.List<ImportDecl> _imports=
            new ContainmentList<ImportDecl>(this, ImportDecl.class);
    private java.util.List<PayloadTypeDecl> _payloadTypes=
            new ContainmentList<PayloadTypeDecl>(this, PayloadTypeDecl.class);

    /**
     * The default constructor for the model.
     */
    public Module() {
    }
    
    /**
     * This method returns the package.
     * 
     * @return The package
     */
    public org.scribble.protocol.model.FullyQualifiedName getPackage() {
    	return (_package);
    }
    
    /**
     * This method sets the package.
     * 
     * @param pn The package
     */
    public void setPackage(org.scribble.protocol.model.FullyQualifiedName pn) {
    	_package = pn;
    }
    
    /**
     * This method returns the list of import definitions.
     * 
     * @return The import definitions
     */
    public java.util.List<ImportDecl> getImports() {
        return (_imports);
    }
    
    /**
     * This method returns the list of payload types.
     * 
     * @return The payload types
     */
    public java.util.List<PayloadTypeDecl> getTypeDeclarations() {
        return (_payloadTypes);
    }
    
    /**
     * This method returns the definition associated with
     * this model.
     * 
     * @return The definition
     */
    public Protocol getProtocol() {
        return (_protocol);
    }
    
    /**
     * This method set the definition associated with the
     * model.
     * 
     * @param defn The definition
     */
    public void setProtocol(Protocol defn) {
        if (_protocol != null) {
            _protocol.setParent(null);
        }
        
        _protocol = defn;
        
        if (_protocol != null) {
            _protocol.setParent(this);
        }
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        
        for (ImportDecl imp : getImports()) {
            imp.visit(visitor);
        }
        
        if (getProtocol() != null) {
            getProtocol().visit(visitor);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString() {
    	StringBuffer buf=new StringBuffer();
    	toText(buf, 0);
    	
    	return (buf.toString());
    }
    
    /**
     * {@inheritDoc}
     */
    public void toText(StringBuffer buf, int level) {
    	if (_package != null) {
    		indent(buf, level);
    		
    		buf.append("package ");
    		
    		_package.toText(buf, level);
    		
    		buf.append(";\n\n");
    	}
    	
    	for (ImportDecl imp : getImports()) {
    		imp.toText(buf, level);
    	}
    	
    	if (getImports().size() > 0) {
    		buf.append("\n");
    	}
    	
    	for (PayloadTypeDecl ptd : getTypeDeclarations()) {
    		ptd.toText(buf, level);
    	}
    	
    	if (getTypeDeclarations().size() > 0) {
    		buf.append("\n");
    	}
    	
    	if (getProtocol() != null) {
    		getProtocol().toText(buf, level);
    	}
    }

}

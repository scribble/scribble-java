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
public class PayloadTypeDecl extends ModelObject {

	private String _format=null;
	private String _type=null;
	private String _schema=null;
    private String _alias=null;
    
    /**
     * The default constructor.
     */
    public PayloadTypeDecl() {
    }
    
    /**
     * The copy constructor.
     * 
     * @param copy The copy
     */
    public PayloadTypeDecl(PayloadTypeDecl copy) {
        super(copy);
        _format = copy.getFormat();
        _type = copy.getType();
        _schema = copy.getSchema();
        _alias = copy.getAlias();
    }
    
    /**
     * This method returns the schema format.
     * 
     * @return The name
     */
    public String getFormat() {
        return (_format);
    }
    
    /**
     * This method sets the schema format.
     * 
     * @param format The format
     */
    public void setFormat(String format) {
        _format = format;
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
    
    /**
     * This method returns the schema.
     * 
     * @return The schema
     */
    public String getSchema() {
        return (_schema);
    }
    
    /**
     * This method sets the schema.
     * 
     * @param schema The schema
     */
    public void setSchema(String schema) {
        _schema = schema;
    }
    
    /**
     * This method returns the alias.
     * 
     * @return The optional alias
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
        String ret=getType();
        
        if (ret == null) {
            ret = "<Unnamed Type>";
        }
        
        return (ret);
    }
    
	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		indent(buf, level);
		
		buf.append("type ");
		
		if (_format != null) {
			buf.append('<');
			buf.append(_format);
			buf.append("> ");
		}
		
		if (_type != null) {
			buf.append("\"");
			buf.append(_type);
			buf.append("\"");
		}
		
		if (_schema != null) {
			buf.append(" from ");
			buf.append("\"");
			buf.append(_schema);
			buf.append("\"");
		}
		
		if (_alias != null) {
			buf.append(" as ");
			buf.append(_alias);
		}
		
		buf.append(";\n");
	}
}

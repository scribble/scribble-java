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
 * This class represents an import definition associated with a model.
 * 
 */
public class TypeImportList extends ImportList {

    private String _format=null;
    private String _location=null;
    private java.util.List<TypeImport> _types=
        new ContainmentList<TypeImport>(this, TypeImport.class);

    /**
     * The default constructor.
     */
    public TypeImportList() {
    }
    
    /**
     * The copy constructor.
     * 
     * @param copy The copy
     */
    public TypeImportList(TypeImportList copy) {
        super(copy);
        _format = copy.getFormat();
        _location = copy.getLocation();
        for (TypeImport ti : copy.getTypeImports()) {
            _types.add(new TypeImport(ti));
        }
    }
    
    /**
     * This method returns the format.
     * 
     * @return The format
     */
    public String getFormat() {
        return (_format);
    }
    
    /**
     * This method sets the format.
     * 
     * @param format The format
     */
    public void setFormat(String format) {
        _format = format;
    }
    
    /**
     * This method returns the location of the schema.
     * 
     * @return The location
     */
    public String getLocation() {
        return (_location);
    }
    
    /**
     * This method sets the location of the schema.
     * 
     * @param location The location
     */
    public void setLocation(String location) {
        _location = location;
    }
    
    /**
     * This method returns the list of imported types.
     * 
     * @return The list of imported types
     */
    public java.util.List<TypeImport> getTypeImports() {
        return (_types);
    }
    
    /**
     * This method returns the imported type associated with
     * the supplied name.
     * 
     * @param name The type name
     * @return The type, or null if not found
     */
    public TypeImport getTypeImport(String name) {
        TypeImport ret=null;
        
        for (int i=0; ret == null
                && i < _types.size(); i++) {
            if (_types.get(i).getName().equals(name)) {
                ret = _types.get(i);
            }
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
        
        for (TypeImport t : getTypeImports()) {
            t.visit(visitor);
        }
    }
}

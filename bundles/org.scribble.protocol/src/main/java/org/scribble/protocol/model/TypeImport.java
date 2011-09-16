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
public class TypeImport extends ModelObject {

    private String _name=null;
    private DataType _dataType=null;
    
    /**
     * The default constructor.
     */
    public TypeImport() {
    }
    
    /**
     * This method returns the name of the
     * type being imported.
     * 
     * @return The name
     */
    public String getName() {
        return (_name);
    }
    
    /**
     * This method sets the name of the
     * type being imported.
     * 
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }
    
    /**
     * This method returns the data type.
     * 
     * @return The data type
     */
    public DataType getDataType() {
        return (_dataType);
    }
    
    /**
     * This method sets the data type.
     * 
     * @param dataType The data type
     */
    public void setDataType(DataType dataType) {
        _dataType = dataType;
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
        String ret=getName();
        
        if (ret == null) {
            ret = "<Unnamed Type>";
        }
        
        return (ret);
    }
}

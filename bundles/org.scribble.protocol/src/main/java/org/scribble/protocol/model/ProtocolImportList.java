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
public class ProtocolImportList extends ImportList {

    private java.util.List<ProtocolImport> _protocolImports=
        new ContainmentList<ProtocolImport>(this, ProtocolImport.class);

    /**
     * The default constructor.
     */
    public ProtocolImportList() {
    }
    
    /**
     * This method returns the list of imported protocols.
     * 
     * @return The list of imported protocols
     */
    public java.util.List<ProtocolImport> getProtocolImports() {
        return (_protocolImports);
    }
    
    /**
     * This method returns the imported protocol associated with
     * the supplied name.
     * 
     * @param name The protocol name
     * @return The protocol, or null if not found
     */
    public ProtocolImport getProtocolImport(String name) {
        ProtocolImport ret=null;
        
        for (int i=0; ret == null
                && i < _protocolImports.size(); i++) {
            if (_protocolImports.get(i).getName().equals(name)) {
                ret = _protocolImports.get(i);
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
        
        for (ProtocolImport pi : getProtocolImports()) {
            pi.visit(visitor);
        }
    }
}

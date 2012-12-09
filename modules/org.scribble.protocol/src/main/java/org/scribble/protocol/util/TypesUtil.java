/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.util;

import org.scribble.protocol.model.ImportList;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.TypeImport;
import org.scribble.protocol.model.TypeImportList;
import org.scribble.protocol.model.TypeReference;

/**
 * Helper functions for dealing with protocol types.
 *
 */
public final class TypesUtil {

    /**
     * Private constructor.
     */
    private TypesUtil() {
    }
    
    /**
     * This method identifies the type import for the supplied
     * type reference.
     * 
     * @param typeRef The type reference
     * @return The type import, or null if not found
     */
    public static TypeImport getTypeImport(TypeReference typeRef) {
        TypeImport ret=null;
        
        String typeName=typeRef.getName();
        
        ProtocolModel pm=null;
        
        ModelObject cur=typeRef;
        while (cur != null) {
            if (cur instanceof ProtocolModel) {
                pm = (ProtocolModel)cur;
            }
            cur = cur.getParent();
        }
        
        if (pm != null) {
            
            for (int i=0; ret == null && i < pm.getImports().size(); i++) {
                ImportList imp=pm.getImports().get(i);
                
                if (imp instanceof TypeImportList) {
                    ret = ((TypeImportList)imp).getTypeImport(typeName);
                }
            }
        }
        
        return (ret);
    }
    
    /**
     * This method identifies the type import for the supplied
     * protocol model and type name.
     * 
     * @param pm The protocol model
     * @param typeName The type name
     * @return The type import, or null if not found
     */
    public static TypeImport getTypeImport(ProtocolModel pm, String typeName) {
        TypeImport ret=null;
        
        if (pm != null && typeName != null) {
            
            for (int i=0; ret == null && i < pm.getImports().size(); i++) {
                ImportList imp=pm.getImports().get(i);
                
                if (imp instanceof TypeImportList) {
                    ret = ((TypeImportList)imp).getTypeImport(typeName);
                }
            }
        }
        
        return (ret);
    }
    
    /**
     * This method determines whether the protocol model has concrete types
     * defined.
     * 
     * @param model The model
     * @return Whether concrete types have been defined
     */
    public static boolean isConcreteTypesDefined(ProtocolModel model) {
        boolean ret=false;
        
        for (ImportList imp : model.getImports()) {
            
            if (imp instanceof TypeImportList) {
                for (TypeImport ti : ((TypeImportList)imp).getTypeImports()) {
                    
                    if (ti.getDataType() != null) {
                        ret = true;
                        break;
                    }
                }
            }
            
            if (ret) {
                break;
            }
        }

        return (ret);
    }
}

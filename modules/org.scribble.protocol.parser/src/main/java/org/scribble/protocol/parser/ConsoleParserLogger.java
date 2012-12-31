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
package org.scribble.protocol.parser;

import org.scribble.protocol.model.ModelObject;

/**
 * This class provides an implementation of the journal that
 * reports issues to the console.
 *
 */
public class ConsoleParserLogger implements ParserLogger {
    
    private static final String NO_DETAILS="";

    /**
     * This method records an error issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void error(String issue, java.util.Map<String,Object> props) {
        System.out.println("ERROR: "+errorDetails(props)+issue);
    }
    
    /**
     * This method records a warning issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void warning(String issue, java.util.Map<String,Object> props) {
        System.out.println("WARN: "+errorDetails(props)+issue);
    }
    
    /**
     * This method records an information issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void info(String issue, java.util.Map<String,Object> props) {
        System.out.println("INFO: "+errorDetails(props)+issue);
    }

    private String errorDetails(java.util.Map<String,Object> props) {
        String ret=NO_DETAILS;
        
        if (props != null && props.containsKey(ModelObject.START_LINE)) {
            ret = "[line "+props.get(ModelObject.START_LINE)+"] ";
        }
        
        return (ret);
    }
}

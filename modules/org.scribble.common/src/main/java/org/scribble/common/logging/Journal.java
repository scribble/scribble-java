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
package org.scribble.common.logging;

/**
 * This interface represents the component used by other scribble modules
 * to record issues.
 *
 */
public interface Journal {

    /**
     * The start line in the document at which the issue has been detected.
     */
    public static final String START_LINE = "start.line";
    
    /**
     * The start column in the document at which the issue has been detected.
     */
    public static final String START_COLUMN = "start.column";
    
    /**
     * The end line in the document at which the issue has been detected.
     */
    public static final String END_LINE = "end.line";
    
    /**
     * The end column in the document at which the issue has been detected.
     */
    public static final String END_COLUMN = "end.column";
    
    /**
     * The start position in the document at which the issue has been detected.
     */
    public static final String START_POSITION = "position.start";
    
    /**
     * The end position in the document at which the issue has been detected.
     */
    public static final String END_POSITION = "position.end";
    
    /**
     * The optional resource URL associated with the issue.
     */
    public static final String RESOURCE_URL = "resource.url";
    
    /**
     * This method records an error issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void error(String issue, java.util.Map<String,Object> props);
    
    /**
     * This method records a warning issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void warning(String issue, java.util.Map<String,Object> props);
    
    /**
     * This method records an information issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void info(String issue, java.util.Map<String,Object> props);

}

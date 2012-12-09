/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.protocol.monitor;

/**
 * This class represents the result from monitoring an event against a
 * behavioural description.
 *
 */
public class Result implements java.io.Serializable {

    private static final long serialVersionUID = 8094872271195676976L;

    private boolean _valid=false;
    private String _reason=null;
    private java.util.Map<String,java.io.Serializable> _properties=
                new java.util.HashMap<String, java.io.Serializable>();
    
    /**
     * Valid result.
     */
    public static final Result VALID = new Result(true);

    /**
     * Message was not handled.
     */
    public static final Result NOT_HANDLED = new Result(false);
    
    /**
     * Invalid result.
     */
    public static final Result INVALID = new Result(false);
    
    /**
     * Constructor specify whether result is valid.
     * 
     * @param valid Whether result is valid
     */
    public Result(boolean valid) {
        _valid = valid;
    }
    
    /**
     * Constructor to specify whether the result is valid, and a reason.
     * 
     * @param valid Whether the result is valid
     * @param reason The reason for the result
     */
    public Result(boolean valid, String reason) {
        this(valid);
        _reason = reason;
    }
    
    /**
     * This method determines whether the result is valid.
     * 
     * @return Whether the result is valid
     */
    public boolean isValid() {
        return (_valid);
    }
    
    /**
     * This method returns the reason for the result.
     * 
     * @return The reason, or null if not specified
     */
    public String getReason() {
        return (_reason);
    }
    
    /**
     * This method returns the properties.
     * 
     * @return The properties
     */
    public java.util.Map<String,java.io.Serializable> getProperties() {
        return (_properties);
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return (this == VALID ? "VALID" : (this == NOT_HANDLED ? "NOT_HANDLED"
                : (this == INVALID ? "INVALID" : "RESULT["+isValid()+" reason="+getReason()
                    +" props="+getProperties()+"]")));
    }
}

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
package org.scribble.protocol.monitor;

/**
 * This interface represents a message to be monitored.
 *
 */
public interface Message {

    /**
     * This method returns the optional operator name.
     * 
     * @return The operator, or null if not relevant
     */
    public String getOperator();
    
    /**
     * This method returns the list of types associated with
     * with the message.
     * 
     * @return The types of the message parameters
     */
    public java.util.List<String> getTypes();
    
}

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
 * This class represents the base class for all Scribble definition
 * components.
 */
public abstract class Activity extends ModelObject {
    
    /**
     * The default constructor.
     */
    public Activity() {
    }
    
    /**
     * The copy constructor.
     * 
     * @param act The activity
     */
    public Activity(Activity act) {
        super(act);
    }
    
    /**
     * This method returns the protocol in which this
     * activity is contained.
     * 
     * @return The protocol, or null if not found
     */
    public Protocol getEnclosingProtocol() {
        Protocol ret=null;
        ModelObject cur=this;
        
        while (ret == null && cur != null) {
            
            if (cur instanceof Protocol) {
                ret = (Protocol) cur;
            } else {
                cur = cur.getParent();
            }
        }
        
        return (ret);
    }
    
}

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
 * This class provides a default implementation of the message.
 *
 */
public class DefaultMessage implements Message {
    
    private String _operator=null;
    private java.util.List<String> _types=new java.util.Vector<String>();

    /**
     * Default constructor.
     */
    public DefaultMessage() {
    }
    
    /**
     * {@inheritDoc}
     */
    public String getOperator() {
        return (_operator);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setOperator(String op) {
        _operator = op;
    }
    
    /**
     * {@inheritDoc}
     */
    public java.util.List<String> getTypes() {
        return (_types);
    }

    @Override
    public String toString() {
        StringBuffer ret=new StringBuffer();
        
        if (_operator != null && _operator.trim().length() > 0) {
            ret.append(_operator);
            ret.append('(');
        }
        
        for (int i=0; i < getTypes().size(); i++) {
            if (i > 0) {
                ret.append(',');
            }
            ret.append(getTypes().get(i));
        }
        
        if (_operator != null && _operator.trim().length() > 0) {
            ret.append(')');
        }
        
        return (ret.toString());
    }
    
    @Override
    public int hashCode() {
        return (toString().hashCode());
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ret=false;
        
        if (obj instanceof DefaultMessage) {
            DefaultMessage other=(DefaultMessage)obj;
            
            if ((_operator == null && other._operator == null)
                    || (_operator != null && other._operator != null
                    && _operator.equals(other._operator))) {
                
                // TODO: Need to validate parameter types
                // Could also have parameter types as Java types
                ret = true;
            }
        }
        
        return (ret);
    }
}

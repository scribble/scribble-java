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
package org.scribble.protocol.parser.antlr;

import org.scribble.protocol.parser.IssueLogger;

/**
 * ANTLR message utilities.
 *
 */
public final class ANTLRMessageUtil {

    /**
     * Private constructor.
     */
    private ANTLRMessageUtil() {
    }
    
    /**
     * End location separator.
     */
    private static final char END_LOCATION_SEPARATOR = ' ';
    
    /**
     * Location prefix token.
     */
    private static final String LOCATION_PREFIX_TOKEN = "line ";

    /**
     * This method removes the message location prefix.
     * 
     * @param mesg The message
     * @return The message with the location information removed
     */
    public static String getMessageText(String mesg) {
        String ret=mesg;
        
        if (mesg != null && mesg.startsWith(LOCATION_PREFIX_TOKEN)) {
            int index=mesg.indexOf(END_LOCATION_SEPARATOR, LOCATION_PREFIX_TOKEN.length());
            
            if (index != -1) {
                ret = mesg.substring(index+1);
            }
        }
        
        return (ret);
    }
    
    /**
     * This method returns the properties.
     * 
     * @param mesg The message
     * @param document The document
     * @return The properties
     */
    public static java.util.Map<String, Object> getProperties(String mesg, String document) {
        java.util.Map<String, Object> ret=null;
        
        if (mesg != null && mesg.startsWith(LOCATION_PREFIX_TOKEN)) {
            int index=mesg.indexOf(END_LOCATION_SEPARATOR, LOCATION_PREFIX_TOKEN.length());
            
            if (index != -1) {
                String lineDetails=mesg.substring(LOCATION_PREFIX_TOKEN.length(), index);
                
                String[] nums=lineDetails.split(":");
                
                if (nums != null && nums.length == 2) {
                    ret = new java.util.HashMap<String, Object>();
                    
                    Integer line=Integer.parseInt(nums[0]);
                    Integer col=Integer.parseInt(nums[1]);
                    
                    ret.put(IssueLogger.START_LINE, line);
                    ret.put(IssueLogger.START_COLUMN, col);
                    
                    // Determine the position within the document
                    int pos=0;
                    
                    for (int i=0; pos != -1 && i < line.intValue()-1; i++) {
                        pos = document.indexOf("\n", pos);
                        
                        if (pos != -1) {
                            pos += 2;
                        }
                    }
                    
                    if (pos != -1) {
                        pos += col.intValue();
                        
                        ret.put(IssueLogger.START_POSITION, new Integer(pos));
                        
                        // Find next whitespace
                        int endpos=document.length();
                        int nextpos=-1;
                        
                        for (int j=pos+1; nextpos == -1 && j < endpos; j++) {
                            char ch=document.charAt(j);
                            if (Character.isWhitespace(ch)) {
                                nextpos = j-1;
                            }
                        }
                        
                        if (nextpos == -1) {
                            nextpos = pos;
                        }
                        
                        ret.put(IssueLogger.END_POSITION, new Integer(nextpos));
                    }
                }
            }
        }
        
        return (ret);
    }
}

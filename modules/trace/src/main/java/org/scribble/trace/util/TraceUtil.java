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
package org.scribble.trace.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.scribble.trace.model.Trace;

/**
 * This class provides trace utility functions.
 *
 */
public class TraceUtil {
	
    protected static final ObjectMapper MAPPER=new ObjectMapper();

    static {
        SerializationConfig config=MAPPER.getSerializationConfig()
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL)
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        
        MAPPER.setSerializationConfig(config);
	}
	
	/**
	 * This method returns the JSON representation of a trace.
	 * 
	 * @param trace The trace
	 * @return The JSON representation
	 * @throws Exception Failed to serialize
	 */
	public static byte[] serializeTrace(Trace trace) throws Exception {
		byte[] ret=null;
		
        java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
        
        MAPPER.writeValue(baos, trace);
        
        ret = baos.toByteArray();
        
        baos.close();
        
        return (ret);
	}
	
	/**
	 * This method returns the simulation list for a supplied JSON representation.
	 * 
	 * @param simulations The JSON representation
	 * @return The simulation list
	 * @throws Exception Failed to deserialize
	 */
	public static Trace deserializeTrace(byte[] trace) throws Exception {
		Trace ret=null;
		
        java.io.ByteArrayInputStream bais=new java.io.ByteArrayInputStream(trace);
        
        ret = MAPPER.readValue(bais, Trace.class);
        
        bais.close();
        
        return (ret);
	}
}

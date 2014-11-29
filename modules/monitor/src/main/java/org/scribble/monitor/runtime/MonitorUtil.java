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
package org.scribble.monitor.runtime;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * This class provides model utility functions.
 *
 */
public class MonitorUtil {
	
    protected static final ObjectMapper MAPPER=new ObjectMapper();

    static {
        SerializationConfig config=MAPPER.getSerializationConfig()
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL)
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        
        MAPPER.setSerializationConfig(config);
        
        MAPPER.configure(SerializationConfig.Feature.WRITE_EMPTY_JSON_ARRAYS, false);
	}
	
	/**
	 * This method returns the JSON representation of the session instance.
	 * 
	 * @param instance The session instance
	 * @return The JSON representation
	 * @throws Exception Failed to serialize
	 */
	public static byte[] serializeSessionInstance(SessionInstance instance) throws Exception {
		byte[] ret=null;
		
        java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
        
        MAPPER.writeValue(baos, instance);
        
        ret = baos.toByteArray();
        
        baos.close();
        
        return (ret);
	}
	
	/**
	 * This method returns the session instance of a supplied JSON representation.
	 * 
	 * @param instance The JSON representation
	 * @return The session instance
	 * @throws Exception Failed to deserialize
	 */
	public static SessionInstance deserializeSessionInstance(byte[] instance) throws Exception {
		SessionInstance ret=null;
		
        java.io.ByteArrayInputStream bais=new java.io.ByteArrayInputStream(instance);
        
        ret = MAPPER.readValue(bais, SessionInstance.class);
        
        bais.close();
        
        return (ret);
	}
	
}

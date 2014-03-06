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
package org.scribble.trace;

import static org.junit.Assert.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.scribble.trace.model.Trace;
import org.scribble.common.resources.DirectoryResourceLocator;

public class SimulatorTest {

	@org.junit.Test
    public void testRequestResponse() {
    	testSimulator("scribble.examples.RequestResponse@Buyer", "First", "trace1", true);
    }

    protected void testSimulator(String module, String protocol, String traceFile, boolean successful) {
    	
    	try {
    		java.net.URL url=ClassLoader.getSystemResource("scribble");
    		java.io.File f=new java.io.File(url.getFile());
    		
    		DirectoryResourceLocator locator=new DirectoryResourceLocator(f.getParentFile().getAbsolutePath());
    		
    		ObjectMapper mapper=new ObjectMapper();
    		mapper.configure(Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
    		
    		java.io.InputStream is=ClassLoader.getSystemResourceAsStream(module.replace('.', '/')+"."+traceFile);
    		    		
       		Trace trace=mapper.readValue(is, Trace.class);
    		
    		is.close();

    		SimulatorContext context=new DefaultSimulatorContext(locator);
    		
    		Simulator simulator=new Simulator();
    		
    		if (simulator.simulate(context, trace) != successful) {
    			fail("Simulation result for module '"+module+"' protocol '"+protocol+"' was not as expected");
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Failed to simulate module '"+module+"' protocol '"+protocol+"' and trace: "+traceFile);
    	}
    }
}

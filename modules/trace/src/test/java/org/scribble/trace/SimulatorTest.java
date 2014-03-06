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
    public void testChoice1() {
    	testSimulator("scribble.examples.Choice@Buyer", "First", 1, true);
    }

	@org.junit.Test
    public void testChoice2() {
    	testSimulator("scribble.examples.Choice@Buyer", "First", 2, true);
    }

	@org.junit.Test
    public void testDo1() {
    	testSimulator("scribble.examples.Do@Buyer", "First", 1, true);
    }

	@org.junit.Test
    public void testInterruptible1() {
    	testSimulator("scribble.examples.Interruptible@Buyer", "First", 1, true);
    }

	@org.junit.Test
    public void testInterruptible2() {
    	testSimulator("scribble.examples.Interruptible@Buyer", "First", 2, true);
    }

	@org.junit.Test
    public void testInterruptible3() {
    	testSimulator("scribble.examples.Interruptible@Buyer", "First", 3, true);
    }

	@org.junit.Test
    public void testParallel1() {
    	testSimulator("scribble.examples.Parallel@Buyer", "First", 1, true);
    }

	@org.junit.Test
    public void testParallel2() {
    	testSimulator("scribble.examples.Parallel@Buyer", "First", 2, true);
    }

	@org.junit.Test
    public void testRecursion1() {
    	testSimulator("scribble.examples.Recursion@Buyer", "First", 1, true);
    }

	@org.junit.Test
    public void testRecursion2() {
    	testSimulator("scribble.examples.Recursion@Buyer", "First", 2, true);
    }

	@org.junit.Test
    public void testRecursion3() {
    	testSimulator("scribble.examples.Recursion@Buyer", "First", 3, true);
    }

	@org.junit.Test
    public void testRequestResponse1() {
    	testSimulator("scribble.examples.RequestResponse@Buyer", "First", 1, true);
    }

	/**
	 * This test indicates that one of the steps is expected to fail.
	 */
	@org.junit.Test
    public void testRequestResponse2() {
    	testSimulator("scribble.examples.RequestResponse@Buyer", "First", 2, true);
    }

	/**
	 * This test checks that the simulation failed.
	 */
	@org.junit.Test
    public void testRequestResponse3() {
    	testSimulator("scribble.examples.RequestResponse@Buyer", "First", 3, false);
    }

    protected void testSimulator(String module, String protocol, int testnum, boolean successful) {
    	
    	try {
    		java.net.URL url=ClassLoader.getSystemResource("scribble");
    		java.io.File f=new java.io.File(url.getFile());
    		
    		DirectoryResourceLocator locator=new DirectoryResourceLocator(f.getParentFile().getAbsolutePath());
    		
    		ObjectMapper mapper=new ObjectMapper();
    		mapper.configure(Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
    		
    		java.io.InputStream is=ClassLoader.getSystemResourceAsStream(module.replace('.', '/')+
    									"-"+testnum+".trace");
    		    		
       		Trace trace=mapper.readValue(is, Trace.class);
    		
    		is.close();

    		SimulatorContext context=new DefaultSimulatorContext(locator);
    		
    		Simulator simulator=new Simulator();
    		
    		if (simulator.simulate(context, trace) != successful) {
    			fail("Simulation result for module '"+module+"' protocol '"+protocol+"' was not as expected");
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Failed to simulate module '"+module+"' protocol '"+protocol+"' and trace number: "+testnum);
    	}
    }
}

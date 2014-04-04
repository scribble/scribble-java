/*
 * Copyright 2009-14 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * Yolassu may obtain a copy of the License at
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
package org.scribble.editor.tools.simulation;

import org.scribble.resources.DirectoryResourceLocator;
import org.scribble.trace.simulation.junit.JUnitSimulator;

/**
 * This class provides the Eclipse extended version of the JUnit based
 * simulator.
 */
public class JUnitSimulatorMain extends JUnitSimulator {

	public static final String UPDATED_XML_NOTIFICATION = "UPDATED XML";

	/**
	 * This main method is invoked with the path of a single
	 * trace file, or a folder containing zero or more trace
	 * files.
	 * 
	 * @param args The path
	 */
	public static void main(String args[]) {
		if (args.length != 2) {
			System.err.println("Usage: JUnitSimulator path junitXmlFile");
			System.exit(1);
		}
		
		if (System.getProperty("MODULE_PATH") == null) {
			System.err.println("'MODULE_PATH' envionment parameter has not been set");
			System.exit(2);
		}
		
		JUnitSimulatorMain sim=new JUnitSimulatorMain();
		
		try {
			DirectoryResourceLocator locator=new DirectoryResourceLocator(System.getProperty("MODULE_PATH"));
			
			sim.setResourceLocator(locator);
			
			sim.simulate(args[0], args[1]);
			
			synchronized (sim) {
				sim.wait(2000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

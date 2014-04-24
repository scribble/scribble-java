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
package org.scribble.monitor.export;

import static org.junit.Assert.*;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.scribble.context.DefaultModuleContext;
import org.scribble.logging.ConsoleIssueLogger;
import org.scribble.model.Module;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.monitor.model.SessionType;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;
import org.scribble.resources.DirectoryResourceLocator;
import org.scribble.resources.InputStreamResource;

public class MonitorExporterTest {

	@org.junit.Test
    public void testRequestResponse() {
    	testMonitorExporter("RequestResponse", "Buyer");
    }

	@org.junit.Test
    public void testChoice() {
    	testMonitorExporter("Choice", "Buyer");
    }

	@org.junit.Test
    public void testParallel() {
    	testMonitorExporter("Parallel", "Buyer");
    }

	@org.junit.Test
    public void testDo() {
    	testMonitorExporter("Do", "Buyer");
    }

	@org.junit.Test
    public void testRecursion() {
    	testMonitorExporter("Recursion", "Buyer");
    }

	@org.junit.Test
    public void testInterruptible() {
    	testMonitorExporter("Interruptible", "Buyer");
    }

	@org.junit.Test
    public void testInterruptible2() {
    	testMonitorExporter("Interruptible2", "Buyer");
    }

    protected void testMonitorExporter(String name, String role) {
    	
    	try {
    		String path="scribble/examples/"+name+"_"+role+".scr";
    		
    		java.net.URL scrurl=ClassLoader.getSystemResource(path);
    		java.io.File scrFile=new java.io.File(scrurl.getFile());
    		java.io.InputStream is=new java.io.FileInputStream(scrFile);
    		
    		java.net.URL url=ClassLoader.getSystemResource("scribble");
    		java.io.File f=new java.io.File(url.getFile());
    		
    		ProtocolParser pp=new ProtocolParser();
    		
    		TestIssueLogger logger=new TestIssueLogger();
    		
    		DirectoryResourceLocator locator=new DirectoryResourceLocator(f.getParentFile().getAbsolutePath());
    		
    		ProtocolModuleLoader loader=new ProtocolModuleLoader(pp, locator, logger);
    		
    		InputStreamResource isr=new InputStreamResource(path, is);
    		
    		Module module=pp.parse(isr, loader, logger);
    		
    		if (module == null) {
    			fail("Module is null");
    		}
    		
    		if (logger.isErrorsOrWarnings()) {
    			fail("Unexpected errors and/or warnings in "+name+"_"+role+".scr");
    		}
    		
    		// Get first local protocol
    		LProtocolDefinition lp=(LProtocolDefinition)module.getProtocols().get(0);
    		
    		MonitorExporter exporter=new MonitorExporter();
    		
    		DefaultModuleContext context=new DefaultModuleContext(isr, lp.getModule(), loader);
    		
    		SessionType type=exporter.export(context, lp);
    		
    		is = ClassLoader.getSystemResourceAsStream("scribble/examples/"+name+"_"+role+".monitor");
    		
    		byte[] b=new byte[is.available()];
    		is.read(b);
    		
    		is.close();
    		
    		ObjectMapper mapper=new ObjectMapper();
    		mapper.configure(Feature.SORT_PROPERTIES_ALPHABETICALLY, true);

    		String monitor=mapper.writeValueAsString(type);
    		String expecting=new String(b).trim();
    		
    		if (!monitor.equals(expecting)) {
    			int len=monitor.length();
    			if (len > expecting.length()) {
    				len = expecting.length();
    			}
    			for (int i=0; i < len; i++) {
    				if (monitor.charAt(i) != expecting.charAt(i)) {
    					System.out.println("DIFF AT POSITION: "+i);
    					int showto=i+30;
    					if (i+10 >= len) {
    						showto = len;
    					}    					
    					System.out.println("EXPORTED: "+monitor.substring(i, showto));
    					System.out.println("EXPECTED: "+expecting.substring(i, showto));
    					break;
    				}
    			}
    			
    			System.err.println("Exported monitor representation '"+name+
    					"_"+role+"' mismatch\nExpecting:\n"+expecting+"\nExported:\n"+monitor);
    			fail("Monitor export '"+name+"_"+role+"' mismatch");
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Failed to export monitor '"+name+"_"+role+".scr'");
    	}
    }
    
    protected class TestIssueLogger extends ConsoleIssueLogger {
    	
    	private java.util.List<String> _errors=new java.util.ArrayList<String>();
    	private java.util.List<String> _warnings=new java.util.ArrayList<String>();

		public void error(String issue, Map<String, Object> props) {
			super.error(issue, props);
			_errors.add(issue);
		}

		public void warning(String issue, Map<String, Object> props) {
			super.warning(issue, props);
			_warnings.add(issue);
		}

		public void info(String issue, Map<String, Object> props) {
			super.info(issue, props);
		}
    	
		public boolean isErrorsOrWarnings() {
			return (_errors.size() > 0 || _warnings.size() > 0);
		}
    }
}

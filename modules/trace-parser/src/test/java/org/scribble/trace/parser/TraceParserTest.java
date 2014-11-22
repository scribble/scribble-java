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
package org.scribble.trace.parser;

import static org.junit.Assert.*;

import java.util.Map;

import org.scribble.logging.ConsoleIssueLogger;
import org.scribble.resources.InputStreamResource;
import org.scribble.trace.model.Trace;

public class TraceParserTest {

    @org.junit.Test
    public void testChoiceBuyer1() {
   		testExample("ChoiceBuyer1");
   	}

    protected void testExample(String name) {
    	testParser("scribble/examples/"+name+".trace");
    }
    		
    protected void testIssue(String name) {
    	testParser("scribble/issues/"+name+".trace");
    }
    		
    protected void testParser(String path) {
    	
    	try {    		
    		java.net.URL scrurl=ClassLoader.getSystemResource(path);
    		java.io.File scrFile=new java.io.File(scrurl.getFile());
    		java.io.InputStream is=new java.io.FileInputStream(scrFile);
    		
    		TraceParser tp=new TraceParser();
    		
    		TestIssueLogger logger=new TestIssueLogger();
    		
    		InputStreamResource isr=new InputStreamResource(path, is);
    		
    		Trace trace=tp.parse(isr, logger);
    		
    		if (trace == null) {
    			fail("Trace is null");
    		}
    		
    		if (logger.isErrorsOrWarnings()) {
    			fail("Unexpected errors and/or warnings in "+path);
    		}
    		
    		is = ClassLoader.getSystemResourceAsStream(path);
    		
    		byte[] b=new byte[is.available()];
    		is.read(b);
    		
    		is.close();
    		
    		String parsed=trace.toString().trim();
    		String expecting=new String(b).replace("\r", "").trim();
    		
    		if (!parsed.equals(expecting)) {
    			int len=parsed.length();
    			if (len > expecting.length()) {
    				len = expecting.length();
    			}
    			for (int i=0; i < len; i++) {
    				if (parsed.charAt(i) != expecting.charAt(i)) {
    					System.out.println("DIFF AT POSITION: "+i);
    					int showto=i+30;
    					if (showto >= len) {
    						showto = len;
    					}    					
    					System.out.println("PARSED: "+parsed.substring(i, showto));
    					System.out.println("EXPECT: "+expecting.substring(i, showto));
    					break;
    				}
    			}
    			
    			System.err.println("Parsed protocol '"+path+
    					"' mismatch\nExpecting:\n"+expecting+"\nParsed:\n"+parsed);
    			fail("Parsed protocol '"+path+"' mismatch");
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Failed to parse '"+path+"'");
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

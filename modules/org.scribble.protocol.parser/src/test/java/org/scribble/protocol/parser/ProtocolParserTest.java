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
package org.scribble.protocol.parser;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Ignore;
import org.scribble.protocol.model.Module;

public class ProtocolParserTest {

    @org.junit.Test
    @Ignore
    public void testTypes() {
    	testParser("Types");
    }

    @org.junit.Test
    @Ignore
    public void testImports() {
    	testParser("Imports");
    }

    @org.junit.Test
    @Ignore
    public void testGMessage() {
    	testParser("GMessage");
    }

    @org.junit.Test
    @Ignore
    public void testGChoice() {
    	testParser("GChoice");
    }

    @org.junit.Test
    @Ignore
    public void testGRecursion() {
    	testParser("GRecursion");
    }
    
    @org.junit.Test
    @Ignore
    public void testGParallel() {
    	testParser("GParallel");
    }
    
    @org.junit.Test
    public void testGDo() {
    	testParser("GDo");
    }

    /*
    /*
    @org.junit.Test
    public void testGSpawn() {
    	testParser("GSpawn");
    }

    @org.junit.Test
    public void testLCreate() {
    	testParser("LCreate");
    }

    @org.junit.Test
    public void testLEnter() {
    	testParser("LEnter");
    }

    @org.junit.Test
    public void testLSend() {
    	testParser("LSend");
    }
    
    @org.junit.Test
    public void testLReceive() {
    	testParser("LReceive");
    }

    @org.junit.Test
    public void testLChoice() {
    	testParser("LChoice");
    }
    
    @org.junit.Test
    public void testLParallel() {
    	testParser("LParallel");
    }

    @org.junit.Test
    public void testLRecursion() {
    	testParser("LRecursion");
    }
    */
    
    protected void testParser(String name) {
    	
    	try {
    		java.io.InputStream is=ClassLoader.getSystemResourceAsStream("scribble/examples/"+name+".scr");
    		
    		java.net.URL url=ClassLoader.getSystemResource("scribble");
    		java.io.File f=new java.io.File(url.getFile());
    		
    		ProtocolParser pp=new ProtocolParser();
    		
    		TestIssueLogger logger=new TestIssueLogger();
    		
    		DirectoryResourceLocator locator=new DirectoryResourceLocator(f.getParentFile().getAbsolutePath());
    		
    		Module module=pp.parse(is, locator, logger);
    		
    		if (module == null) {
    			fail("Module is null");
    		}
    		
    		if (logger.isErrorsOrWarnings()) {
    			fail("Unexpected errors and/or warnings in "+name+".scr");
    		}
    		
    		is = ClassLoader.getSystemResourceAsStream("scribble/examples/"+name+".scr");
    		
    		byte[] b=new byte[is.available()];
    		is.read(b);
    		
    		is.close();
    		
    		String parsed=module.toString().trim();
    		String expecting=new String(b).trim();
    		
    		if (!parsed.equals(expecting)) {
    			int len=parsed.length();
    			if (len > expecting.length()) {
    				len = expecting.length();
    			}
    			for (int i=0; i < len; i++) {
    				if (parsed.charAt(i) != expecting.charAt(i)) {
    					System.out.println("DIFF AT POSITION: "+i);
    					int showto=i+30;
    					if (i+10 >= len) {
    						showto = len;
    					}    					
    					System.out.println("PARSED: "+parsed.substring(i, showto));
    					System.out.println("EXPECT: "+expecting.substring(i, showto));
    					break;
    				}
    			}
    			
    			System.err.println("Parsed protocol '"+name+
    					"' mismatch\nExpecting:\n"+expecting+"\nParsed:\n"+parsed);
    			fail("Parsed protocol '"+name+"' mismatch");
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Failed to parse '"+name+".spr'");
    	}
    }
    
    protected class TestIssueLogger extends ConsoleParserLogger {
    	
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

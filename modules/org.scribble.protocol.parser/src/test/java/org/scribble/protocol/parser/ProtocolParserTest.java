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

import org.scribble.protocol.model.Module;

public class ProtocolParserTest {
    
    @org.junit.Test
    public void testGChoice() {
    	
    	try {
    		java.io.InputStream is=ClassLoader.getSystemResourceAsStream("org/scribble/protocol/parser/GChoice.spr");
    		
    		ProtocolParser pp=new ProtocolParser();
    		
    		IssueLogger logger=new ConsoleIssueLogger();
    		
    		Module module=pp.parse(is, null, logger);
    		
    		System.out.println("MODULE="+module);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    protected class TestIssueLogger implements IssueLogger {

		public void error(String issue, Map<String, Object> props) {
			// TODO Auto-generated method stub
			
		}

		public void warning(String issue, Map<String, Object> props) {
			// TODO Auto-generated method stub
			
		}

		public void info(String issue, Map<String, Object> props) {
			// TODO Auto-generated method stub
			
		}
    	
    }
}

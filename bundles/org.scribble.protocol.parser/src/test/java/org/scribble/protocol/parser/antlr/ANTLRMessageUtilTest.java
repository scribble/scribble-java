/*
 * Copyright 2009-10 www.scribble.org
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

import static org.junit.Assert.*;

import org.scribble.common.logging.Journal;

public class ANTLRMessageUtilTest {

	@org.junit.Test
	public void testGetMessageText() {
		String message="Hello World";
		String text="line 1:1 "+message;
		
		String result=ANTLRMessageUtil.getMessageText(text);
		
		if (result == null) {
			fail("Result is null");
		}
		
		if (result.equals(message) == false) {
			fail("Message mismatch, '"+result+"' against expected '"+message+"'");
		}
	}
	
	@org.junit.Test
	public void testGetProperties() {
		String first="namespace x.y.z;\r\nimport a.b.c;\r\n\r\nprotocol Test {\r\n\t";
		String second="roleX A, B;\r\n;\r\n";
		String document=first+second;
		
		String message="Don't understand roleX";
		String line="5";
		String col="1";
		String text="line "+line+":"+col+" "+message;
		
		java.util.Map<String,Object> result=ANTLRMessageUtil.getProperties(text, document);
		
		if (result == null) {
			fail("Result is null");
		}

		Integer lineProp=(Integer)result.get(Journal.START_LINE);
		Integer colProp=(Integer)result.get(Journal.START_COLUMN);
		Integer posProp=(Integer)result.get(Journal.START_POSITION);
		
		if (lineProp == null) {
			fail("Line not found");
		}
		
		if (lineProp.intValue() != 5) {
			fail("Line is not 5: "+lineProp);
		}
		
		if (colProp == null) {
			fail("Column not found");
		}
		
		if (colProp.intValue() != 1) {
			fail("Column is not 1: "+colProp);
		}
		
		if (posProp == null) {
			fail("Position not found");
		}
		
		if (posProp.intValue() != first.length()) {
			fail("Position is not "+first.length()+": "+posProp);
		}
	}	
}

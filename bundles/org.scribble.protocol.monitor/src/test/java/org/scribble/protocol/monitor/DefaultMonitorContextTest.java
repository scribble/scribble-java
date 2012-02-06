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
package org.scribble.protocol.monitor;

import static org.junit.Assert.*;

import org.junit.Test;

public class DefaultMonitorContextTest {

	@Test
	public void testLinkTrue() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", Boolean.TRUE);
		
		Boolean result=context.evaluate(session, "X");
		
		if (result == null) {
			fail("Result was null");
		}
		
		if (!result.booleanValue()) {
			fail("Result should be true");
		}
	}

	@Test
	public void testLinkFalse() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", Boolean.FALSE);
		
		Boolean result=context.evaluate(session, "X");
		
		if (result == null) {
			fail("Result was null");
		}
		
		if (result.booleanValue()) {
			fail("Result should be false");
		}
	}

	@Test
	public void testLinkUnset() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", null);
		
		Boolean result=context.evaluate(session, "X");
		
		if (result != null) {
			fail("Result was NOT null");
		}
	}

	@Test
	public void testLinkANDTrueNull() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", null);
		session.setState("Y", Boolean.TRUE);
		
		Boolean result=context.evaluate(session, "X and Y");
		
		if (result != null) {
			fail("Result should be null");
		}
	}

	@Test
	public void testLinkANDFalseNull() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", null);
		session.setState("Y", Boolean.FALSE);
		
		Boolean result=context.evaluate(session, "X and Y");
		
		if (result != null) {
			fail("Result should be null");
		}
	}

	@Test
	public void testLinkANDTrueFalse() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", Boolean.FALSE);
		session.setState("Y", Boolean.TRUE);
		
		Boolean result=context.evaluate(session, "X and Y");
		
		if (result == null) {
			fail("Result was null");
		}
		
		if (result.booleanValue()) {
			fail("Result should be false");
		}
	}

	@Test
	public void testLinkANDTrueTrue() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", Boolean.TRUE);
		session.setState("Y", Boolean.TRUE);
		
		Boolean result=context.evaluate(session, "X and Y");
		
		if (result == null) {
			fail("Result was null");
		}
		
		if (!result.booleanValue()) {
			fail("Result should be true");
		}
	}

	@Test
	public void testLinkORTrue1() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", null);
		session.setState("Y", Boolean.TRUE);
		
		Boolean result=context.evaluate(session, "X or Y");
		
		if (result == null) {
			fail("Result was null");
		}
		
		if (!result.booleanValue()) {
			fail("Result should be true");
		}
	}

	@Test
	public void testLinkORTrue2() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", Boolean.FALSE);
		session.setState("Y", Boolean.TRUE);
		
		Boolean result=context.evaluate(session, "X or Y");
		
		if (result == null) {
			fail("Result was null");
		}
		
		if (!result.booleanValue()) {
			fail("Result should be true");
		}
	}

	@Test
	public void testLinkORNull() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", Boolean.FALSE);
		session.setState("Y", null);
		
		// Result should be null, as still chance that result
		// could become true
		Boolean result=context.evaluate(session, "X or Y");
		
		if (result != null) {
			fail("Result should be null");
		}
	}

	@Test
	public void testLinkORFalse() {
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession session=new DefaultSession();
		session.setState("X", Boolean.FALSE);
		session.setState("Y", Boolean.FALSE);
		
		Boolean result=context.evaluate(session, "X or Y");
		
		if (result == null) {
			fail("Result was null");
		}
		
		if (result.booleanValue()) {
			fail("Result should be false");
		}
	}
}

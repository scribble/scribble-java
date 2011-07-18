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

public class DefaultSessionTest {

	@Test
	public void testSerializeSimple() {
		DefaultSession session=new DefaultSession();
		
		session.addNodeIndex(1);
		session.addNodeIndex(2);
		
		try {
			java.io.ByteArrayOutputStream os=new java.io.ByteArrayOutputStream();
			java.io.ObjectOutputStream oos=new java.io.ObjectOutputStream(os);
			
			oos.writeObject(session);
			
			java.io.ByteArrayInputStream is=new java.io.ByteArrayInputStream(os.toByteArray());
			
			java.io.ObjectInputStream ois=new java.io.ObjectInputStream(is);
			
			DefaultSession result=(DefaultSession)ois.readObject();
			
			validateSessions(result, session);
		
		} catch(Exception e) {
			fail("Failed to serialize: "+e);
		}
	}

	@Test
	public void testSerializeNested() {
		DefaultSession session=new DefaultSession();
		
		session.addNodeIndex(1);
		session.addNodeIndex(2);
		
		DefaultSession nested1=(DefaultSession)session.createNestedConversation(3);
		nested1.addNodeIndex(4);
		
		session.getNestedConversations().add(nested1);
		
		try {
			java.io.ByteArrayOutputStream os=new java.io.ByteArrayOutputStream();
			java.io.ObjectOutputStream oos=new java.io.ObjectOutputStream(os);
			
			oos.writeObject(session);
			
			java.io.ByteArrayInputStream is=new java.io.ByteArrayInputStream(os.toByteArray());
			
			java.io.ObjectInputStream ois=new java.io.ObjectInputStream(is);
			
			DefaultSession result=(DefaultSession)ois.readObject();
			
			validateSessions(result, session);
		
		} catch(Exception e) {
			fail("Failed to serialize: "+e);
		}
	}

	@Test
	public void testSerializeInterrupt() {
		DefaultSession session=new DefaultSession();
		
		session.addNodeIndex(1);
		session.addNodeIndex(2);
		
		DefaultSession main=(DefaultSession)session.createNestedConversation(6);
		
		DefaultSession interrupt1=(DefaultSession)session.createInterruptConversation(main, 4);
		interrupt1.addNodeIndex(5);
		
		try {
			java.io.ByteArrayOutputStream os=new java.io.ByteArrayOutputStream();
			java.io.ObjectOutputStream oos=new java.io.ObjectOutputStream(os);
			
			oos.writeObject(session);
			
			java.io.ByteArrayInputStream is=new java.io.ByteArrayInputStream(os.toByteArray());
			
			java.io.ObjectInputStream ois=new java.io.ObjectInputStream(is);
			
			DefaultSession result=(DefaultSession)ois.readObject();
			
			validateSessions(result, session);
		
		} catch(Exception e) {
			fail("Failed to serialize: "+e);
		}
	}

	protected void validateSessions(DefaultSession result, DefaultSession session) {

		if (result.getNumberOfNodeIndexes() != session.getNumberOfNodeIndexes()) {
			fail("Number of node indexes different");
		}
		
		for (int i=0; i < result.getNumberOfNodeIndexes(); i++) {
			if (result.getNodeIndexAt(i) != session.getNodeIndexAt(i)) {
				fail("Node index ["+i+"] is wrong");
			}
		}
		
		if (result.getReturnIndex() != session.getReturnIndex()) {
			fail("Return index different");
		}
		
		if ((result.getParentConversation() != null && session.getParentConversation() == null) ||
				(result.getParentConversation() == null && session.getParentConversation() != null)) {
			fail("Parent conversation not null");
		}
		
		if (result.getNestedConversations().size() != session.getNestedConversations().size()) {
			fail("Nested conversation size different");
		}
		
		for (int i=0; i < result.getNestedConversations().size(); i++) {
			validateSessions((DefaultSession)result.getNestedConversations().get(i),
							(DefaultSession)session.getNestedConversations().get(i));
		}
		
		if (result.getInterruptConversations().size() != session.getInterruptConversations().size()) {
			fail("Interrupt conversation size different");
		}

		for (int i=0; i < result.getInterruptConversations().size(); i++) {
			validateSessions((DefaultSession)result.getInterruptConversations().get(i),
							(DefaultSession)session.getInterruptConversations().get(i));
		}
	}
}

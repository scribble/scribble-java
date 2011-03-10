/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.command.simulate;

import static org.junit.Assert.*;

public class EventProcessorTest {

	@org.junit.Test
	public void testMonitorSendReceiveMessage() {
		String str="sendMessage,Order\r\nreceiveMessage,Confirmation\r\n";
		
		java.io.ByteArrayInputStream bais=new java.io.ByteArrayInputStream(str.getBytes());
		
		EventProcessor ep=new EventProcessor();
		
		try {
			ep.initialize(bais);
			bais.close();
		} catch(Exception e) {
			fail("Failed to initialize event processor: "+e);
		}
		
		java.util.List<Event> events=ep.getEvents();
		
		if (events.size() != 2) {
			fail("Should only be 2 events");
		}
		
		if ((events.get(0) instanceof SendMessage) == false) {
			fail("First event should be send message");
		}
		
		SendMessage evt0=(SendMessage)events.get(0);
		
		if (evt0.getMessage().getOperator() != null) {
			fail("Operator should not be set");
		}
		
		if (evt0.getMessage().getTypes().size() != 1) {
			fail("Should be only 1 type");
		}
		
		if (evt0.getMessage().getTypes().contains("Order") == false) {
			fail("Parameter type should be Order");
		}
		
		if ((events.get(1) instanceof ReceiveMessage) == false) {
			fail("Second event should be receive message");
		}
		
		ReceiveMessage evt1=(ReceiveMessage)events.get(1);
		
		if (evt1.getMessage().getOperator() != null) {
			fail("Operator should not be set");
		}
		
		if (evt1.getMessage().getTypes().size() != 1) {
			fail("Should only be 1 type");
		}		
		
		if (evt1.getMessage().getTypes().contains("Confirmation") == false) {
			fail("Parameter type should be Confirmation");
		}		
	}
	
	@org.junit.Test
	public void testMonitorSendReceiveMessageWithOperator() {
		String str="sendMessage,op1(Order)\r\nreceiveMessage,op2(Confirmation)\r\n";
		
		java.io.ByteArrayInputStream bais=new java.io.ByteArrayInputStream(str.getBytes());
		
		EventProcessor ep=new EventProcessor();
		
		try {
			ep.initialize(bais);
			bais.close();
		} catch(Exception e) {
			fail("Failed to initialize event processor: "+e);
		}
		
		java.util.List<Event> events=ep.getEvents();
		
		if (events.size() != 2) {
			fail("Should only be 2 events");
		}
		
		if ((events.get(0) instanceof SendMessage) == false) {
			fail("First event should be send message");
		}
		
		SendMessage evt0=(SendMessage)events.get(0);
		
		if (evt0.getMessage().getOperator().equals("op1") == false) {
			fail("Operator should be op1");
		}
		
		if (evt0.getMessage().getTypes().size() != 1) {
			fail("Should be only 1 type");
		}
		
		if (evt0.getMessage().getTypes().contains("Order") == false) {
			fail("Parameter type should be Order");
		}
		
		if ((events.get(1) instanceof ReceiveMessage) == false) {
			fail("Second event should be receive message");
		}
		
		ReceiveMessage evt1=(ReceiveMessage)events.get(1);
		
		if (evt1.getMessage().getOperator().equals("op2") == false) {
			fail("Operator should be op2");
		}
		
		if (evt1.getMessage().getTypes().size() != 1) {
			fail("Should be only 1 type");
		}		
		
		if (evt1.getMessage().getTypes().contains("Confirmation") == false) {
			fail("Parameter type should be Confirmation");
		}		
	}
	
	@org.junit.Test
	public void testMonitorSendReceiveChoice() {
		String str="sendChoice,Order\r\nreceiveChoice,Confirmation\r\n";
		
		java.io.ByteArrayInputStream bais=new java.io.ByteArrayInputStream(str.getBytes());
		
		EventProcessor ep=new EventProcessor();
		
		try {
			ep.initialize(bais);
			bais.close();
		} catch(Exception e) {
			fail("Failed to initialize event processor: "+e);
		}
		
		java.util.List<Event> events=ep.getEvents();
		
		if (events.size() != 2) {
			fail("Should only be 2 events");
		}
		
		if ((events.get(0) instanceof SendChoice) == false) {
			fail("First event should be send choice");
		}
		
		SendChoice evt0=(SendChoice)events.get(0);
		
		if (evt0.getLabel().equals("Order") == false) {
			fail("Label should be Order");
		}
		
		if ((events.get(1) instanceof ReceiveChoice) == false) {
			fail("Second event should be receive choice");
		}
		
		ReceiveChoice evt1=(ReceiveChoice)events.get(1);
		
		if (evt1.getLabel().equals("Confirmation") == false) {
			fail("Label should be Confirmation");
		}		
	}
	
	@org.junit.Test
	public void testMonitorSendReceiveDecision() {
		String str="sendDecision,false\r\nreceiveDecision,true\r\n";
		
		java.io.ByteArrayInputStream bais=new java.io.ByteArrayInputStream(str.getBytes());
		
		EventProcessor ep=new EventProcessor();
		
		try {
			ep.initialize(bais);
			bais.close();
		} catch(Exception e) {
			fail("Failed to initialize event processor: "+e);
		}
		
		java.util.List<Event> events=ep.getEvents();
		
		if (events.size() != 2) {
			fail("Should only be 2 events");
		}
		
		if ((events.get(0) instanceof SendDecision) == false) {
			fail("First event should be send decision");
		}
		
		SendDecision evt0=(SendDecision)events.get(0);
		
		if (evt0.getDecision() == true) {
			fail("Decision should be false");
		}
		
		if ((events.get(1) instanceof ReceiveDecision) == false) {
			fail("Second event should be receive decision");
		}
		
		ReceiveDecision evt1=(ReceiveDecision)events.get(1);
		
		if (evt1.getDecision() == false) {
			fail("Decision should be true");
		}		
	}
}

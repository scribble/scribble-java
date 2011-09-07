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
package org.scribble.protocol.monitor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.protocol.monitor.model.*;


public class SellerParticipantTest {

	private static final String OUT_OF_STOCK_MESSAGE_TYPE = "OutOfStock";
	private static final String CONFIRMATION_MESSAGE_TYPE = "Confirmation";
	private static final String ORDER_MESSAGE_TYPE = "Order";

	public enum PurchasingLabel {
		_Confirmation,
		_OutOfStock
	}
	
	public Description getSellerProtocol() {
		Description pd=new Description();
		
		ReceiveMessage recvOrder=new ReceiveMessage();
		//recvOrder.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		recvOrder.setNextIndex(1);

		MessageType mt1=new MessageType();
		mt1.setValue(ORDER_MESSAGE_TYPE);
		recvOrder.getMessageType().add(mt1);
		pd.getNode().add(recvOrder);
		
		Choice choice=new Choice();
		//choice.setNodeType(Protocol.Node.NodeType.SendChoice);

		Path c1=new Path();
		//c1.setId(PurchasingLabel._Confirmation.name());
		c1.setNextIndex(2);
		choice.getPath().add(c1);
		
		Path c2=new Path();
		//c2.setId(PurchasingLabel._OutOfStock.name());
		c2.setNextIndex(3);
		choice.getPath().add(c2);
						
		pd.getNode().add(choice);
		
		SendMessage sendConformation=new SendMessage();
		//sendConformation.setNodeType(Protocol.Node.NodeType.SendMessage);

		MessageType mt2=new MessageType();
		mt2.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendConformation.getMessageType().add(mt2);
		pd.getNode().add(sendConformation);
		
		SendMessage sendOutOfStock=new SendMessage();
		//sendOutOfStock.setNodeType(Protocol.Node.NodeType.SendMessage);

		MessageType mt3=new MessageType();
		mt3.setValue(OUT_OF_STOCK_MESSAGE_TYPE);
		sendOutOfStock.getMessageType().add(mt3);
		pd.getNode().add(sendOutOfStock);
		
		return(pd);
	}	
	
	@Test
	public void testSellerValidOrderConfirmation() {
		Description pd=getSellerProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
			fail("Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);		

		if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
			fail("Confirmation failed");
		}		
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testSellerValidOrderConfirmationLookahead() {
		Description pd=getSellerProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
				
		if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
			fail("Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
			fail("Confirmation failed");
		}		
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testSellerInvalidOrderConfirmation() {
		Description pd=getSellerProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
			fail("Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, message).isValid() == true) {
			fail("Second order should have failed");
		}		
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}
}

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

public class RunTest {

	private static final String CREDIT_OK_MESSAGE_TYPE = "CreditOk";
	private static final String CREDIT_CHECK_MESSAGE_TYPE = "CreditCheck";
	private static final String ORDER_MESSAGE_TYPE = "Order";
	private static final String CONFIRMATION_MESSAGE_TYPE = "Confirmation";

	public enum PurchasingLabel {
		_CreditOk,
		_InsufficientCredit,
		_Confirmation,
		_OutOfStock,
	}
	
	public Description getSendProtocol() {
		Description pd=new Description();
		
		// 0
		ReceiveMessage recvOrder=new ReceiveMessage();
		//recvOrder.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		recvOrder.setNextIndex(1);
		
		MessageType mt1=new MessageType();
		mt1.setValue(ORDER_MESSAGE_TYPE);
		recvOrder.getMessageType().add(mt1);
		pd.getNode().add(recvOrder);

		// 1
		Scope run=new Scope();
		//run.setNodeType(Protocol.Node.NodeType.Run);
		run.setNextIndex(2);
		run.setInnerIndex(3);
		pd.getNode().add(run);
		
		// 2
		SendMessage sendOrder=new SendMessage();
		//sendOrder.setNodeType(Protocol.Node.NodeType.SendMessage);
		
		MessageType mt2=new MessageType();
		mt2.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendOrder.getMessageType().add(mt2);
		pd.getNode().add(sendOrder);
		
		// 3
		SendMessage sendCreditCheck=new SendMessage();
		//sendCreditCheck.setNodeType(Protocol.Node.NodeType.SendMessage);
		sendCreditCheck.setNextIndex(4);
		
		MessageType mt3=new MessageType();
		mt3.setValue(CREDIT_CHECK_MESSAGE_TYPE);
		sendCreditCheck.getMessageType().add(mt3);
		pd.getNode().add(sendCreditCheck);
		
		// 4
		ReceiveMessage recvCreditOk=new ReceiveMessage();
		//recvCreditOk.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt4=new MessageType();
		mt4.setValue(CREDIT_OK_MESSAGE_TYPE);
		recvCreditOk.getMessageType().add(mt4);
		pd.getNode().add(recvCreditOk);
		
		return(pd);
	}	
	
	public Description getReceiveProtocol() {
		Description pd=new Description();
		
		// 0
		SendMessage recvOrder=new SendMessage();
		//recvOrder.setNodeType(Protocol.Node.NodeType.SendMessage);
		recvOrder.setNextIndex(1);
		
		MessageType mt1=new MessageType();
		mt1.setValue(ORDER_MESSAGE_TYPE);
		recvOrder.getMessageType().add(mt1);
		pd.getNode().add(recvOrder);

		// 1
		Scope run=new Scope();
		//run.setNodeType(Protocol.Node.NodeType.Run);
		run.setNextIndex(2);
		run.setInnerIndex(3);
		pd.getNode().add(run);
		
		// 2
		ReceiveMessage sendOrder=new ReceiveMessage();
		//sendOrder.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt2=new MessageType();
		mt2.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendOrder.getMessageType().add(mt2);
		pd.getNode().add(sendOrder);
		
		// 3
		ReceiveMessage sendCreditCheck=new ReceiveMessage();
		//sendCreditCheck.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		sendCreditCheck.setNextIndex(4);
		
		MessageType mt3=new MessageType();
		mt3.setValue(CREDIT_CHECK_MESSAGE_TYPE);
		sendCreditCheck.getMessageType().add(mt3);
		pd.getNode().add(sendCreditCheck);
		
		// 4
		SendMessage recvCreditOk=new SendMessage();
		//recvCreditOk.setNodeType(Protocol.Node.NodeType.SendMessage);
		
		MessageType mt4=new MessageType();
		mt4.setValue(CREDIT_OK_MESSAGE_TYPE);
		recvCreditOk.getMessageType().add(mt4);
		pd.getNode().add(recvCreditOk);
		
		return(pd);
	}	
	
	@Test
	public void testRunSendValidOrderCreditCheckOk() {
		Description pd=getSendProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Send Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testRunSendInvalidOrderCreditCheckOk() {
		Description pd=getSendProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == true) {
			fail("Send Confirmation should have failed");
		}
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}
	@Test
	public void testRunReceiveValidOrderCreditCheckOk() {
		Description pd=getReceiveProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Receive Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testRunReceiveInvalidOrderCreditCheckOk() {
		Description pd=getReceiveProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == true) {
			fail("Receive Confirmation should have failed");
		}
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}
}

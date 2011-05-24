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

public class OptionalWithParticipantTest {

	private static final String OTHER_ROLE = "OtherParticipant";
	private static final String INVALID_ROLE = "InvalidParticipant";
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
	
	public Description getSendDecisionProtocol() {
		Description pd=new Description();
		
		// 0
		ReceiveMessage recvOrder=new ReceiveMessage();
		//recvOrder.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		recvOrder.setNextIndex(1);

		MessageType mt1=new MessageType();
		mt1.setValue(ORDER_MESSAGE_TYPE);
		recvOrder.getMessageType().add(mt1);
		recvOrder.setOtherRole(OTHER_ROLE);
		pd.getNode().add(recvOrder);

		// 1
		Decision conditional=new Decision();
		//conditional.setNodeType(Protocol.Node.NodeType.SendDecision);
		conditional.setNextIndex(4);
		conditional.setInnerIndex(2);
		//conditional.setOtherRole(OTHER_ROLE);
		pd.getNode().add(conditional);
		
		// 2
		SendMessage sendCreditCheck=new SendMessage();
		//sendCreditCheck.setNodeType(Protocol.Node.NodeType.SendMessage);
		sendCreditCheck.setNextIndex(3);

		MessageType mt2=new MessageType();
		mt2.setValue(CREDIT_CHECK_MESSAGE_TYPE);
		sendCreditCheck.getMessageType().add(mt2);
		sendCreditCheck.setOtherRole(OTHER_ROLE);
		pd.getNode().add(sendCreditCheck);
		
		// 3
		ReceiveMessage recvCreditOk=new ReceiveMessage();
		//recvCreditOk.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		recvCreditOk.setNextIndex(4);

		MessageType mt3=new MessageType();
		mt3.setValue(CREDIT_OK_MESSAGE_TYPE);
		recvCreditOk.getMessageType().add(mt3);
		recvCreditOk.setOtherRole(OTHER_ROLE);
		pd.getNode().add(recvCreditOk);
		
		// 4
		SendMessage sendOrder=new SendMessage();
		//sendOrder.setNodeType(Protocol.Node.NodeType.SendMessage);

		MessageType mt4=new MessageType();
		mt4.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendOrder.getMessageType().add(mt4);
		sendOrder.setOtherRole(OTHER_ROLE);
		pd.getNode().add(sendOrder);
		
		return(pd);
	}	
	
	public Description getReceiveDecisionProtocol() {
		Description pd=new Description();
		
		// 0
		SendMessage recvOrder=new SendMessage();
		//recvOrder.setNodeType(Protocol.Node.NodeType.SendMessage);
		recvOrder.setNextIndex(1);

		MessageType mt1=new MessageType();
		mt1.setValue(ORDER_MESSAGE_TYPE);
		recvOrder.getMessageType().add(mt1);
		recvOrder.setOtherRole(OTHER_ROLE);
		pd.getNode().add(recvOrder);

		// 1
		Decision conditional=new Decision();
		//conditional.setNodeType(Protocol.Node.NodeType.ReceiveDecision);
		conditional.setNextIndex(4);
		conditional.setInnerIndex(2);
		//conditional.setOtherRole(OTHER_ROLE);
		pd.getNode().add(conditional);
		
		// 2
		ReceiveMessage sendCreditCheck=new ReceiveMessage();
		//sendCreditCheck.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		sendCreditCheck.setNextIndex(3);

		MessageType mt2=new MessageType();
		mt2.setValue(CREDIT_CHECK_MESSAGE_TYPE);
		sendCreditCheck.getMessageType().add(mt2);
		sendCreditCheck.setOtherRole(OTHER_ROLE);
		pd.getNode().add(sendCreditCheck);
		
		// 3
		SendMessage recvCreditOk=new SendMessage();
		//recvCreditOk.setNodeType(Protocol.Node.NodeType.SendMessage);
		recvCreditOk.setNextIndex(4);

		MessageType mt3=new MessageType();
		mt3.setValue(CREDIT_OK_MESSAGE_TYPE);
		recvCreditOk.getMessageType().add(mt3);
		recvCreditOk.setOtherRole(OTHER_ROLE);
		pd.getNode().add(recvCreditOk);
		
		// 4
		ReceiveMessage sendOrder=new ReceiveMessage();
		//sendOrder.setNodeType(Protocol.Node.NodeType.ReceiveMessage);

		MessageType mt4=new MessageType();
		mt4.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendOrder.getMessageType().add(mt4);
		sendOrder.setOtherRole(OTHER_ROLE);
		pd.getNode().add(sendOrder);
		
		return(pd);
	}	
	
	@Test
	public void testConditionalSendValidOrderCreditCheckOk() {
		Description pd=getSendDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalSendValidOrderConfirmation() {
		Description pd=getSendDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalSendValidOrderCreditCheckOkLookahead() {
		Description pd=getSendDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Receive Order failed");
		}
				
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalSendValidOrderConfirmationLookahead() {
		Description pd=getSendDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalSendInvalidOrderCreditCheckOkByDecision() {
		Description pd=getSendDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
	
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == true) {
			fail("Second order should have failed");
		}
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}

	@Test
	public void testConditionalSendInvalidOrderCreditCheckOkByParticipant() {
		Description pd=getSendDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, INVALID_ROLE, message).isValid() == true) {
			fail("Credit check should have failed");
		}
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}

	@Test
	public void testConditionalReceiveValidOrderCreditCheckOk() {
		Description pd=getReceiveDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalReceiveValidOrderConfirmation() {
		Description pd=getReceiveDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalReceiveValidOrderCreditCheckOkLookahead() {
		Description pd=getReceiveDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Order failed");
		}
				
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalReceiveValidOrderConfirmationLookahead() {
		Description pd=getReceiveDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Receive Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testConditionalReceiveInvalidOrderCreditCheckOkByDecision() {
		Description pd=getReceiveDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
	
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, OTHER_ROLE, message).isValid() == true) {
			fail("Second order should have failed");
		}
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}
	
	@Test
	public void testConditionalReceiveInvalidOrderCreditCheckOkByParticipant() {
		Description pd=getReceiveDecisionProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, OTHER_ROLE, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, INVALID_ROLE, message).isValid() == true) {
			fail("Credit check should have failed");
		}
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}
}

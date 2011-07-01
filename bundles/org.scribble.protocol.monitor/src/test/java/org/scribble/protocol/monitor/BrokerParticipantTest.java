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

public class BrokerParticipantTest {

	private static final String SUPPLIER = "Supplier";
	private static final String CREDIT_AGENCY = "CreditAgency";
	private static final String BUYER = "Buyer";
	private static final String OUT_OF_STOCK_MESSAGE_TYPE = "OutOfStock";
	private static final String INSUFFICIENT_CREDIT_MESSAGE_TYPE = "InsufficientCredit";
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
	
	public Description getBrokerProtocol() {
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
		SendMessage sendCreditCheck=new SendMessage();
		//sendCreditCheck.setNodeType(Protocol.Node.NodeType.SendMessage);
		sendCreditCheck.setNextIndex(2);

		MessageType mt2=new MessageType();
		mt2.setValue(CREDIT_CHECK_MESSAGE_TYPE);
		sendCreditCheck.getMessageType().add(mt2);
		pd.getNode().add(sendCreditCheck);
		
		// 2
		Choice choice1=new Choice();
		//choice1.setNodeType(Protocol.Node.NodeType.ReceiveChoice);

		Path c1=new Path();
		//c1.setId(PurchasingLabel._CreditOk.name());
		c1.setNextIndex(3);
		choice1.getPath().add(c1);
		
		Path c2=new Path();
		//c2.setId(PurchasingLabel._InsufficientCredit.name());
		c2.setNextIndex(10);
		choice1.getPath().add(c2);
						
		pd.getNode().add(choice1);
		
		// 3
		ReceiveMessage recvCreditOk=new ReceiveMessage();
		//recvCreditOk.setNodeType(Protocol.Node.NodeType.ReceiveMessage);

		MessageType mt3=new MessageType();
		mt3.setValue(CREDIT_OK_MESSAGE_TYPE);
		recvCreditOk.getMessageType().add(mt3);
		recvCreditOk.setNextIndex(4);
		pd.getNode().add(recvCreditOk);
		
		// 4
		SendMessage sendOrder=new SendMessage();
		//sendOrder.setNodeType(Protocol.Node.NodeType.SendMessage);
		sendOrder.setNextIndex(5);

		MessageType mt4=new MessageType();
		mt4.setValue(ORDER_MESSAGE_TYPE);
		sendOrder.getMessageType().add(mt4);
		pd.getNode().add(sendOrder);
		
		// 5
		Choice choice2=new Choice();
		//choice2.setNodeType(Protocol.Node.NodeType.ReceiveChoice);

		Path c3=new Path();
		//c3.setId(PurchasingLabel._Confirmation.name());
		c3.setNextIndex(6);
		choice2.getPath().add(c3);
		
		Path c4=new Path();
		//c4.setId(PurchasingLabel._OutOfStock.name());
		c4.setNextIndex(8);
		choice2.getPath().add(c4);
						
		pd.getNode().add(choice2);
		
		// 6
		ReceiveMessage recvConfirmation=new ReceiveMessage();
		//recvConfirmation.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt1a=new MessageType();
		mt1a.setValue(CONFIRMATION_MESSAGE_TYPE);
		recvConfirmation.getMessageType().add(mt1a);
		recvConfirmation.setNextIndex(7);
		pd.getNode().add(recvConfirmation);
		
		// 7
		SendMessage sendConformation=new SendMessage();
		//sendConformation.setNodeType(Protocol.Node.NodeType.SendMessage);
		
		MessageType mt2a=new MessageType();
		mt2a.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendConformation.getMessageType().add(mt2a);
		pd.getNode().add(sendConformation);

		// 8
		ReceiveMessage recvOutOfStock=new ReceiveMessage();
		//recvOutOfStock.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt3a=new MessageType();
		mt3a.setValue(OUT_OF_STOCK_MESSAGE_TYPE);
		recvOutOfStock.getMessageType().add(mt3a);
		recvOutOfStock.setNextIndex(9);
		pd.getNode().add(recvOutOfStock);

		// 9
		SendMessage sendOutOfStock=new SendMessage();
		//sendOutOfStock.setNodeType(Protocol.Node.NodeType.SendMessage);
		
		MessageType mt4a=new MessageType();
		mt4a.setValue(OUT_OF_STOCK_MESSAGE_TYPE);
		sendOutOfStock.getMessageType().add(mt4a);
		pd.getNode().add(sendOutOfStock);

		// 10
		ReceiveMessage recvInsufficientCredit=new ReceiveMessage();
		//recvInsufficientCredit.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt5=new MessageType();
		mt5.setValue(INSUFFICIENT_CREDIT_MESSAGE_TYPE);
		recvInsufficientCredit.getMessageType().add(mt5);
		recvInsufficientCredit.setNextIndex(11);
		pd.getNode().add(recvInsufficientCredit);
		
		// 11
		ReceiveMessage sendInsufficientCredit=new ReceiveMessage();
		//sendInsufficientCredit.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt6=new MessageType();
		mt6.setValue(INSUFFICIENT_CREDIT_MESSAGE_TYPE);
		sendInsufficientCredit.getMessageType().add(mt6);
		pd.getNode().add(sendInsufficientCredit);
		
		return(pd);
	}	
	
	public Description getBrokerWithParticipantProtocol() {
		Description pd=new Description();
		
		// 0
		ReceiveMessage recvOrder=new ReceiveMessage();
		//recvOrder.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		recvOrder.setNextIndex(1);
		
		MessageType mt1=new MessageType();
		mt1.setValue(ORDER_MESSAGE_TYPE);
		recvOrder.getMessageType().add(mt1);
		recvOrder.setOtherRole(BUYER);
		pd.getNode().add(recvOrder);

		// 1
		SendMessage sendCreditCheck=new SendMessage();
		//sendCreditCheck.setNodeType(Protocol.Node.NodeType.SendMessage);
		sendCreditCheck.setNextIndex(2);
		
		MessageType mt2=new MessageType();
		mt2.setValue(CREDIT_CHECK_MESSAGE_TYPE);
		sendCreditCheck.getMessageType().add(mt2);
		sendCreditCheck.setOtherRole(CREDIT_AGENCY);
		pd.getNode().add(sendCreditCheck);
		
		// 2
		Choice choice1=new Choice();
		//choice1.setNodeType(Protocol.Node.NodeType.ReceiveChoice);
		//choice1.setOtherRole(CREDIT_AGENCY);

		Path c1=new Path();
		//c1.setId(PurchasingLabel._CreditOk.name());
		c1.setNextIndex(3);
		choice1.getPath().add(c1);
		
		Path c2=new Path();
		//c2.setId(PurchasingLabel._InsufficientCredit.name());
		c2.setNextIndex(10);
		choice1.getPath().add(c2);
						
		pd.getNode().add(choice1);
		
		// 3
		ReceiveMessage recvCreditOk=new ReceiveMessage();
		//recvCreditOk.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt3=new MessageType();
		mt3.setValue(CREDIT_OK_MESSAGE_TYPE);
		recvCreditOk.getMessageType().add(mt3);
		recvCreditOk.setNextIndex(4);
		recvCreditOk.setOtherRole(CREDIT_AGENCY);
		pd.getNode().add(recvCreditOk);
		
		// 4
		SendMessage sendOrder=new SendMessage();
		//sendOrder.setNodeType(Protocol.Node.NodeType.SendMessage);
		sendOrder.setNextIndex(5);
		
		MessageType mt4=new MessageType();
		mt4.setValue(ORDER_MESSAGE_TYPE);
		sendOrder.getMessageType().add(mt4);
		sendOrder.setOtherRole(SUPPLIER);
		pd.getNode().add(sendOrder);
		
		// 5
		Choice choice2=new Choice();
		//choice2.setNodeType(Protocol.Node.NodeType.ReceiveChoice);
		//choice2.setOtherRole(SUPPLIER);

		Path c3=new Path();
		//c3.setId(PurchasingLabel._Confirmation.name());
		c3.setNextIndex(6);
		choice2.getPath().add(c3);
		
		Path c4=new Path();
		//c4.setId(PurchasingLabel._OutOfStock.name());
		c4.setNextIndex(8);
		choice2.getPath().add(c4);
						
		pd.getNode().add(choice2);
		
		// 6
		ReceiveMessage recvConfirmation=new ReceiveMessage();
		//recvConfirmation.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt5=new MessageType();
		mt5.setValue(CONFIRMATION_MESSAGE_TYPE);
		recvConfirmation.getMessageType().add(mt5);
		recvConfirmation.setNextIndex(7);
		recvConfirmation.setOtherRole(SUPPLIER);
		pd.getNode().add(recvConfirmation);
		
		// 7
		SendMessage sendConfirmation=new SendMessage();
		//sendConfirmation.setNodeType(Protocol.Node.NodeType.SendMessage);
		
		MessageType mt6=new MessageType();
		mt6.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendConfirmation.getMessageType().add(mt6);
		sendConfirmation.setOtherRole(BUYER);
		pd.getNode().add(sendConfirmation);

		// 8
		ReceiveMessage recvOutOfStock=new ReceiveMessage();
		//recvOutOfStock.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt7=new MessageType();
		mt7.setValue(OUT_OF_STOCK_MESSAGE_TYPE);
		recvOutOfStock.getMessageType().add(mt7);
		recvOutOfStock.setNextIndex(9);
		recvOutOfStock.setOtherRole(SUPPLIER);
		pd.getNode().add(recvOutOfStock);

		// 9
		SendMessage sendOutOfStock=new SendMessage();
		//sendOutOfStock.setNodeType(Protocol.Node.NodeType.SendMessage);
		
		MessageType mt8=new MessageType();
		mt8.setValue(OUT_OF_STOCK_MESSAGE_TYPE);
		sendOutOfStock.getMessageType().add(mt8);
		sendOutOfStock.setOtherRole(BUYER);
		pd.getNode().add(sendOutOfStock);

		// 10
		ReceiveMessage recvInsufficientCredit=new ReceiveMessage();
		//recvInsufficientCredit.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt9=new MessageType();
		mt9.setValue(INSUFFICIENT_CREDIT_MESSAGE_TYPE);
		recvInsufficientCredit.getMessageType().add(mt9);
		recvInsufficientCredit.setNextIndex(11);
		recvInsufficientCredit.setOtherRole(CREDIT_AGENCY);
		pd.getNode().add(recvInsufficientCredit);
		
		// 11
		ReceiveMessage sendInsufficientCredit=new ReceiveMessage();
		//sendInsufficientCredit.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
		
		MessageType mt10=new MessageType();
		mt10.setValue(INSUFFICIENT_CREDIT_MESSAGE_TYPE);
		sendInsufficientCredit.getMessageType().add(mt10);
		sendInsufficientCredit.setOtherRole(BUYER);
		pd.getNode().add(sendInsufficientCredit);
		
		return(pd);
	}	
	
	@Test
	public void testBrokerValidOrderCreditCheckOk() {
		Description pd=getBrokerProtocol();
		
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
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Receive Confirmation failed");
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
	public void testBrokerValidOrderCreditCheckOkLookahead() {
		Description pd=getBrokerProtocol();
		
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
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Receive Confirmation failed");
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
	public void testBrokerInvalidOrderCreditCheckOk() {
		Description pd=getBrokerProtocol();
		
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
		message.getTypes().add(ORDER_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == true) {
			fail("Second order should have failed");
		}		
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}	
	
	@Test
	public void testBrokerValidOrderCreditCheckOkWithParticipant() {
		Description pd=getBrokerWithParticipantProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, BUYER, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, CREDIT_AGENCY, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, CREDIT_AGENCY, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, SUPPLIER, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, SUPPLIER, message).isValid() == false) {
			fail("Receive Confirmation failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, BUYER, message).isValid() == false) {
			fail("Send Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testBrokerValidOrderCreditCheckOkLookaheadWithParticipant() {
		Description pd=getBrokerWithParticipantProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, BUYER, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, CREDIT_AGENCY, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, CREDIT_AGENCY, message).isValid() == false) {
			fail("Credit ok failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, SUPPLIER, message).isValid() == false) {
			fail("Send Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, SUPPLIER, message).isValid() == false) {
			fail("Receive Confirmation failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, BUYER, message).isValid() == false) {
			fail("Send Confirmation failed");
		}
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testBrokerInvalidOrderCreditCheckOkByParticipant() {
		Description pd=getBrokerWithParticipantProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, BUYER, message).isValid() == false) {
			fail("Receive Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
		
		if (monitor.messageSent(context, pd, conv, CREDIT_AGENCY, message).isValid() == false) {
			fail("Credit check failed");
		}
		
		if (conv.isFinished() == true) {
			fail("Conversation should NOT be finished");
		}
	}	
}

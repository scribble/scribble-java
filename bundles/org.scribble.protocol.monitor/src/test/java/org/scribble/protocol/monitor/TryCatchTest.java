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


public class TryCatchTest {

	private static final String CONFIRMATION_MESSAGE_TYPE = "Confirmation";
	private static final String ORDER_MESSAGE_TYPE = "Order";
	private static final String CANCEL_MESSAGE_TYPE = "Cancel";
	private static final String FINISH_MESSAGE_TYPE = "Finish";

	public enum PurchasingLabel {
		_Confirmation,
		_OutOfStock
	}
	
	public Description getSellerProtocol() {
		Description pd=new Description();
		
		// 0
		Try tn=new Try();
		pd.getNode().add(tn);
		tn.setNextIndex(4);
		tn.setInnerIndex(1);
		tn.getCatchIndex().add(3);
		
		// 1
		ReceiveMessage recvOrder=new ReceiveMessage();
		recvOrder.setNextIndex(2);

		MessageType mt1=new MessageType();
		mt1.setValue(ORDER_MESSAGE_TYPE);
		recvOrder.getMessageType().add(mt1);
		pd.getNode().add(recvOrder);
		
		// 2
		SendMessage sendConformation=new SendMessage();

		MessageType mt2=new MessageType();
		mt2.setValue(CONFIRMATION_MESSAGE_TYPE);
		sendConformation.getMessageType().add(mt2);
		pd.getNode().add(sendConformation);
		
		// 3
		ReceiveMessage recvCancel=new ReceiveMessage();

		MessageType mt3=new MessageType();
		mt3.setValue(CANCEL_MESSAGE_TYPE);
		recvCancel.getMessageType().add(mt3);
		pd.getNode().add(recvCancel);
		
		// 4
		ReceiveMessage recvFinish=new ReceiveMessage();

		MessageType mt4=new MessageType();
		mt4.setValue(FINISH_MESSAGE_TYPE);
		recvFinish.getMessageType().add(mt4);
		pd.getNode().add(recvFinish);
		
		return(pd);
	}	
	
	@Test
	public void testNoCancel() {
		Description pd=getSellerProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);		

		if (monitor.messageSent(context, pd, conv, null, message).isValid() == false) {
			fail("Confirmation failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(FINISH_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Finish message failed");
		}		
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
	
	@Test
	public void testCancel() {
		Description pd=getSellerProtocol();
		
		// Create Protocol Monitor
		ProtocolMonitor monitor=new DefaultProtocolMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		
		DefaultSession conv=new DefaultSession();
		monitor.initialize(context, pd, conv);
		
		DefaultMessage message=new DefaultMessage();
		message.getTypes().add(ORDER_MESSAGE_TYPE);
		
		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Order failed");
		}
		
		message=new DefaultMessage();
		message.getTypes().add(CANCEL_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Cancel failed");
		}		
		
		message=new DefaultMessage();
		message.getTypes().add(FINISH_MESSAGE_TYPE);		

		if (monitor.messageReceived(context, pd, conv, null, message).isValid() == false) {
			fail("Finish message failed");
		}		
		
		if (conv.isFinished() == false) {
			fail("Conversation should be finished");
		}
	}
}

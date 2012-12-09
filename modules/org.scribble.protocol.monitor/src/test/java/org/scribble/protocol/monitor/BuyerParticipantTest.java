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


public class BuyerParticipantTest {

    private static final String INSUFFICIENT_CREDIT_MESSAGE_TYPE = "InsufficientCredit";
    private static final String OUT_OF_STOCK_MESSAGE_TYPE = "OutOfStock";
    private static final String CONFIRMATION_MESSAGE_TYPE = "Confirmation";
    private static final String ORDER_MESSAGE_TYPE = "Order";

    public enum PurchasingLabel {
        _Confirmation,
        _OutOfStock,
        _InsufficientCredit
    }
    
    public Description getBuyerProtocol() {
        Description pd=new Description();
        
        SendMessage sendOrder=new SendMessage();
        //sendOrder.setNodeType(Protocol.Node.NodeType.SendMessage);
        sendOrder.setNextIndex(1);

        MessageType mt1=new MessageType();
        mt1.setValue(ORDER_MESSAGE_TYPE);
        sendOrder.getMessageType().add(mt1);
        pd.getNode().add(sendOrder);
        
        Choice choice=new Choice();
        //choice.setNodeType(Protocol.Node.NodeType.ReceiveChoice);

        Path c1=new Path();
        //c1.setId(PurchasingLabel._Confirmation.name());
        c1.setNextIndex(2);
        choice.getPath().add(c1);
        
        Path c2=new Path();
        //c2.setId(PurchasingLabel._OutOfStock.name());
        c2.setNextIndex(3);
        choice.getPath().add(c2);
        
        Path c3=new Path();
        //c3.setId(PurchasingLabel._InsufficientCredit.name());
        c3.setNextIndex(4);
        choice.getPath().add(c3);
                        
        pd.getNode().add(choice);
        
        ReceiveMessage recvConfirmation=new ReceiveMessage();
        //recvConfirmation.setNodeType(Protocol.Node.NodeType.ReceiveMessage);

        MessageType mt2=new MessageType();
        mt2.setValue(CONFIRMATION_MESSAGE_TYPE);
        recvConfirmation.getMessageType().add(mt2);
        pd.getNode().add(recvConfirmation);
        
        ReceiveMessage recvOutOfStock=new ReceiveMessage();
        //recvOutOfStock.setNodeType(Protocol.Node.NodeType.ReceiveMessage);

        MessageType mt3=new MessageType();
        mt3.setValue(OUT_OF_STOCK_MESSAGE_TYPE);
        recvOutOfStock.getMessageType().add(mt3);
        pd.getNode().add(recvOutOfStock);
        
        ReceiveMessage recvInsufficientCredit=new ReceiveMessage();
        //recvInsufficientCredit.setNodeType(Protocol.Node.NodeType.ReceiveMessage);

        MessageType mt4=new MessageType();
        mt4.setValue(INSUFFICIENT_CREDIT_MESSAGE_TYPE);
        recvInsufficientCredit.getMessageType().add(mt4);
        pd.getNode().add(recvInsufficientCredit);
        
        return (pd);
    }    
    
    @Test
    public void testBuyerValidOrderConfirmation() {
        Description pd=getBuyerProtocol();
        
        // Create Protocol Monitor
        ProtocolMonitor monitor=new DefaultProtocolMonitor();
        
        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, pd, DefaultSession.class);
        
        DefaultMessage message=new DefaultMessage();
        message.getTypes().add(ORDER_MESSAGE_TYPE);
        
        if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
            fail("Order failed");
        }
        
        message=new DefaultMessage();
        message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);        

        if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
            fail("Confirmation failed");
        }        
        
        if (conv.isFinished() == false) {
            fail("Conversation should be finished");
        }
    }
    
    @Test
    public void testBuyerValidOrderConfirmationLookahead() {
        Description pd=getBuyerProtocol();
        
        // Create Protocol Monitor
        ProtocolMonitor monitor=new DefaultProtocolMonitor();
        
        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, pd, DefaultSession.class);
        
        DefaultMessage message=new DefaultMessage();
        message.getTypes().add(ORDER_MESSAGE_TYPE);
                
        if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
            fail("Order failed");
        }
        
        message=new DefaultMessage();
        message.getTypes().add(CONFIRMATION_MESSAGE_TYPE);
                
        if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
            fail("Confirmation failed");
        }        
        
        if (conv.isFinished() == false) {
            fail("Conversation should be finished");
        }
    }
    
    @Test
    public void testBuyerInvalidOrderConfirmation() {
        Description pd=getBuyerProtocol();
        
        // Create Protocol Monitor
        ProtocolMonitor monitor=new DefaultProtocolMonitor();
        
        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, pd, DefaultSession.class);
        
        DefaultMessage message=new DefaultMessage();
        message.getTypes().add(ORDER_MESSAGE_TYPE);
        
        if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
            fail("Order failed");
        }
        
        message=new DefaultMessage();
        message.getTypes().add(ORDER_MESSAGE_TYPE);
        
        if (monitor.messageReceived(context, pd, conv, message).isValid() == true) {
            fail("Second order should have failed");
        }        
        
        if (conv.isFinished() == true) {
            fail("Conversation should NOT be finished");
        }
    }
}

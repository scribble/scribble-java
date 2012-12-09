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


public class CreditAgencyParticipantTest {

    private static final String INSUFFICIENT_CREDIT_MESSAGE_TYPE = "InsufficientCredit";
    private static final String CREDIT_OK_MESSAGE_TYPE = "CreditOk";
    private static final String CREDIT_CHECK_MESSAGE_TYPE = "CreditCheck";

    public enum PurchasingLabel {
        _CreditOk,
        _InsufficientCredit
    }
    
    public Description getCreditAgencyProtocol() {
        Description pd=new Description();
        
        ReceiveMessage recvCreditCheck=new ReceiveMessage();
        //recvCreditCheck.setNodeType(Protocol.Node.NodeType.ReceiveMessage);
        recvCreditCheck.setNextIndex(1);

        MessageType mt1=new MessageType();
        mt1.setValue(CREDIT_CHECK_MESSAGE_TYPE);
        recvCreditCheck.getMessageType().add(mt1);
        pd.getNode().add(recvCreditCheck);
        
        Choice choice=new Choice();
        //choice.setNodeType(Protocol.Node.NodeType.SendChoice);

        Path c1=new Path();
        //c1.setId(PurchasingLabel._CreditOk.name());
        c1.setNextIndex(2);
        choice.getPath().add(c1);
        
        Path c2=new Path();
        //c2.setId(PurchasingLabel._InsufficientCredit.name());
        c2.setNextIndex(3);
        choice.getPath().add(c2);
                        
        pd.getNode().add(choice);
        
        SendMessage sendCreditOk=new SendMessage();
        //sendCreditOk.setNodeType(Protocol.Node.NodeType.SendMessage);

        MessageType mt2=new MessageType();
        mt2.setValue(CREDIT_OK_MESSAGE_TYPE);
        sendCreditOk.getMessageType().add(mt2);
        pd.getNode().add(sendCreditOk);
        
        SendMessage sendInsufficientCredit=new SendMessage();
        //sendInsufficientCredit.setNodeType(Protocol.Node.NodeType.SendMessage);

        MessageType mt3=new MessageType();
        mt3.setValue(INSUFFICIENT_CREDIT_MESSAGE_TYPE);
        sendInsufficientCredit.getMessageType().add(mt3);
        pd.getNode().add(sendInsufficientCredit);
        
        return (pd);
    }    
    
    @Test
    public void testCreditAgencyValidCreditCheckOk() {
        Description pd=getCreditAgencyProtocol();
        
        // Create Protocol Monitor
        ProtocolMonitor monitor=new DefaultProtocolMonitor();
        
        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, pd, DefaultSession.class);
        
        DefaultMessage message=new DefaultMessage();
        message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
        
        if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
            fail("Credit check failed");
        }
        
        message=new DefaultMessage();
        message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);        

        if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
            fail("Credit ok failed");
        }        
        
        if (conv.isFinished() == false) {
            fail("Conversation should be finished");
        }
    }
    
    @Test
    public void testCreditAgencyValidCreditCheckOkLookahead() {
        Description pd=getCreditAgencyProtocol();
        
        // Create Protocol Monitor
        ProtocolMonitor monitor=new DefaultProtocolMonitor();
        
        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, pd, DefaultSession.class);
        
        DefaultMessage message=new DefaultMessage();
        message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
        
        if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
            fail("CreditCheck failed");
        }
        
        message=new DefaultMessage();
        message.getTypes().add(CREDIT_OK_MESSAGE_TYPE);
        
        if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
            fail("CreditOk failed");
        }        
        
        if (conv.isFinished() == false) {
            fail("Conversation should be finished");
        }
    }
    
    @Test
    public void testCreditAgencyInvalidOrderConfirmation() {
        Description pd=getCreditAgencyProtocol();
        
        // Create Protocol Monitor
        ProtocolMonitor monitor=new DefaultProtocolMonitor();
        
        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, pd, DefaultSession.class);
        
        DefaultMessage message=new DefaultMessage();
        message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
        
        if (monitor.messageReceived(context, pd, conv, message).isValid() == false) {
            fail("CreditCheck failed");
        }
        
        message=new DefaultMessage();
        message.getTypes().add(CREDIT_CHECK_MESSAGE_TYPE);
        
        if (monitor.messageSent(context, pd, conv, message).isValid() == true) {
            fail("Second Credit Check should have failed");
        }        
        
        if (conv.isFinished() == true) {
            fail("Conversation should NOT be finished");
        }
    }
}

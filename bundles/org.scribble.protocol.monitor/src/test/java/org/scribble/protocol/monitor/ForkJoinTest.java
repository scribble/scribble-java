/*
 * Copyright 2009-12 www.scribble.org
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

public class ForkJoinTest {

    private static final String OUT_OF_STOCK_MESSAGE_TYPE = "OutOfStock";
    private static final String CONFIRMATION_MESSAGE_TYPE = "Confirmation";
    private static final String ORDER_MESSAGE_TYPE = "Order";
    private static final String FINISH_MESSAGE_TYPE = "Finish";

    public enum PurchasingLabel {
        _Confirmation,
        _OutOfStock
    }
    
    public Description getProtocol() {
        Description pd=new Description();
        
        // 0
        ReceiveMessage recvOrder=new ReceiveMessage();
        recvOrder.setNextIndex(1);

        MessageType mt1=new MessageType();
        mt1.setValue(ORDER_MESSAGE_TYPE);
        recvOrder.getMessageType().add(mt1);
        pd.getNode().add(recvOrder);
        
        // 1
        LinkDeclaration linkdecl=new LinkDeclaration();
        linkdecl.setName("link1");
        linkdecl.setNextIndex(2);
        pd.getNode().add(linkdecl);
        
        // 2
        Parallel parallel=new Parallel();
        parallel.setNextIndex(7);

        Path c1=new Path();
        c1.setNextIndex(3);
        parallel.getPath().add(c1);
        
        Path c2=new Path();
        c2.setNextIndex(5);
        parallel.getPath().add(c2);
                        
        pd.getNode().add(parallel);
        
        // 3
        SendMessage sendConfirmation=new SendMessage();

        MessageType mt2=new MessageType();
        mt2.setValue(CONFIRMATION_MESSAGE_TYPE);
        sendConfirmation.getMessageType().add(mt2);
        sendConfirmation.setNextIndex(4);
        pd.getNode().add(sendConfirmation);
        
        // 4
        Fork fork=new Fork();
        fork.setLinkName("link1");
        pd.getNode().add(fork);
        
        // 5
        Join join=new Join();
        join.setExpression("link1");
        join.setNextIndex(6);
        pd.getNode().add(join);
        
        // 6
        SendMessage sendOutOfStock=new SendMessage();

        MessageType mt3=new MessageType();
        mt3.setValue(OUT_OF_STOCK_MESSAGE_TYPE);
        sendOutOfStock.getMessageType().add(mt3);
        pd.getNode().add(sendOutOfStock);
        
        // 7
        SendMessage sendFinish=new SendMessage();

        MessageType mt4=new MessageType();
        mt4.setValue(FINISH_MESSAGE_TYPE);
        sendFinish.getMessageType().add(mt4);
        pd.getNode().add(sendFinish);
        
        return (pd);
    }    

    @Test
    public void testValidCompletion() {
        Description pd=getProtocol();
        
        // Create Protocol Monitor
        ProtocolMonitor monitor=new DefaultProtocolMonitor();
        
        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, pd, DefaultSession.class);
        
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
        
        message=new DefaultMessage();
        message.getTypes().add(OUT_OF_STOCK_MESSAGE_TYPE);        

        if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
            fail("Out Of Stock failed");
        }        
        
        message=new DefaultMessage();
        message.getTypes().add(FINISH_MESSAGE_TYPE);        

        if (monitor.messageSent(context, pd, conv, message).isValid() == false) {
            fail("Finish message failed");
        }        
        
        if (conv.isFinished() == false) {
            fail("Conversation should be finished");
        }
    }
    
}
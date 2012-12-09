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

import org.scribble.protocol.monitor.DefaultMessage;
import org.scribble.protocol.monitor.Message;
import org.scribble.protocol.monitor.MonitorContext;
import org.scribble.protocol.monitor.ProtocolMonitor;
import org.scribble.protocol.monitor.Result;
import org.scribble.protocol.monitor.Session;
import org.scribble.protocol.monitor.model.Description;

/**
 * This class represents receiving a message.
 *
 */
public class ReceiveMessage extends Event {
    
    private DefaultMessage _message=new DefaultMessage();

    /**
     * Default constructor.
     */
    public ReceiveMessage() {
    }

    @Override
    public Result validate(ProtocolMonitor monitor, MonitorContext context,
                Description protocol, Session conv) {
        return (monitor.messageReceived(context, protocol, conv, _message));
    }

    @Override
    public void setColumn(int col, String val) {
        
        if (col == 1) {
            int pos=val.indexOf('(');
            
            if (pos == -1) {
                _message.getTypes().add(val);
            } else {
                String op=val.substring(0, pos);
                
                _message.setOperator(op);
                
                int end=val.indexOf(')', pos);
                
                String mesgType=val.substring(pos+1, end);
                
                _message.getTypes().add(mesgType);
            }
        }
    }
    
    /**
     * This method returns the message.
     * 
     * @return The message
     */
    public Message getMessage() {
        return (_message);
    }
    
    @Override
    public String toString() {
        return ("Receive "+_message);
    }
}

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

import org.scribble.protocol.monitor.*;
import org.scribble.protocol.monitor.model.Description;

public class SendMessage extends Event {

	public SendMessage() {
	}

	@Override
	public Result validate(ProtocolMonitor monitor, MonitorContext context, Description protocol,
						Session conv) {
		return(monitor.messageSent(context, protocol, conv, m_role, m_message));
	}

	@Override
	public void setColumn(int col, String val) {
		
		if (col == 1) {
			int pos=0;
			
			if ((pos=val.indexOf('(')) == -1) {
				m_message.getTypes().add(val);
			} else {
				String op=val.substring(0, pos);
				
				m_message.setOperator(op);
				
				int end=val.indexOf(')', pos);
				
				String mesgType=val.substring(pos+1, end);

				m_message.getTypes().add(mesgType);
			}
		} else if (col == 2) {
			m_role = val;
		}
	}
	
	public Message getMessage() {
		return(m_message);
	}
	
	public String getRole() {
		return(m_role);
	}
	
	public String toString() {
		return("SendMessage "+m_message+" to "+m_role);
	}
	
	private DefaultMessage m_message=new DefaultMessage();
	private String m_role=null;
}

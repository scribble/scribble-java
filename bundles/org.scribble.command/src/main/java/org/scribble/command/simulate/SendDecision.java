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

public class SendDecision extends Event {

	public SendDecision() {
	}

	@Override
	public Result validate(ProtocolMonitor monitor, MonitorContext context, Description protocol,
						Session conv) {
		return(monitor.sendDecision(context, protocol, conv, m_role, m_decision));
	}

	@Override
	public void setColumn(int col, String val) {
		
		if (col == 1) {
			m_decision = val.equalsIgnoreCase("true");
		} else if (col == 2) {
			m_role = val;
		}
	}
	
	public boolean getDecision() {
		return(m_decision);
	}
	
	public String getRole() {
		return(m_role);
	}
	
	public String toString() {
		return("SendDecision "+m_decision+" to "+m_role);
	}
	
	private boolean m_decision=false;
	private String m_role=null;
}

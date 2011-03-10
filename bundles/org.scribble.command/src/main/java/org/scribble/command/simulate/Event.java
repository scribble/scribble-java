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

import org.scribble.protocol.monitor.Session;
import org.scribble.protocol.monitor.MonitorContext;
import org.scribble.protocol.monitor.Result;
import org.scribble.protocol.monitor.model.Description;
import org.scribble.protocol.monitor.ProtocolMonitor;

public abstract class Event {

	public Event() {
	}
	
	public abstract void setColumn(int col, String val);
	
	public abstract Result validate(ProtocolMonitor monitor, MonitorContext context,
					Description protocol, Session conv);
}

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

/**
 * This is the abstract base class for all simulation events.
 *
 */
public abstract class Event {

    /**
     * Default constructor.
     */
    public Event() {
    }
    
    /**
     * This method sets the colum and value of the event record from the csv file.
     * 
     * @param col The column
     * @param val The value
     */
    public abstract void setColumn(int col, String val);
    
    /**
     * This method validates the event using the supplied protocol monitor,
     * monitor context, protocol and session details.
     * 
     * @param monitor The monitor
     * @param context The context
     * @param protocol The protocol
     * @param conv The conversation/session
     * @return The validation result
     */
    public abstract Result validate(ProtocolMonitor monitor, MonitorContext context,
                    Description protocol, Session conv);
}

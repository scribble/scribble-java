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

import org.scribble.protocol.monitor.model.Description;

/**
 * This is the interface for the Protocol Monitor.
 *
 */
public interface ProtocolMonitor {
    
    /**
     * This method creates a new session (conversation instance) and initializes
     * it based on the supplied description.
     * 
     * @param context The monitor context
     * @param protocol The protocol description
     * @param sessionClass The session implenentation class to instantiate
     * @return The created and initialized session
     */
    public Session createSession(MonitorContext context, Description protocol,
                            Class<? extends Session> sessionClass);
    
    /**
     * This method checks whether the conversation instance, managed by
     * the supplied context, can handle the supplied 'sent' message.
     * 
     * @param context The monitor context
     * @param protocol The protocol description
     * @param conv The conversation
     * @param mesg The 'sent' message
     * @return The result
     */
    public Result messageSent(MonitorContext context, Description protocol,
                    Session conv, Message mesg);
    
    /**
     * This method checks whether the conversation instance, managed by
     * the supplied context, can handle the supplied 'received' message.
     * 
     * @param context The monitor context
     * @param protocol The protocol description
     * @param conv The conversation
     * @param mesg The 'received' message
     * @return The result
     */
    public Result messageReceived(MonitorContext context, Description protocol,
                    Session conv, Message mesg);
    
}

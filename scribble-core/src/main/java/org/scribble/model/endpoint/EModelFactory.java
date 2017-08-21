/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.model.endpoint;

import java.util.Set;

import org.scribble.model.endpoint.actions.EAccept;
import org.scribble.model.endpoint.actions.ERequest;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.model.endpoint.actions.EWrapClient;
import org.scribble.model.endpoint.actions.EWrapServer;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public interface EModelFactory
{
	ESend newESend(Role peer, MessageId<?> mid, Payload payload);
	EReceive newEReceive(Role peer, MessageId<?> mid, Payload payload);
	ERequest newERequest(Role peer, MessageId<?> mid, Payload payload);
	EAccept newEAccept(Role peer, MessageId<?> mid, Payload payload);
	EDisconnect newEDisconnect(Role peer);
	EWrapClient newEWrapClient(Role peer);
	EWrapServer newEWrapServer(Role peer);

	EState newEState(Set<RecVar> labs);
}

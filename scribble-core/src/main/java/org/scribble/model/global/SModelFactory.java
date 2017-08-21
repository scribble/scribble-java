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
package org.scribble.model.global;

import java.util.Map;

import org.scribble.model.endpoint.EFSM;
import org.scribble.model.global.actions.SAccept;
import org.scribble.model.global.actions.SRequest;
import org.scribble.model.global.actions.SDisconnect;
import org.scribble.model.global.actions.SReceive;
import org.scribble.model.global.actions.SSend;
import org.scribble.model.global.actions.SWrapClient;
import org.scribble.model.global.actions.SWrapServer;
import org.scribble.type.Payload;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public interface SModelFactory
{
	SGraphBuilderUtil newSGraphBuilderUtil();  // Directly created and used by Job.buildSGraph -- cf. EGraphBuilderUtil, encapsulated by EGraphBuilder AST visitor
	
	SSend newSSend(Role subj, Role obj, MessageId<?> mid, Payload payload);
	SReceive newSReceive(Role subj, Role obj, MessageId<?> mid, Payload payload);
	SRequest newSConnect(Role subj, Role obj, MessageId<?> mid, Payload payload);
	SAccept newSAccept(Role subj, Role obj, MessageId<?> mid, Payload payload);
	SDisconnect newSDisconnect(Role subj, Role obj);
	SWrapClient newSWrapClient(Role subj, Role obj);
	SWrapServer newSWrapServer(Role subj, Role obj);

	SState newSState(SConfig config);
	SGraph newSGraph(GProtocolName proto, Map<Integer, SState> states, SState init);
	SConfig newSConfig(Map<Role, EFSM> state, SBuffers buffs);
	SModel newSModel(SGraph g);
}

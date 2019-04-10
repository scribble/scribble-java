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
package org.scribble.core.model;

import java.util.Map;
import java.util.Set;

import org.scribble.core.model.endpoint.EFSM;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAccept;
import org.scribble.core.model.endpoint.actions.EDisconnect;
import org.scribble.core.model.endpoint.actions.EReceive;
import org.scribble.core.model.endpoint.actions.ERequest;
import org.scribble.core.model.endpoint.actions.ESend;
import org.scribble.core.model.endpoint.actions.EWrapClient;
import org.scribble.core.model.endpoint.actions.EWrapServer;
import org.scribble.core.model.global.SBuffers;
import org.scribble.core.model.global.SConfig;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.model.global.SGraphBuilderUtil;
import org.scribble.core.model.global.SModel;
import org.scribble.core.model.global.SState;
import org.scribble.core.model.global.actions.SAccept;
import org.scribble.core.model.global.actions.SDisconnect;
import org.scribble.core.model.global.actions.SReceive;
import org.scribble.core.model.global.actions.SRequest;
import org.scribble.core.model.global.actions.SSend;
import org.scribble.core.model.global.actions.SWrapClient;
import org.scribble.core.model.global.actions.SWrapServer;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.MessageId;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

public interface ModelFactory
{
	ESend newESend(Role peer, MessageId<?> mid, Payload payload);
	EReceive newEReceive(Role peer, MessageId<?> mid, Payload payload);
	ERequest newERequest(Role peer, MessageId<?> mid, Payload payload);
	EAccept newEAccept(Role peer, MessageId<?> mid, Payload payload);
	EDisconnect newEDisconnect(Role peer);
	EWrapClient newEWrapClient(Role peer);
	EWrapServer newEWrapServer(Role peer);

	EState newEState(Set<RecVar> labs);

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

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
package org.scribble.core.model.global;

import java.util.Map;

import org.scribble.core.model.endpoint.EFsm;
import org.scribble.core.model.global.actions.SAcc;
import org.scribble.core.model.global.actions.SClientWrap;
import org.scribble.core.model.global.actions.SDisconnect;
import org.scribble.core.model.global.actions.SRecv;
import org.scribble.core.model.global.actions.SReq;
import org.scribble.core.model.global.actions.SSend;
import org.scribble.core.model.global.actions.SServerWrap;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.MsgId;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

public interface SModelFactory
{
	SGraphBuilderUtil newSGraphBuilderUtil();

	SState newSState(SConfig config);
	SConfig newSConfig(Map<Role, EFsm> state, SingleBuffers buffs);
	SGraph newSGraph(GProtoName proto, Map<Integer, SState> states, SState init);  // states: s.id -> s
	SModel newSModel(SGraph g);
	
	SSend newSSend(Role subj, Role obj, MsgId<?> mid, Payload pay);
	SRecv newSRecv(Role subj, Role obj, MsgId<?> mid, Payload pay);
	SReq newSReq(Role subj, Role obj, MsgId<?> mid, Payload pay);
	SAcc newSAcc(Role subj, Role obj, MsgId<?> mid, Payload pay);
	SDisconnect newSDisconnect(Role subj, Role obj);
	SClientWrap newSClientWrap(Role subj, Role obj);
	SServerWrap newSServerWrap(Role subj, Role obj);
}

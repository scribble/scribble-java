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
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.MsgId;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

// Separate E/SModelFactories fits protected E/SState constructor pattern
public class ModelFactoryImpl implements ModelFactory
{

	@Override
	public ESend newESend(Role peer, MsgId<?> mid, Payload payload)
	{
		return new ESend(this, peer, mid, payload);
	}

	@Override
	public EReceive newEReceive(Role peer, MsgId<?> mid, Payload payload)
	{
		return new EReceive(this, peer, mid, payload);
	}

	@Override
	public ERequest newERequest(Role peer, MsgId<?> mid, Payload payload)
	{
		return new ERequest(this, peer, mid, payload);
	}

	@Override
	public EAccept newEAccept(Role peer, MsgId<?> mid, Payload payload)
	{
		return new EAccept(this, peer, mid, payload);
	}

	@Override
	public EDisconnect newEDisconnect(Role peer)
	{
		return new EDisconnect(this, peer);
	}

	@Override
	public EWrapClient newEWrapClient(Role peer)
	{
		return new EWrapClient(this, peer);
	}

	@Override
	public EWrapServer newEWrapServer(Role peer)
	{
		return new EWrapServer(this, peer);
	}

	@Override
	public EState newEState(Set<RecVar> labs)
	{
		return new EState(labs);
	}

	@Override
	public SGraphBuilderUtil newSGraphBuilderUtil()
	{
		return new SGraphBuilderUtil(this);
	}

	@Override
	public SSend newSSend(Role subj, Role obj, MsgId<?> mid, Payload payload)
	{
		return new SSend(subj, obj, mid, payload);
	}

	@Override
	public SReceive newSReceive(Role subj, Role obj, MsgId<?> mid, Payload payload)
	{
		return new SReceive(subj, obj, mid, payload);
	}

	@Override
	public SRequest newSConnect(Role subj, Role obj, MsgId<?> mid, Payload payload)
	{
		return new SRequest(subj, obj, mid, payload);
	}
	
	@Override
	public SAccept newSAccept(Role subj, Role obj, MsgId<?> mid, Payload payload)
	{
		return new SAccept(subj, obj, mid, payload);
	}

	@Override
	public SDisconnect newSDisconnect(Role subj, Role obj)
	{
		return new SDisconnect(subj, obj);
	}

	@Override
	public SWrapClient newSWrapClient(Role subj, Role obj)
	{
		return new SWrapClient(subj, obj);
	}

	@Override
	public SWrapServer newSWrapServer(Role subj, Role obj)
	{
		return new SWrapServer(subj, obj);
	}

	@Override
	public SState newSState(SConfig config)
	{
		return new SState(config);
	}

	@Override
	public SGraph newSGraph(GProtoName proto, Map<Integer, SState> states, SState init)
	{
		return new SGraph(proto, states, init);
	}

	@Override
	public SConfig newSConfig(Map<Role, EFSM> state, SBuffers buffs)
	{
		return new SConfig(this, state, buffs);
	}
	
	@Override
	public SModel newSModel(SGraph g)
	{
		return new SModel(g);
	}
}

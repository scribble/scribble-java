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

import org.scribble.core.model.endpoint.EFsm;
import org.scribble.core.model.endpoint.EGraphBuilderUtil;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAcc;
import org.scribble.core.model.endpoint.actions.EClientWrap;
import org.scribble.core.model.endpoint.actions.EDisconnect;
import org.scribble.core.model.endpoint.actions.ERecv;
import org.scribble.core.model.endpoint.actions.EReq;
import org.scribble.core.model.endpoint.actions.ESend;
import org.scribble.core.model.endpoint.actions.EServerWrap;
import org.scribble.core.model.global.SConfig;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.model.global.SGraphBuilderUtil;
import org.scribble.core.model.global.SModel;
import org.scribble.core.model.global.SingleBuffers;
import org.scribble.core.model.global.SState;
import org.scribble.core.model.global.actions.SAcc;
import org.scribble.core.model.global.actions.SClientWrap;
import org.scribble.core.model.global.actions.SDisconnect;
import org.scribble.core.model.global.actions.SRecv;
import org.scribble.core.model.global.actions.SReq;
import org.scribble.core.model.global.actions.SSend;
import org.scribble.core.model.global.actions.SServerWrap;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.MsgId;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;

// Separate E/SModelFactories fits protected E/SState constructor pattern
public class ModelFactoryImpl implements ModelFactory
{

	@Override
	public EGraphBuilderUtil newEGraphBuilderUtil()
	{
		return new EGraphBuilderUtil(this);
	}

	@Override
	public ESend newESend(Role peer, MsgId<?> mid, Payload pay)
	{
		return new ESend(this, peer, mid, pay);
	}

	@Override
	public ERecv newERecv(Role peer, MsgId<?> mid, Payload pay)
	{
		return new ERecv(this, peer, mid, pay);
	}

	@Override
	public EReq newEReq(Role peer, MsgId<?> mid, Payload pay)
	{
		return new EReq(this, peer, mid, pay);
	}

	@Override
	public EAcc newEAcc(Role peer, MsgId<?> mid, Payload pay)
	{
		return new EAcc(this, peer, mid, pay);
	}

	@Override
	public EDisconnect newEDisconnect(Role peer)
	{
		return new EDisconnect(this, peer);
	}

	@Override
	public EClientWrap newEClientWrap(Role peer)
	{
		return new EClientWrap(this, peer);
	}

	@Override
	public EServerWrap newEServerWrap(Role peer)
	{
		return new EServerWrap(this, peer);
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
	public SSend newSSend(Role subj, Role obj, MsgId<?> mid, Payload pay)
	{
		return new SSend(subj, obj, mid, pay);
	}

	@Override
	public SRecv newSRecv(Role subj, Role obj, MsgId<?> mid, Payload pay)
	{
		return new SRecv(subj, obj, mid, pay);
	}

	@Override
	public SReq newSReq(Role subj, Role obj, MsgId<?> mid, Payload pay)
	{
		return new SReq(subj, obj, mid, pay);
	}
	
	@Override
	public SAcc newSAcc(Role subj, Role obj, MsgId<?> mid, Payload pay)
	{
		return new SAcc(subj, obj, mid, pay);
	}

	@Override
	public SDisconnect newSDisconnect(Role subj, Role obj)
	{
		return new SDisconnect(subj, obj);
	}

	@Override
	public SClientWrap newSClientWrap(Role subj, Role obj)
	{
		return new SClientWrap(subj, obj);
	}

	@Override
	public SServerWrap newSServerWrap(Role subj, Role obj)
	{
		return new SServerWrap(subj, obj);
	}

	@Override
	public SState newSState(SConfig config)
	{
		return new SState(config);
	}

	@Override
	public SGraph newSGraph(GProtoName proto, Map<Integer, SState> states,
			SState init)
	{
		return new SGraph(proto, states, init);
	}

	@Override
	public SConfig newSConfig(Map<Role, EFsm> state, SingleBuffers buffs)
	{
		return new SConfig(this, state, buffs);
	}
	
	@Override
	public SModel newSModel(SGraph g)
	{
		return new SModel(g);
	}
}

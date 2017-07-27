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
import org.scribble.model.global.actions.SConnect;
import org.scribble.model.global.actions.SDisconnect;
import org.scribble.model.global.actions.SReceive;
import org.scribble.model.global.actions.SSend;
import org.scribble.model.global.actions.SWrapClient;
import org.scribble.model.global.actions.SWrapServer;
import org.scribble.type.Payload;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

// Separate E/SModelFactories fits protected E/SState constructor pattern
public class SModelFactoryImpl implements SModelFactory
{
	@Override
	public SGraphBuilderUtil newSGraphBuilderUtil()
	{
		return new SGraphBuilderUtil(this);
	}

	@Override
	public SSend newSSend(Role subj, Role obj, MessageId<?> mid, Payload payload)
	{
		return new SSend(subj, obj, mid, payload);
	}

	@Override
	public SReceive newSReceive(Role subj, Role obj, MessageId<?> mid, Payload payload)
	{
		return new SReceive(subj, obj, mid, payload);
	}

	@Override
	public SConnect newSConnect(Role subj, Role obj, MessageId<?> mid, Payload payload)
	{
		return new SConnect(subj, obj, mid, payload);
	}
	
	@Override
	public SAccept newSAccept(Role subj, Role obj, MessageId<?> mid, Payload payload)
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
	public SGraph newSGraph(GProtocolName proto, Map<Integer, SState> states, SState init)
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

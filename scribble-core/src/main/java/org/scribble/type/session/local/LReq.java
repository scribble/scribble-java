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
package org.scribble.type.session.local;

import java.util.Set;

import org.scribble.job.ScribbleException;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.ReachabilityEnv;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;
import org.scribble.type.session.ConnectAction;
import org.scribble.type.session.Message;
import org.scribble.type.session.MessageSig;
import org.scribble.type.session.Payload;

public class LReq extends ConnectAction<Local>
		implements LType
{

	// this.src == Role.SELF
	public LReq(org.scribble.ast.DirectedInteraction<Local> source,
			Message msg, Role dst)
	{
		super(source, Role.SELF, msg, dst);
	}

	// CHECKME: remove unnecessary src ?
	@Override
	public LReq reconstruct(
			org.scribble.ast.DirectedInteraction<Local> source, Role src, Message msg,
			Role dst)
	{
		return new LReq(source, msg, dst);
	}

	@Override
	public RecVar isSingleCont()
	{
		return null;
	}

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		return false;
	}

	@Override
	public LReq substitute(Substitutions subs)
	{
		return (LReq) super.substitute(subs);
	}

	@Override
	public LReq getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (LReq) super.getInlined(i);
	}

	@Override
	public LReq unfoldAllOnce(STypeUnfolder<Local> u)
	{
		return this;
	}

	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		Role peer = this.dst;
		MessageId<?> mid = this.msg.getId();
		Payload payload = this.msg.isMessageSig()  // CHECKME: generalise? (e.g., hasPayload)
				? ((MessageSig) msg).payload
				: Payload.EMPTY_PAYLOAD;
		// TODO: add toAction method to base Interaction
		b.addEdge(b.getEntry(), b.ef.newERequest(peer, mid, payload), b.getExit());
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		return env;
	}
	
	@Override
	public String toString()
	{
		return this.msg + " request " + this.dst + ";";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 10597;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LReq))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LReq;
	}

}

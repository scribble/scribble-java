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
package org.scribble.core.type.session.local;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Msg;
import org.scribble.core.type.session.MsgTransfer;

public class LSend extends MsgTransfer<Local, LSeq>
		implements LType
{

	// this.src == Role.SELF
	protected LSend(CommonTree source,
			Msg msg, Role dst)
	{
		super(source, msg, Role.SELF, dst);
	}

	// CHECKME: remove unnecessary src ?
	@Override
	public LSend reconstruct(
			CommonTree source, Msg msg, Role src,
			Role dst)
	{
		return new LSend(source, msg, dst);
	}
	
	@Override
	public String toString()
	{
		return this.msg + " to " + this.dst + ";";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1481;
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
		if (!(o instanceof LSend))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LSend;
	}

}




















/*
	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		return false;
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribException
	{
		return env;
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
		b.addEdge(b.getEntry(), b.ef.newESend(peer, mid, payload), b.getExit());
	}
*/
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

public class LRecv extends MsgTransfer<Local, LSeq>
		implements LType
{

	// this.dst == Role.SELF
	protected LRecv(CommonTree source,
			Role src, Msg msg)
	{
		super(source, msg, src, Role.SELF);
	}

	// FIXME: unnecessary dst 
	@Override
	public LRecv reconstruct(
			CommonTree source, Msg msg, Role src,
			Role dst)
	{
		return new LRecv(source, src, msg);
	}

	@Override
	public String toString()
	{
		return this.msg + " from " + this.src + ";";
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
		if (!(o instanceof LRecv))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LRecv;
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
		Role peer = this.src;
		MessageId<?> mid = this.msg.getId();
		Payload payload = this.msg.isMessageSig()  // CHECKME: generalise? (e.g., hasPayload)
				? ((MessageSig) msg).payload
				: Payload.EMPTY_PAYLOAD;
		b.addEdge(b.getEntry(), b.ef.newEReceive(peer, mid, payload), b.getExit());
	}
	*/

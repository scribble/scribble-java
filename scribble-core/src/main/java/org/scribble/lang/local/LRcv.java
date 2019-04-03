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
package org.scribble.lang.local;

import java.util.Set;

import org.scribble.job.ScribbleException;
import org.scribble.lang.MessageTransfer;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.Message;
import org.scribble.type.MessageSig;
import org.scribble.type.Payload;
import org.scribble.type.kind.Local;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class LRcv extends MessageTransfer<Local>
		implements LType
{

	// this.dst == Role.SELF
	public LRcv(org.scribble.ast.DirectedInteraction<Local> source,
			Role src, Message msg)
	{
		super(source, src, msg, Role.SELF);
	}

	// FIXME: unnecessary dst 
	@Override
	public LRcv reconstruct(
			org.scribble.ast.DirectedInteraction<Local> source, Role src, Message msg,
			Role dst)
	{
		return new LRcv(source, src, msg);
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
	public LRcv substitute(Substitutions subs)
	{
		return (LRcv) super.substitute(subs);
	}

	@Override
	public LRcv getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (LRcv) super.getInlined(i);
	}

	@Override
	public LRcv unfoldAllOnce(STypeUnfolder<Local> u)
	{
		return this;
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

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		return env;
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
		if (!(o instanceof LRcv))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LRcv;
	}

}

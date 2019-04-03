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
import org.scribble.lang.DisconnectAction;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class LDisconnect extends DisconnectAction<Local>
		implements LType
{

	// this.src == Role.SELF
	public LDisconnect(org.scribble.ast.DisconnectAction<Local> source,
			Role peer)
	{
		super(source, Role.SELF, peer);
	}
	
	public Role getPeer()
	{
		return this.right;
	}

	// CHECKME: remove unnecessary self ?
	@Override
	public LDisconnect reconstruct(
			org.scribble.ast.DisconnectAction<Local> source, Role self, Role peer)
	{
		return new LDisconnect(source, peer);
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
	public LDisconnect substitute(Substitutions subs)
	{
		return (LDisconnect) super.substitute(subs);
	}

	@Override
	public LDisconnect getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (LDisconnect) super.getInlined(i);
	}

	@Override
	public LDisconnect unfoldAllOnce(STypeUnfolder<Local> u)
	{
		return this;
	}

	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		Role peer = getPeer();
		// TODO: add toAction method to base Interaction
		b.addEdge(b.getEntry(), b.ef.newEDisconnect(peer), b.getExit());
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
		return "disconnect " + getPeer() + ";";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 8753;
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
		if (!(o instanceof LDisconnect))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LDisconnect;
	}
}

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
import org.scribble.core.type.session.DisconnectAction;

public class LDisconnect extends DisconnectAction<Local, LSeq>
		implements LType
{

	// this.src == Role.SELF
	protected LDisconnect(CommonTree source,
			Role peer)
	{
		super(source, Role.SELF, peer);
	}
	
	public Role getSelf()
	{
		return this.left;
	}
	
	public Role getPeer()
	{
		return this.right;
	}

	// CHECKME: remove unnecessary self ?
	@Override
	public LDisconnect reconstruct(
			CommonTree source, Role self, Role peer)
	{
		return new LDisconnect(source, peer);
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
		Role peer = getPeer();
		// TODO: add toAction method to base Interaction
		b.addEdge(b.getEntry(), b.ef.newEDisconnect(peer), b.getExit());
	}
	*/

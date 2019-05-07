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
package org.scribble.core.type.session.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Msg;
import org.scribble.core.type.session.MsgTransfer;

public class GMessageTransfer extends MsgTransfer<Global, GSeq>
		implements GType
{

	protected GMessageTransfer(CommonTree source, Role src, Msg msg, Role dst)
	{
		super(source, msg, src, dst);
	}

	@Override
	public GMessageTransfer reconstruct(CommonTree source, Msg msg, Role src,
			Role dst)
	{
		return new GMessageTransfer(source, src, msg, dst);
	}

	@Override
	public String toString()
	{
		return this.msg + " from " + this.src + " to " + this.dst + ";";
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
		if (!(o instanceof GMessageTransfer))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GMessageTransfer;
	}
}















/*
	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribException
	{
		if (!enabled.contains(this.src))
		{
			throw new ScribException("Source role not enabled: " + this.src);
		}
		if (enabled.contains(this.dst))
		{
			return enabled;
		}
		Set<Role> tmp = new HashSet<>(enabled); 
		tmp.add(this.dst);
		return Collections.unmodifiableSet(tmp);
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribException
	{
		if (enablers.containsKey(this.dst))
		{
			return enablers;
		}
		Map<Role, Role> tmp = new HashMap<>(enablers);
		tmp.put(this.dst, this.src);
		return Collections.unmodifiableMap(tmp);
	}
	
	@Override
	public LType projectInlined(Role self)
	{
		if (this.src.equals(self))
		{
			/*if (this.dst.equals(self))
			{
				// CHECKME: already checked?
			}* /
			return new LSend(null, this.msg, this.dst);
		}
		else if (this.dst.equals(self))
		{
			return new LRcv(null, this.src, this.msg);
		}
		else
		{
			return LSkip.SKIP;
		}
	}

	@Override
	public LType project(ProjEnv v)
	{
		return projectInlined(v.self);  // No need for "aux", no recursive call
	}
*/

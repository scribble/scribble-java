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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.job.ScribbleException;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.ConnectAction;
import org.scribble.core.type.session.Message;
import org.scribble.core.type.session.local.LAcc;
import org.scribble.core.type.session.local.LReq;
import org.scribble.core.type.session.local.LSkip;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.global.Projector2;

public class GConnect extends ConnectAction<Global, GSeq>
		implements GType
{

	public GConnect(CommonTree source,  // DirectedInteraction not ideal (imprecise)
			Role src, Message msg, Role dst)
	{
		super(source, src, msg, dst);
	}

	@Override
	public GConnect reconstruct(
			CommonTree source, Role src, Message msg,
			Role dst)
	{
		return new GConnect(source, src, msg, dst);
	}
	
	@Override
	public LType projectInlined(Role self)
	{
		if (this.src.equals(self))
		{
			/*if (this.dst.equals(self))
			{
				// CHECKME: already checked?
			}*/
			return new LReq(null, this.msg, this.dst);
		}
		else if (this.dst.equals(self))
		{
			return new LAcc(null, this.src, this.msg);
		}
		else
		{
			return LSkip.SKIP;
		}
	}

	@Override
	public LType project(Projector2 v)
	{
		return projectInlined(v.self);  // No need for "aux", no recursive call
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		if (!enabled.contains(this.src))
		{
			throw new ScribbleException("Source role not enabled: " + this.src);
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
			throws ScribbleException
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
	public String toString()
	{
		return this.msg + " connect " + this.src + " to " + this.dst + ";";
	}

	@Override
	public int hashCode()
	{
		int hash = 10639;
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
		if (!(o instanceof GConnect))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GConnect;
	}
}

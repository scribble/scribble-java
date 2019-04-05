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
package org.scribble.type.session.global;

import java.util.Map;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.type.session.DisconnectAction;
import org.scribble.type.session.local.LDisconnect;
import org.scribble.type.session.local.LSkip;
import org.scribble.type.session.local.LType;
import org.scribble.visit.global.Projector2;

public class GDisconnect extends DisconnectAction<Global, GSeq>
		implements GType
{

	public GDisconnect(CommonTree source,
			Role left, Role right)
	{
		super(source, left, right);
	}

	@Override
	public GDisconnect reconstruct(
			CommonTree source, Role left, Role right)
	{
		return new GDisconnect(source, left, right);
	}
	
	@Override
	public LType projectInlined(Role self)
	{
		if (this.left.equals(self))
		{
			/*if (this.dst.equals(self))
			{
				// CHECKME: already checked?
			}*/
			return new LDisconnect(null, this.right);
		}
		else if (this.right.equals(self))
		{
			return new LDisconnect(null, this.left);
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
		if (!enabled.contains(this.left))
		{
			throw new ScribbleException("Role not enabled: " + this.left);
		}
		if (!enabled.contains(this.right))
		{
			throw new ScribbleException("Role not enabled: " + this.right);
		}
		return enabled;
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		return enablers;
	}

	@Override
	public String toString()
	{
		return "disconnect " + this.left + " and " + this.right + ";";
	}

	@Override
	public int hashCode()
	{
		int hash = 8747;
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
		if (!(o instanceof GDisconnect))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GDisconnect;
	}
}

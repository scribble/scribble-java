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
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;
import org.scribble.type.name.Substitutions;
import org.scribble.type.session.Continue;
import org.scribble.type.session.local.LContinue;
import org.scribble.visit.Projector2;
import org.scribble.visit.STypeInliner;
import org.scribble.visit.STypeUnfolder;

public class GContinue extends Continue<Global, GSeq> implements GType
{
	public GContinue(//org.scribble.ast.Continue<Global> source, 
			CommonTree source,  // Due to inlining, do -> continue
			RecVar recvar)
	{
		super(source, recvar);
	}
	
	@Override
	public GContinue reconstruct(CommonTree source,
			RecVar recvar)
	{
		return new GContinue(source, recvar);
	}

	@Override
	public GContinue substitute(Substitutions subs)
	{
		return (GContinue) super.substitute(subs);
	}

	@Override
	public GContinue pruneRecs()
	{
		return (GContinue) super.pruneRecs();
	}

	@Override
	public GContinue getInlined(STypeInliner v)
	{
		return (GContinue) super.getInlined(v);
	} 

	@Override
	public GRecursion unfoldAllOnce(STypeUnfolder<Global> u)
	{
		return new GRecursion(getSource(), this.recvar,
				(GSeq) u.getRec(this.recvar));
				// CHECKME: Continue (not Recursion) as the source of the unfolding ?
	}

	@Override
	public LContinue projectInlined(Role self)
	{
		return new LContinue(null, this.recvar);
	}
	
	@Override
	public LContinue project(Projector2 v)
	{
		return projectInlined(v.self);  // No need for "aux", no recursive call
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		return enabled;
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		return enablers;
	}
 
	@Override
	public int hashCode()
	{
		int hash = 3457;
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
		if (!(o instanceof GContinue))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GContinue;
	}
}

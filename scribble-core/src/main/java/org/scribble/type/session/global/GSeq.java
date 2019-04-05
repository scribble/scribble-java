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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.type.name.Substitutions;
import org.scribble.type.session.SType;
import org.scribble.type.session.Seq;
import org.scribble.type.session.local.LSeq;
import org.scribble.type.session.local.LSkip;
import org.scribble.type.session.local.LType;
import org.scribble.visit.Projector2;
import org.scribble.visit.STypeInliner;
import org.scribble.visit.STypeUnfolder;

public class GSeq extends Seq<Global> implements GType
{
	// GInteractionSeq or GBlock better as source?
	public GSeq(CommonTree source,
			List<? extends SType<Global>> elems)
	{
		super(source, elems);
	}

	@Override
	public GSeq reconstruct(CommonTree source,
			List<? extends SType<Global>> elems)
	{
		return new GSeq(source, elems);
	}

	@Override
	public GSeq substitute(Substitutions subs)
	{
		return (GSeq) super.substitute(subs);
	}

	@Override
	public GSeq pruneRecs()
	{
		List<GType> elems = new LinkedList<>();
		for (SType<Global> e : this.elems)
		{
			GType e1 = (GType) e.pruneRecs();
			if (e1 instanceof GSeq)  // cf. Recursion::pruneRecs
			{
				elems.addAll(((GSeq) e1).getElements());  // Handles empty Seq case
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(getSource(), elems);
	}

	@Override
	public GSeq getInlined(STypeInliner v)
	{
		return (GSeq) super.getInlined(v);
	}
	/*CommonTree source = getSource(); // CHECKME: or empty source?
		List<SType<Global>> elems = new LinkedList<>();
		for (SType<Global> e : this.elems)
		{
			SType<Global> e1 = e.getInlined(i);// , stack);
			if (e1 instanceof GSeq)
			{
				elems.addAll(((GSeq) e1).elems); // Inline GSeq's returned by GDo::getInlined
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}*/

	@Override
	public GSeq unfoldAllOnce(STypeUnfolder<Global> u)
	{
		CommonTree source = getSource();
		List<SType<Global>> elems = new LinkedList<>();
		for (SType<Global> e : this.elems)
		{
			SType<Global> e1 = e.unfoldAllOnce(u);
			if (e1 instanceof Seq<?>)
			{
				elems.addAll(((Seq<Global>) e1).elems);
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public LSeq projectInlined(Role self)
	{
		return projectAux(this.elems.stream()
				.map(x -> ((GType) x).projectInlined(self)));  
	}
	
	private LSeq projectAux(Stream<LType> elems)
	{
		List<LType> tmp = elems.filter(x -> !x.equals(LSkip.SKIP))
				.collect(Collectors.toList());
		return new LSeq(null, tmp);  
				// Empty seqs converted to LSkip by GChoice/Recursion projection
				// And a WF top-level protocol cannot produce empty LSeq
				// So a projection never contains an empty LSeq -- i.e., "empty choice/rec" pruning unnecessary
	}

	@Override
	public LSeq project(Projector2 v)
	{
		return projectAux(this.elems.stream()
				.map(x -> ((GType) x).project(v)));  
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		for (GType elem : getElements())
		{
			enabled = elem.checkRoleEnabling(enabled);
		}
		return enabled;
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		for (GType elem : getElements())
		{
			enablers = elem.checkExtChoiceConsistency(enablers);
		}
		return enablers;
	}

	/*@Override
	public Map<Role, Role> checkConnections(Map<Role, Role> conns)
			throws ScribbleException
	{
		for (GType elem : getElements())
		{
			conns = elem.checkConnections(conns);
		}
		return conns;
	}*/

	@Override
	public List<GType> getElements()
	{
		return this.elems.stream().map(x -> (GType) x).collect(Collectors.toList());
	}

	@Override
	public int hashCode()
	{
		int hash = 29;
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
		if (!(o instanceof GSeq))
		{
			return false;
		}
		return super.equals(o); // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GSeq;
	}
}

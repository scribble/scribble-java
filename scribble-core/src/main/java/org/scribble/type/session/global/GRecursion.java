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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.job.ScribbleException;
import org.scribble.lang.Projector;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;
import org.scribble.type.session.Recursion;
import org.scribble.type.session.local.LRecursion;
import org.scribble.type.session.local.LSeq;
import org.scribble.type.session.local.LSkip;
import org.scribble.type.session.local.LType;

public class GRecursion extends Recursion<Global, GSeq> implements GType
{
	public GRecursion(//org.scribble.ast.Recursion<Global> source, 
			ProtocolKindNode<Global> source,  // Due to inlining, protocoldecl -> rec
			RecVar recvar, GSeq body)
	{
		super(source, recvar, body);
	}
	
	@Override
	public GRecursion reconstruct(org.scribble.ast.ProtocolKindNode<Global> source,
			RecVar recvar, GSeq block)
	{
		return new GRecursion(source, recvar, block);
	}
	
	@Override
	public GRecursion substitute(Substitutions subs)
	{
		return reconstruct(getSource(), this.recvar, this.body.substitute(subs));
	}

	@Override
	public GRecursion getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.ProtocolKindNode<Global> source = getSource();  // CHECKME: or empty source?
		RecVar rv = i.enterRec(//stack.peek(), 
				this.recvar);  // FIXME: make GTypeInliner, and record recvars to check freshness (e.g., rec X in two choice cases)
		GSeq body = this.body.getInlined(i);//, stack);
		GRecursion res = reconstruct(source, rv, body);
		i.exitRec(this.recvar);
		return res;
	}

	@Override
	public GType unfoldAllOnce(STypeUnfolder<Global> u)
	{
		if (!u.hasRec(this.recvar))  // N.B. doesn't work if recvars shadowed
		{
			u.pushRec(this.recvar, this.body);
			GType unf = (GType) this.body.unfoldAllOnce(u);
			u.popRec(this.recvar);  
					// Needed for, e.g., repeat do's in separate choice cases -- cf. stack.pop in GDo::getInlined, must pop sig there for Seqs
			return unf;
		}
		return this;
	}

	@Override
	public LType projectInlined(Role self)
	{
		LSeq body = this.body.projectInlined(self);
		return projectAux(body);
	}

	private LType projectAux(LSeq body)
	{
		if (body.isEmpty())  // N.B. projection is doing empty-rec pruning
		{
			return LSkip.SKIP;
		}
		/*RecVar rv = body.isSingleCont();  // CHECKME: should do more "compressing" of "single continue" (subset) choice cases?   or should not do "single continue" checking for choice at all?
		if (rv != null)
		{
			return this.recvar.equals(rv) 
					? LSkip.SKIP 
					: new LContinue(null, rv);  // CHECKME: source
		}*/
		Set<RecVar> rvs = new HashSet<>();
		rvs.add(this.recvar);
		if (body.isSingleConts(rvs))
		{
			return LSkip.SKIP;
		}
		return new LRecursion(null, this.recvar, body);
	}

	@Override
	public LType project(Projector v)
	{
		LSeq body = this.body.project(v);
		return projectAux(body);
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		return this.body.checkRoleEnabling(enabled);
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		return this.body.checkExtChoiceConsistency(enablers);
	}

	@Override
	public int hashCode()
	{
		int hash = 2309;
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
		if (!(o instanceof GRecursion))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GRecursion;
	}
}

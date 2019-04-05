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
package org.scribble.type.session.local;

import java.util.List;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.job.ScribbleException;
import org.scribble.lang.SubprotoSig;
import org.scribble.lang.local.LProtocol;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;
import org.scribble.type.name.Substitutions;
import org.scribble.type.session.Arg;
import org.scribble.type.session.Do;
import org.scribble.visit.STypeInliner;
import org.scribble.visit.local.ReachabilityEnv;

public class LDo extends Do<Local, LSeq, LProtocolName> implements LType
{
	public LDo(CommonTree source, LProtocolName proto,
			List<Role> roles, List<Arg<? extends NonRoleParamKind>> args)
	{
		super(source, proto, roles, args);
	}

	@Override
	public LDo reconstruct(CommonTree source,
			LProtocolName proto, List<Role> roles,
			List<Arg<? extends NonRoleParamKind>> args)
	{
		return new LDo(source, proto, roles, args);
	}
	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		throw new RuntimeException("Unsupported for LDo: " + this);
	}

	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		throw new RuntimeException("Unsupported for LDo: " + this);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		throw new RuntimeException("Unsupported for LDo: " + this);
	}

	// CHECKME: factor up to base?
	@Override
	public LType getInlined(STypeInliner v)
	{
		LProtocolName fullname = this.proto;
		SubprotoSig sig = new SubprotoSig(fullname, this.roles, this.args);
		RecVar rv = v.getInlinedRecVar(sig);
		if (v.hasSig(sig))
		{
			return new LContinue(getSource(), rv);
		}
		v.pushSig(sig);
		LProtocol p = v.job.getContext().getProjection(fullname);  // This line differs from GDo version
		Substitutions subs = 
				new Substitutions(p.roles, this.roles, p.params, this.args);
		LSeq inlined = p.def.substitute(subs).getInlined(v);//, stack);  
				// i.e. returning a Seq -- rely on parent Seq to inline
		v.popSig();
		return new LRecursion(null, rv, inlined);
	}

	@Override
	public int hashCode()
	{
		int hash = 1303;
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
		if (!(o instanceof LDo))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LDo;
	}
}


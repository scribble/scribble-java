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
package org.scribble.core.lang.local;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.job.Core;
import org.scribble.core.lang.Protocol;
import org.scribble.core.lang.ProtocolMod;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.RecPruner;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.STypeUnfolder;
import org.scribble.core.visit.Substitutor;
import org.scribble.core.visit.local.EGraphBuilder;
import org.scribble.core.visit.local.ReachabilityChecker;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;

public class LProtocol extends Protocol<Local, LProtoName, LSeq>
		implements LNode
{
	public final Role self;

	public LProtocol(CommonTree source, List<ProtocolMod> mods,
			LProtoName fullname, List<Role> roles, Role self,
			List<MemberName<? extends NonRoleParamKind>> params, LSeq def)
	{
		super(source, mods, fullname, roles, params, def);
		this.self = self;
	}

	public LProtocol reconstruct(CommonTree source,
			List<ProtocolMod> mods, LProtoName fullname, List<Role> roles,
			Role self, List<MemberName<? extends NonRoleParamKind>> params, LSeq def)
	{
		return new LProtocol(source, mods, fullname, roles, self, params, def);
	}

	public void checkReachability() throws ScribException
	{
		this.def.visitWith(new ReachabilityChecker());
	}
	
	// CHECKME: drop from Protocol (after removing Protocol from SType?)
	// Pre: stack.peek is the sig for the calling Do (or top-level entry)
	// i.e., it gives the roles/args at the call-site
	@Override
	public LProtocol getInlined(STypeInliner<Local, LSeq> v)
	{
		// TODO: factor out with GProtocol
		List<Arg<? extends NonRoleParamKind>> params = new LinkedList<>();
		// Convert MemberName params to Args -- cf. NonRoleArgList::getParamKindArgs
		for (MemberName<? extends NonRoleParamKind> n : this.params)
		{
			if (n instanceof DataName)
			{
				params.add((DataName) n);
			}
			else if (n instanceof SigName)
			{
				params.add((SigName) n);
			}
			else
			{
				throw new RuntimeException("TODO: " + n);
			}
		}
		SubprotoSig sig = new SubprotoSig(this.fullname, this.roles, params);
		v.pushSig(sig);

		Substitutor<Local, LSeq> subs = new Substitutor<>(this.roles, sig.roles,
				this.params, sig.args);
		/*LSeq body = (LSeq) this.def.visitWithNoEx(subs).visitWithNoEx(v)
				.visitWithNoEx(new RecPruner<>());*/
		LSeq inlined = v.visitSeq(subs.visitSeq(this.def));
		RecVar rv = v.getInlinedRecVar(sig);
		LRecursion rec = v.core.config.tf.local.LRecursion(null, rv, inlined);  // CHECKME: or protodecl source?
		LSeq seq = v.core.config.tf.local.LSeq(null,
				Stream.of(rec).collect(Collectors.toList()));
		LSeq def = new RecPruner<Local, LSeq>().visitSeq(seq);
		/*//CHECKME: necessary for LProtocol/Projection? cf. global
		Set<Role> used = def.gather(new RoleGatherer<Global, GSeq>()::visit)
				.collect(Collectors.toSet());
		List<Role> rs = this.roles.stream().filter(x -> used.contains(x))  // Prune role decls
				.collect(Collectors.toList());*/
		return new LProtocol(getSource(), this.mods, this.fullname, this.roles,
				this.self, this.params, def);  // CHECKME: or null source?
	}
	
	@Override
	public LProtocol unfoldAllOnce(STypeUnfolder<Local, LSeq> v)
	{
		LSeq unf = (LSeq) this.def.visitWithNoThrow(v);
		return reconstruct(getSource(), this.mods, this.fullname, this.roles,
				this.self, this.params, unf);
	}

	public EGraph toEGraph(Core core)
	{
		//EGraphBuilderUtil2 b = job.newEGraphBuilderUtil();
		////b.init(null);  // FIXME: init param not used
		if (this.def.isEmpty())  // Empty Seq special case for top-level -- in general, Seq must be non-empty, cf. LSeq::buildGraph entry/exit
		{
			//EState s = b.getEntry();
			EState s = core.config.mf.local.EState(Collections.emptySet());
			return new EGraph(s, s);  // TODO: refactor constructor inside mf
		}
		EGraphBuilder b = new EGraphBuilder(core);
		this.def.visitWithNoThrow(b);
		return b.finalise();
	}
	
	@Override
	public String toString()
	{
		return this.mods.stream().map(x -> x.toString() + " ")
				.collect(Collectors.joining(" ")) + "local " + super.toString();
	}
	
	@Override
	protected String rolesToString()
	{
		return "("
				+ this.roles.stream()
						.map(x -> x.equals(this.self)
								? Constants.SELF_KW + " " + this.self
								: Constants.ROLE_KW + " " + x)
						.collect(Collectors.joining(", "))
				+ ")";
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.self.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LProtocol))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LProtocol;
	}
}

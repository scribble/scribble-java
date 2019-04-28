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
package org.scribble.core.lang.global;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.job.Core;
import org.scribble.core.lang.Protocol;
import org.scribble.core.lang.ProtocolMod;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.global.GRecursion;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.STypeUnfolder;
import org.scribble.core.visit.Substitutor;
import org.scribble.core.visit.gather.RoleGatherer;
import org.scribble.core.visit.global.ConnectionChecker;
import org.scribble.core.visit.global.ExtChoiceConsistencyChecker;
import org.scribble.core.visit.global.InlinedProjector;
import org.scribble.core.visit.global.RoleEnablingChecker;
import org.scribble.util.ScribException;

public class GProtocol extends Protocol<Global, GProtoName, GSeq>
		implements GNode  // Mainly for GDel.translate return (to include GProtocol)
{
	public GProtocol(CommonTree source, List<ProtocolMod> mods,
			GProtoName fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, GSeq def)
	{
		super(source, mods, fullname, roles, params, def);
	}

	public GProtocol reconstruct(CommonTree source,
			List<ProtocolMod> mods, GProtoName fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, GSeq def)
	{
		return new GProtocol(source, mods, fullname, roles, params, def);
	}

	// Cf. (e.g.) getInlined, that takes the Visitor (not Core)
	public void checkRoleEnabling(Core core) throws ScribException
	{
		Set<Role> rs = this.roles.stream().collect(Collectors.toSet());
		RoleEnablingChecker v = core.config.vf.RoleEnablingChecker(rs);
		this.def.visitWith(v);
	}

	public void checkExtChoiceConsistency(Core core) throws ScribException
	{
		Map<Role, Role> rs = this.roles.stream()
				.collect(Collectors.toMap(x -> x, x -> x));
		ExtChoiceConsistencyChecker v = core.config.vf
				.ExtChoiceConsistencyChecker(rs);
		this.def.visitWith(v);
	}

	public void checkConnectedness(Core core, boolean implicit)
			throws ScribException
	{
		Set<Role> rs = this.roles.stream().collect(Collectors.toSet());
		ConnectionChecker v = core.config.vf.ConnectionChecker(rs, implicit);
		this.def.visitWith(v);
	}
	
	// Currently assuming inlining (or at least "disjoint" protodecl projection, without role fixing)
	public LProjection projectInlined(Core core, Role self)
	{
		LSeq def = core.config.vf.InlinedProjector(core, self).visitSeq(this.def);
		LSeq fixed = core.config.vf.InlinedExtChoiceSubjFixer().visitSeq(def);
		return projectAux(core, self, this.roles, fixed);
	}

	public LProjection project(Core core, Role self)
	{
		LSeq def = core.config.vf.Projector(core, self).visitSeq(this.def);
			// FIXME: ext choice subj fixing, do pruning -- refactor to Job and use AstVisitor?
		return projectAux(core, self,
				core.getContext().getInlined(this.fullname).roles,  
						// Using inlined global, global role decls already pruned -- CHECKME: is this still relevant?
						// Actual projection role decl pruning done by projectAux
				def);
	}
	
	private LProjection projectAux(Core core, Role self, List<Role> decls,
			LSeq body)
	{
		LSeq pruned = core.config.vf.<Local, LSeq>RecPruner().visitSeq(body);
		LProtoName fullname = InlinedProjector
				.getFullProjectionName(this.fullname, self);
		Set<Role> tmp = pruned.gather(new RoleGatherer<Local, LSeq>()::visit)
				.collect(Collectors.toSet());
		List<Role> roles = decls.stream()
				.filter(x -> x.equals(self) || tmp.contains(x))
				.collect(Collectors.toList());
		List<MemberName<? extends NonRoleParamKind>> params =
				new LinkedList<>(this.params);  // CHECKME: filter params by usage?
		return new LProjection(this.mods, fullname, roles, self, params,
				this.fullname, pruned);
	}
	
	// Cf. (e.g.) checkRoleEnabling, that takes Core
	// CHECKME: drop from Protocol (after removing Protocol from SType?)
	// Pre: stack.peek is the sig for the calling Do (or top-level entry), i.e., it gives the roles/args at the call-site
	@Override
	public GProtocol getInlined(STypeInliner<Global, GSeq> v)
	{
		// TODO: factor out with LProtocol
		List<Arg<? extends NonRoleParamKind>> params = new LinkedList<>();
		// Convert MemberName params to Args -- cf. NonRoleArgList::getParamKindArgs -- TODO: factor out
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

		Substitutor<Global, GSeq> subs = v.core.config.vf.Substitutor(this.roles, sig.roles,
				this.params, sig.args);
		/*GSeq body = (GSeq) this.def.visitWithNoThrow(subs).visitWithNoThrow(v)
				.visitWithNoThrow(new RecPruner<>());*/
		GSeq inlined = v.visitSeq(subs.visitSeq(this.def));
		RecVar rv = v.getInlinedRecVar(sig);
		GRecursion rec = v.core.config.tf.global.GRecursion(null, rv, inlined);  // CHECKME: or protodecl source?
		GSeq seq = v.core.config.tf.global.GSeq(null,
				Stream.of(rec).collect(Collectors.toList()));
		GSeq def = v.core.config.vf.<Global, GSeq>RecPruner().visitSeq(seq);
		Set<Role> used = def.gather(new RoleGatherer<Global, GSeq>()::visit)
				.collect(Collectors.toSet());
		List<Role> rs = this.roles.stream().filter(x -> used.contains(x))  // Prune role decls -- CHECKME: what is an example? was this from before unused role checking?
				.collect(Collectors.toList());
		return new GProtocol(getSource(), this.mods, this.fullname, rs,
				this.params, def);
	}
	
	@Override
	public GProtocol unfoldAllOnce(STypeUnfolder<Global, GSeq> v)
	{
		GSeq unf = v.visitSeq(this.def);
		return reconstruct(getSource(), this.mods, this.fullname, this.roles,
				this.params, unf);
	}
	
	@Override
	public String toString()
	{
		return this.mods.stream().map(x -> x.toString() + " ")
				.collect(Collectors.joining()) + "global " + super.toString();
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
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
		if (!(o instanceof GProtocol))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GProtocol;
	}
}

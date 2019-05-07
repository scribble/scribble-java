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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.job.Core;
import org.scribble.core.lang.Protocol;
import org.scribble.core.lang.ProtoMod;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
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
	public GProtocol(CommonTree source, List<ProtoMod> mods,
			GProtoName fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, GSeq def)
	{
		super(source, mods, fullname, roles, params, def);
	}

	@Override
	public GProtocol reconstruct(CommonTree source,
			List<ProtoMod> mods, GProtoName fullname, List<Role> roles,
			List<MemberName<? extends NonRoleParamKind>> params, GSeq def)
	{
		return new GProtocol(source, mods, fullname, roles, params, def);
	}
	
	// Cf. (e.g.) checkRoleEnabling, that takes Core
	// CHECKME: drop from Protocol (after removing Protocol from SType?)
	// Pre: stack.peek is the sig for the calling Do (or top-level entry), i.e., it gives the roles/args at the call-site
	@Override
	public GProtocol getInlined(STypeInliner<Global, GSeq> v)
	{
		SubprotoSig sig = new SubprotoSig(this);
		v.pushSig(sig);

		Substitutor<Global, GSeq> subs = v.core.config.vf.Substitutor(this.roles, sig.roles,
				this.params, sig.args);
		GSeq inlined = v.visitSeq(subs.visitSeq(this.def));
		RecVar rv = v.getInlinedRecVar(sig);
		GRecursion rec = v.core.config.tf.global.GRecursion(null, rv, inlined);  // CHECKME: or protodecl source?
		GSeq seq = v.core.config.tf.global.GSeq(null, Arrays.asList(rec));
		GSeq def = v.core.config.vf.<Global, GSeq>RecPruner().visitSeq(seq);
		Set<Role> used = def.gather(new RoleGatherer<Global, GSeq>()::visit)
				.collect(Collectors.toSet());
		List<Role> rs = this.roles.stream().filter(x -> used.contains(x))  // Prune role decls -- CHECKME: what is an example? was this from before unused role checking?
				.collect(Collectors.toList());
		return //new GProtocol
				reconstruct(getSource(), this.mods, this.fullname, rs,
				this.params, def);
	}
	
	@Override
	public GProtocol unfoldAllOnce(STypeUnfolder<Global, GSeq> v)
	{
		GSeq unf = v.visitSeq(this.def);
		return reconstruct(getSource(), this.mods, this.fullname, this.roles,
				this.params, unf);
	}

	// Cf. (e.g.) getInlined, that takes the Visitor (not Core)
	public void checkRoleEnabling(Core core) throws ScribException
	{
		Set<Role> rs = this.roles.stream().collect(Collectors.toSet());
		RoleEnablingChecker v = core.config.vf.global.RoleEnablingChecker(rs);
		this.def.visitWith(v);
	}

	public void checkExtChoiceConsistency(Core core) throws ScribException
	{
		Map<Role, Role> rs = this.roles.stream()
				.collect(Collectors.toMap(x -> x, x -> x));
		ExtChoiceConsistencyChecker v = core.config.vf.global
				.ExtChoiceConsistencyChecker(rs);
		this.def.visitWith(v);
	}

	public void checkConnectedness(Core core, boolean implicit)
			throws ScribException
	{
		Set<Role> rs = this.roles.stream().collect(Collectors.toSet());
		ConnectionChecker v = core.config.vf.global.ConnectionChecker(rs, implicit);
		this.def.visitWith(v);
	}
	
	// Currently assuming inlining (or at least "disjoint" protodecl projection, without role fixing)
	public LProjection projectInlined(Core core, Role self)
	{
		LSeq def = core.config.vf.global.InlinedProjector(core, self)
				.visitSeq(this.def);
		LSeq fixed = core.config.vf.local.InlinedExtChoiceSubjFixer().visitSeq(def);
		return projectAux(core, self, this.roles, fixed);
	}
	
	// Does rec and role pruning
	private LProjection projectAux(Core core, Role self, List<Role> decls,
			LSeq def)
	{
		LSeq pruned = core.config.vf.<Local, LSeq>RecPruner().visitSeq(def);
		LProtoName fullname = InlinedProjector
				.getFullProjectionName(this.fullname, self);
		Set<Role> used = pruned.gather(new RoleGatherer<Local, LSeq>()::visit)
				.collect(Collectors.toSet());
		List<Role> roles = decls.stream()
				.filter(x -> x.equals(self) || used.contains(x))
				.collect(Collectors.toList());
		List<MemberName<? extends NonRoleParamKind>> params =
				new LinkedList<>(this.params);  // CHECKME: filter params by usage?
		return new LProjection(this.mods, fullname, roles, self, params,
				this.fullname, pruned);  // CHECKME: add/do via tf?
	}

	// N.B. no "fixing" passes done here -- need breadth-first passes to be sequentialised for subproto visiting
	public LProjection project(Core core, Role self)
	{
		LSeq def = core.config.vf.global.Projector(core, self).visitSeq(this.def);
		// N.B. not using projectAux, because don't want to prune role decls using RoleGatherer: it doesn't follow subprotos, so can over-prune roledecls (e.g., bad.efsm.grecursion.unfair.Test06, C)
		// Instead, retain full global role decls for now -- and prune later, via LDoArgPruner
		LSeq pruned = core.config.vf.<Local, LSeq>RecPruner().visitSeq(def);
		LProtoName fullname = InlinedProjector
				.getFullProjectionName(this.fullname, self);
		List<MemberName<? extends NonRoleParamKind>> params =
				new LinkedList<>(this.params);  // CHECKME: filter params by usage?
		// N.B. also not using PreRoleCollector here for role decl pruning (cf. LRoleDeclAndDoArgPruner), "initial" projection pass not complete yet, so cannot traverse all needed subprotos
		LProjection proj = new LProjection(this.mods, fullname, this.roles, self,
				params, this.fullname, pruned);
		// TODO: fully refactor ext choice subj fixing, do pruning, etc to Job and use AstVisitor?
		return proj;
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

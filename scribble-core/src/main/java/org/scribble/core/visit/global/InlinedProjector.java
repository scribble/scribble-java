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
package org.scribble.core.visit.global;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.job.Core;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.global.GConnect;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.local.LAcc;
import org.scribble.core.type.session.local.LChoice;
import org.scribble.core.type.session.local.LContinue;
import org.scribble.core.type.session.local.LDisconnect;
import org.scribble.core.type.session.local.LRecv;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LReq;
import org.scribble.core.type.session.local.LSend;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LSkip;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.RecPruner;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.core.visit.local.RecPruner1;
import org.scribble.core.visit.local.SingleContinueChecker;

// Pre: use on inlined (i.e., Do inlined, roles pruned)
public class InlinedProjector extends STypeAggNoThrow<Global, GSeq, LType>
{
	public final Core core;
	public final Role self;

	public InlinedProjector(Core core, Role self)
	{
		this.core = core;
		this.self = self;
	}

	@Override
	protected LType unit(SType<Global, GSeq> n)
	{
		throw new RuntimeException("Disregarded for Projector: " + n);
	}

	@Override
	protected LType agg(SType<Global, GSeq> n, Stream<LType> ts)
	{
		throw new RuntimeException("Disregarded for Projector: " + n + " ,, " + ts);
	}

	@Override
	public LType visitChoice(Choice<Global, GSeq> n)
	{
		Role subj = n.subj.equals(self) ? Role.SELF : n.subj;
				// CHECKME: "self" also explcitily used for Do, but implicitly for MessageTransfer, inconsistent?
		List<LSeq> tmp = n.blocks.stream().map(x -> visitSeq(x))
				.filter(x -> !x.isEmpty()).collect(Collectors.toList());
		if (tmp.isEmpty())
		{
			return LSkip.SKIP; // CHECKME: OK, or "empty" choice at subj still important?
		}
		return new LChoice(null, subj, tmp);
	}
	
	@Override
	public LType visitContinue(Continue<Global, GSeq> n)
	{
		return new LContinue(null, n.recvar);
	}

	@Override
	public LType visitDirectedInteraction(DirectedInteraction<Global, GSeq> n)
	{
		/*if (n.src.equals(self) && n.dst.equals(self))
		{
				// CHECKME: already checked?
		}*/
		if (n instanceof GConnect)  // FIXME
		{
			return n.src.equals(self) ? new LReq(null, n.msg, n.dst)
					: n.dst.equals(self)  ? new LAcc(null, n.src, n.msg)
					: LSkip.SKIP;
		}
		else //if (n instanceof GMessageTransfer)
		{
			return n.src.equals(self) ? new LSend(null, n.msg, n.dst)
					: n.dst.equals(self)  ? new LRecv(null, n.src, n.msg)
					: LSkip.SKIP;
		}
	}

	@Override
	public LType visitDisconnect(DisconnectAction<Global, GSeq> n)
	{
		/*if (n.src.equals(self) && n.dst.equals(self))
		{
				// CHECKME: already checked?
		}*/
		return (n.left.equals(self)) ? new LDisconnect(null, n.right)
				: (n.right.equals(self)) ? new LDisconnect(null, n.left)
				: LSkip.SKIP;
	}

	@Override
	public <N extends ProtoName<Global>> LType visitDo(Do<Global, GSeq, N> n)
	{
		throw new RuntimeException("Unsupported for Do: " + n);
	}

	@Override
	public LType visitRecursion(Recursion<Global, GSeq> n)
	{
		LSeq body = visitSeq(n.body);
		if (body.isEmpty())  // N.B. projection is doing empty-rec pruning
		{
			return LSkip.SKIP;
		}
		Set<RecVar> rvs = new HashSet<>();
		rvs.add(n.recvar);
		if (body.visitWithNoThrow(new SingleContinueChecker(rvs)))
		{
			return LSkip.SKIP;
		}
		/*LSeq pruned = new RecPruner1(this.core, n.recvar).visitSeq(body);
		if (pruned.isEmpty())
		{
			return LSkip.SKIP;
		}*/
		return new LRecursion(null, n.recvar, body);
	}

	// Param "hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	@Override
	public LSeq visitSeq(GSeq n)
	{
		List<LType> elems = n.elems.stream().map(x -> x.visitWithNoThrow(this))
				.filter(x -> !x.equals(LSkip.SKIP)).collect(Collectors.toList());
		return new LSeq(null, elems);  
				// Empty seqs converted to LSkip by GChoice/Recursion projection
				// And a WF top-level protocol cannot produce empty LSeq
				// So a projection never contains an empty LSeq -- i.e., "empty choice/rec" pruning unnecessary
	}

	public static LProtoName getSimpledProjectionName(GProtoName simpname,
			Role role)
	{
		return new LProtoName(simpname.toString() + "_" + role.toString());
	}

	// Role is the target subprotocol parameter (not the current projector self -- actually the self just popped) -- ?
	public static LProtoName getFullProjectionName(GProtoName fullname,
			Role role)
	{
		LProtoName simplename = InlinedProjector.getSimpledProjectionName(
				fullname.getSimpleName(), role);
		ModuleName modname = getProjectionModuleName(fullname.getPrefix(), simplename);
		return new LProtoName(modname, simplename);
	}

	// fullname is the un-projected name; localname is the already projected simple name
	public static ModuleName getProjectionModuleName(ModuleName fullname,
			LProtoName localname)
	{
		ModuleName simpname = new ModuleName(
				fullname.getSimpleName().toString() + "_" + localname.toString());
		return new ModuleName(fullname.getPrefix(), simpname); // Supports unary fullname
	}
}

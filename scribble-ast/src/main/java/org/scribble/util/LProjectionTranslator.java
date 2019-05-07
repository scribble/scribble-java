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
package org.scribble.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactory;
import org.scribble.ast.MsgNode;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayElem;
import org.scribble.ast.PayElemList;
import org.scribble.ast.ProtoModNode;
import org.scribble.ast.ProtoModList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.local.LProjectionDecl;
import org.scribble.ast.local.LProtoBlock;
import org.scribble.ast.local.LProtoDef;
import org.scribble.ast.local.LProtoHeader;
import org.scribble.ast.local.LSessionNode;
import org.scribble.ast.name.qualified.DataNameNode;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.ast.name.qualified.LProtoNameNode;
import org.scribble.ast.name.simple.DataParamNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.core.lang.ProtoMod;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.PayElemType;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.Msg;
import org.scribble.core.type.session.SigLit;
import org.scribble.core.type.session.local.LAcc;
import org.scribble.core.type.session.local.LChoice;
import org.scribble.core.type.session.local.LContinue;
import org.scribble.core.type.session.local.LDisconnect;
import org.scribble.core.type.session.local.LDo;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LRecv;
import org.scribble.core.type.session.local.LReq;
import org.scribble.core.type.session.local.LSend;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.job.Job;

// Cannot refactor into LType hierarchy, due to Maven module dependencies (scribble-core cannot see scribble-ast)
// Actually, this is a quite general LScribNode translator
public class LProjectionTranslator
{
	public final Job job;
	private final AstFactory af;
	
	private final RoleNode SELF_NODE;  // translate LChoice/LDo currently make their own self RoleNodes (convenience)

	public LProjectionTranslator(Job job)
	{
		this.job = job;
		this.af = job.config.af;
		SELF_NODE = this.af.RoleNode(null, Role.SELF.toString());
	}

	public LProjectionDecl translate(LProjection proj)
	{
		RoleDeclList rs = this.af.RoleDeclList(null, proj.roles.stream().map(x ->
			{
				RoleNode r = this.af.RoleNode(null, x.toString());
				return x.equals(proj.self) 
						? this.af.LSelfDecl(null, r)
						: this.af.RoleDecl(null, r);
			}).collect(Collectors.toList()));
		List<NonRoleParamDecl<? extends NonRoleParamKind>> pds = new LinkedList<>();
		for (MemberName<? extends NonRoleParamKind> p : proj.params)
		{
			if (p instanceof SigName)
			{
				SigParamNode n = this.af.SigParamNode(null, p.toString());
				pds.add(this.af.SigParamDecl(null, n));
			}
			else if (p instanceof DataName)
			{
				DataParamNode n = this.af.DataParamNode(null, p.toString());
				pds.add(this.af.DataParamDecl(null, n));
			}
			else
			{
				throw new RuntimeException(
						"[TODO] Unsupported kind: " + p.getClass() + "\n\t" + p);
			}
		}
		NonRoleParamDeclList ps = this.af.NonRoleParamDeclList(null, pds);

		ProtoModList mods = this.af.ProtoModList(null, proj.mods.stream()
				.map(x -> translate(x)).collect(Collectors.toList()));
		GProtoNameNode global = this.af.GProtoNameNode(null,
				IdNode.from(this.af, proj.global.getElements()));
		RoleNode self1 = this.af.RoleNode(null, proj.self.toString());  // FIXME: special "self" node?
		LProtoNameNode projFullname = this.af.LProtoNameNode(null,
				IdNode.from(this.af, proj.fullname.getElements()));
		LProtoHeader header = this.af.LProtoHeader(null, projFullname, rs, ps);
		LProtoDef def = this.af.LProtoDef(null, translate(proj.def));
		return this.af.LProjectionDecl(null, mods, header, def, global, self1);
	}
	
	// Cf. IdNode.from
	protected ProtoModNode translate(ProtoMod mod)
	{
		switch (mod)
		{
			case AUX:      return af.AuxMod(null);
			case EXPLICIT: return af.ExplicitMod(null);
			default:       throw new RuntimeException("Unknown modifier: " + mod);
		}
	}
	
	protected MsgNode translate(Msg msg)
	{
		if (msg.isSigLit())
		{	
			SigLit cast = (SigLit) msg;
			OpNode op = this.af.OpNode(null, cast.op.toString());
			PayElemList pay = this.af.PayElemList(null, cast.payload.elems.stream()
					.map(x -> translate(x)).collect(Collectors.toList()));
			return this.af.SigLitNode(null, op, pay);
		}
		else
		{
			SigName cast = (SigName) msg;
			List<IdNode> elems = Arrays.asList(cast.getElements()).stream()
					.map(x -> this.af.IdNode(null, x)).collect(Collectors.toList());
			return this.af.SigNameNode(null, elems);
		}
	}

	protected PayElem<?> translate(PayElemType<?> e)
	{
		if (e instanceof DataName)
		{
			DataNameNode res = this.af.DataNameNode(null,
					IdNode.from(af, ((DataName) e).getElements()));
			return this.af.UnaryPayElem(null, res);
		}
		else
		{
			throw new RuntimeException(
					"[TODO] Unsupported PayElemType: " + e.getClass() + "\n\t" + e);
		}
	}

	// N.B. in the following, "self" are just regular Role names, and translated to regular RoleNode (cf. LSelfDecl)

	protected org.scribble.ast.local.LSend translate(LSend t)
	{
		RoleNode dst = this.af.RoleNode(null, t.dst.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LSend(null, SELF_NODE, msg, dst);  // Ignoring t.src, but it's "self"
	}

	protected org.scribble.ast.local.LRecv translate(LRecv t)
	{
		RoleNode src = this.af.RoleNode(null, t.src.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LRecv(null, src, msg, SELF_NODE);  // Ignoring t.src, but it's "self"
	}

	protected org.scribble.ast.local.LAcc translate(LAcc t)
	{
		RoleNode src = this.af.RoleNode(null, t.src.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LAcc(null, src, msg, SELF_NODE);  // Ignoring t.src, but it's "self"
	}

	protected org.scribble.ast.local.LReq translate(LReq t)
	{
		RoleNode dst = this.af.RoleNode(null, t.dst.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LReq(null, SELF_NODE, msg, dst);  // Ignoring t.src, but it's "self"
	}

	protected org.scribble.ast.local.LDisconnect translate(LDisconnect t)
	{
		RoleNode peer = this.af.RoleNode(null, t.getPeer().toString());
		return this.af.LDisconnect(null, SELF_NODE, peer);  // Ignoring t.src, but it's "self"
	}

	protected org.scribble.ast.local.LContinue translate(LContinue t)
	{
		RecVarNode rv = this.af.RecVarNode(null, t.recvar.toString());
		return this.af.LContinue(null, rv);
	}

	protected org.scribble.ast.local.LDo translate(LDo t)
	{
		List<NonRoleArg> args = new LinkedList<>();
		for (Arg<? extends NonRoleParamKind> p : t.args)
		{
			NonRoleArgKind k = p.getKind();
			NonRoleArgNode a;
			if (k.equals(SigKind.KIND))
			{
				if (p instanceof SigLit)
				{
					SigLit cast = (SigLit) p;
					OpNode op = this.af.OpNode(null, cast.op.toString());
					PayElemList pay = this.af.PayElemList(null, cast.payload.elems
							.stream().map(x -> translate(x)).collect(Collectors.toList()));
					a = this.af.SigLitNode(null, op, pay);  // FIXME: could need to be SigParamNode
				}
				else if (p instanceof SigName)
				{
					a = this.af.SigNameNode(null,
							IdNode.from(this.af, ((SigName) p).getElements()));
						// FIXME: could need to be SigParamNode
				}
				else 
				{
					throw new RuntimeException(
							"Shouldn't get in here: " + p.getClass() + "\n\t" + p);
				}
			}
			else if (k.equals(DataKind.KIND))
			{
				if (p instanceof DataName)
				{
					a = this.af.DataNameNode(null,
							IdNode.from(this.af, ((SigName) p).getElements()));
						// FIXME: could need to be DataParamNode
				}
				else 
				{
					throw new RuntimeException(
							"[TODO] : " + p.getClass() + "\n\t" + p);
				}
			}
			else
			{
				throw new RuntimeException(
						"[TODO] Unsupported kind: " + p.getClass() + "\n\t" + p);
			}
			args.add(this.af.NonRoleArg(null, a));
		}
		NonRoleArgList as = this.af.NonRoleArgList(null, args);

		LProtoNameNode proto = this.af.LProtoNameNode(null,
				IdNode.from(this.af, t.proto.getElements()));
		RoleArgList rs = this.af.RoleArgList(null,  // Cf. translate(LProjectionDecl)
				t.roles.stream().map(
						x -> this.af.RoleArg(null, this.af.RoleNode(null, x.toString())))  // Includes "self"
						.collect(Collectors.toList()));
		return this.af.LDo(null, proto, as, rs);
	}

	protected org.scribble.ast.local.LChoice translate(LChoice t)
	{
		RoleNode subj = this.af.RoleNode(null, t.subj.toString());  // Includes "self"
		List<LProtoBlock> blocks = t.blocks.stream().map(x -> translate(x))
				.collect(Collectors.toList());
		return this.af.LChoice(null, subj, blocks);
	}

	protected org.scribble.ast.local.LRecursion translate(LRecursion t)
	{
		RecVarNode rv = this.af.RecVarNode(null, t.recvar.toString());
		return this.af.LRecursion(null, rv, translate(t.body));
	}

	protected org.scribble.ast.local.LProtoBlock translate(LSeq t)
	{
		List<LSessionNode> elems = t.getElements().stream().map(x -> translate(x))
				.collect(Collectors.toList());
		return this.af.LProtoBlock(null, this.af.LInteractionSeq(null, elems));
	}

	// Cannot refactor into LType hierarchy, due to Maven module dependencies (scribble-core cannot see scribble-ast)
	// Only LSeq not done, return is not an LSessionNode
	protected LSessionNode translate(LType t)
	{
		if (t instanceof LAcc)
		{
			return translate((LAcc) t);
		}
		else if (t instanceof LChoice)
		{
			return translate((LChoice) t);
		}
		else if (t instanceof LContinue)
		{
			return translate((LContinue) t);
		}
		else if (t instanceof LDisconnect)
		{
			return translate((LDisconnect) t);
		}
		else if (t instanceof LDo)
		{
			return translate((LDo) t);
		}
		else if (t instanceof LRecursion)
		{
			return translate((LRecursion) t);
		}
		else if (t instanceof LRecv)
		{
			return translate((LRecv) t);
		}
		else if (t instanceof LReq)
		{
			return translate((LReq) t);
		}
		else if (t instanceof LSend)
		{
			return translate((LSend) t);
		}
		else
		{
			throw new RuntimeException("[TODO] Unhandled LType: " + t.getClass() + "\n" + t);
		}
	}
}

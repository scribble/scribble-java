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
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayElem;
import org.scribble.ast.PayElemList;
import org.scribble.ast.ProtoMod;
import org.scribble.ast.ProtoModList;
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
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.core.job.Core;
import org.scribble.core.job.CoreContext;
import org.scribble.core.lang.ProtocolMod;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.PayElemType;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.SigName;
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
public class LProjectionTranslator
{
	public final Job job;
	private final AstFactory af;

	public LProjectionTranslator(Job job)
	{
		this.job = job;
		this.af = job.config.af;
	}

	public LProjectionDecl translate(GProtoName fullname, Role self)
	{
		Core core = this.job.getCore();
		CoreContext jobc = core.getContext();
		LProjection ltype = jobc.getProjection(fullname, self);

		RoleDeclList rs = this.af.RoleDeclList(null,
				ltype.roles.stream().map(
						x -> this.af.RoleDecl(null, this.af.RoleNode(null, x.toString())))
						.collect(Collectors.toList()));
		List<NonRoleParamDecl<? extends NonRoleParamKind>> pds = new LinkedList<>();
		for (MemberName<? extends NonRoleParamKind> p : ltype.params)
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

		ProtoModList mods = this.af.ProtoModList(null, ltype.mods.stream()
				.map(x -> translate(x)).collect(Collectors.toList()));
		GProtoNameNode global = this.af.GProtoNameNode(null,
				IdNode.from(this.af, fullname.getElements()));
		RoleNode self1 = this.af.RoleNode(null, self.toString());
		LProtoNameNode projFullname = this.af.LProtoNameNode(null,
				IdNode.from(this.af, fullname.getElements()));
		LProtoHeader header = this.af.LProtoHeader(null, projFullname, rs, ps);
		LProtoDef def = this.af.LProtoDef(null, translate(ltype.def));
		return this.af.LProjectionDecl(null, mods, header, def, global, self1);
	}
	
	// Cf. IdNode.from
	protected ProtoMod translate(ProtocolMod mod)
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
			PayElemList pay = this.af.PayElemList(null, cast.payload.elems.stream()
					.map(x -> translate(x)).collect(Collectors.toList()));
			return this.af.SigLitNode(null, this.af.OpNode(null, cast.op.toString()),
					pay);
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
			return (PayElem<?>) res;
		}
		else
		{
			throw new RuntimeException(
					"[TODO] Unsupported PayElemType: " + e.getClass() + "\n\t" + e);
		}
	}
	
	protected org.scribble.ast.local.LAcc translate(LAcc t)
	{
		RoleNode src = this.af.RoleNode(null, t.src.toString());
		RoleNode dst = this.af.RoleNode(null, t.dst.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LAcc(null, src, msg, dst);
	}

	protected org.scribble.ast.local.LReq translate(LReq t)
	{
		RoleNode src = this.af.RoleNode(null, t.src.toString());
		RoleNode dst = this.af.RoleNode(null, t.dst.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LReq(null, src, msg, dst);
	}

	protected org.scribble.ast.local.LSend translate(LSend t)
	{
		RoleNode src = this.af.RoleNode(null, t.src.toString());
		RoleNode dst = this.af.RoleNode(null, t.dst.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LSend(null, src, msg, dst);
	}

	protected org.scribble.ast.local.LRecv translate(LRecv t)
	{
		RoleNode src = this.af.RoleNode(null, t.src.toString());
		RoleNode dst = this.af.RoleNode(null, t.dst.toString());
		MsgNode msg = translate(t.msg);
		return this.af.LRecv(null, src, msg, dst);
	}

	protected org.scribble.ast.local.LDisconnect translate(LDisconnect t)
	{
		RoleNode self = this.af.RoleNode(null, t.left.toString());
		RoleNode peer = this.af.RoleNode(null, t.right.toString());
		return this.af.LDisconnect(null, self, peer);
	}

	protected org.scribble.ast.local.LChoice translate(LChoice t)
	{
		RoleNode subj = this.af.RoleNode(null, t.subj.toString());  // CHECKME: subj's already always fixed?
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
			throw new RuntimeException("[TODO] Unknown LType: " + t.getClass() + "\n" + t);
		}
	}
}

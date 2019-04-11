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
package org.scribble.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.global.GSessionNode;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.qualified.QualifiedNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.ast.name.simple.TypeParamNode;
import org.scribble.core.type.kind.DataTypeKind;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.kind.ModuleKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.OpKind;
import org.scribble.core.type.kind.PayloadTypeKind;
import org.scribble.core.type.kind.RecVarKind;
import org.scribble.core.type.kind.RoleKind;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.Op;
import org.scribble.del.DefaultDel;
import org.scribble.del.ImportModuleDel;
import org.scribble.del.ModuleDel;
import org.scribble.del.NonRoleArgListDel;
import org.scribble.del.NonRoleParamDeclListDel;
import org.scribble.del.RoleArgListDel;
import org.scribble.del.RoleDeclDel;
import org.scribble.del.RoleDeclListDel;
import org.scribble.del.ScribDel;
import org.scribble.del.global.GChoiceDel;
import org.scribble.del.global.GConnectDel;
import org.scribble.del.global.GContinueDel;
import org.scribble.del.global.GDelegationElemDel;
import org.scribble.del.global.GDisconnectDel;
import org.scribble.del.global.GDoDel;
import org.scribble.del.global.GInteractionSeqDel;
import org.scribble.del.global.GMessageTransferDel;
import org.scribble.del.global.GProtocolBlockDel;
import org.scribble.del.global.GProtocolDeclDel;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.del.global.GRecursionDel;
import org.scribble.del.global.GWrapDel;
import org.scribble.del.name.qualified.DataTypeNodeDel;
import org.scribble.del.name.qualified.MessageSigNameNodeDel;
import org.scribble.del.name.simple.AmbigNameNodeDel;
import org.scribble.del.name.simple.NonRoleParamNodeDel;
import org.scribble.del.name.simple.RecVarNodeDel;
import org.scribble.del.name.simple.RoleNodeDel;


public class AstFactoryImpl implements AstFactory
{
	//public static final AstFactory FACTORY = new AstFactoryImpl();

	private static MessageSigNode UNIT_MESSAGE_SIG_NODE;  // A "constant"
	
	public AstFactoryImpl()
	{
		
	}

	protected ScribDel createDefaultDelegate()
	{
		return new DefaultDel();
	}
	
	// FIXME: factor out -- change return to void (ScribNodeBase.del is a mutating setter)
	//@SuppressWarnings("unchecked")
	protected static <T extends ScribNodeBase> T del(T n, ScribDel del)
	{
		/*ScribNodeBase ret = n.del(del);  // FIXME: del makes another shallow copy of n
		if (ret.getClass() != n.getClass())
		{
			throw new RuntimeException("Shouldn't get in here: " + ret.getClass() + ", " + n.getClass());
		}
		return (T) ret;*/
		return ScribNodeBase.del(n, del);
	}
	
  // FIXME: inconsistent wrt. this.source -- it is essentially parsed (in the sense of *omitted* syntax), but not recorded
	// FIXME: this pattern is not ideal ("exposed" public constructor arg in GWrap/GDisconnect)
	//     An alternative would be to make subclasses, e.g., UnitMessageSigNode, UnitOp, EmptyPayloadElemList -- but a lot of extra classes
	protected MessageSigNode UnitMessageSigNode()
	{
		if (UNIT_MESSAGE_SIG_NODE == null)
		{
			UNIT_MESSAGE_SIG_NODE = MessageSigNode(null,
					(OpNode) SimpleNameNode(null, OpKind.KIND,
							Op.EMPTY_OP.toString()),
					PayloadElemList(null, Collections.emptyList()));
					// Payload.EMPTY_PAYLOAD?
		}
		return UNIT_MESSAGE_SIG_NODE;
	}
	
	@Override
	public MessageSigNode MessageSigNode(CommonTree source, OpNode op,
			PayloadElemList payload)
	{
		MessageSigNode msn = new MessageSigNode(source, op, payload);
		msn = del(msn, createDefaultDelegate());
		return msn;
	}

	@Override
	//public PayloadElemList PayloadElemList(List<PayloadElem> payloadelems)
	public PayloadElemList PayloadElemList(Token t,
			List<PayloadElem<?>> payloadelems)
	{
		PayloadElemList p = new PayloadElemList(t);
		p.addChildren(payloadelems);
		p = del(p, createDefaultDelegate());
		return p;
	}

	/*@Override
	public PayloadElem PayloadElem(PayloadElemNameNode name)
	{
		PayloadElem pe = new PayloadElem(name);
		pe = del(pe, createDefaultDelegate());
		return pe;
	}*/

	@Override
	public <K extends PayloadTypeKind> UnaryPayloadElem<K> UnaryPayloadElem(
			Token t, PayloadElemNameNode<K> name)
	{
		UnaryPayloadElem<K> de = new UnaryPayloadElem<>(t);
		// Cf. Scribble.g children order
		de.addChild(name);
		de = del(de, createDefaultDelegate());
		return de;
		//throw new RuntimeException("[TODO] : " + name);  // Currently parsed "directly" by ANTLR -- do we still need to construct manually?
	}

	@Override
	public GDelegationElem GDelegationElem(CommonTree source,
			GProtocolNameNode proto, RoleNode role)
	{
		GDelegationElem de = new GDelegationElem(source, proto, role);
		//de = del(de, createDefaultDelegate());
		de = del(de, new GDelegationElemDel());  // FIXME: GDelegationElemDel
		return de;
	}
	
	@Override
	public Module Module(CommonTree source, ModuleDecl moddecl,
			List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data,
			List<ProtocolDecl<?>> protos)
	{
		Module module = new Module(source, moddecl, imports, data, protos);
		module = del(module, new ModuleDel());
		return module;
	}

	@Override
	public ModuleDecl ModuleDecl(Token t, ModuleNameNode fullmodname)
	{
		ModuleDecl n = new ModuleDecl(t);
		n.addChild(fullmodname);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public ImportModule ImportModule(CommonTree source, ModuleNameNode modname,
			ModuleNameNode alias)
	{
		ImportModule im = new ImportModule(source, modname, alias);
		im = del(im, new ImportModuleDel());
		return im;
	}

	@Override
	public MessageSigNameDecl MessageSigNameDecl(Token t, IdNode schema,
			IdNode extName, IdNode extSource, MessageSigNameNode alias)
	{
		MessageSigNameDecl n = new MessageSigNameDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public DataTypeDecl DataTypeDecl(Token t, IdNode schema,
			IdNode extName, IdNode extSource, DataTypeNode alias)
	{
		DataTypeDecl n = new DataTypeDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public GProtocolDecl GProtocolDecl(Token t, ProtocolModList mods,
			GProtocolHeader header, GProtocolDef def)
	{
		GProtocolDecl gpd = new GProtocolDecl(t);
		gpd.addChild(mods);
		gpd.addChild(header);
		gpd.addChild(def);
		gpd = del(gpd, new GProtocolDeclDel());
		return gpd;
	}

	@Override
	public GProtocolHeader GProtocolHeader(Token t,
			GProtocolNameNode name, RoleDeclList roledecls,
			NonRoleParamDeclList paramdecls)
	{
		GProtocolHeader n = new GProtocolHeader(t);
		n.addChild(name);
		n.addChild(paramdecls);
		n.addChild(roledecls);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public RoleDeclList RoleDeclList(Token t, List<RoleDecl> rds)
	{
		RoleDeclList rdl = new RoleDeclList(t);
		// Cf. Scribble.g children order
		rdl.addChildren(rds);
		rdl = del(rdl, new RoleDeclListDel());
		return rdl;
	}

	@Override
	public RoleDecl RoleDecl(Token t, RoleNode namenode)
	{
		RoleDecl n = new RoleDecl(t);
		n.addChild(namenode);
		n = del(n, new RoleDeclDel());
		return n;
	}

	/*@Override
	public ConnectDecl ConnectDecl(RoleNode src, RoleNode role)
	{
		ConnectDecl cd = new ConnectDecl(src, role);
		cd = del(cd, new ConnectDeclDel());
		return cd;
	}*/

	@Override
	public NonRoleParamDeclList NonRoleParamDeclList(Token t,
			List<NonRoleParamDecl<NonRoleParamKind>> pds)
	{
		NonRoleParamDeclList pdl = new NonRoleParamDeclList(t);
		// Cf. Scribble.g children order
		pdl.addChildren(pds);
		pdl = del(pdl, new NonRoleParamDeclListDel());
		return pdl;
	}

	@Override
	public <K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(
			CommonTree source, K kind, NonRoleParamNode<K> namenode)
	{
		/*NonRoleParamDecl<K> pd = new NonRoleParamDecl<K>(source, kind, namenode);
		pd = del(pd, new NonRoleParamDeclDel());
		return pd;*/
		throw new RuntimeException("Deprecated");
	}

	@Override
	public GProtocolDef GProtocolDef(Token t, GProtocolBlock block)
	{
		GProtocolDef n = new GProtocolDef(t);
		n.addChild(block);
		n = del(n, new GProtocolDefDel());
		return n;
	}

	@Override
	public GProtocolBlock GProtocolBlock(Token t, GInteractionSeq seq)
	{
		GProtocolBlock n = new GProtocolBlock(t);
		n.addChild(seq);
		n = del(n, new GProtocolBlockDel());
		return n;
	}

	@Override
	public GInteractionSeq GInteractionSeq(CommonTree source,
			List<GSessionNode> actions)
	{
		GInteractionSeq gis = new GInteractionSeq(source, actions);
		gis = del(gis, new GInteractionSeqDel());
		return gis;
	}

	@Override
	public GMessageTransfer GMessageTransfer(Token t, RoleNode src,
			MessageNode msg, List<RoleNode> dsts)
	{
		GMessageTransfer n = new GMessageTransfer(t);
		n.addChild(msg);
		n.addChild(src);
		n.addChildren(dsts);
		n = del(n, new GMessageTransferDel());
		return n;
	}

	@Override
	public GConnect GConnect(Token t, RoleNode src, MessageNode msg, RoleNode dst)
	{
		GConnect n = new GConnect(t);
		n.addChild(msg);
		n.addChild(src);
		n.addChild(dst);
		n = del(n, new GConnectDel());
		return n;
	}

	@Override
	public GDisconnect GDisconnect(Token t, RoleNode left, RoleNode right)
	{
		GDisconnect n = new GDisconnect(t);
		n.addChild(left);
		n.addChild(right);
		n = del(n, new GDisconnectDel());
		return n;
	}

	@Override
	public GWrap GWrap(Token t, RoleNode src, RoleNode dst)
	{
		GWrap n = new GWrap(t);
		n.addChild(src);
		n.addChild(dst);
		n = del(n, new GWrapDel());
		return n;
	}

	@Override
	public GChoice GChoice(CommonTree source, RoleNode subj,
			List<GProtocolBlock> blocks)
	{
		GChoice gc = new GChoice(source, subj, blocks);
		gc = del(gc, new GChoiceDel());
		return gc;
	}

	@Override
	public GRecursion GRecursion(Token t, RecVarNode recvar,
			GProtocolBlock block)
	{
		GRecursion n = new GRecursion(t);
		n.addChild(recvar);
		n.addChild(block);
		n = del(n, new GRecursionDel());
		return n;
	}

	@Override
	public GContinue GContinue(Token t, RecVarNode recvar)
	{
		GContinue n = new GContinue(t);
		n.addChild(recvar);
		n = del(n, new GContinueDel());
		return n;
	}

	@Override
	public GDo GDo(Token t, RoleArgList roleinstans,
			NonRoleArgList arginstans, GProtocolNameNode proto)
	{
		GDo n = new GDo(t);
		n.addChild(proto);
		n.addChild(roleinstans);
		n.addChild(arginstans);
		n = del(n, new GDoDel());
		return n;
	}

	@Override
	public RoleArgList RoleArgList(Token t, List<RoleArg> ris)
	{
		RoleArgList n = new RoleArgList(t);
		n.addChildren(ris);
		n = del(n, new RoleArgListDel());
		return n;
	}

	@Override
	public RoleArg RoleArg(Token t, RoleNode role)
	{
		RoleArg n = new RoleArg(t);
		n.addChild(role);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public NonRoleArgList NonRoleArgList(Token t, List<NonRoleArg> ais)
	{
		NonRoleArgList n = new NonRoleArgList(t);
		n.addChildren(ais);
		n = del(n, new NonRoleArgListDel());
		return n;
	}

	@Override
	public NonRoleArg NonRoleArg(Token t, NonRoleArgNode arg)
	{
		NonRoleArg n = new NonRoleArg(t);
		n.addChild(arg);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public <K extends Kind> NameNode<K> SimpleNameNode(CommonTree source, K kind,
			String identifier)
	{
		NameNode<? extends Kind> snn = null;

		// "Custom" del's
		if (kind.equals(RecVarKind.KIND))
		{
			snn = new RecVarNode(source, identifier);
			snn = del(snn, new RecVarNodeDel());
		}
		else if (kind.equals(RoleKind.KIND))
		{
			snn = new RoleNode(source, identifier);
			snn = del(snn, new RoleNodeDel());
		}
		if (snn != null)
		{
			return castNameNode(kind, snn);
		}

		// Default del's
		if (kind.equals(OpKind.KIND))
		{
			snn = new OpNode(source, identifier);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castNameNode(kind, del(snn, createDefaultDelegate()));
	}

	@Override
	public <K extends Kind> QualifiedNameNode<K> QualifiedNameNode(
			CommonTree source, K kind, String... elems)
	{
		QualifiedNameNode<? extends Kind> qnn = null;
		if (kind.equals(SigKind.KIND))
		{
			qnn = new MessageSigNameNode(source, elems);
			qnn = del(qnn, new MessageSigNameNodeDel());
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			qnn = new DataTypeNode(source, elems);
			qnn = del(qnn, new DataTypeNodeDel());
		}
		if (qnn != null)
		{
			return castNameNode(kind, qnn);
		}

		if (kind.equals(ModuleKind.KIND))
		{
			qnn = new ModuleNameNode(source, elems);
		}
		else if (kind.equals(Global.KIND))
		{
			qnn = new GProtocolNameNode(source, elems);
		}
		else if (kind.equals(Local.KIND))
		{
			qnn = new LProtocolNameNode(source, elems);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		System.out.println("rrrr: " + kind + " ,, " + Arrays.toString(elems));
		return castNameNode(kind, del(qnn, createDefaultDelegate()));
	}

	protected static <T extends NameNode<K>, K extends Kind> T castNameNode(
			K kind, NameNode<? extends Kind> n)
	{
		if (!n.toName().getKind().equals(kind))
		{
			throw new RuntimeException("Shouldn't get in here: " + kind + ", " + n);
		}
		@SuppressWarnings("unchecked")
		T tmp = (T) n;
		return tmp;
	}

	@Override
	public AmbigNameNode AmbiguousNameNode(CommonTree source, String identifier)
	{
		AmbigNameNode ann = new AmbigNameNode(source, identifier);
		ann = (AmbigNameNode) ann.del(new AmbigNameNodeDel());
		return ann;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(
			CommonTree source, K kind, String identifier)
	{
		NonRoleParamNode<K> pn; //= new NonRoleParamNode<K>(source, kind, identifier);
		if (kind.equals(SigKind.KIND))
		{
			pn = (NonRoleParamNode<K>) new SigParamNode(source.getToken());
		}
		else
		{
			pn = (NonRoleParamNode<K>) new TypeParamNode(source.getToken());
		}
		pn = del(pn, new NonRoleParamNodeDel());
		return pn;
	}
}









/*
	@Override
	public DummyProjectionRoleNode DummyProjectionRoleNode()
	{
		DummyProjectionRoleNode dprn = new DummyProjectionRoleNode();
		dprn = (DummyProjectionRoleNode) dprn.del(createDefaultDelegate());
		return dprn;
	}

	@Override
	public LDelegationElem LDelegationElem(CommonTree source,
			LProtocolNameNode proto)
	{
		LDelegationElem de = new LDelegationElem(source, proto);
		de = del(de, createDefaultDelegate());
		return de;
	}

	@Override  // Called from LProtocolDecl::clone, but currently never used  -- local proto decls only projected, not parsed
	public LProtocolDecl LProtocolDecl(CommonTree source, List<ProtocolMod> mods,
			LProtocolHeader header, LProtocolDef def)
	{
		LProtocolDecl lpd = new LProtocolDecl(source, mods, header, def);
		lpd = del(lpd, new LProtocolDeclDel());
		return lpd;
	}

	@Override
	public LProjectionDecl LProjectionDecl(CommonTree source,
			List<ProtocolMod> mods, GProtocolName fullname, Role self,
			LProtocolHeader header, LProtocolDef def) 
			// del extends that of LProtocolDecl
	{
		LProjectionDecl lpd = new LProjectionDecl(source, mods, header, def);
		lpd = del(lpd, new LProjectionDeclDel());//fullname, self));
		return lpd;
	}

	@Override
	public LProtocolHeader LProtocolHeader(CommonTree source,
			LProtocolNameNode name, RoleDeclList roledecls,
			NonRoleParamDeclList paramdecls)
	{
		LProtocolHeader lph = new LProtocolHeader(source, name, roledecls,
				paramdecls);
		lph = del(lph, createDefaultDelegate());
		return lph;
	}

	@Override
	public SelfRoleDecl SelfRoleDecl(CommonTree source, RoleNode namenode)
	{
		SelfRoleDecl rd = new SelfRoleDecl(source, namenode);
		rd = del(rd, new RoleDeclDel());
		return rd;
	}

	@Override
	public LProtocolDef LProtocolDef(CommonTree source, LProtocolBlock block)
	{
		LProtocolDef lpd = new LProtocolDef(source, block);
		lpd = del(lpd, new LProtocolDefDel());
		return lpd;
	}

	@Override
	public LProtocolBlock LProtocolBlock(CommonTree source, LInteractionSeq seq)
	{
		LProtocolBlock lpb = new LProtocolBlock(source, seq);
		lpb = del(lpb, new LProtocolBlockDel());
		return lpb;
	}

	@Override
	public LInteractionSeq LInteractionSeq(CommonTree source,
			List<LSessionNode> actions)
	{
		LInteractionSeq lis = new LInteractionSeq(source, actions);
		lis = del(lis, new LInteractionSeqDel());
		return lis;
	}

	@Override
	public LSend LSend(CommonTree source, RoleNode src, MessageNode msg,
			List<RoleNode> dests)
	{
		LSend ls = new LSend(source, src, msg, dests);
		ls = del(ls, new LSendDel());
		return ls;
	}

	@Override
	public LRecv LReceive(CommonTree source, RoleNode src, MessageNode msg,
			List<RoleNode> dests)
	{
		LRecv ls = new LRecv(source, src, msg, dests);
		ls = del(ls, new LReceiveDel());
		return ls;
	}
	
	@Override
	public LRequest LRequest(CommonTree source, RoleNode src, MessageNode msg,
			RoleNode dest)
	//public LConnect LConnect(RoleNode src, RoleNode dest)
	{
		LRequest lc = new LRequest(source, src, msg, dest);
		//LConnect lc = new LConnect(src, dest);
		lc = del(lc, new LRequestDel());
		return lc;
	}

	@Override
	public LAccept LAccept(CommonTree source, RoleNode src, MessageNode msg,
			RoleNode dest)
	//public LAccept LAccept(RoleNode src, RoleNode dest)
	{
		LAccept la = new LAccept(source, src, msg, dest);
		//LAccept la = new LAccept(src, dest);
		la = del(la, new LAcceptDel());
		return la;
	}

	@Override
	public LDisconnect LDisconnect(CommonTree source, RoleNode self,
			RoleNode peer)
	{
		LDisconnect lc = new LDisconnect(source, UnitMessageSigNode(), self, peer);
		lc = del(lc, new LDisconnectDel());
		return lc;
	}

	@Override
	public LWrapClient LWrapClient(CommonTree source, RoleNode self,
			RoleNode peer)
	{
		LWrapClient lwc = new LWrapClient(source, UnitMessageSigNode(), self, peer);
		lwc = del(lwc, new LWrapClientDel());
		return lwc;
	}

	@Override
	public LWrapServer LWrapServer(CommonTree source, RoleNode self,
			RoleNode peer)
	{
		LWrapServer lws = new LWrapServer(source, UnitMessageSigNode(), self, peer);
		lws = del(lws, new LWrapServerDel());
		return lws;
	}

	@Override
	public LChoice LChoice(CommonTree source, RoleNode subj,
			List<LProtocolBlock> blocks)
	{
		LChoice lc = new LChoice(source, subj, blocks);
		lc = del(lc, new LChoiceDel());
		return lc;
	}

	@Override
	public LRecursion LRecursion(CommonTree source, RecVarNode recvar,
			LProtocolBlock block)
	{
		LRecursion lr = new LRecursion(source, recvar, block);
		lr = del(lr, new LRecursionDel());
		return lr;
	}

	@Override
	public LContinue LContinue(CommonTree source, RecVarNode recvar)
	{
		LContinue lc = new LContinue(source, recvar);
		lc = del(lc, new LContinueDel());
		return lc;
	}

	@Override
	public LDo LDo(CommonTree source, RoleArgList roleinstans,
			NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		LDo ld = new LDo(source, roleinstans, arginstans, proto);
		ld = del(ld, new LDoDel());
		return ld;
	}
*/

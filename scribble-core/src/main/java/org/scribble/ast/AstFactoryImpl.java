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

import java.util.Collections;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.local.LAccept;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LConnect;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LDelegationElem;
import org.scribble.ast.local.LDisconnect;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.local.LReceive;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.local.LSend;
import org.scribble.ast.local.LWrapClient;
import org.scribble.ast.local.LWrapServer;
import org.scribble.ast.local.SelfRoleDecl;
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.qualified.QualifiedNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.DummyProjectionRoleNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.DefaultDel;
import org.scribble.del.ImportModuleDel;
import org.scribble.del.ModuleDel;
import org.scribble.del.NonRoleArgListDel;
import org.scribble.del.NonRoleParamDeclDel;
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
import org.scribble.del.local.LAcceptDel;
import org.scribble.del.local.LChoiceDel;
import org.scribble.del.local.LConnectDel;
import org.scribble.del.local.LContinueDel;
import org.scribble.del.local.LDisconnectDel;
import org.scribble.del.local.LDoDel;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.del.local.LProjectionDeclDel;
import org.scribble.del.local.LProtocolBlockDel;
import org.scribble.del.local.LProtocolDeclDel;
import org.scribble.del.local.LProtocolDefDel;
import org.scribble.del.local.LReceiveDel;
import org.scribble.del.local.LRecursionDel;
import org.scribble.del.local.LSendDel;
import org.scribble.del.local.LWrapClientDel;
import org.scribble.del.local.LWrapServerDel;
import org.scribble.del.name.AmbigNameNodeDel;
import org.scribble.del.name.DataTypeNodeDel;
import org.scribble.del.name.MessageSigNameNodeDel;
import org.scribble.del.name.ParamNodeDel;
import org.scribble.del.name.RecVarNodeDel;
import org.scribble.del.name.RoleNodeDel;
import org.scribble.type.kind.DataTypeKind;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.Kind;
import org.scribble.type.kind.Local;
import org.scribble.type.kind.ModuleKind;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.kind.OpKind;
import org.scribble.type.kind.PayloadTypeKind;
import org.scribble.type.kind.RecVarKind;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.kind.SigKind;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Op;
import org.scribble.type.name.Role;


public class AstFactoryImpl implements AstFactory
{
	//public static final AstFactory FACTORY = new AstFactoryImpl();

	private static MessageSigNode UNIT_MESSAGE_SIG_NODE;  // A "constant"
	
	public AstFactoryImpl()
	{
		
	}
	
  // FIXME: inconsistent wrt. this.source -- it is essentially parsed (in the sense of *omitted* syntax), but not recorded
	// FIXME: this pattern is not ideal ("exposed" public constructor arg in GWrap/GDisconnect)
	//     An alternative would be to make subclasses, e.g., UnitMessageSigNode, UnitOp, EmptyPayloadElemList -- but a lot of extra classes
	protected MessageSigNode UnitMessageSigNode()
	{
		if (UNIT_MESSAGE_SIG_NODE == null)
		{
			UNIT_MESSAGE_SIG_NODE = MessageSigNode(null, (OpNode) SimpleNameNode(null, OpKind.KIND, Op.EMPTY_OPERATOR.toString()),
					PayloadElemList(null, Collections.emptyList()));  // Payload.EMPTY_PAYLOAD?
		}
		return UNIT_MESSAGE_SIG_NODE;
	}
	
	@Override
	public MessageSigNode MessageSigNode(CommonTree source, OpNode op, PayloadElemList payload)
	{
		MessageSigNode msn = new MessageSigNode(source, op, payload);
		msn = del(msn, createDefaultDelegate());
		return msn;
	}

	@Override
	//public PayloadElemList PayloadElemList(List<PayloadElem> payloadelems)
	public PayloadElemList PayloadElemList(CommonTree source, List<PayloadElem<?>> payloadelems)
	{
		PayloadElemList p = new PayloadElemList(source, payloadelems);
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
	//public UnaryPayloadElem DataTypeElem(PayloadElemNameNode<DataTypeKind> name)
	//public UnaryPayloadElem UnaryPayloadElem(PayloadElemNameNode<?> name)
	public <K extends PayloadTypeKind> UnaryPayloadElem<K> UnaryPayloadElem(CommonTree source, PayloadElemNameNode<K> name)
	{
		UnaryPayloadElem<K> de= new UnaryPayloadElem<>(source, name);
		de = del(de, createDefaultDelegate());
		return de;
	}

	@Override
	public GDelegationElem GDelegationElem(CommonTree source, GProtocolNameNode proto, RoleNode role)
	{
		GDelegationElem de = new GDelegationElem(source, proto, role);
		//de = del(de, createDefaultDelegate());
		de = del(de, new GDelegationElemDel());  // FIXME: GDelegationElemDel
		return de;
	}

	@Override
	public LDelegationElem LDelegationElem(CommonTree source, LProtocolNameNode proto)
	{
		LDelegationElem de = new LDelegationElem(source, proto);
		de = del(de, createDefaultDelegate());
		return de;
	}
	
	@Override
	public Module Module(CommonTree source, ModuleDecl moddecl, List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data, List<ProtocolDecl<?>> protos)
	{
		Module module = new Module(source, moddecl, imports, data, protos);
		module = del(module, new ModuleDel());
		return module;
	}

	@Override
	public ModuleDecl ModuleDecl(CommonTree source, ModuleNameNode fullmodname)
	{
		ModuleDecl md = new ModuleDecl(source, fullmodname);
		md = del(md, createDefaultDelegate());
		return md;
	}

	@Override
	public ImportModule ImportModule(CommonTree source, ModuleNameNode modname, ModuleNameNode alias)
	{
		ImportModule im = new ImportModule(source, modname, alias);
		im = del(im, new ImportModuleDel());
		return im;
	}
	
	@Override
	public MessageSigNameDecl MessageSigNameDecl(CommonTree source, String schema, String extName, String extSource, MessageSigNameNode alias)
	{
		MessageSigNameDecl msd = new MessageSigNameDecl(source, schema, extName, extSource, alias);
		msd = del(msd, createDefaultDelegate());
		return msd;
	}

	@Override
	public DataTypeDecl DataTypeDecl(CommonTree source, String schema, String extName, String extSource, DataTypeNode alias)
	{
		DataTypeDecl dtd = new DataTypeDecl(source, schema, extName, extSource, alias);
		dtd = del(dtd, createDefaultDelegate());
		return dtd;
	}

	@Override
	public GProtocolDecl GProtocolDecl(CommonTree source, List<GProtocolDecl.Modifiers> modifiers, GProtocolHeader header, GProtocolDef def)
	{
		GProtocolDecl gpd = new GProtocolDecl(source, modifiers, header, def);
		gpd = del(gpd, new GProtocolDeclDel());
		return gpd;
	}

	@Override
	public GProtocolHeader GProtocolHeader(CommonTree source, GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		GProtocolHeader gph = new GProtocolHeader(source, name, roledecls, paramdecls);
		gph = del(gph, createDefaultDelegate());
		return gph;
	}

	@Override
	public RoleDeclList RoleDeclList(CommonTree source, List<RoleDecl> rds)
	{
		RoleDeclList rdl = new RoleDeclList(source, rds);
		rdl = del(rdl, new RoleDeclListDel());
		return rdl;
	}

	@Override
	public RoleDecl RoleDecl(CommonTree source, RoleNode namenode)
	{
		RoleDecl rd = new RoleDecl(source, namenode);
		rd = del(rd, new RoleDeclDel());
		return rd;
	}

	/*@Override
	public ConnectDecl ConnectDecl(RoleNode src, RoleNode role)
	{
		ConnectDecl cd = new ConnectDecl(src, role);
		cd = del(cd, new ConnectDeclDel());
		return cd;
	}*/

	@Override
	public NonRoleParamDeclList NonRoleParamDeclList(CommonTree source, List<NonRoleParamDecl<NonRoleParamKind>> pds)
	{
		NonRoleParamDeclList pdl = new NonRoleParamDeclList(source, pds);
		pdl = del(pdl, new NonRoleParamDeclListDel());
		return pdl;
	}

	@Override
	public <K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(CommonTree source, K kind, NonRoleParamNode<K> namenode)
	{
		NonRoleParamDecl<K> pd = new NonRoleParamDecl<K>(source, kind, namenode);
		pd = del(pd, new NonRoleParamDeclDel());
		return pd;
	}

	@Override
	public GProtocolDef GProtocolDef(CommonTree source, GProtocolBlock block)
	{
		GProtocolDef gpd = new GProtocolDef(source, block);
		gpd = del(gpd, new GProtocolDefDel());
		return gpd;
	}

	@Override
	public GProtocolBlock GProtocolBlock(CommonTree source, GInteractionSeq seq)
	{
		GProtocolBlock gpb = new GProtocolBlock(source, seq);
		gpb = del(gpb, new GProtocolBlockDel());
		return gpb;
	}

	@Override
	public GInteractionSeq GInteractionSeq(CommonTree source, List<GInteractionNode> actions)
	{
		GInteractionSeq gis = new GInteractionSeq(source, actions);
		gis = del(gis, new GInteractionSeqDel());
		return gis;
	}

	@Override
	public GMessageTransfer GMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		GMessageTransfer gmt = new GMessageTransfer(source, src, msg, dests);
		gmt = del(gmt, new GMessageTransferDel());
		return gmt;
	}

	@Override
	public GConnect GConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public GConnect GConnect(RoleNode src, RoleNode dest)
	{
		GConnect gc = new GConnect(source, src, msg, dest);
		//GConnect gc = new GConnect(src, dest);
		gc = del(gc, new GConnectDel());
		return gc;
	}

	@Override
	public GDisconnect GDisconnect(CommonTree source, RoleNode src, RoleNode dest)
	{
		GDisconnect gc = new GDisconnect(source, UnitMessageSigNode(), src, dest);
		gc = del(gc, new GDisconnectDel());
		return gc;
	}

	@Override
	public GWrap GWrap(CommonTree source, RoleNode src, RoleNode dest)
	{
		GWrap gw = new GWrap(source, UnitMessageSigNode(), src, dest);
		gw = del(gw, new GWrapDel());
		return gw;
	}

	@Override
	public GChoice GChoice(CommonTree source, RoleNode subj, List<GProtocolBlock> blocks)
	{
		GChoice gc = new GChoice(source, subj, blocks);
		gc = del(gc, new GChoiceDel());
		return gc;
	}

	@Override
	public GRecursion GRecursion(CommonTree source, RecVarNode recvar, GProtocolBlock block)
	{
		GRecursion gr = new GRecursion(source, recvar, block);
		gr = del(gr, new GRecursionDel());
		return gr;
	}

	@Override
	public GContinue GContinue(CommonTree source, RecVarNode recvar)
	{
		GContinue gc = new GContinue(source, recvar);
		gc = del(gc, new GContinueDel());
		return gc;
	}

	@Override
	public GDo GDo(CommonTree source, RoleArgList roleinstans, NonRoleArgList arginstans, GProtocolNameNode proto)
	{
		GDo gd = new GDo(source, roleinstans, arginstans, proto);
		gd = del(gd, new GDoDel());
		return gd;
	}

	@Override
	public RoleArgList RoleArgList(CommonTree source, List<RoleArg> ris)
	{
		RoleArgList rdl = new RoleArgList(source, ris);
		rdl = del(rdl, new RoleArgListDel());
		return rdl;
	}

	@Override
	public RoleArg RoleArg(CommonTree source, RoleNode role)
	{
		RoleArg ri = new RoleArg(source, role);
		ri = del(ri, createDefaultDelegate());
		return ri;
	}

	@Override
	public NonRoleArgList NonRoleArgList(CommonTree source, List<NonRoleArg> ais)
	{
		NonRoleArgList rdl = new NonRoleArgList(source, ais);
		rdl = del(rdl, new NonRoleArgListDel());
		return rdl;
	}

	@Override
	public NonRoleArg NonRoleArg(CommonTree source, NonRoleArgNode arg)
	{
		NonRoleArg ri = new NonRoleArg(source, arg);
		ri = del(ri, createDefaultDelegate());
		return ri;
	}
	
	@Override
	public <K extends Kind> NameNode<K> SimpleNameNode(CommonTree source, K kind, String identifier)
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
	public <K extends Kind> QualifiedNameNode<K> QualifiedNameNode(CommonTree source, K kind, String... elems)
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
			qnn = new LProtocolNameNode(source,elems);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castNameNode(kind, del(qnn, createDefaultDelegate()));
	}
	
	protected static <T extends NameNode<K>, K extends Kind> T castNameNode(K kind, NameNode<? extends Kind> n)
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

	@Override
	public <K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(CommonTree source, K kind, String identifier)
	{
		NonRoleParamNode<K> pn = new NonRoleParamNode<K>(source, kind, identifier);
		pn = del(pn, new ParamNodeDel());
		return pn;
	}

	@Override
	public DummyProjectionRoleNode DummyProjectionRoleNode()
	{
		DummyProjectionRoleNode dprn = new DummyProjectionRoleNode();
		dprn = (DummyProjectionRoleNode) dprn.del(createDefaultDelegate());
		return dprn;
	}

	@Override
	public LProtocolDecl LProtocolDecl(CommonTree source, List<ProtocolDecl.Modifiers> modifiers, LProtocolHeader header, LProtocolDef def)
	{
		LProtocolDecl lpd = new LProtocolDecl(source, modifiers, header, def);
		lpd = del(lpd, new LProtocolDeclDel());
		return lpd;
	}

	@Override
	public LProtocolHeader LProtocolHeader(CommonTree source, LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		LProtocolHeader lph = new LProtocolHeader(source, name, roledecls, paramdecls);
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
	public LInteractionSeq LInteractionSeq(CommonTree source, List<LInteractionNode> actions)
	{
		LInteractionSeq lis = new LInteractionSeq(source, actions);
		lis = del(lis, new LInteractionSeqDel());
		return lis;
	}

	@Override
	public LSend LSend(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LSend ls = new LSend(source, src, msg, dests);
		ls = del(ls, new LSendDel());
		return ls;
	}

	@Override
	public LReceive LReceive(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LReceive ls = new LReceive(source, src, msg, dests);
		ls = del(ls, new LReceiveDel());
		return ls;
	}
	
	@Override
	public LConnect LConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect LConnect(RoleNode src, RoleNode dest)
	{
		LConnect lc = new LConnect(source, src, msg, dest);
		//LConnect lc = new LConnect(src, dest);
		lc = del(lc, new LConnectDel());
		return lc;
	}

	@Override
	public LAccept LAccept(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public LAccept LAccept(RoleNode src, RoleNode dest)
	{
		LAccept la = new LAccept(source, src, msg, dest);
		//LAccept la = new LAccept(src, dest);
		la = del(la, new LAcceptDel());
		return la;
	}

	@Override
	public LDisconnect LDisconnect(CommonTree source, RoleNode self, RoleNode peer)
	{
		LDisconnect lc = new LDisconnect(source, UnitMessageSigNode(), self, peer);
		lc = del(lc, new LDisconnectDel());
		return lc;
	}

	@Override
	public LWrapClient LWrapClient(CommonTree source, RoleNode self, RoleNode peer)
	{
		LWrapClient lwc = new LWrapClient(source, UnitMessageSigNode(), self, peer);
		lwc = del(lwc, new LWrapClientDel());
		return lwc;
	}

	@Override
	public LWrapServer LWrapServer(CommonTree source, RoleNode self, RoleNode peer)
	{
		LWrapServer lws = new LWrapServer(source, UnitMessageSigNode(), self, peer);
		lws = del(lws, new LWrapServerDel());
		return lws;
	}

	@Override
	public LChoice LChoice(CommonTree source, RoleNode subj, List<LProtocolBlock> blocks)
	{
		LChoice lc = new LChoice(source, subj, blocks);
		lc = del(lc, new LChoiceDel());
		return lc;
	}

	@Override
	public LRecursion LRecursion(CommonTree source, RecVarNode recvar, LProtocolBlock block)
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
	public LDo LDo(CommonTree source, RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		LDo ld = new LDo(source, roleinstans, arginstans, proto);
		ld = del(ld, new LDoDel());
		return ld;
	}

	protected ScribDel createDefaultDelegate()
	{
		return new DefaultDel();
	}
	
	// FIXME: factor out
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

	@Override
	public LProtocolDecl LProjectionDecl(CommonTree source, List<ProtocolDecl.Modifiers> modifiers, GProtocolName fullname, Role self, LProtocolHeader header, LProtocolDef def)  // del extends that of LProtocolDecl 
	{
		LProtocolDecl lpd = new LProtocolDecl(source, modifiers, header, def);
		lpd = ScribNodeBase.del(lpd, new LProjectionDeclDel(fullname, self));
		return lpd;
	}
}

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
package org.scribble.parser.scribble;

import java.util.List;

import org.antlr.runtime.CommonToken;
import org.scribble.ast.AstFactory;
import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolModList;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.UnaryPayloadElem;
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
import org.scribble.core.type.kind.ModuleKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.OpKind;
import org.scribble.core.type.kind.PayloadTypeKind;
import org.scribble.core.type.kind.RecVarKind;
import org.scribble.core.type.kind.RoleKind;
import org.scribble.core.type.kind.SigKind;
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
import org.scribble.parser.antlr.ScribbleParser;



public class AstFactoryImpl implements AstFactory
{
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
	
	@Override
	public IdNode IdNode(String id)
	{
		CommonToken t = new CommonToken(ScribbleParser.IDENTIFIER, id);  
				// IdNode is the only token with a "different" text to its node type -- info stored directly as its the text, no children
		IdNode n = new IdNode(t);
		return n;
	}
	
	// type comes from the int constants in ScribbleParser, which come from the tokens in Scribble.g
	// Pre: any type from ScribbleParser, except IDENTIFIER (IdNode)
	private CommonToken newToken(int type)
	{
		String text = null;  // CHECKEME: use reflection?
		return new CommonToken(type, text);
	}
	
	private CommonToken newToken(int type, String text)
	{
		return new CommonToken(type, text);
	}

	@Override
	public MessageSigNode MessageSigNode(OpNode op, PayloadElemList pay)
	{
		CommonToken t = newToken(ScribbleParser.MESSAGESIGNATURE, "MESSAGESIGNATURE");  
		MessageSigNode msn = new MessageSigNode(t);
		msn.addChild(op);
		msn.addChild(pay);
		msn = del(msn, createDefaultDelegate());
		return msn;
	}

	@Override
	public PayloadElemList PayloadElemList(List<PayloadElem<?>> elems)
	{
		CommonToken t = newToken(ScribbleParser.PAYLOAD, "PAYLOAD");  
		PayloadElemList p = new PayloadElemList(t);
		p.addChildren(elems);
		p = del(p, createDefaultDelegate());
		return p;
	}

	// Not used by ScribbleParser -- parsed "directly" within Scribble.g
	@Override
	public <K extends PayloadTypeKind> UnaryPayloadElem<K> UnaryPayloadElem(
			PayloadElemNameNode<K> name)
	{
		CommonToken t = newToken(ScribbleParser.UNARYPAYLOADELEM, "UNARYPAYLOADELEM");  
		UnaryPayloadElem<K> de = new UnaryPayloadElem<>(t);
		// Cf. Scribble.g children order
		de.addChild(name);
		de = del(de, createDefaultDelegate());
		return de;
	}

	@Override
	public GDelegationElem GDelegationElem(GProtocolNameNode proto, RoleNode role)
	{
		CommonToken t = newToken(ScribbleParser.DELEGATION, "DELEGATION");  
		GDelegationElem de = new GDelegationElem(t);
		de.addChild(proto);
		de.addChild(role);
		//de = del(de, createDefaultDelegate());
		de = del(de, new GDelegationElemDel());  // FIXME: GDelegationElemDel
		return de;
	}
	
	@Override
	public Module Module(ModuleDecl moddecl,
			List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data,
			List<ProtocolDecl<?>> protos)
	{
		CommonToken t = newToken(ScribbleParser.MODULE, "MODULE");  
		Module n = new Module(t);
		n.addChild(moddecl);
		n.addChildren(imports);
		n.addChildren(data);
		n.addChildren(protos);
		n = del(n, new ModuleDel());
		return n;
	}

	@Override
	public ModuleDecl ModuleDecl(ModuleNameNode fullmodname)
	{
		CommonToken t = newToken(ScribbleParser.MODULEDECL, "MODULEDECL");  
		ModuleDecl n = new ModuleDecl(t);
		n.addChild(fullmodname);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public ImportModule ImportModule(ModuleNameNode modname,
			ModuleNameNode alias)
	{
		CommonToken t = newToken(ScribbleParser.IMPORTMODULE, "IMPORTMODULE");  
		ImportModule im = new ImportModule(t);
		im.addChild(modname);
		if (alias != null)
		{
			im.addChild(alias);
		}
		im = del(im, new ImportModuleDel());
		return im;
	}

	@Override
	public MessageSigNameDecl MessageSigNameDecl(IdNode schema,
			IdNode extName, IdNode extSource, MessageSigNameNode alias)
	{
		CommonToken t = newToken(ScribbleParser.MESSAGESIGNATUREDECL,
				"MESSAGESIGNATUREDECL");
		MessageSigNameDecl n = new MessageSigNameDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public DataTypeDecl DataTypeDecl(IdNode schema,
			IdNode extName, IdNode extSource, DataTypeNode alias)
	{
		CommonToken t = newToken(ScribbleParser.PAYLOADTYPEDECL,
				"PAYLOADTYPEDECL");
		DataTypeDecl n = new DataTypeDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public GProtocolDecl GProtocolDecl(ProtocolModList mods,
			GProtocolHeader header, GProtocolDef def)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALPROTOCOLDECL,
				"GLOBALPROTOCOLDECL");
		GProtocolDecl gpd = new GProtocolDecl(t);
		gpd.addChild(mods);
		gpd.addChild(header);
		gpd.addChild(def);
		gpd = del(gpd, new GProtocolDeclDel());
		return gpd;
	}

	@Override
	public GProtocolHeader GProtocolHeader(GProtocolNameNode name,
			RoleDeclList rs, NonRoleParamDeclList ps)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALPROTOCOLHEADER,
				"GLOBALPROTOCOLHEADER");
		GProtocolHeader n = new GProtocolHeader(t);
		n.addChild(name);
		n.addChild(ps);
		n.addChild(rs);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public RoleDeclList RoleDeclList(List<RoleDecl> ds)
	{
		CommonToken t = newToken(ScribbleParser.ROLEDECLLIST,
				"ROLEDECLLIST");
		RoleDeclList rdl = new RoleDeclList(t);
		// Cf. Scribble.g children order
		rdl.addChildren(ds);
		rdl = del(rdl, new RoleDeclListDel());
		return rdl;
	}

	@Override
	public RoleDecl RoleDecl(RoleNode r)
	{
		CommonToken t = newToken(ScribbleParser.ROLEDECL,
				"ROLEDECL");
		RoleDecl n = new RoleDecl(t);
		n.addChild(r);
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
	public NonRoleParamDeclList NonRoleParamDeclList(
			List<NonRoleParamDecl<NonRoleParamKind>> ds)
	{
		CommonToken t = newToken(ScribbleParser.ROLEDECL,
				"ROLEDECL");
		NonRoleParamDeclList pdl = new NonRoleParamDeclList(t);
		// Cf. Scribble.g children order
		pdl.addChildren(ds);
		pdl = del(pdl, new NonRoleParamDeclListDel());
		return pdl;
	}

	@Deprecated
	@Override
	public <K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(
			K kind, NonRoleParamNode<K> namenode)
	{
		/*NonRoleParamDecl<K> pd = new NonRoleParamDecl<K>(source, kind, namenode);
		pd = del(pd, new NonRoleParamDeclDel());
		return pd;*/
		throw new RuntimeException("Deprecated");
	}

	@Override
	public GProtocolDef GProtocolDef(GProtocolBlock block)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALPROTOCOLDEF,
				"GLOBALPROTOCOLDEF");
		GProtocolDef n = new GProtocolDef(t);
		n.addChild(block);
		n = del(n, new GProtocolDefDel());
		return n;
	}

	@Override
	public GProtocolBlock GProtocolBlock(GInteractionSeq seq)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALPROTOCOLBLOCK,
				"GLOBALPROTOCOLBLOCK");
		GProtocolBlock n = new GProtocolBlock(t);
		n.addChild(seq);
		n = del(n, new GProtocolBlockDel());
		return n;
	}

	@Override
	public GInteractionSeq GInteractionSeq(List<GSessionNode> elems)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALINTERACTIONSEQUENCE,
				"GLOBALINTERACTIONSEQUENCE");
		GInteractionSeq n = new GInteractionSeq(t);
		n.addChildren(elems);
		n = del(n, new GInteractionSeqDel());
		return n;
	}

	@Override
	public GMessageTransfer GMessageTransfer(RoleNode src,
			MessageNode msg, List<RoleNode> dsts)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALMESSAGETRANSFER,
				"GLOBALMESSAGETRANSFER");
		GMessageTransfer n = new GMessageTransfer(t);
		n.addChild(msg);
		n.addChild(src);
		n.addChildren(dsts);
		n = del(n, new GMessageTransferDel());
		return n;
	}

	@Override
	public GConnect GConnect(RoleNode src, MessageNode msg, RoleNode dst)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALCONNECT,
				"GLOBALCONNECT");
		GConnect n = new GConnect(t);
		n.addChild(msg);
		n.addChild(src);
		n.addChild(dst);
		n = del(n, new GConnectDel());
		return n;
	}

	@Override
	public GDisconnect GDisconnect(RoleNode left, RoleNode right)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALDISCONNECT,
				"GLOBALDISCONNECT");
		GDisconnect n = new GDisconnect(t);
		n.addChild(left);
		n.addChild(right);
		n = del(n, new GDisconnectDel());
		return n;
	}

	@Override
	public GWrap GWrap(RoleNode src, RoleNode dst)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALWRAP,
				"GLOBALWRAP");
		GWrap n = new GWrap(t);
		n.addChild(src);
		n.addChild(dst);
		n = del(n, new GWrapDel());
		return n;
	}

	@Override
	public GChoice GChoice(RoleNode subj, List<GProtocolBlock> blocks)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALCHOICE,
				"GLOBALCHOICE");
		GChoice n = new GChoice(t);
		n.addChild(subj);
		n.addChildren(blocks);
		n = del(n, new GChoiceDel());
		return n;
	}

	@Override
	public GRecursion GRecursion(RecVarNode rv,
			GProtocolBlock block)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALRECURSION,
				"GLOBALRECURSION");
		GRecursion n = new GRecursion(t);
		n.addChild(rv);
		n.addChild(block);
		n = del(n, new GRecursionDel());
		return n;
	}

	@Override
	public GContinue GContinue(RecVarNode rv)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALCONTINUE,
				"GLOBALCONTINUE");
		GContinue n = new GContinue(t);
		n.addChild(rv);
		n = del(n, new GContinueDel());
		return n;
	}

	@Override
	public GDo GDo(RoleArgList rs, NonRoleArgList as, GProtocolNameNode proto)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALDO,
				"GLOBALDO");
		GDo n = new GDo(t);
		n.addChild(proto);
		n.addChild(rs);
		n.addChild(as);
		n = del(n, new GDoDel());
		return n;
	}

	@Override
	public RoleArgList RoleArgList(List<RoleArg> rs)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALDO,
				"GLOBALDO");
		RoleArgList n = new RoleArgList(t);
		n.addChildren(rs);
		n = del(n, new RoleArgListDel());
		return n;
	}

	@Override
	public RoleArg RoleArg(RoleNode r)
	{
		CommonToken t = newToken(ScribbleParser.GLOBALDO,
				"GLOBALDO");
		RoleArg n = new RoleArg(t);
		n.addChild(r);
		n = del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public NonRoleArgList NonRoleArgList(List<NonRoleArg> as)
	{
		CommonToken t = newToken(ScribbleParser.ARGUMENTINSTANTIATIONLIST,
				"ARGUMENTINSTANTIATIONLIST");
		NonRoleArgList n = new NonRoleArgList(t);
		n.addChildren(as);
		n = del(n, new NonRoleArgListDel());
		return n;
	}

	@Override
	public NonRoleArg NonRoleArg(NonRoleArgNode arg)
	{
		CommonToken t = newToken(ScribbleParser.ARGUMENTINSTANTIATIONLIST,
				"ARGUMENTINSTANTIATIONLIST");
		NonRoleArg n = new NonRoleArg(t);
		n.addChild(arg);
		n = del(n, createDefaultDelegate());
		return n;
	}

	// FIXME: split up this method
	// CHECKME: organise explicit Scribble Tokens
	@Override
	public <K extends Kind> NameNode<K> SimpleNameNode(K kind,
			IdNode id)
	{
		NameNode<? extends Kind> snn = null;

		// "Custom" del's
		if (kind.equals(RecVarKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.RECURSIONVAR, "RECURSIONVAR");
			snn = new RecVarNode(t);
			snn = del(snn, new RecVarNodeDel());
		}
		else if (kind.equals(RoleKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.ROLENAME, "ROLENAME");
			snn = new RoleNode(t);
			snn = del(snn, new RoleNodeDel());
		}
		if (snn != null)
		{
			snn.addChild(id);
			return castNameNode(kind, snn);
		}

		// Default del's
		if (kind.equals(OpKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.OPNAME, "OPNAME");
			snn = new OpNode(t);
			snn.addChild(id);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castNameNode(kind, del(snn, createDefaultDelegate()));
	}

	@Override
	public <K extends Kind> QualifiedNameNode<K> QualifiedNameNode(
			K kind, List<IdNode> elems)
	{
		QualifiedNameNode<? extends Kind> qnn = null;
		if (kind.equals(SigKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.SIGNAME, "SIGNAME");
			qnn = new MessageSigNameNode(t);
			qnn = del(qnn, new MessageSigNameNodeDel());
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.TYPENAME, "TYPENAME");
			qnn = new DataTypeNode(t);
			qnn = del(qnn, new DataTypeNodeDel());
		}
		if (qnn != null)
		{
			qnn.addChildren(elems);
			return castNameNode(kind, qnn);
		}

		if (kind.equals(ModuleKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.MODULENAME, "MODULENAME");
			qnn = new ModuleNameNode(t);
		}
		else if (kind.equals(Global.KIND))
		{
			CommonToken t = newToken(ScribbleParser.GPROTOCOLNAME, "GPROTOCOLNAME");
			qnn = new GProtocolNameNode(t);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		qnn.addChildren(elems);
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

	// Deprecate?  Never need to make ambigname "manually" via af?  (only constructed by ScribbleParser)
	@Override
	public AmbigNameNode AmbiguousNameNode(IdNode id)
	{
		CommonToken t = newToken(ScribbleParser.AMBIGUOUSNAME, "AMBIGUOUSNAME");
		AmbigNameNode n = new AmbigNameNode(t);
		n.addChild(id);
		n = (AmbigNameNode) n.del(new AmbigNameNodeDel());
		return n;
	}

	@SuppressWarnings("unchecked")  // FIXME
	@Override
	public <K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(
			K kind, String identifier)
	{
		NonRoleParamNode<K> pn;
		if (kind.equals(SigKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.SIGPARAMNAME, "SIGPARAMNAME");  // N.B. cf. ScribbleParser.SIGNAME
			pn = (NonRoleParamNode<K>) new SigParamNode(t);
		}
		else
		{
			CommonToken t = newToken(ScribbleParser.TYPEPARAMNAME, "TYPEPARAMNAME");  // N.B. cf. ScribbleParser.TYPENAME
			pn = (NonRoleParamNode<K>) new TypeParamNode(t);
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

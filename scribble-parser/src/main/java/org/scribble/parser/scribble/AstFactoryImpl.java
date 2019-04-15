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
import org.scribble.core.type.kind.PayloadTypeKind;
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
	public AstFactoryImpl()
	{
		
	}

	protected ScribDel createDefaultDelegate()
	{
		return new DefaultDel();
	}
	
	// FIXME: factor out -- change return to void (ScribNodeBase.del is a mutating setter)
//	protected static <T extends ScribNodeBase> T del(T n, ScribDel del)
	protected static <T extends ScribNodeBase> T del(T n, ScribDel del)
	{
		return ScribNodeBase.del(n, del);
	}
	
	@Override
	public IdNode IdNode(String text)
	{
		CommonToken t = new CommonToken(ScribbleParser.IDENTIFIER, text);  
				// IdNode is the only token with a "different" text to its node type -- info stored directly as its the text, no children
		IdNode n = new IdNode(t);
		return n;
	}
	
	// Deprecate?  Never need to make ambigname "manually" via af?  (only constructed by ScribbleParser)
	@Override
	public AmbigNameNode AmbiguousNameNode(String text)
	{
		int ttype = ScribbleParser.AMBIG_NAME;
		CommonToken t = newToken(ttype, text);
		AmbigNameNode n = new AmbigNameNode(ttype, t);  // Cf. Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
		n.del(new AmbigNameNodeDel());
		return n;
	}

	@Override
	public OpNode OpNode(String text)
	{
		int ttype = ScribbleParser.IDENTIFIER;
		CommonToken t = newToken(ttype, "OPNAME");
		OpNode n = new OpNode(ttype, t);  // Cf. Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public RecVarNode RecVarNode(String text)
	{
		int ttype = ScribbleParser.IDENTIFIER;
		CommonToken t = newToken(ttype, "RECURSIONVAR");
		RecVarNode n = new RecVarNode(ttype, t);  // Cf. Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
		del(n, new RecVarNodeDel());
		return n;
	}

	@Override
	public RoleNode RoleNode(String text)
	{
		int ttype = ScribbleParser.IDENTIFIER;
		CommonToken t = newToken(ttype, "ROLENAME");
		RoleNode n = new RoleNode(ttype, t);  // Cf. Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
		del(n, new RoleNodeDel());
		return n;
	}

	@Override
	public SigParamNode SigParamNode(String text)
	{
		int ttype = ScribbleParser.IDENTIFIER;  // N.B. cf. ScribbleParser.SIGNAME
		CommonToken t = newToken(ttype, text);
		SigParamNode n = new SigParamNode(ttype, t);  // Cf. Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
		n.del(new NonRoleParamNodeDel());
		return n;
	}

	@Override
	public TypeParamNode TypeParamNode(String text)
	{
		int ttype = ScribbleParser.IDENTIFIER;  // N.B. cf. ScribbleParser.TYPENAME
		CommonToken t = newToken(ttype, text);
		TypeParamNode n = new TypeParamNode(ttype, t);  // Cf. Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
		n.del(new NonRoleParamNodeDel());
		return n;
	}

	@Override
	public <K extends Kind> QualifiedNameNode<K> QualifiedNameNode(
			K kind, List<IdNode> elems)
	{
		QualifiedNameNode<? extends Kind> n = null;
		if (kind.equals(SigKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.SIG_NAME, "SIG_NAME");
			n = new MessageSigNameNode(t);
			del(n, new MessageSigNameNodeDel());
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.TYPE_NAME, "TYPE_NAME");
			n = new DataTypeNode(t);
			del(n, new DataTypeNodeDel());
		}
		if (n != null)
		{
			n.addChildren(elems);
			return castNameNode(kind, n);
		}

		if (kind.equals(ModuleKind.KIND))
		{
			CommonToken t = newToken(ScribbleParser.MODULE_NAME, "MODULE_NAME");
			n = new ModuleNameNode(t);
		}
		else if (kind.equals(Global.KIND))
		{
			CommonToken t = newToken(ScribbleParser.GPROTO_NAME, "GPROTO_NAME");
			n = new GProtocolNameNode(t);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		n.addChildren(elems);
		return castNameNode(kind, del(n, createDefaultDelegate()));
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
	
	// type comes from the int constants in ScribbleParser, which come from the tokens in Scribble.g
	// Pre: any type from ScribbleParser, except IDENTIFIER (IdNode)
	private CommonToken newToken(int type)
	{
		String text = null;  // CHECKEME: use reflection to get token int field name?
		return new CommonToken(type, text);
	}
	
	private CommonToken newToken(int type, String text)
	{
		return new CommonToken(type, text);
	}

	@Override
	public MessageSigNode MessageSigNode(OpNode op, PayloadElemList pay)
	{
		CommonToken t = newToken(ScribbleParser.SIG_LIT, "SIG_LIT");  
		MessageSigNode n = new MessageSigNode(t);
		n.addChild(op);
		n.addChild(pay);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public PayloadElemList PayloadElemList(List<PayloadElem<?>> elems)
	{
		CommonToken t = newToken(ScribbleParser.PAYELEM_LIST, "PAYELEM_LIST");  
		PayloadElemList n = new PayloadElemList(t);
		n.addChildren(elems);
		del(n, createDefaultDelegate());
		return n;
	}

	// Not used by ScribbleParser -- parsed "directly" within Scribble.g
	@Override
	public <K extends PayloadTypeKind> UnaryPayloadElem<K> UnaryPayloadElem(
			PayloadElemNameNode<K> name)
	{
		CommonToken t = newToken(ScribbleParser.UNARY_PAYELEM, "UNARY_PAYELEM");  
		UnaryPayloadElem<K> n = new UnaryPayloadElem<>(t);
		// Cf. Scribble.g children order
		n.addChild(name);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public GDelegationElem GDelegationElem(GProtocolNameNode proto, RoleNode role)
	{
		CommonToken t = newToken(ScribbleParser.DELEG_PAYELEM, "DELEG_PAYELEM");  
		GDelegationElem n = new GDelegationElem(t);
		n.addChild(proto);
		n.addChild(role);
		//del(n, createDefaultDelegate());
		del(n, new GDelegationElemDel());  // FIXME: GDelegationElemDel
		return n;
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
		del(n, new ModuleDel());
		return n;
	}

	@Override
	public ModuleDecl ModuleDecl(ModuleNameNode fullmodname)
	{
		CommonToken t = newToken(ScribbleParser.MODULEDECL, "MODULEDECL");  
		ModuleDecl n = new ModuleDecl(t);
		n.addChild(fullmodname);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public ImportModule ImportModule(ModuleNameNode modname,
			ModuleNameNode alias)
	{
		CommonToken t = newToken(ScribbleParser.IMPORTMODULE, "IMPORTMODULE");  
		ImportModule n = new ImportModule(t);
		n.addChild(modname);
		if (alias != null)
		{
			n.addChild(alias);
		}
		del(n, new ImportModuleDel());
		return n;
	}

	@Override
	public MessageSigNameDecl MessageSigNameDecl(IdNode schema,
			IdNode extName, IdNode extSource, MessageSigNameNode alias)
	{
		CommonToken t = newToken(ScribbleParser.SIGDECL,
				"SIGDECL");
		MessageSigNameDecl n = new MessageSigNameDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public DataTypeDecl DataTypeDecl(IdNode schema,
			IdNode extName, IdNode extSource, DataTypeNode alias)
	{
		CommonToken t = newToken(ScribbleParser.DATADECL,
				"DATADECL");
		DataTypeDecl n = new DataTypeDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public GProtocolDecl GProtocolDecl(ProtocolModList mods,
			GProtocolHeader header, GProtocolDef def)
	{
		CommonToken t = newToken(ScribbleParser.GPROTODECL,
				"GPROTODECL");
		GProtocolDecl n = new GProtocolDecl(t);
		n.addChild(mods);
		n.addChild(header);
		n.addChild(def);
		del(n, new GProtocolDeclDel());
		return n;
	}

	@Override
	public GProtocolHeader GProtocolHeader(GProtocolNameNode name,
			RoleDeclList rs, NonRoleParamDeclList ps)
	{
		CommonToken t = newToken(ScribbleParser.GPROTOHEADER,
				"GPROTOHEADER");
		GProtocolHeader n = new GProtocolHeader(t);
		n.addChild(name);
		n.addChild(ps);
		n.addChild(rs);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public RoleDeclList RoleDeclList(List<RoleDecl> ds)
	{
		CommonToken t = newToken(ScribbleParser.ROLEDECL_LIST,
				"ROLEDECL_LIST");
		RoleDeclList n = new RoleDeclList(t);
		// Cf. Scribble.g children order
		n.addChildren(ds);
		del(n, new RoleDeclListDel());
		return n;
	}

	@Override
	public RoleDecl RoleDecl(RoleNode r)
	{
		CommonToken t = newToken(ScribbleParser.ROLEDECL,
				"ROLEDECL");
		RoleDecl n = new RoleDecl(t);
		n.addChild(r);
		del(n, new RoleDeclDel());
		return n;
	}

	@Override
	public NonRoleParamDeclList NonRoleParamDeclList(
			List<NonRoleParamDecl<NonRoleParamKind>> ds)
	{
		CommonToken t = newToken(ScribbleParser.PARAMDECL_LIST,
				"PARAMDECL_LIST");
		NonRoleParamDeclList n = new NonRoleParamDeclList(t);
		// Cf. Scribble.g children order
		n.addChildren(ds);
		del(n, new NonRoleParamDeclListDel());
		return n;
	}

	@Deprecated
	@Override
	public <K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(
			K kind, NonRoleParamNode<K> namenode)
	{
		/*NonRoleParamDecl<K> pd = new NonRoleParamDecl<K>(source, kind, namenode);
		del(pd, new NonRoleParamDeclDel());
		return pd;*/
		throw new RuntimeException("Deprecated");
	}

	@Override
	public GProtocolDef GProtocolDef(GProtocolBlock block)
	{
		CommonToken t = newToken(ScribbleParser.GPROTODEF,
				"GPROTODEF");
		GProtocolDef n = new GProtocolDef(t);
		n.addChild(block);
		del(n, new GProtocolDefDel());
		return n;
	}

	@Override
	public GProtocolBlock GProtocolBlock(GInteractionSeq seq)
	{
		CommonToken t = newToken(ScribbleParser.GPROTOBLOCK,
				"GPROTOBLOCK");
		GProtocolBlock n = new GProtocolBlock(t);
		n.addChild(seq);
		del(n, new GProtocolBlockDel());
		return n;
	}

	@Override
	public GInteractionSeq GInteractionSeq(List<GSessionNode> elems)
	{
		CommonToken t = newToken(ScribbleParser.GACTIONSEQ,
				"GACTIONSEQ");
		GInteractionSeq n = new GInteractionSeq(t);
		n.addChildren(elems);
		del(n, new GInteractionSeqDel());
		return n;
	}

	@Override
	public GMessageTransfer GMessageTransfer(RoleNode src,
			MessageNode msg, List<RoleNode> dsts)
	{
		CommonToken t = newToken(ScribbleParser.GMSGTRANSFER,
				"GMSGTRANSFER");
		GMessageTransfer n = new GMessageTransfer(t);
		n.addChild(msg);
		n.addChild(src);
		n.addChildren(dsts);
		del(n, new GMessageTransferDel());
		return n;
	}

	@Override
	public GConnect GConnect(RoleNode src, MessageNode msg, RoleNode dst)
	{
		CommonToken t = newToken(ScribbleParser.GCONNECT,
				"GCONNECT");
		GConnect n = new GConnect(t);
		n.addChild(msg);
		n.addChild(src);
		n.addChild(dst);
		del(n, new GConnectDel());
		return n;
	}

	@Override
	public GDisconnect GDisconnect(RoleNode left, RoleNode right)
	{
		CommonToken t = newToken(ScribbleParser.GDCONN,
				"GDCONN");
		GDisconnect n = new GDisconnect(t);
		n.addChild(left);
		n.addChild(right);
		del(n, new GDisconnectDel());
		return n;
	}

	@Override
	public GWrap GWrap(RoleNode src, RoleNode dst)
	{
		CommonToken t = newToken(ScribbleParser.GWRAP,
				"GWRAP");
		GWrap n = new GWrap(t);
		n.addChild(src);
		n.addChild(dst);
		del(n, new GWrapDel());
		return n;
	}

	@Override
	public GChoice GChoice(RoleNode subj, List<GProtocolBlock> blocks)
	{
		CommonToken t = newToken(ScribbleParser.GCHOICE,
				"GCHOICE");
		GChoice n = new GChoice(t);
		n.addChild(subj);
		n.addChildren(blocks);
		del(n, new GChoiceDel());
		return n;
	}

	@Override
	public GRecursion GRecursion(RecVarNode rv,
			GProtocolBlock block)
	{
		CommonToken t = newToken(ScribbleParser.GRECURSION,
				"GRECURSION");
		GRecursion n = new GRecursion(t);
		n.addChild(rv);
		n.addChild(block);
		del(n, new GRecursionDel());
		return n;
	}

	@Override
	public GContinue GContinue(RecVarNode rv)
	{
		CommonToken t = newToken(ScribbleParser.GCONTINUE,
				"GCONTINUE");
		GContinue n = new GContinue(t);
		n.addChild(rv);
		del(n, new GContinueDel());
		return n;
	}

	@Override
	public GDo GDo(RoleArgList rs, NonRoleArgList as, GProtocolNameNode proto)
	{
		CommonToken t = newToken(ScribbleParser.GDO,
				"GDO");
		GDo n = new GDo(t);
		n.addChild(proto);
		n.addChild(rs);
		n.addChild(as);
		del(n, new GDoDel());
		return n;
	}

	@Override
	public RoleArgList RoleArgList(List<RoleArg> rs)
	{
		CommonToken t = newToken(ScribbleParser.ROLEARG_LIST,
				"ROLEARG_LIST");
		RoleArgList n = new RoleArgList(t);
		n.addChildren(rs);
		del(n, new RoleArgListDel());
		return n;
	}

	@Override
	public RoleArg RoleArg(RoleNode r)
	{
		CommonToken t = newToken(ScribbleParser.ROLEARG,
				"ROLEARG");
		RoleArg n = new RoleArg(t);
		n.addChild(r);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public NonRoleArgList NonRoleArgList(List<NonRoleArg> as)
	{
		CommonToken t = newToken(ScribbleParser.ARG_LIST,
				"ARG_LIST");
		NonRoleArgList n = new NonRoleArgList(t);
		n.addChildren(as);
		del(n, new NonRoleArgListDel());
		return n;
	}

	@Override
	public NonRoleArg NonRoleArg(NonRoleArgNode arg)
	{
		CommonToken t = newToken(ScribbleParser.ARG,
				"ARG");
		NonRoleArg n = new NonRoleArg(t);
		n.addChild(arg);
		del(n, createDefaultDelegate());
		return n;
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

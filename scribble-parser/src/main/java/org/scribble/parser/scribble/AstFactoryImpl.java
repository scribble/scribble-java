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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.CommonToken;
import org.scribble.ast.AstFactory;
import org.scribble.ast.DataDecl;
import org.scribble.ast.DataParamDecl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.MsgNode;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtoDecl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayElem;
import org.scribble.ast.PayElemList;
import org.scribble.ast.ProtoDecl;
import org.scribble.ast.ProtoModList;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.SigDecl;
import org.scribble.ast.SigLitNode;
import org.scribble.ast.SigParamDecl;
import org.scribble.ast.UnaryPayElem;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDelegPayElem;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMsgTransfer;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.ast.global.GProtoHeader;
import org.scribble.ast.global.GProtoBlock;
import org.scribble.ast.global.GProtoDef;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.global.GSessionNode;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.name.PayElemNameNode;
import org.scribble.ast.name.qualified.DataNameNode;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.qualified.SigNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.DataParamNode;
import org.scribble.ast.name.simple.ExtIdNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.PayloadTypeKind;
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
import org.scribble.del.name.qualified.DataTypeNodeDel;
import org.scribble.del.name.qualified.MessageSigNameNodeDel;
import org.scribble.del.name.simple.AmbigNameNodeDel;
import org.scribble.del.name.simple.NonRoleParamNodeDel;
import org.scribble.del.name.simple.RecVarNodeDel;
import org.scribble.del.name.simple.RoleNodeDel;
import org.scribble.parser.antlr.ScribbleParser;



public class AstFactoryImpl implements AstFactory
{
	// Purely for the convenience of newToken(type)
	protected final ScribbleParser parser;
	protected final Map<Integer, String> tokens;
	
	public AstFactoryImpl(ScribAntlrWrapper antlr)
	{
		try
		{
			Class<ScribbleParser> parserC = 
					org.scribble.parser.antlr.ScribbleParser.class;
			this.parser = antlr.newScribbleParser(null);
			Map<Integer, String> tokens = new HashMap<>();
			for (String t : ScribbleParser.tokenNames)
			{
				char c = t.charAt(0);
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
				{
					tokens.put(parserC.getField(t).getInt(this.parser), t);
				}
			}
			this.tokens = Collections.unmodifiableMap(tokens);
		}
		catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	// type comes from the int constants in ScribbleParser, which come from the tokens in Scribble.g
	// Pre: type is an "imaginary token" type from ScribbleParser -- (not ID)
	protected CommonToken newToken(int type)
	{
		// As a default, token text is set to the textual name of the token type int field (also the Scribble.g default)
		String text = this.tokens.get(type);
		return new CommonToken(type, text);
	}

	protected CommonToken newIdToken(String text)
	{
		return new CommonToken(ScribbleParser.ID, text);
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
		CommonToken t = newIdToken(text);  
				// (Ext)IdNode is the only token with a "different" text to its node type -- info stored directly as its the text, no children
		IdNode n = new IdNode(t);
		return n;
	}

	@Override
	public ExtIdNode ExtIdNode(String text)
	{
		CommonToken t = newIdToken(text);  
				// (Ext)IdNode is the only token with a "different" text to its node type -- info stored directly as its the text, no children
		ExtIdNode n = new ExtIdNode(t);
		return n;
	}
	
	// Deprecate?  Never need to make ambigname "manually" via af?  (only constructed by ScribbleParser)
	@Override
	public AmbigNameNode AmbigNameNode(String text)
	{
		int ttype = ScribbleParser.ID;
		CommonToken t = newIdToken(text);
		AmbigNameNode n = new AmbigNameNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.del(new AmbigNameNodeDel());
		return n;
	}

	@Override
	public OpNode OpNode(String text)
	{
		int ttype = ScribbleParser.ID;
		CommonToken t = newIdToken(text);
		OpNode n = new OpNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public RecVarNode RecVarNode(String text)
	{
		int ttype = ScribbleParser.ID;
		CommonToken t = newIdToken(text);
		RecVarNode n = new RecVarNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		del(n, new RecVarNodeDel());
		return n;
	}

	@Override
	public RoleNode RoleNode(String text)
	{
		int ttype = ScribbleParser.ID;
		CommonToken t = newIdToken(text);
		RoleNode n = new RoleNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		del(n, new RoleNodeDel());
		return n;
	}

	@Override
	public SigParamNode SigParamNode(String text)
	{
		int ttype = ScribbleParser.ID;  // N.B. cf. ScribbleParser.SIGNAME
		CommonToken t = newIdToken(text);
		SigParamNode n = new SigParamNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.del(new NonRoleParamNodeDel());
		return n;
	}

	@Override
	public DataParamNode DataParamNode(String text)
	{
		int ttype = ScribbleParser.ID;  // N.B. cf. ScribbleParser.TYPENAME
		CommonToken t = newIdToken(text);
		DataParamNode n = new DataParamNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.del(new NonRoleParamNodeDel());
		return n;
	}

	@Override
	public DataNameNode DataNameNode(List<IdNode> elems)
	{
		CommonToken t = newToken(ScribbleParser.DATA_NAME);
		DataNameNode n = new DataNameNode(t);
		n.addChildren(elems);
		del(n, new DataTypeNodeDel());
		return n;
	}

	@Override
	public GProtoNameNode GProtoNameNode(List<IdNode> elems)
	{
		CommonToken t = newToken(ScribbleParser.GPROTO_NAME);
		GProtoNameNode n = new GProtoNameNode(t);
		n.addChildren(elems);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public ModuleNameNode ModuleNameNode(List<IdNode> elems)
	{
		CommonToken t = newToken(ScribbleParser.MODULE_NAME);
		ModuleNameNode n = new ModuleNameNode(t);
		n.addChildren(elems);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public SigNameNode SigNameNode(List<IdNode> elems)
	{
		CommonToken t = newToken(ScribbleParser.SIG_NAME);
		SigNameNode n = new SigNameNode(t);
		n.addChildren(elems);
		del(n, new MessageSigNameNodeDel());
		return n;
	}
	
	@Override
	public Module Module(ModuleDecl moddecl, List<ImportDecl<?>> imports,
			List<NonProtoDecl<?>> data, List<ProtoDecl<?>> protos)
	{
		CommonToken t = newToken(ScribbleParser.MODULE);  
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
		CommonToken t = newToken(ScribbleParser.MODULEDECL);  
		ModuleDecl n = new ModuleDecl(t);
		n.addChild(fullmodname);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public ImportModule ImportModule(ModuleNameNode modname, ModuleNameNode alias)
	{
		CommonToken t = newToken(ScribbleParser.IMPORTMODULE);  
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
	public DataDecl DataDecl(IdNode schema, IdNode extName, IdNode extSource,
			DataNameNode alias)
	{
		CommonToken t = newToken(ScribbleParser.DATADECL);
		DataDecl n = new DataDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public SigDecl SigDecl(IdNode schema, IdNode extName, IdNode extSource,
			SigNameNode alias)
	{
		CommonToken t = newToken(ScribbleParser.SIGDECL);
		SigDecl n = new SigDecl(t);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.addChild(alias);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public GProtoDecl GProtoDecl(ProtoModList mods, GProtoHeader header,
			GProtoDef def)
	{
		CommonToken t = newToken(ScribbleParser.GPROTODECL);
		GProtoDecl n = new GProtoDecl(t);
		n.addChild(mods);
		n.addChild(header);
		n.addChild(def);
		del(n, new GProtocolDeclDel());
		return n;
	}

	@Override
	public GProtoHeader GProtocolHeader(GProtoNameNode name,
			RoleDeclList rs, NonRoleParamDeclList ps)
	{
		CommonToken t = newToken(ScribbleParser.GPROTOHEADER);
		GProtoHeader n = new GProtoHeader(t);
		n.addChild(name);
		n.addChild(ps);
		n.addChild(rs);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public RoleDeclList RoleDeclList(List<RoleDecl> ds)
	{
		CommonToken t = newToken(ScribbleParser.ROLEDECL_LIST);
		RoleDeclList n = new RoleDeclList(t);
		// Cf. Scribble.g children order
		n.addChildren(ds);
		del(n, new RoleDeclListDel());
		return n;
	}

	@Override
	public RoleDecl RoleDecl(RoleNode r)
	{
		CommonToken t = newToken(ScribbleParser.ROLEDECL);
		RoleDecl n = new RoleDecl(t);
		n.addChild(r);
		del(n, new RoleDeclDel());
		return n;
	}

	@Override
	public NonRoleParamDeclList NonRoleParamDeclList(
			List<NonRoleParamDecl<NonRoleParamKind>> ds)
	{
		CommonToken t = newToken(ScribbleParser.PARAMDECL_LIST);
		NonRoleParamDeclList n = new NonRoleParamDeclList(t);
		// Cf. Scribble.g children order
		n.addChildren(ds);
		del(n, new NonRoleParamDeclListDel());
		return n;
	}

	@Override
	public DataParamDecl DataParamDecl(DataParamNode p)
	{
		CommonToken t = newToken(ScribbleParser.DATAPARAMDECL);
		DataParamDecl n = new DataParamDecl(t);
		// Cf. Scribble.g children order
		n.addChild(p);
		del(n, new NonRoleParamDeclDel());
		return n;
	}

	@Override
	public SigParamDecl SigParamDecl(SigParamNode p)
	{
		CommonToken t = newToken(ScribbleParser.SIGPARAMDECL);
		SigParamDecl n = new SigParamDecl(t);
		// Cf. Scribble.g children order
		n.addChild(p);
		del(n, new NonRoleParamDeclDel());
		return n;
	}

	@Override
	public GProtoDef GProtoDef(GProtoBlock block)
	{
		CommonToken t = newToken(ScribbleParser.GPROTODEF);
		GProtoDef n = new GProtoDef(t);
		n.addChild(block);
		del(n, new GProtocolDefDel());
		return n;
	}

	@Override
	public GProtoBlock GProtoBlock(GInteractionSeq seq)
	{
		CommonToken t = newToken(ScribbleParser.GPROTOBLOCK);
		GProtoBlock n = new GProtoBlock(t);
		n.addChild(seq);
		del(n, new GProtocolBlockDel());
		return n;
	}

	@Override
	public GInteractionSeq GInteractionSeq(List<GSessionNode> elems)
	{
		CommonToken t = newToken(ScribbleParser.GINTERSEQ);
		GInteractionSeq n = new GInteractionSeq(t);
		n.addChildren(elems);
		del(n, new GInteractionSeqDel());
		return n;
	}

	@Override
	public SigLitNode SigLitNode(OpNode op, PayElemList pay)
	{
		CommonToken t = newToken(ScribbleParser.SIG_LIT);  
		SigLitNode n = new SigLitNode(t);
		n.addChild(op);
		n.addChild(pay);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public PayElemList PayElemList(List<PayElem<?>> elems)
	{
		CommonToken t = newToken(ScribbleParser.PAYELEM_LIST);  
		PayElemList n = new PayElemList(t);
		n.addChildren(elems);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public <K extends PayloadTypeKind> UnaryPayElem<K> UnaryPayElem(
			PayElemNameNode<K> name)
	{
		CommonToken t = newToken(ScribbleParser.UNARY_PAYELEM);  
		UnaryPayElem<K> n = new UnaryPayElem<>(t);
		// Cf. Scribble.g children order
		n.addChild(name);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public GDelegPayElem GDelegPayElem(GProtoNameNode proto, RoleNode role)
	{
		CommonToken t = newToken(ScribbleParser.DELEG_PAYELEM);  
		GDelegPayElem n = new GDelegPayElem(t);
		n.addChild(proto);
		n.addChild(role);
		//del(n, createDefaultDelegate());
		del(n, new GDelegationElemDel());  // FIXME: GDelegationElemDel
		return n;
	}

	@Override
	public GConnect GConnect(RoleNode src, MsgNode msg, RoleNode dst)
	{
		CommonToken t = newToken(ScribbleParser.GCONNECT);
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
		CommonToken t = newToken(ScribbleParser.GDCONN);
		GDisconnect n = new GDisconnect(t);
		n.addChild(left);
		n.addChild(right);
		del(n, new GDisconnectDel());
		return n;
	}

	@Override
	public GMsgTransfer GMsgTransfer(RoleNode src,
			MsgNode msg, List<RoleNode> dsts)
	{
		CommonToken t = newToken(ScribbleParser.GMSGTRANSFER);
		GMsgTransfer n = new GMsgTransfer(t);
		n.addChild(msg);
		n.addChild(src);
		n.addChildren(dsts);
		del(n, new GMessageTransferDel());
		return n;
	}

	@Override
	public GWrap GWrap(RoleNode src, RoleNode dst)
	{
		CommonToken t = newToken(ScribbleParser.GWRAP);
		GWrap n = new GWrap(t);
		n.addChild(src);
		n.addChild(dst);
		del(n, new GWrapDel());
		return n;
	}

	@Override
	public GContinue GContinue(RecVarNode rv)
	{
		CommonToken t = newToken(ScribbleParser.GCONTINUE);
		GContinue n = new GContinue(t);
		n.addChild(rv);
		del(n, new GContinueDel());
		return n;
	}

	@Override
	public GDo GDo(RoleArgList rs, NonRoleArgList as, GProtoNameNode proto)
	{
		CommonToken t = newToken(ScribbleParser.GDO);
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
		CommonToken t = newToken(ScribbleParser.ROLEARG_LIST);
		RoleArgList n = new RoleArgList(t);
		n.addChildren(rs);
		del(n, new RoleArgListDel());
		return n;
	}

	@Override
	public RoleArg RoleArg(RoleNode r)
	{
		CommonToken t = newToken(ScribbleParser.ROLEARG);
		RoleArg n = new RoleArg(t);
		n.addChild(r);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public NonRoleArgList NonRoleArgList(List<NonRoleArg> as)
	{
		CommonToken t = newToken(ScribbleParser.NONROLEARG_LIST);
		NonRoleArgList n = new NonRoleArgList(t);
		n.addChildren(as);
		del(n, new NonRoleArgListDel());
		return n;
	}

	@Override
	public NonRoleArg NonRoleArg(NonRoleArgNode arg)
	{
		CommonToken t = newToken(ScribbleParser.NONROLEARG);
		NonRoleArg n = new NonRoleArg(t);
		n.addChild(arg);
		del(n, createDefaultDelegate());
		return n;
	}

	@Override
	public GChoice GChoice(RoleNode subj, List<GProtoBlock> blocks)
	{
		CommonToken t = newToken(ScribbleParser.GCHOICE);
		GChoice n = new GChoice(t);
		n.addChild(subj);
		n.addChildren(blocks);
		del(n, new GChoiceDel());
		return n;
	}

	@Override
	public GRecursion GRecursion(RecVarNode rv,
			GProtoBlock block)
	{
		CommonToken t = newToken(ScribbleParser.GRECURSION);
		GRecursion n = new GRecursion(t);
		n.addChild(rv);
		n.addChild(block);
		del(n, new GRecursionDel());
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

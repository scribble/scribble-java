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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDelegPayElem;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMsgTransfer;
import org.scribble.ast.global.GProtoBlock;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.ast.global.GProtoDef;
import org.scribble.ast.global.GProtoHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.global.GSessionNode;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.local.LAcc;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LClientWrap;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LDisconnect;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProjectionDecl;
import org.scribble.ast.local.LProtoBlock;
import org.scribble.ast.local.LProtoDef;
import org.scribble.ast.local.LProtoHeader;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.local.LRecv;
import org.scribble.ast.local.LReq;
import org.scribble.ast.local.LSelfDecl;
import org.scribble.ast.local.LSend;
import org.scribble.ast.local.LServerWrap;
import org.scribble.ast.local.LSessionNode;
import org.scribble.ast.name.PayElemNameNode;
import org.scribble.ast.name.qualified.DataNameNode;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.ast.name.qualified.LProtoNameNode;
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
import org.scribble.core.type.kind.PayElemKind;
import org.scribble.del.DefaultDel;
import org.scribble.del.DelFactory;
import org.scribble.del.ScribDel;
import org.scribble.parser.ScribAntlrWrapper;
import org.scribble.parser.antlr.ScribbleParser;


public class AstFactoryImpl implements AstFactory
{
	// Purely for the convenience of newToken(Token, type), parser instance used to access token int constants
	protected final ScribbleParser parser;
	protected final Map<Integer, String> tokens;

	protected final DelFactory df;
	
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
			this.df = antlr.df;
		}
		catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	// type comes from the int constants in ScribbleParser, which come from the tokens in Scribble.g
	// Pre: type is an "imaginary token" type from ScribbleParser -- (not ID)
	protected CommonToken newToken(Token old, int type)
	{
		// As a default, token text is set to the textual name of the token type int field (also the Scribble.g default)
		String text = this.tokens.get(type);
		if (old == null)
		{
			return new CommonToken(type, text);
		}
		CommonToken t = new CommonToken(old);
		t.setType(type);
		t.setText(text);
		return t;
	}

	protected CommonToken newIdToken(Token old, String text)
	{
		if (old == null)
		{
			return new CommonToken(ScribbleParser.ID, text);
		}
		CommonToken t = new CommonToken(old);  // Type and text set below, but "inherit" some other additional info
		t.setType(ScribbleParser.ID);
		t.setText(text);
		return t;
	}

	protected ScribDel createDefaultDelegate()
	{
		return new DefaultDel();
	}
	
	// public for DelDecoratorImpl (used from there; not here directly)
	// Mutating setter
	public static void setDel(ScribNodeBase n, ScribDel del)
	{
		//ScribNodeBase.del(n, del);  // Defensive setter -- unnecessary ?
		n.setDel(del);  // Mutating setter
	}
	
	
	/**
	 * NameNodes
	 */
	
	@Override
	public IdNode IdNode(Token t, String text)
	{
		t = newIdToken(t, text);
				// (Ext)IdNode is the only token with a "different" text to its node type -- info stored directly as its the text, no children
		IdNode n = new IdNode(t);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public ExtIdNode ExtIdNode(Token t, String text)
	{
		t = newIdToken(t, text);
				// (Ext)IdNode is the only token with a "different" text to its node type -- info stored directly as its the text, no children
		ExtIdNode n = new ExtIdNode(t);
		n.decorateDel(this.df);
		return n;
	}
	
	// Deprecate?  Never need to make ambigname "manually" via af?  (only constructed by ScribbleParser)
	@Override
	public AmbigNameNode AmbigNameNode(Token t, String text)
	{
		int ttype = ScribbleParser.ID;
		t = newIdToken(t, text);
		AmbigNameNode n = new AmbigNameNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public DataParamNode DataParamNode(Token t, String text)
	{
		int ttype = ScribbleParser.ID;  // N.B. cf. ScribbleParser.DATA_NAME
		t = newIdToken(t, text);
		DataParamNode n = new DataParamNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public OpNode OpNode(Token t, String text)
	{
		int ttype = ScribbleParser.ID;
		t = newIdToken(t, text);
		OpNode n = new OpNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public RecVarNode RecVarNode(Token t, String text)
	{
		int ttype = ScribbleParser.ID;
		t = newIdToken(t, text);
		RecVarNode n = new RecVarNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public RoleNode RoleNode(Token t, String text)
	{
		int ttype = ScribbleParser.ID;
		t = newIdToken(t, text);
		RoleNode n = new RoleNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public SigParamNode SigParamNode(Token t, String text)
	{
		int ttype = ScribbleParser.ID;  // N.B. cf. ScribbleParser.SIG_NAME
		t = newIdToken(t, text);
		SigParamNode n = new SigParamNode(ttype, t);  // Cf. Scribble.g, ID<...Node>[$ID]
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public DataNameNode DataNameNode(Token t, List<IdNode> elems)
	{
		t = newToken(t, ScribbleParser.DATA_NAME);
		DataNameNode n = new DataNameNode(t);
		n.addChildren(elems);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GProtoNameNode GProtoNameNode(Token t, List<IdNode> elems)
	{
		t = newToken(t, ScribbleParser.GPROTO_NAME);
		GProtoNameNode n = new GProtoNameNode(t);
		n.addChildren(elems);
		n.decorateDel(this.df);
		return n;
	}
	
	@Override
	public LProtoNameNode LProtoNameNode(Token t, List<IdNode> elems)
	{
		t = newToken(t, ScribbleParser.LPROTO_NAME);
		LProtoNameNode n = new LProtoNameNode(t);
		n.addChildren(elems);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public ModuleNameNode ModuleNameNode(Token t, List<IdNode> elems)
	{
		t = newToken(t, ScribbleParser.MODULE_NAME);
		ModuleNameNode n = new ModuleNameNode(t);
		n.addChildren(elems);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public SigNameNode SigNameNode(Token t, List<IdNode> elems)
	{
		t = newToken(t, ScribbleParser.SIG_NAME);
		SigNameNode n = new SigNameNode(t);
		n.addChildren(elems);
		n.decorateDel(this.df);
		return n;
	}
	
	
	
	/**
	 * From here, remaining ScribNodes that are not NameNodes -- should use the node specific addChildren1 (TODO: rename)
	 */
	
	@Override
	public Module Module(Token t, ModuleDecl moddecl, List<? extends ImportDecl<?>> imports,
			List<? extends NonProtoDecl<?>> data, List<? extends ProtoDecl<?>> protos)
	{
		t = newToken(t, ScribbleParser.MODULE);  
		Module n = new Module(t);
		n.addScribChildren(moddecl, imports, data, protos);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public ModuleDecl ModuleDecl(Token t, ModuleNameNode fullname)
	{
		t = newToken(t, ScribbleParser.MODULEDECL);  
		ModuleDecl n = new ModuleDecl(t);
		n.addScribChildren(fullname);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public ImportModule ImportModule(Token t, ModuleNameNode modname,
			ModuleNameNode alias)
	{
		t = newToken(t, ScribbleParser.IMPORTMODULE);  
		ImportModule n = new ImportModule(t);
		n.addScribChildren(modname, alias);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public DataDecl DataDecl(Token t, IdNode schema, ExtIdNode extName,
			ExtIdNode extSource, DataNameNode alias)
	{
		t = newToken(t, ScribbleParser.DATADECL);
		DataDecl n = new DataDecl(t);
		n.addScribChildren(alias, schema, extName, extSource);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public SigDecl SigDecl(Token t, IdNode schema, ExtIdNode extName,
			ExtIdNode extSource, SigNameNode alias)
	{
		t = newToken(t, ScribbleParser.SIGDECL);
		SigDecl n = new SigDecl(t);
		n.addScribChildren(alias, schema, extName, extSource);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GProtoDecl GProtoDecl(Token t, ProtoModList mods, GProtoHeader header,
			GProtoDef def)
	{
		t = newToken(t, ScribbleParser.GPROTODECL);
		GProtoDecl n = new GProtoDecl(t);
		n.addScribChildren(mods, header, def);
		n.decorateDel(this.df);
		return n;
	}
	
	@Override
	public ProtoModList ProtoModList(Token t, List<ProtoModNode> mods)
	{
		t = newToken(t, ScribbleParser.PROTOMOD_LIST);
		ProtoModList n = new ProtoModList(t);
		n.addScribChildren(mods);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public AuxMod AuxMod(Token t)
	{
		t = newToken(t, ScribbleParser.AUX_KW);  // N.B. directly using KW
		AuxMod n = new AuxMod(t);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public ExplicitMod ExplicitMod(Token t)
	{
		t = newToken(t, ScribbleParser.EXPLICIT_KW);  // N.B. directly using KW
		ExplicitMod n = new ExplicitMod(t);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GProtoHeader GProtocolHeader(Token t, GProtoNameNode name,
			RoleDeclList rs, NonRoleParamDeclList ps)
	{
		t = newToken(t, ScribbleParser.GPROTOHEADER);
		GProtoHeader n = new GProtoHeader(t);
		n.addScribChildren(name, ps, rs);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public RoleDeclList RoleDeclList(Token t, List<RoleDecl> ds)
	{
		t = newToken(t, ScribbleParser.ROLEDECL_LIST);
		RoleDeclList n = new RoleDeclList(t);
		n.addScribChildren(ds);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public RoleDecl RoleDecl(Token t, RoleNode r)
	{
		t = newToken(t, ScribbleParser.ROLEDECL);
		RoleDecl n = new RoleDecl(t);
		n.addScribChildren(r);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public NonRoleParamDeclList NonRoleParamDeclList(Token t, 
			List<NonRoleParamDecl<? extends NonRoleParamKind>> ds)
	{
		t = newToken(t, ScribbleParser.PARAMDECL_LIST);
		NonRoleParamDeclList n = new NonRoleParamDeclList(t);
		n.addScribChildren(ds);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public DataParamDecl DataParamDecl(Token t, DataParamNode p)
	{
		t = newToken(t, ScribbleParser.DATAPARAMDECL);
		DataParamDecl n = new DataParamDecl(t);
		n.addScribChildren(p);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public SigParamDecl SigParamDecl(Token t, SigParamNode p)
	{
		t = newToken(t, ScribbleParser.SIGPARAMDECL);
		SigParamDecl n = new SigParamDecl(t);
		n.addScribChildren(p);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GProtoDef GProtoDef(Token t, GProtoBlock block)
	{
		t = newToken(t, ScribbleParser.GPROTODEF);
		GProtoDef n = new GProtoDef(t);
		n.addScribChildren(block);
		return n;
	}

	@Override
	public GProtoBlock GProtoBlock(Token t, GInteractionSeq seq)
	{
		t = newToken(t, ScribbleParser.GPROTOBLOCK);
		GProtoBlock n = new GProtoBlock(t);
		n.addScribChildren(seq);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GInteractionSeq GInteractionSeq(Token t, List<GSessionNode> elems)
	{
		t = newToken(t, ScribbleParser.GINTERSEQ);
		GInteractionSeq n = new GInteractionSeq(t);
		n.addScribChildren(elems);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public SigLitNode SigLitNode(Token t, OpNode op, PayElemList pay)
	{
		t = newToken(t, ScribbleParser.SIG_LIT);  
		SigLitNode n = new SigLitNode(t);
		n.addScribChildren(op, pay);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public PayElemList PayElemList(Token t, List<PayElem<?>> elems)
	{
		t = newToken(t, ScribbleParser.PAYELEM_LIST);  
		PayElemList n = new PayElemList(t);
		n.addScribChildren(elems);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public <K extends PayElemKind> UnaryPayElem<K> UnaryPayElem(Token t,
			PayElemNameNode<K> name)
	{
		t = newToken(t, ScribbleParser.UNARY_PAYELEM);  
		UnaryPayElem<K> n = new UnaryPayElem<>(t);
		n.addScribChildren(name);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GDelegPayElem GDelegPayElem(Token t, GProtoNameNode proto,
			RoleNode role)
	{
		t = newToken(t, ScribbleParser.GDELEG_PAYELEM);  
		GDelegPayElem n = new GDelegPayElem(t);
		n.addScribChildren(proto, role);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GMsgTransfer GMsgTransfer(Token t, RoleNode src, MsgNode msg,
			List<RoleNode> dsts)
	{
		t = newToken(t, ScribbleParser.GMSGTRANSFER);
		GMsgTransfer n = new GMsgTransfer(t);
		n.addScribChildren(msg, src, dsts);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GConnect GConnect(Token t, RoleNode src, MsgNode msg, RoleNode dst)
	{
		t = newToken(t, ScribbleParser.GCONNECT);
		GConnect n = new GConnect(t);
		n.addScribChildren(msg, src, Arrays.asList(dst));
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GDisconnect GDisconnect(Token t, RoleNode left, RoleNode right)
	{
		t = newToken(t, ScribbleParser.GDCONN);
		GDisconnect n = new GDisconnect(t);
		n.addScribChildren(left, right);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GWrap GWrap(Token t, RoleNode client, RoleNode server)
	{
		t = newToken(t, ScribbleParser.GWRAP);
		GWrap n = new GWrap(t);
		n.addScribChildren(client, server);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GContinue GContinue(Token t, RecVarNode rv)
	{
		t = newToken(t, ScribbleParser.GCONTINUE);
		GContinue n = new GContinue(t);
		n.addScribChildren(rv);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GDo GDo(Token t, GProtoNameNode proto, NonRoleArgList as,
			RoleArgList rs)
	{
		t = newToken(t, ScribbleParser.GDO);
		GDo n = new GDo(t);
		n.addScribChildren(proto, as, rs);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public RoleArgList RoleArgList(Token t, List<RoleArg> rs)
	{
		t = newToken(t, ScribbleParser.ROLEARG_LIST);
		RoleArgList n = new RoleArgList(t);
		n.addScribChildren(rs);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public RoleArg RoleArg(Token t, RoleNode r)
	{
		t = newToken(t, ScribbleParser.ROLEARG);
		RoleArg n = new RoleArg(t);
		n.addScribChildren(r);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public NonRoleArgList NonRoleArgList(Token t, List<NonRoleArg> as)
	{
		t = newToken(t, ScribbleParser.NONROLEARG_LIST);
		NonRoleArgList n = new NonRoleArgList(t);
		n.addScribChildren(as);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public NonRoleArg NonRoleArg(Token t, NonRoleArgNode arg)
	{
		t = newToken(t, ScribbleParser.NONROLEARG);
		NonRoleArg n = new NonRoleArg(t);
		n.addScribChildren(arg);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GChoice GChoice(Token t, RoleNode subj, List<GProtoBlock> blocks)
	{
		t = newToken(t, ScribbleParser.GCHOICE);
		GChoice n = new GChoice(t);
		n.addScribChildren(subj, blocks);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public GRecursion GRecursion(Token t, RecVarNode rv, GProtoBlock block)
	{
		t = newToken(t, ScribbleParser.GRECURSION);
		GRecursion n = new GRecursion(t);
		n.addScribChildren(rv, block);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LProjectionDecl LProjectionDecl(Token t, ProtoModList mods,
			LProtoHeader header, LProtoDef def, GProtoNameNode fullname,
			RoleNode self)  // del extends that of LProtoDecl
	{
		t = newToken(t, ScribbleParser.LPROTODECL);
		LProjectionDecl n = new LProjectionDecl(t);
		n.addScribChildren(mods, header, def, fullname, self);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LProtoHeader LProtoHeader(Token t, LProtoNameNode name, RoleDeclList rs,
			NonRoleParamDeclList ps)
	{
		t = newToken(t, ScribbleParser.LPROTOHEADER);
		LProtoHeader n = new LProtoHeader(t);
		n.addScribChildren(name, ps, rs);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LSelfDecl LSelfDecl(Token t, RoleNode r)
	{
		t = newToken(t, ScribbleParser.LSELFROLEDECL);
		LSelfDecl n = new LSelfDecl(t);
		n.addScribChildren(r);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LProtoDef LProtoDef(Token t, LProtoBlock block)
	{
		t = newToken(t, ScribbleParser.LPROTODEF);
		LProtoDef n = new LProtoDef(t);
		n.addScribChildren(block);
		return n;
	}

	@Override
	public LProtoBlock LProtoBlock(Token t, LInteractionSeq seq)
	{
		t = newToken(t, ScribbleParser.LPROTOBLOCK);
		LProtoBlock n = new LProtoBlock(t);
		n.addScribChildren(seq);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LInteractionSeq LInteractionSeq(Token t, List<LSessionNode> elems)
	{
		t = newToken(t, ScribbleParser.LINTERSEQ);
		LInteractionSeq n = new LInteractionSeq(t);
		n.addScribChildren(elems);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LSend LSend(Token t, RoleNode self, MsgNode msg, RoleNode dst)
	{
		t = newToken(t, ScribbleParser.LSEND);
		LSend n = new LSend(t);
		n.addScribChildren(msg, self, Arrays.asList(dst));
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LRecv LRecv(Token t, RoleNode src, MsgNode msg, RoleNode self)
	{
		t = newToken(t, ScribbleParser.LRECV);
		LRecv n = new LRecv(t);
		n.addScribChildren(msg, src, Arrays.asList(self));
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LAcc LAcc(Token t, RoleNode src, MsgNode msg, RoleNode self)
	{
		t = newToken(t, ScribbleParser.LACC);
		LAcc n = new LAcc(t);
		n.addScribChildren(msg, src, Arrays.asList(self));
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LReq LReq(Token t, RoleNode self, MsgNode msg, RoleNode dst)
	{
		t = newToken(t, ScribbleParser.LREQ);
		LReq n = new LReq(t);
		n.addScribChildren(msg, self, Arrays.asList(dst));
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LDisconnect LDisconnect(Token t, RoleNode self, RoleNode peer)
	{
		t = newToken(t, ScribbleParser.LDCONN);
		LDisconnect n = new LDisconnect(t);
		n.addScribChildren(self, peer);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LClientWrap LClientWrap(Token t, RoleNode client, RoleNode server)
	{
		t = newToken(t, ScribbleParser.LCLIENTWRAP);
		LClientWrap n = new LClientWrap(t);
		n.addScribChildren(client, server);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LServerWrap LServerWrap(Token t, RoleNode client, RoleNode server)
	{
		t = newToken(t, ScribbleParser.LSERVERWRAP);
		LServerWrap n = new LServerWrap(t);
		n.addScribChildren(client, server);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LContinue LContinue(Token t, RecVarNode rv) 
	{
		t = newToken(t, ScribbleParser.LCONTINUE);
		LContinue n = new LContinue(t);
		n.addScribChildren(rv);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LDo LDo(Token t, LProtoNameNode proto, NonRoleArgList as,
			RoleArgList rs)
	{
		t = newToken(t, ScribbleParser.LDO);
		LDo n = new LDo(t);
		n.addScribChildren(proto, as, rs);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LChoice LChoice(Token t, RoleNode subj, List<LProtoBlock> blocks)
	{
		t = newToken(t, ScribbleParser.LCHOICE);
		LChoice n = new LChoice(t);
		n.addScribChildren(subj, blocks);
		n.decorateDel(this.df);
		return n;
	}

	@Override
	public LRecursion LRecursion(Token t, RecVarNode rv, LProtoBlock block)
	{
		t = newToken(t, ScribbleParser.LRECURSION);
		LRecursion n = new LRecursion(t);
		n.addScribChildren(rv, block);
		n.decorateDel(this.df);
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

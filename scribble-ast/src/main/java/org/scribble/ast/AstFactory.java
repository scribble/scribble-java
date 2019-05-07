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

import java.util.List;

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


// Pass null as Token t to create a fresh Token
// AstFactory is for making "fresh" nodes ("fresh" Tokens) with new dels -- cf. ScribNode reconstruct pattern (for Token and del preservation)
// Implementations located in scribble-parser, use ScribbleParser for Token construction
// Currently, used only in relatively niche places (since ANTLR now constructs all parsed nodes "directly")
public interface AstFactory
{
	IdNode IdNode(Token t, String text);
	ExtIdNode ExtIdNode(Token t, String text);

	AmbigNameNode AmbigNameNode(Token t, String text);	 // Deprecate?  Never need to make ambigname "manually" via af?  (constructed only by ScribbleParser)
	DataParamNode DataParamNode(Token t, String text);
	OpNode OpNode(Token t, String text);
	RecVarNode RecVarNode(Token t, String text);
	RoleNode RoleNode(Token t, String text);
	SigParamNode SigParamNode(Token t, String text);

	DataNameNode DataNameNode(Token t, List<IdNode> elems);
	GProtoNameNode GProtoNameNode(Token t, List<IdNode> elems);
	LProtoNameNode LProtoNameNode(Token t, List<IdNode> elems);
	ModuleNameNode ModuleNameNode(Token t, List<IdNode> elems);
	SigNameNode SigNameNode(Token t, List<IdNode> elems);
	
	Module Module(Token t, ModuleDecl mdecl,
			List<? extends ImportDecl<?>> imports,
			List<? extends NonProtoDecl<?>> nonprotos,
			List<? extends ProtoDecl<?>> protos);

	ModuleDecl ModuleDecl(Token t, ModuleNameNode fullname);
	ImportModule ImportModule(Token t, ModuleNameNode modname,
			ModuleNameNode alias);  // alias == null for no alias (child not added)

	DataDecl DataDecl(Token t, IdNode schema, ExtIdNode extName,
			ExtIdNode extSource, DataNameNode name);
	SigDecl SigDecl(Token t, IdNode schema, ExtIdNode extName,
			ExtIdNode extSource, SigNameNode name);
	GProtoDecl GProtoDecl(Token t, ProtoModList mods, GProtoHeader header,
			GProtoDef def);

	// TODO: refactor to use ProtoModList, etc.
	ProtoModList ProtoModList(Token t, List<ProtoModNode> mods);
	AuxMod AuxMod(Token t);
	ExplicitMod ExplicitMod(Token t);

	GProtoHeader GProtocolHeader(Token t, GProtoNameNode name, RoleDeclList rs,
			NonRoleParamDeclList ps);
	RoleDeclList RoleDeclList(Token t, List<RoleDecl> ds);
	RoleDecl RoleDecl(Token t, RoleNode r);
	NonRoleParamDeclList NonRoleParamDeclList(Token t, 
			List<NonRoleParamDecl<? extends NonRoleParamKind>> ds);
	DataParamDecl DataParamDecl(Token t, DataParamNode p);
	SigParamDecl SigParamDecl(Token t, SigParamNode p);

	GProtoDef GProtoDef(Token t, GProtoBlock block);
	GProtoBlock GProtoBlock(Token t, GInteractionSeq seq);
	GInteractionSeq GInteractionSeq(Token t, List<GSessionNode> elems);  // CHECKME: ? extends GSessionNode ? -- and similar others?

	SigLitNode SigLitNode(Token t, OpNode op, PayElemList pay);
	PayElemList PayElemList(Token t, List<PayElem<?>> elems);
	<K extends PayElemKind> UnaryPayElem<K> UnaryPayElem(Token t, 
			PayElemNameNode<K> name);
	GDelegPayElem GDelegPayElem(Token t, GProtoNameNode name, RoleNode r);

	GMsgTransfer GMsgTransfer(Token t, RoleNode src, MsgNode msg,
			List<RoleNode> dsts);
	GConnect GConnect(Token t, RoleNode src, MsgNode msg, RoleNode dst);
	GDisconnect GDisconnect(Token t, RoleNode left, RoleNode right);
	GWrap GWrap(Token t, RoleNode client, RoleNode server);

	GContinue GContinue(Token t, RecVarNode rv);
	GDo GDo(Token t, GProtoNameNode proto, NonRoleArgList args, RoleArgList rs);

	RoleArgList RoleArgList(Token t, List<RoleArg> rs);
	RoleArg RoleArg(Token t, RoleNode r);
	NonRoleArgList NonRoleArgList(Token t, List<NonRoleArg> args);
	NonRoleArg NonRoleArg(Token t, NonRoleArgNode arg);

	GChoice GChoice(Token t, RoleNode subj, List<GProtoBlock> blocks);
	GRecursion GRecursion(Token t, RecVarNode rv, GProtoBlock block);

	/*LProtoDecl LProtoDecl(Token t, ProtoModList mods,
			LProtoHeader header, LProtoDef def);  // Not currently used -- local protos not yet parsed, only projected*/

	LProjectionDecl LProjectionDecl(Token t, ProtoModList mods,
			LProtoHeader header, LProtoDef def, GProtoNameNode fullname,
			RoleNode self);  // del extends that of LProtoDecl

	LProtoHeader LProtoHeader(Token t, LProtoNameNode name, RoleDeclList rs,
			NonRoleParamDeclList ps);
	LSelfDecl LSelfDecl(Token t, RoleNode r);

	LProtoDef LProtoDef(Token t, LProtoBlock block);
	LProtoBlock LProtoBlock(Token t, LInteractionSeq seq);
	LInteractionSeq LInteractionSeq(Token t, List<LSessionNode> elems);

	// Following take "self" param in case of parsed Token (not actually supported yet)
	LSend LSend(Token t, RoleNode self, MsgNode msg, RoleNode dst);
	LRecv LRecv(Token t, RoleNode src, MsgNode msg, RoleNode self);
	LAcc LAcc(Token t, RoleNode src, MsgNode msg, RoleNode self);
	LReq LReq(Token t, RoleNode self, MsgNode msg, RoleNode dst);
	LDisconnect LDisconnect(Token t, RoleNode self, RoleNode peer); 
	LClientWrap LClientWrap(Token t, RoleNode client, RoleNode server);
	LServerWrap LServerWrap(Token t, RoleNode client, RoleNode server);

	LContinue LContinue(Token t, RecVarNode rv); 
	LDo LDo(Token t, LProtoNameNode proto, NonRoleArgList as, RoleArgList rs);

	LChoice LChoice(Token t, RoleNode subj, List<LProtoBlock> blocks);
	LRecursion LRecursion(Token t, RecVarNode rv, LProtoBlock block);
}























/*
	DummyProjectionRoleNode DummyProjectionRoleNode();

	LDelegationElem LDelegationElem(CommonTree source, LProtocolNameNode name);

	LProtocolDecl LProtocolDecl(CommonTree source, List<ProtocolMod> modifiers,
			LProtocolHeader header, LProtocolDef def); 
			// Not currently used -- local protos not parsed, only projected

	LProjectionDecl LProjectionDecl(CommonTree source,
			List<ProtocolMod> modifiers, GProtocolName fullname, Role self,
			LProtocolHeader header, LProtocolDef def); 
			// del extends that of LProtocolDecl

	LProtocolHeader LProtocolHeader(CommonTree source, LProtocolNameNode name,
			RoleDeclList roledecls, NonRoleParamDeclList paramdecls);

	SelfRoleDecl SelfRoleDecl(CommonTree source, RoleNode namenode);

	LProtocolDef LProtocolDef(CommonTree source, LProtocolBlock block);

	LProtocolBlock LProtocolBlock(CommonTree source, LInteractionSeq seq);

	LInteractionSeq LInteractionSeq(CommonTree source,
			List<LSessionNode> actions);

	LSend LSend(CommonTree source, RoleNode src, MessageNode msg,
			List<RoleNode> dests);

	LRecv LReceive(CommonTree source, RoleNode src, MessageNode msg,
			List<RoleNode> dests);

	LRequest LRequest(CommonTree source, RoleNode src, MessageNode msg,
			RoleNode dest);

	LAccept LAccept(CommonTree source, RoleNode src, MessageNode msg,
			RoleNode dest);

	/*LConnect LConnect(CommonTree source, RoleNode src, RoleNode dest);
	LAccept LAccept(CommonTree source, RoleNode src, RoleNode dest);* /

	LDisconnect LDisconnect(CommonTree source, RoleNode self, RoleNode peer);

	LWrapClient LWrapClient(CommonTree source, RoleNode self, RoleNode peer);

	LWrapServer LWrapServer(CommonTree source, RoleNode self, RoleNode peer);

	LChoice LChoice(CommonTree source, RoleNode subj,
			List<LProtocolBlock> blocks);

	LRecursion LRecursion(CommonTree source, RecVarNode recvar,
			LProtocolBlock block);

	LContinue LContinue(CommonTree source, RecVarNode recvar);

	LDo LDo(CommonTree source, RoleArgList roles, NonRoleArgList args,
			LProtocolNameNode proto);
*/

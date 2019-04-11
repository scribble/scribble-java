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
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.PayloadTypeKind;


// Currently, used only in relatively niche places (since ANTLR now constructs all parsed nodes "directly")
public interface AstFactory
{
	Module Module(Token t, ModuleDecl mdecl,
			List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data,
			List<ProtocolDecl<?>> protos);

	MessageSigNode MessageSigNode(Token t, OpNode op, PayloadElemList pay);

	GDelegationElem GDelegationElem(Token t, GProtocolNameNode name,
			RoleNode r);

	PayloadElemList PayloadElemList(Token t, List<PayloadElem<?>> elems);

	// PayloadElem PayloadElem(PayloadElemNameNode name);
	<K extends PayloadTypeKind> UnaryPayloadElem<K> UnaryPayloadElem(Token t,
			PayloadElemNameNode<K> name);

	ModuleDecl ModuleDecl(Token t, ModuleNameNode fullmodname);

	ImportModule ImportModule(Token t, ModuleNameNode modname,
			ModuleNameNode alias);

	MessageSigNameDecl MessageSigNameDecl(Token t, IdNode schema, IdNode extName,
			IdNode extSource, MessageSigNameNode name);

	DataTypeDecl DataTypeDecl(Token t, IdNode schema, IdNode extName,
			IdNode extSource, DataTypeNode name);

	GProtocolDecl GProtocolDecl(Token t, ProtocolModList modifiers,
			GProtocolHeader header, GProtocolDef def);

	GProtocolHeader GProtocolHeader(Token t, GProtocolNameNode name,
			RoleDeclList rdecls, NonRoleParamDeclList paramdecls);

	RoleDeclList RoleDeclList(Token t, List<RoleDecl> ds);

	RoleDecl RoleDecl(Token t, RoleNode r);

	// ConnectDecl ConnectDecl(Token t, RoleNode src, RoleNode r);

	NonRoleParamDeclList NonRoleParamDeclList(Token t,
			List<NonRoleParamDecl<NonRoleParamKind>> ds);

	@Deprecated
	<K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(
			Token t, K kind, NonRoleParamNode<K> name);

	GProtocolDef GProtocolDef(Token t, GProtocolBlock block);

	GProtocolBlock GProtocolBlock(Token t, GInteractionSeq seq);

	GInteractionSeq GInteractionSeq(Token t, List<GSessionNode> elems);

	GMessageTransfer GMessageTransfer(Token t, RoleNode src, MessageNode msg,
			List<RoleNode> dsts);

	GConnect GConnect(Token t, RoleNode src, MessageNode msg, RoleNode dst);

	GDisconnect GDisconnect(Token t, RoleNode src, RoleNode dst);

	GWrap GWrap(Token t, RoleNode src, RoleNode dst);

	GChoice GChoice(Token t, RoleNode subj, List<GProtocolBlock> blocks);

	GRecursion GRecursion(Token t, RecVarNode rv, GProtocolBlock block);

	GContinue GContinue(Token t, RecVarNode rv);

	GDo GDo(Token t, RoleArgList rs, NonRoleArgList args,
			GProtocolNameNode proto);

	RoleArgList RoleArgList(Token t, List<RoleArg> rs);

	RoleArg RoleArg(Token t, RoleNode r);

	NonRoleArgList NonRoleArgList(Token t, List<NonRoleArg> args);

	NonRoleArg NonRoleArg(Token t, NonRoleArgNode arg);

	<K extends Kind> NameNode<K> SimpleNameNode(Token t, K kind, IdNode id);

	<K extends Kind> QualifiedNameNode<K> QualifiedNameNode(Token t, K kind,
			IdNode... elems);

	AmbigNameNode AmbiguousNameNode(Token t, IdNode id);

	<K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(
			Token t, K kind, String id);
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

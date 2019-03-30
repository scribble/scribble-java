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
package org.scribble.parser.scribble.del;

import org.scribble.ast.AuxMod;
import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ExplicitMod;
import org.scribble.ast.ImportModule;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.ProtocolModList;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.SigParamDecl;
import org.scribble.ast.TypeParamDecl;
import org.scribble.ast.UnaryPayloadElem;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.ast.name.simple.TypeParamNode;


// A dec method for each AST class
// Wach method named after the class name -- currently dispatched by reflection
public interface DelDecorator
{
	void decorate(ScribNode n);
	
	void Module(Module m);

	void ModuleDecl(ModuleDecl n);
	void ImportModule(ImportModule n);
	
	void MessageSigNameDecl(MessageSigNameDecl n);
	void DataTypeDecl(DataTypeDecl n);

	void GProtocolDecl(GProtocolDecl n);
	void ProtocolModList(ProtocolModList n);
	void AuxMod(AuxMod n);
	void ExplicitMod(ExplicitMod n);

	void GProtocolHeader(GProtocolHeader n);
	void RoleDeclList(RoleDeclList n);
	void RoleDecl(RoleDecl n);
	void NonRoleParamDeclList(NonRoleParamDeclList n);
	//<K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(CommonTree source, K kind, NonRoleParamNode<K> name);
	void TypeParamDecl(TypeParamDecl n);
	void SigParamDecl(SigParamDecl n);
	
	void GProtocolDef(GProtocolDef n);
	void GProtocolBlock(GProtocolBlock n);
	void GInteractionSeq(GInteractionSeq n);

	void GMessageTransfer(GMessageTransfer n);
	/*GConnect GConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest);
	GDisconnect GDisconnect(CommonTree source, RoleNode src, RoleNode dest);
	GWrap GWrap(CommonTree source, RoleNode src, RoleNode dest);*/
	void GChoice(GChoice n);
	void GRecursion(GRecursion n);
	void GContinue(GContinue n);
	void GDo(GDo n);
	
	void MessageSigNode(MessageSigNode n);
	void PayloadElemList(PayloadElemList n);
	void UnaryPayloadElem(UnaryPayloadElem<?> n);
	/*GDelegationElem GDelegationElem(CommonTree source, GProtocolNameNode name, RoleNode role);
	LDelegationElem LDelegationElem(CommonTree source, LProtocolNameNode name);*/
	
	void RoleArgList(RoleArgList n);
	void RoleArg(RoleArg r);
	void NonRoleArgList(NonRoleArgList n);
	void NonRoleArg(NonRoleArg n);

	/*<K extends Kind> NameNode<K> SimpleNameNode(CommonTree source, K kind, String identifier);
	<K extends Kind> QualifiedNameNode<K> QualifiedNameNode(CommonTree source, K kind, String... elems);*/

	void RecVarNode(RecVarNode n);
	void RoleNode(RoleNode n);
	void OpNode(OpNode n);
	void MessageSigNameNode(MessageSigNameNode n);
	void DataTypeNode(DataTypeNode n);
	void ModuleNameNode(ModuleNameNode n);
	void GProtocolNameNode(GProtocolNameNode n);
	void LProtocolNameNode(LProtocolNameNode n);
	
	void AmbigNameNode(AmbigNameNode n);
	void IdNode(IdNode n);

	//<K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(CommonTree source, K kind, String identifier);
	void SigParamNode(SigParamNode n);
	void TypeParamNode(TypeParamNode n);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*DummyProjectionRoleNode DummyProjectionRoleNode();

	LProtocolDecl LProtocolDecl(CommonTree source, List<ProtocolDecl.Modifiers> modifiers, LProtocolHeader header, LProtocolDef def);  // Not currently used -- local protos not parsed, only projected
	LProjectionDecl LProjectionDecl(CommonTree source, List<ProtocolDecl.Modifiers> modifiers, GProtocolName fullname, Role self, LProtocolHeader header, LProtocolDef def);  // del extends that of LProtocolDecl 

	LProtocolHeader LProtocolHeader(CommonTree source, LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls);
	SelfRoleDecl SelfRoleDecl(CommonTree source, RoleNode namenode);
	LProtocolDef LProtocolDef(CommonTree source, LProtocolBlock block);
	LProtocolBlock LProtocolBlock(CommonTree source, LInteractionSeq seq);
	LInteractionSeq LInteractionSeq(CommonTree source, List<LInteractionNode> actions);

	LSend LSend(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests);
	LReceive LReceive(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests);
	LRequest LRequest(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest);
	LAccept LAccept(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest);
	LDisconnect LDisconnect(CommonTree source, RoleNode self, RoleNode peer);
	LWrapClient LWrapClient(CommonTree source, RoleNode self, RoleNode peer);
	LWrapServer LWrapServer(CommonTree source, RoleNode self, RoleNode peer);
	LChoice LChoice(CommonTree source, RoleNode subj, List<LProtocolBlock> blocks);
	LRecursion LRecursion(CommonTree source, RecVarNode recvar, LProtocolBlock block);
	LContinue LContinue(CommonTree source, RecVarNode recvar);
	LDo LDo(CommonTree source, RoleArgList roles, NonRoleArgList args, LProtocolNameNode proto);*/
}

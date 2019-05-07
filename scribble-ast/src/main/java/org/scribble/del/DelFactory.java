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
package org.scribble.del;

import org.scribble.ast.AuxMod;
import org.scribble.ast.DataDecl;
import org.scribble.ast.DataParamDecl;
import org.scribble.ast.ExplicitMod;
import org.scribble.ast.ImportModule;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayElemList;
import org.scribble.ast.ProtoModList;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
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
import org.scribble.ast.global.GProtoBlock;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.ast.global.GProtoDef;
import org.scribble.ast.global.GProtoHeader;
import org.scribble.ast.global.GRecursion;
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
import org.scribble.ast.local.LProtoDecl;
import org.scribble.ast.local.LProtoDef;
import org.scribble.ast.local.LProtoHeader;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.local.LRecv;
import org.scribble.ast.local.LReq;
import org.scribble.ast.local.LSelfDecl;
import org.scribble.ast.local.LSend;
import org.scribble.ast.local.LServerWrap;
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


// A little bit more than a "plain" factory, methods do the decoration via ScribNodeBase.setDel
// Use after ANTLR parsing (specifically, ScribTreeAdaptor) -- not needed for AstFactory constructions
// (Perhaps could integrate with ScribTreeAdaptor, but easier to do after -- AST construction technically may further create/dup nodes during the ongoing pass)
// A dec method for each AST class -- method name must be the same as target class name
// Implementations (DelDecoratorImpl) in scribble-parser, can depend on parser implementation
// E.g., DelDecoratorImpl: each method named after the class name
public interface DelFactory
{
	// Names
	void IdNode(IdNode n);
	void ExtIdNode(ExtIdNode n);

	void AmbigNameNode(AmbigNameNode n);
	void DataParamNode(DataParamNode n);
	void OpNode(OpNode n);
	void RecVarNode(RecVarNode n);
	void RoleNode(RoleNode n);
	void SigParamNode(SigParamNode n);

	void DataNameNode(DataNameNode n);
	void GProtoNameNode(GProtoNameNode n);
	void LProtoNameNode(LProtoNameNode n);
	void ModuleNameNode(ModuleNameNode n);
	void SigNameNode(SigNameNode n);
	

	// General and globals
	void Module(Module m);
	void ModuleDecl(ModuleDecl n);
	void ImportModule(ImportModule n);
	
	void DataDecl(DataDecl n);
	void SigDecl(SigDecl n);
	void GProtoDecl(GProtoDecl n);

	void ProtoModList(ProtoModList n);
	void AuxMod(AuxMod n);
	void ExplicitMod(ExplicitMod n);

	void GProtoHeader(GProtoHeader n);
	void RoleDeclList(RoleDeclList n);
	void RoleDecl(RoleDecl n);
	void NonRoleParamDeclList(NonRoleParamDeclList n);
	void DataParamDecl(DataParamDecl n);
	void SigParamDecl(SigParamDecl n);
	
	void GProtoDef(GProtoDef n);
	void GProtoBlock(GProtoBlock n);
	void GInteractionSeq(GInteractionSeq n);
	
	void SigLitNode(SigLitNode n);
	void PayElemList(PayElemList n);
	void UnaryPayElem(UnaryPayElem<?> n);
	void GDelegPayElem(GDelegPayElem n);

	void GMsgTransfer(GMsgTransfer n);
	void GConnect(GConnect n);
	void GDisconnect(GDisconnect n);
	void GWrap(GWrap n);

	void GContinue(GContinue n);
	void GDo(GDo n);

	void RoleArgList(RoleArgList n);
	void RoleArg(RoleArg r);
	void NonRoleArgList(NonRoleArgList n);
	void NonRoleArg(NonRoleArg n);

	void GChoice(GChoice n);
	void GRecursion(GRecursion n);

	
	// Locals
	void LProtoDecl(LProtoDecl n);
	void LProjectionDecl(LProjectionDecl n);

	void LProtoHeader(LProtoHeader n);
	
	void LSelfDecl(LSelfDecl n);

	void LProtoDef(LProtoDef n);
	void LProtoBlock(LProtoBlock n);
	void LInteractionSeq(LInteractionSeq n);

	//void LDelegElem(LDelegElem n);

	void LSend(LSend n);
	void LRecv(LRecv n);
	void LAcc(LAcc n);
	void LReq(LReq n);
	void LDisconnect(LDisconnect n);
	void LClientWrap(LClientWrap n);
	void LServerWrap(LServerWrap n);

	void LContinue(LContinue n);
	void LDo(LDo n);

	void LChoice(LChoice n);
	void LRecursion(LRecursion n);
}
	
	

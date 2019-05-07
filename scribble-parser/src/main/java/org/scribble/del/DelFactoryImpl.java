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

import org.scribble.ast.AstFactoryImpl;
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
import org.scribble.del.global.GChoiceDel;
import org.scribble.del.global.GConnectDel;
import org.scribble.del.global.GContinueDel;
import org.scribble.del.global.GDelegationElemDel;
import org.scribble.del.global.GDisconnectDel;
import org.scribble.del.global.GDoDel;
import org.scribble.del.global.GInteractionSeqDel;
import org.scribble.del.global.GMessageTransferDel;
import org.scribble.del.global.GProtoBlockDel;
import org.scribble.del.global.GProtoDeclDel;
import org.scribble.del.global.GProtoDefDel;
import org.scribble.del.global.GRecursionDel;
import org.scribble.del.global.GWrapDel;
import org.scribble.del.local.LAccDel;
import org.scribble.del.local.LChoiceDel;
import org.scribble.del.local.LClientWrapDel;
import org.scribble.del.local.LContinueDel;
import org.scribble.del.local.LDisconnectDel;
import org.scribble.del.local.LDoDel;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.del.local.LProjectionDeclDel;
import org.scribble.del.local.LProtoBlockDel;
import org.scribble.del.local.LProtoDeclDel;
import org.scribble.del.local.LProtoDefDel;
import org.scribble.del.local.LRecursionDel;
import org.scribble.del.local.LRecvDel;
import org.scribble.del.local.LReqDel;
import org.scribble.del.local.LSendDel;
import org.scribble.del.local.LServerWrapDel;
import org.scribble.del.name.qualified.DataNameNodeDel;
import org.scribble.del.name.qualified.SigNameNodeDel;
import org.scribble.del.name.simple.AmbigNameNodeDel;
import org.scribble.del.name.simple.NonRoleParamNodeDel;
import org.scribble.del.name.simple.RecVarNodeDel;
import org.scribble.del.name.simple.RoleNodeDel;


public class DelFactoryImpl implements DelFactory
{
	public DelFactoryImpl()
	{
		
	}

	protected ScribDel createDefaultDel()
	{
		return new DefaultDel();
	}
	
	// Mutating setter
	protected static void setDel(ScribNodeBase n, ScribDel del)
	{
		AstFactoryImpl.setDel(n, del);
	}

	@Override
	public void IdNode(IdNode n)
	{
		setDel(n, createDefaultDel());  // Necessary?
	}

	@Override
	public void ExtIdNode(ExtIdNode n)
	{
		setDel(n, createDefaultDel());  // Necessary?
	}

	@Override
	public void AmbigNameNode(AmbigNameNode n)
	{
		setDel(n, new AmbigNameNodeDel());
	}

	@Override
	public void DataParamNode(DataParamNode n)
	{
		setDel(n, new NonRoleParamNodeDel());
	}

	@Override
	public void OpNode(OpNode n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void RecVarNode(RecVarNode n)
	{
		setDel(n, new RecVarNodeDel());
	}

	@Override
	public void RoleNode(RoleNode r)
	{
		setDel(r, new RoleNodeDel());
	}

	@Override
	public void SigParamNode(SigParamNode n)
	{
		setDel(n, new NonRoleParamNodeDel());
	}

	@Override
	public void DataNameNode(DataNameNode n)
	{
		setDel(n, new DataNameNodeDel());
	}

	@Override
	public void GProtoNameNode(GProtoNameNode n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void LProtoNameNode(LProtoNameNode n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void ModuleNameNode(ModuleNameNode n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void SigNameNode(SigNameNode n)
	{
		setDel(n, new SigNameNodeDel());
	}
	
	@Override
	public void Module(Module n)
	{
		setDel(n, new ModuleDel());
	}

	@Override
	public void ModuleDecl(ModuleDecl n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void ImportModule(ImportModule n)
	{
		setDel(n, new ImportModuleDel());
	}

	@Override
	public void DataDecl(DataDecl n)
	{
		setDel(n, createDefaultDel());
	}
	
	@Override
	public void SigDecl(SigDecl n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void GProtoDecl(GProtoDecl n)
	{
		setDel(n, new GProtoDeclDel());
	}

	@Override
	public void ProtoModList(ProtoModList n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void AuxMod(AuxMod n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void ExplicitMod(ExplicitMod n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void GProtoHeader(GProtoHeader n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void RoleDeclList(RoleDeclList n)
	{
		setDel(n, new RoleDeclListDel());
	}

	@Override
	public void RoleDecl(RoleDecl n)
	{
		setDel(n, new RoleDeclDel());
	}

	@Override
	public void NonRoleParamDeclList(NonRoleParamDeclList n)
	{
		setDel(n, new NonRoleParamDeclListDel());
	}

	@Override
	public void DataParamDecl(DataParamDecl n)
	{
		setDel(n, new NonRoleParamDeclDel());
	}

	@Override
	public void SigParamDecl(SigParamDecl sd)
	{
		setDel(sd, new NonRoleParamDeclDel());
	}

	@Override
	public void GProtoDef(GProtoDef n)
	{
		setDel(n, new GProtoDefDel());
	}

	@Override
	public void GProtoBlock(GProtoBlock n)
	{
		setDel(n, new GProtoBlockDel());
	}

	@Override
	public void GInteractionSeq(GInteractionSeq n)
	{
		setDel(n, new GInteractionSeqDel());
	}

	@Override
	public void SigLitNode(SigLitNode n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void PayElemList(PayElemList n)
	{
		setDel(n, createDefaultDel());
		//setDel(pay, new PayloadElemListDel());
	}

	@Override
	public void UnaryPayElem(UnaryPayElem<?> n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void GDelegPayElem(GDelegPayElem n)
	{
		//setDel(n, createDefaultDelegate());
		setDel(n, new GDelegationElemDel());
	}

	/*@Override
	public LDelegationElem LDelegationElem(CommonTree source, LProtocolNameNode proto)
	{
		LDelegationElem de = new LDelegationElem(source, proto);
		de = setDel(de, createDefaultDelegate());
		return de;
	}*/

	@Override
	public void GMsgTransfer(GMsgTransfer n)
	{
		setDel(n, new GMessageTransferDel());
	}

	@Override
	public void GConnect(GConnect n)
	{
		setDel(n, new GConnectDel());
	}

	@Override
	public void GDisconnect(GDisconnect n)
	{
		setDel(n, new GDisconnectDel());
	}

	@Override
	public void GWrap(GWrap n)
	{
		setDel(n, new GWrapDel());
	}

	@Override
	public void GContinue(GContinue n)
	{
		setDel(n, new GContinueDel());
	}

	@Override
	public void GDo(GDo n)
	{
		setDel(n, new GDoDel());
	}

	@Override
	public void RoleArgList(RoleArgList n)
	{
		setDel(n, new RoleArgListDel());
	}

	@Override
	public void RoleArg(RoleArg n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void NonRoleArgList(NonRoleArgList n)
	{
		setDel(n, new NonRoleArgListDel());
	}

	@Override
	public void NonRoleArg(NonRoleArg n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void GChoice(GChoice n)
	{
		setDel(n, new GChoiceDel());
	}

	@Override
	public void GRecursion(GRecursion n)
	{
		setDel(n, new GRecursionDel());
	}

	@Override
	public void LProtoDecl(LProtoDecl n)
	{
		setDel(n, new LProtoDeclDel());
	}

	@Override
	public void LProjectionDecl(LProjectionDecl n)
	{
		setDel(n, new LProjectionDeclDel());
	}

	@Override
	public void LProtoHeader(LProtoHeader n)
	{
		setDel(n, createDefaultDel());
	}

	@Override
	public void LSelfDecl(LSelfDecl n)
	{
		setDel(n, new RoleDeclDel());
	}

	@Override
	public void LProtoDef(LProtoDef n)
	{
		setDel(n, new LProtoDefDel());
	}

	@Override
	public void LProtoBlock(LProtoBlock n)
	{
		setDel(n, new LProtoBlockDel());
	}

	@Override
	public void LInteractionSeq(LInteractionSeq n)
	{
		setDel(n, new LInteractionSeqDel());
	}

	/*@Override
	public void LDelegElem(LDelegElem n)
	{
		setDel(n, new LDelegElemDel());
	}*/

	@Override
	public void LSend(LSend n)
	{
		setDel(n, new LSendDel());
	}

	@Override
	public void LRecv(LRecv n)
	{
		setDel(n, new LRecvDel());
	}

	@Override
	public void LAcc(LAcc n)
	{
		setDel(n, new LAccDel());
	}

	@Override
	public void LReq(LReq n)
	{
		setDel(n, new LReqDel());
	}

	@Override
	public void LDisconnect(LDisconnect n)
	{
		setDel(n, new LDisconnectDel());
	}

	@Override
	public void LClientWrap(LClientWrap n)
	{
		setDel(n, new LClientWrapDel());
	}

	@Override
	public void LServerWrap(LServerWrap n)
	{
		setDel(n, new LServerWrapDel());
	}

	@Override
	public void LContinue(LContinue n)
	{
		setDel(n, new LContinueDel());
	}

	@Override
	public void LDo(LDo n)
	{
		setDel(n, new LDoDel());
	}

	@Override
	public void LChoice(LChoice n)
	{
		setDel(n, new LChoiceDel());
	}

	@Override
	public void LRecursion(LRecursion n)
	{
		setDel(n, new LRecursionDel());
	}
}

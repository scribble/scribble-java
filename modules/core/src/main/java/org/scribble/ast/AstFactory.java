package org.scribble.ast;

import java.util.List;

import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LContinue;
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
import org.scribble.ast.local.SelfRoleDecl;
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.name.qualified.DataTypeNameNode;
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
import org.scribble.sesstype.kind.ImportKind;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.NonProtocolKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.ProtocolKind;

public interface AstFactory
{
	Module Module( 
			ModuleDecl moddecl,
			List<ImportDecl<? extends ImportKind>> imports,
			List<NonProtocolDecl<? extends NonProtocolKind>> data,
			List<ProtocolDecl<? extends ProtocolKind>> protos);
	
	MessageSigNode MessageSigNode(OpNode op, PayloadElemList payload);
	PayloadElemList PayloadElemList(List<PayloadElem> payloadelems);
	PayloadElem PayloadElement(PayloadElemNameNode name);

	ModuleDecl ModuleDecl(ModuleNameNode fullmodname);
	ImportModule ImportModule(ModuleNameNode modname, ModuleNameNode alias);
	
	MessageSigNameDecl MessageSigDecl(String schema, String extName, String source, MessageSigNameNode alias);
	DataTypeDecl DataTypeDecl(String schema, String extName, String source, DataTypeNameNode alias);

	GProtocolDecl GProtocolDecl(GProtocolHeader header, GProtocolDef def);
	GProtocolHeader GProtocolHeader(GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls);

	RoleDeclList RoleDeclList(List<RoleDecl> rds);
	RoleDecl RoleDecl(RoleNode namenode);
	NonRoleParamDeclList NonRoleParamDeclList(List<NonRoleParamDecl<NonRoleParamKind>> pds);
	<K extends NonRoleParamKind> NonRoleParamDecl<K> ParamDecl(K kind, NonRoleParamNode<K> namenode);
	
	GProtocolDef GProtocolDefinition(GProtocolBlock block);
	GProtocolBlock GProtocolBlock(GInteractionSeq gis);
	GInteractionSeq GInteractionSequence(List<GInteractionNode> gis);

	GMessageTransfer GMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests);
	GChoice GChoice(RoleNode subj, List<GProtocolBlock> blocks);
	GRecursion GRecursion(RecVarNode recvar, GProtocolBlock block);
	GContinue GContinue(RecVarNode recvar);
	GDo GDo(RoleArgList roleinstans, NonRoleArgList arginstans, GProtocolNameNode proto);
	
	RoleArgList RoleArgList(List<RoleArg> ris);
	RoleArg RoleArg(RoleNode role);
	NonRoleArgList NonRoleArgList(List<NonRoleArg> ais);
	NonRoleArg NonRoleArg(NonRoleArgNode arg);

	<K extends Kind> NameNode<K> SimpleNameNode(K kind, String identifier);
	<K extends Kind> QualifiedNameNode<K> QualifiedNameNode(K kind, String... elems);
	
	AmbigNameNode AmbiguousNameNode(String identifier);
	<K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(K kind, String identifier);
	DummyProjectionRoleNode DummyProjectionRoleNode();

	LProtocolDecl LProtocolDecl(LProtocolHeader header, LProtocolDef def);
	LProtocolHeader LProtocolHeader(LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls);
	SelfRoleDecl SelfRoleDecl(RoleNode namenode);
	LProtocolDef LProtocolDefinition(LProtocolBlock block);
	LProtocolBlock LProtocolBlock(LInteractionSeq seq);
	LInteractionSeq LInteractionSequence(List<LInteractionNode> actions);

	LSend LSend(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LReceive LReceive(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LChoice LChoice(RoleNode subj, List<LProtocolBlock> blocks);
	LRecursion LRecursion(RecVarNode recvar, LProtocolBlock block);
	LContinue LContinue(RecVarNode recvar);
	LDo LDo(RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto);
}

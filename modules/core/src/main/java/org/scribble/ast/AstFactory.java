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
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.ProtocolKind;

public interface AstFactory
{
	//enum SIMPLE_NAME { AMBIG, OPERATOR, PARAMETER, RECURSIONVAR, ROLE, MODULE, PROTOCOL }
	//enum QUALIFIED_NAME { MESSAGESIGNATURE, MODULE, PAYLOADTYPE, PROTOCOL }
	
	Module Module( 
			ModuleDecl moddecl,
			//List<? extends ImportDecl> imports,
			List<ImportDecl> imports,
			List<NonProtocolDecl<? extends Kind>> data,
			//List<? extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos);
			List<ProtocolDecl<? extends ProtocolKind>> protos);
	
	MessageSigNode MessageSigNode(OpNode op, PayloadElemList payload);
	PayloadElemList PayloadElemList(List<PayloadElem> payloadelems);
	PayloadElem PayloadElement(PayloadElemNameNode name);

	ModuleDecl ModuleDecl(ModuleNameNode fullmodname);
	//ImportModule ImportModule(ModuleNameNode modname, SimpleProtocolNameNode alias);
	ImportModule ImportModule(ModuleNameNode modname, ModuleNameNode alias);
	
	MessageSigNameDecl MessageSigDecl(String schema, String extName, String source, MessageSigNameNode alias);
	DataTypeDecl DataTypeDecl(String schema, String extName, String source, DataTypeNameNode alias);

	GProtocolDecl GProtocolDecl(GProtocolHeader header, GProtocolDef def);
	//GProtocolHeader GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls);
	GProtocolHeader GProtocolHeader(GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls);

	//RoleDeclList RoleDeclList(List<RoleDecl> rds);
	//RoleDeclList RoleDeclList(List<HeaderParamDecl<Role, RoleKind>> rds);
	RoleDeclList RoleDeclList(List<RoleDecl> rds);
	RoleDecl RoleDecl(RoleNode namenode);
	//ParamDeclList ParameterDeclList(List<ParamDecl> pds);
	//ParamDeclList ParameterDeclList(List<HeaderParamDecl<Name<Kind>, Kind>> pds);
	//ParamDeclList ParameterDeclList(List<HeaderParamDecl<Kind>> pds);
	NonRoleParamDeclList NonRoleParamDeclList(List<NonRoleParamDecl<Kind>> pds);
	//ParamDecl ParameterDecl(org.scribble2.model.ParamDecl.Kind kind, ParameterNode namenode);
	<K extends Kind> NonRoleParamDecl<K> ParamDecl(K kind, NonRoleParamNode<K> namenode);
	//<K extends Kind> ParamDecl<K> ParameterDecl(ParameterNode<K> namenode);
	
	GProtocolDef GProtocolDefinition(GProtocolBlock block);
	GProtocolBlock GProtocolBlock(GInteractionSeq gis);
	GInteractionSeq GInteractionSequence(List<GInteractionNode> gis);

	GMessageTransfer GMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests);
	//GlobalChoice GlobalChoice(RoleNode subj, List<GlobalProtocolBlock> blocks);
	GChoice GChoice(RoleNode subj, List<ProtocolBlock<Global>> blocks);
	//GlobalRecursion GlobalRecursion(RecursionVarNode recvar, GlobalProtocolBlock block);
	GRecursion GRecursion(RecVarNode recvar, ProtocolBlock<Global> block);
	GContinue GContinue(RecVarNode recvar);
	//GlobalDo GlobalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	//GDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	GDo GDo(RoleArgList roleinstans, NonRoleArgList arginstans, GProtocolNameNode proto);
	
	RoleArgList RoleArgList(List<RoleArg> ris);
	RoleArg RoleArg(RoleNode role);
	NonRoleArgList NonRoleArgList(List<NonRoleArg> ais);
	NonRoleArg NonRoleArg(ArgNode arg);

	// FIXME: instead of enums, take class as generic parameter
	//SimpleNameNode SimpleNameNode(SIMPLE_NAME kind, String identifier);
	//<K extends Kind> NameNode<? extends Name<K>, K> SimpleNameNode(K kind, String identifier);
	<K extends Kind> NameNode<K> SimpleNameNode(K kind, String identifier);
	//QualifiedNameNode QualifiedNameNode(QUALIFIED_NAME kind, String... elems);
	//<K extends Kind> QualifiedNameNode<? extends Name<K>, K> QualifiedNameNode(K kind, String... elems);
	<K extends Kind> QualifiedNameNode<K> QualifiedNameNode(K kind, String... elems);
	
	AmbigNameNode AmbiguousNameNode(String identifier);
	<K extends Kind> NonRoleParamNode<K> NonRoleParamNode(K kind, String identifier);

	LProtocolDecl LProtocolDecl(LProtocolHeader header, LProtocolDef def);
	//LProtocolHeader LocalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls);
	LProtocolHeader LProtocolHeader(LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls);
	SelfRoleDecl SelfRoleDecl(RoleNode namenode);
	LProtocolDef LProtocolDefinition(LProtocolBlock block);
	LProtocolBlock LProtocolBlock(LInteractionSeq seq);
	//LocalInteractionSequence LocalInteractionSequence(List<LocalInteractionNode> actions);
	LInteractionSeq LInteractionSequence(List<? extends InteractionNode<Local>> actions);

	LSend LSend(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LReceive LReceive(RoleNode src, MessageNode msg, List<RoleNode> dests);
	//LocalChoice LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks);
	LChoice LChoice(RoleNode subj, List<ProtocolBlock<Local>> blocks);
	LRecursion LRecursion(RecVarNode recvar, LProtocolBlock block);
	LContinue LContinue(RecVarNode recvar);
	//LocalDo LocalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	//LDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	LDo LDo(RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto);
}

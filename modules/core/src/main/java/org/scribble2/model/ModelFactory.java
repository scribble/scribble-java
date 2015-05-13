package org.scribble2.model;

import java.util.List;

import org.scribble2.model.global.GChoice;
import org.scribble2.model.global.GContinue;
import org.scribble2.model.global.GDo;
import org.scribble2.model.global.GInteractionNode;
import org.scribble2.model.global.GInteractionSeq;
import org.scribble2.model.global.GMessageTransfer;
import org.scribble2.model.global.GProtocolBlock;
import org.scribble2.model.global.GProtocolDecl;
import org.scribble2.model.global.GProtocolDef;
import org.scribble2.model.global.GProtocolHeader;
import org.scribble2.model.global.GRecursion;
import org.scribble2.model.local.LChoice;
import org.scribble2.model.local.LContinue;
import org.scribble2.model.local.LDo;
import org.scribble2.model.local.LInteractionSeq;
import org.scribble2.model.local.LProtocolBlock;
import org.scribble2.model.local.LProtocolDecl;
import org.scribble2.model.local.LProtocolDef;
import org.scribble2.model.local.LProtocolHeader;
import org.scribble2.model.local.LReceive;
import org.scribble2.model.local.LRecursion;
import org.scribble2.model.local.LSend;
import org.scribble2.model.local.SelfRoleDecl;
import org.scribble2.model.name.NameNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.qualified.QualifiedNameNode;
import org.scribble2.model.name.qualified.SimpleProtocolNameNode;
import org.scribble2.model.name.simple.AmbiguousNameNode;
import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.model.name.simple.RecVarNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.kind.Global;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.Role;

public interface ModelFactory
{
	//enum SIMPLE_NAME { AMBIG, OPERATOR, PARAMETER, RECURSIONVAR, ROLE, MODULE, PROTOCOL }
	//enum QUALIFIED_NAME { MESSAGESIGNATURE, MODULE, PAYLOADTYPE, PROTOCOL }
	
	Module Module( 
			ModuleDecl moddecl,
			//List<? extends ImportDecl> imports,
			List<ImportDecl> imports,
			List<NonProtocolDecl> data,
			//List<? extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos);
			List<ProtocolDecl<? extends org.scribble2.sesstype.kind.ProtocolKind>> protos);
	
	MessageSigNode MessageSignatureNode(OperatorNode op, PayloadNode payload);
	PayloadNode Payload(List<PayloadElement> payloadelems);
	PayloadElement PayloadElement(PayloadElementNameNode name);

	ModuleDecl ModuleDecl(ModuleNameNode fullmodname);
	ImportModule ImportModule(ModuleNameNode modname, SimpleProtocolNameNode alias);

	GProtocolDecl GlobalProtocolDecl(GProtocolHeader header, GProtocolDef def);
	GProtocolHeader GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls);

	//RoleDeclList RoleDeclList(List<RoleDecl> rds);
	RoleDeclList RoleDeclList(List<HeaderParamDecl<Role, RoleKind>> rds);
	RoleDecl RoleDecl(RoleNode namenode);
	//ParamDeclList ParameterDeclList(List<ParamDecl> pds);
	ParamDeclList ParameterDeclList(List<HeaderParamDecl<Name<Kind>, Kind>> pds);
	//ParamDecl ParameterDecl(org.scribble2.model.ParamDecl.Kind kind, ParameterNode namenode);
	<K extends Kind> ParamDecl<K> ParameterDecl(K kind, ParameterNode<K> namenode);
	//<K extends Kind> ParamDecl<K> ParameterDecl(ParameterNode<K> namenode);
	
	GProtocolDef GlobalProtocolDefinition(GProtocolBlock block);
	GProtocolBlock GlobalProtocolBlock(GInteractionSeq gis);
	GInteractionSeq GlobalInteractionSequence(List<GInteractionNode> gis);

	GMessageTransfer GlobalMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests);
	//GlobalChoice GlobalChoice(RoleNode subj, List<GlobalProtocolBlock> blocks);
	GChoice GlobalChoice(RoleNode subj, List<ProtocolBlock<Global>> blocks);
	//GlobalRecursion GlobalRecursion(RecursionVarNode recvar, GlobalProtocolBlock block);
	GRecursion GlobalRecursion(RecVarNode recvar, ProtocolBlock<Global> block);
	GContinue GlobalContinue(RecVarNode recvar);
	//GlobalDo GlobalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	GDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	
	RoleInstantiationList RoleInstantiationList(List<RoleInstantiation> ris);
	RoleInstantiation RoleInstantiation(RoleNode role);
	ArgumentInstantiationList ArgumentInstantiationList(List<ArgumentInstantiation> ais);
	ArgumentInstantiation ArgumentInstantiation(ArgumentNode arg);

	// FIXME: instead of enums, take class as generic parameter
	//SimpleNameNode SimpleNameNode(SIMPLE_NAME kind, String identifier);
	<K extends Kind> NameNode<? extends Name<K>, K> SimpleNameNode(K kind, String identifier);
	//QualifiedNameNode QualifiedNameNode(QUALIFIED_NAME kind, String... elems);
	<K extends Kind> QualifiedNameNode<? extends Name<K>, K> QualifiedNameNode(K kind, String... elems);
	
	AmbiguousNameNode AmbiguousNameNode(String identifier);
	<K extends Kind> ParameterNode<K> ParameterNode(K kind, String identifier);

	LProtocolDecl LocalProtocolDecl(LProtocolHeader header, LProtocolDef def);
	LProtocolHeader LocalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls);
	SelfRoleDecl SelfRoleDecl(RoleNode namenode);
	LProtocolDef LocalProtocolDefinition(LProtocolBlock block);
	LProtocolBlock LocalProtocolBlock(LInteractionSeq seq);
	//LocalInteractionSequence LocalInteractionSequence(List<LocalInteractionNode> actions);
	LInteractionSeq LocalInteractionSequence(List<? extends InteractionNode<Local>> actions);

	LSend LocalSend(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LReceive LocalReceive(RoleNode src, MessageNode msg, List<RoleNode> dests);
	//LocalChoice LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks);
	LChoice LocalChoice(RoleNode subj, List<ProtocolBlock<Local>> blocks);
	LRecursion LocalRecursion(RecVarNode recvar, LProtocolBlock block);
	LContinue LocalContinue(RecVarNode recvar);
	//LocalDo LocalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	LDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
}


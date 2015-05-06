package org.scribble2.model;

import java.util.List;

import org.scribble2.model.global.GlobalChoice;
import org.scribble2.model.global.GlobalContinue;
import org.scribble2.model.global.GlobalDo;
import org.scribble2.model.global.GlobalInteractionNode;
import org.scribble2.model.global.GlobalInteractionSequence;
import org.scribble2.model.global.GlobalMessageTransfer;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.global.GlobalProtocolDefinition;
import org.scribble2.model.global.GlobalProtocolHeader;
import org.scribble2.model.global.GlobalRecursion;
import org.scribble2.model.local.LocalChoice;
import org.scribble2.model.local.LocalContinue;
import org.scribble2.model.local.LocalDo;
import org.scribble2.model.local.LocalInteractionNode;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.local.LocalProtocolDecl;
import org.scribble2.model.local.LocalProtocolDefinition;
import org.scribble2.model.local.LocalProtocolHeader;
import org.scribble2.model.local.LocalReceive;
import org.scribble2.model.local.LocalRecursion;
import org.scribble2.model.local.LocalSend;
import org.scribble2.model.local.SelfRoleDecl;
import org.scribble2.model.name.AmbiguousKindedNameNode;
import org.scribble2.model.name.KindedNameNode;
import org.scribble2.model.name.KindedRoleNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.sesstype.kind.GlobalProtocolKind;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.sesstype.kind.OperatorKind;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.kind.RecursionVarKind;
import org.scribble2.sesstype.kind.RoleKind;

public interface ModelFactory
{
	enum SIMPLE_NAME { AMBIG, OPERATOR, PARAMETER, RECURSIONVAR, ROLE, MODULE, PROTOCOL }
	enum QUALIFIED_NAME { MESSAGESIGNATURE, MODULE, PAYLOADTYPE, PROTOCOL }
	
	Module Module( 
			ModuleDecl moddecl,
			List<? extends ImportDecl> imports,
			List<DataTypeDecl> data,
			List<? extends ProtocolDecl<? extends ProtocolHeader<? extends ProtocolKind>, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos);
	
	//MessageSignatureNode MessageSignatureNode(OperatorNode op, Payload payload);
	MessageSignatureNode MessageSignatureNode(SimpleKindedNameNode<OperatorKind> op, Payload payload);
	Payload Payload(List<PayloadElement> payloadelems);
	PayloadElement PayloadElement(PayloadElementNameNode name);

	//ModuleDecl ModuleDecl(ModuleNameNode fullmodname);
	ModuleDecl ModuleDecl(KindedNameNode<ModuleKind> fullmodname);
	//ImportModule ImportModule(ModuleNameNode modname, SimpleProtocolNameNode alias);
	public ImportModule ImportModule(KindedNameNode<ModuleKind> modname, SimpleKindedNameNode<ModuleKind> alias);

	GlobalProtocolDecl GlobalProtocolDecl(GlobalProtocolHeader header, GlobalProtocolDefinition def);
	//GlobalProtocolHeader GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls);
	GlobalProtocolHeader GlobalProtocolHeader(SimpleKindedNameNode<GlobalProtocolKind> name, RoleDeclList roledecls, ParameterDeclList paramdecls);

	RoleDeclList RoleDeclList(List<RoleDecl> rds);
	//RoleDecl RoleDecl(RoleNode namenode);
	RoleDecl RoleDecl(SimpleKindedNameNode<RoleKind> namenode);
	ParameterDeclList ParameterDeclList(List<ParameterDecl<Kind>> pds);
	//ParameterDecl ParameterDecl(org.scribble2.model.ParameterDecl.ParamDeclKind kind, ParameterNode namenode);
	<K extends Kind> ParameterDecl<K> ParameterDecl(K kind, ParameterNode<K> namenode);
	
	GlobalProtocolDefinition GlobalProtocolDefinition(GlobalProtocolBlock block);
	GlobalProtocolBlock GlobalProtocolBlock(GlobalInteractionSequence gis);
	GlobalInteractionSequence GlobalInteractionSequence(List<GlobalInteractionNode> gis);

	//GlobalMessageTransfer GlobalMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests);
	GlobalMessageTransfer GlobalMessageTransfer(SimpleKindedNameNode<RoleKind> src, MessageNode msg, List<SimpleKindedNameNode<RoleKind>> dests);
	//GlobalChoice GlobalChoice(RoleNode subj, List<GlobalProtocolBlock> blocks);
	GlobalChoice GlobalChoice(SimpleKindedNameNode<RoleKind> subj, List<GlobalProtocolBlock> blocks);
	//GlobalRecursion GlobalRecursion(RecursionVarNode recvar, GlobalProtocolBlock block);
	GlobalRecursion GlobalRecursion(SimpleKindedNameNode<RecursionVarKind> recvar, GlobalProtocolBlock block);
	//GlobalContinue GlobalContinue(RecursionVarNode recvar);
	GlobalContinue GlobalContinue(SimpleKindedNameNode<RecursionVarKind> recvar);
	//GlobalDo GlobalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	//GlobalDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	GlobalDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, KindedNameNode<GlobalProtocolKind> proto);

	RoleInstantiationList RoleInstantiationList(List<RoleInstantiation> ris);
	//RoleInstantiation RoleInstantiation(RoleNode role);
	//RoleInstantiation RoleInstantiation(SimpleKindedNameNode<RoleKind> role);
	RoleInstantiation RoleInstantiation(KindedRoleNode role);
	ArgumentInstantiationList ArgumentInstantiationList(List<ArgumentInstantiation> ais);
	ArgumentInstantiation ArgumentInstantiation(ArgumentNode arg);

	/*// FIXME: instead of enums, take class as generic parameter
	SimpleNameNode SimpleNameNode(SIMPLE_NAME kind, String identifier);
	QualifiedNameNode QualifiedNameNode(QUALIFIED_NAME kind, String... elems);*/
	
	AmbiguousKindedNameNode AmbiguousKindedNameNode(String identifier);
	<K extends Kind> SimpleKindedNameNode<K> SimpleKindedNameNode(K kind, String identifier);
	<K extends Kind> KindedNameNode<K> KindedNameNode(K kind, String... elems);

	LocalProtocolDecl LocalProtocolDecl(LocalProtocolHeader header, LocalProtocolDefinition def);
	LocalProtocolHeader LocalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls);
	//SelfRoleDecl SelfRoleDecl(RoleNode namenode);
	SelfRoleDecl SelfRoleDecl(SimpleKindedNameNode<RoleKind> namenode);
	LocalProtocolDefinition LocalProtocolDefinition(LocalProtocolBlock block);
	LocalProtocolBlock LocalProtocolBlock(LocalInteractionSequence seq);
	LocalInteractionSequence LocalInteractionSequence(List<LocalInteractionNode> actions);

	LocalSend LocalSend(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LocalReceive LocalReceive(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LocalChoice LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks);
	LocalRecursion LocalRecursion(RecursionVarNode recvar, LocalProtocolBlock block);
	LocalContinue LocalContinue(RecursionVarNode recvar);
	//LocalDo LocalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
	LocalDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);
}


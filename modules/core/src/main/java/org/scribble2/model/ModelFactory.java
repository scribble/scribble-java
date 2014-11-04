package org.scribble2.model;

import java.util.List;

import org.scribble2.model.global.GlobalInteraction;
import org.scribble2.model.global.GlobalInteractionSequence;
import org.scribble2.model.global.GlobalMessageTransfer;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.global.GlobalProtocolDefinition;
import org.scribble2.model.global.GlobalProtocolHeader;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;

public interface ModelFactory
{
	Module Module( 
			ModuleDecl moddecl,
			List<? extends ImportDecl> imports,
			List<DataTypeDecl> data,
			List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos);
	
	MessageSignatureNode MessageSignatureNode(OperatorNode op, Payload payload);
	Payload Payload(List<PayloadElement> payloadelems);
	PayloadElement PayloadElement(PayloadElementNameNode name);

	ModuleDecl ModuleDecl(ModuleNameNode fullmodname);
	GlobalProtocolDecl GlobalProtocolDecl(GlobalProtocolHeader header, GlobalProtocolDefinition def);
	GlobalProtocolHeader GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls);

	RoleDeclList RoleDeclList(List<RoleDecl> rds);
	ParameterDeclList ParameterDeclList(List<ParameterDecl> pds);
	
	GlobalProtocolDefinition GlobalProtocolDefinition(GlobalProtocolBlock block);
	GlobalProtocolBlock GlobalProtocolBlock(GlobalInteractionSequence gis);
	GlobalInteractionSequence GlobalInteractionSequence(List<GlobalInteraction> gis);
	GlobalMessageTransfer GlobalMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests);
}

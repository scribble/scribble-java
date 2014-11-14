package org.scribble2.model;

import java.util.List;

import org.scribble2.model.ParameterDecl.Kind;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.del.ModelDelegateBase;
import org.scribble2.model.del.ParameterDeclDelegate;
import org.scribble2.model.del.name.AmbiguousNameDelegate;
import org.scribble2.model.global.GlobalInteraction;
import org.scribble2.model.global.GlobalInteractionSequence;
import org.scribble2.model.global.GlobalMessageTransfer;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.global.GlobalProtocolDefinition;
import org.scribble2.model.global.GlobalProtocolHeader;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.model.name.qualified.MessageSignatureNameNode;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.qualified.QualifiedNameNode;
import org.scribble2.model.name.simple.AmbiguousNameNode;
import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.name.simple.SimpleNameNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;

public class ModelFactoryImpl implements ModelFactory
{
	public static final ModelFactory FACTORY = new ModelFactoryImpl();  // FIXME: move somewhere else
	
	@SuppressWarnings("unchecked")
	private static <T extends ModelNodeBase> T del(T n, ModelDelegate del)
	{
		ModelNodeBase ret = n.del(del);
		if (ret.getClass() != n.getClass())
		{
			throw new RuntimeException("Shouldn't get in here: " + ret);
		}
		return (T) ret;
	}
	
	private ModelDelegate createDefaultDelegate()
	{
		return new ModelDelegateBase();
	}
	
	@Override
	public MessageSignatureNode MessageSignatureNode(OperatorNode op, Payload payload)
	{
		MessageSignatureNode msn = new MessageSignatureNode(op, payload);
		msn = del(msn, createDefaultDelegate());  // FIXME: does another shallow copy
		return msn;
	}

	@Override
	public Payload Payload(List<PayloadElement> payloadelems)
	{
		Payload p = new Payload(payloadelems);
		p = del(p, createDefaultDelegate());
		return p;
	}

	@Override
	public PayloadElement PayloadElement(PayloadElementNameNode name)
	{
		PayloadElement pe = new PayloadElement(name);
		pe = del(pe, createDefaultDelegate());
		return pe;
	}
	
	@Override
	public Module Module( 
			ModuleDecl moddecl,
			List<? extends ImportDecl> imports,
			List<DataTypeDecl> data,
			List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos)
	{
		Module module = new Module(moddecl, imports, data, protos);
		module = del(module, createDefaultDelegate());
		return module;
	}

	@Override
	public ModuleDecl ModuleDecl(ModuleNameNode fullmodname)
	{
		ModuleDecl md = new ModuleDecl(fullmodname);
		md = del(md, createDefaultDelegate());
		return md;
	}

	@Override
	public GlobalProtocolDecl GlobalProtocolDecl(GlobalProtocolHeader header, GlobalProtocolDefinition def)
	{
		GlobalProtocolDecl gpd = new GlobalProtocolDecl(header, def);
		gpd = del(gpd, createDefaultDelegate());
		return gpd;
	}

	@Override
	public GlobalProtocolHeader GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls)
	{
		GlobalProtocolHeader gph = new GlobalProtocolHeader(name, roledecls, paramdecls);
		gph = del(gph, createDefaultDelegate());
		return gph;
	}

	@Override
	public RoleDeclList RoleDeclList(List<RoleDecl> rds)
	{
		RoleDeclList rdl = new RoleDeclList(rds);
		rdl = del(rdl, createDefaultDelegate());
		return rdl;
	}

	@Override
	public RoleDecl RoleDecl(RoleNode namenode)
	{
		RoleDecl rd = new RoleDecl(namenode);
		rd = del(rd, createDefaultDelegate());
		return rd;
	}

	@Override
	public ParameterDeclList ParameterDeclList(List<ParameterDecl> pds)
	{
		ParameterDeclList pdl = new ParameterDeclList(pds);
		pdl = del(pdl, createDefaultDelegate());
		return pdl;
	}

	@Override
	public ParameterDecl ParameterDecl(Kind kind, ParameterNode namenode)
	{
		ParameterDecl pd = new ParameterDecl(kind, namenode);
		//pd = del(pd, createDefaultDelegate());
		pd = del(pd, new ParameterDeclDelegate());
		return pd;
	}

	@Override
	public GlobalProtocolDefinition GlobalProtocolDefinition(GlobalProtocolBlock block)
	{
		GlobalProtocolDefinition gpd = new GlobalProtocolDefinition(block);
		gpd = del(gpd, createDefaultDelegate());
		return gpd;
	}

	@Override
	public GlobalProtocolBlock GlobalProtocolBlock(GlobalInteractionSequence seq)
	{
		GlobalProtocolBlock gpb = new GlobalProtocolBlock(seq);
		gpb = del(gpb, createDefaultDelegate());
		return gpb;
	}

	@Override
	public GlobalInteractionSequence GlobalInteractionSequence(List<GlobalInteraction> actions)
	{
		GlobalInteractionSequence gis = new GlobalInteractionSequence(actions);
		gis = del(gis, createDefaultDelegate());
		return gis;
	}

	@Override
	public GlobalMessageTransfer GlobalMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		GlobalMessageTransfer gmt = new GlobalMessageTransfer(src, msg, dests);
		gmt = del(gmt, createDefaultDelegate());
		return gmt;
	}
	
	@Override
	public SimpleNameNode SimpleNameNode(SIMPLE_NAME kind, String identifier)
	{
		SimpleNameNode snn;
		switch(kind)
		{
			case AMBIG: 
			{
				snn = new AmbiguousNameNode(identifier); 
				snn = (AmbiguousNameNode) snn.del(new AmbiguousNameDelegate());
				return snn;
			}
			case OPERATOR: snn = new OperatorNode(identifier); break;
			case PARAMETER: snn = new ParameterNode(identifier); break;
			case RECURSIONVAR: snn = new RecursionVarNode(identifier); break;
			case ROLE: snn = new RoleNode(identifier); break;
			case PROTOCOL: snn = new SimpleProtocolNameNode(identifier); break;
			default: throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		snn = (SimpleNameNode) snn.del(createDefaultDelegate());
		return snn;
	}

	@Override
	public QualifiedNameNode QualifiedNameNode(QUALIFIED_NAME kind, String... elems)
	{
		QualifiedNameNode qnn;
		switch(kind)
		{
			case MESSAGESIGNATURE: qnn = new MessageSignatureNameNode(elems); break;
			case MODULE: qnn = new ModuleNameNode(elems); break;
			case PROTOCOL: qnn = new ProtocolNameNode(elems); break;
			default: throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		qnn = (QualifiedNameNode) qnn.del(createDefaultDelegate());
		return qnn;
	}
}

package org.scribble2.model;

import java.util.List;

import org.scribble2.model.ParameterDecl.Kind;
import org.scribble2.model.del.DefaultModelDelegate;
import org.scribble2.model.del.ImportModuleDelegate;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.del.ModuleDelegate;
import org.scribble2.model.del.ParameterDeclDelegate;
import org.scribble2.model.del.RoleDeclDelegate;
import org.scribble2.model.del.global.GlobalChoiceDelegate;
import org.scribble2.model.del.global.GlobalContinueDelegate;
import org.scribble2.model.del.global.GlobalDoDelegate;
import org.scribble2.model.del.global.GlobalInteractionSequenceDelegate;
import org.scribble2.model.del.global.GlobalMessageTransferDelegate;
import org.scribble2.model.del.global.GlobalProtocolBlockDelegate;
import org.scribble2.model.del.global.GlobalProtocolDeclDelegate;
import org.scribble2.model.del.global.GlobalProtocolDefinitionDelegate;
import org.scribble2.model.del.global.GlobalRecursionDelegate;
import org.scribble2.model.del.local.LocalChoiceDelegate;
import org.scribble2.model.del.local.LocalContinueDelegate;
import org.scribble2.model.del.local.LocalDoDelegate;
import org.scribble2.model.del.local.LocalInteractionSequenceDelegate;
import org.scribble2.model.del.local.LocalProtocolBlockDelegate;
import org.scribble2.model.del.local.LocalProtocolDeclDelegate;
import org.scribble2.model.del.local.LocalProtocolDefinitionDelegate;
import org.scribble2.model.del.local.LocalReceiveDelegate;
import org.scribble2.model.del.local.LocalRecursionDelegate;
import org.scribble2.model.del.local.LocalSendDelegate;
import org.scribble2.model.del.name.AmbiguousNameDelegate;
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
		//module = del(module, new ModuleDelegate(module.getFullModuleName()));
		module = del(module, new ModuleDelegate());
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
	public ImportModule ImportModule(ModuleNameNode modname, SimpleProtocolNameNode alias)
	{
		ImportModule im = new ImportModule(modname, alias);
		//im = del(im, createDefaultDelegate());
		im = del(im, new ImportModuleDelegate());
		return im;
	}

	@Override
	public GlobalProtocolDecl GlobalProtocolDecl(GlobalProtocolHeader header, GlobalProtocolDefinition def)
	{
		GlobalProtocolDecl gpd = new GlobalProtocolDecl(header, def);
		gpd = del(gpd, new GlobalProtocolDeclDelegate());
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
		rd = del(rd, new RoleDeclDelegate());
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
		pd = del(pd, new ParameterDeclDelegate());
		return pd;
	}

	@Override
	public GlobalProtocolDefinition GlobalProtocolDefinition(GlobalProtocolBlock block)
	{
		GlobalProtocolDefinition gpd = new GlobalProtocolDefinition(block);
		gpd = del(gpd, new GlobalProtocolDefinitionDelegate());
		return gpd;
	}

	@Override
	public GlobalProtocolBlock GlobalProtocolBlock(GlobalInteractionSequence seq)
	{
		GlobalProtocolBlock gpb = new GlobalProtocolBlock(seq);
		gpb = del(gpb, new GlobalProtocolBlockDelegate());
		return gpb;
	}

	@Override
	public GlobalInteractionSequence GlobalInteractionSequence(List<GlobalInteractionNode> actions)
	{
		GlobalInteractionSequence gis = new GlobalInteractionSequence(actions);
		gis = del(gis, new GlobalInteractionSequenceDelegate());
		return gis;
	}

	@Override
	public GlobalMessageTransfer GlobalMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		GlobalMessageTransfer gmt = new GlobalMessageTransfer(src, msg, dests);
		gmt = del(gmt, new GlobalMessageTransferDelegate());
		return gmt;
	}

	@Override
	public GlobalChoice GlobalChoice(RoleNode subj, List<GlobalProtocolBlock> blocks)
	{
		GlobalChoice gc = new GlobalChoice(subj, blocks);
		gc = del(gc, new GlobalChoiceDelegate());
		return gc;
	}

	@Override
	public GlobalRecursion GlobalRecursion(RecursionVarNode recvar, GlobalProtocolBlock block)
	{
		GlobalRecursion gr = new GlobalRecursion(recvar, block);
		gr = del(gr, new GlobalRecursionDelegate());
		return gr;
	}

	@Override
	public GlobalContinue GlobalContinue(RecursionVarNode recvar)
	{
		GlobalContinue gc = new GlobalContinue(recvar);
		gc = del(gc, new GlobalContinueDelegate());
		return gc;
	}

	/*@Override
	public GlobalDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		return GlobalDo(null, roleinstans, arginstans, proto);
	}*/

	@Override
	//public GlobalDo GlobalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	public GlobalDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		//GlobalDo gd = new GlobalDo(scope, roleinstans, arginstans, proto);
		GlobalDo gd = new GlobalDo(roleinstans, arginstans, proto);
		//gd = del(gd, createDefaultDelegate());  // FIXME
		gd = del(gd, new GlobalDoDelegate());  // FIXME
		return gd;
	}

	@Override
	public RoleInstantiationList RoleInstantiationList(List<RoleInstantiation> ris)
	{
		RoleInstantiationList rdl = new RoleInstantiationList(ris);
		rdl = del(rdl, createDefaultDelegate());
		return rdl;
	}

	@Override
	public RoleInstantiation RoleInstantiation(RoleNode role)
	{
		RoleInstantiation ri = new RoleInstantiation(role);
		ri = del(ri, createDefaultDelegate());
		return ri;
	}

	@Override
	public ArgumentInstantiationList ArgumentInstantiationList(List<ArgumentInstantiation> ais)
	{
		ArgumentInstantiationList rdl = new ArgumentInstantiationList(ais);
		rdl = del(rdl, createDefaultDelegate());
		return rdl;
	}

	@Override
	public ArgumentInstantiation ArgumentInstantiation(ArgumentNode arg)
	{
		ArgumentInstantiation ri = new ArgumentInstantiation(arg);
		ri = del(ri, createDefaultDelegate());
		return ri;
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
			case OPERATOR:     snn = new OperatorNode(identifier);           break;
			case PARAMETER:    snn = new ParameterNode(identifier);          break;
			case RECURSIONVAR: snn = new RecursionVarNode(identifier);       break;
			case ROLE:         snn = new RoleNode(identifier);               break;
			case PROTOCOL:     snn = new SimpleProtocolNameNode(identifier); break;
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
			case MODULE:           qnn = new ModuleNameNode(elems); break;
			case PROTOCOL:         qnn = new ProtocolNameNode(elems); break;
			default: throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		qnn = (QualifiedNameNode) qnn.del(createDefaultDelegate());
		return qnn;
	}

	@Override
	public LocalProtocolDecl LocalProtocolDecl(LocalProtocolHeader header, LocalProtocolDefinition def)
	{
		LocalProtocolDecl lpd = new LocalProtocolDecl(header, def);
		lpd = del(lpd, new LocalProtocolDeclDelegate());
		return lpd;
	}

	@Override
	public LocalProtocolHeader LocalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls)
	{
		LocalProtocolHeader lph = new LocalProtocolHeader(name, roledecls, paramdecls);
		lph = del(lph, createDefaultDelegate());
		return lph;
	}

	@Override
	public SelfRoleDecl SelfRoleDecl(RoleNode namenode)
	{
		SelfRoleDecl rd = new SelfRoleDecl(namenode);
		rd = del(rd, new RoleDeclDelegate());
		return rd;
	}

	@Override
	public LocalProtocolDefinition LocalProtocolDefinition(LocalProtocolBlock block)
	{
		LocalProtocolDefinition lpd = new LocalProtocolDefinition(block);
		lpd = del(lpd, new LocalProtocolDefinitionDelegate());
		return lpd;
	}

	@Override
	public LocalProtocolBlock LocalProtocolBlock(LocalInteractionSequence seq)
	{
		LocalProtocolBlock lpb = new LocalProtocolBlock(seq);
		lpb = del(lpb, new LocalProtocolBlockDelegate());
		return lpb;
	}

	@Override
	public LocalInteractionSequence LocalInteractionSequence(List<LocalInteractionNode> actions)
	{
		LocalInteractionSequence lis = new LocalInteractionSequence(actions);
		lis = del(lis, new LocalInteractionSequenceDelegate());
		return lis;
	}

	@Override
	public LocalSend LocalSend(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LocalSend ls = new LocalSend(src, msg, dests);
		ls = del(ls, new LocalSendDelegate());
		return ls;
	}

	@Override
	public LocalReceive LocalReceive(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LocalReceive ls = new LocalReceive(src, msg, dests);
		ls = del(ls, new LocalReceiveDelegate());
		return ls;
	}

	@Override
	public LocalChoice LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks)
	{
		LocalChoice lc = new LocalChoice(subj, blocks);
		lc = del(lc, new LocalChoiceDelegate());
		return lc;
	}

	@Override
	public LocalRecursion LocalRecursion(RecursionVarNode recvar, LocalProtocolBlock block)
	{
		LocalRecursion lr = new LocalRecursion(recvar, block);
		lr = del(lr, new LocalRecursionDelegate());
		return lr;
	}

	@Override
	public LocalContinue LocalContinue(RecursionVarNode recvar)
	{
		LocalContinue lc = new LocalContinue(recvar);
		lc = del(lc, new LocalContinueDelegate());
		return lc;
	}

	/*@Override
	public LocalDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		return LocalDo(null, roleinstans, arginstans, proto);
	}*/

	@Override
	//public LocalDo LocalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	public LocalDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		//LocalDo ld = new LocalDo(scope, roleinstans, arginstans, proto);
		LocalDo ld = new LocalDo(roleinstans, arginstans, proto);
		//ld = del(ld, createDefaultDelegate());
		ld = del(ld, new LocalDoDelegate());
		return ld;
	}

	private ModelDelegate createDefaultDelegate()
	{
		return new DefaultModelDelegate();
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends ModelNodeBase> T del(T n, ModelDelegate del)
	{
		ModelNodeBase ret = n.del(del);
		if (ret.getClass() != n.getClass())
		{
			throw new RuntimeException("Shouldn't get in here: " + ret.getClass() + ", " + n.getClass());
		}
		return (T) ret;
	}
}

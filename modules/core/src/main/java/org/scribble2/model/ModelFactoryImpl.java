package org.scribble2.model;

import java.util.List;

import org.scribble2.model.del.DefaultModelDel;
import org.scribble2.model.del.ImportModuleDel;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.del.ModuleDel;
import org.scribble2.model.del.ParamDeclDel;
import org.scribble2.model.del.RoleDeclDel;
import org.scribble2.model.del.global.GChoiceDel;
import org.scribble2.model.del.global.GContinueDel;
import org.scribble2.model.del.global.GDoDel;
import org.scribble2.model.del.global.GInteractionSeqDel;
import org.scribble2.model.del.global.GMessageTransferDel;
import org.scribble2.model.del.global.GProtocolBlockDel;
import org.scribble2.model.del.global.GProtocolDeclDel;
import org.scribble2.model.del.global.GProtocolDefDel;
import org.scribble2.model.del.global.GRecursionDel;
import org.scribble2.model.del.local.LChoiceDel;
import org.scribble2.model.del.local.LContinueDel;
import org.scribble2.model.del.local.LDoDel;
import org.scribble2.model.del.local.LInteractionSeqDel;
import org.scribble2.model.del.local.LProtocolBlockDel;
import org.scribble2.model.del.local.LProtocolDeclDel;
import org.scribble2.model.del.local.LProtocolDefDel;
import org.scribble2.model.del.local.LReceiveDel;
import org.scribble2.model.del.local.LRecursionDel;
import org.scribble2.model.del.local.LSendDel;
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
import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.model.name.simple.RecVarNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.kind.Global;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.sesstype.kind.OperatorKind;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.kind.RecVarKind;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.Role;

public class ModelFactoryImpl implements ModelFactory
{
	public static final ModelFactory FACTORY = new ModelFactoryImpl();  // FIXME: move somewhere else
	
	@Override
	public MessageSigNode MessageSignatureNode(OperatorNode op, PayloadNode payload)
	{
		MessageSigNode msn = new MessageSigNode(op, payload);
		msn = del(msn, createDefaultDelegate());  // FIXME: does another shallow copy
		return msn;
	}

	@Override
	public PayloadNode Payload(List<PayloadElement> payloadelems)
	{
		PayloadNode p = new PayloadNode(payloadelems);
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
			//List<? extends ImportDecl> imports,
			List<ImportDecl> imports,
			List<NonProtocolDecl> data,
			//List<? extends AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos)
			List<ProtocolDecl<? extends org.scribble2.sesstype.kind.ProtocolKind>> protos)
	{
		Module module = new Module(moddecl, imports, data, protos);
		//module = del(module, new ModuleDelegate(module.getFullModuleName()));
		module = del(module, new ModuleDel());
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
		im = del(im, new ImportModuleDel());
		return im;
	}

	@Override
	public GProtocolDecl GlobalProtocolDecl(GProtocolHeader header, GProtocolDef def)
	{
		GProtocolDecl gpd = new GProtocolDecl(header, def);
		gpd = del(gpd, new GProtocolDeclDel());
		return gpd;
	}

	@Override
	public GProtocolHeader GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	{
		GProtocolHeader gph = new GProtocolHeader(name, roledecls, paramdecls);
		gph = del(gph, createDefaultDelegate());
		return gph;
	}

	@Override
	//public RoleDeclList RoleDeclList(List<RoleDecl> rds)
	public RoleDeclList RoleDeclList(List<HeaderParamDecl<Role, RoleKind>> rds)
	{
		RoleDeclList rdl = new RoleDeclList(rds);
		rdl = del(rdl, createDefaultDelegate());
		return rdl;
	}

	@Override
	public RoleDecl RoleDecl(RoleNode namenode)
	{
		RoleDecl rd = new RoleDecl(namenode);
		rd = del(rd, new RoleDeclDel());
		return rd;
	}

	@Override
	//public ParamDeclList ParameterDeclList(List<ParamDecl> pds)
	public ParamDeclList ParameterDeclList(List<HeaderParamDecl<Name<Kind>, Kind>> pds)
	{
		ParamDeclList pdl = new ParamDeclList(pds);
		pdl = del(pdl, createDefaultDelegate());
		return pdl;
	}

	@Override
	//public ParamDecl ParameterDecl(org.scribble2.model.ParamDecl.Kind kind, ParameterNode namenode)
	public <K extends Kind> ParamDecl<K> ParameterDecl(K kind, ParameterNode<K> namenode)
	//public <K extends Kind> ParamDecl<K> ParameterDecl(ParameterNode<K> namenode)
	{
		ParamDecl<K> pd = new ParamDecl<K>(kind, namenode);
		//ParamDecl<K> pd = new ParamDecl<K>(namenode);
		pd = del(pd, new ParamDeclDel());
		return pd;
	}

	@Override
	public GProtocolDef GlobalProtocolDefinition(GProtocolBlock block)
	{
		GProtocolDef gpd = new GProtocolDef(block);
		gpd = del(gpd, new GProtocolDefDel());
		return gpd;
	}

	@Override
	public GProtocolBlock GlobalProtocolBlock(GInteractionSeq seq)
	{
		GProtocolBlock gpb = new GProtocolBlock(seq);
		gpb = del(gpb, new GProtocolBlockDel());
		return gpb;
	}

	@Override
	public GInteractionSeq GlobalInteractionSequence(List<GInteractionNode> actions)
	{
		GInteractionSeq gis = new GInteractionSeq(actions);
		gis = del(gis, new GInteractionSeqDel());
		return gis;
	}

	@Override
	public GMessageTransfer GlobalMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		GMessageTransfer gmt = new GMessageTransfer(src, msg, dests);
		gmt = del(gmt, new GMessageTransferDel());
		return gmt;
	}

	@Override
	//public GlobalChoice GlobalChoice(RoleNode subj, List<GlobalProtocolBlock> blocks)
	public GChoice GlobalChoice(RoleNode subj, List<ProtocolBlock<Global>> blocks)
	{
		GChoice gc = new GChoice(subj, blocks);
		gc = del(gc, new GChoiceDel());
		return gc;
	}

	@Override
	//public GlobalRecursion GlobalRecursion(RecursionVarNode recvar, GlobalProtocolBlock block)
	public GRecursion GlobalRecursion(RecVarNode recvar, ProtocolBlock<Global> block)
	{
		GRecursion gr = new GRecursion(recvar, block);
		gr = del(gr, new GRecursionDel());
		return gr;
	}

	@Override
	public GContinue GlobalContinue(RecVarNode recvar)
	{
		GContinue gc = new GContinue(recvar);
		gc = del(gc, new GContinueDel());
		return gc;
	}

	/*@Override
	public GlobalDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		return GlobalDo(null, roleinstans, arginstans, proto);
	}*/

	@Override
	//public GlobalDo GlobalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	public GDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		//GlobalDo gd = new GlobalDo(scope, roleinstans, arginstans, proto);
		GDo gd = new GDo(roleinstans, arginstans, proto);
		//gd = del(gd, createDefaultDelegate());  // FIXME
		gd = del(gd, new GDoDel());  // FIXME
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
	//public SimpleNameNode SimpleNameNode(SIMPLE_NAME kind, String identifier)
	public <K extends Kind> NameNode<? extends Name<K>, K> SimpleNameNode(K kind, String identifier)
	{
		//SimpleNameNode snn;
		NameNode<? extends Name<? extends Kind>, ? extends Kind> snn;
		/*switch(kind)
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
		}*/
		if (kind.equals(OperatorKind.KIND))
		{
			snn = new OperatorNode(identifier);
		}
		else if (kind.equals(RecVarKind.KIND))
		{
			snn = new RecVarNode(identifier);
		}
		else if (kind.equals(RoleKind.KIND))
		{
			snn = new RoleNode(identifier);
		}
		else if (kind.equals(ProtocolKind.KIND))
		{
			snn = new SimpleProtocolNameNode(identifier);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return (NameNode<? extends Name<K>, K>) snn.del(createDefaultDelegate());
	}

	@Override
	//public QualifiedNameNode QualifiedNameNode(QUALIFIED_NAME kind, String... elems)
	public <K extends Kind> QualifiedNameNode<? extends Name<K>, K> QualifiedNameNode(K kind, String... elems)
	{
		QualifiedNameNode<? extends Name<? extends Kind>, ? extends Kind> qnn;
		/*switch(kind)
		{
			case MESSAGESIGNATURE: qnn = new MessageSignatureNameNode(elems); break;
			case MODULE:           qnn = new ModuleNameNode(elems); break;
			case PROTOCOL:         qnn = new ProtocolNameNode(elems); break;
			default: throw new RuntimeException("Shouldn't get in here: " + kind);
		}*/
		if (kind.equals(ModuleKind.KIND))
		{
			qnn = new ModuleNameNode(elems);
		}
		else if (kind.equals(ProtocolKind.KIND))
		{
			qnn = new ProtocolNameNode(elems);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return (QualifiedNameNode<? extends Name<K>, K>) qnn.del(createDefaultDelegate());
	}

	@Override
	public <K extends Kind> ParameterNode<K> ParameterNode(K kind, String identifier)
	{
		ParameterNode<K> pn = new ParameterNode<K>(kind, identifier);
		pn = del(pn, createDefaultDelegate());
		return pn;
	}

	@Override
	public LProtocolDecl LocalProtocolDecl(LProtocolHeader header, LProtocolDef def)
	{
		LProtocolDecl lpd = new LProtocolDecl(header, def);
		lpd = del(lpd, new LProtocolDeclDel());
		return lpd;
	}

	@Override
	public LProtocolHeader LocalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	{
		LProtocolHeader lph = new LProtocolHeader(name, roledecls, paramdecls);
		lph = del(lph, createDefaultDelegate());
		return lph;
	}

	@Override
	public SelfRoleDecl SelfRoleDecl(RoleNode namenode)
	{
		SelfRoleDecl rd = new SelfRoleDecl(namenode);
		rd = del(rd, new RoleDeclDel());
		return rd;
	}

	@Override
	public LProtocolDef LocalProtocolDefinition(LProtocolBlock block)
	{
		LProtocolDef lpd = new LProtocolDef(block);
		lpd = del(lpd, new LProtocolDefDel());
		return lpd;
	}

	@Override
	public LProtocolBlock LocalProtocolBlock(LInteractionSeq seq)
	{
		LProtocolBlock lpb = new LProtocolBlock(seq);
		lpb = del(lpb, new LProtocolBlockDel());
		return lpb;
	}

	@Override
	//public LocalInteractionSequence LocalInteractionSequence(List<LocalInteractionNode> actions)
	public LInteractionSeq LocalInteractionSequence(List<? extends InteractionNode<Local>> actions)
	{
		LInteractionSeq lis = new LInteractionSeq(actions);
		lis = del(lis, new LInteractionSeqDel());
		return lis;
	}

	@Override
	public LSend LocalSend(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LSend ls = new LSend(src, msg, dests);
		ls = del(ls, new LSendDel());
		return ls;
	}

	@Override
	public LReceive LocalReceive(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LReceive ls = new LReceive(src, msg, dests);
		ls = del(ls, new LReceiveDel());
		return ls;
	}

	@Override
	//public LocalChoice LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks)
	public LChoice LocalChoice(RoleNode subj, List<ProtocolBlock<Local>> blocks)
	{
		LChoice lc = new LChoice(subj, blocks);
		lc = del(lc, new LChoiceDel());
		return lc;
	}

	@Override
	public LRecursion LocalRecursion(RecVarNode recvar, LProtocolBlock block)
	{
		LRecursion lr = new LRecursion(recvar, block);
		lr = del(lr, new LRecursionDel());
		return lr;
	}

	@Override
	public LContinue LocalContinue(RecVarNode recvar)
	{
		LContinue lc = new LContinue(recvar);
		lc = del(lc, new LContinueDel());
		return lc;
	}

	/*@Override
	public LocalDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		return LocalDo(null, roleinstans, arginstans, proto);
	}*/

	@Override
	//public LocalDo LocalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	public LDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		//LocalDo ld = new LocalDo(scope, roleinstans, arginstans, proto);
		LDo ld = new LDo(roleinstans, arginstans, proto);
		//ld = del(ld, createDefaultDelegate());
		ld = del(ld, new LDoDel());
		return ld;
	}

	private ModelDel createDefaultDelegate()
	{
		return new DefaultModelDel();
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends ModelNodeBase> T del(T n, ModelDel del)
	{
		ModelNodeBase ret = n.del(del);
		if (ret.getClass() != n.getClass())
		{
			throw new RuntimeException("Shouldn't get in here: " + ret.getClass() + ", " + n.getClass());
		}
		return (T) ret;
	}
}

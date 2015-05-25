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
import org.scribble2.model.del.name.AmbigNameNodeDel;
import org.scribble2.model.del.name.ParamNodeDel;
import org.scribble2.model.del.name.RoleNodeDel;
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
import org.scribble2.model.name.PayloadElemNameNode;
import org.scribble2.model.name.qualified.DataTypeNameNode;
import org.scribble2.model.name.qualified.GProtocolNameNode;
import org.scribble2.model.name.qualified.LProtocolNameNode;
import org.scribble2.model.name.qualified.MessageSigNameNode;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.qualified.QualifiedNameNode;
import org.scribble2.model.name.simple.AmbigNameNode;
import org.scribble2.model.name.simple.NonRoleParamNode;
import org.scribble2.model.name.simple.OpNode;
import org.scribble2.model.name.simple.RecVarNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.kind.DataTypeKind;
import org.scribble2.sesstype.kind.Global;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.sesstype.kind.OpKind;
import org.scribble2.sesstype.kind.RecVarKind;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.kind.SigKind;

public class ModelFactoryImpl implements ModelFactory
{
	public static final ModelFactory FACTORY = new ModelFactoryImpl();  // FIXME: move somewhere else
	
	@Override
	public MessageSigNode MessageSigNode(OpNode op, PayloadElemList payload)
	{
		MessageSigNode msn = new MessageSigNode(op, payload);
		msn = del(msn, createDefaultDelegate());  // FIXME: does another shallow copy
		return msn;
	}

	@Override
	public PayloadElemList PayloadElemList(List<PayloadElem> payloadelems)
	{
		PayloadElemList p = new PayloadElemList(payloadelems);
		p = del(p, createDefaultDelegate());
		return p;
	}

	@Override
	public PayloadElem PayloadElement(PayloadElemNameNode name)
	{
		PayloadElem pe = new PayloadElem(name);
		pe = del(pe, createDefaultDelegate());
		return pe;
	}
	
	@Override
	public Module Module( 
			ModuleDecl moddecl,
			//List<? extends ImportDecl> imports,
			List<ImportDecl> imports,
			List<NonProtocolDecl<? extends Kind>> data,
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
	//public ImportModule ImportModule(ModuleNameNode modname, SimpleProtocolNameNode alias)
	public ImportModule ImportModule(ModuleNameNode modname, ModuleNameNode alias)
	{
		ImportModule im = new ImportModule(modname, alias);
		//im = del(im, createDefaultDelegate());
		im = del(im, new ImportModuleDel());
		return im;
	}
	
	@Override
	public MessageSigNameDecl MessageSigDecl(String schema, String extName, String source, MessageSigNameNode alias)
	{
		MessageSigNameDecl msd = new MessageSigNameDecl(schema, extName, source, alias);
		msd = del(msd, createDefaultDelegate());
		return msd;
	}

	@Override
	public DataTypeDecl DataTypeDecl(String schema, String extName, String source, DataTypeNameNode alias)
	{
		DataTypeDecl dtd = new DataTypeDecl(schema, extName, source, alias);
		dtd = del(dtd, createDefaultDelegate());
		return dtd;
	}

	@Override
	public GProtocolDecl GProtocolDecl(GProtocolHeader header, GProtocolDef def)
	{
		GProtocolDecl gpd = new GProtocolDecl(header, def);
		gpd = del(gpd, new GProtocolDeclDel());
		return gpd;
	}

	@Override
	//public GProtocolHeader GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	public GProtocolHeader GProtocolHeader(GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		GProtocolHeader gph = new GProtocolHeader(name, roledecls, paramdecls);
		gph = del(gph, createDefaultDelegate());
		return gph;
	}

	@Override
	//public RoleDeclList RoleDeclList(List<RoleDecl> rds)
	//public RoleDeclList RoleDeclList(List<HeaderParamDecl<Role, RoleKind>> rds)
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
		rd = del(rd, new RoleDeclDel());
		return rd;
	}

	@Override
	//public ParamDeclList ParameterDeclList(List<ParamDecl> pds)
	//public ParamDeclList ParameterDeclList(List<HeaderParamDecl<Name<Kind>, Kind>> pds)
	public NonRoleParamDeclList NonRoleParamDeclList(List<NonRoleParamDecl<Kind>> pds)
	{
		NonRoleParamDeclList pdl = new NonRoleParamDeclList(pds);
		pdl = del(pdl, createDefaultDelegate());
		return pdl;
	}

	@Override
	//public ParamDecl ParameterDecl(org.scribble2.model.ParamDecl.Kind kind, ParameterNode namenode)
	public <K extends Kind> NonRoleParamDecl<K> ParamDecl(K kind, NonRoleParamNode<K> namenode)
	//public <K extends Kind> ParamDecl<K> ParameterDecl(ParameterNode<K> namenode)
	{
		NonRoleParamDecl<K> pd = new NonRoleParamDecl<K>(kind, namenode);
		//ParamDecl<K> pd = new ParamDecl<K>(namenode);
		pd = del(pd, new ParamDeclDel());
		return pd;
	}

	@Override
	public GProtocolDef GProtocolDefinition(GProtocolBlock block)
	{
		GProtocolDef gpd = new GProtocolDef(block);
		gpd = del(gpd, new GProtocolDefDel());
		return gpd;
	}

	@Override
	public GProtocolBlock GProtocolBlock(GInteractionSeq seq)
	{
		GProtocolBlock gpb = new GProtocolBlock(seq);
		gpb = del(gpb, new GProtocolBlockDel());
		return gpb;
	}

	@Override
	public GInteractionSeq GInteractionSequence(List<GInteractionNode> actions)
	{
		GInteractionSeq gis = new GInteractionSeq(actions);
		gis = del(gis, new GInteractionSeqDel());
		return gis;
	}

	@Override
	public GMessageTransfer GMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		GMessageTransfer gmt = new GMessageTransfer(src, msg, dests);
		gmt = del(gmt, new GMessageTransferDel());
		return gmt;
	}

	@Override
	//public GlobalChoice GlobalChoice(RoleNode subj, List<GlobalProtocolBlock> blocks)
	public GChoice GChoice(RoleNode subj, List<ProtocolBlock<Global>> blocks)
	{
		GChoice gc = new GChoice(subj, blocks);
		gc = del(gc, new GChoiceDel());
		return gc;
	}

	@Override
	//public GlobalRecursion GlobalRecursion(RecursionVarNode recvar, GlobalProtocolBlock block)
	public GRecursion GRecursion(RecVarNode recvar, ProtocolBlock<Global> block)
	{
		GRecursion gr = new GRecursion(recvar, block);
		gr = del(gr, new GRecursionDel());
		return gr;
	}

	@Override
	public GContinue GContinue(RecVarNode recvar)
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
	//public GDo GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	public GDo GDo(RoleArgList roleinstans, NonRoleArgList arginstans, GProtocolNameNode proto)
	{
		//GlobalDo gd = new GlobalDo(scope, roleinstans, arginstans, proto);
		GDo gd = new GDo(roleinstans, arginstans, proto);
		//gd = del(gd, createDefaultDelegate());  // FIXME
		gd = del(gd, new GDoDel());  // FIXME
		return gd;
	}

	@Override
	public RoleArgList RoleArgList(List<RoleArg> ris)
	{
		RoleArgList rdl = new RoleArgList(ris);
		rdl = del(rdl, createDefaultDelegate());
		return rdl;
	}

	@Override
	public RoleArg RoleArg(RoleNode role)
	{
		RoleArg ri = new RoleArg(role);
		ri = del(ri, createDefaultDelegate());
		return ri;
	}

	@Override
	public NonRoleArgList NonRoleArgList(List<NonRoleArg> ais)
	{
		NonRoleArgList rdl = new NonRoleArgList(ais);
		rdl = del(rdl, createDefaultDelegate());
		return rdl;
	}

	@Override
	public NonRoleArg NonRoleArg(ArgNode arg)
	{
		NonRoleArg ri = new NonRoleArg(arg);
		ri = del(ri, createDefaultDelegate());
		return ri;
	}
	
	@Override
	//public SimpleNameNode SimpleNameNode(SIMPLE_NAME kind, String identifier)
	//public <K extends Kind> NameNode<? extends Name<K>, K> SimpleNameNode(K kind, String identifier)
	public <K extends Kind> NameNode<K> SimpleNameNode(K kind, String identifier)
	{
		//SimpleNameNode snn;
		//NameNode<? extends Name<? extends Kind>, ? extends Kind> snn;
		NameNode<? extends Kind> snn;
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
		if (kind.equals(OpKind.KIND))
		{
			snn = new OpNode(identifier);
		}
		else if (kind.equals(RecVarKind.KIND))
		{
			snn = new RecVarNode(identifier);
		}
		else if (kind.equals(RoleKind.KIND))
		{
			RoleNode rn = new RoleNode(identifier);
			rn = (RoleNode) del(rn, new RoleNodeDel());
			return castNameNode(kind, rn);
		}
		/*else if (kind.equals(ProtocolKind.KIND))
		{
			snn = new SimpleProtocolNameNode(identifier);
		}*/
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		//return castNameNode(kind, (NameNode<? extends Name<K>, K>) snn.del(createDefaultDelegate()));
		return castNameNode(kind, del(snn, createDefaultDelegate()));
	}
	
	/*private static <T extends NameNode<? extends Name<? extends Kind>, K>, K extends Kind> T
			castNameNode(K kind, NameNode<? extends Name<? extends Kind>, ? extends Kind> n)*/
	private static <T extends NameNode<K>, K extends Kind> T
			castNameNode(K kind, NameNode<? extends Kind> n)
	{
		if (!n.toName().kind.equals(kind))
		{
			throw new RuntimeException("Shouldn't get in here: " + kind + ", " + n);
		}
		@SuppressWarnings("unchecked")
		T tmp = (T) n;
		return tmp;
	}

	@Override
	//public QualifiedNameNode QualifiedNameNode(QUALIFIED_NAME kind, String... elems)
	//public <K extends Kind> QualifiedNameNode<? extends Name<K>, K> QualifiedNameNode(K kind, String... elems)
	public <K extends Kind> QualifiedNameNode<K> QualifiedNameNode(K kind, String... elems)
	{
		//QualifiedNameNode<? extends Name<? extends Kind>, ? extends Kind> qnn;
		QualifiedNameNode<? extends Kind> qnn;
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
		else if (kind.equals(Global.KIND))
		{
			qnn = new GProtocolNameNode(elems);
		}
		else if (kind.equals(Local.KIND))
		{
			qnn = new LProtocolNameNode(elems);
		}
		else if (kind.equals(SigKind.KIND))
		{
			qnn = new MessageSigNameNode(elems);
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			qnn = new DataTypeNameNode(elems);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castQualifiedNameNode(kind, del(qnn, createDefaultDelegate()));
	}

	/*private static <T extends QualifiedNameNode<? extends Name<? extends Kind>, K>, K extends Kind> T
			castQualifiedNameNode(K kind, QualifiedNameNode<? extends Name<? extends Kind>, ? extends Kind> n)*/
	private static <T extends QualifiedNameNode<K>, K extends Kind> T
			castQualifiedNameNode(K kind, QualifiedNameNode<? extends Kind> n)
	{
		if (!n.toName().kind.equals(kind))
		{
			throw new RuntimeException("Shouldn't get in here: " + kind + ", " + n);
		}
		@SuppressWarnings("unchecked")
		T tmp = (T) n;
		return tmp;
	}

	@Override
	public AmbigNameNode AmbiguousNameNode(String identifier)
	{
		AmbigNameNode ann = new AmbigNameNode(identifier); 
		ann = (AmbigNameNode) ann.del(new AmbigNameNodeDel());
		return ann;
	}

	@Override
	public <K extends Kind> NonRoleParamNode<K> NonRoleParamNode(K kind, String identifier)
	{
		NonRoleParamNode<K> pn = new NonRoleParamNode<K>(kind, identifier);
		pn = del(pn, new ParamNodeDel());
		return pn;
	}

	@Override
	public LProtocolDecl LProtocolDecl(LProtocolHeader header, LProtocolDef def)
	{
		LProtocolDecl lpd = new LProtocolDecl(header, def);
		lpd = del(lpd, new LProtocolDeclDel());
		return lpd;
	}

	@Override
	//public LProtocolHeader LocalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	public LProtocolHeader LProtocolHeader(LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
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
	public LProtocolDef LProtocolDefinition(LProtocolBlock block)
	{
		LProtocolDef lpd = new LProtocolDef(block);
		lpd = del(lpd, new LProtocolDefDel());
		return lpd;
	}

	@Override
	public LProtocolBlock LProtocolBlock(LInteractionSeq seq)
	{
		LProtocolBlock lpb = new LProtocolBlock(seq);
		lpb = del(lpb, new LProtocolBlockDel());
		return lpb;
	}

	@Override
	//public LocalInteractionSequence LocalInteractionSequence(List<LocalInteractionNode> actions)
	public LInteractionSeq LInteractionSequence(List<? extends InteractionNode<Local>> actions)
	{
		LInteractionSeq lis = new LInteractionSeq(actions);
		lis = del(lis, new LInteractionSeqDel());
		return lis;
	}

	@Override
	public LSend LSend(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LSend ls = new LSend(src, msg, dests);
		ls = del(ls, new LSendDel());
		return ls;
	}

	@Override
	public LReceive LReceive(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LReceive ls = new LReceive(src, msg, dests);
		ls = del(ls, new LReceiveDel());
		return ls;
	}

	@Override
	//public LocalChoice LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks)
	public LChoice LChoice(RoleNode subj, List<ProtocolBlock<Local>> blocks)
	{
		LChoice lc = new LChoice(subj, blocks);
		lc = del(lc, new LChoiceDel());
		return lc;
	}

	@Override
	public LRecursion LRecursion(RecVarNode recvar, LProtocolBlock block)
	{
		LRecursion lr = new LRecursion(recvar, block);
		lr = del(lr, new LRecursionDel());
		return lr;
	}

	@Override
	public LContinue LContinue(RecVarNode recvar)
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
	//public LDo LocalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	public LDo LDo(RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
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

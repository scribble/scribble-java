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
import org.scribble.ast.name.qualified.DataTypeNode;
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
import org.scribble.del.DefaultDel;
import org.scribble.del.ImportModuleDel;
import org.scribble.del.ModuleDel;
import org.scribble.del.NonRoleArgListDel;
import org.scribble.del.NonRoleParamDeclDel;
import org.scribble.del.NonRoleParamDeclListDel;
import org.scribble.del.RoleArgListDel;
import org.scribble.del.RoleDeclDel;
import org.scribble.del.RoleDeclListDel;
import org.scribble.del.ScribDel;
import org.scribble.del.global.GChoiceDel;
import org.scribble.del.global.GContinueDel;
import org.scribble.del.global.GDoDel;
import org.scribble.del.global.GInteractionSeqDel;
import org.scribble.del.global.GMessageTransferDel;
import org.scribble.del.global.GProtocolBlockDel;
import org.scribble.del.global.GProtocolDeclDel;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.del.global.GRecursionDel;
import org.scribble.del.local.LChoiceDel;
import org.scribble.del.local.LContinueDel;
import org.scribble.del.local.LDoDel;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.del.local.LProjectionDeclDel;
import org.scribble.del.local.LProtocolBlockDel;
import org.scribble.del.local.LProtocolDeclDel;
import org.scribble.del.local.LProtocolDefDel;
import org.scribble.del.local.LReceiveDel;
import org.scribble.del.local.LRecursionDel;
import org.scribble.del.local.LSendDel;
import org.scribble.del.name.AmbigNameNodeDel;
import org.scribble.del.name.DataTypeNodeDel;
import org.scribble.del.name.MessageSigNameNodeDel;
import org.scribble.del.name.ParamNodeDel;
import org.scribble.del.name.RecVarNodeDel;
import org.scribble.del.name.RoleNodeDel;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.OpKind;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;


public class AstFactoryImpl implements AstFactory
{
	public static final AstFactory FACTORY = new AstFactoryImpl();
	
	@Override
	public MessageSigNode MessageSigNode(OpNode op, PayloadElemList payload)
	{
		MessageSigNode msn = new MessageSigNode(op, payload);
		msn = del(msn, createDefaultDelegate());
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
	public PayloadElem PayloadElem(PayloadElemNameNode name)
	{
		PayloadElem pe = new PayloadElem(name);
		pe = del(pe, createDefaultDelegate());
		return pe;
	}
	
	@Override
	public Module Module(ModuleDecl moddecl, List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data, List<ProtocolDecl<?>> protos)
	{
		Module module = new Module(moddecl, imports, data, protos);
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
	public ImportModule ImportModule(ModuleNameNode modname, ModuleNameNode alias)
	{
		ImportModule im = new ImportModule(modname, alias);
		im = del(im, new ImportModuleDel());
		return im;
	}
	
	@Override
	public MessageSigNameDecl MessageSigNameDecl(String schema, String extName, String source, MessageSigNameNode alias)
	{
		MessageSigNameDecl msd = new MessageSigNameDecl(schema, extName, source, alias);
		msd = del(msd, createDefaultDelegate());
		return msd;
	}

	@Override
	public DataTypeDecl DataTypeDecl(String schema, String extName, String source, DataTypeNode alias)
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
	public GProtocolHeader GProtocolHeader(GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		GProtocolHeader gph = new GProtocolHeader(name, roledecls, paramdecls);
		gph = del(gph, createDefaultDelegate());
		return gph;
	}

	@Override
	public RoleDeclList RoleDeclList(List<RoleDecl> rds)
	{
		RoleDeclList rdl = new RoleDeclList(rds);
		rdl = del(rdl, new RoleDeclListDel());
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
	public NonRoleParamDeclList NonRoleParamDeclList(List<NonRoleParamDecl<NonRoleParamKind>> pds)
	{
		NonRoleParamDeclList pdl = new NonRoleParamDeclList(pds);
		pdl = del(pdl, new NonRoleParamDeclListDel());
		return pdl;
	}

	@Override
	public <K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(K kind, NonRoleParamNode<K> namenode)
	{
		NonRoleParamDecl<K> pd = new NonRoleParamDecl<K>(kind, namenode);
		pd = del(pd, new NonRoleParamDeclDel());
		return pd;
	}

	@Override
	public GProtocolDef GProtocolDef(GProtocolBlock block)
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
	public GInteractionSeq GInteractionSeq(List<GInteractionNode> actions)
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
	public GChoice GChoice(RoleNode subj, List<GProtocolBlock> blocks)
	{
		GChoice gc = new GChoice(subj, blocks);
		gc = del(gc, new GChoiceDel());
		return gc;
	}

	@Override
	public GRecursion GRecursion(RecVarNode recvar, GProtocolBlock block)
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

	@Override
	public GDo GDo(RoleArgList roleinstans, NonRoleArgList arginstans, GProtocolNameNode proto)
	{
		GDo gd = new GDo(roleinstans, arginstans, proto);
		gd = del(gd, new GDoDel());
		return gd;
	}

	@Override
	public RoleArgList RoleArgList(List<RoleArg> ris)
	{
		RoleArgList rdl = new RoleArgList(ris);
		rdl = del(rdl, new RoleArgListDel());
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
		rdl = del(rdl, new NonRoleArgListDel());
		return rdl;
	}

	@Override
	public NonRoleArg NonRoleArg(NonRoleArgNode arg)
	{
		NonRoleArg ri = new NonRoleArg(arg);
		ri = del(ri, createDefaultDelegate());
		return ri;
	}
	
	@Override
	public <K extends Kind> NameNode<K> SimpleNameNode(K kind, String identifier)
	{
		NameNode<? extends Kind> snn = null;
		if (kind.equals(RecVarKind.KIND))
		{
			snn = new RecVarNode(identifier);
			snn = del(snn, new RecVarNodeDel());
		}
		else if (kind.equals(RoleKind.KIND))
		{
			snn = new RoleNode(identifier);
			snn = del(snn, new RoleNodeDel());
		}
		if (snn != null)
		{
			return castNameNode(kind, snn);
		}

		if (kind.equals(OpKind.KIND))
		{
			snn = new OpNode(identifier);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castNameNode(kind, del(snn, createDefaultDelegate()));
	}

	@Override
	public <K extends Kind> QualifiedNameNode<K> QualifiedNameNode(K kind, String... elems)
	{
		QualifiedNameNode<? extends Kind> qnn = null;
		if (kind.equals(SigKind.KIND))
		{
			qnn = new MessageSigNameNode(elems);
			qnn = del(qnn, new MessageSigNameNodeDel());
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			qnn = new DataTypeNode(elems);
			qnn = del(qnn, new DataTypeNodeDel());
		}
		if (qnn != null)
		{
			return castNameNode(kind, qnn);
		}

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
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castNameNode(kind, del(qnn, createDefaultDelegate()));
	}
	
	private static <T extends NameNode<K>, K extends Kind> T castNameNode(K kind, NameNode<? extends Kind> n)
	{
		if (!n.toName().getKind().equals(kind))
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
	public <K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(K kind, String identifier)
	{
		NonRoleParamNode<K> pn = new NonRoleParamNode<K>(kind, identifier);
		pn = del(pn, new ParamNodeDel());
		return pn;
	}

	@Override
	public DummyProjectionRoleNode DummyProjectionRoleNode()
	{
		DummyProjectionRoleNode dprn = new DummyProjectionRoleNode();
		dprn = (DummyProjectionRoleNode) dprn.del(createDefaultDelegate());
		return dprn;
	}

	@Override
	public LProtocolDecl LProtocolDecl(LProtocolHeader header, LProtocolDef def)
	{
		LProtocolDecl lpd = new LProtocolDecl(header, def);
		lpd = del(lpd, new LProtocolDeclDel());
		return lpd;
	}

	@Override
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
	public LProtocolDef LProtocolDef(LProtocolBlock block)
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
	public LInteractionSeq LInteractionSeq(List<LInteractionNode> actions)
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
	public LChoice LChoice(RoleNode subj, List<LProtocolBlock> blocks)
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

	@Override
	public LDo LDo(RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		LDo ld = new LDo(roleinstans, arginstans, proto);
		ld = del(ld, new LDoDel());
		return ld;
	}

	private ScribDel createDefaultDelegate()
	{
		return new DefaultDel();
	}
	
	// FIXME: factor out
	//@SuppressWarnings("unchecked")
	private static <T extends ScribNodeBase> T del(T n, ScribDel del)
	{
		/*ScribNodeBase ret = n.del(del);  // FIXME: del makes another shallow copy of n
		if (ret.getClass() != n.getClass())
		{
			throw new RuntimeException("Shouldn't get in here: " + ret.getClass() + ", " + n.getClass());
		}
		return (T) ret;*/
		return ScribNodeBase.del(n, del);
	}

	@Override
	public LProtocolDecl LProjectionDecl(GProtocolName fullname, Role self, LProtocolHeader header, LProtocolDef def)  // del extends that of LProtocolDecl 
	{
		LProtocolDecl lpd = new LProtocolDecl(header, def);
		lpd = ScribNodeBase.del(lpd, new LProjectionDeclDel(fullname, self));
		return lpd;
	}
}

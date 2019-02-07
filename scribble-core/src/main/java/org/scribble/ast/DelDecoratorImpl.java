/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.ast;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.DefaultDel;
import org.scribble.del.ImportModuleDel;
import org.scribble.del.ModuleDel;
import org.scribble.del.NonRoleParamDeclListDel;
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
import org.scribble.del.name.DataTypeNodeDel;
import org.scribble.del.name.MessageSigNameNodeDel;
import org.scribble.del.name.RecVarNodeDel;
import org.scribble.del.name.RoleNodeDel;


// ast package to access protected non-defensive del setter
public class DelDecoratorImpl implements DelDecorator
{
	public DelDecoratorImpl()
	{
		
	}
	
	// TODO: refactor, cf. AntlrToScribParserUtil
	// Visitor enter/leave framework uses dels -- DelDecorator is a proto visitor (akin to parsing)
	public void decorate(CommonTree n)
	{
		String type = n.getToken().getText();  // CHECKME: do by Antlr Token, or at this point by Scib AST type?
		switch (type)
		{
			case "MODULE":
				Module((Module) n);
				break;
			case "MODULEDECL":
				ModuleDecl((ModuleDecl) n);
				break;

			//case IMPORTMODULE:

			/*case MESSAGESIGNATUREDECL:      return AntlrMessageSigDecl.parseMessageSigDecl(this, ct, af);
			case PAYLOADTYPEDECL:           return AntlrDataTypeDecl.parseDataTypeDecl(this, ct, af);*/

			case "GLOBALPROTOCOLDECL":
				GProtocolDecl((GProtocolDecl) n);
				break;
			case "ROLEDECLLIST":
				RoleDeclList((RoleDeclList) n);
				break;
			case "ROLEDECL":
				RoleDecl((RoleDecl) n);
				break;
			case "PARAMETERDECLLIST":
			//case EMPTY_PARAMETERDECLLST:
				NonRoleParamDeclList((NonRoleParamDeclList) n);
				break;
			//case PARAMETERDECL:             return AntlrNonRoleParamDecl.parseNonRoleParamDecl(this, ct, af);*/

			case "GLOBALPROTOCOLHEADER":
				GProtocolHeader((GProtocolHeader) n);
				break;
			case "GPROTOCOLNAME":
				GProtocolNameNode((GProtocolNameNode) n);
				break;
			case "GLOBALPROTOCOLDEF":
				GProtocolDef((GProtocolDef) n);
				break;
			case "GLOBALPROTOCOLBLOCK":
				GProtocolBlock((GProtocolBlock) n);
				break;
			case "GLOBALINTERACTIONSEQUENCE":
				GInteractionSeq((GInteractionSeq) n);
				break;

			case "MESSAGESIGNATURE":
				MessageSigNode((MessageSigNode) n);
				break;
			case "PAYLOAD":
				PayloadElemList((PayloadElemList) n);
				break;

			/*case ROLEINSTANTIATIONLIST:     return AntlrRoleArgList.parseRoleArgList(this, ct, af);
			case ROLEINSTANTIATION:         return AntlrRoleArg.parseRoleArg(this, ct, af);
			case ARGUMENTINSTANTIATIONLIST: return AntlrNonRoleArgList.parseNonRoleArgList(this, ct, af);*/

			case "GLOBALMESSAGETRANSFER":
				GMessageTransfer((GMessageTransfer) n);
				break;
			/*case GLOBALCONNECT:             return AntlrGConnect.parseGConnect(this, ct, af);
			case GLOBALDISCONNECT:          return AntlrGDisconnect.parseGDisconnect(this, ct, af);
			case GLOBALWRAP:                return AntlrGWrap.parseGWrap(this, ct, af);*/
			case "GLOBALCHOICE":              
				GChoice((GChoice) n);
				break;
			case "GLOBALRECURSION":
				GRecursion((GRecursion) n);
				break;
			case "GLOBALCONTINUE":
				GContinue((GContinue) n);
				break;
			case "GLOBALDO":
				GDo((GDo) n);
				break;
				
			case "ROLENAME":
				RoleNode((RoleNode) n);
				break;
			case "RECURSIONVAR":
				RecVarNode((RecVarNode) n);
				break;
			case "OPNAME":
				OpNode((OpNode) n);
				break;
			case "MODULENAME":
				
				//System.out.println("5555b: " + n.getClass() + " ,, " + type + " ,, " + n);
				//System.out.println("4444:" + n + " ,, " + n.getChildren());
				
				ModuleNameNode((ModuleNameNode) n);
				break;

			default:  // Leaf "ID" nodes
				//throw new RuntimeException("Unknown ANTLR node type: " + type);
				//System.out.println("9999:" + n + " ,, " + n.getChildren());
				break;
		}

				//System.out.println("8888:" + n + " ,, " + n.getChildren());
		((List<?>) n.getChildren()).stream().map(x -> (CommonTree) x)
				.forEach(x -> decorate(x));
	}

	/*@Override
	public GDelegationElem GDelegationElem(CommonTree source, GProtocolNameNode proto, RoleNode role)
	{
		GDelegationElem de = new GDelegationElem(source, proto, role);
		//de = del(de, createDefaultDelegate());
		de = setDel(de, new GDelegationElemDel());  // FIXME: GDelegationElemDel
		return de;
	}

	@Override
	public LDelegationElem LDelegationElem(CommonTree source, LProtocolNameNode proto)
	{
		LDelegationElem de = new LDelegationElem(source, proto);
		de = setDel(de, createDefaultDelegate());
		return de;
	}*/
	
	@Override
	public void Module(Module m)
	{
		setDel(m, new ModuleDel());
	}

	@Override
	public void ModuleDecl(ModuleDecl md)
	{
		setDel(md, createDefaultDelegate());
	}

	@Override
	public void ImportModule(ImportModule im)
	{
		setDel(im, new ImportModuleDel());
	}
	
	/*@Override
	public MessageSigNameDecl MessageSigNameDecl(CommonTree source, String schema, String extName, String extSource, MessageSigNameNode alias)
	{
		MessageSigNameDecl msd = new MessageSigNameDecl(source, schema, extName, extSource, alias);
		msd = setDel(msd, createDefaultDelegate());
		return msd;
	}

	@Override
	public DataTypeDecl DataTypeDecl(CommonTree source, String schema, String extName, String extSource, DataTypeNode alias)
	{
		DataTypeDecl dtd = new DataTypeDecl(source, schema, extName, extSource, alias);
		dtd = setDel(dtd, createDefaultDelegate());
		return dtd;
	}*/

	@Override
	public void GProtocolDecl(GProtocolDecl gpd)
	{
		setDel(gpd, new GProtocolDeclDel());
	}

	@Override
	public void GProtocolHeader(GProtocolHeader gph)
	{
		setDel(gph, createDefaultDelegate());
	}

	@Override
	public void RoleDeclList(RoleDeclList rds)
	{
		setDel(rds, new RoleDeclListDel());
	}

	@Override
	public void RoleDecl(RoleDecl rd)
	{
		setDel(rd, new RoleDeclDel());
	}

	@Override
	public void NonRoleParamDeclList(NonRoleParamDeclList pds)
	{
		setDel(pds, new NonRoleParamDeclListDel());
	}

	/*@Override
	public <K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(CommonTree source, K kind, NonRoleParamNode<K> namenode)
	{
		NonRoleParamDecl<K> pd = new NonRoleParamDecl<K>(source, kind, namenode);
		pd = setDel(pd, new NonRoleParamDeclDel());
		return pd;
	}*/

	@Override
	public void GProtocolDef(GProtocolDef gpd)
	{
		setDel(gpd, new GProtocolDefDel());
	}

	@Override
	public void GProtocolBlock(GProtocolBlock gpb)
	{
		setDel(gpb, new GProtocolBlockDel());
	}

	@Override
	public void GInteractionSeq(GInteractionSeq gis)
	{
		setDel(gis, new GInteractionSeqDel());
	}

	@Override
	public void GMessageTransfer(GMessageTransfer gmt)
	{
		setDel(gmt, new GMessageTransferDel());
	}

	/*@Override
	public GConnect GConnect(GConnect gc)
	{
		GConnect gc = new GConnect(source, src, msg, dest);
		gc = setDel(gc, new GConnectDel());
		return gc;
	}

	@Override
	public GDisconnect GDisconnect(CommonTree source, RoleNode src, RoleNode dest)
	{
		GDisconnect gc = new GDisconnect(source, //UnitMessageSigNode(), 
				src, dest);
		gc = setDel(gc, new GDisconnectDel());
		return gc;
	}

	@Override
	public GWrap GWrap(CommonTree source, RoleNode src, RoleNode dest)
	{
		GWrap gw = new GWrap(source, UnitMessageSigNode(), src, dest);
		gw = setDel(gw, new GWrapDel());
		return gw;
	}*/

	@Override
	public void GChoice(GChoice gc)
	{
		setDel(gc, new GChoiceDel());
	}

	@Override
	public void GRecursion(GRecursion gr)
	{
		setDel(gr, new GRecursionDel());
	}

	@Override
	public void GContinue(GContinue gc)
	{
		setDel(gc, new GContinueDel());
	}

	@Override
	public void GDo(GDo gd)
	{
		setDel(gd, new GDoDel());
	}

	@Override
	public void MessageSigNode(MessageSigNode mn)
	{
		setDel(mn, createDefaultDelegate());
	}

	@Override
	public void PayloadElemList(PayloadElemList pay)
	{
		setDel(pay, createDefaultDelegate());
	}

	/*@Override
	public <K extends PayloadTypeKind> UnaryPayloadElem<K> UnaryPayloadElem(CommonTree source, PayloadElemNameNode<K> name)
	{
		UnaryPayloadElem<K> de= new UnaryPayloadElem<>(source, name);
		de = del(de, createDefaultDelegate());
		return de;
	}*/

	/*@Override
	public RoleArgList RoleArgList(CommonTree source, List<RoleArg> ris)
	{
		RoleArgList rdl = new RoleArgList(source, ris);
		rdl = setDel(rdl, new RoleArgListDel());
		return rdl;
	}

	@Override
	public RoleArg RoleArg(CommonTree source, RoleNode role)
	{
		RoleArg ri = new RoleArg(source, role);
		ri = setDel(ri, createDefaultDelegate());
		return ri;
	}

	@Override
	public NonRoleArgList NonRoleArgList(CommonTree source, List<NonRoleArg> ais)
	{
		NonRoleArgList rdl = new NonRoleArgList(source, ais);
		rdl = setDel(rdl, new NonRoleArgListDel());
		return rdl;
	}

	@Override
	public NonRoleArg NonRoleArg(CommonTree source, NonRoleArgNode arg)
	{
		NonRoleArg ri = new NonRoleArg(source, arg);
		ri = setDel(ri, createDefaultDelegate());
		return ri;
	}*/
	
	/*@Override
	public <K extends Kind> NameNode<K> SimpleNameNode(CommonTree source, K kind, String identifier)
	{
		NameNode<? extends Kind> snn = null;
		
		// "Custom" del's
		if (kind.equals(RecVarKind.KIND))
		{
			snn = new RecVarNode(source, identifier);
			snn = setDel(snn, new RecVarNodeDel());
		}
		else if (kind.equals(RoleKind.KIND))
		{
			snn = new RoleNode(source, identifier);
			snn = setDel(snn, new RoleNodeDel());
		}
		if (snn != null)
		{
			return castNameNode(kind, snn);
		}

		// Default del's
		if (kind.equals(OpKind.KIND))
		{
			snn = new OpNode(source, identifier);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castNameNode(kind, setDel(snn, createDefaultDelegate()));
	}*/

	@Override
	public void RoleNode(RoleNode r)
	{
		setDel(r, new RoleNodeDel());
	}

	@Override
	public void RecVarNode(RecVarNode rv)
	{
		setDel(rv, new RecVarNodeDel());
	}

	@Override
	public void OpNode(OpNode op)
	{
		setDel(op, createDefaultDelegate());
	}

	@Override
	public void MessageSigNameNode(MessageSigNameNode mn)
	{
		setDel(mn, new MessageSigNameNodeDel());
	}

	@Override
	public void DataTypeNode(DataTypeNode dn)
	{
		setDel(dn, new DataTypeNodeDel());
	}

	@Override
	public void ModuleNameNode(ModuleNameNode mn)
	{
		setDel(mn, createDefaultDelegate());
	}

	@Override
	public void GProtocolNameNode(GProtocolNameNode gpn)
	{
		setDel(gpn, createDefaultDelegate());
	}

	@Override
	public void LProtocolNameNode(LProtocolNameNode lpn)
	{
		setDel(lpn, createDefaultDelegate());
	}

	/*@Override
	public <K extends Kind> QualifiedNameNode<K> QualifiedNameNode(CommonTree source, K kind, String... elems)
	{
		QualifiedNameNode<? extends Kind> qnn = null;
		if (kind.equals(SigKind.KIND))
		{
			qnn = new MessageSigNameNode(source, elems);
			qnn = setDel(qnn, new MessageSigNameNodeDel());
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			qnn = new DataTypeNode(source, elems);
			qnn = setDel(qnn, new DataTypeNodeDel());
		}
		if (qnn != null)
		{
			return castNameNode(kind, qnn);
		}

		if (kind.equals(ModuleKind.KIND))
		{
			qnn = new ModuleNameNode(source, elems);
		}
		else if (kind.equals(Global.KIND))
		{
			qnn = new GProtocolNameNode(source, elems);
		}
		else if (kind.equals(Local.KIND))
		{
			qnn = new LProtocolNameNode(source,elems);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
		return castNameNode(kind, setDel(qnn, createDefaultDelegate()));
	}
	
	protected static <T extends NameNode<K>, K extends Kind> T castNameNode(K kind, NameNode<? extends Kind> n)
	{
		if (!n.toName().getKind().equals(kind))
		{
			throw new RuntimeException("Shouldn't get in here: " + kind + ", " + n);
		}
		@SuppressWarnings("unchecked")
		T tmp = (T) n;
		return tmp;
	}*/

	/*@Override
	public AmbigNameNode AmbiguousNameNode(CommonTree source, String identifier)
	{
		AmbigNameNode ann = new AmbigNameNode(source, identifier); 
		ann = (AmbigNameNode) ann.del(new AmbigNameNodeDel());
		return ann;
	}

	@Override
	public <K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(CommonTree source, K kind, String identifier)
	{
		NonRoleParamNode<K> pn = new NonRoleParamNode<K>(source, kind, identifier);
		pn = setDel(pn, new ParamNodeDel());
		return pn;
	}

	@Override
	public DummyProjectionRoleNode DummyProjectionRoleNode()
	{
		DummyProjectionRoleNode dprn = new DummyProjectionRoleNode();
		dprn = (DummyProjectionRoleNode) dprn.del(createDefaultDelegate());
		return dprn;
	}*/

	/*@Override  // Called from LProtocolDecl::clone, but currently never used  -- local proto decls only projected, not parsed
	public LProtocolDecl LProtocolDecl(CommonTree source, List<ProtocolDecl.Modifiers> mods, LProtocolHeader header, LProtocolDef def)
	{
		LProtocolDecl lpd = new LProtocolDecl(source, mods, header, def);
		lpd = setDel(lpd, new LProtocolDeclDel());
		return lpd;
	}

	@Override
	public LProjectionDecl LProjectionDecl(CommonTree source, List<ProtocolDecl.Modifiers> mods, GProtocolName fullname, Role self, LProtocolHeader header, LProtocolDef def)  // del extends that of LProtocolDecl 
	{
		LProjectionDecl lpd = new LProjectionDecl(source, mods, header, def);
		lpd = ScribNodeBase.setDel(lpd, new LProjectionDeclDel(fullname, self));
		return lpd;
	}

	@Override
	public LProtocolHeader LProtocolHeader(CommonTree source, LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		LProtocolHeader lph = new LProtocolHeader(source, name, roledecls, paramdecls);
		lph = setDel(lph, createDefaultDelegate());
		return lph;
	}

	@Override
	public SelfRoleDecl SelfRoleDecl(CommonTree source, RoleNode namenode)
	{
		SelfRoleDecl rd = new SelfRoleDecl(source, namenode);
		rd = setDel(rd, new RoleDeclDel());
		return rd;
	}

	@Override
	public LProtocolDef LProtocolDef(CommonTree source, LProtocolBlock block)
	{
		LProtocolDef lpd = new LProtocolDef(source, block);
		lpd = setDel(lpd, new LProtocolDefDel());
		return lpd;
	}

	@Override
	public LProtocolBlock LProtocolBlock(CommonTree source, LInteractionSeq seq)
	{
		LProtocolBlock lpb = new LProtocolBlock(source, seq);
		lpb = setDel(lpb, new LProtocolBlockDel());
		return lpb;
	}

	@Override
	public LInteractionSeq LInteractionSeq(CommonTree source, List<LInteractionNode> actions)
	{
		LInteractionSeq lis = new LInteractionSeq(source, actions);
		lis = setDel(lis, new LInteractionSeqDel());
		return lis;
	}

	@Override
	public LSend LSend(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LSend ls = new LSend(source, src, msg, dests);
		ls = setDel(ls, new LSendDel());
		return ls;
	}

	@Override
	public LReceive LReceive(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LReceive ls = new LReceive(source, src, msg, dests);
		ls = setDel(ls, new LReceiveDel());
		return ls;
	}
	
	@Override
	public LRequest LRequest(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect LConnect(RoleNode src, RoleNode dest)
	{
		LRequest lc = new LRequest(source, src, msg, dest);
		//LConnect lc = new LConnect(src, dest);
		lc = setDel(lc, new LRequestDel());
		return lc;
	}

	@Override
	public LAccept LAccept(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public LAccept LAccept(RoleNode src, RoleNode dest)
	{
		LAccept la = new LAccept(source, src, msg, dest);
		//LAccept la = new LAccept(src, dest);
		la = setDel(la, new LAcceptDel());
		return la;
	}

	@Override
	public LDisconnect LDisconnect(CommonTree source, RoleNode self, RoleNode peer)
	{
		LDisconnect lc = new LDisconnect(source, UnitMessageSigNode(), self, peer);
		lc = setDel(lc, new LDisconnectDel());
		return lc;
	}

	@Override
	public LWrapClient LWrapClient(CommonTree source, RoleNode self, RoleNode peer)
	{
		LWrapClient lwc = new LWrapClient(source, UnitMessageSigNode(), self, peer);
		lwc = setDel(lwc, new LWrapClientDel());
		return lwc;
	}

	@Override
	public LWrapServer LWrapServer(CommonTree source, RoleNode self, RoleNode peer)
	{
		LWrapServer lws = new LWrapServer(source, UnitMessageSigNode(), self, peer);
		lws = setDel(lws, new LWrapServerDel());
		return lws;
	}

	@Override
	public LChoice LChoice(CommonTree source, RoleNode subj, List<LProtocolBlock> blocks)
	{
		LChoice lc = new LChoice(source, subj, blocks);
		lc = setDel(lc, new LChoiceDel());
		return lc;
	}

	@Override
	public LRecursion LRecursion(CommonTree source, RecVarNode recvar, LProtocolBlock block)
	{
		LRecursion lr = new LRecursion(source, recvar, block);
		lr = setDel(lr, new LRecursionDel());
		return lr;
	}

	@Override
	public LContinue LContinue(CommonTree source, RecVarNode recvar)
	{
		LContinue lc = new LContinue(source, recvar);
		lc = setDel(lc, new LContinueDel());
		return lc;
	}

	@Override
	public LDo LDo(CommonTree source, RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		LDo ld = new LDo(source, roleinstans, arginstans, proto);
		ld = setDel(ld, new LDoDel());
		return ld;
	}*/

	protected ScribDel createDefaultDelegate()
	{
		return new DefaultDel();
	}
	
	// Non-defensive
	//protected static <T extends ScribNodeBase> T setDel(T n, ScribDel del)
	protected static void setDel(ScribNodeBase n, ScribDel del)
	{
		n.setDel(del);
		//return n;
		/*// Defensive helper with cast check
		ScribNodeBase copy = ((ScribNodeBase) n).clone();  // Need deep clone, since children have parent field
		//copy.del = del;
		copy.setDel(del);
		return ScribUtil.castNodeByClass(n, copy);*/
	}
}

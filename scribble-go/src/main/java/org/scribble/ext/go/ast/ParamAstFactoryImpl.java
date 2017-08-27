package org.scribble.ext.go.ast;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.RoleDeclDel;
import org.scribble.del.global.GMessageTransferDel;
import org.scribble.ext.go.ast.global.ParamGCrossMessageTransfer;
import org.scribble.ext.go.ast.global.ParamGDotMessageTransfer;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexVar;


public class ParamAstFactoryImpl extends AstFactoryImpl implements ParamAstFactory
{
	
	// Instantiating existing node classes with new dels

	/*@Override
	public GProtocolDecl GProtocolDecl(CommonTree source, List<GProtocolDecl.Modifiers> mods, GProtocolHeader header, GProtocolDef def)
	{
		GProtocolDecl gpd = new GProtocolDecl(source, mods, header, def);
		gpd = del(gpd, new AssrtGProtocolDeclDel());
		return gpd;
	}

	@Override
	public GProtocolDef GProtocolDef(CommonTree source, GProtocolBlock block)
	{
		GProtocolDef gpd = new GProtocolDef(source, block);
		gpd = del(gpd, new AssrtGProtocolDefDel());  // Uses header annot to do AssrtAnnotationChecker Def enter/exit
		return gpd;
	}
	
	@Override
	public GProtocolBlock GProtocolBlock(CommonTree source, GInteractionSeq seq)
	{
		GProtocolBlock gpb = new GProtocolBlock(source, seq);
		gpb = del(gpb, new AssrtGProtocolBlockDel());
		return gpb;
	}

	@Override
	public GChoice GChoice(CommonTree source, RoleNode subj, List<GProtocolBlock> blocks)
	{
		GChoice gc = new GChoice(source, subj, blocks);
		gc = del(gc, new AssrtGChoiceDel());
		return gc;
	}

	/*@Override
	public GRecursion GRecursion(CommonTree source, RecVarNode recvar, GProtocolBlock block)
	{
		GRecursion gr = new GRecursion(source, recvar, block);
		gr = del(gr, new AssrtGRecursionDel());
		return gr;
	}* /

	@Override
	public AmbigNameNode AmbiguousNameNode(CommonTree source, String identifier)
	{
		AmbigNameNode ann = new AmbigNameNode(source, identifier); 
		ann = (AmbigNameNode) ann.del(new AssrtAmbigNameNodeDel());
		return ann;
	}

	@Override
	public LProjectionDecl LProjectionDecl(CommonTree source, List<ProtocolDecl.Modifiers> mods, GProtocolName fullname, Role self, LProtocolHeader header, LProtocolDef def)
	{
		LProjectionDecl lpd = new LProjectionDecl(source, mods, header, def);
		lpd = del(lpd, new AssrtLProjectionDeclDel(fullname, self));
		return lpd;
	}

	@Override
	public LProtocolDef LProtocolDef(CommonTree source, LProtocolBlock block)
	{
		LProtocolDef lpd = new LProtocolDef(source, block);
		lpd = del(lpd, new AssrtLProtocolDefDel());
		return lpd;
	}

	
	@Override
	public LReceive LReceive(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		LReceive ls = new LReceive(source, src, msg, dests);  // FIXME: AssrtLReceive with assertion?
		ls = del(ls, new AssrtLReceiveDel());
		return ls;
	}

	@Override
	public LProtocolBlock LProtocolBlock(CommonTree source, LInteractionSeq seq)
	{
		LProtocolBlock lpb = new LProtocolBlock(source, seq);
		lpb = del(lpb, new AssrtLProtocolBlockDel());
		return lpb;
	}

	/* // Cf. GMessageTransfer -- empty-annotation sends still created as AssrtLSend, with null assertion -- but AssrtLSendDel still needed
	 * // No: not needed, because all globals made as Assrts, so projections are always Assrts
	@Override
	public AssrtLSend LSend(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		//LSend ls = new LSend(source, src, msg, dests);
		AssrtLSend ls = new AssrtLSend(source, src, msg, dests);
		ls = del(ls, new AssrtLSendDel());
		return ls;
	}*/

	/*@Override
	public AssrtLConnect LConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dests)
	{
		AssrtLConnect ls = new AssrtLConnect(source, src, msg, dests);
		ls = del(ls, new AssrtLConnectDel());
		return ls;
	}* /
	
	
	// Returning new node classes in place of existing

	// Still used by parsing for empty annotation/assertion nodes -- but we return an Assrt node
	// Easier to make all global as Assrt nodes, to avoid cast checks in, e.g., AssrtGProtocolDeclDel::leaveProjection (for GProtocolHeader), and so all projections will be Assrt kinds only

	@Override
	public AssrtGProtocolHeader GProtocolHeader(CommonTree source, GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		// Alternative is to make parsing return all as AssrtGProtocolHeader directly
		AssrtGProtocolHeader gpb = new AssrtGProtocolHeader(source, name, roledecls, paramdecls);
		gpb = del(gpb, createDefaultDelegate());  // Annots handled directly by AssrtAnnotationChecker Def enter/exit
		return gpb;
	}

	// Same pattern as for GProtocolHeader
	// Non-annotated message transfers still created as AssrtGMessageTransfer -- null assertion, but AssrtGMessageTransferDel is still needed (why?)
	@Override
	public AssrtGMessageTransfer GMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		AssrtGMessageTransfer gmt = new AssrtGMessageTransfer(source, src, msg, dests);
		gmt = del(gmt, new AssrtGMessageTransferDel());
		return gmt;
	}

	@Override 
	public AssrtGConnect GConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)  // Cf. AssrtAstFactoryImpl::GMessageTransfer
	{
		AssrtGConnect gc = new AssrtGConnect(source, src, msg, dest);
		gc = del(gc, new AssrtGConnectDel());
		return gc;
	}
	
	@Override
	public GRecursion GRecursion(CommonTree source, RecVarNode recvar, GProtocolBlock block)
	{
		AssrtGRecursion gr = new AssrtGRecursion(source, recvar, block);
		gr = del(gr, new AssrtGRecursionDel());
		return gr;
	}

	@Override
	public AssrtGContinue GContinue(CommonTree source, RecVarNode recvar)
	{
		AssrtGContinue gc = new AssrtGContinue(source, recvar);
		gc = del(gc, new AssrtGContinueDel());
		return gc;
	}

	@Override
	public AssrtGDo GDo(CommonTree source, RoleArgList roleinstans, NonRoleArgList arginstans, GProtocolNameNode proto)
	{
		AssrtGDo gd = new AssrtGDo(source, roleinstans, arginstans, proto);
		gd = del(gd, new AssrtGDoDel());
		return gd;
	}
	*/

	// Explicitly creating new Assrt nodes

	@Override
	//public ParamRoleDecl ParamRoleDecl(CommonTree source, RoleNode namenode, List<ParamRoleParamNode> params)
	public ParamRoleDecl ParamRoleDecl(CommonTree source, RoleNode namenode, List<ParamIndexVar> params)
	{
		ParamRoleDecl rd = new ParamRoleDecl(source, namenode, params);
		rd = del(rd, new RoleDeclDel());
		return rd;
	}

	@Override
	public ParamGCrossMessageTransfer ParamGCrossMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest,
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		ParamGCrossMessageTransfer mt = new ParamGCrossMessageTransfer(source, src, msg, dest,
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		mt = del(mt, new GMessageTransferDel());  // FIXME
		return mt;
	}

	@Override
	public ParamGDotMessageTransfer ParamGDotMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest,
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		ParamGDotMessageTransfer mt = new ParamGDotMessageTransfer(source, src, msg, dest,
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		mt = del(mt, new GMessageTransferDel());  // FIXME
		return mt;
	}
	
	/*@Override
	public <K extends Kind> NameNode<K> SimpleNameNode(CommonTree source, K kind, String identifier)
	{
		NameNode<? extends Kind> snn = null;
		
		// Default del's
		if (kind.equals(ParamRoleParamKind.KIND))
		{
			snn = new ParamRoleParamNode(source, identifier);
			return castNameNode(kind, del(snn, createDefaultDelegate()));
		}
		else
		{
			return super.SimpleNameNode(source, kind, identifier);
		}
	}*/
}

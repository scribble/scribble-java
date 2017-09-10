package org.scribble.ext.go.ast;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.RoleDeclDel;
import org.scribble.ext.go.ast.global.ParamGChoice;
import org.scribble.ext.go.ast.global.ParamGCrossMessageTransfer;
import org.scribble.ext.go.ast.global.ParamGDotMessageTransfer;
import org.scribble.ext.go.ast.global.ParamGMultiChoices;
import org.scribble.ext.go.ast.global.ParamGMultiChoicesTransfer;
import org.scribble.ext.go.del.global.ParamGChoiceDel;
import org.scribble.ext.go.del.global.ParamGMessageTransferDel;
import org.scribble.ext.go.del.global.ParamGMultiChoicesDel;
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
	}*/
	
	
	// Returning new node classes in place of existing -- FIXME: do for GMessageTransfer and GChoice

	@Override
	public ParamRoleDecl RoleDecl(CommonTree source, RoleNode namenode)
	{
		ParamRoleDecl rd = new ParamRoleDecl(source, namenode);
		rd = del(rd, new RoleDeclDel());
		return rd;
	}


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
		mt = del(mt, new ParamGMessageTransferDel());  // FIXME: parameterised self connection check
		return mt;
	}

	@Override
	public ParamGDotMessageTransfer ParamGDotMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest,
			//ParamRoleParamNode srcRangeStart, ParamRoleParamNode srcRangeEnd, ParamRoleParamNode destRangeStart, ParamRoleParamNode destRangeEnd)
			ParamIndexExpr srcRangeStart, ParamIndexExpr srcRangeEnd, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		ParamGDotMessageTransfer mt = new ParamGDotMessageTransfer(source, src, msg, dest,
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		mt = del(mt, new ParamGMessageTransferDel());  // FIXME: parameterised self connection check
		return mt;
	}
	
	@Override
	public ParamGChoice ParamGChoice(CommonTree source, RoleNode subj, ParamIndexExpr expr, List<GProtocolBlock> blocks)
	{
		ParamGChoice gc = new ParamGChoice(source, subj, expr, blocks);
		gc = del(gc, new ParamGChoiceDel());
		return gc;
	}

	@Override
	public ParamGMultiChoices ParamGMultiChoices(CommonTree source, RoleNode subj, ParamIndexVar var,
			ParamIndexExpr start, ParamIndexExpr end, List<GProtocolBlock> blocks)
	{
		ParamGMultiChoices gc = new ParamGMultiChoices(source, subj, var, start, end, blocks);
		gc = del(gc, new ParamGMultiChoicesDel());
		return gc;
	}
	
	@Override
	public ParamGMultiChoicesTransfer ParamGMultiChoicesTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			ParamIndexVar var, ParamIndexExpr destRangeStart, ParamIndexExpr destRangeEnd)
	{
		ParamGMultiChoicesTransfer mt = new ParamGMultiChoicesTransfer(source, src, msg, dest,
				var, destRangeStart, destRangeEnd);
		mt = del(mt, new ParamGMessageTransferDel());  // FIXME: not a ParamGMessageTransfer
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

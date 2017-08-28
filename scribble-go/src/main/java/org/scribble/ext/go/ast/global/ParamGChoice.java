package org.scribble.ext.go.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

public class ParamGChoice extends GChoice
{

	public final ParamIndexExpr expr;
	public ParamGChoice(CommonTree source, RoleNode subj, ParamIndexExpr expr, List<GProtocolBlock> blocks)
	{
		super(source, subj, blocks);
		this.expr = expr;
	}
	
	// Similar pattern to reconstruct
	// Idea is, if extending the AST class (more fields), then reconstruct/project should also be extended (and called from extended del)
	public LChoice project(AstFactory af, Role self, List<LProtocolBlock> blocks)
	{
		throw new RuntimeException("[param] TODO: " + this);  // Not just project, but most passes after parsing
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new ParamGChoice(this.source, this.subj, this.expr, getBlocks());
	}
	
	@Override
	public ParamGChoice clone(AstFactory af)
	{
		RoleNode subj = this.subj.clone(af);
		List<GProtocolBlock> blocks = ScribUtil.cloneList(af, getBlocks());
		return ((ParamAstFactory) af).ParamGChoice(this.source, subj, this.expr, blocks);
	}

	@Override
	public ParamGChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Global>> blocks)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	public ParamGChoice reconstruct(RoleNode subj, ParamIndexExpr expr, List<? extends ProtocolBlock<Global>> blocks)
	{
		ScribDel del = del();
		ParamGChoice gc = new ParamGChoice(this.source, subj, expr, castBlocks(blocks));
		gc = (ParamGChoice) gc.del(del);
		return gc;
	}
	
	@Override
	public ParamGChoice visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode subj = (RoleNode) visitChild(this.subj, nv);
		List<GProtocolBlock> blocks = visitChildListWithClassEqualityCheck(this, getBlocks(), nv);
		return reconstruct(subj, this.expr, blocks);
	}
	
	@Override
	public String toString()
	{
		String sep = " " + Constants.OR_KW + " ";
		return Constants.CHOICE_KW + " " + Constants.AT_KW + " " + this.subj
				+ "[" + this.expr + "]"
				+ " " + getBlocks().stream().map(b -> b.toString()).collect(Collectors.joining(sep));
	}
}

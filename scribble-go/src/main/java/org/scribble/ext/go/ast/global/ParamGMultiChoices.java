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
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

public class ParamGMultiChoices extends GChoice
{
	public final ParamIndexVar var;
	public final ParamIndexExpr start;
	public final ParamIndexExpr end;

	public ParamGMultiChoices(CommonTree source, RoleNode subj, ParamIndexVar var,
			ParamIndexExpr start, ParamIndexExpr end, List<GProtocolBlock> blocks)
	{
		super(source, subj, blocks);
		this.var = var;
		this.start = start;
		this.end = end;
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
		return new ParamGMultiChoices(this.source, this.subj, this.var, this.start, this.end, getBlocks());
	}
	
	@Override
	public ParamGMultiChoices clone(AstFactory af)
	{
		RoleNode subj = this.subj.clone(af);
		List<GProtocolBlock> blocks = ScribUtil.cloneList(af, getBlocks());
		return ((ParamAstFactory) af).ParamGMultiChoices(this.source, subj, this.var, this.start, this.end, blocks);
	}

	@Override
	public ParamGMultiChoices reconstruct(RoleNode subj, List<? extends ProtocolBlock<Global>> blocks)
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	public ParamGMultiChoices reconstruct(RoleNode subj, ParamIndexVar var,
			ParamIndexExpr start, ParamIndexExpr end, List<? extends ProtocolBlock<Global>> blocks)
	{
		ScribDel del = del();
		ParamGMultiChoices gc = new ParamGMultiChoices(this.source, subj, var, start, end, castBlocks(blocks));
		gc = (ParamGMultiChoices) gc.del(del);
		return gc;
	}
	
	@Override
	public ParamGMultiChoices visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode subj = (RoleNode) visitChild(this.subj, nv);
		List<GProtocolBlock> blocks = visitChildListWithClassEqualityCheck(this, getBlocks(), nv);
		return reconstruct(subj, this.var, this.start, this.end, blocks);
	}
	
	@Override
	public String toString()
	{
		String sep = " " + Constants.OR_KW + " ";
		return Constants.CHOICES_KW + " " + Constants.AT_KW + " " + this.subj
				+ "[" + this.var + ":" + this.start + ".." + this.end + "]"
				+ " " + getBlocks().stream().map(b -> b.toString()).collect(Collectors.joining(sep));
	}
}

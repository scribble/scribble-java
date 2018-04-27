package org.scribble.ext.go.ast.global;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.global.GCompoundInteractionNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.local.LCompoundInteractionNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.ast.RPForeach;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class RPGForeach extends RPForeach<Global> implements GCompoundInteractionNode
{
	public RPGForeach(CommonTree source, RoleNode subj, RPIndexVar var,
			RPIndexExpr start, RPIndexExpr end, GProtocolBlock block)
	{
		super(source, subj, var, start, end, block);
	}
	
	@Override
	public GProtocolBlock getBlock()
	{
		return (GProtocolBlock) this.block;
	}
	
	// Similar pattern to reconstruct
	// Idea is, if extending the AST class (more fields), then reconstruct/project should also be extended (and called from extended del)
	public LCompoundInteractionNode project(AstFactory af, Role self, List<LProtocolBlock> blocks)
	{
		throw new RuntimeException("[param] TODO: " + this);  // Because -rp-core only  // Not just project, but most passes after parsing
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new RPGForeach(this.source, this.subj, this.var, this.start, this.end, getBlock());
	}
	
	@Override
	public RPGForeach clone(AstFactory af)
	{
		RoleNode subj = this.subj.clone(af);
		GProtocolBlock block = getBlock().clone(af);
		return ((RPAstFactory) af).RPGForeach(this.source, subj, this.var, this.start, this.end, block);
	}

	@Override
	public RPGForeach reconstruct(RoleNode subj, RPIndexVar var, RPIndexExpr start, RPIndexExpr end, ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		RPGForeach gc = new RPGForeach(this.source, subj, var, start, end, (GProtocolBlock) block);
		gc = (RPGForeach) gc.del(del);
		return gc;
	}
}

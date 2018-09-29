package org.scribble.ext.go.ast.global;

import java.util.List;
import java.util.stream.Collectors;

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
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class RPGForeach extends RPForeach<Global> implements GCompoundInteractionNode
{
	public RPGForeach(CommonTree source, List<RoleNode> subjs, 
			List<RPForeachVar> params, List<RPIndexExpr> starts, List<RPIndexExpr> ends, 
			GProtocolBlock block)
	{
		super(source, subjs, params, starts, ends, block);
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
		return new RPGForeach(this.source, this.subjs, this.params, this.starts, this.ends, getBlock());
	}
	
	@Override
	public RPGForeach clone(AstFactory af)
	{
		List<RoleNode> subjs = this.subjs.stream().map(r -> r.clone(af)).collect(Collectors.toList());
		GProtocolBlock block = getBlock().clone(af);
		return ((RPAstFactory) af).RPGForeach(this.source, subjs, this.params, this.starts, this.ends, block);
	}

	@Override
	public RPGForeach reconstruct(List<RoleNode> subjs, List<RPForeachVar> params, List<RPIndexExpr> starts, List<RPIndexExpr> ends, ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		RPGForeach gc = new RPGForeach(this.source, subjs, params, starts, ends, (GProtocolBlock) block);
		gc = (RPGForeach) gc.del(del);
		return gc;
	}
}

package org.scribble.ext.go.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.CompoundInteractionNode;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class RPForeach<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	public final RoleNode subj;
	public final RPIndexVar var;
	public final RPIndexExpr start;
	public final RPIndexExpr end;
	public final ProtocolBlock<K> block;

	protected RPForeach(CommonTree source, RoleNode subj, RPIndexVar var, RPIndexExpr start, RPIndexExpr end, ProtocolBlock<K> block)
	{
		super(source);
		this.subj = subj;
		this.var = var;
		this.start = start;
		this.end = end;
		this.block = block;
	}

	public abstract RPForeach<K> reconstruct(RoleNode subj, RPIndexVar var, RPIndexExpr start, RPIndexExpr end, ProtocolBlock<K> block);
	
	@Override
	public abstract RPForeach<K> clone(AstFactory af);

	@Override
	public RPForeach<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode subj = (RoleNode) visitChild(this.subj, nv);
		ProtocolBlock<K> block = visitChildWithClassEqualityCheck(this, this.block, nv);
		return reconstruct(subj, var, start, end, block);
	}
	
	public abstract ProtocolBlock<K> getBlock();

	@Override
	public String toString()
	{
		return "foreach " + this.subj + "[" + this.var + ":" + this.start + "," + this.end + "] " + this.block;
	}
}

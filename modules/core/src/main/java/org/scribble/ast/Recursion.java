package org.scribble.ast;

import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class Recursion<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	public final RecVarNode recvar;
	public final ProtocolBlock<K> block;

	protected Recursion(RecVarNode recvar, ProtocolBlock<K> block)
	{
		this.recvar = recvar;
		this.block = block;
	}

	public abstract Recursion<K> reconstruct(RecVarNode recvar, ProtocolBlock<K> block);
	
	@Override
	public abstract Recursion<K> clone();

	@Override
	public Recursion<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(this.recvar, nv);
		ProtocolBlock<K> block = visitChildWithClassEqualityCheck(this, this.block, nv);
		return reconstruct(recvar, block);
	}
	
	public abstract ProtocolBlock<K> getBlock();

	@Override
	public String toString()
	{
		return Constants.REC_KW + " " + this.recvar + " " + block;
	}
}

package org.scribble.ast.local;

import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LRecursion extends Recursion<Local> implements LCompoundInteractionNode
{
	public LRecursion(RecVarNode recvar, LProtocolBlock block)
	{
		super(recvar, block);
	}

	@Override
	public LRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Local> block)
	{
		ScribDel del = del();
		LRecursion lr = new LRecursion(recvar, (LProtocolBlock) block);
		lr = (LRecursion) lr.del(del);
		return lr;
	}

	@Override
	protected LRecursion copy()
	{
		return new LRecursion(this.recvar, getBlock());
	}
	
	@Override
	public LProtocolBlock getBlock()
	{
		return (LProtocolBlock) this.block;
	}
}

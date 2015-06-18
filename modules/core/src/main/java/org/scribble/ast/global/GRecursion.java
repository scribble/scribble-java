package org.scribble.ast.global;

import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

public class GRecursion extends Recursion<Global> implements GCompoundInteractionNode
{
	public GRecursion(RecVarNode recvar, GProtocolBlock block)
	{
		super(recvar, block);
	}

	@Override
	protected GRecursion copy()
	{
		return new GRecursion(this.recvar, (GProtocolBlock) this.block);
	}

	@Override
	protected GRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		GRecursion gr = new GRecursion(recvar, (GProtocolBlock) block);
		gr = (GRecursion) gr.del(del);
		return gr;
	}
}

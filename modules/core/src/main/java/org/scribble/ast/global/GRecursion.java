package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
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
		return new GRecursion(this.recvar, getBlock());
	}
	
	@Override
	public GRecursion clone()
	{
		RecVarNode recvar = this.recvar.clone();
		GProtocolBlock block = getBlock().clone();
		return AstFactoryImpl.FACTORY.GRecursion(recvar, block);
	}

	@Override
	public GRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		GRecursion gr = new GRecursion(recvar, (GProtocolBlock) block);
		gr = (GRecursion) gr.del(del);
		return gr;
	}
	
	@Override
	public GProtocolBlock getBlock()
	{
		return (GProtocolBlock) this.block;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GCompoundInteractionNode.super.getKind();
	}
}

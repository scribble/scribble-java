package org.scribble.ast.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDef;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LProtocolDef extends ProtocolDef<Local> implements LNode
{
	public LProtocolDef(LProtocolBlock block)
	{
		super(block);
	}

	@Override
	protected LProtocolDef copy()
	{
		return new LProtocolDef(getBlock());
	}
	
	@Override
	public LProtocolDef clone()
	{
		LProtocolBlock block = getBlock().clone();
		return AstFactoryImpl.FACTORY.LProtocolDef(block);
	}

	@Override
	public LProtocolDef reconstruct(ProtocolBlock<Local> block)
	{
		ScribDel del = del();
		LProtocolDef lpd = new LProtocolDef((LProtocolBlock) block);
		lpd = (LProtocolDef) lpd.del(del);
		return lpd;
	}
	
	@Override
	public LProtocolBlock getBlock()
	{
		return (LProtocolBlock) this.block;
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LNode.super.getKind();
	}
}

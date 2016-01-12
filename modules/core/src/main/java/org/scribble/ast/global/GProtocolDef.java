package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDef;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

public class GProtocolDef extends ProtocolDef<Global> implements GNode
{
	public GProtocolDef(GProtocolBlock block)
	{
		super(block);
	}

	@Override
	protected GProtocolDef copy()
	{
		return new GProtocolDef(getBlock());
	}
	
	@Override
	public GProtocolDef clone()
	{
		GProtocolBlock block = getBlock().clone();
		return AstFactoryImpl.FACTORY.GProtocolDef(block);
	}

	@Override
	public GProtocolDef reconstruct(ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		GProtocolDef gpd = new GProtocolDef((GProtocolBlock) block);
		gpd = (GProtocolDef) gpd.del(del);
		return gpd;
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
		return GNode.super.getKind();
	}
}

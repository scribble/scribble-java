package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

public class GProtocolBlock extends ProtocolBlock<Global> implements GNode
{
	public GProtocolBlock(GInteractionSeq seq)
	{
		super(seq);
	}

	@Override
	protected GProtocolBlock copy()
	{
		return new GProtocolBlock(getInteractionSeq());
	}
	
	@Override
	public GProtocolBlock clone()
	{
		GInteractionSeq gis = getInteractionSeq().clone();
		return AstFactoryImpl.FACTORY.GProtocolBlock(gis);
	}

	@Override
	public GProtocolBlock reconstruct(InteractionSeq<Global> seq)
	{
		ScribDel del = del();
		GProtocolBlock gpb = new GProtocolBlock((GInteractionSeq) seq);
		gpb = (GProtocolBlock) gpb.del(del);
		return gpb;
	}

	@Override
	public GInteractionSeq getInteractionSeq()
	{
		return (GInteractionSeq) this.seq;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GNode.super.getKind();
	}
}

package org.scribble.ast.global;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

public class GProtocolBlock extends ProtocolBlock<Global> implements GNode
{
	public GProtocolBlock(InteractionSeq<Global> seq)
	{
		super(seq);
	}

	@Override
	protected GProtocolBlock copy()
	{
		return new GProtocolBlock(this.seq);
	}

	@Override
	protected GProtocolBlock reconstruct(InteractionSeq<Global> seq)
	{
		ScribDel del = del();
		GProtocolBlock gpb = new GProtocolBlock(seq);
		gpb = (GProtocolBlock) gpb.del(del);
		return gpb;
	}
}

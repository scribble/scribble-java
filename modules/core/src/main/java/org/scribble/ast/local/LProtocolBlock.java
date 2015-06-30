package org.scribble.ast.local;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LProtocolBlock extends ProtocolBlock<Local> implements LNode
{
	public LProtocolBlock(LInteractionSeq seq)
	{
		super(seq);
	}

	@Override
	protected LProtocolBlock copy()
	{
		return new LProtocolBlock(getInteractionSeq());
	}

	@Override
	protected LProtocolBlock reconstruct(InteractionSeq<Local> seq)
	{
		ScribDel del = del();
		LProtocolBlock lpb = new LProtocolBlock((LInteractionSeq) seq);
		lpb = (LProtocolBlock) lpb.del(del);
		return lpb;
	}

	@Override
	public LInteractionSeq getInteractionSeq()
	{
		return (LInteractionSeq) this.seq;
	}
}

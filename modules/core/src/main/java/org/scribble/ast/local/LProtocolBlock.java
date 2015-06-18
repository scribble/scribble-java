package org.scribble.ast.local;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LProtocolBlock extends ProtocolBlock<Local> implements LNode
{
	public LProtocolBlock(InteractionSeq<Local> seq)
	{
		super(seq);
	}

	@Override
	protected LProtocolBlock copy()
	{
		return new LProtocolBlock(this.seq);
	}

	@Override
	protected LProtocolBlock reconstruct(InteractionSeq<Local> seq)
	{
		ScribDel del = del();
		LProtocolBlock lpb = new LProtocolBlock(seq);
		lpb = (LProtocolBlock) lpb.del(del);
		return lpb;
	}
}

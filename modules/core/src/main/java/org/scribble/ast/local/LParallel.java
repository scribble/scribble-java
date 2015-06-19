package org.scribble.ast.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Parallel;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LParallel extends Parallel<Local> implements LCompoundInteractionNode
{
	public LParallel(List<LProtocolBlock> blocks)
	{
		super(blocks);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LParallel(getBlocks());
	}
	
	@Override
	protected LParallel reconstruct(List<? extends ProtocolBlock<Local>> blocks)
	{
		ScribDel del = del();
		LParallel lp = new LParallel(castBlocks(blocks));
		lp = (LParallel) lp.del(del);
		return lp;
	}

	@Override
	public List<LProtocolBlock> getBlocks()
	{
		return castBlocks(this.blocks);
	}
	
	private static List<LProtocolBlock> castBlocks(List<? extends ProtocolBlock<Local>> blocks)
	{
		return blocks.stream().map((b) -> (LProtocolBlock) b).collect(Collectors.toList());
	}
}

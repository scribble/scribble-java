package org.scribble.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Choice;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

public class GChoice extends Choice<Global> implements GCompoundInteractionNode
{
	public GChoice(RoleNode subj, List<GProtocolBlock> blocks)
	{
		super(subj, blocks);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new GChoice(this.subj, getBlocks());
	}

	@Override
	protected GChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Global>> blocks)
	{
		ScribDel del = del();
		GChoice gc = new GChoice(subj, castBlocks(blocks));
		gc = (GChoice) gc.del(del);
		return gc;
	}
	
	@Override
	public List<GProtocolBlock> getBlocks()
	{
		return castBlocks(this.blocks);
	}
	
	private static List<GProtocolBlock> castBlocks(List<? extends ProtocolBlock<Global>> blocks)
	{
		return blocks.stream().map((b) -> (GProtocolBlock) b).collect(Collectors.toList());
	}
}

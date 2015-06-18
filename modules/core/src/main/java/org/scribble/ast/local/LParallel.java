package org.scribble.ast.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Parallel;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

//public class LocalParallel extends Parallel<LocalProtocolBlock> implements CompoundLocalInteractionNode
public class LParallel extends Parallel<Local> implements LCompoundInteractionNode
{
	//public LocalParallel(List<LocalProtocolBlock> blocks)
	public LParallel(List<? extends ProtocolBlock<Local>> blocks)
	{
		super(blocks);
	}
	
	@Override
	//protected LocalParallel reconstruct(List<LocalProtocolBlock> blocks)
	protected LParallel reconstruct(List<? extends ProtocolBlock<Local>> blocks)
	{
		ScribDel del = del();
		LParallel lp = new LParallel(blocks);
		lp = (LParallel) lp.del(del);
		return lp;
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LParallel(this.blocks);
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
	
	/*@Override
	public LocalParallel leaveGraphBuilding(GraphBuilder builder)
	{
		throw new RuntimeException("TODO: ");
	}*/

	/*@Override
	public LocalParallel leaveContextBuilding(Node parent, NodeContextBuilder builder) throws ScribbleException
	{
		Parallel<LocalProtocolBlock> par = super.leaveContextBuilding(parent, builder);
		return new LocalParallel(par.ct, par.blocks, (CompoundInteractionNodeContext) par.getContext());
	}

	@Override
	public LocalParallel leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Parallel<LocalProtocolBlock> par = super.leaveWFChoiceCheck(checker);
		return new LocalParallel(par.ct, par.blocks, par.getContext(), (WellFormedChoiceEnv) par.getEnv());
	}

	@Override
	public LocalParallel visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Parallel<LocalProtocolBlock> par = super.visitChildren(nv);
		return new LocalParallel(par.ct, par.blocks, par.getContext(), par.getEnv());
	}*/
}

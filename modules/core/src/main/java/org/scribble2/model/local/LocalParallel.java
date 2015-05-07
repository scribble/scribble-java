package org.scribble2.model.local;

import java.util.List;

import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.Parallel;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.kind.LocalKind;

//public class LocalParallel extends Parallel<LocalProtocolBlock> implements CompoundLocalInteractionNode
public class LocalParallel extends Parallel<LocalKind> implements CompoundLocalInteractionNode
{
	//public LocalParallel(List<LocalProtocolBlock> blocks)
	public LocalParallel(List<? extends ProtocolBlock<LocalKind>> blocks)
	{
		super(blocks);
	}
	
	@Override
	//protected LocalParallel reconstruct(List<LocalProtocolBlock> blocks)
	protected LocalParallel reconstruct(List<? extends ProtocolBlock<LocalKind>> blocks)
	{
		ModelDelegate del = del();
		LocalParallel lp = new LocalParallel(blocks);
		lp = (LocalParallel) lp.del(del);
		return lp;
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LocalParallel(this.blocks);
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

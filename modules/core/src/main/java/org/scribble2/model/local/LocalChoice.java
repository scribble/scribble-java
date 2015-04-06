package org.scribble2.model.local;

import java.util.List;

import org.scribble2.model.Choice;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.RoleNode;

public class LocalChoice extends Choice<LocalProtocolBlock> implements CompoundLocalInteractionNode
{
	public LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks)
	{
		super(subj, blocks);
	}

	@Override
	protected LocalChoice reconstruct(RoleNode subj, List<LocalProtocolBlock> blocks)
	{
		ModelDelegate del = del();
		LocalChoice lc = new LocalChoice(subj, blocks);
		lc = (LocalChoice) lc.del(del);
		return lc;
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LocalChoice(this.subj, this.blocks);
	}
	
	/*@Override
	public LocalChoice leaveContextBuilding(Node parent, NodeContextBuilder builder) throws ScribbleException
	{
		Choice<LocalProtocolBlock> cho = super.leaveContextBuilding(parent, builder);
		return new LocalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext());
	}

	@Override
	public LocalChoice leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Choice<LocalProtocolBlock> cho = super.leaveWFChoiceCheck(checker);
		return new LocalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext(), (WellFormedChoiceEnv) cho.getEnv());
	}

	@Override
	public LocalChoice visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Choice<LocalProtocolBlock> cho = super.visitChildren(nv);
		//List<LocalProtocolBlock> blocks = GlobalProtocolBlock.toGlobalProtocolBlockList.apply(cho.blocks);
		//List<LocalProtocolBlock> blocks = cho.blocks.stream().map(GlobalProtocolBlock.toGlobalProtocolBlock).collect(Collectors.toList());
		return new LocalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext(), cho.getEnv());
	}*/
	
	/*@Override
	public LocalChoice buildGraph(GraphBuilder builder)
	{
		return this;
	}*/
}

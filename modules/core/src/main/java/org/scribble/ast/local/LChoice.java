package org.scribble.ast.local;

import java.util.List;

import org.scribble.ast.Choice;
import org.scribble.ast.ModelNodeBase;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.del.ModelDel;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.kind.Local;

//public class LocalChoice extends Choice<LocalProtocolBlock> implements CompoundLocalInteractionNode
public class LChoice extends Choice<Local> implements LCompoundInteractionNode
{
	//public LocalChoice(RoleNode subj, List<LocalProtocolBlock> blocks)
	public LChoice(RoleNode subj, List<? extends ProtocolBlock<Local>> blocks)
	{
		super(subj, blocks);
	}

	@Override
	//protected LocalChoice reconstruct(RoleNode subj, List<LocalProtocolBlock> blocks)
	protected LChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Local>> blocks)
	{
		ModelDel del = del();
		LChoice lc = new LChoice(subj, blocks);
		lc = (LChoice) lc.del(del);
		return lc;
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LChoice(this.subj, this.blocks);
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

package org.scribble2.model.local;

import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.Recursion;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.sesstype.kind.LocalKind;

//public class LocalRecursion extends Recursion<LocalProtocolBlock> implements CompoundLocalInteractionNode
public class LocalRecursion extends Recursion<LocalKind> implements CompoundLocalInteractionNode
{
	//public LocalRecursion(RecursionVarNode recvar, LocalProtocolBlock block)
	public LocalRecursion(RecursionVarNode recvar, ProtocolBlock<LocalKind> block)
	{
		super(recvar, block);
	}

	@Override
	//protected LocalRecursion reconstruct(RecursionVarNode recvar, LocalProtocolBlock block)
	protected LocalRecursion reconstruct(RecursionVarNode recvar, ProtocolBlock<LocalKind> block)
	{
		ModelDelegate del = del();
		LocalRecursion lr = new LocalRecursion(recvar, block);
		lr = (LocalRecursion) lr.del(del);
		return lr;
	}

	@Override
	protected LocalRecursion copy()
	{
		return new LocalRecursion(this.recvar, this.block);
	}
	
	/*@Override
	public GraphBuilder enterGraphBuilding(GraphBuilder builder)
	{
		builder.setRecursionEntry(this.recvar.toName());
		return builder;
	}

	@Override
	public LocalRecursion leaveGraphBuilding(GraphBuilder builder)
	{
		builder.removeRecursionEntry(this.recvar.toName());
		return this;
	}*/
	
	/*@Override
	public LocalRecursion leaveContextBuilding(Node parent, NodeContextBuilder builder) throws ScribbleException
	{
		Recursion<LocalProtocolBlock> rec = super.leaveContextBuilding(parent, builder);
		return new LocalRecursion(rec.ct, rec.recvar, rec.block, (CompoundInteractionNodeContext) rec.getContext());
	}

	@Override
	public LocalRecursion leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Recursion<LocalProtocolBlock> rec = super.leaveWFChoiceCheck(checker);
		return new LocalRecursion(rec.ct, rec.recvar, rec.block, rec.getContext(), (WellFormedChoiceEnv) rec.getEnv());
	}
	
	@Override
	pukblic LocalRecursion visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Recursion<LocalProtocolBlock> rec = super.visitChildren(nv);
		return new LocalRecursion(rec.ct, rec.recvar, rec.block, rec.getContext(), rec.getEnv());
	}*/
}

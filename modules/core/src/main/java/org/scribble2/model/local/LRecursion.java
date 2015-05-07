package org.scribble2.model.local;

import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.Recursion;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.sesstype.kind.Local;

//public class LocalRecursion extends Recursion<LocalProtocolBlock> implements CompoundLocalInteractionNode
public class LRecursion extends Recursion<Local> implements LCompoundInteractionNode
{
	//public LocalRecursion(RecursionVarNode recvar, LocalProtocolBlock block)
	public LRecursion(RecursionVarNode recvar, ProtocolBlock<Local> block)
	{
		super(recvar, block);
	}

	@Override
	//protected LocalRecursion reconstruct(RecursionVarNode recvar, LocalProtocolBlock block)
	protected LRecursion reconstruct(RecursionVarNode recvar, ProtocolBlock<Local> block)
	{
		ModelDel del = del();
		LRecursion lr = new LRecursion(recvar, block);
		lr = (LRecursion) lr.del(del);
		return lr;
	}

	@Override
	protected LRecursion copy()
	{
		return new LRecursion(this.recvar, this.block);
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

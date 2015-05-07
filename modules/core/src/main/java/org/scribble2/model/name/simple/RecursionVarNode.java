package org.scribble2.model.name.simple;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.name.RecursionVar;



public class RecursionVarNode extends SimpleNameNode
{
	public RecursionVarNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected RecursionVarNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		RecursionVarNode rvn = new RecursionVarNode(identifier);
		rvn = (RecursionVarNode) rvn.del(del);
		return rvn;
	}

	@Override
	protected RecursionVarNode copy()
	{
		return new RecursionVarNode(this.identifier);
	}

	@Override
	public RecursionVar toName()
	{
		return new RecursionVar(this.identifier);
	}
}

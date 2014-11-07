package org.scribble2.model.name.simple;

import org.scribble2.sesstype.name.RecursionVar;



public class RecursionVarNode extends SimpleNameNode
{
	public RecursionVarNode(String identifier)
	{
		super(identifier);
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

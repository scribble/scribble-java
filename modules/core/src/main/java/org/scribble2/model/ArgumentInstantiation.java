package org.scribble2.model;


public class ArgumentInstantiation extends Instantiation<ArgumentNode>
{
	public ArgumentInstantiation(ArgumentNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new ArgumentInstantiation(this.arg);
	}
}

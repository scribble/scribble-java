package org.scribble2.model;


public abstract class Instantiation<T extends ModelNode> extends ModelNodeBase
{
	public final T arg;

	public Instantiation(T arg)
	{
		this.arg = arg;
	}
}

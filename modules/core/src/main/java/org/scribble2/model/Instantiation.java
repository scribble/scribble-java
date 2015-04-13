package org.scribble2.model;


// Cf. NameDeclNode/HeaderParameterDecl
// Simpler than NameDeclNode, doesn't constrain node-type correspondence for names
public abstract class Instantiation<T extends InstantiationNode> extends ModelNodeBase
{
	public final T arg;

	public Instantiation(T arg)
	{
		this.arg = arg;
	}
}

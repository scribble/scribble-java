package org.scribble2.model.name.qualified;


public abstract class QualifiedNameNode extends CompoundNameNode
{
	//public QualifiedNameNodes(PrimitiveNameNode... ns)
	public QualifiedNameNode(String... ns)
	{
		super(ns);
	}
}

package org.scribble2.model.name.qualified;

import org.scribble2.model.name.CompoundNameNode;
import org.scribble2.sesstype.name.CompoundName;


public abstract class QualifiedNameNode<T extends CompoundName> extends CompoundNameNode<T>
{
	//public QualifiedNameNodes(PrimitiveNameNode... ns)
	public QualifiedNameNode(String... ns)
	{
		super(ns);
	}
}

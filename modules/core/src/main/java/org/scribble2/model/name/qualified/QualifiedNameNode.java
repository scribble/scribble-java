package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Name;


public abstract class QualifiedNameNode<T extends Name<K>, K extends Kind> extends CompoundNameNode<T, K>
{
	//public QualifiedNameNodes(PrimitiveNameNode... ns)
	public QualifiedNameNode(String... ns)
	{
		super(ns);
	}
}

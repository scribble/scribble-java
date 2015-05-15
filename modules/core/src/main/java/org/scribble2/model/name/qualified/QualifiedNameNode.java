package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.kind.Kind;


//public abstract class QualifiedNameNode<T extends Name<K>, K extends Kind> extends CompoundNameNode<T, K>
public abstract class QualifiedNameNode<K extends Kind> extends CompoundNameNode<K>
{
	//public QualifiedNameNodes(PrimitiveNameNode... ns)
	public QualifiedNameNode(String... ns)
	{
		super(ns);
	}
}

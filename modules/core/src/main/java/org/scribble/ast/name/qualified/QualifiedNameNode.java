package org.scribble.ast.name.qualified;

import org.scribble.ast.name.NameNode;
import org.scribble.sesstype.kind.Kind;


//public abstract class QualifiedNameNode<T extends Name<K>, K extends Kind> extends CompoundNameNode<T, K>
public abstract class QualifiedNameNode<K extends Kind> extends NameNode<K> //CompoundNameNode<K>
{
	//public QualifiedNameNodes(PrimitiveNameNode... ns)
	public QualifiedNameNode(String... ns)
	{
		super(ns);
	}
}

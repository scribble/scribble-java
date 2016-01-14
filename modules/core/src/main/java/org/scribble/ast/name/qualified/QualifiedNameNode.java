package org.scribble.ast.name.qualified;

import org.scribble.ast.name.NameNode;
import org.scribble.sesstype.kind.Kind;

public abstract class QualifiedNameNode<K extends Kind> extends NameNode<K>
{
	public QualifiedNameNode(String... ns)
	{
		super(ns);
	}
}

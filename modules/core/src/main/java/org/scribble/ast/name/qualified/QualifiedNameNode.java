package org.scribble.ast.name.qualified;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.NameNode;
import org.scribble.sesstype.kind.Kind;

public abstract class QualifiedNameNode<K extends Kind> extends NameNode<K>
{
	public QualifiedNameNode(CommonTree source, String... ns)
	{
		super(source, ns);
	}
}

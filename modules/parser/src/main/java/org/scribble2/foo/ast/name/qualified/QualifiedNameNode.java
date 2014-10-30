package org.scribble2.foo.ast.name.qualified;

import org.antlr.runtime.Token;

public abstract class QualifiedNameNode extends CompoundNameNode
{
	//public QualifiedNameNodes(PrimitiveNameNode... ns)
	public QualifiedNameNode(Token t, String... ns)
	{
		super(t, ns);
	}
}

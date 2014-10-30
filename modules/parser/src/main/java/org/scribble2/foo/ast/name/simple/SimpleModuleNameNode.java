package org.scribble2.foo.ast.name.simple;

import org.antlr.runtime.Token;

public abstract class SimpleModuleNameNode extends SimpleNameNode
{
	public SimpleModuleNameNode(Token t, String name)
	{
		super(t, name);
	}
}

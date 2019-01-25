package org.scribble.parser.scribble.ast.tree;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ScribNodeBase;

@Deprecated
public class MyCommonTree extends ScribNodeBase
{
	public MyCommonTree(CommonTree node)
	{
		super(node);
	}

	public MyCommonTree(Token t)
	{
		super(t);
	}

	@Override
	public ScribNodeBase dupNode()
	{
		return new MyCommonTree(this);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return (ScribNodeBase) dupNode();
	}

	@Override
	public ScribNodeBase clone(AstFactory af)
	{
		return (ScribNodeBase) dupNode();
	}*/
}

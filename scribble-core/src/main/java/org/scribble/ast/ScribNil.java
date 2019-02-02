package org.scribble.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public class ScribNil extends ScribNodeBase
{
	// Used by (Scrib)TreeAdapator
	public ScribNil()
	{
		super((Token) null);
	}

	// Copy constructor
	protected ScribNil(CommonTree node)
	{
		super(node);
	}

	@Override
	public ScribNodeBase dupNode()
	{
		return new ScribNil(this);  // nil can have children(?), so may need to actually copy
	}
}

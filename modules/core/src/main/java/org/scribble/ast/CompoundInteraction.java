package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;

// ProtocolBlock or CompoundInteractionNode
public abstract class CompoundInteraction extends ScribNodeBase
{
	public CompoundInteraction(CommonTree source)
	{
		super(source);
	}
}

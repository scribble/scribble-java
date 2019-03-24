package org.scribble.ast;

import org.antlr.runtime.Token;
import org.scribble.job.ScribbleException;
import org.scribble.visit.AstVisitor;

public abstract class ProtocolMod extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public ProtocolMod(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ProtocolMod(ProtocolMod node)
	{
		super(node);
	}
	
	public boolean isAux()
	{
		return false;
	}
	
	public boolean isExplicit()
	{
		return false;
	}
	
	@Override
	public ProtocolMod visitChildren(AstVisitor nv) throws ScribbleException
	{
		return this;
	}
}

package org.scribble.ast;

import org.antlr.runtime.Token;

public class ExplicitMod extends ProtocolMod
{
	// ScribTreeAdaptor#create constructor
	public ExplicitMod(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ExplicitMod(ExplicitMod node)
	{
		super(node);
	}

	@Override
	public boolean isExplicit()
	{
		return true;
	}
	
	@Override
	public ExplicitMod dupNode()
	{
		return new ExplicitMod(this);  // return this also OK, since no children
	}
	
	@Override
	public String toString()
	{
		return "explicit";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final ExplicitMod EXPLICIT = new ExplicitMod((Token) null);
}

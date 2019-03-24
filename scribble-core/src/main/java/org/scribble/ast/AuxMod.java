package org.scribble.ast;

import org.antlr.runtime.Token;

public class AuxMod extends ProtocolMod
{
	// ScribTreeAdaptor#create constructor
	public AuxMod(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected AuxMod(AuxMod node)
	{
		super(node);
	}

	@Override
	public boolean isAux()
	{
		return true;
	}
	
	@Override
	public AuxMod dupNode()
	{
		return new AuxMod(this);  // return this also OK, since no children
	}
	
	@Override
	public String toString()
	{
		return "aux";
	}
	
	
	
	
	
	
	
	
	public static final AuxMod AUX = new AuxMod((Token) null);
}

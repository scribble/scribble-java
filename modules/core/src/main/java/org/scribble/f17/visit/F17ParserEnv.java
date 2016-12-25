package org.scribble.f17.visit;

import org.scribble.visit.env.Env;

public class F17ParserEnv extends Env<F17ParserEnv>
{
	private boolean unguarded;
	
	public F17ParserEnv()
	{
		this.unguarded = false;
	}
	
	@Override
	protected F17ParserEnv copy()
	{
		F17ParserEnv copy = new F17ParserEnv();
		copy.unguarded = this.unguarded;
		return copy;
	}

	@Override
	public F17ParserEnv enterContext()
	{
		return copy();
	}
	
	//public F17ParserEnv enterChoiceContext()  // Following WFChoiceEnv
	public F17ParserEnv setUnguarded()
	{
		F17ParserEnv copy = copy();
		copy.unguarded = true;
		return copy;
	}
	
	public boolean isUnguarded()
	{
		return this.unguarded;
	}
}
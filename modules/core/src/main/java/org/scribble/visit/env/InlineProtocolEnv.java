package org.scribble.visit.env;

import org.scribble.ast.ScribNode;

public class InlineProtocolEnv extends Env
{
	private ScribNode inlined;
	
	public InlineProtocolEnv()
	{

	}

	@Override
	protected Env copy()
	{
		return new InlineProtocolEnv();
	}

	@Override
	public Env enterContext()
	{
		return copy();
	}

	public ScribNode getTranslation()
	{
		return this.inlined;
	}
	
	public InlineProtocolEnv setTranslation(ScribNode inlined)
	{
		InlineProtocolEnv copy = new InlineProtocolEnv();
		copy.inlined = inlined;
		return copy;
	}

	@Override
	public String toString()
	{
		return "inlined=" + this.inlined;
	}
}

package org.scribble.visit.env;

import org.scribble.ast.global.GNode;

public class InlineProtocolEnv extends Env
{
	private GNode inlined;
	
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
	
	public InlineProtocolEnv setTranslation(GNode inlined)
	{
		InlineProtocolEnv copy = new InlineProtocolEnv();
		copy.inlined = inlined;
		return copy;
	}

	public GNode getTranslation()
	{
		return this.inlined;
	}

	@Override
	public String toString()
	{
		return "inlined=" + this.inlined;
	}
}

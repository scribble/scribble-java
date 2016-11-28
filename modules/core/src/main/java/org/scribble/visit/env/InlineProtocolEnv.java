package org.scribble.visit.env;

import org.scribble.ast.ScribNode;

public class InlineProtocolEnv extends Env<InlineProtocolEnv>
{
	private ScribNode inlined;
	
	public InlineProtocolEnv()
	{

	}

	@Override
	protected InlineProtocolEnv copy()
	{
		return new InlineProtocolEnv();
	}

	@Override
	public InlineProtocolEnv enterContext()
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

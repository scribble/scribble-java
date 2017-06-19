package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;

public abstract class STEndStateBuilder extends STStateChanBuilder
{
	public STEndStateBuilder()
	{

	}
	
	@Override
	public String build(STStateChanAPIBuilder api, EState s)
	{
		return getPreamble(api, s);
	}
}

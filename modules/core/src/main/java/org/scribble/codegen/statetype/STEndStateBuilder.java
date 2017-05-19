package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;

public abstract class STEndStateBuilder extends STStateBuilder
{
	public STEndStateBuilder()
	{

	}
	
	@Override
	public String build(STAPIBuilder api, EState s)
	{
		return getPreamble(api, s);
	}
}

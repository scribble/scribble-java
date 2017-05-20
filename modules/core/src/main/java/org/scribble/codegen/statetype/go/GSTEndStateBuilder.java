package org.scribble.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.model.endpoint.EState;

public class GSTEndStateBuilder extends STEndStateBuilder
{
	public GSTEndStateBuilder()
	{

	}
	
	@Override
	public String build(STStateChanAPIBuilder api, EState s)
	{
		return getPreamble(api, s);
	}

	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		return GSTStateChanAPIBuilder.getStateChanPremable(api, s);
	}
}

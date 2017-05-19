package org.scribble.codegen.statetype.go;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.model.endpoint.EState;

public class GSTEndStateBuilder extends STEndStateBuilder
{
	public GSTEndStateBuilder()
	{

	}
	
	@Override
	public String build(STAPIBuilder api, EState s)
	{
		return getPreamble(api, s);
	}

	@Override
	public String getPreamble(STAPIBuilder api, EState s)
	{
		return GSTOutputStateBuilder.getPremable1(getPackage(api), api.getSTStateName(s));
	}
}

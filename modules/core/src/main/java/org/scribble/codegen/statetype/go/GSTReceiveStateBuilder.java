package org.scribble.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STReceiveStateBuilder;
import org.scribble.model.endpoint.EState;

public class GSTReceiveStateBuilder extends STReceiveStateBuilder
{
	public GSTReceiveStateBuilder(STReceiveActionBuilder rb)
	{
		super(rb);
	}

	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		return GSTStateChanAPIBuilder.getStateChanPremable(api, s);
	}
}

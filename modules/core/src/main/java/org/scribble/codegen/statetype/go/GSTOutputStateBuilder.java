package org.scribble.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.model.endpoint.EState;

public class GSTOutputStateBuilder extends STOutputStateBuilder
{
	public GSTOutputStateBuilder(STSendActionBuilder sb)
	{
		super(sb);
	}
	
	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		return GSTStateChanAPIBuilder.getStateChanPremable(api, s);
	}
}

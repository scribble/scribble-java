package org.scribble.ext.go.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.model.endpoint.EState;

public class GoSTOutputStateBuilder extends STOutputStateBuilder
{
	public GoSTOutputStateBuilder(STSendActionBuilder sb)
	{
		super(sb);
	}
	
	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		return GoSTStateChanAPIBuilder.getStateChanPremable(api, s);
	}
}

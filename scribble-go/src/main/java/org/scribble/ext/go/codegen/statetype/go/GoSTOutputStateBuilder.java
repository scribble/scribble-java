package org.scribble.ext.go.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanApiBuilder;
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
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		return GoSTStateChanApiBuilder.getStateChanPremable((GoSTStateChanApiBuilder) api, s);
	}
}

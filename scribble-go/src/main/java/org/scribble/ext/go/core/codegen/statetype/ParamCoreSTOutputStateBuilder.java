package org.scribble.ext.go.core.codegen.statetype;

import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;

public class ParamCoreSTOutputStateBuilder extends STOutputStateBuilder
{
	public ParamCoreSTOutputStateBuilder(STSendActionBuilder sb)
	{
		super(sb);
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		return ((ParamCoreSTStateChanApiBuilder) api).getStateChanPremable(s);
	}
}

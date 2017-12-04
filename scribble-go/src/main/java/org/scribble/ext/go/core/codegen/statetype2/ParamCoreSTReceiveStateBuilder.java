package org.scribble.ext.go.core.codegen.statetype2;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STReceiveStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;

public class ParamCoreSTReceiveStateBuilder extends STReceiveStateBuilder
{
	public ParamCoreSTReceiveStateBuilder(STReceiveActionBuilder sb)
	{
		super(sb);
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		return ((ParamCoreSTStateChanApiBuilder) api).getStateChanPremable(s);
	}
}

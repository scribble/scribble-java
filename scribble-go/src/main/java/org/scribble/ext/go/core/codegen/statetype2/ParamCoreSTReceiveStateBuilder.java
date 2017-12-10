package org.scribble.ext.go.core.codegen.statetype2;

import java.util.List;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STReceiveStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class ParamCoreSTReceiveStateBuilder extends STReceiveStateBuilder
{
	public final ParamCoreSTReceiveActionBuilder vb;
	
	public ParamCoreSTReceiveStateBuilder(STReceiveActionBuilder sb, ParamCoreSTReceiveActionBuilder vb)
	{
		super(sb);
		this.vb = vb;
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		return ((ParamCoreSTStateChanApiBuilder) api).getStateChanPremable(s);
	}
	
	@Override
	public String build(STStateChanApiBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		List<EAction> as = s.getActions();
		if (as.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + as);
		}
		out += "\n\n";
		out += this.rb.build(api, s, as.get(0));
		out += "\n\n";
		out += this.vb.build(api, s, as.get(0));

		return out;
	}
}


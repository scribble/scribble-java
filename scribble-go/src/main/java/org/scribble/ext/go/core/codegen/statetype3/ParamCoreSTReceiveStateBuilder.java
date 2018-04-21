package org.scribble.ext.go.core.codegen.statetype3;

import java.util.List;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STReceiveStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;

public class ParamCoreSTReceiveStateBuilder extends STReceiveStateBuilder
{
	public final ParamCoreSTReceiveActionBuilder vb;
	
	// sb is ParamCoreSTReduceActionBuilder
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
		EAction a = as.get(0);
		
		out += "\n\n";
		out += this.rb.build(api, s, a);

		// FIXME: delegation 
		if (!a.payload.elems.stream()
				.anyMatch(pet -> ((ParamCoreSTStateChanApiBuilder) api).isDelegType((DataType) pet)))
		{
			out += "\n\n";
			out += this.vb.build(api, s, a);
		}

		return out;
	}
}


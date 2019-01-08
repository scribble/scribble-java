package org.scribble.ext.go.core.codegen.statetype.flat;

import java.util.List;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STReceiveStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class RPCoreSTReceiveStateBuilder extends STReceiveStateBuilder
{
	public final RPCoreSTReceiveActionBuilder vb;
	
	// rb is ParamCoreSTReduceActionBuilder
	public RPCoreSTReceiveStateBuilder(STReceiveActionBuilder rb, RPCoreSTReceiveActionBuilder vb)
	{
		super(rb);
		this.vb = vb;
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder apib, EState s)
	{
		return ((RPCoreSTStateChanApiBuilder) apib).getStateChanPremable(s);
	}
	
	@Override
	public String build(STStateChanApiBuilder apib, EState s)
	{
		String out = getPreamble(apib, s)
			+ ((RPCoreSTStateChanApiBuilder) apib).makeStateChannelType((RPCoreEState) s); 
		
		List<EAction> as = s.getActions();
		if (as.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + as);
		}
		EAction a = as.get(0);
		
		/*out += "\n\n";
		out += this.rb.build(api, s, a);*/

		/*// FIXME: delegation 
		if (!a.payload.elems.stream()
				.anyMatch(pet -> ((RPCoreSTStateChanApiBuilder) api)//.isDelegType((DataType) pet)))
							.isDelegType(pet)))*/
		{
			out += "\n\n";
			out += this.vb.build(apib, s, a);
		}

		return out;
	}
}


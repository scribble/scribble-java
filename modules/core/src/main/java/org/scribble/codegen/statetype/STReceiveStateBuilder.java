package org.scribble.codegen.statetype;

import java.util.List;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public abstract class STReceiveStateBuilder extends STStateBuilder
{
	protected final STReceiveActionBuilder rb;
	
	public STReceiveStateBuilder(STReceiveActionBuilder rb)
	{
		this.rb = rb;
	}
	
	@Override
	public String build(STAPIBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		List<EAction> as = s.getActions();
		if (as.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + as);
		}
		out += "\n\n";
		out += this.rb.build(api, s, as.get(0));

		return out;
	}
}

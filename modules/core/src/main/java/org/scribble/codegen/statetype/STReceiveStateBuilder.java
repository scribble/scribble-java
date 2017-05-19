package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EReceive;

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
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof EReceive)  // FIXME: factor out action kind
			{
				out += this.rb.build(api, s, a);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}

		return out;
	}
}

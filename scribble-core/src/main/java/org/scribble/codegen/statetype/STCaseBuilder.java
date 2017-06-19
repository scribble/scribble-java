package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EReceive;

public abstract class STCaseBuilder extends STStateChanBuilder  // Is a distinct type but not a state -- OK for now
{
	protected final STCaseActionBuilder cb;
	
	public STCaseBuilder(STCaseActionBuilder cb)
	{
		this.cb = cb;
	}
	
	@Override
	public String build(STStateChanAPIBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof EReceive)  // FIXME: factor out action kind
			{
				out += this.cb.build(api, s, a);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}

		return out;
	}

	public abstract String getCaseStateChanName(STStateChanAPIBuilder api, EState s);
}

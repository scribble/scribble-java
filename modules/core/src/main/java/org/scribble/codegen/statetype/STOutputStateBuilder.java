package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EConnect;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.ESend;

public abstract class STOutputStateBuilder extends STStateBuilder
{
	protected final STSendActionBuilder sb;
	
	public STOutputStateBuilder(STSendActionBuilder sb)
	{
		this.sb = sb;
	}
	
	@Override
	public String build(STAPIBuilder api, EState s)
	{
		String out = getPreamble(api, s);
				  /*"package " + getPackage(gpn)
				+ "\n"
				+ "type " + getSTStateName(gpn, role, s) + " struct{}";*/
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof ESend)  // FIXME: factor out action kind
			{
				out += this.sb.build(api, s, a);
			}
			else if (a instanceof EConnect)
			{
				throw new RuntimeException("TODO: " + a);
			}
			else if (a instanceof EDisconnect)
			{
				throw new RuntimeException("TODO: " + a);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}
		
		return out;
	}
}

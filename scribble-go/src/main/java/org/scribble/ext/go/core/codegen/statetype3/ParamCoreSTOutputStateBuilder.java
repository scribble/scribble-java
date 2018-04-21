package org.scribble.ext.go.core.codegen.statetype3;

import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.ERequest;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.type.name.DataType;

public class ParamCoreSTOutputStateBuilder extends STOutputStateBuilder
{
	public final ParamCoreSTSendActionBuilder nb;

	// sb is ParamCoreSTSplitActionBuilder
	public ParamCoreSTOutputStateBuilder(STSendActionBuilder sb, ParamCoreSTSendActionBuilder nb)
	{
		super(sb);
		this.nb = nb;
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
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof ESend)  // FIXME: factor out action kind
			{
				out += this.sb.build(api, s, a);

				// FIXME: delegation 
				if (!a.payload.elems.stream()
						.anyMatch(pet -> ((ParamCoreSTStateChanApiBuilder) api).isDelegType((DataType) pet)))
				{
					out += "\n\n";
					out += this.nb.build(api, s, a);
				}
			}
			else if (a instanceof ERequest)
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
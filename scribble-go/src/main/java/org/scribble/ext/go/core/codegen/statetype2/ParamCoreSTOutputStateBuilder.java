package org.scribble.ext.go.core.codegen.statetype2;

import java.util.List;

import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

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
		
		List<EAction> as = s.getActions();
		if (as.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + as);
		}
		out += "\n\n";
		out += this.sb.build(api, s, as.get(0));
		out += "\n\n";
		out += this.nb.build(api, s, as.get(0));

		return out;
	}
}
package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTSendActionBuilder extends STSendActionBuilder
{

	@Override
	public String getActionName(STStateChanAPIBuilder api, EAction a)
	{
		return "Send_" + a.peer + "_" + a.mid;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> "arg" + i + " " + a.payload.elems.get(i)).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanAPIBuilder api, EState curr, EAction a, EState succ)
	{
		String chan = api.getChannelName(api, a);
		return 
				  //chan + "<- " + GSTBranchStateBuilder.getBranchEnumValue(a.mid) + "\n"
				  chan + ".Write(" + GSTBranchStateBuilder.getBranchEnumValue(a.mid) + ")\n"
				+ IntStream.range(0, a.payload.elems.size())
				           //.mapToObj(i -> chan + "<- arg" + i).collect(Collectors.joining("\n")) + "\n"
				           .mapToObj(i -> chan + ".Write(arg" + i + ")").collect(Collectors.joining("\n")) + "\n"
				+ buildReturn(api, curr, succ);
	}
}

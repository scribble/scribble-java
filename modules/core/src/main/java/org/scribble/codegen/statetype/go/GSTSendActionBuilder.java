package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTSendActionBuilder extends STSendActionBuilder
{

	@Override
	public String getSTActionName(STAPIBuilder api, EAction a)
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
	public String buildBody(STAPIBuilder api, EState curr, EAction a, EState succ)
	{
		return 
				  "role" + api.role + "." + a.peer + "<- " + a.mid + "\n"  // FIXME: op type
				+ IntStream.range(0, a.payload.elems.size())
				           .mapToObj(i -> "role" + api.role + "." + a.peer + "<- arg" + i).collect(Collectors.joining("\n")) + "\n"
				+ "return " + buildReturn(null, api, succ) + "{}";
	}
}

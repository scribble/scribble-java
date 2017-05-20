package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTReceiveActionBuilder extends STReceiveActionBuilder
{

	@Override
	public String getSTActionName(STStateChanAPIBuilder api, EAction a)
	{
		return "Recv_" + a.peer + "_" + a.mid;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> "arg" + i + " *" + a.payload.elems.get(i)).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanAPIBuilder api, EState curr, EAction a, EState succ)
	{
		return 
				  //"op := 
				  "<-role" + api.role + "." + a.peer + "\n"
				+ IntStream.range(0, a.payload.elems.size())
				           .mapToObj(i -> "val" + i + " := <-role" + api.role + "." + a.peer
				          		 + "\n" + "*arg" + i + " = val" + i + ".(" + a.payload.elems.get(i) + ")"
				          		 ).collect(Collectors.joining("\n")) + "\n"
				+ "return " + buildReturn(null, api, succ) + "{}";
	}
}

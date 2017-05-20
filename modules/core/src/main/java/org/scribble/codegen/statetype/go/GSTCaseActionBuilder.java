package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.codegen.statetype.STCaseActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTCaseActionBuilder extends STCaseActionBuilder
{

	@Override
	public String getSTActionName(STAPIBuilder api, EAction a)
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
	public String buildBody(STAPIBuilder api, EState curr, EAction a, EState succ)
	{
		return 
				  IntStream.range(0, a.payload.elems.size())
				           .mapToObj(i -> "val" + i + " := <-role" + api.role + "." + a.peer
				          		 + "\n" + "*arg" + i + " = val" + i + ".(" + a.payload.elems.get(i) + ")"
				          		 ).collect(Collectors.joining("\n")) + "\n"
				+ "return " + buildReturn(null, api, succ) + "{}";
	}
	
	@Override
	protected String getType(STAPIBuilder api, EState curr, EAction a)
	{
		return GSTCaseBuilder.getOpTypeName(a.mid);
	}
}
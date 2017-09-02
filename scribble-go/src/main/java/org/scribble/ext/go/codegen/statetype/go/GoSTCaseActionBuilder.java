package org.scribble.ext.go.codegen.statetype.go;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.codegen.statetype.STCaseActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GoSTCaseActionBuilder extends STCaseActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
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
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		return 
				  IntStream.range(0, a.payload.elems.size())
							.mapToObj(i -> 
										//"val" + i + " := " + api.getChannelName(api, a) + ".Read()\n"
									  "val" + i + " := s.ep.Read(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")\n"
									+ "if s.ep.Err != nil {\n"
									+ "return nil\n"
								  + "}\n"
									+ "*arg" + i + " = val" + i + ".(" + a.payload.elems.get(i) + ")"
							).collect(Collectors.joining("\n")) + "\n"
				+ buildReturn(api, curr, succ);
	}
	
	@Override
	public String getStateChanType(STStateChanApiBuilder api, EState curr, EAction a)
	{
		return GoSTCaseBuilder.getOpTypeName(api, curr, a.mid);
	}
}

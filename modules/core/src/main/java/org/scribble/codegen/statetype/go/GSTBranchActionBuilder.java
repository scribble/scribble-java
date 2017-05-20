package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTBranchActionBuilder extends STBranchActionBuilder
{

	@Override
	public String getSTActionName(STAPIBuilder api, EAction a)
	{
		return "Branch_" + a.peer;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return "";
	}

	@Override
	public String buildReturn(EState curr, STAPIBuilder api, EState succ)
	{
		return api.getSTStateName(curr) + "_Cases";  // FIXME: factor out with case builder
	}

	@Override
	public String buildBody(STAPIBuilder api, EState curr, EAction a, EState succ)
	{
		return 
				  "tmp := <-role" + api.role + "." + a.peer + "\n"
				+ "op := tmp.(" + api.getSTStateName(curr) + "_Enum)\n"  // FIXME: factor out with branch state builder
				+ "switch op {\n"
				+ curr.getActions().stream().map(x -> 
						  "case " + x.mid + ":\n"
						+ "return " + GSTCaseBuilder.getOpTypeName(x.mid) +"{}\n"  // FIXME: factor out
					).collect(Collectors.joining(""))
				+ "}\n"
				+ "return nil";  // FIXME: panic instead
	}
}

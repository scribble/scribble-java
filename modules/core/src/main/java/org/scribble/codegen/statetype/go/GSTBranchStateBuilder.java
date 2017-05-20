package org.scribble.codegen.statetype.go;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTBranchStateBuilder extends STBranchStateBuilder
{
	public GSTBranchStateBuilder(GSTBranchActionBuilder bb)
	{
		super(bb);
	}

	@Override
	public String getPreamble(STAPIBuilder api, EState s)
	{
		String ename = api.getSTStateName(s) + "_Enum";  // FIXME: factor out with branch action builder
		List<EAction> as = s.getActions();
		return GSTOutputStateBuilder.getPremable1(api, s)
				+ "\n"
				+ " type " + ename + " int\n"
				+ "\n"
				+ "const (\n"
				+ as.get(0).mid.toString() + " " + ename + " = iota \n"
				+ as.subList(1, as.size()).stream().map(a -> a.mid.toString()).collect(Collectors.joining("\n")) + "\n"
				+ ")";
	}
}

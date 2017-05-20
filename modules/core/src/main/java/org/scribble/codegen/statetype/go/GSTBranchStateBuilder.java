package org.scribble.codegen.statetype.go;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
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
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		String ename = api.getStateChanName(s) + "_Enum";  // FIXME: factor out with branch action builder
		List<EAction> as = s.getActions();
		return GSTStateChanAPIBuilder.getStateChanPremable(api, s) + "\n"
				+ "\n"
				+ "type " + ename + " int\n"
				+ "\n"
				+ "const (\n"
				+ as.get(0).mid.toString() + " " + ename + " = iota \n"  // FIXME: factor out with send action builder (and use lower+_)
				+ as.subList(1, as.size()).stream().map(a -> a.mid.toString()).collect(Collectors.joining("\n")) + "\n"
				+ ")";
	}
}

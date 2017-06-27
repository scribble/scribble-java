package org.scribble.ext.go.codegen.statetype.go;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.model.endpoint.EState;

public class GSTBranchStateBuilder extends STBranchStateBuilder
{
	public GSTBranchStateBuilder(GSTBranchActionBuilder bb)
	{
		super(bb);
	}

	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		/*String ename = getBranchEnumType(api, s);
		List<EAction> as = s.getActions();*/
		return GSTStateChanAPIBuilder.getStateChanPremable(api, s) /*+ "\n"
				+ "\n"
				+ "type " + ename + " int\n"
				+ "\n"
				+ "const (\n"
				+ getBranchEnumValue(as.get(0).mid) + " " + ename + " = iota \n"
				+ as.subList(1, as.size()).stream().map(a -> getBranchEnumValue(a.mid)).collect(Collectors.joining("\n")) + "\n"
				+ ")"*/;
	}
	
	/*protected static String getBranchEnumType(STStateChanAPIBuilder api, EState s)
	{
		return api.getStateChanName(s) + "_Enum";
	}
	
	protected static String getBranchEnumValue(MessageId<?> mid)
	{
		return "_" + mid;
	}*/
}

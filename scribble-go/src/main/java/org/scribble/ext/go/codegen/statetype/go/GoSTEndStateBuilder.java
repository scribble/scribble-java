package org.scribble.ext.go.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.model.endpoint.EState;

public class GoSTEndStateBuilder extends STEndStateBuilder
{
	public GoSTEndStateBuilder()
	{

	}
	
	@Override
	public String build(STStateChanApiBuilder api, EState s)
	{
		return getPreamble(api, s);
	}

	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		//return GSTStateChanAPIBuilder.getStateChanPremable(api, s);
		String tname = api.getStateChanName(s);
		String res =
				  GoSTStateChanApiBuilder.getPackageDecl((GoSTStateChanApiBuilder) api) + "\n"
				+ "\n"
				+ "import \"org/scribble/runtime/net\"\n"
				+ "\n"
				+ "type " + tname + " struct{\n"
				+ "ep *net.MPSTEndpoint\n"  // FIXME: factor out
				+ "}";
		return res;  // No LinearResource
	}
}

package org.scribble.ext.go.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.model.endpoint.EState;

public class GoSTEndStateBuilder extends STEndStateBuilder
{
	public GoSTEndStateBuilder()
	{

	}
	
	@Override
	public String build(STStateChanAPIBuilder api, EState s)
	{
		return getPreamble(api, s);
	}

	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		//return GSTStateChanAPIBuilder.getStateChanPremable(api, s);
		String tname = api.getStateChanName(s);
		String res =
				  GoSTStateChanAPIBuilder.getPackageDecl(api) + "\n"
				+ "\n"
				+ "import \"org/scribble/runtime/net\"\n"
				+ "\n"
				+ "type " + tname + " struct{\n"
				+ "ep *net.MPSTEndpoint\n"  // FIXME: factor out
				+ "}";
		return res;  // No LinearResource
	}
}

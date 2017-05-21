package org.scribble.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.model.endpoint.EState;

public class GSTEndStateBuilder extends STEndStateBuilder
{
	public GSTEndStateBuilder()
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
				  GSTStateChanAPIBuilder.getPackageDecl(api) + "\n"
				+ "\n"
				+ "import \"org/scribble/runtime/net\"\n"
				+ "\n"
				+ "type " + tname + " struct{\n"
				+ "ep *net.MPSTEndpoint\n"  // FIXME: factor out
				+ "}";
		return res;  // No LinearResource
	}
}

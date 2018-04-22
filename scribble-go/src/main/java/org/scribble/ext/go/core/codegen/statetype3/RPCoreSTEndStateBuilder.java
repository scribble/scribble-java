package org.scribble.ext.go.core.codegen.statetype3;

import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;

public class RPCoreSTEndStateBuilder extends STEndStateBuilder
{
	public RPCoreSTEndStateBuilder()
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
		RPCoreSTStateChanApiBuilder schangen = (RPCoreSTStateChanApiBuilder) api;
		//return GSTStateChanAPIBuilder.getStateChanPremable(api, s);
		String tname = api.getStateChanName(s);
		String res =
				  //schangen.apigen.generateRootPackageDecl() + "\n"
				  "package " + RPCoreSTApiGenerator.getGeneratedRoleVariantName(((RPCoreSTStateChanApiBuilder) api).actual) + "\n"
				+ "\n"
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "\n"
				+ "type " + tname + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
						+ " *" + RPCoreSTApiGenerator.getEndpointKindTypeName(api.gpn.getSimpleName(), schangen.actual) + "\n"  // FIXME: factor out
						//+ " session.ParamEndpoint" + "\n"  // FIXME: factor out
				+ "}";
		return res;  // No LinearResource
	}
}

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
		String scTypeName = api.getStateChanName(s);
		String res =
				  "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(((RPCoreSTStateChanApiBuilder) api).variant) + "\n"
				+ "\n"
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " error\n"

				+ "id uint64\n"

				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *"
						+ RPCoreSTApiGenerator.getEndpointKindTypeName(api.gpn.getSimpleName(), schangen.variant) + "\n"  // FIXME: factor out
						
				+ (((RPCoreSTStateChanApiBuilder) api).apigen.job.parForeach ? "Thread int\n"	: "")
						
				+ "}";
		return res;  // No LinearResource
	}
}

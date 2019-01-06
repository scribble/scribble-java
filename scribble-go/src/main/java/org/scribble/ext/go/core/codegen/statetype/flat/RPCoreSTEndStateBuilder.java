package org.scribble.ext.go.core.codegen.statetype.flat;

import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
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
	public String getPreamble(STStateChanApiBuilder apib, EState s)
	{
		RPCoreSTStateChanApiBuilder rpapib = (RPCoreSTStateChanApiBuilder) apib;
		String scTypeName = apib.getStateChanName(s);
		String res =
				  "package " + rpapib.apigen.namegen.getEndpointKindPackageName(rpapib.variant) + "\n"
				+ "\n"
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " error\n"

				+ "id uint64\n"

				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *"
						+ rpapib.apigen.namegen.getEndpointKindTypeName(rpapib.variant) + "\n"  // FIXME: factor out
						
				+ (((RPCoreSTStateChanApiBuilder) apib).apigen.job.parForeach ? "Thread int\n"	: "")
						
				+ "}";
		return res;  // No LinearResource
	}
}

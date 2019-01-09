package org.scribble.ext.go.core.codegen.statetype.flat;

import org.scribble.codegen.statetype.STEndStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.model.endpoint.EState;

// CHECKME: factor out between "flat" and "nested" APIs?
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
				  "package " + rpapib.parent.namegen.getEndpointKindPackageName(rpapib.variant) + "\n"
				+ "\n"
				//+ "import \"" + ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n"
				+ "id uint64\n"  // TODO: use for session completion check
				+ RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *"
						+ rpapib.parent.namegen.getEndpointKindTypeName(rpapib.variant) + "\n"  // FIXME: factor out
						
				+ (((RPCoreSTStateChanApiBuilder) apib).parent.job.parForeach ? "Thread int\n"	: "")
						
				+ "}";
		return res;  // No LinearResource
	}
}

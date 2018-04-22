package org.scribble.ext.go.core.codegen.statetype3;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STCaseActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class RPCoreSTCaseActionBuilder extends STCaseActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName((RPIndexedRole) a.peer) + "_Recv_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> "arg" + i + " *" + a.payload.elems.get(i)).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		RPIndexedRole peer = (RPIndexedRole) a.peer;  // Singleton interval

		String sEpRecv = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ "[\"" +  peer.getName() + "\"]";

		String extName = rpapi.batesHack(a.payload.elems.get(0));
		
		String res = "";

		// Duplicated from RPCoreSTReceiveActionBuilder
		if (!a.payload.elems.isEmpty())
		{
			if (a.payload.elems.size() > 1)
			{
				throw new RuntimeException("TODO: " + a);
			}

			res += "var tmp " + extName + "\n"  // var tmp needed for deserialization -- FIXME?
				+ (extName.startsWith("[]") ? "tmp = make(" + extName + ", len(*arg0))\n" : "")  // HACK? for passthru?
				+ "if err := " + sEpRecv + "[1]"  // FIXME: use peer interval
						+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL + "(&tmp)"
				+ "; err != nil {\n"
				+ "log.Fatal(err)\n"
				+ "}\n"
				+ "arg0 = &tmp\n";
		}
		return res + buildReturn(rpapi, curr, succ);
	}
	
	@Override
	public String getStateChanType(STStateChanApiBuilder api, EState curr, EAction a)
	{
		return RPCoreSTCaseBuilder.getOpTypeName(api, curr, a.mid);
	}
}

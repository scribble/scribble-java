package org.scribble.ext.go.core.codegen.statetype3;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STCaseActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;
import org.scribble.type.name.MessageSigName;

public class RPCoreSTCaseActionBuilder extends STCaseActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return //RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName((RPIndexedRole) a.peer) + "_" +
				"Recv_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder api, EAction a)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		if (a.mid.isOp())
		{
			return IntStream.range(0, a.payload.elems.size()) 
						.mapToObj(i -> RPCoreSTApiGenConstants.GO_CASE_METHOD_ARG + i
								+ " *" + rpapi.getExtName((DataType) a.payload.elems.get(i)))
						.collect(Collectors.joining(", "));
		}
		else //if (a.mid.isMessageSigName())
		{
			return RPCoreSTApiGenConstants.GO_CASE_METHOD_ARG + "0 *"
					+ rpapi.getExtName((MessageSigName) a.mid);
		}
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		RPIndexedRole peer = (RPIndexedRole) a.peer;  // Singleton interval

		String sEpRecv = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ "[\"" +  peer.getName() + "\"]";
		
		// Duplicated from RPCoreSTReceiveActionBuilder
		Function<String, String> f = extName -> 
				  "var tmp " + extName + "\n"  // var tmp needed for deserialization -- FIXME?
				+ (extName.startsWith("[]") ? "tmp = make(" + extName + ", len(*arg0))\n" : "")  // HACK: []  // N.B. *arg0 matches buildArgs
				+ "if err := " + sEpRecv + "[1]"  // FIXME: use peer interval
						+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL + "(&tmp)"
						+ "; err != nil {\n"
				+ "log.Fatal(err)\n"
				+ "}\n"
				+ "*arg0 = tmp\n";  // N.B. *arg0 matches buildArgs
		
		String res = "";
		if (a.mid.isOp())
		{
			if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[rp-core] [-param-api] TODO: " + a);
				}

				res += f.apply(rpapi.getExtName((DataType) a.payload.elems.get(0)));
			}
		}
		else //if (a.mid.isMessageSigName())
		{
			// FIXME: no -- Branch() should already receive the sig message and case action should just return it
			res += f.apply(rpapi.getExtName((MessageSigName) a.mid));
		}
		return res + buildReturn(rpapi, curr, succ);
	}
	
	@Override
	public String getStateChanType(STStateChanApiBuilder api, EState curr, EAction a)
	{
		return RPCoreSTCaseBuilder.getOpTypeName(api, curr, a.mid);
	}
}

package org.scribble.ext.go.core.codegen.statetype3;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.main.GoJob;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;

public class RPCoreSTReceiveActionBuilder extends STReceiveActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + RPCoreSTApiGenConstants.RP_GATHER_METHOD_PREFIX  // FIXME: make unary Receive special case
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder api, EAction a)
	{
		if (a.mid.isOp())
		{
			return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.GO_CROSS_RECEIVE_METHOD_ARG + i + " []"
							+ ((RPCoreSTStateChanApiBuilder) api).getExtName((DataType) a.payload.elems.get(i))) //a.payload.elems.get(i)
					.collect(Collectors.joining(", "));
		}
		else //if (a.mid.isMessageSigName())
		{
			return "a *" + a.mid;
		}
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		if(a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[param-core] TODO: " + a);
		}

		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		RPIndexedRole peer = (RPIndexedRole) a.peer;
		RPInterval d = peer.intervals.iterator().next();
		if (peer.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + a);
		}

		String sEpRecv = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ "[\"" +  peer.getName() + "\"]";

		String res = "";
		if (a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[param-core] [TODO] payload size > 1: " + a);
		}

		String extName = rpapi.getExtName((DataType) a.payload.elems.get(0));

		if (((GoJob) rpapi.job).noCopy)
		{
			/*res += "data := make([]" + a.payload.elems.get(0) + ", len(b))\n"
				+ "for i := 0; i < len(b); i++ {\n"
				+ "data[i] = *b[i].(*" + a.payload.elems.get(0) + ")\n"
				+ "}\n"
				+ "*arg0 = reduceFn0(data)\n";*/
			throw new RuntimeException("[rp-core] TODO: -nocopy: " + a);  // FIXME: currently missing from sender side?
		}
		else
		{
			String start = RPCoreSTStateChanApiBuilder.generateIndexExpr(d.start);
			res += "for i := " + start + ";"
					+ " i <= " + RPCoreSTStateChanApiBuilder.generateIndexExpr(d.end) + "; i++ {\n";

			if (!a.mid.toString().equals("")) // HACK FIXME?
			{ 
				res += "var lab string\n"  // var decl needed for deserializatoin -- FIXME?
						+ "if err := " + sEpRecv + "[i]"
						+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL
								+ "(" + "&lab" + ")"
						+ "; err != nil {\n"
						+ "log.Fatal(err)\n"
						+ "}\n";
			}

			if (!a.payload.elems.isEmpty())
			{
				res += "var tmp " + extName + "\n"  // tmp var decl needed for deserialization -- FIXME?
					+ (extName.startsWith("[]") ? "tmp = make(" + extName + ", len(arg0))\n" : "")  // HACK? for passthru?
					+ "if err := " + sEpRecv + "[i]"
					+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL
							+ "(&tmp)"
					+ "; err != nil {\n"
					+ "log.Fatal(err)\n"
					+ "}\n"
					+ "arg0[i-" + start + "] = tmp\n"
					+ "}\n";
			}
		}
				
		return res + buildReturn(rpapi, curr, succ);
	}
	
	/*protected static String hackGetValues(String t)
	{
		if (t.equals("int"))
		{
			return "util.GetValuesInt";
		}
		else if (t.equals("string"))
		{
			return "util.GetValuesString";
		}
		else if (t.equals("[]byte"))
		{
			return "util.GetValuesBates";
		}
		else
		{
			throw new RuntimeException("[TODO] " + t);
			//return t;
		}
	}*/
}

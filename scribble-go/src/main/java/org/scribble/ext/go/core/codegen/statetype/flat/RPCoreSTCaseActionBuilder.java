package org.scribble.ext.go.core.codegen.statetype.flat;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STCaseActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
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
						.mapToObj(i -> RPCoreSTApiGenConstants.API_CASE_ARG + i
								+ " *" + rpapi.getExtName((DataType) a.payload.elems.get(i)))
						.collect(Collectors.joining(", "));
		}
		else //if (a.mid.isMessageSigName())
		{
			return RPCoreSTApiGenConstants.API_CASE_ARG + "0 *"
					+ rpapi.getExtName((MessageSigName) a.mid);
		}
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		RPIndexedRole peer = (RPIndexedRole) a.peer;  // Singleton interval

		String sEpRecv = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD
				+ "." + RPCoreSTApiGenConstants.ENDPOINT_MPCHAN_FIELD; /*+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP
				+ "[\"" +  peer.getName() + "\"]";*/
		
		// Duplicated from RPCoreSTReceiveActionBuilder
		RPInterval d = peer.intervals.iterator().next();
		if (peer.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + a);
		}
		if (!d.isSingleton())
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + a);
		}
		// makeCaseReceive is called only for payload type which involves the
		// use of IRecv. The caller is Recv_Label(arg0 *T) for Label(T) message.
		// IRecv accepts pointer as parameter so passing arg0 here (of type *T)
		// instead of &arg0 (or type **T).
		Function<String, String> makeCaseReceive = pt -> 
				//+ (extName.startsWith("[]") ? "tmp = make(" + extName + ", len(*arg0))\n" : "")  // HACK: []  // N.B. *arg0 matches buildArgs
				  "if err := " + sEpRecv /*+ "[1]"  // FIXME: use peer interval
						+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_READALL + "(&tmp)"*/
						+ "." + RPCoreSTApiGenConstants.MPCHAN_IRECV + "(\"" + peer.getName() + "\", "
								+ rpapi.generateIndexExpr(d.start) + ", arg0)"
						+ "; err != nil {\n"
				//+ "log.Fatal(err)\n"
				//+ "return " + rpapi.makeCreateSuccStateChan(succ) + "\n"  // FIXME: disable linearity check for error chan?  Or doesn't matter -- only need to disable completion check?
				+ rpapi.makeReturnSuccStateChan(succ) + "\n"
				+ "}\n";
				//+ "*arg0 = tmp.(" + extName + ")\n";  // N.B. *arg0 matches buildArgs
				//+ "*arg0 = *(tmp.(*" + pt + "))\n";  // Cf. RPCoreSTReceiveActionBuilder  // N.B. *arg0 matches buildArgs

		String res = "";
		if (a.mid.isOp())
		{
			if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[rp-core] [-param-api] TODO: " + a);
				}

				res += makeCaseReceive.apply(rpapi.getExtName((DataType) a.payload.elems.get(0)));  // Payload "type"
			}
		}
		else //if (a.mid.isMessageSigName())
		{
			//res += f.apply(rpapi.getExtName((MessageSigName) a.mid));
					// FIXME: no -- Branch() should already receive the sig message and case action should just return it
			res += "*arg0 = *s.msg\n";
		}
		return res + buildReturn(rpapi, curr, succ);
	}
	
	@Override
	public String getStateChanType(STStateChanApiBuilder api, EState curr, EAction a)
	{
		return RPCoreSTCaseBuilder.getOpTypeName(api, curr, a.mid);
	}
}

package org.scribble.ext.go.core.codegen.statetype.nested;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.main.GoJob;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.MessageSigName;

public class RPCoreNReceiveActionBuilder extends STReceiveActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder scb, EAction a)
	{
		return ((RPCoreSTStateChanApiBuilder) scb).parent.namegen
					.getGeneratedIndexedRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + RPCoreSTApiGenConstants.API_GATHER_PREFIX  // FIXME: make unary Receive special case
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder api, EAction a)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		if (a.mid.isOp())
		{
			return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.API_RECEIVE_ARG + i + " []"
							+ rpapi.getPayloadElemTypeName(a.payload.elems.get(i)))
					.collect(Collectors.joining(", "));
		}
		else //if (a.mid.isMessageSigName())
		{
			return RPCoreSTApiGenConstants.API_RECEIVE_ARG + "0 []"
					+ rpapi.getExtName((MessageSigName) a.mid);
		}
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		// Duplicated form RPCoreNSendActionBuilder
		String schan = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER;
		if (((RPCoreSTStateChanApiBuilder) api).parent.job.dotApi)
		{
			schan += ".schan";
		}

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

		String sEp = schan + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
		String sEpRecv = sEp
				+ "." + RPCoreSTApiGenConstants.ENDPOINT_MPCHAN_FIELD; /*+ "." //+ RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP
				+ "[\"" +  peer.getName() + "\"]";*/

		String res = "";
		if (a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[param-core] [TODO] payload size > 1: " + a);
		}

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

			// TODO: factor out with send, etc.
			String lte;
			String inc;
			switch (rpapi.parent.mode)
			{
				case Int:  
				{
					lte = " <= " + rpapi.generateIndexExpr(d.end);  
					inc = "i+1";
					break;
				}
				case IntPair:  
				{
					lte = ".Lte(" + rpapi.generateIndexExpr(d.end) + ")";  
					inc = "i.Inc(" + rpapi.generateIndexExpr(d.end) + ")"; 
					break;
				}
				default:  throw new RuntimeException("Shouldn't get in here: " + rpapi.parent.mode);
			}

			String start = rpapi.generateIndexExpr(d.start);
			res += //"var err error\n"
					  "for i, j := " + start + ", 0;"
					+ " i " + lte + "; i, j = " + inc + ", j+1 {\n";

			// For payloads -- FIXME: currently hardcoded for exactly one payload

			String errorField = sEp + "."
                                + "_" + rpapi.getSuccStateChanName(succ) + "."
                                + RPCoreSTApiGenConstants.SCHAN_ERR_FIELD;

			if (a.mid.isOp())
			{
				//if (!a.mid.toString().equals("")) // HACK FIXME?  // Now redundant, -param-api checks mid starts uppercase
				{ 
					res += "var lab string\n"
							+ "if " + errorField + " = " + sEpRecv /*+ "[i]"
									+ "." //+ RPCoreSTApiGenConstants.GO_ENDPOINT_READALL + "(" + "&lab" + ")"
											+ RPCoreSTApiGenConstants.GO_FORMATTER_DECODE_STRING + "()"*/
									+ "." + RPCoreSTApiGenConstants.MPCHAN_IRECV + "(\"" + peer.getName() + "\", i, &lab)" 
									+ "; " + errorField + " != nil {\n"
							//+ "log.Fatal(err)\n"
							//+ "return " + rpapi.makeCreateSuccStateChan(succ) + "\n"  // FIXME: disable linearity check for error chan?  Or doesn't matter -- only need to disable completion check?
							+ rpapi.makeReturnSuccStateChan(succ) + "\n"
							+ "}\n";
				}

				if (!a.payload.elems.isEmpty())
				{
					if (a.payload.elems.size() > 1)
					{
						throw new RuntimeException("[rp-core] [-param-api] TODO: " + a);
					}

					Function<String, String> makeReceivePayType = pt -> 
							//+ (extName.startsWith("[]") ? "tmp = make(" + extName + ", len(arg0))\n" : "")  // HACK? for passthru?
							"if " + errorField + " = " + sEpRecv /*+ "[i]"  // FIXME: use peer interval
									+ "." //+ RPCoreSTApiGenConstants.GO_ENDPOINT_READALL + "(&tmp)"
									+ RPCoreSTApiGenConstants.GO_FORMATTER_DECODE_INT + "()"*/
									+ "." + RPCoreSTApiGenConstants.MPCHAN_IRECV + "(\"" + peer.getName() + "\", i, &arg0[j])"
					
							+ "; " + errorField + " != nil {\n"
							//+ "log.Fatal(err)\n"
							//+ "return " + rpapi.makeCreateSuccStateChan(succ) + "\n"  // FIXME: disable linearity check for error chan?  Or doesn't matter -- only need to disable completion check?
							+ rpapi.makeReturnSuccStateChan(succ) + "\n"
							+ "}\n";
							// + "arg0[i-" + start + "] = *(tmp.(*" + pt + "))\n";  // FIXME: doesn't work for gob, pointer decoding seems flattened? ("*" dropped) ...  // Cf. ISend in RPCoreSTSendActionBuilder
							//+ "arg0[i-" + start + "] = tmp.(" + pt + ")\n";  // FIXME: ... but doesn't work for shm
					res += makeReceivePayType.apply(rpapi.getPayloadElemTypeName(a.payload.elems.get(0)));
				}
			}
			else //if (a.mid.isMessageSigName())
			{
				Function<String, String> makeReceiveExtName = extName -> 
							"var tmp " + RPCoreSTApiGenConstants.SCRIB_MESSAGE_TYPE + "\n"  // var tmp needed for deserialization -- FIXME?
						+ "if " + errorField + " = " + sEpRecv /*+ "[i]"  // FIXME: use peer interval
								+ RPCoreSTApiGenConstants.GO_FORMATTER_DECODE_INT + "()"*/
								+ "." + RPCoreSTApiGenConstants.MPCHAN_MRECV + "(\"" + peer.getName() + "\", i, &tmp)" 
				
						+ "; " + errorField + " != nil {\n"
						//+ "log.Fatal(err)\n"
						//+ "return " + rpapi.makeCreateSuccStateChan(succ) + "\n"  // FIXME: disable linearity check for error chan?  Or doesn't matter -- only need to disable completion check?
						+ rpapi.makeReturnSuccStateChan(succ) + "\n"
						+ "}\n"
						+ "arg0[j] = *(tmp.(*" + extName + "))\n";  // Cf. ISend in RPCoreSTSendActionBuilder
				res += makeReceiveExtName.apply(rpapi.getExtName((MessageSigName) a.mid));
			}
		
			res += "}\n";
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

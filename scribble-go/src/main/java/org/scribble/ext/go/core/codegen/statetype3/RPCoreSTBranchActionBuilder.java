package org.scribble.ext.go.core.codegen.statetype3;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.MessageSigName;

public class RPCoreSTBranchActionBuilder extends STBranchActionBuilder
{
	// Called by STBranchStateBuilder#build on .get(1) action (a hack for Branch states)
	@Override
	public String build(STStateChanApiBuilder api, EState curr, EAction a)  // FIXME: "overriding" STStateChanAPIBuilder.buildAction to hack around *interface return  // FIXME: factor out
	{
		//RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		//String scTypeName = rpapi.getStateChanName(curr);

		EState succ = curr.getSuccessor(a); 
		return
				  "func (s *" + getStateChanType(api, curr, a) + ") " + getActionName(api, a) + "(" 
						+ buildArgs(null, a)
						+ ") " + getReturnType(api, curr, succ) + " {\n"  // HACK: return type is interface, so no need for *return (unlike other state chans)
				+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
						+ "." + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
				+ buildBody(api, curr, a, succ) + "\n"
				+ "}";
	}

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName((RPIndexedRole) a.peer) + "_Branch";
	}

	@Override
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		return "";
	}

	@Override
	public String getReturnType(STStateChanApiBuilder api, EState curr, EState succ)
	{
		return api.cb.getCaseStateChanName(api, curr);
	}

	// "a" and "succ" are the .get(1) action (a hack for Branch states)
	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		List<EAction> as = curr.getAllActions();
		if (!as.stream().allMatch(x -> x.mid.isOp()) && !as.stream().allMatch(x -> x.mid.isMessageSigName()))
		{
			throw new RuntimeException("[rp-core] [-param-api] Mixed choice of message sig and sig names not supported: " + as);
		}

		RPIndexedRole peer = (RPIndexedRole) a.peer;  // Singleton interval
		String sEpRecv = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN; /*+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP
				+ "[\"" + peer.getName() + "\"]";*/
				
		String res = "";
			
		// FIXME: factor out with RPCoreSTStateChanApiBuilder#getSuccStateChan -- don't need terminal check for succ though
		String ret = RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": "
				+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ", "
				+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + ": new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")";
		
		if (a.mid.isOp())  // Currently, assuming all mids are Ops; else all mids are sig names
		{
			// Duplicated from RPCoreSTReceiveActionBuilder
			res += "var lab interface{}\n"  // string  // cf. RPCoreSTReceiveActionBuilder // var decl needed for deserializatoin -- FIXME?
					+ "if err := " + sEpRecv /*+ "[1]"  // FIXME: use peer interval
					+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_IRECV + "(" + "&lab" + ")"*/
					+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_IRECV + "(\"" + peer.getName() + "\", 1, &lab" + ")"
							+ "; err != nil {\n"
					+ "log.Fatal(err)\n"
					+ "}\n";

			// Switch and return Cases value
			res += "\n"
					+ "switch lab {\n"
					+ as.stream().map(x -> 
								"case \"" + x.mid + "\":\n" + "return &" + RPCoreSTCaseBuilder.getOpTypeName(api, curr, x.mid)
							//+ "{ Ept: s.Ept, Res: new(session.LinearResource) }\n"
							+ "{" + ret + "}\n"
						).collect(Collectors.joining(""))
					+ "default: panic(\"Shouldn't get in here: \" + lab.(string))\n"
					+ "}\n"
					+ "return nil\n";  // FIXME: panic instead
		}
		else //if (a.mid.isMessageSigName())
		{
			// FIXME: factor out futher (receive, case)
			res += //"var msg session.T\n"  // var decl needed for deserialization -- FIXME?
					  "var msg session2.ScribMessage\n"
					+ "if err := " + sEpRecv + "[1]"  // FIXME: use peer interval
					+ "." //+ RPCoreSTApiGenConstants.GO_MPCHAN_READALL + "(" + "&msg" + ")"
							+ RPCoreSTApiGenConstants.GO_MPCHAN_MRECV + "(\"" + peer.getName() + "\", 1, &msg" + ")"
							+ "; err != nil {\n"
					+ "log.Fatal(err)\n"
					+ "}\n";

			res += "\n"
					+ "switch x := msg.(type) {\n"
					+ as.stream().map(x ->
							  "case " + ((RPCoreSTStateChanApiBuilder) api).getExtName((MessageSigName) x.mid) + ":\n"
							+ "return &" + RPCoreSTCaseBuilder.getOpTypeName(api, curr, x.mid) + " { "
							+ ret + ", msg: &x }\n"
						).collect(Collectors.joining(""))
					+ "default: panic(\"Shouldn't get in here: \" + reflect.TypeOf(msg).String())\n"
					+ "}\n"
					+ "return nil\n";  // FIXME: panic instead
		}

		return res;
	}
}

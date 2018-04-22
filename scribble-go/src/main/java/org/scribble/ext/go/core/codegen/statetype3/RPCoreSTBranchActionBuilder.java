package org.scribble.ext.go.core.codegen.statetype3;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class RPCoreSTBranchActionBuilder extends STBranchActionBuilder
{
	@Override
	public String build(STStateChanApiBuilder api, EState curr, EAction a)  // FIXME: "overriding" STStateChanAPIBuilder.buildAction to hack around *interface return  // FIXME: factor out
	{
		//RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		//String scTypeName = rpapi.getStateChanName(curr);

		EState succ = curr.getSuccessor(a); 
		return
				  "func (s *" + getStateChanType(api, curr, a) + ") " + getActionName(api, a) + "(" 
				+ buildArgs(null, a)
				+ ") " + getReturnType(api, curr, succ) + " {\n"  // HACK: Return type is interface, so no need for *return (unlike other state chans)
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

	// CHECKME: what is succ for Branch action?
	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		//RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		RPIndexedRole peer = (RPIndexedRole) a.peer;  // Singleton interval
		String sEpRecv = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ "[\"" + peer.getName() + "\"]";

				  /*//"tmp := " + api.getChannelName(api, a) + ".Read()\n"
				  "tmp := s.ep.Read(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")\n"
				//+ "op := tmp.(" + GSTBranchStateBuilder.getBranchEnumType(api, curr) + ")\n"
				+ "if s.ep.Err != nil {\n"
				+ "return nil\n"
				+ "}\n"
				+ "op := tmp.(string)\n"*/
				
		String res = "";

		// Duplicated from RPCoreSTReceiveActionBuilder
		res += "var lab string\n"  // var decl needed for deserializatoin -- FIXME?
				+ "if err := " + sEpRecv + "[1]"  // FIXME: use peer interval
				+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL
						+ "(" + "&lab" + ")"
				+ "; err != nil {\n"
				+ "log.Fatal(err)\n"
				+ "}\n";

		res += "\n"
				+ "switch lab {\n"
				+ curr.getActions().stream().map(x -> 
						  "case \"" + x.mid + "\":\n"
						+ "return &" + RPCoreSTCaseBuilder.getOpTypeName(api, curr, x.mid) +"{ Ept: s.Ept, Res: new(session.LinearResource) }\n"  // FIXME: factor out with RPCoreSTStateChanApiBuilder buildActionReturn/getSuccStateChan
						//+ rpapi.buildActionReturn(((RPCoreSTReceiveStateBuilder) rpapi.rb).vb, curr, curr.getSuccessor(a)) + "\n"  // No: need to return Cases object, not state chan
					).collect(Collectors.joining(""))
				+ "default: panic(\"Shouldn't get in here: \" + lab)\n"
				+ "}\n"
				+ "return nil";  // FIXME: panic instead

		return res;
	}
}

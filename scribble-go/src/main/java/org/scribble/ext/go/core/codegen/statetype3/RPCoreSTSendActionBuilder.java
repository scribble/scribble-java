package org.scribble.ext.go.core.codegen.statetype3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;

public class RPCoreSTSendActionBuilder extends STSendActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + RPCoreSTApiGenConstants.RP_SCATTER_METHOD_PREFIX  // FIXME: make unary Send special case
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		System.out.println("aaa: "+ a + ", " + a.mid + ", " + (a.mid.getClass()) + ", " + a.mid.isOp());
		if (a.mid.isOp())
		{
			return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.GO_CROSS_SEND_METHOD_ARG + i + " []"
							+ ((RPCoreSTStateChanApiBuilder) apigen).getExtName((DataType) a.payload.elems.get(i))) //a.payload.elems.get(i)
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

		List<EAction> as = curr.getActions();
		if (as.size() > 1 && as.stream().anyMatch(b -> b.mid.toString().equals("")))  // HACK
		{
			throw new RuntimeException("[param-core] Empty labels not allowed in non-unary choices: " + curr.getActions());
		}

		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		RPIndexedRole r = (RPIndexedRole) a.peer;
		RPInterval d = r.intervals.iterator().next();
		if (r.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + a);
		}
		
		String sEpWrite = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ "[\"" + r.getName() + "\"]";

		// FIXME: single arg  // Currently never true because of RPCoreSTOutputStateBuilder
		boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParamCoreDelegDecl, not GDelegationElem
				rpapi.isDelegType((DataType) pet));
		
		String res = "for i, j := " + RPCoreSTStateChanApiBuilder.generateIndexExpr(d.start) + ", 0;"
				+ " i <= " + RPCoreSTStateChanApiBuilder.generateIndexExpr(d.end)+"; i, j = i+1, j+1 {\n";

		// Write label
		if (!a.mid.toString().equals("")) {  // HACK FIXME?
			res += "if err := " + sEpWrite + "[i]"
						+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL + "(\"" + a.mid + "\"" + ")" 
				+ "; err != nil {\n"
				+ "log.Fatal(err)\n"  // FIXME
				+ "}\n";
		}

		// Write payload
		if (isDeleg)  //... FIXME: delegation: take pointer?  send underlying ept? -- don't do (multi)"send"?
		{
			res += "log.Fatal(\"TODO\")\n";
		}
		else
		{
			res += "if err := " + sEpWrite + "[i]"
					+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL
							+ "(" + "arg0[j])"  // FIXME: hardcoded arg0
					+ "; err != nil {\n"
					+ "log.Fatal(err)\n"
					+ "}\n";
		}

		res += "}\n";
				
		return res + buildReturn(api, curr, succ);
	}
}

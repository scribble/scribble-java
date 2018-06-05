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
import org.scribble.type.name.MessageSigName;

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
	public String buildArgs(STStateChanApiBuilder api, EAction a)
	{
		if (a.mid.isOp())
		{
			return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.GO_CROSS_SEND_METHOD_ARG + i + " []"
							+ ((RPCoreSTStateChanApiBuilder) api).getExtName((DataType) a.payload.elems.get(i))) //a.payload.elems.get(i)
					.collect(Collectors.joining(", "));
		}
		else //if (a.mid.isMessageSigName())
		{
			return RPCoreSTApiGenConstants.GO_CROSS_SEND_METHOD_ARG + "0 []"
					+ ((RPCoreSTStateChanApiBuilder) api).getExtName((MessageSigName) a.mid);
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
				+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN; /*+ "." //+ RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP
				+ "[\"" + r.getName() + "\"]";*/

		// FIXME: single arg  // Currently never true because of RPCoreSTOutputStateBuilder
		boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParamCoreDelegDecl, not GDelegationElem
				rpapi.isDelegType((DataType) pet));
		
		String res = "for i, j := " + rpapi.generateIndexExpr(d.start) + ", 0;"
				+ " i <= " + rpapi.generateIndexExpr(d.end)+"; i, j = i+1, j+1 {\n";

		String errorField = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "."
                            + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "."
                            + "_" + rpapi.getSuccStateChanName(succ) + "."
                            + RPCoreSTApiGenConstants.GO_MPCHAN_ERR;

		if (a.mid.isOp())
		{
			// Write label
			if (!a.mid.toString().equals("")) {  // HACK FIXME?
				res += "op := \"" + a.mid + "\"\n"  // FIXME: API constant?
							+ "if " + errorField + " = " + sEpWrite /*+ "[i]"
							+ "." //+ RPCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL
							+ RPCoreSTApiGenConstants.GO_FORMATTER_ENCODE_STRING
							+ "(\"" + a.mid + "\"" + ")" */
							+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_ISEND + "(\"" + r.getName() + "\", i, &op)"
							+ "; " + errorField + " != nil {\n"
					//+ "log.Fatal(err)\n"  // FIXME
					//+ "return " + rpapi.makeCreateSuccStateChan(succ) + "\n"  // FIXME: disable linearity check for error chan?  Or doesn't matter -- only need to disable completion check?
					+ rpapi.makeReturnSuccStateChan(succ) + "\n"
					+ "}\n";
			}
		}

		// Write message sig or payload
		if (isDeleg)  //... FIXME: delegation: take pointer?  send underlying ept? -- don't do (multi)"send"?
		{
			res += "log.Fatal(\"TODO\")\n";
		}
		else
		{
			if (a.mid.isOp() && a.payload.elems.size() < 1)
			{
				throw new RuntimeException("[rp-core] [param-api] TODO: " + a);
			}
			if (a.payload.elems.size() > 1)
			{
				throw new RuntimeException("[rp-core] [param-api] TODO: " + a);
			}
			
			/*res += "if err := " + sEpWrite /*+ "[i]"
							+ "." //+ RPCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL
							+ RPCoreSTApiGenConstants.GO_FORMATTER_ENCODE_INT
							+ "(" + "arg0[j])"  // FIXME: hardcoded arg0* /
							+ "." 
							+ (a.mid.isOp() ? RPCoreSTApiGenConstants.GO_MPCHAN_ISEND : RPCoreSTApiGenConstants.GO_MPCHAN_MSEND)
							+ "(\"" + r.getName() + "\", i, &arg0[j])"
							+ "; err != nil {\n"
					+ "log.Fatal(err)\n"
					+ "}\n";*/
			res += errorField + " = "
					+ sEpWrite
                    + "."
                    + (a.mid.isOp() ? RPCoreSTApiGenConstants.GO_MPCHAN_ISEND : RPCoreSTApiGenConstants.GO_MPCHAN_MSEND)
                    + "(\"" + r.getName() + "\", i, &arg0[j])\n";
		}

		res += "}\n";
				
		return res + buildReturn(api, curr, succ);
	}
}

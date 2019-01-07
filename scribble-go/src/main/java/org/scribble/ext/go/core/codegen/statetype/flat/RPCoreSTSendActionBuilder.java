package org.scribble.ext.go.core.codegen.statetype.flat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.MessageSigName;

public class RPCoreSTSendActionBuilder extends STSendActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + RPCoreSTApiGenConstants.API_SCATTER_PREFIX  // FIXME: make unary Send special case
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder api, EAction a)
	{
		RPCoreSTStateChanApiBuilder apigen = (RPCoreSTStateChanApiBuilder) api;
		if (a.mid.isOp())
		{
			return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.API_SEND_ARG + i + " []"
							+ apigen.getPayloadElemTypeName(a.payload.elems.get(i))) //a.payload.elems.get(i)
					.collect(Collectors.joining(", "));
		}
		else //if (a.mid.isMessageSigName())
		{
			return RPCoreSTApiGenConstants.API_SEND_ARG + "0 []"
					+ apigen.getExtName((MessageSigName) a.mid);
		}
	}

	/**
	 * checkError helper function surrounds the given expression expr with Go's error check
	 * and assigns errorField with err if it exists, i.e.
	 *
	 * <pre>{@code
	 * if err := $expression; err != nil {
	 * 	 $errField = err
	 * }
	 * }</pre>
	 *
	 * @param expr Expression that returns error
	 * @param errField Location to store the error
	 * @return code fragment of expr with error check
	 */
	private String checkError(String expr, String errField) {
		return "if err := " + expr + "; err != nil {\n" + errField + " = err\n" + "}\n";
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
		
		String sEpWrite = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD
				+ "." + RPCoreSTApiGenConstants.ENDPOINT_MPCHAN_FIELD; /*+ "." //+ RPCoreSTApiGenConstants.GO_CONNECTION_MAP
				+ RPCoreSTApiGenConstants.GO_MPCHAN_FORMATTER_MAP
				+ "[\"" + r.getName() + "\"]";*/

		// FIXME: single arg  // Currently never true because of RPCoreSTOutputStateBuilder
		//boolean isDeleg = 
		a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParamCoreDelegDecl, not GDelegationElem
				rpapi//.isDelegType((DataType) pet));
							.isDelegType(pet));
		
		// TODO: factor out with receive, etc.
		String lte;
		String inc;
		switch (rpapi.apigen.mode)
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
			default:  throw new RuntimeException("Shouldn't get in here: " + rpapi.apigen.mode);
		}
		
		String res = "for i, j := " + rpapi.generateIndexExpr(d.start) + ", 0;"
				+ " i" + lte + "; i, j = " + inc + ", j+1 {\n";

		String errorField = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
                            + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + "."
                            + "_" + rpapi.getSuccStateChanName(succ) + "."
                            + RPCoreSTApiGenConstants.SCHAN_ERR_FIELD;

		if (a.mid.isOp())
		{
			// Write label
			if (!a.mid.toString().equals("")) {  // HACK FIXME?
				res += "op := \"" + a.mid + "\"\n"  // FIXME: API constant?
							+ "if " + errorField + " = " + sEpWrite /*+ "[i]"
							+ "." //+ RPCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL
							+ RPCoreSTApiGenConstants.GO_FORMATTER_ENCODE_STRING
							+ "(\"" + a.mid + "\"" + ")" */
							+ "." + RPCoreSTApiGenConstants.MPCHAN_ISEND + "(\"" + r.getName() + "\", i, &op)"
							+ "; " + errorField + " != nil {\n"
					//+ "log.Fatal(err)\n"  // FIXME
					//+ "return " + rpapi.makeCreateSuccStateChan(succ) + "\n"  // FIXME: disable linearity check for error chan?  Or doesn't matter -- only need to disable completion check?
					+ rpapi.makeReturnSuccStateChan(succ) + "\n"
					+ "}\n";
			}
		}

		// Write message sig or payload
		/*if (isDeleg)  //... FIXME: delegation: take pointer?  send underlying ept? -- don't do (multi)"send"?
		{
			res += "log.Fatal(\"TODO\")\n";
		}
		else*/
		{
			/*if (a.mid.isOp() && a.payload.elems.size() < 1)
			{
				throw new RuntimeException("[rp-core] [param-api] TODO: " + a);
			}*/
			if (a.mid.isOp())
			{
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
				if (!a.payload.elems.isEmpty())
				{
					if (a.payload.elems.size() > 1)
					{
						throw new RuntimeException("[rp-core] [param-api] TODO: " + a);
					}
				
					res +=  checkError(sEpWrite
												+ "."
												+ (a.mid.isOp() ? RPCoreSTApiGenConstants.MPCHAN_ISEND : RPCoreSTApiGenConstants.MPCHAN_MSEND)
												+ "(\"" + r.getName() + "\", i" 
												+ IntStream.range(0, a.payload.elems.size()).mapToObj(i -> ", &arg" + i + "[j]").collect(Collectors.joining(""))
												+ ")", errorField);
				}
			}
			else //(a.mid.isMessageSigName)
			{
				// FIXME: factor out with above
				res += checkError( sEpWrite
											+ "."
											+ (a.mid.isOp() ? RPCoreSTApiGenConstants.MPCHAN_ISEND : RPCoreSTApiGenConstants.MPCHAN_MSEND)
											+ "(\"" + r.getName() + "\", i, &arg0[j])", errorField);
			}
		}

		res += "}\n";
				
		return res + buildReturn(api, curr, succ);
	}
}

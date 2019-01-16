package org.scribble.ext.go.core.codegen.statetype.nested;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STCaseActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;
import org.scribble.type.name.MessageSigName;

public class RPCoreNCaseActionBuilder extends STCaseActionBuilder
{
	// HACK FIXME: overriding because don't want to treat -dotapi for case objects
	@Override
	public String build(STStateChanApiBuilder scb, EState curr, EAction a)  // FIXME: "overriding" STStateChanAPIBuilder.buildAction to hack around *interface return  // FIXME: factor out
	{
		RPCoreSTStateChanApiBuilder rpscb = (RPCoreSTStateChanApiBuilder) scb;
		
		EState succ = curr.getSuccessor(a);
		String schan = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER;
		Map<RPIndexedRole, Set<String>> menu = null;
		if (false)
		{
			schan += ".schan";
			menu = rpscb.parent.getStateChanMenu((RPCoreEState) curr);
		}

		RPCoreEAction rpa = (RPCoreEAction) a;
		if (false && menu.get(rpa.getPeer()).size() > 1)
		{
			throw new RuntimeException("[rp-core][-dotapi] TODO: " + a);
		}
		String res = "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER
				+ " *" 
				// FIXME: change peer-action map to key on actual action
					+ (false
							? menu.get(rpa.getPeer()).iterator().next() + "_" + curr.id   // HACK FIXME
							: getStateChanType(rpscb, curr, a)) 
					+ ") " + getActionName(rpscb, a) + "(" 
					+ buildArgs(rpscb, a)
					+ ") *" //+ ab.getReturnType(this, curr, succ)  // No: uses getStateChanName, which returns intermed name for nested states
					+ rpscb.getSuccStateChanName(succ)
					+ " {\n"

				  // FIXME: currently redundant for case objects (cf. branch action err handling)
				+ "if " + schan + "."
					+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " != nil {\n" + "panic("
					+ schan + "."
					+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + ")\n"				+ "}\n";

		if (rpscb.parent.job.parForeach)
		{
			res += schan + "."
					+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + "."
					+ RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
		}
		else
		{
			// HACK FIXME: pre-create case objects
			res += schan + "." + RPCoreSTApiGenConstants.SCHAN_RES_FIELD + "."
							+ RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
				/*+ "atomic.AddUint64(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
						+ "." + "lin" + ")\n"*/
		}

		// Delegate back to action builder
		res += buildBody(rpscb, curr, a, succ) + "\n"
				+ "}";

		return res;
	}

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return //RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName((RPIndexedRole) a.peer) + "_" +
				//"Recv_" + 
				a.mid.toString();
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
	public String buildBody(STStateChanApiBuilder scb, EState curr, EAction a, EState succ)
	{
		// Duplicated from RPCoreSTStateChanApiBuilder#buildAction
		String schan = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER;
		/*if (((RPCoreSTStateChanApiBuilder) api).parent.job.dotApi)
		{
			schan += ".schan";
		}*/

		RPCoreSTStateChanApiBuilder rpscb = (RPCoreSTStateChanApiBuilder) scb;
		RPIndexedRole peer = (RPIndexedRole) a.peer;  // Singleton interval

		String sEp = schan + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
		String sEpRecv = sEp
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
								+ rpscb.generateIndexExpr(d.start, true) + ", arg0)"
						+ "; err != nil {\n"
				//+ "log.Fatal(err)\n"
				//+ "return " + rpapi.makeCreateSuccStateChan(succ) + "\n"  // FIXME: disable linearity check for error chan?  Or doesn't matter -- only need to disable completion check?
				+ //rpscb.makeReturnSuccStateChan(succ) 
					rpscb.makeReturnSuccStateChan(rpscb.getSuccStateChanName(succ), false)
					+ "\n"
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

				res += makeCaseReceive.apply(rpscb.getExtName((DataType) a.payload.elems.get(0)));  // Payload "type"
			}
		}
		else //if (a.mid.isMessageSigName())
		{
			//res += f.apply(rpapi.getExtName((MessageSigName) a.mid));
					// FIXME: no -- Branch() should already receive the sig message and case action should just return it
			res += "*arg0 = *s.msg\n";
		}
		return res + //buildReturn(rpapi, curr, succ);
			rpscb.makeReturnSuccStateChan(rpscb.getSuccStateChanName(succ), false);
	}
	
	@Override
	public String getStateChanType(STStateChanApiBuilder api, EState curr, EAction a)
	{
		return RPCoreNCaseBuilder.getOpTypeName(api, curr, a.mid);
	}
}

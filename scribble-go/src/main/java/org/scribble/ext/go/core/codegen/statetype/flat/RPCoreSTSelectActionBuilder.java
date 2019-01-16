package org.scribble.ext.go.core.codegen.statetype.flat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder.RPCoreEStateKind;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.main.GoJob;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;

// Case/Recv action on select case objects
// N.B. Select means Go select statement for input-choice; not output-choice
public class RPCoreSTSelectActionBuilder extends STBranchActionBuilder
{
	@Override
	public String build(STStateChanApiBuilder api, EState curr, EAction a)
	{
		EState succ = curr.getSuccessor(a);
		if (//((GoJob) api.job).selectApi &&
		RPCoreSTStateChanApiBuilder
				.getStateKind(curr) == RPCoreEStateKind.CROSS_RECEIVE
				&& curr.getActions().size() > 1)
		{
			// HACK FIXME: move to action builder
			return
					  "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER
								+ " *" + getStateChanType(api, curr, a) + ") " + getActionName(api, a) + "(" 
								+ buildArgs(api, a)
								+ ") <-chan *" + getReturnType(api, curr, succ) + " {\n"
					+ buildBody(api, curr, a, succ) + "\n"
					+ "}";
		}
		else
		{
			return super.build(api, curr, a);
		}
	}

	@Override
	public String getActionName(STStateChanApiBuilder scb, EAction a)
	{
		return ((RPCoreSTStateChanApiBuilder) scb).parent.namegen
					.getGeneratedIndexedRoleName(((RPCoreEAction) a).getPeer()) + "_"
				+ RPCoreSTApiGenConstants.API_RECEIVE_PREFIX + "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder api, EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.API_RECEIVE_ARG
							+ i + " *" + ((RPCoreSTStateChanApiBuilder) api).getExtName((DataType) a.payload.elems.get(i))
							//+ ", reduceFn" + i + " func(" + ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG + i + " []int) int"  // No: singleton choice subj (not multichoices)
							).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;

		boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParmaCoreDelegDecl, not GDelegationElem
				((RPCoreSTStateChanApiBuilder) api)//.isDelegType((DataType) pet));
							.isDelegType(pet));
		if (isDeleg)
		{
			throw new RuntimeException("[rp-core] TODO: " + a);
		}

		RPIndexedRole peer = (RPIndexedRole) curr.getActions().iterator().next().peer;
		RPInterval d = peer.intervals.iterator().next();
		
		String sEp = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
		String sEpRecv = sEp + "." + RPCoreSTApiGenConstants.ENDPOINT_MPCHAN_FIELD; /*+ "."
					+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + peer.getName() + "\"]";*/
		
		String res = "_, selected := <-s._" + a.mid + "_Chan\n"
				+ "if !selected {\n"
				+ "\treturn nil // select ignores nilchan\n"
				+ "}\n";

		if (a.mid.isOp())
		{
			if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[rp-core] TODO: payload size > 1: " + a);
				}
				String extName = ((RPCoreSTStateChanApiBuilder) api).getExtName((DataType) a.payload.elems.get(0));

				if (((GoJob) api.job).noCopy)
				{
					//res += "decoded = *bs[0].(*" + a.payload.elems.get(0) + ")\n";
					throw new RuntimeException("[rp-core] TODO: -nocopy: " + a);
				}

				res += "if err := " + sEpRecv // + (((GoJob) api.job).noCopy ? "Raw" : "");
								//+ "[" + RPCoreSTStateChanApiBuilder.generateIndexExpr(d.start) + "].Recv(&arg0)"
								+ "." + RPCoreSTApiGenConstants.MPCHAN_IRECV + "(\"" + peer.getName() + "\", "
								+ rpapi.generateIndexExpr(d.start, true) + ", &arg0)"
						+ "; err != nil {\n"
						+ "log.Fatal(err)\n"
						+ "}\n"
						//+ "*arg0 = tmp.(" + extName + ")\n"
						+ "ch := make(chan *" + rpapi.getStateChanName(curr.getSuccessor(a)) + ", 1)\n"
						//+ "ch <- " + rpapi.makeCreateSuccStateChan(this, curr, curr.getSuccessor(a), sEp) + "\n";
						+ "ch <- " + "FIXME" + /*FIXME: rpapi.makeCreateSuccStateChan(curr.getSuccessor(a)) +*/ "\n";
						// FIXME: arg0 // FIXME: args depends on label // FIXME: store args in s.args
			}
		}
		else //if (a.mid.isMessageSigName())
		{
			throw new RuntimeException("[rp-core] TODO: " + a.mid);
		}
				
		return res + "return ch";
	}
}

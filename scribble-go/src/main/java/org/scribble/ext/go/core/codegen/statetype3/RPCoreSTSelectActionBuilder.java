package org.scribble.ext.go.core.codegen.statetype3;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype3.RPCoreSTStateChanApiBuilder.RPCoreEStateKind;
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
				RPCoreSTStateChanApiBuilder.getStateKind(curr) == RPCoreEStateKind.CROSS_RECEIVE && curr.getActions().size() > 1)
		{
			// HACK FIXME: move to action builder
			return
					  "func (" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER
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
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return RPCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_PREFIX + "_"
				+ RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder api, EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.GO_CROSS_RECEIVE_METHOD_ARG
							+ i + " *" + a.payload.elems.get(i)
							//+ ", reduceFn" + i + " func(" + ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG + i + " []int) int"  // No: singleton choice subj (not multichoices)
							).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		RPCoreSTStateChanApiBuilder apigen = (RPCoreSTStateChanApiBuilder) api;

		if(a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + a);
		}

		boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParmaCoreDelegDecl, not GDelegationElem
				((RPCoreSTStateChanApiBuilder) api).isDelegType((DataType) pet));
		if (isDeleg)
		{
			throw new RuntimeException("[rp-core] TODO: " + a);
		}
		
		String res = "_, selected := <-s._" + a.mid + "_Chan\n"
				+ "if !selected {\n"
				+ "\treturn nil // select ignores nilchan\n"
				+ "}\n";

		RPIndexedRole peer = (RPIndexedRole) curr.getActions().iterator().next().peer;
		RPInterval d = peer.intervals.iterator().next();
		
		String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		String sEpRecv = sEp + "." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "."
					+ RPCoreSTApiGenConstants.GO_CONNECTION_MAP + "[\"" + peer.getName() + "\"]";

		if (!a.payload.elems.isEmpty())
		{
			if (a.payload.elems.size() > 1)
			{
				throw new RuntimeException("[rp-core] TODO: payload size > 1: " + a);
			}

			if (((GoJob) api.job).noCopy)
			{
				//res += "decoded = *bs[0].(*" + a.payload.elems.get(0) + ")\n";
				throw new RuntimeException("[rp-core] TODO: -nocopy: " + a);
			}

			res += "if err := " + sEpRecv; // + (((GoJob) api.job).noCopy ? "Raw" : "");
			res += "[" + RPCoreSTStateChanApiBuilder.generateIndexExpr(d.start) + "].Recv(&arg0); err != nil {\n"
					+ "log.Fatal(err)\n" + "}\n"
					+ "ch := make(chan *" + apigen.getStateChanName(curr.getSuccessor(a)) + ", 1)\n"
					+ "ch <- " + apigen.getSuccStateChan(this, curr, curr.getSuccessor(a), sEp) + "\n";
					// FIXME: arg0 // FIXME: args depends on label // FIXME: store args in s.args
		}
				
		return res + "return ch";
	}
}

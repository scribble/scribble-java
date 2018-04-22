package org.scribble.ext.go.core.codegen.statetype3;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;

// N.B. Select means Go select statement for input-choice; not output-choice
public class RPCoreSTSelectActionBuilder extends STBranchActionBuilder
{
	/*@Override
	public String build(STStateChanApiBuilder api, EState curr, EAction a)  // FIXME: "overriding" GSTStateChanAPIBuilder.buildAction to hack around *interface return  // FIXME: factor out
	{
		EState succ = curr.getSuccessor(a); 
		return
				  "func (s *" + getStateChanType(api, curr, a) + ") " + getActionName(api, a) + "(" 
				+ buildArgs(a)
				+ ") " + getReturnType(api, curr, succ) + " {\n"  // HACK: Return type is interface, so no need for *return (unlike other state chans)
				+ "s.state.Use()\n"
				+ buildBody(api, curr, a, succ) + "\n"
				+ "}";
	}*/

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return RPCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_PREFIX + "_"
				+ RPCoreSTStateChanApiBuilder.getGeneratedIndexedRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.GO_CROSS_RECEIVE_METHOD_ARG
							+ i + " *" + a.payload.elems.get(i)
							//+ ", reduceFn" + i + " func(" + ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG + i + " []int) int"  // No: singleton choice subj (not multichoices)
							).collect(Collectors.joining(", "));
	}

	/*@Override
	public String getReturnType(STStateChanApiBuilder api, EState curr, EState succ)
	{
		return api.cb.getCaseStateChanName(api, curr);
	}*/

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		if(a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[param-core] TODO: " + a);
		}

		boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParmaCoreDelegDecl, not GDelegationElem
				((RPCoreSTStateChanApiBuilder) api).isDelegType((DataType) pet));
		if (isDeleg)
		{
			throw new RuntimeException("[param-core] TODO: " + a);
		}
		
		String res =
				   "_, selected := <-s._" + a.mid + "_Chan\n"
				 + "if !selected {\n"
                 + "\treturn nil // select ignores nilchan\n"
                 + "}\n";

		RPIndexedRole peer = (RPIndexedRole) curr.getActions().iterator().next().peer;
		RPInterval g = peer.intervals.iterator().next();
		Function<RPIndexExpr, String> foo = e ->
		{
			if (e instanceof RPIndexInt)
			{
				return e.toString();
			}
			else if (e instanceof RPIndexVar)
			{
				return RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Params[\"" + e + "\"]";
			}
			else
			{
				throw new RuntimeException("[param-core] TODO: " + e);
			}
		};
		String sEpRecv = 
				 RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER
				+ "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				//+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT;
				+ ".Ept";
				//+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL;
		/*String sEpProto =
				//"s.ep.Proto"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO;*/
		
		RPCoreSTStateChanApiBuilder apigen = (RPCoreSTStateChanApiBuilder) api;
		String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;

			if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[param-core] [TODO] payload size > 1: " + a);
				}
				
				res +=
				//+ "data := make([]int, " + foo.apply(g.end) + ")\n"
				//+ "for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end) + "; i++ {\n"  // FIXME: num args
						"";
				
				res +=
				  ((((GoJob) api.job).noCopy)
					?
							"decoded = *bs[0].(*" + a.payload.elems.get(0) + ")\n"
					:
						""
						//+ "data[0] = decoded\n"
				//+ "}\n"
				);

				//+ "*arg0 = reduceFn0(data)\n"  // FIXME: arg0
				// + "*arg0 = data[0]\n"

				//+ "*arg0 = (<-s.data).(" + ((DataType) a.payload.elems.get(0)).getSimpleName() + ")\n";

				res +=
							  "if err := " + sEpRecv + (((GoJob) api.job).noCopy ? "Raw" : "")
							//+  ".Conn[" + sEpProto + "." + peer.getName() + ".Name()][" + foo.apply(g.start) + "].Recv(&arg0); err != nil {\n"
							+  ".Conn[\"" + peer.getName() + "\"][" + foo.apply(g.start) + "].Recv(&arg0); err != nil {\n"
							+ "log.Fatal(err)\n"
							+ "}\n"
							+ "\n"
							+ "\tch := make(chan *" + apigen.getStateChanName(curr.getSuccessor(a)) + ", 1)\n"
							+ "\tch <- " + apigen.getSuccStateChan(this, curr, curr.getSuccessor(a), sEp) + "\n";

								// FIXME: arg0  // FIXME: args depends on label  // FIXME: store args in s.args

		}
				
		return res
				+ "return ch";
	}
}

package org.scribble.ext.go.core.codegen.statetype2;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEAction;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexInt;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;

public class ParamCoreSTBranchActionBuilder extends STBranchActionBuilder
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
		return ParamCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_PREFIX + "_"
				+ ParamCoreSTStateChanApiBuilder.getGeneratedParamRoleName(((ParamCoreEAction) a).getPeer())
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> ParamCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_ARG
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
		/*String sEpRecv = 
				 ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL;
		String sEpProto =
				//"s.ep.Proto"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO;
		/*String sEpErr =
				//"s.ep.Err"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ERR;*/

		/*ParamRole r = (ParamRole) a.peer;
		ParamRange g = r.ranges.iterator().next();
		Function<ParamIndexExpr, String> foo = e ->
		{
			if (e instanceof ParamIndexInt)
			{
				return e.toString();
			}
			else if (e instanceof ParamIndexVar)
			{
				return ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Params[\"" + e + "\"]";
			}
			else
			{
				throw new RuntimeException("[param-core] TODO: " + e);
			}
		};*/

		if(a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[param-core] TODO: " + a);
		}

		boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParmaCoreDelegDecl, not GDelegationElem
				((ParamCoreSTStateChanApiBuilder) api).isDelegType((DataType) pet));
		if (isDeleg)
		{
			throw new RuntimeException("[param-core] TODO: " + a);
		}
		
		String res =
				  /*sEpRecv
				+ "(" + sEpProto
				+ ".(*" + api.gpn.getSimpleName() +")." + r.getName() + ", "
						+ foo.apply(g.start) + ", " + foo.apply(g.end) + ", "
						+ "\"" + a.mid + "\")\n"
				+ IntStream.range(0, a.payload.elems.size())
				      .mapToObj(i -> sEpRecv + "(" + sEpProto + ".(*" + api.gpn.getSimpleName() +")." + r.getName() + ", "
									+ foo.apply(g.start) + ", " + foo.apply(g.end) + ", "
				      		+ "arg" + i + ")")
				      .collect(Collectors.joining("\n")) + "\n"
				/*+ "if " + sEpErr + " != nil {\n"
				+ "return nil\n"
				+ "}\n"*/
				//buildReturn(api, curr, succ);
				   //"ch, selected := <-s._" + a.mid + "_Chan\n"
				   "_, selected := <-s._" + a.mid + "_Chan\n"
				 + "if !selected {\n"
                 + "\treturn nil // select ignores nilchan\n"
                 + "}\n";

		ParamRole peer = (ParamRole) curr.getActions().iterator().next().peer;
		ParamRange g = peer.ranges.iterator().next();
		Function<ParamIndexExpr, String> foo = e ->
		{
			if (e instanceof ParamIndexInt)
			{
				return e.toString();
			}
			else if (e instanceof ParamIndexVar)
			{
				return ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Params[\"" + e + "\"]";
			}
			else
			{
				throw new RuntimeException("[param-core] TODO: " + e);
			}
		};
		String sEpRecv = 
				 ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
				+ "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT;
				//+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL;
		String sEpProto =
				//"s.ep.Proto"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO;
		
		ParamCoreSTStateChanApiBuilder apigen = (ParamCoreSTStateChanApiBuilder) api;
		String sEp = ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;

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
							+  ".Conn[" + sEpProto + "." + peer.getName() + ".Name()][" + foo.apply(g.start) + "].Recv(&arg0); err != nil {\n"
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

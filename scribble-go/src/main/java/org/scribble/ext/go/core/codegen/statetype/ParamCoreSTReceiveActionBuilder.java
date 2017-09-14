package org.scribble.ext.go.core.codegen.statetype;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
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

public class ParamCoreSTReceiveActionBuilder extends STReceiveActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return ParamCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_PREFIX + "_"
				+ ParamCoreSTStateChanApiBuilder.getGeneratedParamRoleName(((ParamCoreEAction) a).getPeer())
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> ParamCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_ARG
							+ i + " *" + a.payload.elems.get(i)
							+ ", reduceFn" + i + " func(" + ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG + i + " []int) int"
							).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		String sEpRecv = 
				 ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
				+ "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
				+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL;
		String sEpProto =
				//"s.ep.Proto"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO;
		/*String sEpErr =
				//"s.ep.Err"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ERR;*/

		ParamRole r = (ParamRole) a.peer;
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
		};
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
                + "for i := " + foo.apply(g.start) + "; i <= "+foo.apply(g.end)+"; i++ {\n"
                + "\tvar decoded "+a.payload.elems.get(0)+"\n"
                + "\tif err := gob.NewDecoder(bytes.NewReader(b[i-"+foo.apply(g.start)+"])).Decode(&decoded); err != nil {\n\t\t"
                    + ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Errors <- session.DeserialiseFailed(err, \"" + getActionName(api, a) +"\", "
					+ ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Self.Name())\n"
                + "\t}\n"
                + "\tdata[i-"+foo.apply(g.start)+"] = decoded\n"
                + "}\n"
                + "*arg0 = reduceFn(data)"
				/*+ "if " + sEpErr + " != nil {\n"
				+ "return nil\n"
				+ "}\n"*/

			String res =
					sEpRecv + (((GoJob) api.job).noCopy ? "Raw" : "") 
							+ "(" + sEpProto +  "." + r.getName() + ", " + foo.apply(g.start) + ", " + foo.apply(g.end) + ")\n";	 // Discard op

			if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[param-core] [TODO] payload size > 1: " + a);
				}
				res +=
				  (((GoJob) api.job).noCopy 
				?
						"b := " + sEpRecv + "Raw(" + sEpProto + "." + r.getName() + ", " + foo.apply(g.start) + ", " + foo.apply(g.end) + ")\n"
					+ "data := make([]" + a.payload.elems.get(0) + ", len(b))\n"
					+ "for i := 0; i < len(b); i++ {\n"
					+ "data[i] = *b[i].(*" + a.payload.elems.get(0) + ")\n"
					+ "}\n"
					+ "*arg0 = reduceFn0(data)\n"
				:
						"b := " + sEpRecv + "(" + sEpProto + "." + r.getName() + ", " + foo.apply(g.start) + ", " + foo.apply(g.end) + ")\n"
					+ "data := make([]int, " + foo.apply(g.end) + ")\n"
					+ "for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end) + "; i++ {\n"  // FIXME: num args
							+ "var decoded int\n"
							+ "if err := gob.NewDecoder(bytes.NewReader(b[i-1])).Decode(&decoded); err != nil {\n"
							+	ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
									+ "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
									+ ".Errors <- session.DeserialiseFailed(err, \"" + getActionName(api, a) + "\","
									+ ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
										+ "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
									+ ".Self.Name())\n"
							+ "}\n"
							+ "data[i-1] = decoded\n"
					+ "}\n"
					+ "*arg0 = reduceFn0(data)\n");  // FIXME: arg0
			}
				
		return res
				+ buildReturn(api, curr, succ);
	}
}

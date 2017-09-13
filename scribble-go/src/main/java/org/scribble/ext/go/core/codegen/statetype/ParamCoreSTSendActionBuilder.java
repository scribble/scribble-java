package org.scribble.ext.go.core.codegen.statetype;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STSendActionBuilder;
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

public class ParamCoreSTSendActionBuilder extends STSendActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_PREFIX + "_"
				+ ParamCoreSTStateChanApiBuilder.getGeneratedParamRoleName(((ParamCoreEAction) a).getPeer())
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG
							+ i + " " + a.payload.elems.get(i)
							+ ", splitFn" + i + " func(" + ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG + i + " int, i" + i + " int) int"
							).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanApiBuilder api, EState curr, EAction a, EState succ)
	{
		String sEpWrite = 
				//s.ep.Write
				 ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				 		+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
						 + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL;
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
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".params[\"" + e + "\"]";
			}
			else
			{
				throw new RuntimeException("[param-core] TODO: " + e);
			}
		};
		return
				
					(((GoJob) api.job).noCopy
				?
				  "labels := make([]interface{}, " + foo.apply(g.end) + "-" + foo.apply(g.start) + "+1)\n"
				+ "for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end) + "; i++ {\n"
						+ "tmp := \"" + a.mid + "\"\n"
						+ "\tlabels[i-" + foo.apply(g.start) + "] = &tmp\n"
				+ "}\n"
				:
				  "labels := make([][]byte, " + foo.apply(g.end) + "-" + foo.apply(g.start) + "+1)\n"
				+ "for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end) + "; i++ {\n"
						+ "\tlabels[i-" + foo.apply(g.start) + "] = []byte(\"" + a.mid + "\")\n"
				+ "}\n")

				+ sEpWrite + (((GoJob) api.job).noCopy ? "Raw" : "")
						+ "(" + sEpProto + "." + r.getName() + ", "
						+ foo.apply(g.start) + ", " + foo.apply(g.end) + ", "
						+ "labels)\n"

				+ (((GoJob) api.job).noCopy
				?
				  "b := make([]interface{}, " + foo.apply(g.end) + "-" + foo.apply(g.start) + "+1)\n"
				+ "for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end)+"; i++ {\n"
						+ "tmp := splitFn0(arg0, i)\n"
						+ "\tb[i-"+foo.apply(g.start)+"] = &tmp\n"
				+ "}\n"
				:
				  "b := make([][]byte, " + foo.apply(g.end) + "-" + foo.apply(g.start) + "+1)\n"
				+ "for i := " + foo.apply(g.start) + "; i <= "+foo.apply(g.end)+"; i++ {\n"
						+ "\tvar buf bytes.Buffer\n"
						+ "\tif err := gob.NewEncoder(&buf).Encode(splitFn0(arg0, i)); err != nil {\n\t\t" // only arg0
						+ ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
								+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
								+ ".Errors <- session.SerialiseFailed(err, \"" + getActionName(api, a) +"\", "
								+ ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
								+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
								+ ".Self.Name())\n"
						+ "\t}\n"
						+ "\tb[i-"+foo.apply(g.start)+"] = buf.Bytes()\n"
				+ "}\n")
				
				+ sEpWrite + (((GoJob) api.job).noCopy ? "Raw" : "")
				+ "(" + sEpProto
				//+ ".(*" + api.gpn.getSimpleName() +")"
				+ "." + r.getName() + ", "
						+ foo.apply(g.start) + ", " + foo.apply(g.end) + ", "
								//+ "\"" + a.mid + "\""
								+ "b"
						+ ")\n"
				/*+ IntStream.range(0, a.payload.elems.size())
				      .mapToObj(i -> sEpWrite + "(" + sEpProto
				      				//+ ".(*" + api.gpn.getSimpleName() +")."
				      		+ r.getName() + ", "
									+ foo.apply(g.start) + ", " + foo.apply(g.end) + ", "
				      		+ "arg" + i + ")")
	 			      .collect(Collectors.joining("\n")) + "\n"*/
				/*+ "if " + sEpErr + " != nil {\n"
				+ "return nil\n"
				+ "}\n"*/
				+ buildReturn(api, curr, succ);
	}
}

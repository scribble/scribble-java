package org.scribble.ext.go.core.codegen.statetype2;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;

public class ParamCoreSTSendActionBuilder extends STSendActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		return ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_PREFIX + "_"
				+ ParamCoreSTStateChanApiBuilder.getGeneratedParamRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> ParamCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG
							+ i + " []" + ((ParamCoreSTStateChanApiBuilder) apigen).batesHack(a.payload.elems.get(i)) //a.payload.elems.get(i)
							).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanApiBuilder apigen, EState curr, EAction a, EState succ)
	{
		if(a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[param-core] TODO: " + a);
		}

		List<EAction> as = curr.getActions();
		if (as.size() > 1 && as.stream().anyMatch(b -> b.mid.toString().equals("")))  // HACK
		{
			throw new //ParamCoreException
					RuntimeException("[param-core] Empty labels not allowed in non-unary choices: " + curr.getActions());
		}
		
		String sEpWrite = 
				//s.ep.Write
				 ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				 		+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
						//+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL;
						+ ".Conn";
		String sEpProto =
				//"s.ep.Proto"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO;
		/*String sEpErr =
				//"s.ep.Err"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ERR;*/

		RPIndexedRole r = (RPIndexedRole) a.peer;
		RPInterval g = r.ranges.iterator().next();
		Function<RPIndexExpr, String> foo = e ->
		{
			if (e instanceof RPIndexInt)
			{
				return e.toString();
			}
			else if (e instanceof RPIndexVar)
			{
				return ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Params[\"" + e + "\"]";
			}
			else
			{
				throw new RuntimeException("[param-core] TODO: " + e);
			}
		};
				
		/*String res =
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
						+ "labels)\n";

			if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[param-core] [TODO] payload size > 1: " + a);
				}
				res +=
				  (((GoJob) api.job).noCopy
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
				+ "." + r.getName() + ", "
						+ foo.apply(g.start) + ", " + foo.apply(g.end) + ", "
								//+ "\"" + a.mid + "\""
								+ "b"
						+ ")\n";
			}*/

		// FIXME: single arg  // Currently never true because of ParamCoreSTOutputStateBuilder
		boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
				//pet.isGDelegationType()  // FIXME: currently deleg specified by ParmaCoreDelegDecl, not GDelegationElem
				((ParamCoreSTStateChanApiBuilder) apigen).isDelegType((DataType) pet));
		
		String res =
			//st1.Use()
				"j := 0\n"
				+ "for i := " + foo.apply(g.start) + "; i <= "+foo.apply(g.end)+"; i++ {\n"

				//+ ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ (a.mid.toString().equals("") ? "" :  // HACK
					"if err := " + sEpWrite
						+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
						+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL
						+ "(" //+ sEpProto + "." + r.getName() + ", "
						+ "\"" + a.mid + "\"" + "); err != nil {\n"
						+ "log.Fatal(err)\n"
						+ "}\n")

				//+ ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ ((isDeleg)  //... FIXME: delegation: take pointer?  send underlying ept? -- don't do (multi)"send"?
					?
						"log.Fatal(\"TODO\")\n"
					:
						"if err := " + sEpWrite
							+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
							+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_WRITEALL
							+ "(" //+ sEpProto + "." + r.getName() + ", "
							+ "arg0[j]" //+ "splitFn0(arg0, i)"  // FIXME: hardcoded arg0
									+ "); err != nil {\n"
							+ "log.Fatal(err)\n"
							+ "}\n"
							+ "j = j+1\n"
					+ "}\n");

			/*for i, v := range pl {
				st1.ept.Conn[Worker][i].Send(a.mid)
				st1.ept.Conn[Worker][i].Send(v)
			}*/
				
		return
					res
				+ buildReturn(apigen, curr, succ);
	}
}

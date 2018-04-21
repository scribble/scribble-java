package org.scribble.ext.go.core.codegen.statetype3;

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
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> ParamCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_ARG
							+ i + " *[]" + ((ParamCoreSTStateChanApiBuilder) apigen).batesHack(a.payload.elems.get(i)) //a.payload.elems.get(i)
							).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanApiBuilder apigen, EState curr, EAction a, EState succ)
	{
		if(a.payload.elems.size() > 1)
		{
			throw new RuntimeException("[param-core] TODO: " + a);
		}

		String sEpRecv = 
				 ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
				+ "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
				+ "." + "Conn";//ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL;
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

			/*String res =
					sEpRecv + (((GoJob) api.job).noCopy ? "Raw" : "") 
							+ "(" + sEpProto +  "." + r.getName() + ", " + foo.apply(g.start) + ", " + foo.apply(g.end) + ")\n";	 // Discard op*/
			String res = "";

			if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[param-core] [TODO] payload size > 1: " + a);
				}

				String extName = ((ParamCoreSTStateChanApiBuilder) apigen).batesHack(a.payload.elems.get(0));

				res +=
				  (((GoJob) apigen.job).noCopy 
				?
					  "data := make([]" + a.payload.elems.get(0) + ", len(b))\n"
					+ "for i := 0; i < len(b); i++ {\n"
					+ "data[i] = *b[i].(*" + a.payload.elems.get(0) + ")\n"
					+ "}\n"
					+ "*arg0 = reduceFn0(data)\n"
				:
					  //"data := make([]"
					  "data := make(map[int]"
								 + extName//a.payload.elems.get(0)
					  		+ ", " + foo.apply(g.end) + ")\n"
					+ "for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end) + "; i++ {\n"  // FIXME: num args

							+ (a.mid.toString().equals("") ? "" :  // HACK
								"var lab string\n"
							+ "if err := " + sEpRecv
									+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
									+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL
									+ "(" //+ sEpProto + "." + r.getName() + ", "
									+ "&lab" + "); err != nil {\n"
									+ "log.Fatal(err)\n"
									+ "}\n")

							+ "var tmp " + extName + "\n"
							+ (extName.startsWith("[]") ? "tmp = make(" + extName + ", len(*arg0))\n" : "")  // HACK? for passthru?

							+ "if err := " + sEpRecv
									+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
									+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL
									+ "(" //+ sEpProto + "." + r.getName() + ", "
									//+ "&data[i-1]" 
									+ "&tmp"
											+ "); err != nil {\n"
									+ "log.Fatal(err)\n"
									+ "}\n"
							+ "data[i-1] = tmp\n"
							+ "}\n"
					//+ "*arg0 = data\n");  // FIXME: arg0
					
					//+ "*arg0 = " + hackGetValues(extName) + "(data)\n";  // FIXME: arg0
							
					//+ ((!Stream.of("int", "string", "[]byte").collect(Collectors.toSet()).contains(extName)
					//		? 
						+ "f := func(m map[int] " + extName + ") []" + extName + " {\n"
						+ "xs := make([]" + extName + ", len(m))\n"
						+ "keys := make([]int, 0)\n"
						+ "for k, _ := range m {\n"
						+ "keys = append(keys, k)\n"
						+ "}\n"
						+ "sort.Ints(keys)\n"
						+ "for i, k := range keys {\n"
						+ "xs[i] = m[k]\n"
						+ "}\n"
						+ "return xs\n"
						+ "}\n"
						+ "*arg0 = f(data)\n");
					//	: "")
			}
				
		return res
				+ buildReturn(apigen, curr, succ);
	}
	
	protected static String hackGetValues(String t)
	{
		if (t.equals("int"))
		{
			return "util.GetValuesInt";
		}
		else if (t.equals("string"))
		{
			return "util.GetValuesString";
		}
		else if (t.equals("[]byte"))
		{
			return "util.GetValuesBates";
		}
		else
		{
			throw new RuntimeException("[TODO] " + t);
			//return t;
		}
	}
}

package org.scribble.ext.go.core.codegen.statetype3;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
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

public class RPCoreSTReduceActionBuilder extends STReceiveActionBuilder
{

	@Override
	public String getActionName(STStateChanApiBuilder api, EAction a)
	{
		//return ParamCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_PREFIX + "_"
		return RPCoreSTApiGenConstants.GO_CROSS_REDUCE_FUN_PREFIX + "_"
				+ RPCoreSTStateChanApiBuilder.getGeneratedParamRoleName(((RPCoreEAction) a).getPeer())
				+ "_" + a.mid;
	}

	@Override
	public String buildArgs(STStateChanApiBuilder apigen, EAction a)
	{
		DataType[] pet = new DataType[1];
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> RPCoreSTApiGenConstants.GO_CROSS_RECEIVE_FUN_ARG
							+ i + " *" + ((RPCoreSTStateChanApiBuilder) apigen).batesHack(pet[0] = (DataType) a.payload.elems.get(i)) //a.payload.elems.get(i)

							// HACK
							+ ((((RPCoreSTStateChanApiBuilder) apigen).isDelegType(pet[0])) ? "" :
								", reduceFn" + i + " func(" + RPCoreSTApiGenConstants.GO_CROSS_SEND_FUN_ARG + i
									//+ " []" + a.payload.elems.get(i) + ") " + a.payload.elems.get(i)
									+ " []" 
									
											+ ((RPCoreSTStateChanApiBuilder) apigen).batesHack(pet[0])

											+ ") "
											
											+ ((RPCoreSTStateChanApiBuilder) apigen).batesHack(pet[0])
									)

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
				 RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
				+ "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT
				+ "." + "Conn";//ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL;
		String sEpProto =
				//"s.ep.Proto"
				RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_ENDPOINT_PROTO;
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
				return RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Params[\"" + e + "\"]";
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

			// FIXME: single arg  // Currently never true because of ParamCoreSTOutputStateBuilder
			boolean isDeleg = a.payload.elems.stream().anyMatch(pet -> 
					//pet.isGDelegationType()  // FIXME: currently deleg specified by ParmaCoreDelegDecl, not GDelegationElem
					((RPCoreSTStateChanApiBuilder) apigen).isDelegType((DataType) pet));

			/*String res =
					sEpRecv + (((GoJob) api.job).noCopy ? "Raw" : "") 
							+ "(" + sEpProto +  "." + r.getName() + ", " + foo.apply(g.start) + ", " + foo.apply(g.end) + ")\n";	 // Discard op*/
			String res = "";

			if (isDeleg)
			{
				/*var ept *session.Endpoint
				err := conn.Recv(&ept)
				if err != nil {
					log.Fatalf("Wrong value received..")
				}
				sesss[i] = &Game_a_Init{ept: ept}  // Delegated session initialisation

				st.ept.ConnMu.RUnlock()
				return sesss, &ClientA_p_End{}*/

				String extName = ((RPCoreSTStateChanApiBuilder) apigen).batesHack(a.payload.elems.get(0));

				res = 
						"for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end) + "; i++ {\n"  // FIXME: num args

							+ (a.mid.toString().equals("") ? "" :  // HACK
								"var lab string\n"
							+ "if err := " + sEpRecv
									+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
									+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL
									+ "(" //+ sEpProto + "." + r.getName() + ", "
									+ "&lab" + "); err != nil {\n"
									+ "log.Fatal(err)\n"
									+ "}\n")

							+ "var tmp *" + 
									extName + "\n"
									//extName.substring(0, extName.indexOf('_', extName.indexOf('_', extName.indexOf('_')+1)+1)) + "\n"  // FIXME HACK
		
							+ "if err := " + sEpRecv
									+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
									+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL
									+ "(" //+ sEpProto + "." + r.getName() + ", "
									//+ "&data[i-1]"
									+ "&tmp"
											+ "); err != nil {\n"
									+ "log.Fatal(err)\n"
									+ "}\n"
									+ "arg0 = &" + extName + "{" + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": tmp." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " }\n"
							+ "}\n";

			}
			else if (!a.payload.elems.isEmpty())
			{
				if (a.payload.elems.size() > 1)
				{
					throw new RuntimeException("[param-core] [TODO] payload size > 1: " + a);
				}

				String extName = ((RPCoreSTStateChanApiBuilder) apigen).batesHack(a.payload.elems.get(0));

				res +=
				  (((GoJob) apigen.job).noCopy 
				?
					  "data := make([]" + a.payload.elems.get(0) + ", len(b))\n"
					+ "for i := 0; i < len(b); i++ {\n"
					+ "data[i] = *b[i].(*" + a.payload.elems.get(0) + ")\n"
					+ "}\n"
					+ "*arg0 = reduceFn0(data)\n"
				:
					  /*"data := make([]" + ParamCoreSTStateChanApiBuilder.batesHack(a.payload.elems.get(0)) //a.payload.elems.get(0)
								+ ", " + foo.apply(g.end) + ")\n"*/
							"data := make(map[int]" + extName + ")\n"
					+ "for i := " + foo.apply(g.start) + "; i <= " + foo.apply(g.end) + "; i++ {\n"  // FIXME: num args

							+ (a.mid.toString().equals("") ? "" :  // HACK
								"var lab string\n"
							+ "if err := " + sEpRecv
									+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
									+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL
									+ "(" //+ sEpProto + "." + r.getName() + ", "
									+ "&lab" + "); err != nil {\n"
									+ "log.Fatal(err)\n"
									+ "}\n")

							+ "var tmp " + extName + "\n"

							+ (extName.startsWith("[]") ? "tmp = make(" + extName + ", len(*arg0))\n" : "")  // HACK?  for passthru?

							+ "if err := " + sEpRecv
									+ "[" +  sEpProto + "." + r.getName() + ".Name()][i]"
									+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_READALL
									+ "(" //+ sEpProto + "." + r.getName() + ", "
									//+ "&data[i-1]"
									+ "&tmp"
											+ "); err != nil {\n"
									+ "log.Fatal(err)\n"
									+ "}\n"
									+ "data[i] = tmp\n"
							+ "}\n"
					//+ "*arg0 = reduceFn0("
					//		+ ParamCoreSTReceiveActionBuilder.hackGetValues(((ParamCoreSTStateChanApiBuilder) apigen).batesHack(a.payload.elems.get(0))) + "(data))\n");  // FIXME: arg0

						+ "f := func(m map[int] " + extName + ") []" + extName + " {\n"

						+ "xs := make([]" + extName + ", len(m))\n"
						+ "keys := make([]int, 0)\n"
						+ "for k, _ := range m {\n"
						+ "keys = append(keys, k)\n"
						+ "}\n"
						+ "sort.Ints(keys)\n"  // Needed?
						+ "for i, k := range keys {\n"
						+ "xs[i] = m[k]\n"
						+ "}\n"
						+ "return xs\n"
						+ "}\n"
						+ "*arg0 = reduceFn0(f(data))\n");
			}
				
		return res
				+ buildReturn(apigen, curr, succ);
	}
}

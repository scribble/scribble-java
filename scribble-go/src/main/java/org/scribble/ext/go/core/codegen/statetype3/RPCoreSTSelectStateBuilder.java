package org.scribble.ext.go.core.codegen.statetype3;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.type.name.GProtocolName;

// N.B. Select means Go select statement for input-choice; not output-choice
public class RPCoreSTSelectStateBuilder extends STBranchStateBuilder
{
	public RPCoreSTSelectStateBuilder(RPCoreSTSelectActionBuilder bb)
	{
		super(bb);
	}

	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		RPRoleVariant actual = ((RPCoreSTStateChanApiBuilder) api).actual;

		String sEpRecv = 
				 RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
				+ "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				//+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT;
				+ ".Ept";
				//+ "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_READALL;
		/*String sEpProto =
				//"s.ep.Proto"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "."
					+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_PROTO;*/

		//return ((ParamCoreSTStateChanApiBuilder) api).getStateChanPremable(s);
		RPCoreSTStateChanApiBuilder apigen = (RPCoreSTStateChanApiBuilder) api;
		//Role r = apigen.actual.getName();
		GProtocolName simpname = apigen.apigen.proto.getSimpleName();
		String tname = apigen.getStateChanName(s);
		//String epType = ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, r); 
		String epType = RPCoreSTApiGenerator.getGeneratedEndpointTypeName(simpname, apigen.actual); 
		String res =
				  //apigen.apigen.generateRootPackageDecl() + "\n"
				  "package " + RPCoreSTApiGenerator.getGeneratedActualRoleName(actual) + "\n"
				+ "\n"
				//+ apigen.apigen.generateScribbleRuntimeImports() + "\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "import \"log\"\n"
				
				//+ (((GoJob) api.job).noCopy ? "" : Stream.of(ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_BYTES_PACKAGE, ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_GOB_PACKAGE) .map(x -> "import \"" + x + "\"").collect(Collectors.joining("\n")))

				+ "\n"
				+ "type " + tname + " struct{\n"
				//+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n" 
				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epType + "\n" 
				+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ s.getActions().stream().map(a -> "_" + a.mid + "_Chan chan string\n") // chan *" + apigen.getStateChanName(s.getSuccessor(a)) + "\n")
						.collect(Collectors.joining(""))
				/*+ (((GoJob) apigen.job).noCopy ? "data chan []interface{}" : 
							//"data chan [][]byte\n" 
							"data chan interface{}\n" 
					)*/
				+ "}\n";

		res += "\n"
				+ "func (ep *" + epType + ") New" 
						+ ((s.id != api.graph.init.id) ? tname
								//: ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(actual) + "_1")  // cf. ParamCoreSTStateChanApiBuilder::getStateChanPremable init state case
								: "Init")
						+ "() *" + tname + " {\n"  // FIXME: factor out
				+ "s := &" + tname + " { " + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": ep"
						+ ", " + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + ": new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), "
						+ s.getActions().stream().map(a -> "_" + a.mid + "_Chan: make(chan "//chan *" + apigen.getStateChanName(s.getSuccessor(a))
								+ "string" +
								", 1)")
								.collect(Collectors.joining(", ")) + ", "

						/*+ "data: make(chan " + (((GoJob) api.job).noCopy ? "[]interface{}" : 
							//"[][]byte"
									" interface{}"
							) + ", 1)"*/

						+ "}\n"
				+ "go s.foo()\n"
				+ "return s\n"
				+ "}\n";

		res += "\n"
				+ "func (s *" + tname + ") foo() {\n"
				+ "s." + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + "." + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
			  + "var op string\n";

		RPIndexedRole peer = (RPIndexedRole) s.getActions().iterator().next().peer;
		RPInterval g = peer.ranges.iterator().next();
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
		
		if (((GoJob) apigen.job).noCopy)
		{
		res += 
				  "label := " + sEpRecv + "Raw(\"" + peer.getName() + "\", "
				  		+ foo.apply(g.start) + ", " + foo.apply(g.end) + ")\n"
				+ "op := *label[0].(*string)\n";  // FIXME: cast for safety?
		}
		else
		{
		res +=
				  "if err := " + sEpRecv + ".Conn[\"" + peer.getName() + "\"][" 
				  		+ foo.apply(g.start) + "].Recv(&op); err != nil {\n"  // g.end = g.start
				+ "log.Fatal(err)\n"
				+ "}\n";
		}

		/*List<EAction> as = s.getActions();
		boolean allEmpty = as.stream().allMatch(a -> a.payload.elems.isEmpty());
		if (!allEmpty)  // FIXME:
		{
			res +=
						  "var b string\n"  // HACK?
						  //"var b int\n"
					  + "if err := " + sEpRecv + (((GoJob) api.job).noCopy ? "Raw" : "")
						+  ".Conn[" + sEpProto + "." + peer.getName() + ".Name()][" + foo.apply(g.start) + "-1].Recv(&b); err != nil {\n"
						+ "log.Fatal(err)\n"
						+ "}\n"
					+ "s.data <- b\n";
							// FIXME: arg0  // FIXME: args depends on label  // FIXME: store args in s.args
		}*/
				
		res+= "if "
				+ s.getActions().stream().map(a ->
					{
						//String sEp = ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
						return
								"op == \"" + a.mid + "\" {\n"
								/*+ "\tch := make(chan *" + apigen.getStateChanName(s.getSuccessor(a)) + ", 1)\n"
								+ "\tch <- " + apigen.getSuccStateChan(this.bb, s, s.getSuccessor(a), sEp) + "\n"
								+ "\ts._" + a.mid + "_Chan <- ch\n"*/
								+ "\ts._" + a.mid + "_Chan <- \"" + a.mid +"\"\n"
								+ "\t" + s.getActions().stream()
										.filter(otheract -> otheract.mid != a.mid)
										.map(otheract -> { return "close(s._" + otheract.mid + "_Chan)"; })
										.collect(Collectors.joining("\n\t")) + "\n"
								+ "}";
					}).collect(Collectors.joining(" else if ")) + "\n"
				+ "}\n";

		return res;
	}
	
	@Override
	public String build(STStateChanApiBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof EReceive)  // FIXME: factor out action kind
			{
				out += this.bb.build(api, s, a);  // Getting 1 checks non-unary
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}

		return out;
	}
}

package org.scribble.ext.go.core.codegen.statetype3;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
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

	// Cf. RPCoreSTStateChanApiBuilder -- the hierarchy splits off branch state building separately
	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;

		GProtocolName simpname = rpapi.apigen.proto.getSimpleName();
		RPRoleVariant variant = ((RPCoreSTStateChanApiBuilder) api).variant;
		String scTypeName = rpapi.getStateChanName(s);
		String epTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, rpapi.variant); 

		String sEpRecv = 
				  RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER
				+ "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
				+ "." + RPCoreSTApiGenConstants.GO_MPCHAN_SESSCHAN;

		String res =
				  "package " + RPCoreSTApiGenerator.getGeneratedRoleVariantName(variant) + "\n"
				+ "\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				+ "import \"log\"\n"

				// State channel type
				+ "\n"
				+ "type " + scTypeName + " struct{\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epTypeName + "\n" 
				+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ s.getActions().stream().map(a -> "_" + a.mid + "_Chan chan string\n").collect(Collectors.joining(""))
				+ "}\n";

		// Explicit constructor -- for creating internal channels
		res += "\n"
				+ "func newBranch"   // FIXME: factor out, RPCoreSTStateChanApiBuilder#getSuccStateChan and RPCoreSTSelectActionBuilder#buildEndpointKindApi
						+ scTypeName + "(ep *" + epTypeName + ") *" + scTypeName + " {\n"
				+ "s := &" + scTypeName + " { " + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": ep" + ", "
						+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + ": new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), "
						+ s.getActions().stream().map(a -> "_" + a.mid + "_Chan: make(chan string, 1)").collect(Collectors.joining(", ")) + " "
						+ "}\n"
				+ "go s.branch()\n"
				+ "return s\n"
				+ "}\n";

		RPIndexedRole peer = (RPIndexedRole) s.getActions().iterator().next().peer;  // All branch actions have same subject
		RPInterval d = peer.intervals.iterator().next();
		if (peer.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + s);
		}

		// Branch background thread -- receive label, and signal corresponding channel
		res += "\n"
				+ "func (" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + " *" + scTypeName + ") branch() {\n"
				+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
						+ "." + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
			  + "var op string\n";
		if (((GoJob) rpapi.job).noCopy)
		{
			/*res += 
					  "label := " + sEpRecv + "Raw(\"" + peer.getName() + "\", "
							+ RPCoreSTStateChanApiBuilder.generateIndexExpr(g.start) + ", " + RPCoreSTStateChanApiBuilder.generateIndexExpr(g.end) + ")\n"
					+ "op := *label[0].(*string)\n";  // FIXME: cast for safety?*/
			throw new RuntimeException("[rp-core] TODO: -nocopy: " + s);
		}
		else
		{
			res +=
					 "if err := " + sEpRecv + "." + RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + peer.getName() + "\"][" 
				  		+ RPCoreSTStateChanApiBuilder.generateIndexExpr(d.start) + "].Recv(&op); err != nil {\n"  // g.end = g.start -- CFSM only has ? for input
					+ "log.Fatal(err)\n"
					+ "}\n";
		}
		res+= "if " + s.getActions().stream().map(a ->
					{
						return "op == \"" + a.mid + "\" {\n"
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
}

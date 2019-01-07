package org.scribble.ext.go.core.codegen.statetype.flat;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
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
	public String getPreamble(STStateChanApiBuilder apib, EState s)
	{
		RPCoreSTStateChanApiBuilder rpapib = (RPCoreSTStateChanApiBuilder) apib;

		GProtocolName simpname = rpapib.parent.proto.getSimpleName();
		RPRoleVariant variant = ((RPCoreSTStateChanApiBuilder) apib).variant;
		String scTypeName = rpapib.getStateChanName(s);
		String epTypeName = rpapib.parent.namegen.getEndpointKindTypeName(rpapib.variant); 

		String sEpRecv = 
				  RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER
				+ "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD
				+ "." + RPCoreSTApiGenConstants.ENDPOINT_MPCHAN_FIELD;

		String res =
				  "package " + rpapib.parent.namegen.getEndpointKindPackageName(variant) + "\n"
				+ "\n"
				+ "import \"" + RPCoreSTApiGenConstants.INT_RUNTIME_SESSION_PACKAGE + "\"\n"
				+ "import \"log\"\n"

				// State channel type
				+ "\n"
				+ "type " + scTypeName + " struct{\n"
				+ RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + epTypeName + "\n" 
				+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + " *" + RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE +"\n"
				+ s.getActions().stream().map(a -> "_" + a.mid + "_Chan chan string\n").collect(Collectors.joining(""))
				+ "}\n";

		// Explicit constructor -- for creating internal channels
		res += "\n"
				+ "func newBranch"   // FIXME: factor out, RPCoreSTStateChanApiBuilder#getSuccStateChan and RPCoreSTSelectActionBuilder#buildEndpointKindApi
						+ scTypeName + "(ep *" + epTypeName + ") *" + scTypeName + " {\n"
				+ "s := &" + scTypeName + " { " + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + ": ep" + ", "
						+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + ": new(" + RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "), "
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
		if (s.getAllActions().get(1).mid.isMessageSigName())
		{
			throw new RuntimeException("[rp-core] TODO: " + s.getAllActions());
		}
		
		res += "\n"
				+ "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + " *" + scTypeName + ") branch() {\n"
				+ RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_RES_FIELD
						+ "." + RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
		if (((GoJob) rpapib.job).noCopy)
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
					 "if err := " + sEpRecv + "." /*+ RPCoreSTApiGenConstants.GO_MPCHAN_CONN_MAP + "[\"" + peer.getName() + "\"][" 
				  		+ RPCoreSTStateChanApiBuilder.generateIndexExpr(d.start) + "].Recv(&op)*/
							+ RPCoreSTApiGenConstants.MPCHAN_IRECV + "(\"" + peer.getName() + "\", "
				  		+ rpapib.generateIndexExpr(d.start) + ", &op)"
					+ "; err != nil {\n"  // g.end = g.start -- CFSM only has ? for input
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

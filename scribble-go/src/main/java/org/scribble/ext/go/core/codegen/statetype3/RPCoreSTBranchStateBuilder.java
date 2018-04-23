package org.scribble.ext.go.core.codegen.statetype3;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;

// Type switch branch -- cf., RPCoreSTSelectStateBuilder
public class RPCoreSTBranchStateBuilder extends STBranchStateBuilder
{
	public RPCoreSTBranchStateBuilder(RPCoreSTBranchActionBuilder bb)
	{
		super(bb);
	}

	// Cf. RPCoreSTStateChanApiBuilder -- the hierarchy splits off branch state building separately
	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;

		/*GProtocolName simpname = rpapi.apigen.proto.getSimpleName();
		String scTypeName = rpapi.getStateChanName(s);
		String epTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, rpapi.variant); */

		String res = rpapi.getStateChanPremable(s);

		/*// Explicit constructor -- for compatibility with RPCoreSTSelectStateBuilder -- not currently needed: assuming select/typeswitch API gen mutually exclusive
		res += "\n"
				+ "func (ep *" + epTypeName + ") NewBranchInit"   // FIXME: factor out
						+ "() *" + scTypeName + " {\n"
				+ "s := &" + scTypeName + " { " + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": ep" + ", "
						+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + ": new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ") "
						+ "}\n"
				+ "return s\n"
				+ "}\n";*/

		return res;
	}
}

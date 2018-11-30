package org.scribble.ext.go.core.codegen.statetype;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator.Mode;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.type.name.GProtocolName;

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

		String res = //rpapi.getStateChanPremable(s);
				getStateChanPremable(rpapi, s);

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

	// FIXME refactor -- duplicated from RPCoreSTStateChanApiBuilder#getStateChanPremable, just to set false in apib.makeMessageImports(s, false);
	protected String getStateChanPremable(RPCoreSTStateChanApiBuilder apib, EState s)
	{
		GProtocolName simpname = apib.apigen.proto.getSimpleName();
		String scTypeName = apib.getStateChanName(s);
		String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, apib.variant); 
		
		String res =
				  "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(apib.variant) + "\n"
				+ "\n";
				
		// Not needed by select-branch or Case objects  // FIXME: refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.OUTPUT || s.getStateKind() == EStateKind.UNARY_INPUT
				|| (s.getStateKind() == EStateKind.POLY_INPUT && !apib.apigen.job.selectApi))
		{
			res += apib.makeMessageImports(s, false);
		}

		// FIXME: still needed? -- refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
		{
			switch (apib.apigen.mode)
			{
				case Int:  res += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n";  break;
				case IntPair:  res += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";  break;
				default:  throw new RuntimeException("Shouldn't get in here:" + apib.apigen.mode);
			}

			res += "import \"sync/atomic\"\n";
			res += "import \"reflect\"\n";
			res += "import \"sort\"\n";
			res += "\n";
			
			res += "var _ = session2.NewMPChan\n";

			res += "var _ = atomic.AddUint64\n";
			res += "var _ = reflect.TypeOf\n";
			res += "var _ = sort.Sort\n";
		}
		else if (s.getStateKind() == EStateKind.OUTPUT)
		{
			if (apib.apigen.mode == Mode.IntPair)
			{
				res += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";
				res += "\n";
				res += "var _ = session2.XY\n";  // For nested states that only use foreach vars (so no session2.XY)
			}
		}

				// State channel type
		res += "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " error\n";

		if (apib.apigen.job.parForeach)
		{
			res += RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "\n";
		}
		else
		{
			res += "id uint64\n";
		}

		res += RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epkindTypeName + "\n";

		if (apib.apigen.job.parForeach)
		{
			res += "Thread int\n";
		}

		res += "}\n";

		return res;
	}
}

package org.scribble.ext.go.core.codegen.statetype.nested;

import java.util.Map;
import java.util.Set;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator.Mode;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;

// Type switch branch -- cf., RPCoreSTSelectStateBuilder
public class RPCoreNBranchStateBuilder extends STBranchStateBuilder
{
	public RPCoreNBranchStateBuilder(RPCoreNBranchActionBuilder bb)
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
		String res =
				  "package " + apib.parent.namegen.getEndpointKindTypeName(apib.variant) + "\n"
				+ "\n";
				
		// Not needed by select-branch or Case objects  // FIXME: refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.OUTPUT || s.getStateKind() == EStateKind.UNARY_INPUT
				|| (s.getStateKind() == EStateKind.POLY_INPUT && !apib.parent.job.selectApi))
		{
			res += apib.makeMessageImports(s, false);
		}

		// FIXME: still needed? -- refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
		{
			// FIXME: factor out
			switch (apib.parent.mode)
			{
				case Int:  res += "import \"" + RPCoreSTApiGenConstants.INT_RUNTIME_SESSION_PACKAGE + "\"\n";  break;
				case IntPair:  res += "import \"" + RPCoreSTApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n";  break;
				default:  throw new RuntimeException("Shouldn't get in here:" + apib.parent.mode);
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
			if (apib.parent.mode == Mode.IntPair)
			{
				res += "import \"" + RPCoreSTApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n";
				res += "\n";
				res += "var _ = session2.XY\n";  // For nested states that only use foreach vars (so no session2.XY)
			}
		}

		// State channel type
		res += "\n"
				+ makeStateChannelType(apib, (RPCoreEState) s);

		return res;
	}

	private String makeStateChannelType(RPCoreSTStateChanApiBuilder apib,
			RPCoreEState s)
	{
		/*res += "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n";

		if (apib.parent.job.parForeach)
		{
			res += RPCoreSTApiGenConstants.SCHAN_RES_FIELD + " *" + RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "\n";
		}
		else
		{
			res += "id uint64\n";
		}

		res += RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + epkindTypeName + "\n";

		if (apib.parent.job.parForeach)
		{
			res += "Thread int\n";
		}

		res += "}\n";*/
		
		String schanTypeName = apib.getStateChanName(s);
		String epkindTypeName = apib.parent.namegen
				.getEndpointKindTypeName(apib.variant);

		String res = "type " + schanTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n";

		if (apib.parent.job.parForeach)
		{
			res += RPCoreSTApiGenConstants.SCHAN_RES_FIELD + " *"
					+ RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "\n";
		}
		else
		{
			res += "id uint64\n";
		}

		res += RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + epkindTypeName
				+ "\n";

		if (apib.parent.job.parForeach)  // TODO: make a default Thread int for non -parforeach
		{
			res += "Thread int\n";
		}
		
		Map<RPIndexedRole, Set<String>> menu = apib.parent.getStateChanMenu(s);

		for (RPIndexedRole peer : menu.keySet())
		{
			String peerFieldName = apib.parent.namegen.getGeneratedIndexedRoleName(peer);
			String peerTypeName = peerFieldName + "__" + s.id;  // CHECKME: alternative type name?
			res += peerFieldName + " *" + peerTypeName + "\n";
		}

		res += "}\n";
		
		// Peer types
		for (RPIndexedRole peer : menu.keySet())  // FIXME: sort
		{
			String peerTypeName = apib.parent.namegen
					.getGeneratedIndexedRoleName(peer) + "__" + s.id;  // Cf. above
			res += "\n"
					+ "type " + peerTypeName + " struct {\n";
			/*for (String action : menu.get(peer))
			{
				String actionTypeName = action + "_" + s.id;  // CHECKME: alternative type name?
				res += action + " *" + actionTypeName + "\n";
			}*/
			res += "schan *" + schanTypeName + "\n";
			res += "}\n";

			/*// Action types
			for (String action : menu.get(peer))  // FIXME: sort
			{
				String actionTypeName = action + "_" + s.id;  // Cf. above
				res += "\n"
						+ "type " + actionTypeName + " struct {\n"
						+ "schan *" + schanTypeName + "\n"
						+ "}\n";
			}*/
		}
//end := s.W_1toK.Scatter.W_1toK_Scatter_A(pay)

		return res;
	}
}

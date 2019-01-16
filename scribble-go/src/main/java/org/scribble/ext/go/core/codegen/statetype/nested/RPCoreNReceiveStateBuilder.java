package org.scribble.ext.go.core.codegen.statetype.nested;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.codegen.statetype.STReceiveActionBuilder;
import org.scribble.codegen.statetype.STReceiveStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class RPCoreNReceiveStateBuilder extends STReceiveStateBuilder
{
	public final RPCoreNReceiveActionBuilder vb;
	
	// rb is ParamCoreSTReduceActionBuilder
	public RPCoreNReceiveStateBuilder(STReceiveActionBuilder rb, RPCoreNReceiveActionBuilder vb)
	{
		super(rb);
		this.vb = vb;
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder apib, EState s)
	{
		return ((RPCoreSTStateChanApiBuilder) apib).getStateChanPremable(s);
	}

	// Duplicated from RPCoreNOutputStateBuilder
	private String makeStateChannelType(RPCoreSTStateChanApiBuilder apib,
			RPCoreEState s)
	{
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
			String typename = apib.parent.namegen.getGeneratedIndexedRoleName(peer)
					+ "__" + s.id;  // Cf. above
			res += "\n"
					+ "type " + typename + " struct {\n";
			for (String action : menu.get(peer))
			{
				String actionTypeName = action + "_" + s.id;  // CHECKME: alternative type name?
				res += action + " *" + actionTypeName + "\n";
			}
			res += "}\n";

			// Action types
			for (String action : menu.get(peer))  // FIXME: sort
			{
				String actionTypeName = action + "_" + s.id;  // Cf. above
				res += "\n"
						+ "type " + actionTypeName + " struct {\n"
						+ "schan *" + schanTypeName + "\n"
						+ "}\n";
			}
		}
//end := s.W_1toK.Scatter.W_1toK_Scatter_A(pay)

		return res;
	}
	
	@Override
	public String build(STStateChanApiBuilder apib, EState s)
	{
		String out = getPreamble(apib, s)
			//+ ((RPCoreSTStateChanApiBuilder) apib).makeStateChannelType((RPCoreEState) s); 
				+ makeStateChannelType((RPCoreSTStateChanApiBuilder) apib, (RPCoreEState) s);
		
		List<EAction> as = s.getActions();
		if (as.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + as);
		}
		EAction a = as.get(0);
		
		/*out += "\n\n";
		out += this.rb.build(api, s, a);*/

		/*// FIXME: delegation 
		if (!a.payload.elems.stream()
				.anyMatch(pet -> ((RPCoreSTStateChanApiBuilder) api)//.isDelegType((DataType) pet)))
							.isDelegType(pet)))*/
		{
			out += "\n\n";
			out += this.vb.build(apib, s, a);
		}

		return out;
	}
}


package org.scribble.ext.go.core.codegen.statetype.nested;

import java.util.Map;
import java.util.Set;

import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.ERequest;
import org.scribble.model.endpoint.actions.ESend;

public class RPCoreNOutputStateBuilder extends STOutputStateBuilder
{
	public final RPCoreNSendActionBuilder nb;

	// sb is ParamCoreSTSplitActionBuilder
	public RPCoreNOutputStateBuilder(STSendActionBuilder sb, RPCoreNSendActionBuilder nb)
	{
		super(sb);
		this.nb = nb;
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder apib, EState s)
	{
		return ((RPCoreSTStateChanApiBuilder) apib).getStateChanPremable(s);
	}

	// TODO: factor out with other state kinds
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
			String fieldname = apib.parent.namegen.getGeneratedIndexedRoleName(peer);
			String typename = fieldname;  // CHECKME: alternative type name?
			res += fieldname + " *" + typename + "\n";
		}

		res += "}\n";
		
		// Peer types
		for (RPIndexedRole peer : menu.keySet())  // FIXME: sort
		{
			String typename = apib.parent.namegen.getGeneratedIndexedRoleName(peer);  // Cf. above
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
				+ makeStateChannelType((RPCoreSTStateChanApiBuilder) apib, (RPCoreEState) s);
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof ESend)  // FIXME: factor out action kind
			{
				//out += this.sb.build(api, s, a);

				/*// FIXME: delegation 
				if (!a.payload.elems.stream()
						.anyMatch(pet -> ((RPCoreSTStateChanApiBuilder) api)//.isDelegType((DataType) pet)))
							.isDelegType(pet)))*/
				{
					out += "\n\n";
					out += this.nb.build(apib, s, a);
				}
			}
			else if (a instanceof ERequest)
			{
				throw new RuntimeException("TODO: " + a);
			}
			else if (a instanceof EDisconnect)
			{
				throw new RuntimeException("TODO: " + a);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}

		return out;
	}
}

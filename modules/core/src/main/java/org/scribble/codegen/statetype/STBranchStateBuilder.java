package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;

public abstract class STBranchStateBuilder extends STStateBuilder
{
	protected final STBranchActionBuilder bb;
	
	public STBranchStateBuilder(STBranchActionBuilder bb)
	{
		this.bb = bb;
	}
	
	@Override
	public String build(STAPIBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		out += "\n\n";
		out += this.bb.build(api, s, s.getActions().get(1));  // Getting 1 checks non-unary

		return out;
	}
}

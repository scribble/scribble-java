package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;

public abstract class STStateBuilder
{
	public String getPackage(STAPIBuilder api)
	{
		return api.gpn.toString();
	}

	public abstract String getPreamble(STAPIBuilder api, EState s);
	public abstract String build(STAPIBuilder api, EState s);
}

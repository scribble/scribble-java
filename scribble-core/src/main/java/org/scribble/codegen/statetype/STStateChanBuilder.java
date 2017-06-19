package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;

public abstract class STStateChanBuilder
{
	public abstract String getPreamble(STStateChanAPIBuilder api, EState s);
	public abstract String build(STStateChanAPIBuilder api, EState s);
}

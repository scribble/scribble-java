package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public abstract class STActionBuilder
{
	public abstract String getSTActionName(STAPIBuilder api, EAction a);
	public abstract String buildArgs(EAction a);
	public abstract String buildBody(STAPIBuilder api, EState curr, EAction a, EState succ);

	public String buildReturn(EState curr, STAPIBuilder api, EState succ)
	{
		return api.getSTStateName(succ);
	}
	
	public String build(STAPIBuilder api, EState curr, EAction a)
	{
		EState succ = curr.getSuccessor(a);
		return
				  "func (" + getType(api, curr, a) + ") " + getSTActionName(api, a) + "(" 
				+ buildArgs(a)
				+ ") " + buildReturn(curr, api, succ) + " {"
				+ "\n" + buildBody(api, curr, a, succ)
				+ "\n}";
	}
	
	protected String getType(STAPIBuilder api, EState curr, EAction a)
	{
		return api.getSTStateName(curr);
	}
}

package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public abstract class STActionBuilder
{
	public abstract String getSTActionName(STAPIBuilder api, EAction a);
	public abstract String buildArgs(EAction a);
	public abstract String buildBody(STAPIBuilder api, EAction a, EState succ);

	public String buildReturn(STAPIBuilder api, EState succ)
	{
		return api.getSTStateName(succ);
	}
	
	public String build(STAPIBuilder api, EState curr, EAction a)
	{
		EState succ = curr.getSuccessor(a);
		return
				  "func (" + api.getSTStateName(curr) + ") " + getSTActionName(api, a) + "(" 
				+ buildArgs(a)
				+ ") " + buildReturn(api, succ) + " {"
			  + "\n" + buildBody(api, a, succ)
			  + "\n}";
	}
}

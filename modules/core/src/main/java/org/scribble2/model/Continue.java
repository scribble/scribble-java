package org.scribble2.model;

import org.scribble2.model.name.simple.RecursionVarNode;

public abstract class Continue extends ModelNodeBase implements SimpleInteractionNode
{
	public final RecursionVarNode recvar;

	/*protected Continue(CommonTree ct, RecursionVarNode lab)
	{
		this(ct, lab, null, null);
	}

	protected Continue(CommonTree ct, RecursionVarNode lab, SimpleInteractionNodeContext sicontext)
	{
		this(ct, lab, sicontext, null);
	}*/

	protected Continue(RecursionVarNode recvar)//, SimpleInteractionNodeContext sicontext, Env env)
	{
		this.recvar = recvar;
	}

	/*protected abstract Continue reconstruct(CommonTree ct, RecursionVarNode recvar, SimpleInteractionNodeContext sicontext, Env env);

	@Override
	public Continue visitChildren(NodeVisitor nv) throws ScribbleException
	{
		RecursionVarNode recvar = (RecursionVarNode) visitChild(this.recvar, nv);
		//return new Continue(this.ct, recvar, getContext(), getEnv());
		return reconstruct(this.ct, recvar, getContext(), getEnv());
	}

	@Override
	public Continue leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}*/

	@Override
	public String toString()
	{
		return Constants.CONTINUE_KW + " " + recvar + ";";
	}
}

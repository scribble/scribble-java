package org.scribble.ast;

import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.visit.ModelVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;

public abstract class Continue<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	public final RecVarNode recvar;

	/*protected Continue(CommonTree ct, RecursionVarNode lab)
	{
		this(ct, lab, null, null);
	}

	protected Continue(CommonTree ct, RecursionVarNode lab, SimpleInteractionNodeContext sicontext)
	{
		this(ct, lab, sicontext, null);
	}*/

	protected Continue(RecVarNode recvar)//, SimpleInteractionNodeContext sicontext, Env env)
	{
		this.recvar = recvar;
	}

	protected abstract Continue<K> reconstruct(RecVarNode recvar);//, SimpleInteractionNodeContext sicontext, Env env);

	@Override
	public Continue<K> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		RecVarNode recvar = (RecVarNode) visitChild(this.recvar, nv);
		//return new Continue(this.ct, recvar, getContext(), getEnv());
		return reconstruct(recvar);//, getContext(), getEnv());
	}

	/*@Override
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

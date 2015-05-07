package org.scribble2.model.local;

import org.scribble2.model.Continue;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.sesstype.kind.LocalKind;

public class LocalContinue extends Continue<LocalKind> implements SimpleLocalInteractionNode
{
	public LocalContinue(RecursionVarNode recvar)
	{
		super(recvar);
	}

	@Override
	protected LocalContinue reconstruct(RecursionVarNode recvar)
	{
		ModelDelegate del = del();
		LocalContinue lc = new LocalContinue(recvar);//, sicontext, env);
		lc = (LocalContinue) lc.del(del);
		return lc;
	}

	@Override
	protected LocalContinue copy()
	{
		return new LocalContinue(this.recvar);
	}

	/*@Override
	protected LocalContinue reconstruct(RecursionVarNode recvar)
	{
		return new LocalContinue(recvar);
	}*/
	
	/*@Override
	public LocalContinue leaveGraphBuilding(GraphBuilder builder)
	{
		builder.setEntry(builder.getRecursionEntry(this.recvar.toName()));
		return this;
	}*/
	
	/*@Override
	public LocalContinue visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Continue cont = super.visitChildren(nv);
		return new LocalContinue(cont.ct, cont.recvar);
	}*/
}

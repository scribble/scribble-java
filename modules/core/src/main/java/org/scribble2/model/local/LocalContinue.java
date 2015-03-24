package org.scribble2.model.local;

import org.scribble2.model.Continue;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.name.simple.RecursionVarNode;

public class LocalContinue extends Continue implements LocalInteraction
{
	public LocalContinue(RecursionVarNode recvar)
	{
		super(recvar);
	}

	@Override
	protected ModelNodeBase copy()
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

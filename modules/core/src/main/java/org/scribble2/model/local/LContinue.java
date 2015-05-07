package org.scribble2.model.local;

import org.scribble2.model.Continue;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.simple.RecVarNode;
import org.scribble2.sesstype.kind.Local;

public class LContinue extends Continue<Local> implements LSimpleInteractionNode
{
	public LContinue(RecVarNode recvar)
	{
		super(recvar);
	}

	@Override
	protected LContinue reconstruct(RecVarNode recvar)
	{
		ModelDel del = del();
		LContinue lc = new LContinue(recvar);//, sicontext, env);
		lc = (LContinue) lc.del(del);
		return lc;
	}

	@Override
	protected LContinue copy()
	{
		return new LContinue(this.recvar);
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

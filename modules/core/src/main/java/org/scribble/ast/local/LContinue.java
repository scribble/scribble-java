package org.scribble.ast.local;

import org.scribble.ast.Continue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LContinue extends Continue<Local> implements LSimpleInteractionNode
{
	public LContinue(RecVarNode recvar)
	{
		super(recvar);
	}

	@Override
	protected LContinue copy()
	{
		return new LContinue(this.recvar);
	}

	@Override
	protected LContinue reconstruct(RecVarNode recvar)
	{
		ScribDel del = del();
		LContinue lc = new LContinue(recvar);
		lc = (LContinue) lc.del(del);
		return lc;
	}
}

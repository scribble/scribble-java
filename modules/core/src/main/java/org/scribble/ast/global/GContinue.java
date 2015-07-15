package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Continue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

public class GContinue extends Continue<Global> implements GSimpleInteractionNode
{
	public GContinue(RecVarNode recvar)
	{
		super(recvar);
	}

	@Override
	protected GContinue copy()
	{
		return new GContinue(this.recvar);
	}
	
	@Override
	public GContinue clone()
	{
		RecVarNode rv = this.recvar.clone();
		return AstFactoryImpl.FACTORY.GContinue(rv);
	}

	@Override
	public GContinue reconstruct(RecVarNode recvar)
	{
		ScribDel del = del();
		GContinue gc = new GContinue(recvar);
		gc = (GContinue) gc.del(del);
		return gc;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}
}

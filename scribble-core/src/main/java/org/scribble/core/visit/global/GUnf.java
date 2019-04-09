package org.scribble.core.visit.global;

import org.scribble.core.type.kind.Global;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.global.GRecursion;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.global.GType;
import org.scribble.core.visit.Unf;

public class GUnf extends Unf<Global, GSeq>
{

	@Override
	public GType visitContinue(Continue<Global, GSeq> n)
	{
		return new GRecursion(n.getSource(), n.recvar, (GSeq) getRec(n.recvar));
	}
}

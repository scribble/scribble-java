package org.scribble.core.visit.local;

import org.scribble.core.type.kind.Local;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeUnfolder;

public class LTypeUnfolder extends STypeUnfolder<Local, LSeq>
{
	@Override
	public LType visitContinue(Continue<Local, LSeq> n)
	{
		return new LRecursion(n.getSource(), n.recvar, (LSeq) getRec(n.recvar));
	}
}

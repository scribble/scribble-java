package org.scribble.del.global;

import org.scribble.ast.global.GNode;
import org.scribble.ast.local.LNode;
import org.scribble.del.InterruptibleDel;
import org.scribble.sesstype.name.Role;

public class GInterruptibleDel extends InterruptibleDel implements GCompoundInteractionNodeDel
{
	@Override
	public LNode project(GNode n, Role self)
	{
		throw new RuntimeException("TODO: " + n);
	}
}

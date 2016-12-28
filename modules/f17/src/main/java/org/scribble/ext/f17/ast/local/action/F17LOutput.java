package org.scribble.ext.f17.ast.local.action;

import org.scribble.sesstype.name.Role;


public abstract class F17LOutput extends F17LAction
{
	public F17LOutput(Role self, Role peer)
	{
		super(self, peer);
	}
	
	@Override
	public boolean isOutput()
	{
		return true;
	}
}

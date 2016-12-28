package org.scribble.ext.f17.ast.local.action;

import org.scribble.sesstype.name.Role;

public abstract class F17LInput extends F17LAction
{
	public F17LInput(Role self, Role peer)
	{
		super(self, peer);
	}
	
	@Override
	public boolean isInput()
	{
		return true;
	}
}

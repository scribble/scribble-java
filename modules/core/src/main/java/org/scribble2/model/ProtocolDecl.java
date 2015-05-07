package org.scribble2.model;

import org.scribble2.sesstype.name.ProtocolName;

public abstract class ProtocolDecl extends ModelNodeBase
{
	public abstract ProtocolName getFullProtocolName(Module mod);
	
	public boolean isGlobal()
	{
		return false;
	}

	public boolean isLocal()
	{
		return false;
	}
}

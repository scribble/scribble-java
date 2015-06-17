package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Local;


public class LProtocolName extends ProtocolName<Local>
{
	private static final long serialVersionUID = 1L;

	public LProtocolName(ModuleName modname, ProtocolName<Local> membname)
	{
		super(Local.KIND, modname, membname);
	}
	
	public LProtocolName(String membname)
	{
		super(Local.KIND, membname);
	}

	@Override
	public LProtocolName getSimpleName()
	{
		return new LProtocolName(getLastElement());
	}
}

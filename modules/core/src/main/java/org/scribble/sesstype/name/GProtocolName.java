package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Global;


public class GProtocolName extends ProtocolName<Global>
{
	private static final long serialVersionUID = 1L;

	public GProtocolName(ModuleName modname, ProtocolName<Global> membname)
	{
		super(Global.KIND, modname, membname);
	}
	
	public GProtocolName(String membname)
	{
		super(Global.KIND, membname);
	}

	@Override
	public GProtocolName getSimpleName()
	{
		return new GProtocolName(getLastElement());
	}
}

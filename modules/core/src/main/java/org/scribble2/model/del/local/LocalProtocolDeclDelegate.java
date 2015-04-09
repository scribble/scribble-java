package org.scribble2.model.del.local;

import org.scribble2.model.del.ProtocolDeclDelegate;

public class LocalProtocolDeclDelegate extends ProtocolDeclDelegate
{
	public LocalProtocolDeclDelegate()
	{

	}

	/*protected LocalProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		super(dependencies);
	}

	@Override
	protected LocalProtocolDeclDelegate reconstruct(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return new LocalProtocolDeclDelegate(dependencies);
	}*/

	@Override
	protected LocalProtocolDeclDelegate copy()
	{
		return new LocalProtocolDeclDelegate();
	}

	/*@Override
	public LocalProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return (LocalProtocolDeclDelegate) super.setDependencies(dependencies);
	}*/
}

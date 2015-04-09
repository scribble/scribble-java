package org.scribble2.model.del.local;

import java.util.Map;
import java.util.Set;

import org.scribble2.model.del.ProtocolDeclDelegate;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;

public class LocalProtocolDeclDelegate extends ProtocolDeclDelegate
{
	public LocalProtocolDeclDelegate()
	{

	}

	protected LocalProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		super(dependencies);
	}

	@Override
	protected LocalProtocolDeclDelegate reconstruct(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return new LocalProtocolDeclDelegate(dependencies);
	}

	/*@Override
	public LocalProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return (LocalProtocolDeclDelegate) super.setDependencies(dependencies);
	}*/
}

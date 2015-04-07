package org.scribble2.model.del.global;

import java.util.Map;
import java.util.Set;

import org.scribble2.model.del.ProtocolDeclDelegate;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;

public class GlobalProtocolDeclDelegate extends ProtocolDeclDelegate
{
	public GlobalProtocolDeclDelegate()
	{

	}
	
	protected GlobalProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		super(dependencies);
	}

	@Override
	protected GlobalProtocolDeclDelegate reconstruct(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return new GlobalProtocolDeclDelegate(dependencies);
	}

	@Override
	public GlobalProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return (GlobalProtocolDeclDelegate) super.setDependencies(dependencies);
	}
}

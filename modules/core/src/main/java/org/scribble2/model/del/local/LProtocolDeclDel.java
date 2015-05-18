package org.scribble2.model.del.local;

import org.scribble2.model.del.ProtocolDeclDel;

//public class LProtocolDeclDel extends ProtocolDeclDel<Local>
public class LProtocolDeclDel extends ProtocolDeclDel
{
	public LProtocolDeclDel()
	{

	}

	/*@Override
	//protected DependencyMap<? extends ProtocolName<? extends ProtocolKind>> newDependencyMap()
	protected DependencyMap<LProtocolName> newDependencyMap()
	{
		return new DependencyMap<LProtocolName>();
	}*/

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
	protected LProtocolDeclDel copy()
	{
		return new LProtocolDeclDel();
	}

	/*@Override
	public LocalProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return (LocalProtocolDeclDelegate) super.setDependencies(dependencies);
	}*/
}

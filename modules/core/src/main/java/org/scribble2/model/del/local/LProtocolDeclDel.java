package org.scribble2.model.del.local;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble2.model.context.LProtocolDeclContext;
import org.scribble2.model.del.ProtocolDeclDel;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.DependencyMap;

public class LProtocolDeclDel extends ProtocolDeclDel<Local>
//public class LProtocolDeclDel extends ProtocolDeclDel
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


	@Override
	protected LProtocolDeclContext newProtocolDeclContext(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> deps)
	{
		return new LProtocolDeclContext(new DependencyMap<>(cast(deps)));
	}

	private static Map<Role, Map<LProtocolName, Set<Role>>> cast(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static Map<LProtocolName, Set<Role>> castAux(Map<ProtocolName<? extends ProtocolKind>, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> (LProtocolName) k, (k) -> map.get(k)));
	}
}

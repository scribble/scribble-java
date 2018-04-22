package org.scribble.ext.go.core.ast;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.type.kind.ProtocolKind;

public abstract class RPCoreChoice<C extends RPCoreType<K>, K extends ProtocolKind> implements RPCoreType<K>
{
	public final RPIndexedRole role;
	public final RPCoreActionKind<K> kind;
	public final LinkedHashMap<RPCoreMessage, C> cases;
	
	// Pre: cases.size() > 1
	public RPCoreChoice(RPIndexedRole role, RPCoreActionKind<K> kind, LinkedHashMap<RPCoreMessage, C> cases)
	{
		this.role = role;
		this.kind = kind;
		this.cases = new LinkedHashMap<>(cases);
	}
	
	public abstract RPCoreActionKind<K> getKind();
	
	@Override
	public int hashCode()
	{
		int hash = 29;
		hash = 31 * hash + this.role.hashCode();
		hash = 31 * hash + this.kind.hashCode();
		hash = 31 * hash + this.cases.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreChoice))
		{
			return false;
		}
		RPCoreChoice<?, ?> them = (RPCoreChoice<?, ?>) obj; 
		return them.canEquals(this)
				&& this.role.equals(them.role) && this.kind.equals(kind) && this.cases.equals(them.cases);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreChoice;
	}
	
	protected String casesToString()
	{
		String s = this.cases.entrySet().stream()
				.map(e -> e.getKey() + "." + e.getValue()).collect(Collectors.joining(", "));
		s = (this.cases.size() > 1)
				? "{ " + s + " }"
				: ":" + s;
		return s;
	}
}

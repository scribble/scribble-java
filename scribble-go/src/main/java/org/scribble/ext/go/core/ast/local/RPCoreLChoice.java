package org.scribble.ext.go.core.ast.local;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.RPCoreChoice;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.type.Message;
import org.scribble.type.kind.Local;

public abstract class RPCoreLChoice extends RPCoreChoice<RPCoreLType, Local> implements RPCoreLType
{
	//protected RPCoreLChoice(RPIndexedRole role, RPCoreLActionKind kind, LinkedHashMap<RPCoreMessage, RPCoreLType> cases)
	protected RPCoreLChoice(RPIndexedRole role, RPCoreLActionKind kind, LinkedHashMap<Message, RPCoreLType> cases)
	{
		super(role, kind, cases);
	}

	@Override
	public RPCoreLActionKind getKind()
	{
		return (RPCoreLActionKind) this.kind;
	}

	@Override
	public String toString()
	{
		return this.role.toString() + this.kind + casesToString();
	}
	
	@Override
	protected String casesToString()
	{
		String s = this.cases.entrySet().stream()
				.map(e -> e.getKey() + "." + e.getValue()).collect(Collectors.joining(", "));
		s = (this.cases.size() > 1)
				? "{ " + s + " }"
				: s;  // No ":", cf. global
		return s;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2399;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreLChoice))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreLChoice;
	}
}

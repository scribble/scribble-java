package org.scribble.ext.go.core.ast.local;

import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.ParamCoreChoice;
import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.kind.Local;

public abstract class ParamCoreLChoice extends ParamCoreChoice<ParamCoreLType, Local> implements ParamCoreLType
{
	protected ParamCoreLChoice(ParamRole role, ParamCoreLActionKind kind, Map<ParamCoreMessage, ParamCoreLType> cases)
	{
		super(role, kind, cases);
	}

	@Override
	public ParamCoreLActionKind getKind()
	{
		return (ParamCoreLActionKind) this.kind;
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
		if (!(obj instanceof ParamCoreLChoice))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLChoice;
	}
}

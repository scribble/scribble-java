package org.scribble.ext.go.core.ast;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.kind.ProtocolKind;

public abstract class ParamCoreChoice<C extends ParamCoreType, K extends ProtocolKind> implements ParamCoreType
{
	public final ParamRole role;
	public final Map<ParamCoreMessage, C> cases;
	
	// Pre: cases.size() > 1
	public ParamCoreChoice(ParamRole role, Map<ParamCoreMessage, C> cases)
	{
		this.role = role;
		this.cases = Collections.unmodifiableMap(cases);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 29;
		hash = 31 * hash + this.role.hashCode();
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
		if (!(obj instanceof ParamCoreChoice))
		{
			return false;
		}
		ParamCoreChoice<?, ?> them = (ParamCoreChoice<?, ?>) obj; 
		return them.canEquals(this)
				&& this.role.equals(them.role) && this.cases.equals(them.cases);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreChoice;
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

package org.scribble.ext.go.core.ast.local;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.type.index.ParamIndexVar;

// FIXME: factor out better with ParamCore(L)Choice -- or deprecate and just use kind?
public class ParamCoreLMultiChoices extends ParamCoreLChoice
{
	public final ParamIndexVar var;  // Redundant?
	
	// Pre: cases.size() > 1
	public ParamCoreLMultiChoices(ParamRole role, ParamIndexVar var, List<ParamCoreMessage> cases, ParamCoreLType cont)
	{
		super(role, ParamCoreLActionKind.MULTICHOICES_RECEIVE, foo(cases, cont));
		this.var = var;
	}
	
	private static LinkedHashMap<ParamCoreMessage, ParamCoreLType> foo(List<ParamCoreMessage> cases, ParamCoreLType cont)
	{
		LinkedHashMap<ParamCoreMessage, ParamCoreLType> tmp = new LinkedHashMap<>();
		cases.stream().forEach(c -> tmp.put(c, cont));
		return tmp;
	}
	
	public ParamCoreLType getContinuation()
	{
		if (new HashSet<>(this.cases.values()).size() > 1)
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + this.cases);
		}
		return this.cases.values().iterator().next();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7207;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.var.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreLMultiChoices))
		{
			return false;
		}
		ParamCoreLMultiChoices them = (ParamCoreLMultiChoices) obj; 
		return super.equals(this)
				&& this.var.equals(them.var);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLMultiChoices;
	}
}

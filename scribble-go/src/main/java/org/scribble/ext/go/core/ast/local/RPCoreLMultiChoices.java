package org.scribble.ext.go.core.ast.local;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.scribble.ext.go.core.ast.RPCoreMessage;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.type.index.RPIndexVar;

// FIXME: factor out better with ParamCore(L)Choice -- or deprecate and just use kind?
public class RPCoreLMultiChoices extends RPCoreLChoice
{
	public final RPIndexVar var;  // Redundant?
	
	// Pre: cases.size() > 1
	public RPCoreLMultiChoices(RPIndexedRole role, RPIndexVar var, List<RPCoreMessage> cases, RPCoreLType cont)
	{
		super(role, RPCoreLActionKind.MULTICHOICES_RECEIVE, foo(cases, cont));
		this.var = var;
	}
	
	private static LinkedHashMap<RPCoreMessage, RPCoreLType> foo(List<RPCoreMessage> cases, RPCoreLType cont)
	{
		LinkedHashMap<RPCoreMessage, RPCoreLType> tmp = new LinkedHashMap<>();
		cases.stream().forEach(c -> tmp.put(c, cont));
		return tmp;
	}
	
	public RPCoreLType getContinuation()
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
		if (!(obj instanceof RPCoreLMultiChoices))
		{
			return false;
		}
		RPCoreLMultiChoices them = (RPCoreLMultiChoices) obj; 
		return super.equals(this)
				&& this.var.equals(them.var);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreLMultiChoices;
	}
}

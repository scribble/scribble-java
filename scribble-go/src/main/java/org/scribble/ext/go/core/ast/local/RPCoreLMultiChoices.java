package org.scribble.ext.go.core.ast.local;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.Message;
import org.scribble.type.kind.Local;

// FIXME: factor out better with ParamCore(L)Choice -- or deprecate and just use kind?
@Deprecated
public class RPCoreLMultiChoices extends RPCoreLChoice
{
	public final RPIndexVar var;  // Redundant?
	
	// Pre: cases.size() > 1
	public RPCoreLMultiChoices(RPIndexedRole role, RPIndexVar var, //List<RPCoreMessage> cases, 
			List<Message> cases,
			RPCoreLType cont)
	{
		super(role, RPCoreLActionKind.MULTICHOICES_RECEIVE, foo(cases, cont));
		this.var = var;
	}

	@Override
	public RPCoreLType compactSingletonIvals(RPCoreAstFactory af, RPRoleVariant subj)
	{
		throw new RuntimeException("[rp-core] Shouldn't get in here: " + this);
	}

	@Override
	public RPCoreLType subs(RPCoreAstFactory af, RPCoreType<Local> old, RPCoreType<Local> neu)
	{
		throw new RuntimeException("[rp-core] Shouldn't get in here: " + this);
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}
	
	private static //LinkedHashMap<RPCoreMessage, RPCoreLType> foo(List<RPCoreMessage> cases, RPCoreLType cont)
			LinkedHashMap<Message, RPCoreLType> foo(List<Message> cases, RPCoreLType cont)
	{
		//LinkedHashMap<RPCoreMessage, RPCoreLType> 
		LinkedHashMap<Message, RPCoreLType> 
				tmp = new LinkedHashMap<>();
		cases.forEach(c -> tmp.put(c, cont));
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

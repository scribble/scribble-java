package org.scribble.ext.go.core.ast.local;

import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.type.index.ParamIndexVar;

// FIXME: factor out better with ParamCore(L)Choice -- or deprecate and just use kind?
public class ParamCoreLMultiChoices extends ParamCoreLChoice
{
	public final ParamIndexVar var;  // Redundant?
	
	// Pre: cases.size() > 1
	public ParamCoreLMultiChoices(ParamRole role, ParamIndexVar var, Set<ParamCoreMessage> cases, ParamCoreLType cont)
	{
		super(role, ParamCoreLActionKind.MULTICHOICES_RECEIVE, cases.stream().collect(Collectors.toMap(c -> c, c -> cont)));
		this.var = var;
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

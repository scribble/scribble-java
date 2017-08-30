package org.scribble.ext.go.core.ast.local;

import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.ParamCoreChoice;
import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.type.kind.Local;

// FIXME: factor out better with ParamCoreChoice
public class ParamCoreLMultiChoices extends ParamCoreChoice<ParamCoreLType, Local> implements ParamCoreLType
{
	public final ParamIndexVar var;  // Redundant?
	
	// Pre: cases.size() > 1
	public ParamCoreLMultiChoices(ParamRole role, ParamCoreLActionKind kind, ParamIndexVar var, Set<ParamCoreMessage> cases, ParamCoreLType cont)
	{
		super(role, kind, cases.stream().collect(Collectors.toMap(c -> c, c -> cont)));
		this.var = var;
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
		if (!(obj instanceof ParamCoreChoice))
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
		return o instanceof ParamCoreChoice;
	}
}

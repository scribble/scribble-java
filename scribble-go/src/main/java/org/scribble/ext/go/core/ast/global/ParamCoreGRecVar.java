package org.scribble.ext.go.core.ast.global;

import java.util.Collections;
import java.util.Set;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreRecVar;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;

	
public class ParamCoreGRecVar extends ParamCoreRecVar<Global> implements ParamCoreGType
{
	public ParamCoreGRecVar(RecVar var)
	{
		super(var);
	}
	
	@Override
	public Set<ParamRole> getParamRoles()
	{
		return Collections.emptySet();
	}

	@Override
	//public ParamCoreLRecVar project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges)
	public ParamCoreLType project(ParamCoreAstFactory af, ParamActualRole subj) throws ParamCoreSyntaxException
	{
		return af.ParamCoreLRecVar(this.recvar);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ParamCoreGRecVar))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}

	@Override
	public int hashCode()
	{
		int hash = 2411;
		hash = 31*hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreGRecVar;
	}
}

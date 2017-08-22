package org.scribble.ext.go.core.ast.global;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreRec;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.ParamCoreLEnd;
import org.scribble.ext.go.core.ast.local.ParamCoreLRecVar;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class ParamCoreGRec extends ParamCoreRec<ParamCoreGType> implements ParamCoreGType
{
	public ParamCoreGRec(RecVar recvar, ParamCoreGType body)
	{
		super(recvar, body);
	}

	@Override
	public ParamCoreLType project(ParamCoreAstFactory af, Role r) throws ParamCoreSyntaxException
	{
		ParamCoreLType proj = this.body.project(af, r);
		return (proj instanceof ParamCoreLRecVar) ? ParamCoreLEnd.END : af.ParamCoreLRec(this.recvar, proj);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreGRec))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreGRec;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2333;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}

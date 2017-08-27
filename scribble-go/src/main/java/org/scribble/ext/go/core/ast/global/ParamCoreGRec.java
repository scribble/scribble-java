package org.scribble.ext.go.core.ast.global;

import java.util.Set;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreRec;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.ParamCoreLEnd;
import org.scribble.ext.go.core.ast.local.ParamCoreLRecVar;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;

public class ParamCoreGRec extends ParamCoreRec<ParamCoreGType, Global> implements ParamCoreGType
{
	public ParamCoreGRec(RecVar recvar, ParamCoreGType body)
	{
		super(recvar, body);
	}
	
	@Override
	public Set<ParamRole> getParamRoles()
	{
		return this.body.getParamRoles();
	}

	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public ParamCoreLType project(ParamCoreAstFactory af, ParamActualRole subj) throws ParamCoreSyntaxException
	{
		//ParamCoreLType proj = this.body.project(af, r, ranges);
		ParamCoreLType proj = this.body.project(af, subj);
		if (proj instanceof ParamCoreLRecVar)
		{
			ParamCoreLRecVar rv = (ParamCoreLRecVar) proj;
			return rv.recvar.equals(this.recvar) ? ParamCoreLEnd.END : rv;
		}
		else
		{	
			return af.ParamCoreLRec(this.recvar, proj);
		}
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

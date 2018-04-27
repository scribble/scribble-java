package org.scribble.ext.go.core.ast.global;

import java.util.Set;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreForeach;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class RPCoreGForeach extends RPCoreForeach<RPCoreGType, Global> implements RPCoreGType
{
	public RPCoreGForeach(Role role, RPIndexVar var, RPIndexExpr src, RPIndexExpr dest, RPCoreGType body, RPCoreGType cont)
	{
		super(role, var, src, dest, body, cont);
	}
	
	@Override
	public boolean isWellFormed(GoJob job, GProtocolDecl gpd)
	{
		//return this.body.isWellFormed(job, gpd);
		throw new RuntimeException("TODO: ");
	}
	
	@Override
	public Set<RPIndexedRole> getIndexedRoles()
	{
		//return this.body.getIndexedRoles();  // foreach subjects?
		throw new RuntimeException("TODO: ");
	}

	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj) throws RPCoreSyntaxException
	{
		/*//ParamCoreLType proj = this.body.project(af, r, ranges);
		RPCoreLType proj = this.body.project(af, subj);
		if (proj instanceof RPCoreLRecVar)
		{
			RPCoreLRecVar rv = (RPCoreLRecVar) proj;
			return rv.recvar.equals(this.recvar) ? RPCoreLEnd.END : rv;
		}
		else
		{	
			return af.ParamCoreLRec(this.recvar, proj);
		}*/
		throw new RuntimeException("TODO: ");
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreGForeach))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreGForeach;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 4273;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}

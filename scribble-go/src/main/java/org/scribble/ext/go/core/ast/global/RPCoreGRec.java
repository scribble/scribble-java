package org.scribble.ext.go.core.ast.global;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreRec;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.ast.local.RPCoreLEnd;
import org.scribble.ext.go.core.ast.local.RPCoreLRecVar;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class RPCoreGRec extends RPCoreRec<RPCoreGType, Global> implements RPCoreGType
{
	public RPCoreGRec(RecVar recvar, RPCoreGType body)
	{
		super(recvar, body);
	}
	
	@Override
	public RPCoreGType subs(RPCoreAstFactory af, RPCoreType<Global> old, RPCoreType<Global> neu)
	{
		if (this.equals(old))
		{
			return (RPCoreGType) neu;
		}
		else
		{
			return af.ParamCoreGRec(this.recvar, this.body.subs(af, old, neu));
		}
	}
	
	@Override
	public boolean isWellFormed(GoJob job, //Stack<Map<RPForeachVar, RPInterval>> context, 
			Stack<Map<RPIndexVar, RPInterval>> context, 
			GProtocolDecl gpd, Smt2Translator smt2t)
	{
		return this.body.isWellFormed(job, context, gpd, smt2t);
	}
	
	@Override
	public Set<RPIndexedRole> getIndexedRoles()
	{
		return this.body.getIndexedRoles();
	}

	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj, Smt2Translator smt2t) throws RPCoreSyntaxException
	{
		//ParamCoreLType proj = this.body.project(af, r, ranges);
		RPCoreLType proj = this.body.project(af, subj, smt2t);
		if (proj instanceof RPCoreLRecVar)
		{
			RPCoreLRecVar rv = (RPCoreLRecVar) proj;
			return rv.recvar.equals(this.recvar) ? RPCoreLEnd.END : rv;
		}
		else
		{	
			return af.ParamCoreLRec(this.recvar, proj);
		}
	}
	
  // G proj R \vec{C} r[z]
	@Override
	public RPCoreLType project3(RPCoreAstFactory af, Set<Role> roles, Set<RPAnnotatedInterval> ivals, RPIndexedRole subj) throws RPCoreSyntaxException
	{
		return af.ParamCoreLRec(this.recvar, this.body.project3(af, roles, ivals, subj));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreGRec))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreGRec;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2333;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}

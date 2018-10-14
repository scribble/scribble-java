package org.scribble.ext.go.core.ast.global;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreRecVar;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

	
public class RPCoreGRecVar extends RPCoreRecVar<Global> implements RPCoreGType
{
	public RPCoreGRecVar(RecVar var)
	{
		super(var);
	}
	
	@Override
	public RPCoreGType subs(RPCoreAstFactory af, RPCoreType<Global> old, RPCoreType<Global> neu) 
	{
		if (this.equals(old))  // Shouldn't happen?
		{
			return (RPCoreGType) neu;
		}
		return this;
	}
	
	@Override
	public boolean isWellFormed(GoJob job, Stack<Map<RPForeachVar, RPInterval>> context, GProtocolDecl gpd, Smt2Translator smt2t)
	{
		return true;
	}
	
	@Override
	public Set<RPIndexedRole> getIndexedRoles()
	{
		return Collections.emptySet();
	}

	@Override
	//public ParamCoreLRecVar project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges)
	public RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj, Smt2Translator smt2t) throws RPCoreSyntaxException
	{
		return af.ParamCoreLRecVar(this.recvar);
	}
	
  // G proj R \vec{C} r[z]
	@Override
	public RPCoreLType project3(RPCoreAstFactory af, Set<Role> roles, Set<RPAnnotatedInterval> ivals, RPIndexedRole subj) throws RPCoreSyntaxException
	{
		return af.ParamCoreLRecVar(this.recvar);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof RPCoreGRecVar))
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
		return o instanceof RPCoreGRecVar;
	}
}

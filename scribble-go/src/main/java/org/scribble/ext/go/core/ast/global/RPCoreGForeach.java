package org.scribble.ext.go.core.ast.global;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreForeach;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.ast.local.RPCoreLCont;
import org.scribble.ext.go.core.ast.local.RPCoreLEnd;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexFactory;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class RPCoreGForeach extends RPCoreForeach<RPCoreGType, Global> implements RPCoreGType
{
	public RPCoreGForeach(//Role role, RPForeachVar var, RPIndexExpr start, RPIndexExpr end, 
			Set<Role> roles, Set<RPAnnotatedInterval> ivals,
			RPCoreGType body, RPCoreGType seq)
	{
		//super(role, var, start, end, body, seq);
		super(roles, ivals, body, seq);
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
			return af.RPCoreGForeach(//this.role, this.param, this.start, this.end,
					this.roles, this.ivals,
					this.body.subs(af, old, neu), this.seq.subs(af, old, neu));
		}
	}
	
	@Override
	public boolean isWellFormed(GoJob job, Stack<Map<RPForeachVar, RPInterval>> context, GProtocolDecl gpd)
	{
		// CHECKME: interval constraints, nested foreach constraints, etc. -- cf. RPCoreGChoice
		Map<RPForeachVar, RPInterval> curr = new HashMap<>();
		this.ivals.forEach(ival -> curr.put(ival.var, ival));
		context.push(curr);
		boolean res = this.body.isWellFormed(job, context, gpd);
		context.pop();
		return res;
	}
	
	@Override
	public Set<RPIndexedRole> getIndexedRoles()  // cf. RPForeachDel#leaveIndexVarCollection (though not currently used)
	{
		/*RPIndexVar tmp = RPIndexFactory.ParamIntVar(this.param.toString()); 
				// HACK FIXME: because RPIndexVar and RPForeachVar now distinguished

		Set<RPInterval> d = Stream.of(new RPInterval(this.start, this.end)).collect(Collectors.toSet());
		Set<RPInterval> var = Stream.of(//new RPInterval(this.param, this.param)
				new RPInterval(tmp, tmp)
		).collect(Collectors.toSet());
		Set<RPIndexedRole> irs = this.body.getIndexedRoles()
				.stream().map(
						ir -> ir.intervals.equals(var)
								? new RPIndexedRole(ir.getName().toString(), d)
								: ir
				).collect(Collectors.toSet());
		irs.addAll(this.seq.getIndexedRoles());*/

		Set<RPIndexedRole> found = this.body.getIndexedRoles();
		Set<RPIndexedRole> done = new HashSet<>();
		Set<RPIndexedRole> res = new HashSet<>();
		for (RPAnnotatedInterval ival : this.ivals)
		{
			RPIndexVar tmp = RPIndexFactory.ParamIntVar(ival.var.toString()); 
					// HACK FIXME: because RPIndexVar and RPForeachVar now distinguished -- unify?

			Set<RPInterval> d = Stream.of(new RPInterval(ival.start, ival.end)).collect(Collectors.toSet()); 
					// TODO: multidim intervals (currently singleton set for onedim)
			Set<RPInterval> var = Stream.of(new RPInterval(tmp, tmp)).collect(Collectors.toSet());
			for (RPIndexedRole ir : found)
			{
				if (ir.intervals.equals(var))
				{
					done.add(ir);
					res.add(new RPIndexedRole(ir.getName().toString(), d));
				}
			}
		}

		found.removeAll(done);
		res.addAll(found);
		res.addAll(this.seq.getIndexedRoles());
		return res;
	}

	// G proj r \vec{D}
	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj) throws RPCoreSyntaxException
	{
		RPCoreLType seq = this.seq.project(af, subj);
		if (this.roles.contains(subj.getName()))  // FIXME: factor out -- cf. RPCoreGChoice#project
		{
			/*RPIndexVar tmp = RPIndexFactory.ParamIntVar(this.param.toString()); 
				// HACK FIXME: because RPIndexVar and RPForeachVar now distinguished*/

			/* // FIXME: subj.intervals was meant for multidim nat intervals, but that should be refactored inside (a single) RPInterval
			if (subj.intervals.size() != 1)
			{
				throw new RuntimeException("TODO: " + )
			}*/
			Set<RPAnnotatedInterval> filtered = this.ivals.stream().filter(iv -> subj.intervals.stream().anyMatch(x -> iv.isSame(x))).collect(Collectors.toSet());
					// CHECKME: actual interval inclusion? (vs. "syntactic" equals) -- also RPCoreGChoice#project?
			//RPIndexedRole tmp = new RPIndexedRole(subj.getName().toString(), filtered);  // FIXME: should be this, but currently RPIndexedRole interval set for multidim nat intervals (but with >1 TODO exception)
			RPCoreGForeach tmp = af.RPCoreGForeach(this.roles, this.ivals, body, af.ParamCoreGEnd());  
					// CHECKME: should be "end" so that it will be discarded?  seq will be substituted for cont in the final "unrolling" of foreach
			
			RPCoreLType proj = tmp.project2(af, subj.getName(), filtered);
			return proj.subs(af, RPCoreLCont.CONT, seq);
		}
		else 
		{
			RPCoreLType body = this.body.project(af, subj);
			if (body instanceof RPCoreLEnd)
			{
				return seq;
			}
			else
			{
				return af.RPCoreLForeach(this.roles, this.ivals, body, seq);
			}
		}
	}

	// G proj r \vec{C}
	private RPCoreLType project2(RPCoreAstFactory af, Role name, Set<RPAnnotatedInterval> ivals) throws RPCoreSyntaxException
	{
		/*RPInterval v = new RPInterval(this.start, this.end);
		if (subj.intervals.contains(v))
		{
			RPRoleVariant indexed = new RPRoleVariant(subj.getName().toString(),
					Stream.of(//new RPInterval(this.param, this.param))
							new RPInterval(tmp, tmp)).collect(Collectors.toSet()), Collections.emptySet());
			RPCoreLType body = proj.project(af, indexed);
			return body.subs(af, RPCoreLEnd.END, seq);
		}
		else
		{
			// ...else substitute cont for end in body ?
			throw new RuntimeException("Shouldn't get in here? " + this + ", " + subj);
		}*/

		if (ivals.isEmpty())
		{
			return RPCoreLCont.CONT;
		}
		RPAnnotatedInterval max = ivals.stream()
				.filter(x -> ivals.stream().filter(y -> !x.equals(y)).allMatch(y -> ((RPIndexInt) x.start).val > ((RPIndexInt) y.start).val))
				.findFirst().get();
						// FIXME: non-RPIndexInt start exprs for nat intervals -- FIXME: generalised interval comparison (multidim)
		RPIndexVar var = RPIndexFactory.ParamIntVar(max.var.toString());  // N.B. not RPForeachVar -- occurrences in body are parsed as RPIndexVar, not RPForeachVar
		RPCoreLType proj = this.body.project3(af, this.roles, this.ivals, 
				new RPIndexedRole(name.toString(), Stream.of(new RPInterval(var, var)).collect(Collectors.toSet())));
		Set<RPAnnotatedInterval> tmp = new HashSet<>(ivals);
		tmp.remove(max);
		return proj.subs(af, RPCoreLCont.CONT, project2(af, name, tmp));
	}

  // G proj R \vec{C} r[z]
	@Override
	public RPCoreLType project3(RPCoreAstFactory af, Set<Role> roles, Set<RPAnnotatedInterval> ivals, RPIndexedRole subj) throws RPCoreSyntaxException
	{
		return af.RPCoreLForeach(this.roles, this.ivals, this.body.project3(af, roles, ivals, subj), this.seq.project3(af, roles, ivals, subj));
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

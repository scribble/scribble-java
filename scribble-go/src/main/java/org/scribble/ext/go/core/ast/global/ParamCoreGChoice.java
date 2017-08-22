package org.scribble.ext.go.core.ast.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreChoice;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.ParamRole;
import org.scribble.ext.go.core.ast.local.ParamCoreLActionKind;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class ParamCoreGChoice extends ParamCoreChoice<ParamCoreGType, Global> implements ParamCoreGType
{
	public final ParamRole src;   // Singleton -- no disconnect for now
	public final ParamRole dest;  // this.dest == super.role -- arbitrary?

	public ParamCoreGChoice(ParamRole src, ParamRole dest, Map<ParamCoreMessage, ParamCoreGType> cases)
	{
		super(dest, cases);
		this.src = src;
		this.dest = dest;
	}

	@Override
	public ParamCoreLType project(ParamCoreAstFactory af, Role r) throws ParamCoreSyntaxException
	{
		Map<ParamCoreMessage, ParamCoreLType> projs = new HashMap<>();
		for (Entry<ParamCoreMessage, ParamCoreGType> e : this.cases.entrySet())
		{
			ParamCoreMessage a = e.getKey();
			projs.put(a, e.getValue().project(af, r));
					// N.B. local actions directly preserved from globals -- so core-receive also has assertion (cf. ParamGActionTransfer.project, currently no ParamLReceive)
					// FIXME: receive assertion projection -- should not be the same as send?
		}
		
		// "Simple" cases
		if (this.src.equals(r) || this.dest.equals(r))
		{
			ParamRole role = this.src.equals(r) ? this.dest : this.src;
			ParamCoreLActionKind kind = this.src.equals(r) ? ParamCoreLActionKind.SEND : ParamCoreLActionKind.RECEIVE;
			return af.ParamCoreLChoice(role, kind, projs);
		}
		
		throw new RuntimeException("[param-core] TODO: " + this);

		/*// "Merge"
		if (projs.values().stream().anyMatch(v -> (v instanceof ParamCoreLRecVar)))
		{
			if (projs.values().stream().anyMatch(v -> !(v instanceof ParamCoreLRecVar)))
			{
				throw new ParamCoreSyntaxException("[assrt-core] Cannot project \n" + this + "\n onto " + r + ": cannot merge unguarded rec vars.");
			}

			Set<RecVar> rvs = projs.values().stream().map(v -> ((ParamCoreLRecVar) v).recvar).collect(Collectors.toSet());
			Set<List<ParamArithFormula>> fs = projs.values().stream().map(v -> ((ParamCoreLRecVar) v).annotexprs).collect(Collectors.toSet());  // FIXME? syntactic equality of exprs
			if (rvs.size() > 1 || fs.size() > 1)
			{
				throw new ParamCoreSyntaxException("[assrt-core] Cannot project \n" + this + "\n onto " + r + ": mixed unguarded rec vars: " + rvs);
			}

			return af.ParamCoreLRecVar(rvs.iterator().next(), fs.iterator().next());
		}
		
		List<ParamCoreLType> filtered = projs.values().stream()
			.filter(v -> !v.equals(ParamCoreLEnd.END))
			////.collect(Collectors.toMap(e -> Map.Entry<ParamCoreAction, ParamCoreLType>::getKey, e -> Map.Entry<ParamCoreAction, ParamCoreLType>::getValue));
			//.map(v -> (ParamCoreLChoice) v)
			.collect(Collectors.toList());
	
		if (filtered.size() == 0)
		{
			return ParamCoreLEnd.END;
		}
		else if (filtered.size() == 1)
		{
			return //(ParamCoreLChoice)
					filtered.iterator().next();  // RecVar disallowed above
		}
		
		List<ParamCoreLChoice> choices = filtered.stream().map(v -> (ParamCoreLChoice) v).collect(Collectors.toList());
	
		Set<Role> roles = choices.stream().map(v -> v.role).collect(Collectors.toSet());  // Subj not one of curent src/dest, must be projected inside each case to a guarded continuation
		if (roles.size() > 1)
		{
			throw new ParamCoreSyntaxException("[assrt-core] Cannot project \n" + this + "\n onto " + r + ": mixed peer roles: " + roles);
		}
		Set<ParamCoreActionKind<?>> kinds = choices.stream().map(v -> v.kind).collect(Collectors.toSet());  // Subj not one of curent src/dest, must be projected inside each case to a guarded continuation
		if (kinds.size() > 1)
		{
			throw new ParamCoreSyntaxException("[assrt-core] Cannot project \n" + this + "\n onto " + r + ": mixed action kinds: " + kinds);
		}
		
		Map<ParamCoreAction, ParamCoreLType> merged = new HashMap<>();
		choices.forEach(v ->
		{
			if (!v.kind.equals(ParamCoreLActionKind.RECEIVE))
			{
				throw new RuntimeException("[assrt-core] Shouldn't get here: " + v);  // By role-enabling?
			}
			v.cases.entrySet().forEach(e ->
			{
				ParamCoreAction k = e.getKey();
				ParamCoreLType b = e.getValue();
				if (merged.containsKey(k)) //&& !b.equals(merged.get(k))) // TODO
				{
					throw new RuntimeException("[assrt-core] Cannot project \n" + this + "\n onto " + r + ": cannot merge: " + b + " and " + merged.get(k));
				}
				merged.put(k, b);
			});
		});
		
		return af.ParamCoreLChoice(roles.iterator().next(), ParamCoreLActionKind.RECEIVE, merged);*/
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2339;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.src.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreGChoice))
		{
			return false;
		}
		return super.equals(obj) && this.src.equals(((ParamCoreGChoice) obj).src);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreGChoice;
	}

	@Override
	public String toString()
	{
		return this.src.toString() + "->" + this.dest + casesToString();  // toString needed?
	}
}

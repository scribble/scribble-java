package org.scribble.ext.go.core.ast.global;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreChoice;
import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.ParamCoreLActionKind;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.kind.Global;

public class ParamCoreGChoice extends ParamCoreChoice<ParamCoreGType, Global> implements ParamCoreGType
{
	public final ParamRole src;   // Singleton -- no disconnect for now
	public final ParamRole dest;  // this.dest == super.role -- arbitrary?

	public ParamCoreGChoice(ParamRole src, ParamCoreGActionKind kind, ParamRole dest, Map<ParamCoreMessage, ParamCoreGType> cases)
	{
		super(dest, kind, cases);
		this.src = src;
		this.dest = dest;
	}

	@Override
	public ParamCoreGActionKind getKind()
	{
		return (ParamCoreGActionKind) this.kind;
	}
	
	@Override
	public Set<ParamRole> getParamRoles()
	{
		Set<ParamRole> res = Stream.of(this.src, this.dest).collect(Collectors.toSet());
		this.cases.values().forEach(c -> res.addAll(c.getParamRoles()));
		return res;
	}

	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public ParamCoreLType project(ParamCoreAstFactory af, ParamActualRole subj) throws ParamCoreSyntaxException
	{
		if (this.kind != ParamCoreGActionKind.CROSS_TRANSFER)
		{
			throw new RuntimeException("[param-core] TODO: " + this);
		}
		
		Map<ParamCoreMessage, ParamCoreLType> projs = new HashMap<>();
		for (Entry<ParamCoreMessage, ParamCoreGType> e : this.cases.entrySet())
		{
			ParamCoreMessage a = e.getKey();
			//projs.put(a, e.getValue().project(af, r, ranges));
			projs.put(a, e.getValue().project(af, subj));
					// N.B. local actions directly preserved from globals -- so core-receive also has assertion (cf. ParamGActionTransfer.project, currently no ParamLReceive)
					// FIXME: receive assertion projection -- should not be the same as send?
		}
		
		// "Simple" cases
		//if (this.src.getName().equals(r))
		if (this.src.getName().equals(subj.getName()) && subj.ranges.contains(this.src.ranges.iterator().next()))  // FIXME: factor out?
		{
			return af.ParamCoreLChoice(this.dest, ParamCoreLActionKind.SEND_ALL, projs);
		}
		else if (this.dest.getName().equals(subj.getName()) && subj.ranges.contains(this.dest.ranges.iterator().next()))
		{
			return af.ParamCoreLChoice(this.src, ParamCoreLActionKind.RECEIVE_ALL, projs);
		}
		
		// src name != dest name
		//return merge(af, r, ranges, projs);
		return merge(af, subj, projs);
	}
		
	//private ParamCoreLType merge(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges, Map<ParamCoreMessage, ParamCoreLType> projs) throws ParamCoreSyntaxException
	private ParamCoreLType merge(ParamCoreAstFactory af, ParamActualRole r, Map<ParamCoreMessage, ParamCoreLType> projs) throws ParamCoreSyntaxException
	{
		// "Merge"
		Collection<ParamCoreLType> values = projs.values();
		if (values.size() > 1)
		{
			throw new ParamCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r 
					//+ " for " + ranges
					+ ": cannot merge for: " + projs.keySet());
		}
		
		return values.iterator().next();
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
		return this.src.toString() + this.kind + this.dest + casesToString();  // toString needed?
	}
}

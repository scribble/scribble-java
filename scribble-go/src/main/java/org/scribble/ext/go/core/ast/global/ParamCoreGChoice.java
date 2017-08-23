package org.scribble.ext.go.core.ast.global;

import java.util.Collection;
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
		
		// "Merge"
		Collection<ParamCoreLType> values = projs.values();
		if (values.size() > 1)
		{
			throw new ParamCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r + ": cannot merge for: " + projs.keySet());
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
		return this.src.toString() + "->" + this.dest + casesToString();  // toString needed?
	}
}

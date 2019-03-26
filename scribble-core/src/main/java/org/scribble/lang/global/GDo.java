package org.scribble.lang.global;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.job.ScribbleException;
import org.scribble.lang.Do;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.LType;
import org.scribble.type.Arg;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class GDo extends Do<Global, GProtocolName> implements GType
{
	public GDo(org.scribble.ast.Do<Global> source, GProtocolName proto,
			List<Role> roles, List<Arg<? extends NonRoleParamKind>> args)
	{
		super(source, proto, roles, args);
	}

	@Override
	public GDo reconstruct(org.scribble.ast.Do<Global> source,
			GProtocolName proto, List<Role> roles, List<Arg<? extends NonRoleParamKind>> args)
	{
		return new GDo(source, proto, roles, args);
	}

	@Override
	public GDo substitute(Substitutions subs)
	{
		return (GDo) super.substitute(subs);
	}

	// CHECKME: factor up to base?  (though currently there is no LDo)
	@Override
	public GType getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		GProtocolName fullname = this.proto;
		SubprotoSig sig = new SubprotoSig(fullname, this.roles, this.args);
		RecVar rv = i.makeRecVar(sig);
		if (i.hasSig(sig))
		{
			return new GContinue(getSource(), rv);
		}
		i.pushSig(sig);
		GProtocol g = i.job.getJobContext().getIntermediate(fullname);
		Substitutions subs = 
				new Substitutions(g.roles, this.roles, g.params, this.args);
		GSeq inlined = g.def.substitute(subs).getInlined(i);//, stack);  
				// i.e. returning a GSeq -- rely on parent GSeq to inline
		i.popSig();
		return new GRecursion(null, rv, inlined);
	}

	@Override
	public LType project(Role self)
	{
		// TODO: consider role fixing and do pruning
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		throw new RuntimeException("Unsupported for Do: " + this);
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		throw new RuntimeException("Unsupported for Do: " + this);
	}

	@Override
	public org.scribble.ast.global.GDo getSource()
	{
		return (org.scribble.ast.global.GDo) super.getSource();
	}

	@Override
	public int hashCode()
	{
		int hash = 1303;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GDo))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GDo;
	}
}


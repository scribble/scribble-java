package org.scribble.lang.local;

import java.util.Collections;
import java.util.List;

import org.scribble.lang.Do;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Local;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class LDo extends Do<Local, LProtocolName> implements LType
{
	public LDo(org.scribble.ast.Do<Local> source, LProtocolName proto,
			List<Role> roles)
	{
		super(source, proto, roles);
	}

	@Override
	public LDo reconstruct(org.scribble.ast.Do<Local> source,
			LProtocolName proto, List<Role> roles)
	{
		return new LDo(source, proto, roles);
	}

	@Override
	public LDo substitute(Substitutions<Role> subs)
	{
		return (LDo) super.substitute(subs);
	}

	@Override
	public LType getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		Substitutions<Role> subs = 
				new Substitutions<>(this.roles, i.peek().roles);  // FIXME: args
		LProtocolName fullname = this.proto;
		SubprotoSig sig = new SubprotoSig(fullname, this.roles, 
				Collections.emptyList());  // FIXME
		if (i.hasSig(sig))
		{
			RecVar rv = i.makeRecVar(sig);
			return new LContinue(getSource(), rv);
		}
		i.pushSig(sig);
		/*LSeq inlined = i.job.getJobContext().getIntermediate(fullname).body.substitute(subs)
				.getInlined(i);//, stack);  // i.e. returning a GSeq -- rely on parent GSeq to inline
		i.popSig();
		return inlined;*/
		throw new RuntimeException("TODO?");
	}

	@Override
	public org.scribble.ast.local.LDo getSource()
	{
		return (org.scribble.ast.local.LDo) super.getSource();
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
		if (!(o instanceof LDo))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LDo;
	}
}


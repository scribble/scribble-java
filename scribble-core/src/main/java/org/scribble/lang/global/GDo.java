package org.scribble.lang.global;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.scribble.lang.Do;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class GDo extends Do<Global, GProtocolName> implements GType
{
	public GDo(org.scribble.ast.Do<Global> source, GProtocolName proto,
			List<Role> roles)
	{
		super(source, proto, roles);
	}

	@Override
	public GDo reconstruct(org.scribble.ast.Do<Global> source,
			GProtocolName proto, List<Role> roles)
	{
		return new GDo(source, proto, roles);
	}

	@Override
	public GDo substitute(Substitutions<Role> subs)
	{
		return (GDo) super.substitute(subs);
	}

	@Override
	public GType getInlined(GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		Substitutions<Role> subs = 
				new Substitutions<>(this.roles, stack.peek().roles);  // FIXME: args
		GProtocolName fullname = this.proto;
		SubprotoSig sig = new SubprotoSig(fullname, this.roles, 
				Collections.emptyList());  // FIXME
		if (stack.contains(sig))
		{
			RecVar rv = t.makeRecVar(sig);
			return new GContinue(getSource(), rv);
		}
		stack.push(sig);
		GSeq inlined = t.job.getJobContext().getIntermediate(fullname).body.substitute(subs)
				.getInlined(t, stack);  // i.e. returning a GSeq -- rely on parent GSeq to inline
		stack.pop();
		return inlined;
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


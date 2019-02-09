package org.scribble.lang.global;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.scribble.job.Job;
import org.scribble.lang.Do;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
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
	public GType getInlined(Job job, Deque<SubprotoSig> stack)
	{
		GProtocolName fullname = this.proto;
		SubprotoSig sig = new SubprotoSig(fullname, this.roles, 
				Collections.emptyList());  // FIXME
		if (stack.contains(sig))
		{
			return this;
		}
		stack.push(sig);
		return job.getJobContext().getIntermediate(fullname).body.getInlined(job,
				stack);  // i.e. returning a GSeq -- rely on parent GSeq to inline
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


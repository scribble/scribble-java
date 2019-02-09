package org.scribble.lang.global;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.job.Job;
import org.scribble.lang.Protocol;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

public class GProtocol extends
		Protocol<Global, GProtocolName, GSeq> implements GType
{
	public GProtocol(ProtocolDecl<Global> source, GProtocolName fullname,
			List<Role> roles, // List<?> params,
			GSeq body)
	{
		super(source, fullname, roles, body);
	}

	@Override
	public GProtocol reconstruct(ProtocolDecl<Global> source,
			GProtocolName fullname, List<Role> roles, GSeq body)
	{
		return new GProtocol(source, fullname, roles, body);
	}
	
	// Top-level call should give an empty stack
	// But stack is non-empty for recursive calls with, e.g., permuted role args
	@Override
	public GProtocol getInlined(Job job, Deque<SubprotoSig> stack)
	{
		stack.push(new SubprotoSig(this.fullname, this.roles, 
				Collections.emptyList()));  // FIXME
		GProtocolDecl source = getSource();  // CHECKME: or empty source?
		GSeq body = (GSeq) this.body.getInlined(job, stack);
		return reconstruct(source, this.fullname, this.roles, body);
	}
	
	@Override
	public GProtocolDecl getSource()
	{
		return (GProtocolDecl) super.getSource();
	}
	
	@Override
	public String toString()
	{
		return "global" + super.toString();
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
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
		if (!(o instanceof GProtocol))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GProtocol;
	}
}

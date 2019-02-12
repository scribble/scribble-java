package org.scribble.lang.global;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.lang.Protocol;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class GProtocol extends
		Protocol<Global, GProtocolName, GSeq> implements GType
{
	public GProtocol(ProtocolDecl<Global> source, GProtocolName fullname,
			List<Role> roles, 
			// List<?> params,  // TODO
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

	@Override
	public GType substitute(Substitutions<Role> subs)
	{
		List<Role> roles = this.roles.stream().map(x -> subs.apply(x))
				.collect(Collectors.toList());
		return reconstruct(getSource(), this.fullname, roles,
				this.body.substitute(subs));
	}
	
	// Pre: stack.peek is the sig for the calling Do (or top-level entry)
	// i.e., it gives the roles/args at the call-site
	@Override
	public GRecursion getInlined(GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		SubprotoSig sig = stack.peek();
		Substitutions<Role> subs = new Substitutions<>(this.roles, sig.roles);  // FIXME: args
		GSeq body = this.body.substitute(subs).getInlined(t, stack);
		GProtocolDecl source = getSource();  // CHECKME: or empty source?
		RecVar rv = t.makeRecVar(sig);
		return new GRecursion(source, rv, body);
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

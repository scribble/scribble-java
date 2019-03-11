package org.scribble.lang.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Local;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class LProjection extends
		LProtocol
{
	public final GProtocolName parent;
	public final Role self;
	
	public LProjection(GProtocolName parent, Role self, LProtocolName fullname,
			List<Role> roles, 
			// List<?> params,  // TODO
			LSeq body)
	{
		super(null, fullname, roles, body);
		this.parent = parent;
		this.self = self;
	}

	@Override
	public LProjection reconstruct(ProtocolDecl<Local> source,
			LProtocolName fullname, List<Role> roles, LSeq body)
	{
		return new LProjection(this.parent, this.self, fullname, roles, body);
	}

	@Override
	public LType substitute(Substitutions<Role> subs)
	{
		List<Role> roles = this.roles.stream().map(x -> subs.apply(x))
				.collect(Collectors.toList());
		return reconstruct(getSource(), this.fullname, roles,
				this.def.substitute(subs));
	}
	
	// Pre: stack.peek is the sig for the calling Do (or top-level entry)
	// i.e., it gives the roles/args at the call-site
	@Override
	public LRecursion getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		SubprotoSig sig = i.peek();
		Substitutions<Role> subs = new Substitutions<>(this.roles, sig.roles);  // FIXME: args
		LSeq body = this.def.substitute(subs).getInlined(i);//, stack);
		LProtocolDecl source = getSource();  // CHECKME: or empty source?
		RecVar rv = i.makeRecVar(sig);
		return new LRecursion(source, rv, body);
	}
	
	@Override
	public LProtocolDecl getSource()
	{
		return (LProtocolDecl) super.getSource();
	}
	
	@Override
	public String toString()
	{
		return "local protocol " + this.fullname 
				 + "(" + this.roles.stream().map(x -> x.toString())
						.collect(Collectors.joining(", ")) + ")"
				+ " projects " + this.parent
				+ "@" + this.self + " {\n" + this.def + "\n}";
	}

	@Override
	public int hashCode()
	{
		int hash = 3167;
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
		if (!(o instanceof LProjection))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LProjection;
	}
}

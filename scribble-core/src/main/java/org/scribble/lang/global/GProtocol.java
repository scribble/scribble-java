package org.scribble.lang.global;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.lang.Protocol;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.LProjection;
import org.scribble.lang.local.LSeq;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class GProtocol extends
		Protocol<Global, GProtocolName, GSeq> implements GType
{
	public GProtocol(ProtocolDecl<Global> source, GProtocolName fullname,
			List<Role> roles, 
			// List<?> params,  // TODO
			GSeq def)
	{
		super(source, fullname, roles, def);
	}

	@Override
	public GProtocol reconstruct(ProtocolDecl<Global> source,
			GProtocolName fullname, List<Role> roles, GSeq def)
	{
		return new GProtocol(source, fullname, roles, def);
	}

	@Override
	public GType substitute(Substitutions<Role> subs)
	{
		List<Role> roles = this.roles.stream().map(x -> subs.apply(x))
				.collect(Collectors.toList());
		return reconstruct(getSource(), this.fullname, roles,
				this.def.substitute(subs));
	}
	
	// Pre: stack.peek is the sig for the calling Do (or top-level entry)
	// i.e., it gives the roles/args at the call-site
	@Override
	public GProtocol getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		SubprotoSig sig = i.peek();
		Substitutions<Role> subs = new Substitutions<>(this.roles, sig.roles);  // FIXME: args
		GSeq body = this.def.substitute(subs).getInlined(i);//, stack);
		RecVar rv = i.makeRecVar(sig);
		GRecursion rec = new GRecursion(null, rv, body);  // CHECKME: or protodecl source?
		GProtocolDecl source = getSource();
		GSeq def = new GSeq(null, Stream.of(rec).collect(Collectors.toList()));
		return new GProtocol(source, fullname, this.roles, def);
	}
	
	@Override
	public GProtocol unfoldAllOnce(STypeUnfolder<Global> u)
	{
		GSeq unf = (GSeq) this.def.unfoldAllOnce(u);
		return reconstruct(getSource(), this.fullname, this.roles, unf);
	}
	
	// Currently assuming inlining (or at least "disjoint" protodecl projection, without role fixing)
	@Override
	public LProjection project(Role self)
	{
		LProtocolName fullname = new LProtocolName(this.fullname + "_" + self);
		LSeq body = (LSeq) this.def.project(self);
		Set<Role> tmp = body.getRoles();
		List<Role> roles = this.roles.stream()
				.map(x -> x.equals(self) ? Role.SELF : x).filter(x -> tmp.contains(x))
				.collect(Collectors.toList());
		return new LProjection(this.fullname, self, fullname, roles, body);
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

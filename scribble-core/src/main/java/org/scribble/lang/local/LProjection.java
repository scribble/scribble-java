package org.scribble.lang.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ProtocolDecl;
import org.scribble.lang.ProtocolMod;
import org.scribble.type.kind.Local;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.Role;

public class LProjection extends LProtocol
{
	public final GProtocolName parent;
	
	public LProjection(List<ProtocolMod> mods, LProtocolName fullname,
			List<Role> roles, Role self,
			List<MemberName<? extends NonRoleParamKind>> params, GProtocolName parent,
			LSeq body)
	{
		super(null, mods, fullname, roles, self, params, body);
		this.parent = parent;
	}

	@Override
	public LProjection reconstruct(ProtocolDecl<Local> source,
			List<ProtocolMod> mods, LProtocolName fullname, List<Role> roles,
			Role self, List<MemberName<? extends NonRoleParamKind>> params, LSeq body)
	{
		return new LProjection(mods, fullname, roles, this.self, params,
				this.parent, body);
	}

	/*@Override
	public LType substitute(Substitutions subs)
	{
		*List<Role> roles = this.roles.stream().map(x -> subs.subsRole(x))
				.collect(Collectors.toList());
		return reconstruct(getSource(), this.mods, this.fullname, roles,
				this.def.substitute(subs));
	}*/
	
	/*// Pre: stack.peek is the sig for the calling Do (or top-level entry)
	// i.e., it gives the roles/args at the call-site
	@Override
	public LRecursion getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		SubprotoSig sig = i.peek();
		Substitutions subs = new Substitutions(this.roles, sig.roles, this.params,
				sig.args);
		LSeq body = this.def.substitute(subs).getInlined(i);//, stack);
		LProtocolDecl source = getSource();  // CHECKME: or empty source?
		RecVar rv = i.makeRecVar(sig);
		return new LRecursion(source, rv, body);
	}*/
	
	/*@Override
	public LProtocolDecl getSource()
	{
		return (LProtocolDecl) super.getSource();
	}*/
	
	@Override
	public String toString()
	{
		return this.mods.stream().map(x -> x.toString() + " ")
				.collect(Collectors.joining())
				+ "local protocol " + this.fullname.getSimpleName()
				+ paramsToString()
				+ rolesToString()
				+ " projects " + this.parent
				+ " {\n" + this.def + "\n}";
	}

	@Override
	public int hashCode()
	{
		int hash = 3167;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.parent.hashCode();
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

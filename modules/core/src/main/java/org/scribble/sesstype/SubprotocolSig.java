package org.scribble.sesstype;

import java.util.LinkedList;
import java.util.List;

import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;

public class SubprotocolSig
{
	public ProtocolName<? extends ProtocolKind> fmn;
	//public Scope scope;
	public List<Role> roles;
	public List<Arg<? extends Kind>> args;

	//public SubprotocolSignature(ProtocolName fmn, Scope scope, List<Role> roles, List<Argument<? extends Kind>> args)
	public SubprotocolSig(ProtocolName<? extends ProtocolKind> fmn, List<Role> roles, List<Arg<? extends NonRoleArgKind>> args)
	{
		this.fmn = fmn;
		//this.scope = scope;
		this.roles = new LinkedList<>(roles);
		this.args = new LinkedList<>(args);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1093;
		hash = 31 * hash + this.fmn.hashCode();
		//hash = 31 * hash + this.scope.hashCode();
		hash = 31 * hash + this.roles.hashCode();
		hash = 31 * hash + this.args.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		//if (o == null || this.getClass() != o.getClass())
		if (!(o instanceof SubprotocolSig))
		{
			return false;
		}
		SubprotocolSig subsig = (SubprotocolSig) o;
		return this.fmn.equals(subsig.fmn) //&& this.scope.equals(subsig.scope)
				&& this.roles.equals(subsig.roles) && this.args.equals(subsig.args);
	}
	
	@Override
	public String toString()
	{
		String args = this.args.toString();
		String roles = this.roles.toString();
		return //this.scope + ":" + 
				this.fmn + "<" + args.substring(1, args.length() - 1) + ">(" + roles.substring(1, roles.length() - 1) + ")";
	}
}

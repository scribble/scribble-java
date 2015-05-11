package org.scribble2.sesstype;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;

// FIXME: rename to unscoped
public class SubprotocolSignature
{
	public ProtocolName fmn;
	public Scope scope;
	public List<Role> roles;
	public List<Argument<? extends Kind>> args;

	public SubprotocolSignature(ProtocolName fmn, Scope scope, List<Role> roles, List<Argument<? extends Kind>> args)
	{
		this.fmn = fmn;
		this.scope = scope;
		this.roles = new LinkedList<>(roles);
		this.args = new LinkedList<>(args);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1093;
		hash = 31 * hash + this.fmn.hashCode();
		hash = 31 * hash + this.scope.hashCode();
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
		if (!(o instanceof SubprotocolSignature))
		{
			return false;
		}
		SubprotocolSignature subsig = (SubprotocolSignature) o;
		return this.fmn.equals(subsig.fmn) && this.scope.equals(subsig.scope) && this.roles.equals(subsig.roles) && this.args.equals(subsig.args);
	}
	
	@Override
	public String toString()
	{
		// FIXME: lists are being printed directly
		return this.scope + ":" + 
				this.fmn + "<" + this.args + ">(" + this.roles + ")";
	}
}

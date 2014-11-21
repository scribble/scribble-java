package org.scribble2.sesstype;

import java.util.List;

import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;

public class ScopedSubprotocolSignature extends SubprotocolSignature
{
	public Scope scope;
	//public SimpleName scope;
	public SubprotocolSignature sig;  // Message signature arguments are not scoped

	public ScopedSubprotocolSignature(Scope scope, ProtocolName fmn, List<Role> roles, List<Argument> args)
	{
		super(fmn, roles, args);
		this.sig = new SubprotocolSignature(fmn, roles, args);
		this.scope = scope;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1097;
		hash = 31 * super.hashCode();
		hash = 31 * hash + this.scope.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!super.equals(o))
		{
			return false;
		}
		ScopedSubprotocolSignature ssubsig = (ScopedSubprotocolSignature) o;
		return this.scope.equals(ssubsig.scope);
	}
	
	@Override
	public String toString()
	{
		return this.scope + ":" + this.sig;
	}
}

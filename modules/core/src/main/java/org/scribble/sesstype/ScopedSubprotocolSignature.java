package org.scribble.sesstype;

import java.util.List;

import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.sesstype.name.Scope;

// FIXME: deprecate
@Deprecated
public class ScopedSubprotocolSignature extends SubprotocolSig
{
	public Scope scope;
	//public SimpleName scope;
	public SubprotocolSig sig;  // Message signature arguments are not scoped

	public ScopedSubprotocolSignature(Scope scope, ProtocolName fmn, List<Role> roles, List<Arg<? extends Kind>> args)
	{
		super(fmn, roles, args);
		//super(fmn, null, roles, args);
		this.sig = null;//new SubprotocolSignature(fmn, roles, args);
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

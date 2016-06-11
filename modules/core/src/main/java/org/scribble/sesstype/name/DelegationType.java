package org.scribble.sesstype.name;

import java.io.IOException;
import java.io.Serializable;

import org.scribble.sesstype.kind.Local;

public class DelegationType implements PayloadType<Local>, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private GProtocolName proto;  // Cannot be final, for Serializable
	private Role role;

	public DelegationType(GProtocolName proto, Role role)
	{
		this.proto = proto;
		this.role = role;
	}

	@Override
	public boolean isDelegationType()
	{
		return true;
	}
	
	public GProtocolName getGlobalProtocol()
	{
		return this.proto;
	}

	public Role getRole()
	{
		return this.role;
	}

	@Override
	public Local getKind()
	{
		return Local.KIND;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DelegationType))
		{
			return false;
		}
		DelegationType them = (DelegationType) o;
		return them.canEqual(this) && this.proto.equals(them.proto) && this.role.equals(them.role);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof DelegationType;
	}

	@Override
	public int hashCode()
	{
		int hash = 1381;
		hash = 31 * this.proto.hashCode();
		hash = 31 * this.role.hashCode();
		return hash;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeObject(this.proto);
		out.writeObject(this.role);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		this.proto = (GProtocolName) in.readObject();
		this.role = (Role) in.readObject();
	}
}

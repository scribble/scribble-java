package org.scribble.ext.go.core.type.name;

import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.type.name.GDelegationType;
import org.scribble.type.name.GProtocolName;

public class RPCoreGDelegationType extends GDelegationType
{
	private static final long serialVersionUID = 1L;
	
	public RPCoreGDelegationType(GProtocolName proto, RPRoleVariant v)
	{
		super(proto, v);
	}

	public RPRoleVariant getVariant()
	{
		return (RPRoleVariant) this.role;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPCoreGDelegationType))
		{
			return false;
		}
		RPCoreGDelegationType them = (RPCoreGDelegationType) o;
		return them.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof RPCoreGDelegationType;
	}

	@Override
	public int hashCode()
	{
		int hash = 6673;
		hash = 31 * super.hashCode();
		return hash;
	}

	/*private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeObject(this.proto);
		out.writeObject(this.role);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		this.proto = (GProtocolName) in.readObject();
		this.role = (Role) in.readObject();
	}*/
}

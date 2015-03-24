package org.scribble2.model.local;

import org.scribble2.model.Constants;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.ParameterDeclList;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.RoleDecl;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.sesstype.name.Role;

public class LocalProtocolHeader extends ProtocolHeader implements LocalNode
{
	public LocalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls)
	{
		super(name, roledecls, paramdecls);
	}

	public Role getSelfRole()
	{
		for (RoleDecl rd : this.roledecls.decls)
		{
			if (rd.isSelfRoleDecl())
			{
				return rd.name.toName();
			}
		}
		throw new RuntimeException("Shouldn't get here: " + this.roledecls);
	}
	
	@Override
	public String toString()
	{
		return Constants.GLOBAL_KW + " " + super.toString();
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LocalProtocolHeader(this.name, this.roledecls, this.paramdecls);
	}

	@Override
	protected ProtocolHeader reconstruct(SimpleProtocolNameNode name, RoleDeclList rdl, ParameterDeclList pdl)
	{
		ModelDelegate del = del();
		LocalProtocolHeader gph = new LocalProtocolHeader(name, rdl, pdl);
		gph = (LocalProtocolHeader) gph.del(del);
		return gph;
	}
}

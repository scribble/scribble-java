package org.scribble2.model.local;

import org.scribble2.model.Constants;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.NameDeclNode;
import org.scribble2.model.ParamDeclList;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.RoleDecl;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Role;

public class LProtocolHeader extends ProtocolHeader implements LocalNode
{
	public LProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	{
		super(name, roledecls, paramdecls);
	}

	public Role getSelfRole()
	{
		//for (RoleDecl rd : this.roledecls.decls)
		for (NameDeclNode<Role, RoleKind> rd : this.roledecls.decls)
		{
			RoleDecl tmp = (RoleDecl) rd;
			if (tmp.isSelfRoleDecl())
			{
				return tmp.name.toName();
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
		return new LProtocolHeader(this.name, this.roledecls, this.paramdecls);
	}

	@Override
	protected ProtocolHeader reconstruct(SimpleProtocolNameNode name, RoleDeclList rdl, ParamDeclList pdl)
	{
		ModelDel del = del();
		LProtocolHeader gph = new LProtocolHeader(name, rdl, pdl);
		gph = (LProtocolHeader) gph.del(del);
		return gph;
	}
}

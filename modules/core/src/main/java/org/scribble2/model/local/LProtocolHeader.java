package org.scribble2.model.local;

import org.scribble2.model.Constants;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.NameDeclNode;
import org.scribble2.model.NonRoleParamDeclList;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.RoleDecl;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.qualified.LProtocolNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.Role;

public class LProtocolHeader extends ProtocolHeader<Local> implements LocalNode
{
	//public LProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	public LProtocolHeader(LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		super(name, roledecls, paramdecls);
	}

	public Role getSelfRole()
	{
		//for (RoleDecl rd : this.roledecls.decls)
		//for (NameDeclNode<Role, RoleKind> rd : this.roledecls.decls)
		for (NameDeclNode<RoleKind> rd : this.roledecls.decls)
		{
			RoleDecl tmp = (RoleDecl) rd;
			if (tmp.isSelfRoleDecl())
			{
				return tmp.getDeclName();
			}
		}
		throw new RuntimeException("Shouldn't get here: " + this.roledecls);
	}
	
	@Override
	public LProtocolName getDeclName()
	{
		return (LProtocolName) super.getDeclName();
	}
	
	@Override
	public String toString()
	{
		return Constants.GLOBAL_KW + " " + super.toString();
	}

	@Override
	protected ModelNodeBase copy()
	{
		//return new LProtocolHeader((SimpleProtocolNameNode) this.name, this.roledecls, this.paramdecls);
		return new LProtocolHeader((LProtocolNameNode) this.name, this.roledecls, this.paramdecls);
	}

	@Override
	//protected ProtocolHeader reconstruct(SimpleProtocolNameNode name, RoleDeclList rdl, ParamDeclList pdl)
	protected LProtocolHeader reconstruct(ProtocolNameNode<Local> name, RoleDeclList rdl, NonRoleParamDeclList pdl)
	{
		ModelDel del = del();
		LProtocolHeader gph = new LProtocolHeader((LProtocolNameNode) name, rdl, pdl);
		gph = (LProtocolHeader) gph.del(del);
		return gph;
	}
}

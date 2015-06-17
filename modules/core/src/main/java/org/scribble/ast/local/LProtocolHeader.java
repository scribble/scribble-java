package org.scribble.ast.local;

import org.scribble.ast.Constants;
import org.scribble.ast.ModelNodeBase;
import org.scribble.ast.NameDeclNode;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.del.ModelDel;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;

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

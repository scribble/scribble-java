package org.scribble.ast.global;

import org.scribble.ast.Constants;
import org.scribble.ast.ModelNodeBase;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.del.ModelDel;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;

public class GProtocolHeader extends ProtocolHeader<Global> implements GlobalNode
{
	//public GProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	public GProtocolHeader(GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		super(name, roledecls, paramdecls);
	}
	
	@Override
	public GProtocolName getDeclName()
	{
		return (GProtocolName) super.getDeclName();
	}
	
	@Override
	public String toString()
	{
		return Constants.GLOBAL_KW + " " + super.toString();
	}

	@Override
	protected ModelNodeBase copy()
	{
		//return new GProtocolHeader((SimpleProtocolNameNode) this.name, this.roledecls, this.paramdecls);
		return new GProtocolHeader((GProtocolNameNode) this.name, this.roledecls, this.paramdecls);
	}

	@Override
	//protected ProtocolHeader reconstruct(SimpleProtocolNameNode name, RoleDeclList rdl, ParamDeclList pdl)
	protected GProtocolHeader reconstruct(ProtocolNameNode<Global> name, RoleDeclList rdl, NonRoleParamDeclList pdl)
	{
		ModelDel del = del();
		GProtocolHeader gph = new GProtocolHeader((GProtocolNameNode) name, rdl, pdl);
		gph = (GProtocolHeader) gph.del(del);
		return gph;
	}
}

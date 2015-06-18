package org.scribble.ast.global;

import org.scribble.ast.Constants;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;

public class GProtocolHeader extends ProtocolHeader<Global> implements GNode
{
	public GProtocolHeader(GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		super(name, roledecls, paramdecls);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new GProtocolHeader((GProtocolNameNode) this.name, this.roledecls, this.paramdecls);
	}

	@Override
	protected GProtocolHeader reconstruct(ProtocolNameNode<Global> name, RoleDeclList rdl, NonRoleParamDeclList pdl)
	{
		ScribDel del = del();
		GProtocolHeader gph = new GProtocolHeader((GProtocolNameNode) name, rdl, pdl);
		gph = (GProtocolHeader) gph.del(del);
		return gph;
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
}

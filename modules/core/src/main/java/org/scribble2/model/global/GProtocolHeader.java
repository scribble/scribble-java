package org.scribble2.model.global;

import org.scribble2.model.Constants;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.ParamDeclList;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.qualified.SimpleProtocolNameNode;

public class GProtocolHeader extends ProtocolHeader implements GlobalNode
{
	public GProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParamDeclList paramdecls)
	{
		super(name, roledecls, paramdecls);
	}
	
	@Override
	public String toString()
	{
		return Constants.GLOBAL_KW + " " + super.toString();
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new GProtocolHeader(this.name, this.roledecls, this.paramdecls);
	}

	@Override
	protected ProtocolHeader reconstruct(SimpleProtocolNameNode name, RoleDeclList rdl, ParamDeclList pdl)
	{
		ModelDel del = del();
		GProtocolHeader gph = new GProtocolHeader(name, rdl, pdl);
		gph = (GProtocolHeader) gph.del(del);
		return gph;
	}
}

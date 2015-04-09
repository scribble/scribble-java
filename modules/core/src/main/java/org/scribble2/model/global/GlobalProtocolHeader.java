package org.scribble2.model.global;

import org.scribble2.model.Constants;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.ParameterDeclList;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;

public class GlobalProtocolHeader extends ProtocolHeader implements GlobalNode
{
	public GlobalProtocolHeader(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls)
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
		return new GlobalProtocolHeader(this.name, this.roledecls, this.paramdecls);
	}

	@Override
	protected ProtocolHeader reconstruct(SimpleProtocolNameNode name, RoleDeclList rdl, ParameterDeclList pdl)
	{
		ModelDelegate del = del();
		GlobalProtocolHeader gph = new GlobalProtocolHeader(name, rdl, pdl);
		gph = (GlobalProtocolHeader) gph.del(del);
		return gph;
	}
}
package org.scribble2.model.global;

import org.scribble2.model.Constants;
import org.scribble2.model.ParameterDeclList;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.RoleDeclList;
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
}

package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.ProtocolName;



public class ProtocolNameNode extends MemberNameNode
{
	//public ProtocolNameNodes(PrimitiveNameNode... ns)
	public ProtocolNameNode(String... ns)
	{
		super(ns);
	}

	@Override
	protected ProtocolNameNode copy()
	{
		return new ProtocolNameNode(this.elems);
	}
	
	@Override
	public ProtocolName toName()
	{
		String membname = getLastElement();
		if (!isPrefixed())
		{
			return new ProtocolName(membname);
		}
		ModuleName modname = getModulePrefix().toName();
		return new ProtocolName(modname, membname);
	}
}

package org.scribble2.model.name.qualified;


public class ProtocolNameNode extends MemberNameNode
{
	//public ProtocolNameNodes(PrimitiveNameNode... ns)
	public ProtocolNameNode(String... ns)
	{
		super(ns);
	}
	
	/*@Override
	public ProtocolName toName()
	{
		String membname = getLastElement();
		if (!isPrefixed())
		{
			return new ProtocolName(membname);
		}
		ModuleName modname = getModulePrefix().toName();
		return new ProtocolName(modname, membname);
	}*/
}

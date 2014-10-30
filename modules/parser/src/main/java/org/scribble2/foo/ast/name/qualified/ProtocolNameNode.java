package org.scribble2.foo.ast.name.qualified;

import org.antlr.runtime.Token;

public class ProtocolNameNode extends MemberNameNode
{
	//public ProtocolNameNodes(PrimitiveNameNode... ns)
	public ProtocolNameNode(Token t, String... ns)
	{
		super(t, ns);
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

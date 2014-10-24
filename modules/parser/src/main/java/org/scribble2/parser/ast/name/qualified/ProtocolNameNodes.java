package org.scribble2.parser.ast.name.qualified;

import org.antlr.runtime.tree.CommonTree;

import scribble2.sesstype.name.ModuleName;
import scribble2.sesstype.name.ProtocolName;

public class ProtocolNameNodes extends MemberNameNodes
{
	//public ProtocolNameNodes(PrimitiveNameNode... ns)
	public ProtocolNameNodes(CommonTree ct, String... ns)
	{
		super(ct, ns);
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

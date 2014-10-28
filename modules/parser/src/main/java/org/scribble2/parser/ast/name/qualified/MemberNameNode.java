package org.scribble2.parser.ast.name.qualified;

import org.antlr.runtime.Token;

public abstract class MemberNameNode extends QualifiedNameNode
{
	//public MemberNameNodes(PrimitiveNameNode... ns)
	public MemberNameNode(Token t, String... ns)
	{
		super(t, ns);
	}
	
	/*protected ModuleNameNode getModulePrefix()
	{
		return new ModuleNameNode(null, getPrefixElements());
	}*/
}

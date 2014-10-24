package org.scribble2.parser.ast.name.qualified;

import org.antlr.runtime.tree.CommonTree;

public abstract class MemberNameNodes extends QualifiedNameNode
{
	//public MemberNameNodes(PrimitiveNameNode... ns)
	public MemberNameNodes(CommonTree ct, String... ns)
	{
		super(ct, ns);
	}
	
	protected ModuleNameNode getModulePrefix()
	{
		return new ModuleNameNode(null, getPrefixElements());
	}
}

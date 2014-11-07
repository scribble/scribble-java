package org.scribble2.model.name.qualified;


public abstract class MemberNameNode extends QualifiedNameNode
{
	//public MemberNameNodes(PrimitiveNameNode... ns)
	public MemberNameNode(String... ns)
	{
		super(ns);
	}
	
	protected ModuleNameNode getModulePrefix()
	{
		return new ModuleNameNode(getPrefixElements());
	}
}

package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Name;


public abstract class MemberNameNode<T extends Name<K>, K extends Kind> extends QualifiedNameNode<T, K>
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

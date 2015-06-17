package org.scribble.ast.name.qualified;

import java.util.Arrays;

import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.PackageName;


//public abstract class MemberNameNode<T extends Name<K>, K extends Kind> extends QualifiedNameNode<T, K>
//public abstract class MemberNameNode<T extends MemberName<K>, K extends Kind> extends QualifiedNameNode<T, K>
public abstract class MemberNameNode<K extends Kind> extends QualifiedNameNode<K>
{
	//public MemberNameNodes(PrimitiveNameNode... ns)
	public MemberNameNode(String... ns)
	{
		super(ns);
	}
	
	//protected ModuleNameNode getModulePrefix()
	protected ModuleName getModuleNamePrefix()
	{
		//return new ModuleNameNode(getPrefixElements());
		//return new ModuleNameNode(getPrefixElements()).toName();
		String[] prefix = getPrefixElements();
		ModuleName mn = new ModuleName(prefix[prefix.length - 1]);
		if (prefix.length == 1)
		{
			return mn;
		}
		return new ModuleName(new PackageName(Arrays.copyOf(prefix, prefix.length - 1)), mn);
	}
}

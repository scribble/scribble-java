package org.scribble.ast.name.qualified;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.PackageName;

public class ModuleNameNode extends QualifiedNameNode<ModuleKind>
{
	public ModuleNameNode(CommonTree source, String... ns)
	{
		super(source, ns);
	}

	@Override
	protected ModuleNameNode copy()
	{
		return new ModuleNameNode(this.source, this.elems);
	}
	
	@Override
	public ModuleNameNode clone()
	{
		return (ModuleNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(this.source, ModuleKind.KIND, this.elems);
	}
	
	@Override
	public ModuleName toName()
	{
		ModuleName modname = new ModuleName(getLastElement());
		return isPrefixed()
				? new ModuleName(new PackageName(getPrefixElements()), modname)
				: modname;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ModuleNameNode))
		{
			return false;
		}
		return ((ModuleNameNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ModuleNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 409;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}

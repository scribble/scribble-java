package org.scribble2.model;

import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.ModuleKind;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.util.ScribbleException;

//public class ModuleDecl extends NameDeclNode<ModuleNameNode, ModuleName>
public class ModuleDecl extends NameDeclNode<ModuleName, ModuleKind>
{
	public final ModuleNameNode fullmodname;

	public ModuleDecl(ModuleNameNode fullmodname)
	{
		this.fullmodname = fullmodname;
	}

	@Override
	public ModuleDecl visitChildren(ModelVisitor nv) throws ScribbleException
	{
		ModuleNameNode fullmodname = (ModuleNameNode) visitChild(this.fullmodname, nv);
		return ModelFactoryImpl.FACTORY.ModuleDecl(fullmodname);
	}

	@Override
	public String toString()
	{
		return Constants.MODULE_KW + " " + this.fullmodname + ";";
	}

	@Override
	protected ModuleDecl copy()
	{
		return new ModuleDecl(fullmodname);
	}

	@Override
	public ModuleName toName()
	{
		return this.fullmodname.toName();
	}
}

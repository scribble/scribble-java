package org.scribble.ast;

import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.visit.ModelVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.name.ModuleName;

//public class ModuleDecl extends NameDeclNode<ModuleNameNode, ModuleName>
//public class ModuleDecl extends NameDeclNode<ModuleName, ModuleKind>
public class ModuleDecl extends NameDeclNode<ModuleKind>
{
	//public final ModuleNameNode fullmodname;

	public ModuleDecl(ModuleNameNode fullmodname)
	{
		super(fullmodname);
		//this.fullmodname = fullmodname;
	}

	@Override
	public ModuleDecl visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//ModuleNameNode fullmodname = (ModuleNameNode) visitChild(this.fullmodname, nv);
		ModuleNameNode fullmodname = (ModuleNameNode) visitChild(this.name, nv);
		return AstFactoryImpl.FACTORY.ModuleDecl(fullmodname);
	}

	@Override
	public String toString()
	{
		//return Constants.MODULE_KW + " " + this.fullmodname + ";";
		return Constants.MODULE_KW + " " + this.name + ";";
	}

	@Override
	protected ModuleDecl copy()
	{
		//return new ModuleDecl(fullmodname);
		return new ModuleDecl((ModuleNameNode) this.name);
	}

	@Override
	public ModuleName getDeclName()
	{
		//return this.fullmodname.toName();
		return (ModuleName) this.name.toName();
	}

	public ModuleName getFullModuleName()
	{
		return getDeclName();
	}
}

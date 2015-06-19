package org.scribble.ast;

import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ModuleKind;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.visit.AstVisitor;

public class ModuleDecl extends NameDeclNode<ModuleKind>
{
	public ModuleDecl(ModuleNameNode fullmodname)
	{
		super(fullmodname);
	}

	@Override
	protected ModuleDecl copy()
	{
		return new ModuleDecl((ModuleNameNode) this.name);
	}

	@Override
	public ModuleDecl visitChildren(AstVisitor nv) throws ScribbleException
	{
		ModuleNameNode fullmodname = (ModuleNameNode) visitChild(this.name, nv);
		return AstFactoryImpl.FACTORY.ModuleDecl(fullmodname);
	}

	@Override
	public String toString()
	{
		return Constants.MODULE_KW + " " + this.name + ";";
	}

	@Override
	public ModuleName getDeclName()
	{
		return getFullModuleName();  // FIXME: full or simple? -- currently not consistent with NonProtocolDecl and ProtocolDecl
	}

	public ModuleName getFullModuleName()
	{
		return (ModuleName) this.name.toName();
	}
}

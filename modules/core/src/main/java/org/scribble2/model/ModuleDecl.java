package org.scribble2.model;

import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.sesstype.name.ModuleName;

public class ModuleDecl extends ModelNodeBase implements NameDecl
{
	public final ModuleNameNode fullmodname;

	public ModuleDecl(ModuleNameNode fullmodname)
	{
		this.fullmodname = fullmodname;
	}

	/*@Override
	public ModuleDecl visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ModuleNameNodes fullmodname = (ModuleNameNodes) visitChild(this.fullmodname, nv);
		return new ModuleDecl(this.ct, fullmodname);
	}*/

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

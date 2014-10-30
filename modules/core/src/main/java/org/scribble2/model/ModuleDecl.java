package org.scribble2.model;

import org.scribble2.model.name.qualified.ModuleNameNode;

public class ModuleDecl extends ModelNodeBase
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
	}

	@Override
	public String toString()
	{
		return AntlrConstants.MODULE_KW + " " + this.fullmodname + ";";
	}*/
}

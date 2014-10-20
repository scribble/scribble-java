package org.scribble2.parser.ast;

import org.antlr.runtime.Token;
import org.scribble2.parser.ast.name.qualified.ModuleNameNode;

public class ModuleDecl extends ScribbleASTBase
{
	public final ModuleNameNode fullmodname;

	public ModuleDecl(Token t, ModuleNameNode fullmodname)
	{
		super(t);
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

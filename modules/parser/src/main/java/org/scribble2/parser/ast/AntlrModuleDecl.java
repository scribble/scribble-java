package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModuleDecl;
import org.scribble2.parser.AntlrModuleParser;

public class AntlrModuleDecl
{
	public static final int MODULENAME_CHILD_INDEX = 0;

	public static ModuleDecl parseModuleDecl(AntlrModuleParser parser, CommonTree ct)
	{
		return new ModuleDecl(AntlrQualifiedName.toModuleNameNodes(getModuleNameChild(ct)));
	}

	public static CommonTree getModuleNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULENAME_CHILD_INDEX);
	}
}

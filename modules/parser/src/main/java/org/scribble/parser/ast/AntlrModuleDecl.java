package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ModuleDecl;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrQualifiedName;

public class AntlrModuleDecl
{
	public static final int MODULENAME_CHILD_INDEX = 0;

	public static ModuleDecl parseModuleDecl(ScribParser parser, CommonTree ct)
	{
		return AstFactoryImpl.FACTORY.ModuleDecl(AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct)));
	}

	public static CommonTree getModuleNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULENAME_CHILD_INDEX);
	}
}

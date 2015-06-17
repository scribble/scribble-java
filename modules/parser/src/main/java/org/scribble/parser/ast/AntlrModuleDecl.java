package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ModelFactoryImpl;
import org.scribble.ast.ModuleDecl;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.ast.name.AntlrQualifiedName;

public class AntlrModuleDecl
{
	public static final int MODULENAME_CHILD_INDEX = 0;

	public static ModuleDecl parseModuleDecl(ScribbleParser parser, CommonTree ct)
	{
		//return new ModuleDecl(AntlrQualifiedName.toModuleNameNodes(getModuleNameChild(ct)));
		return ModelFactoryImpl.FACTORY.ModuleDecl(AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct)));
	}

	public static CommonTree getModuleNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULENAME_CHILD_INDEX);
	}
}

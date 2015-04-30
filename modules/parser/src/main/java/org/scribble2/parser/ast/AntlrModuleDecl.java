package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModuleDecl;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrQualifiedName;

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

package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ImportModule;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrQualifiedName;
import org.scribble.parser.ast.name.AntlrSimpleName;

public class AntlrImportModule
{
	public static final int MODULENAME_CHILD_INDEX = 0;
	public static final int ALIAS_CHILD_INDEX = 1; 

	private static final String EMPTY_ALIAS = "EMPTY_ALIAS";

	public static ImportModule parseImportModule(ScribParser parser, CommonTree ct)
	{
		ModuleNameNode fmn = AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct));
		ModuleNameNode alias = (hasAlias(ct))
				? AntlrSimpleName.toModuleNameNode(getAliasChild(ct))
				: null;
		return AstFactoryImpl.FACTORY.ImportModule(ct, fmn, alias);
	}

	public static CommonTree getModuleNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULENAME_CHILD_INDEX);
	}

	public static CommonTree getAliasChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ALIAS_CHILD_INDEX);
	}
	
	public static boolean hasAlias(CommonTree ct)
	{
		return !ct.getChild(ALIAS_CHILD_INDEX).getText().equals(EMPTY_ALIAS);
	}
}

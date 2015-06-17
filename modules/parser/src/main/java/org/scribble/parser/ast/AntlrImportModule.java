package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ImportModule;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.ast.name.AntlrQualifiedName;

public class AntlrImportModule
{
	public static final int MODULENAME_CHILD_INDEX = 0;
	public static final int ALIAS_CHILD_INDEX = 1; 

	private static final String EMPTY_ALIAS = "EMPTY_ALIAS";

	public static ImportModule parseImportModule(ScribbleParser parser, CommonTree ct)
	{
		ModuleNameNode fmn = AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct));
		//SimpleProtocolNameNode alias = null;
		ModuleNameNode alias = null;
		if (hasAlias(ct))
		{
			//alias = AntlrSimpleName.toSimpleProtocolNameNode(getAliasChild(ct));
			alias = AntlrQualifiedName.toModuleNameNode(getAliasChild(ct));
		}
		//return new ImportModule(fmn, alias);
		return AstFactoryImpl.FACTORY.ImportModule(fmn, alias);
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
		//return ct.getChildCount() > 1;
		return !ct.getChild(ALIAS_CHILD_INDEX).getText().equals(EMPTY_ALIAS);
	}
}

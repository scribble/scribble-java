package scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.parser.ast.name.AntlrQualifiedName;
import org.scribble2.parser.ast.name.AntlrSimpleName;

import scribble2.ast.ImportModule;
import scribble2.ast.name.ModuleNameNodes;
import scribble2.ast.name.SimpleProtocolNameNode;
import scribble2.parser.AntlrTreeParser;

public class AntlrImportModule
{
	public static final int MODULENAME_CHILD_INDEX = 0;
	public static final int ALIAS_CHILD_INDEX = 1; 

	public static ImportModule parseImportModule(AntlrModuleParser parser, CommonTree ct)
	{
		ModuleNameNodes fmn = AntlrQualifiedName.toModuleNameNode(getModuleNameChild(ct));
		SimpleProtocolNameNode alias = null;
		if (hasAlias(ct))
		{
			alias = AntlrSimpleName.toSimpleProtocolNameNode(getAliasChild(ct));
		}
		return new ImportModule(ct, fmn, alias);
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
		return ct.getChildCount() > 1;
	}
}

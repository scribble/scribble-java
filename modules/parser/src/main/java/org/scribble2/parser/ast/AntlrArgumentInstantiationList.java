package scribble2.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.ArgumentInstantiation;
import scribble2.ast.ArgumentInstantiationList;
import scribble2.parser.AntlrTreeParser;
import scribble2.util.Util;

public class AntlrArgumentInstantiationList
{
	//public static final String EMPTY_ARGUMENTLIST = "EMPTY_ARGUMENT_LIST";

	// Similar to parseParameterDeclList
	public static ArgumentInstantiationList parseArgumentInstantiationList(AntlrModuleParser parser, CommonTree ct)
	{
		List<ArgumentInstantiation> as = new LinkedList<>();
		for (CommonTree a : getArgumentChildren(ct))
		{
			as.add((ArgumentInstantiation) parser.parse(a));
		}
		return new ArgumentInstantiationList(ct, as);
	}

	public static List<CommonTree> getArgumentChildren(CommonTree ct)
	{
		if (ct.getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return Util.toCommonTreeList(ct.getChildren());
	}
}

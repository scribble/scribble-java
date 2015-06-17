package org.scribble.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ArgNode;
import org.scribble.ast.ModelFactoryImpl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.AntlrConstants.AntlrNodeType;
import org.scribble.parser.ast.name.AntlrAmbigName;
import org.scribble.parser.util.Util;

public class AntlrNonRoleArgList
{
	//public static final String EMPTY_ARGUMENTLIST = "EMPTY_ARGUMENT_LIST";

	// Similar to parseParameterDeclList
	public static NonRoleArgList parseNonRoleArgList(ScribbleParser parser, CommonTree ct)
	{
		List<NonRoleArg> as = new LinkedList<>();
		for (CommonTree a : getArgumentChildren(ct))
		{
			AntlrNodeType type = Util.getAntlrNodeType(a);
			if (type == AntlrNodeType.MESSAGESIGNATURE)
			{
				//as.add((ArgumentInstantiation) parser.parse(a));
				ArgNode arg = (ArgNode) parser.parse(a);
				as.add(ModelFactoryImpl.FACTORY.NonRoleArg(arg));
			}
			else if (type == AntlrNodeType.AMBIGUOUSNAME)
			{
				as.add(ModelFactoryImpl.FACTORY.NonRoleArg(AntlrAmbigName.toAmbigNameNode(a)));
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + type);
			}
		}
		//return new ArgumentInstantiationList(as);
		return ModelFactoryImpl.FACTORY.NonRoleArgList(as);
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

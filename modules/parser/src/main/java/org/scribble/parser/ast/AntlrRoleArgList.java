package org.scribble.parser.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.parser.ScribParser;
import org.scribble.parser.util.ScribParserUtil;
import org.scribble.util.ScribParserException;

public class AntlrRoleArgList
{
	public static RoleArgList parseRoleArgList(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		//List<RoleArg> ris = getRoleInstantiationChildren(ct).stream().map((ri) -> (RoleArg) parser.parse(ri)).collect(Collectors.toList());
		List<RoleArg> ris = new LinkedList<>();
		for (CommonTree ri : getRoleInstantiationChildren(ct))
		{
			ris.add((RoleArg) parser.parse(ri));
		}
		return AstFactoryImpl.FACTORY.RoleArgList(ct, ris);
	}

	public static final List<CommonTree> getRoleInstantiationChildren(CommonTree ct)
	{
		return ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}

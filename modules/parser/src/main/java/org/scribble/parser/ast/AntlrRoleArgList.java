package org.scribble.parser.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.parser.ScribParser;
import org.scribble.parser.util.ScribParserUtil;

public class AntlrRoleArgList
{
	public static RoleArgList parseRoleArgList(ScribParser parser, CommonTree ct)
	{
		List<RoleArg> ris = getRoleInstantiationChildren(ct).stream().map((ri) -> (RoleArg) parser.parse(ri)).collect(Collectors.toList());
		return AstFactoryImpl.FACTORY.RoleArgList(ris);
	}

	public static final List<CommonTree> getRoleInstantiationChildren(CommonTree ct)
	{
		return ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}

package org.scribble.parser.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.parser.ScribParser;
import org.scribble.parser.util.ScribParserUtil;

public class AntlrRoleDeclList
{
	public static RoleDeclList parseRoleDeclList(ScribParser parser, CommonTree ct)
	{
		List<RoleDecl> rds = getRoleDeclChildren(ct).stream().map((pd) -> (RoleDecl) parser.parse(pd)).collect(Collectors.toList());
		return AstFactoryImpl.FACTORY.RoleDeclList(rds);
	}

	public static List<CommonTree> getRoleDeclChildren(CommonTree ct)
	{
		return ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}

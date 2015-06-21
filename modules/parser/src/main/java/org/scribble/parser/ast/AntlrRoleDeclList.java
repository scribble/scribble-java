package org.scribble.parser.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.util.Util;

public class AntlrRoleDeclList
{
	public static RoleDeclList parseRoleDeclList(ScribbleParser parser, CommonTree ct)
	{
		List<RoleDecl> rds = getRoleDeclChildren(ct).stream().map((pd) -> (RoleDecl) parser.parse(pd)).collect(Collectors.toList());
		return AstFactoryImpl.FACTORY.RoleDeclList(rds);
	}

	public static List<CommonTree> getRoleDeclChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren());
	}
}

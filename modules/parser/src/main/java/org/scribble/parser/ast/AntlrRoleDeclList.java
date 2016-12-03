package org.scribble.parser.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.parser.ScribParser;
import org.scribble.parser.util.ScribParserUtil;
import org.scribble.util.ScribParserException;

public class AntlrRoleDeclList
{
	public static RoleDeclList parseRoleDeclList(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		//List<RoleDecl> rds = getRoleDeclChildren(ct).stream().map((pd) -> (RoleDecl) parser.parse(pd)).collect(Collectors.toList());
		List<RoleDecl> rds = new LinkedList<>();
		for (CommonTree pd : getRoleDeclChildren(ct))
		{
			rds.add((RoleDecl) parser.parse(pd));
		}
		return AstFactoryImpl.FACTORY.RoleDeclList(ct, rds);
	}

	public static List<CommonTree> getRoleDeclChildren(CommonTree ct)
	{
		return ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}

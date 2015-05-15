package org.scribble2.parser.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.RoleDecl;
import org.scribble2.model.RoleDeclList;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Util;

public class AntlrRoleDeclList
{
	public static RoleDeclList parseRoleDeclList(ScribbleParser parser, CommonTree ct)
	{
		//List<RoleDecl> rds = new LinkedList<>();
		//List<HeaderParamDecl<Role, RoleKind>> rds = new LinkedList<>();
		//List<HeaderParamDecl<RoleKind>> rds = new LinkedList<>();
		List<RoleDecl> rds = new LinkedList<>();
		for (CommonTree pd : getRoleDeclChildren(ct))
		{
			rds.add((RoleDecl) parser.parse(pd));
		}
		//return new RoleDeclList(rds);
		return ModelFactoryImpl.FACTORY.RoleDeclList(rds);
	}

	public static List<CommonTree> getRoleDeclChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren());
	}
}

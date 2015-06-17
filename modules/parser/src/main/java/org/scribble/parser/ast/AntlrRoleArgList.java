package org.scribble.parser.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.util.Util;

public class AntlrRoleArgList
{
	public static RoleArgList parseRoleArgList(ScribbleParser parser, CommonTree ct)
	{
		List<RoleArg> ris = new LinkedList<>();
		for (CommonTree ri : getRoleInstantiationChildren(ct))
		{
			ris.add((RoleArg) parser.parse(ri));
		}
		//return new RoleInstantiationList(ris);
		return AstFactoryImpl.FACTORY.RoleArgList(ris);
	}

	public static final List<CommonTree> getRoleInstantiationChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren());
	}
}

package org.scribble2.parser.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.RoleArg;
import org.scribble2.model.RoleArgList;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Util;

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
		return ModelFactoryImpl.FACTORY.RoleArgList(ris);
	}

	public static final List<CommonTree> getRoleInstantiationChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren());
	}
}

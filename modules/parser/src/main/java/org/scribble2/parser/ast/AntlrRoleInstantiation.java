package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.RoleInstantiation;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.parser.AntlrModuleParser;

public class AntlrRoleInstantiation
{
	public static final int ARG_CHILD_INDEX = 0;

	public static RoleInstantiation parseRoleInstantiation(AntlrModuleParser parser, CommonTree ct)
	{
		RoleNode arg = AntlrSimpleName.toRoleNode(getArgChild(ct));
		//return new RoleInstantiation(arg);
		return arg;
	}

	public static CommonTree getArgChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARG_CHILD_INDEX);
	}
}

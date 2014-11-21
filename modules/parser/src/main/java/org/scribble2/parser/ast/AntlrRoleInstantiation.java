package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.RoleInstantiation;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrRoleInstantiation
{
	public static final int ARG_CHILD_INDEX = 0;

	public static RoleInstantiation parseRoleInstantiation(AntlrModuleParser parser, CommonTree ct)
	{
		RoleNode role = AntlrSimpleName.toRoleNode(getArgChild(ct));
		//return new RoleInstantiation(arg);
		//return arg;
		return ModelFactoryImpl.FACTORY.RoleInstantiation(role);
	}

	public static CommonTree getArgChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARG_CHILD_INDEX);
	}
}

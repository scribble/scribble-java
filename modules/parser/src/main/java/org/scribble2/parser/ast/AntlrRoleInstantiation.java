package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.RoleInstantiation;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;
import org.scribble2.sesstype.kind.RoleKind;

public class AntlrRoleInstantiation
{
	public static final int ARG_CHILD_INDEX = 0;

	public static RoleInstantiation parseRoleInstantiation(ScribbleParser parser, CommonTree ct)
	{
		//RoleNode role = AntlrSimpleName.toRoleNode(getArgChild(ct));
		SimpleKindedNameNode<RoleKind> role = AntlrSimpleName.toRoleNode(getArgChild(ct));
		//return new RoleInstantiation(arg);
		//return arg;
		//return ModelFactoryImpl.FACTORY.RoleInstantiation(role);
		return ModelFactoryImpl.FACTORY.RoleInstantiation(role);
	}

	public static CommonTree getArgChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARG_CHILD_INDEX);
	}
}

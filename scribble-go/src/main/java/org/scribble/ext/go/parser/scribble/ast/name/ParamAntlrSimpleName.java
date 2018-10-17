package org.scribble.ext.go.parser.scribble.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ext.go.ast.name.simple.RPIndexedRoleNode;
import org.scribble.ext.go.type.kind.RPIndexedRoleKind;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

@Deprecated
public class ParamAntlrSimpleName
{
	public static RPIndexedRoleNode toParamRoleParamNode(CommonTree ct, AstFactory af)
	{
		return (RPIndexedRoleNode) af.SimpleNameNode(ct, RPIndexedRoleKind.KIND, AntlrSimpleName.getName(ct));
	}
}

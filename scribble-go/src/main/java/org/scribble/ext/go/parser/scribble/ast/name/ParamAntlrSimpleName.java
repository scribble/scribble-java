package org.scribble.ext.go.parser.scribble.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ext.go.ast.name.simple.RPRoleParamNode;
import org.scribble.ext.go.type.kind.RPRoleParamKind;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

@Deprecated
public class ParamAntlrSimpleName
{
	public static RPRoleParamNode toParamRoleParamNode(CommonTree ct, AstFactory af)
	{
		return (RPRoleParamNode) af.SimpleNameNode(ct, RPRoleParamKind.KIND, AntlrSimpleName.getName(ct));
	}
}

package org.scribble.ext.go.parser.scribble.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ext.go.ast.name.simple.ParamRoleParamNode;
import org.scribble.ext.go.type.kind.ParamRoleParamKind;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

@Deprecated
public class ParamAntlrSimpleName
{
	public static ParamRoleParamNode toParamRoleParamNode(CommonTree ct, AstFactory af)
	{
		return (ParamRoleParamNode) af.SimpleNameNode(ct, ParamRoleParamKind.KIND, AntlrSimpleName.getName(ct));
	}
}

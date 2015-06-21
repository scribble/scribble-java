package org.scribble.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.util.Util;
import org.scribble.sesstype.kind.NonRoleParamKind;

public class AntlrNonRoleParamDeclList
{
	public static NonRoleParamDeclList parseNonRoleParamDeclList(ScribbleParser parser, CommonTree ct)
	{
		List<NonRoleParamDecl<NonRoleParamKind>> pds = new LinkedList<>();
		for (CommonTree pd : getParamDeclChildren(ct))
		{
			pds.add((NonRoleParamDecl<NonRoleParamKind>) parser.parse(pd));  // FIXME: ? extends NonRoleParamKind
		}
		return AstFactoryImpl.FACTORY.NonRoleParamDeclList(pds);
	}
	
	public static final List<CommonTree> getParamDeclChildren(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: Util.toCommonTreeList(ct.getChildren());
	}
}

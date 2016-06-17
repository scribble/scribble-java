package org.scribble.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.parser.ScribParser;
import org.scribble.parser.util.ScribParserUtil;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.util.ScribParserException;

public class AntlrNonRoleParamDeclList
{
	public static NonRoleParamDeclList parseNonRoleParamDeclList(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		List<NonRoleParamDecl<NonRoleParamKind>> pds = new LinkedList<>();
		for (CommonTree pd : getParamDeclChildren(ct))
		{
			NonRoleParamDecl<? extends NonRoleParamKind> parsed = (NonRoleParamDecl<?>) parser.parse(pd);
			@SuppressWarnings("unchecked")  // OK: the node is immutable -- will never "rewrite" the generic value (the kind) in it
			NonRoleParamDecl<NonRoleParamKind> tmp = (NonRoleParamDecl<NonRoleParamKind>) parsed;
			pds.add(tmp);
		}
		return AstFactoryImpl.FACTORY.NonRoleParamDeclList(pds);
	}
	
	public static final List<CommonTree> getParamDeclChildren(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}

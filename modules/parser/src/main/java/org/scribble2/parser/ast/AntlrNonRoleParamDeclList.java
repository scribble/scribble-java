package org.scribble2.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.NonRoleParamDecl;
import org.scribble2.model.NonRoleParamDeclList;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Util;
import org.scribble2.sesstype.kind.Kind;

public class AntlrNonRoleParamDeclList
{
	public static NonRoleParamDeclList parseNonRoleParamDeclList(ScribbleParser parser, CommonTree ct)
	{
		/*AntlrNodeType type = Util.getAntlrNodeType(ct);
		switch (type)
		{
			case EMPTY_PARAMETERDECLLST:
			{
				return new ParameterDeclList(ct, Collections.<ParameterDecl>emptyList());
			}
			default:
			{*/
				//List<ParamDecl> pds = new LinkedList<>();
				//List<HeaderParamDecl<Name<Kind>, Kind>> pds = new LinkedList<>();
				//List<HeaderParamDecl<Kind>> pds = new LinkedList<>();
				List<NonRoleParamDecl<Kind>> pds = new LinkedList<>();
				for (CommonTree pd : getParamDeclChildren(ct))
				{
					pds.add((NonRoleParamDecl) parser.parse(pd));
				}
				//return new ParameterDeclList(pds);
				return ModelFactoryImpl.FACTORY.NonRoleParamDeclList(pds);
			/*}
		}*/
	}
	
	public static final List<CommonTree> getParamDeclChildren(CommonTree ct)
	{
		if (ct.getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return Util.toCommonTreeList(ct.getChildren());
	}
}

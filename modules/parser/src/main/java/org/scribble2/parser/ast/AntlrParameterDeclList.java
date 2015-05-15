package org.scribble2.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ParamDecl;
import org.scribble2.model.ParamDeclList;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Util;
import org.scribble2.sesstype.kind.Kind;

public class AntlrParameterDeclList
{
	public static ParamDeclList parseParameterDeclList(ScribbleParser parser, CommonTree ct)
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
				List<ParamDecl<Kind>> pds = new LinkedList<>();
				for (CommonTree pd : getParameterDeclChildren(ct))
				{
					pds.add((ParamDecl) parser.parse(pd));
				}
				//return new ParameterDeclList(pds);
				return ModelFactoryImpl.FACTORY.ParameterDeclList(pds);
			/*}
		}*/
	}
	
	public static final List<CommonTree> getParameterDeclChildren(CommonTree ct)
	{
		if (ct.getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return Util.toCommonTreeList(ct.getChildren());
	}
}

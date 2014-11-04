package org.scribble2.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ParameterDecl;
import org.scribble2.model.ParameterDeclList;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.util.Util;

public class AntlrParameterDeclList
{
	public static ParameterDeclList parseParameterDeclList(AntlrModuleParser parser, CommonTree ct)
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
				List<ParameterDecl> pds = new LinkedList<>();
				for (CommonTree pd : getParameterDeclChildren(ct))
				{
					pds.add((ParameterDecl) parser.parse(pd));
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

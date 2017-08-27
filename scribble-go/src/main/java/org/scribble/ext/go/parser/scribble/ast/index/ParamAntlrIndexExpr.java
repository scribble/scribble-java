package org.scribble.ext.go.parser.scribble.ast.index;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ext.go.type.index.ParamBinIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexFactory;

public class ParamAntlrIndexExpr
{
	public static ParamIndexExpr parseParamIndexExpr(CommonTree ct, AstFactory af)
	{
		String type = ct.getToken().getText();  // Duplicated from ScribParserUtil.getAntlrNodeType
		switch (type)
		{
			case "PARAM_BININDEXEXPR":  // FIXME: factor out
			{
				ParamIndexExpr left = parseParamIndexExpr((CommonTree) ct.getChild(0), af);
				if (ct.getChildCount() < 2)
				{
					return left;
				}
				ParamIndexExpr right = parseParamIndexExpr((CommonTree) ct.getChild(2), af);
				ParamBinIndexExpr.Op op;
				switch (ct.getChild(1).getText())
				{
					case "+": op = ParamBinIndexExpr.Op.Add;  break;
					case "-": op = ParamBinIndexExpr.Op.Subt; break;
					default:  throw new RuntimeException("[param] Shouldn't get in here: " + ct);
				}
				return ParamIndexFactory.ParamBinIndexExpr(op, left, right);
			}
			default:
			{
				try
				{
					return ParamIndexFactory.ParamIntVal(Integer.parseInt(type));
				}
				catch (NumberFormatException e)
				{
					return ParamIndexFactory.ParamIntVar(type);
				}
			}
		}
	}
}

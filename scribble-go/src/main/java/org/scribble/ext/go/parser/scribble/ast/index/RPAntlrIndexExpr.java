package org.scribble.ext.go.parser.scribble.ast.index;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ext.go.type.index.RPBinIndexExpr;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexFactory;

public class RPAntlrIndexExpr
{
	public static RPIndexExpr parseParamIndexExpr(CommonTree ct, AstFactory af)
	{
		String type = ct.getToken().getText();  // Duplicated from ScribParserUtil.getAntlrNodeType
		switch (type)
		{
			case "PARAM_BININDEXEXPR":  // FIXME: factor out
			{
				RPIndexExpr left = parseParamIndexExpr((CommonTree) ct.getChild(0), af);
				if (ct.getChildCount() < 3)
				{
					return left;
				}
				RPIndexExpr right = parseParamIndexExpr((CommonTree) ct.getChild(2), af);
				RPBinIndexExpr.Op op;
				switch (ct.getChild(1).getText())
				{
					case "+": op = RPBinIndexExpr.Op.Add;  break;
					case "-": op = RPBinIndexExpr.Op.Subt; break;
					case "*": op = RPBinIndexExpr.Op.Mult; break;
					default:  throw new RuntimeException("[param] Shouldn't get in here: " + ct);
				}
				return RPIndexFactory.ParamBinIndexExpr(op, left, right);
			}
			case "PARAM_PAIR":  // FIXME
			{
				RPIndexExpr left = parseParamIndexExpr((CommonTree) ct.getChild(0), af);
				if (ct.getChildCount() < 2)
				{
					return left;
				}
				RPIndexExpr right = parseParamIndexExpr((CommonTree) ct.getChild(1), af);
				return RPIndexFactory.RPIndexPair(left, right);
			}
			default:
			{
				return isInteger(type)
						? RPIndexFactory.ParamIntVal(Integer.parseInt(type))
						: RPIndexFactory.ParamIndexVar(type);
			}
		}
	}

	public static RPForeachVar parseRPForeachVar(CommonTree ct, AstFactory af)
	{
		// Duplicated from above
		String type = ct.getToken().getText();  // Parsed as simplename
		return RPIndexFactory.RPForeachVar(type);
	}

	private static boolean isInteger(String s)
	{
		// return Arrays.stream(s.getBytes()).stream().allMatch(c -> // Character.digit((char) c,radix) < 0); // no Byte stream
		if (s.isEmpty()) return false;
		for (int i = 0; i < s.length(); i++)
		{
			if (Character.digit(s.charAt(i), 10) < 0) return false;
		}
		return true;
	}
}

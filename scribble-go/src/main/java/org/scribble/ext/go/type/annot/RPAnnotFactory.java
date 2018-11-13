package org.scribble.ext.go.type.annot;

// Used directly by source ast parsing -- cf. scrib-assrt, AssrtAntlrToFormulaParser
// Would correspond to a "types factory" -- cf. AST factory
public class RPAnnotFactory
{
	
	public static RPBinAnnotExpr ParamBinAnnotExpr(RPBinAnnotExpr.Op  op, RPAnnotExpr left, RPAnnotExpr right)
	{
		return new RPBinAnnotExpr(op, left, right); 
	}

	public static RPAnnotInt ParamIntVal(int i)
	{
		return new RPAnnotInt(i);
	}

	public static RPAnnotVar ParamIndexVar(String text)
	{
		/*// Check here?  Or in API gen
		char c = text.charAt(0);
		if (c < 'A' || c > 'Z')
		{
			throw new RuntimeException("[param] Annot variables must be uppercase for Go accessibility: " + text);  
					// FIXME: return proper parsing error -- refactor as param API gen errors
		}*/
		return new RPAnnotVar(text);
	}
	
	public static RPAnnotIntPair RPAnnotPair(RPAnnotExpr left, RPAnnotExpr right)
	{
		return new RPAnnotIntPair(left, right);
	}
}

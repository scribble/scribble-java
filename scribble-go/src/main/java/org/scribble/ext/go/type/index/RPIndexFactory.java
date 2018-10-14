package org.scribble.ext.go.type.index;

// Used directly by source ast parsing -- cf. scrib-assrt, AssrtAntlrToFormulaParser
// Would correspond to a "types factory" -- cf. AST factory
public class RPIndexFactory
{
	
	public static RPBinIndexExpr ParamBinIndexExpr(RPBinIndexExpr.Op  op, RPIndexExpr left, RPIndexExpr right)
	{
		return new RPBinIndexExpr(op, left, right); 
	}

	public static RPIndexInt ParamIntVal(int i)
	{
		return new RPIndexInt(i);
	}

	public static RPIndexVar ParamIntVar(String text)
	{
		/*// Check here?  Or in API gen
		char c = text.charAt(0);
		if (c < 'A' || c > 'Z')
		{
			throw new RuntimeException("[param] Index variables must be uppercase for Go accessibility: " + text);  
					// FIXME: return proper parsing error -- refactor as param API gen errors
		}*/
		return new RPIndexVar(text);
	}

	public static RPForeachVar RPForeachVar(String text)
	{
		return new RPForeachVar(text);
	}
	
	public static RPIndexSelf RPIndexSelf()
	{
		return RPIndexSelf.SELF;
	}
	
	public static RPIndexIntPair RPIndexPair(RPIndexExpr left, RPIndexExpr right)
	{
		return new RPIndexIntPair(left, right);
	}
}

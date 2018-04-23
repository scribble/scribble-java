package org.scribble.ext.go.type.index;

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
		char c = text.charAt(0);
		if (c < 'A' || c > 'Z')
		{
			throw new RuntimeException("[param] Index variables must be uppercase for Go accessibility: " + text);  // FIXME: return proper parsing error
		}
		return new RPIndexVar(text);
	}
}

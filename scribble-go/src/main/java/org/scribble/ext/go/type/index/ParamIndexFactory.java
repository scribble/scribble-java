package org.scribble.ext.go.type.index;

// Would correspond to a "types factory" -- cf. AST factory
public class ParamIndexFactory
{
	
	public static ParamBinIndexExpr ParamBinIndexExpr(ParamBinIndexExpr.Op  op, ParamIndexExpr left, ParamIndexExpr right)
	{
		return new ParamBinIndexExpr(op, left, right); 
	}

	public static ParamIndexInt ParamIntVal(int i)
	{
		return new ParamIndexInt(i);
	}

	public static ParamIndexVar ParamIntVar(String text)
	{
		return new ParamIndexVar(text);
	}
}

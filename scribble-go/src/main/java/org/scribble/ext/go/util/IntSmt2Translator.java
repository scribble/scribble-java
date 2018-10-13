package org.scribble.ext.go.util;

public class IntSmt2Translator extends Smt2Translator
{
	public IntSmt2Translator()
	{
		//super(Sort.Int);
	}
	
	@Override
	public String getSort()
	{
		return "Int";
	}

	@Override
	public String getZeroValue()
	{
		return "0";
	}

	@Override
	public String getDefaultBaseValue()
	{
		return "1";
	}

	@Override
	public String getLtOp()
	{
		return "<";
	}

	@Override
	public String getLteOp()
	{
		return "<=";
	}

	@Override
	public String getGtOp()
	{
		return ">";
	}

	@Override
	public String getGteOp()
	{
		return ">=";
	}

	@Override
	public String getPlusOp()
	{
		return "+";
	}

	@Override
	public String getSubOp()
	{
		return "-";
	}
}

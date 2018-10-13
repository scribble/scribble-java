package org.scribble.ext.go.util;

public class PairSmt2Translator extends Smt2Translator
{
	public PairSmt2Translator()
	{
		//super(Sort.Pair);
	}
	
	public String getSort()
	{
		return "Pair";
	}

	@Override
	public String getLtOp()
	{
		return "pair_lt";
	}

	@Override
	public String getLteOp()
	{
		return "pair_lte";
	}

	@Override
	public String getGtOp()
	{
		return "pair_gt";
	}

	@Override
	public String getGteOp()
	{
		return "pair_gte";
	}
}

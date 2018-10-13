package org.scribble.ext.go.util;

public class PairSmt2Translator extends Smt2Translator
{
	public PairSmt2Translator()
	{
		//super(Sort.Pair);
	}
	
	@Override
	public String getSort()
	{
		return "(Pair Int Int)";
	}

	@Override
	public String getZeroValue()
	{
		return "(mk-pair 0 0)";
	}

	@Override
	public String getDefaultBaseValue()
	{
		return "(mk-pair 1 1)";
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

	@Override
	public String getPlusOp()
	{
		return "pair_plus";
	}

	@Override
	public String getSubOp()
	{
		return "pair_sub";
	}
}

package org.scribble.cli;

import java.util.Arrays;
import java.util.List;


public class CLFlag
{
	final String flag;  // ID field
	final int numArgs;
	final boolean unique;
	final boolean barrier;
	final String err;
	final List<String> clashes;

	public CLFlag(String flag, int numArgs, boolean unique, boolean barrier,
			String err, String... clashes)
	{
		this.flag = flag;
		this.numArgs = numArgs;
		this.unique = unique;
		this.barrier = barrier;
		this.err = err;
		this.clashes = Arrays.asList(clashes);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof CLFlag))
		{
			return false;
		}
		CLFlag them = (CLFlag) o;
		return this.flag.equals(them.flag);
	}

	@Override
	public int hashCode()
	{
		int hash = 14411;
		hash = 31 * this.flag.hashCode();
		return hash;
	}
}
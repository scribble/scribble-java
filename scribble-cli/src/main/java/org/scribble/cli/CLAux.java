package org.scribble.cli;

import java.util.Arrays;
import java.util.List;

//... FIXME: integrate CLAux with CLFlags, and refactor unique as a boolean field
//..also refactor non/attemptable as a boolean field
public class CLAux
{
	final String flag;  // ID field
	final int numArgs;
	final boolean unique;
	final boolean barrier;
	final String err;
	final List<String> clashes;

	public CLAux(String flag, int numArgs, boolean unique, boolean barrier,
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
		if (!(o instanceof CLAux))
		{
			return false;
		}
		CLAux them = (CLAux) o;
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
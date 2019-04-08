package org.scribble.cli;

import java.util.Arrays;
import java.util.List;


public class CLFlag
{
	final String lab;  // ID field: CLFlags String constant -- N.B. includes "-" prefix
	final int numArgs;
	final boolean unique;
	final boolean enact;
	final boolean barrier;  // TODO: rename, barrier misleading (sounds like a sync)
	final String err;
	final List<String> clashes;

	public CLFlag(String lab, int numArgs, boolean unique, boolean enact,
			boolean barrier, String err, String... clashes)
	{
		this.lab = lab;
		this.numArgs = numArgs;
		this.unique = unique;
		this.enact = enact;
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
		return this.lab.equals(them.lab);
	}

	@Override
	public int hashCode()
	{
		int hash = 14411;
		hash = 31 * this.lab.hashCode();
		return hash;
	}
}
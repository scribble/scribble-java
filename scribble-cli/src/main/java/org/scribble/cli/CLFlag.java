package org.scribble.cli;

import java.util.Arrays;
import java.util.List;


/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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
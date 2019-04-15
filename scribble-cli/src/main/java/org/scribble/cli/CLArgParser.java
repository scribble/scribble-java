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
package org.scribble.cli;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.scribble.util.Pair;

// A Scribble extension should call the super constructor, then add any additional flags to this.info -- FIXME: do in CLFlags, not here
// String[] -> Map<CommandLine.Arg, String[]> -- Map array values are the arguments associated to each CommandLine.Arg
public class CLArgParser
{
	private final CLFlags flags;
	private final String[] raw;  // Raw args from main method

	// Flag String constants from CLFlags -> flag arguments (possibly none)
	private final List<Pair<String, String[]>> parsed = new LinkedList<>();
	
	public CLArgParser(CLFlags flags, String[] raw)
	{
		this.flags = flags;
		this.raw = raw;
	}		
	
	// Return: left = CLFlags String constant, right = flag args (if any)
	public List<Pair<String, String[]>> getParsed() throws CommandLineException
	{
		parseArgs();
		List<String> labs = getParsedKeys();
		if (!(labs.contains(CLFlags.MAIN_MOD_FLAG)
				^ labs.contains(CLFlags.INLINE_MAIN_MOD_FLAG)))
		{
			throw new CommandLineException("No/multiple main module specified\n");
		}
		if (labs.contains(CLFlags.MAIN_MOD_FLAG))
		{
			String main = getParsedUnique(CLFlags.MAIN_MOD_FLAG)[0];
			if (!validateModuleArg(main))
			{
				throw new CommandLineException("Bad module arg: " + main);
			}
		}
		if (labs.contains(CLFlags.IMPORT_PATH_FLAG))
		{
			validatePaths(getParsedUnique(CLFlags.IMPORT_PATH_FLAG)[0]);
		}
		return this.parsed;
	}
	
	private void parseArgs() throws CommandLineException
	{
		for (int i = 0; i < this.raw.length; )  // Parse args in order
		{
			String arg = this.raw[i];
			if (this.flags.explicit.containsKey(arg))
			{
				List<String> labs = getParsedKeys();
				List<String> clashes = this.flags.explicit.get(arg).clashes.stream()
						.filter(x -> labs.contains(x)).collect(Collectors.toList());
				if (!clashes.isEmpty())
				{
					throw new CommandLineException(
							"Flag clash for " + arg + ": " + clashes);
				}
				i = this.parseFlag(i);  // Returns index of next arg to parse
			}
			else
			{
				if (isMainModuleParsed())
				{
					if (arg.startsWith("-"))
					{
						throw new CommandLineException(
								"Unknown flag or bad main module arg: " + arg);
					}
					// Could actually be the second "bad argument" -- we didn't validate the value of the (supposed) "main arg"
					throw new CommandLineException(
							"Bad/multiple main module arg: " + arg);
				}
				i = parseMain(i);
			}
		}
	}
	
	// Pre: i = (the index of the current flag to parse)
	// Puts into this.parsed
	// Post: i = 1 + (the index of the last argument parsed)
	// N.B. currently allows repeat flag decls: last overwrites previous
	protected int parseFlag(int i) throws CommandLineException
	{
		String lab = this.raw[i];
		if (this.flags.explicit.get(lab).unique)
		{
			if (getParsedKeys().contains(lab))
			{
				throw new CommandLineException("duplicate: " + lab);
			}
		}
		int num = this.flags.explicit.get(lab).numArgs;
		if ((i + num) >= this.raw.length)
		{
			throw new CommandLineException(this.flags.explicit.get(lab).err);
		}
		String[] flagArgs = new String[num];
		if (num > 0)
		{
			System.arraycopy(this.raw, i+1, flagArgs, 0, num);
		}
		this.parsed.add(new Pair<>(lab, flagArgs));
		return i+1 + num;
	}

	// Puts into this.parsed
	protected int parseMain(int i) throws CommandLineException
	{
		String main = this.raw[i];
		this.parsed.add(new Pair<>(CLFlags.MAIN_MOD_FLAG, new String[]{main}));
		return i+1;
	}
	
	private boolean isMainModuleParsed()
	{
		List<String> labs = getParsedKeys();
		return labs.contains(CLFlags.MAIN_MOD_FLAG)
				|| labs.contains(CLFlags.INLINE_MAIN_MOD_FLAG);
	}

	// This check guards the subsequent file open attempt?
	private boolean validateModuleArg(String arg)
	{
		return arg.chars()
				.noneMatch(i -> !Character.isLetterOrDigit(i) && i != '.'
						&& i != File.separatorChar && i != ':' && i != '-' && i != '_'
						&& i != '/'); // Hack? (cygwin)
	}

	private boolean validatePaths(String paths)
	{
		for (String path : paths.split(File.pathSeparator))
		{
			if (!new File(path).isDirectory())
			{
				return false;
			}
		}
		return true;
	}

	private List<String> getParsedKeys()
	{
		return this.parsed.stream().map(x -> x.left).collect(Collectors.toList());
	}
	
	// Hardcoded to find any one, so applies to uniques only
	private String[] getParsedUnique(String flag)
	{
		Optional<Pair<String, String[]>> res = this.parsed.stream()
				.filter(x -> x.left.equals(flag)).findAny();
		return res.isPresent() ? res.get().right : null;
	}
}


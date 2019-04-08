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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// A Scribble extension should call the super constructor, then add any additional flags to this.info -- FIXME: do in CLFlags, not here
// String[] -> Map<CommandLine.Arg, String[]> -- Map array values are the arguments associated to each CommandLine.Arg
public class CLArgParser2
{
	public final CLFlags flags = new CLFlags();

	private final String[] raw;  // Raw args from main method

	// Flag String constants from CLFlags -> flag arguments (possibly none)
	private final Map<String, String[]> parsed = new HashMap<>();
	
	public CLArgParser2(String[] raw)
	{
		this.raw = raw;
	}		
	
	public Map<String, String[]> getParsed() throws CommandLineException
	{
		parseArgs();
		if (!(this.parsed.containsKey(CLFlags.MAIN_MOD_FLAG)
				^ this.parsed.containsKey(CLFlags.INLINE_MAIN_MOD_FLAG)))
		{
			throw new CommandLineException("No/multiple main module specified\n");
		}
		if (this.parsed.containsKey(CLFlags.MAIN_MOD_FLAG))
		{
			String main = this.parsed.get(CLFlags.MAIN_MOD_FLAG)[0];
			if (!validateModuleArg(main))
			{
				throw new CommandLineException("Bad module arg: " + main);
			}
		}
		if (this.parsed.containsKey(CLFlags.IMPORT_PATH_FLAG))
		{
			validatePaths(this.parsed.get(CLFlags.IMPORT_PATH_FLAG)[0]);
		}
		return Collections.unmodifiableMap(this.parsed);
	}
	
	private void parseArgs() throws CommandLineException
	{
		for (int i = 0; i < this.raw.length; )
		{
			String a = this.raw[i];
			if (this.flags.flags.containsKey(a))
			{
				i = this.parseFlag(i);  // Returns index of next arg to parse
			}
			else
			{
				if (isMainModuleParsed())
				{
					if (a.startsWith("-"))
					{
						throw new CommandLineException(
								"Unknown flag or bad main module arg: " + a);
					}
					// Could actually be the second "bad argument" -- we didn't validate the value of the (supposed) "main arg"
					throw new CommandLineException("Bad/multiple main module arg: " + a);
				}
				i = parseMain(i);
			}
		}
	}
	
	// Pre: i = (the index of the current flag to parse)
	// Post: i = 1 + (the index of the last argument parsed)
	// N.B. currently allows repeat flag decls: last overwrites previous
	protected int parseFlag(int i) throws CommandLineException
	{
		String flag = this.raw[i];
		if (this.flags.flags.get(flag).unique)
		{
			if (this.parsed.containsKey(flag))
			{
				throw new CommandLineException("duplicate: " + flag);
			}
		}
		int num = this.flags.flags.get(flag).numArgs;
		if ((i + num) >= this.raw.length)
		{
			throw new CommandLineException(this.flags.flags.get(flag).err);
		}
		String[] flagArgs = new String[num];
		if (num > 0)
		{
			System.arraycopy(this.raw, i+1, flagArgs, 0, num);
		}
		this.parsed.put(flag, flagArgs);
		return i+1 + num;
	}

	protected int parseMain(int i) throws CommandLineException
	{
		String main = this.raw[i];
		this.parsed.put(CLFlags.MAIN_MOD_FLAG, new String[]{main});
		return i+1;
	}
	
	private boolean isMainModuleParsed()
	{
		return this.parsed.containsKey(CLFlags.MAIN_MOD_FLAG)
				|| this.parsed.containsKey(CLFlags.INLINE_MAIN_MOD_FLAG);
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
	
	
	
	
	
	
	
	
	

	/*private int parseImportPath(int i) throws CommandLineException
	{
		if ((i + 1) >= this.raw.length)
		{
			throw new CommandLineException("Missing path argument");
		}
		String path = this.raw[++i];
		if (!validatePaths(path))
		{
			// FIXME: move validation to CL
			throw new CommandLineException("Scribble module import path '"+ path +"' is not valid\r\n");
		}
		checkAndAddUniqueFlag(CLArgParser.IMPORT_PATH_FLAG, new String[] { path });
		return i;
	}

	private void checkAndAddUniqueFlag(String flag, String[] args) throws CommandLineException
	{
		//CLArgFlag argFlag = CLArgParser.UNIQUE_FLAGS.get(flag);
		if (this.parsed.containsKey(flag))
		{
			throw new CommandLineException("Duplicate flag: " + flag);
		}
		this.parsed.put(flag, args);
	}*/
}

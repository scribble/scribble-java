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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// String[] -> Map<CommandLine.Arg, String[]> -- Map array values are the arguments associated to each CommandLine.Arg
public class CLArgParser
{
	// Unique flags
	public static final String JUNIT_FLAG = "-junit";  // For internal use (JUnit test harness)
	public static final String VERBOSE_FLAG = "-V";
	public static final String IMPORT_PATH_FLAG = "-ip";
	public static final String API_OUTPUT_DIR_FLAG = "-d";
	public static final String STATECHAN_SUBTYPES_FLAG = "-subtypes";
	public static final String OLD_WF_FLAG = "-oldwf";
	public static final String NO_LIVENESS_FLAG = "-nolive";
	public static final String LTSCONVERT_MIN_FLAG = "-minlts";
	public static final String FAIR_FLAG = "-fair";
	public static final String NO_LOCAL_CHOICE_SUBJECT_CHECK = "-nolocalchoicecheck";
	public static final String NO_ACCEPT_CORRELATION_CHECK = "-nocorrelation";
	public static final String DOT_FLAG = "-dot";
	public static final String AUT_FLAG = "-aut";
	public static final String NO_VALIDATION_FLAG = "-novalid";
	public static final String INLINE_MAIN_MOD_FLAG = "-inline";
	public static final String F17_FLAG = "-f17";
	
	// Non-unique flags
	public static final String PROJECT_FLAG = "-project";
	public static final String EFSM_FLAG = "-fsm";
	public static final String VALIDATION_EFSM_FLAG = "-vfsm";
	public static final String UNFAIR_EFSM_FLAG = "-ufsm";
	public static final String EFSM_PNG_FLAG = "-fsmpng";
	public static final String VALIDATION_EFSM_PNG_FLAG = "-vfsmpng";
	public static final String UNFAIR_EFSM_PNG_FLAG = "-ufsmpng";
	public static final String SGRAPH_FLAG = "-model";
	public static final String UNFAIR_SGRAPH_FLAG = "-umodel";
	public static final String SGRAPH_PNG_FLAG = "-modelpng";
	public static final String UNFAIR_SGRAPH_PNG_FLAG = "-umodelpng";
	public static final String API_GEN_FLAG = "-api";
	public static final String SESSION_API_GEN_FLAG = "-sessapi";
	public static final String STATECHAN_API_GEN_FLAG = "-chanapi";
	
	private static final Map<String, CLArgFlag> UNIQUE_FLAGS = new HashMap<>();
	{
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.JUNIT_FLAG, CLArgFlag.JUNIT);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.VERBOSE_FLAG, CLArgFlag.VERBOSE);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.IMPORT_PATH_FLAG, CLArgFlag.IMPORT_PATH);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.API_OUTPUT_DIR_FLAG, CLArgFlag.API_OUTPUT);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.STATECHAN_SUBTYPES_FLAG, CLArgFlag.SCHAN_API_SUBTYPES);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.OLD_WF_FLAG, CLArgFlag.OLD_WF);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.LTSCONVERT_MIN_FLAG, CLArgFlag.LTSCONVERT_MIN);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.FAIR_FLAG, CLArgFlag.FAIR);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.NO_LOCAL_CHOICE_SUBJECT_CHECK, CLArgFlag.NO_LOCAL_CHOICE_SUBJECT_CHECK);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.NO_ACCEPT_CORRELATION_CHECK, CLArgFlag.NO_ACCEPT_CORRELATION_CHECK);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.DOT_FLAG, CLArgFlag.DOT);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.AUT_FLAG, CLArgFlag.AUT);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.NO_VALIDATION_FLAG, CLArgFlag.NO_VALIDATION);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.INLINE_MAIN_MOD_FLAG, CLArgFlag.INLINE_MAIN_MOD);
		CLArgParser.UNIQUE_FLAGS.put(CLArgParser.F17_FLAG, CLArgFlag.F17);
	}

	private static final Map<String, CLArgFlag> NON_UNIQUE_FLAGS = new HashMap<>();
	{
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.PROJECT_FLAG, CLArgFlag.PROJECT);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.EFSM_FLAG, CLArgFlag.EFSM);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.VALIDATION_EFSM_FLAG, CLArgFlag.VALIDATION_EFSM);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.UNFAIR_EFSM_FLAG, CLArgFlag.UNFAIR_EFSM);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.EFSM_PNG_FLAG, CLArgFlag.EFSM_PNG);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.VALIDATION_EFSM_PNG_FLAG, CLArgFlag.VALIDATION_EFSM_PNG);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.UNFAIR_EFSM_PNG_FLAG, CLArgFlag.UNFAIR_EFSM_PNG);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.SGRAPH_FLAG, CLArgFlag.SGRAPH);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.UNFAIR_SGRAPH_FLAG, CLArgFlag.UNFAIR_SGRAPH);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.SGRAPH_PNG_FLAG, CLArgFlag.SGRAPH_PNG);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.UNFAIR_SGRAPH_PNG_FLAG, CLArgFlag.UNFAIR_SGRAPH_PNG);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.API_GEN_FLAG, CLArgFlag.API_GEN);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.SESSION_API_GEN_FLAG, CLArgFlag.SESS_API_GEN);
		CLArgParser.NON_UNIQUE_FLAGS.put(CLArgParser.STATECHAN_API_GEN_FLAG, CLArgFlag.SCHAN_API_GEN);
	}

	private static final Map<String, CLArgFlag> FLAGS = new HashMap<>();
	{
		CLArgParser.FLAGS.putAll(CLArgParser.UNIQUE_FLAGS);
		CLArgParser.FLAGS.putAll(CLArgParser.NON_UNIQUE_FLAGS);
	}

	private final String[] args;
	private final Map<CLArgFlag, String[]> parsed = new HashMap<>();
	
	public CLArgParser(String[] args) throws CommandLineException
	{
		this.args = args;
		parseArgs();
	}		
	
	public Map<CLArgFlag, String[]> getArgs()
	{
		return this.parsed;
	}
	
	private void parseArgs() throws CommandLineException
	{
		for (int i = 0; i < this.args.length; i++)
		{
			String arg = this.args[i];
			if (CLArgParser.FLAGS.containsKey(arg))
			{
				i = this.parseFlag(i);
			}
			else
			{
				if (isMainModuleParsed())
				{
					if (arg.startsWith("-"))
					{
						throw new CommandLineException("Unknown flag or bad main module arg: " + arg);
					}
					// May actually be the second bad argument -- we didn't validate the value of the main arg
					throw new CommandLineException("Bad/multiple main module arg: " + arg);
				}
				parseMain(i);
			}
		}
	}
	
	private boolean isMainModuleParsed()
	{
		return this.parsed.containsKey(CLArgFlag.MAIN_MOD) || this.parsed.containsKey(CLArgFlag.INLINE_MAIN_MOD);
	}
	
	// Pre: i is the index of the current flag to parse
	// Post: i is the index of the last argument parsed -- parseArgs does the index increment to the next current flag
	// Currently allows repeat flag decls: next overrides previous
	private int parseFlag(int i) throws CommandLineException
	{
		String flag = this.args[i];
		switch (flag)
		{
			// Unique flags
			case CLArgParser.IMPORT_PATH_FLAG:
			{
				return parseImportPath(i);
			}
			case CLArgParser.INLINE_MAIN_MOD_FLAG:
			{
				if (isMainModuleParsed())
				{
					throw new CommandLineException("Multiple main modules given.");
				}
				return parseInlineMainModule(i);
			}
			case CLArgParser.F17_FLAG:
			{
				return parseF17(i);
			}
			case CLArgParser.JUNIT_FLAG:
			case CLArgParser.VERBOSE_FLAG:
			case CLArgParser.STATECHAN_SUBTYPES_FLAG:
			case CLArgParser.OLD_WF_FLAG:
			case CLArgParser.NO_LIVENESS_FLAG:
			case CLArgParser.LTSCONVERT_MIN_FLAG:
			case CLArgParser.FAIR_FLAG:
			case CLArgParser.NO_LOCAL_CHOICE_SUBJECT_CHECK:
			case CLArgParser.NO_ACCEPT_CORRELATION_CHECK:
			case CLArgParser.NO_VALIDATION_FLAG:
			{
				checkAndAddNoArgUniqueFlag(flag, new String[0]);
				return i;
			}
			case CLArgParser.API_OUTPUT_DIR_FLAG:
			{
				return parseOutput(i);
			}
			case CLArgParser.DOT_FLAG:
			{
				if (this.parsed.containsKey(CLArgParser.UNIQUE_FLAGS.get(AUT_FLAG)))
				{
					throw new CommandLineException("Incompatible flags: " + DOT_FLAG + " and " + AUT_FLAG);
				}
				checkAndAddNoArgUniqueFlag(flag, new String[0]);
				return i;
			}
			case CLArgParser.AUT_FLAG:
			{
				if (this.parsed.containsKey(CLArgParser.UNIQUE_FLAGS.get(DOT_FLAG)))
				{
					throw new CommandLineException("Incompatible flags: " + DOT_FLAG + " and " + AUT_FLAG);
				}
				checkAndAddNoArgUniqueFlag(flag, new String[0]);
				return i;
			}

			// Non-unique flags
			case CLArgParser.PROJECT_FLAG:
			{
				return parseProject(i);
			}
			case CLArgParser.EFSM_FLAG:
			case CLArgParser.VALIDATION_EFSM_FLAG:
			case CLArgParser.UNFAIR_EFSM_FLAG:
			case CLArgParser.API_GEN_FLAG:
			case CLArgParser.STATECHAN_API_GEN_FLAG:
			{
				return parseProtoAndRoleArgs(flag, i);
			}
			case CLArgParser.EFSM_PNG_FLAG:
			case CLArgParser.VALIDATION_EFSM_PNG_FLAG:
			case CLArgParser.UNFAIR_EFSM_PNG_FLAG:
			{
				return parseProtoRoleAndFileArgs(flag, i);
			}
			case CLArgParser.SGRAPH_FLAG:
			case CLArgParser.UNFAIR_SGRAPH_FLAG:
			case CLArgParser.SESSION_API_GEN_FLAG:
			{
				return parseProtoArg(flag, i);
			}
			case CLArgParser.SGRAPH_PNG_FLAG:
			case CLArgParser.UNFAIR_SGRAPH_PNG_FLAG:
			{
				return parseProtoAndFileArgs(flag, i);
			}

			default:
			{
				throw new RuntimeException("[TODO] Unknown flag: " + flag);
			}
		}
	}

	private void checkAndAddNoArgUniqueFlag(String flag, String[] args) throws CommandLineException
	{
		CLArgFlag argFlag = CLArgParser.UNIQUE_FLAGS.get(flag);
		if (this.parsed.containsKey(argFlag))
		{
			throw new CommandLineException("Duplicate flag: " + flag);
		}
		this.parsed.put(argFlag, args);
	}

	private int parseOutput(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing directory argument");
		}
		String dir = this.args[++i];
		this.parsed.put(CLArgParser.UNIQUE_FLAGS.get(CLArgParser.API_OUTPUT_DIR_FLAG), new String[] { dir } );
		return i;
	}

	private void parseMain(int i) throws CommandLineException
	{
		String main = args[i];
		if (!CLArgParser.validateModuleArg(main))
		{
			throw new CommandLineException("Bad module arg: " + main);
		}
		this.parsed.put(CLArgFlag.MAIN_MOD, new String[] { main } );
	}

	private int parseImportPath(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing path argument");
		}
		String path = this.args[++i];
		if (!validatePaths(path))
		{
			throw new CommandLineException("Scribble module import path '"+ path +"' is not valid\r\n");
		}
		//this.parsed.put(CommandLineArgParser.FLAGS.get(CommandLineArgParser.PATH_FLAG), new String[] { path });
		checkAndAddNoArgUniqueFlag(CLArgParser.IMPORT_PATH_FLAG, new String[] { path });
		return i;
	}

	private int parseInlineMainModule(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing module definition");
		}
		String inline = this.args[++i];
		checkAndAddNoArgUniqueFlag(CLArgParser.INLINE_MAIN_MOD_FLAG, new String[] { inline });
		return i;
	}

	private int parseF17(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing simple global protocol name argument");
		}
		String proto = this.args[++i];
		checkAndAddNoArgUniqueFlag(CLArgParser.F17_FLAG, new String[] { proto });
		return i;
	}
	
	private int parseProject(int i) throws CommandLineException  // Similar to parseProtoAndRoleArgs
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		/*if (!validateProtocolName(proto))  // TODO
		{
			throw new RuntimeException("Protocol name '"+ proto +"' is not valid\r\n");
		}*/
		concatArgs(CLArgParser.NON_UNIQUE_FLAGS.get(CLArgParser.PROJECT_FLAG), proto, role);
		return i;
	}

	private int parseProtoAndRoleArgs(String f, int i) throws CommandLineException
	{
		CLArgFlag flag = CLArgParser.NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(flag, proto, role);
		return i;
	}

	private int parseProtoRoleAndFileArgs(String f, int i) throws CommandLineException
	{
		CLArgFlag flag = CLArgParser.NON_UNIQUE_FLAGS.get(f);
		if ((i + 3) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role/file arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		String png = this.args[++i];
		concatArgs(flag, proto, role, png);
		return i;
	}

	private int parseProtoArg(String f, int i) throws CommandLineException
	{
		CLArgFlag flag = CLArgParser.NON_UNIQUE_FLAGS.get(f);
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol argument");
		}
		String proto = this.args[++i];
		concatArgs(flag, proto);
		return i;
	}

	private int parseProtoAndFileArgs(String f, int i) throws CommandLineException
	{
		CLArgFlag flag = CLArgParser.NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/file arguments");
		}
		String proto = this.args[++i];
		String png = this.args[++i];
		concatArgs(flag, proto, png);
		return i;
	}
	
	private void concatArgs(CLArgFlag flag, String... toAdd)
	{
		String[] args = this.parsed.get(flag);
		if (args == null)
		{
			args = Arrays.copyOf(toAdd, toAdd.length);
		}
		else
		{
			String[] tmp = new String[args.length + toAdd.length];
			System.arraycopy(args, 0, tmp, 0, args.length);
			System.arraycopy(toAdd, 0, tmp, args.length, toAdd.length);
			args = tmp;
		}
		this.parsed.put(flag, args);
	}

	// Used to guard subsequent file open attempt?
	private static boolean validateModuleArg(String arg)
	{
		return arg.chars().noneMatch((i) ->
				!Character.isLetterOrDigit(i) && i != '.' && i != File.separatorChar && i != ':' && i != '-' && i != '_'
						&& i != '/');  // Hack? (cygwin)
	}

	private static boolean validatePaths(String paths)
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
}

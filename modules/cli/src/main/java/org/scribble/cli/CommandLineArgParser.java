package org.scribble.cli;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.scribble.cli.CommandLine.ArgFlag;

// String[] -> Map<CommandLine.Arg, String[]> -- Map array values are the arguments associated to each CommandLine.Arg
public class CommandLineArgParser
{
	// Unique flags
	public static final String JUNIT_FLAG = "-junit";  // For internal use (JUnit test harness)
	public static final String VERBOSE_FLAG = "-V";
	public static final String PATH_FLAG = "-ip";
	public static final String PROJECT_FLAG = "-project";
	/*public static final String DOT_FLAG = "-dot";
	public static final String DOT_PNG_FLAG = "-dotpng";
	public static final String CHECKED_DOT_FLAG = "-vdot";
	public static final String CHECKED_DOT_PNG_FLAG = "-vdotpng";
	public static final String AUT_FLAG = "-aut";
	public static final String CHECKED_AUT_FLAG = "-vaut";*/
	public static final String SESSION_FLAG = "-session";
	public static final String STATECHAN_FLAG = "-schan";
	public static final String API_FLAG = "-api";
	public static final String API_OUTPUT_FLAG = "-d";
	public static final String STATECHANSUBTYPES_FLAG = "-subtypes";
	/*public static final String GLOBAL_MODEL_DOT_FLAG = "-modeldot";
	public static final String GLOBAL_MODEL_DOT_PNG_FLAG = "-modeldotpng";
	public static final String GLOBAL_MODEL_AUT_FLAG = "-modelaut";*/
	//public static final String PROJECTED_MODEL_FLAG = "-pmodel";
	public static final String OLD_WF_FLAG = "-oldwf";
	public static final String NO_LIVENESS_FLAG = "-nolive";
	public static final String MIN_EFSM_FLAG = "-minlts";
	public static final String FAIR_FLAG = "-fair";
	public static final String NO_LOCAL_CHOICE_SUBJECT_CHECK = "-nolocalchoicecheck";
	public static final String NO_ACCEPT_CORRELATION_CHECK = "-nocorrelation";
	public static final String DOT_FLAG = "-dot";
	public static final String AUT_FLAG = "-aut";
	
	// Non-unique flags
	public static final String EFSM_FLAG = "-fsm";
	public static final String EFSMPNG_FLAG = "-fsmpng";
	public static final String VEFSM_FLAG = "-vfsm";
	public static final String VEFSMPNG_FLAG = "-vfsmpng";
	public static final String UEFSM_FLAG = "-ufsm";
	public static final String UEFSMPNG_FLAG = "-ufsmpng";
	public static final String GMODEL_FLAG = "-model";
	public static final String GMODELPNG_FLAG = "-modelpng";
	public static final String UGMODEL_FLAG = "-umodel";
	public static final String UGMODELPNG_FLAG = "-umodelpng";
	
	private static final Map<String, CommandLine.ArgFlag> UNIQUE_FLAGS = new HashMap<>();
	{
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.JUNIT_FLAG, CommandLine.ArgFlag.JUNIT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.VERBOSE_FLAG, CommandLine.ArgFlag.VERBOSE);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.PATH_FLAG, CommandLine.ArgFlag.PATH);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.API_OUTPUT_FLAG, CommandLine.ArgFlag.API_OUTPUT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.STATECHANSUBTYPES_FLAG, CommandLine.ArgFlag.SCHAN_API_SUBTYPES);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.OLD_WF_FLAG, CommandLine.ArgFlag.OLD_WF);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.MIN_EFSM_FLAG, CommandLine.ArgFlag.MIN_EFSM);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.FAIR_FLAG, CommandLine.ArgFlag.FAIR);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.NO_LOCAL_CHOICE_SUBJECT_CHECK, CommandLine.ArgFlag.NO_LOCAL_CHOICE_SUBJECT_CHECK);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.NO_ACCEPT_CORRELATION_CHECK, CommandLine.ArgFlag.NO_ACCEPT_CORRELATION_CHECK);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.DOT_FLAG, CommandLine.ArgFlag.DOT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.AUT_FLAG, CommandLine.ArgFlag.AUT);
	}

	private static final Map<String, CommandLine.ArgFlag> NON_UNIQUE_FLAGS = new HashMap<>();
	{
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.PROJECT_FLAG, CommandLine.ArgFlag.PROJECT);
		/*CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.DOT_FLAG, CommandLine.ArgFlag.DOT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.DOT_PNG_FLAG, CommandLine.ArgFlag.DOT_PNG);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.CHECKED_DOT_FLAG, CommandLine.ArgFlag.CHECKED_DOT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.CHECKED_DOT_PNG_FLAG, CommandLine.ArgFlag.CHECKED_DOT_PNG);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.AUT_FLAG, CommandLine.ArgFlag.AUT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.CHECKED_AUT_FLAG, CommandLine.ArgFlag.CHECKED_AUT);*/
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.SESSION_FLAG, CommandLine.ArgFlag.SESS_API);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.STATECHAN_FLAG, CommandLine.ArgFlag.SCHAN_API);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.API_FLAG, CommandLine.ArgFlag.EP_API);
		/*CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.GLOBAL_MODEL_DOT_FLAG, CommandLine.ArgFlag.GLOBAL_MODEL_DOT);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.GLOBAL_MODEL_DOT_PNG_FLAG, CommandLine.ArgFlag.GLOBAL_MODEL_DOT_PNG);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.GLOBAL_MODEL_AUT_FLAG, CommandLine.ArgFlag.GLOBAL_MODEL_AUT);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.NO_LIVENESS_FLAG, CommandLine.ArgFlag.NO_LIVENESS);*/
		//CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.PROJECTED_MODEL_FLAG, CommandLine.ArgFlag.PROJECTED_MODEL);

		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.EFSM_FLAG, CommandLine.ArgFlag.EFSM);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.EFSMPNG_FLAG, CommandLine.ArgFlag.EFSMPNG);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.VEFSM_FLAG, CommandLine.ArgFlag.VEFSM);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.VEFSMPNG_FLAG, CommandLine.ArgFlag.VEFSMPNG);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.UEFSM_FLAG, CommandLine.ArgFlag.UEFSM);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.UEFSMPNG_FLAG, CommandLine.ArgFlag.UEFSMPNG);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.GMODEL_FLAG, CommandLine.ArgFlag.GMODEL);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.GMODELPNG_FLAG, CommandLine.ArgFlag.GMODELPNG);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.UGMODEL_FLAG, CommandLine.ArgFlag.UGMODEL);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.UGMODELPNG_FLAG, CommandLine.ArgFlag.UGMODELPNG);
	}

	private static final Map<String, CommandLine.ArgFlag> FLAGS = new HashMap<>();
	{
		CommandLineArgParser.FLAGS.putAll(CommandLineArgParser.UNIQUE_FLAGS);
		CommandLineArgParser.FLAGS.putAll(CommandLineArgParser.NON_UNIQUE_FLAGS);
	}

	private final String[] args;
	private final Map<CommandLine.ArgFlag, String[]> parsed = new HashMap<>();
	
	public CommandLineArgParser(String[] args) throws CommandLineException
	{
		this.args = args;
		parseArgs();
	}		
	
	public Map<CommandLine.ArgFlag, String[]> getArgs()
	{
		return this.parsed;
	}
	
	private void parseArgs() throws CommandLineException
	{
		for (int i = 0; i < this.args.length; i++)
		{
			String arg = this.args[i];
			if (CommandLineArgParser.FLAGS.containsKey(arg))
			{
				i = this.parseFlag(i);
			}
			else
			{
				if (this.parsed.containsKey(CommandLine.ArgFlag.MAIN))
				{
					if (arg.startsWith("-"))
					{
						throw new CommandLineException("Unknown flag or bad main module arg: " + arg);
					}
					// May actually be the second bad argument -- we didn't validate the value of the main arg
					throw new CommandLineException("Bad/duplicate main module arg: " + arg);
				}
				parseMain(i);
			}
		}
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
			case CommandLineArgParser.JUNIT_FLAG:
			{
				this.parsed.put(CommandLine.ArgFlag.JUNIT, new String[0]);
				return i;
			}
			case CommandLineArgParser.VERBOSE_FLAG:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.VERBOSE_FLAG, new String[0]);
				return i;
			}
			case CommandLineArgParser.PATH_FLAG:
			{
				return parsePath(i);
			}
			case CommandLineArgParser.STATECHANSUBTYPES_FLAG:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.STATECHANSUBTYPES_FLAG, new String[0]);
				return i;
			}
			case CommandLineArgParser.OLD_WF_FLAG:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.OLD_WF_FLAG, new String[0]);
				return i;
			}
			case CommandLineArgParser.NO_LIVENESS_FLAG:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.NO_LIVENESS_FLAG, new String[0]);
				return i;
			}
			case CommandLineArgParser.MIN_EFSM_FLAG:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.MIN_EFSM_FLAG, new String[0]);
				return i;
			}
			case CommandLineArgParser.FAIR_FLAG:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.FAIR_FLAG, new String[0]);
				return i;
			}
			case CommandLineArgParser.NO_LOCAL_CHOICE_SUBJECT_CHECK:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.NO_LOCAL_CHOICE_SUBJECT_CHECK, new String[0]);
				return i;
			}
			case CommandLineArgParser.NO_ACCEPT_CORRELATION_CHECK:
			{
				checkAndPutUniqueFlag(CommandLineArgParser.NO_ACCEPT_CORRELATION_CHECK, new String[0]);
				return i;
			}
			case CommandLineArgParser.DOT_FLAG:
			{
				if (this.parsed.containsKey(CommandLineArgParser.UNIQUE_FLAGS.get(AUT_FLAG)))
				{
					throw new CommandLineException("Incompatible flags: " + DOT_FLAG + " and " + AUT_FLAG);
				}
				checkAndPutUniqueFlag(CommandLineArgParser.DOT_FLAG, new String[0]);
				return i;
			}
			case CommandLineArgParser.AUT_FLAG:
			{
				if (this.parsed.containsKey(CommandLineArgParser.UNIQUE_FLAGS.get(DOT_FLAG)))
				{
					throw new CommandLineException("Incompatible flags: " + DOT_FLAG + " and " + AUT_FLAG);
				}
				checkAndPutUniqueFlag(CommandLineArgParser.AUT_FLAG, new String[0]);
				return i;
			}

			// Non-unique flags
			case CommandLineArgParser.PROJECT_FLAG:
			{
				return parseProject(i);
			}
			case CommandLineArgParser.SESSION_FLAG:
			{
				return parseSession(i);
			}
			case CommandLineArgParser.STATECHAN_FLAG:
			{
				return parseStateChannels(i);
			}
			case CommandLineArgParser.API_FLAG:
			{
				return parseApi(i);
			}
			case CommandLineArgParser.API_OUTPUT_FLAG:
			{
				return parseOutput(i);
			}
			case CommandLineArgParser.EFSM_FLAG:
			{
				return parseEfsm(i);
			}
			case CommandLineArgParser.EFSMPNG_FLAG:
			{
				return parseEfsmPng(i);
			}
			case CommandLineArgParser.VEFSM_FLAG:
			{
				return parseValidationEfsm(i);
			}
			case CommandLineArgParser.VEFSMPNG_FLAG:
			{
				return parseValidationEfsmPng(i);
			}
			case CommandLineArgParser.UEFSM_FLAG:
			{
				return parseUnfairEfsm(i);
			}
			case CommandLineArgParser.UEFSMPNG_FLAG:
			{
				return parseUnfairEfsmPng(i);
			}
			case CommandLineArgParser.GMODEL_FLAG:
			{
				return parseGModel(i);
			}
			case CommandLineArgParser.GMODELPNG_FLAG:
			{
				return parseGModelPng(i);
			}
			case CommandLineArgParser.UGMODEL_FLAG:
			{
				return parseUnfairGModel(i);
			}
			case CommandLineArgParser.UGMODELPNG_FLAG:
			{
				return parseUnfairGModelPng(i);
			}

			default:
			{
				throw new RuntimeException("[TODO] Unknown flag: " + flag);
			}
		}
	}

	private void checkAndPutUniqueFlag(String flag, String[] args) throws CommandLineException
	{
		ArgFlag argFlag = CommandLineArgParser.UNIQUE_FLAGS.get(flag);
		if (this.parsed.containsKey(argFlag))
		{
			throw new CommandLineException("Duplicate flag: " + flag);
		}
		this.parsed.put(argFlag, args);
	}

	private void parseMain(int i) throws CommandLineException
	{
		String main = args[i];
		if (!CommandLineArgParser.validateModuleArg(main))
		{
			throw new CommandLineException("Bad module arg: " + main);
		}
		this.parsed.put(CommandLine.ArgFlag.MAIN, new String[] { main } );
	}

	private int parsePath(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing path argument");
		}
		String path = this.args[++i];
		if (!validatePaths(path))
		{
			throw new CommandLineException("Module path '"+ path +"' is not valid\r\n");
		}
		//this.parsed.put(CommandLineArgParser.FLAGS.get(CommandLineArgParser.PATH_FLAG), new String[] { path });
		checkAndPutUniqueFlag(CommandLineArgParser.PATH_FLAG, new String[] { path });
		return i;
	}
	
	private int parseProject(int i) throws CommandLineException
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
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.PROJECT_FLAG), proto, role);
		return i;
	}

	private int parseSession(int i) throws CommandLineException 
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol argument");
		}
		String proto = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.SESSION_FLAG), proto);
		return i;
	}

	private int parseStateChannels(int i) throws CommandLineException  // Almost same as parseProject
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.STATECHAN_FLAG), proto, role);
		return i;
	}

	private int parseApi(int i) throws CommandLineException  // Almost same as parseProject
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.API_FLAG), proto, role);
		return i;
	}
	
	private int parseEfsm(int i) throws CommandLineException
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.EFSM_FLAG), proto, role);
		return i;
	}

	private int parseValidationEfsm(int i) throws CommandLineException
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.VEFSM_FLAG), proto, role);
		return i;
	}

	private int parseUnfairEfsm(int i) throws CommandLineException
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.UEFSM_FLAG), proto, role);
		return i;
	}

	private int parseGModel(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol arguments");
		}
		String proto = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.GMODEL_FLAG), proto);
		return i;
	}

	private int parseEfsmPng(int i) throws CommandLineException
	{
		if ((i + 3) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role/file arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		String png = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.EFSMPNG_FLAG), proto, role, png);
		return i;
	}

	private int parseValidationEfsmPng(int i) throws CommandLineException
	{
		if ((i + 3) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role/file arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		String png = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.VEFSMPNG_FLAG), proto, role, png);
		return i;
	}

	private int parseUnfairEfsmPng(int i) throws CommandLineException
	{
		if ((i + 3) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role/file arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		String png = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.UEFSMPNG_FLAG), proto, role, png);
		return i;
	}

	private int parseUnfairGModel(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.UGMODEL_FLAG), proto);
		return i;
	}

	private int parseGModelPng(int i) throws CommandLineException
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/file arguments");
		}
		String proto = this.args[++i];
		String png = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.GMODELPNG_FLAG), proto, png);
		return i;
	}

	private int parseUnfairGModelPng(int i) throws CommandLineException
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/file arguments");
		}
		String proto = this.args[++i];
		String png = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.UGMODELPNG_FLAG), proto, png);
		return i;
	}


	private int parseOutput(int i) throws CommandLineException  // Almost same as parseProject
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing directory argument");
		}
		String dir = this.args[++i];
		this.parsed.put(CommandLineArgParser.FLAGS.get(CommandLineArgParser.API_OUTPUT_FLAG), new String[] { dir } );
		return i;
	}
	
	private void concatArgs(ArgFlag flag, String... toAdd)
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

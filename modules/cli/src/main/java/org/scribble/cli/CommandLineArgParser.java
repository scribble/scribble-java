package org.scribble.cli;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.scribble.cli.CommandLine.ArgFlag;

// String[] -> Map<CommandLine.Arg, String[]> -- Map array values are the arguments associated to each CommandLine.Arg
public class CommandLineArgParser
{
	public static final String VERBOSE_FLAG = "-V";
	public static final String PATH_FLAG = "-ip";
	public static final String PROJECT_FLAG = "-project";
	public static final String FSM_FLAG = "-fsm";
	public static final String SESSION_FLAG = "-session";
	public static final String STATECHAN_FLAG = "-statechan";
	public static final String API_FLAG = "-api";
	public static final String OUTPUT_FLAG = "-d";
	public static final String STATECHANSUBTYPES_FLAG = "-subtypes";
	
	private static final Map<String, CommandLine.ArgFlag> UNIQUE_FLAGS = new HashMap<>();
	{
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.VERBOSE_FLAG, CommandLine.ArgFlag.VERBOSE);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.PATH_FLAG, CommandLine.ArgFlag.PATH);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.OUTPUT_FLAG, CommandLine.ArgFlag.OUTPUT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.STATECHANSUBTYPES_FLAG, CommandLine.ArgFlag.SCHAN_API_SUBTYPES);
	}

	private static final Map<String, CommandLine.ArgFlag> NON_UNIQUE_FLAGS = new HashMap<>();
	{
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.PROJECT_FLAG, CommandLine.ArgFlag.PROJECT);
		CommandLineArgParser.UNIQUE_FLAGS.put(CommandLineArgParser.FSM_FLAG, CommandLine.ArgFlag.FSM);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.SESSION_FLAG, CommandLine.ArgFlag.SESS_API);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.STATECHAN_FLAG, CommandLine.ArgFlag.SCHAN_API);
		CommandLineArgParser.NON_UNIQUE_FLAGS.put(CommandLineArgParser.API_FLAG, CommandLine.ArgFlag.EP_API);
	}

	private static final Map<String, CommandLine.ArgFlag> FLAGS = new HashMap<>();
	{
		CommandLineArgParser.FLAGS.putAll(CommandLineArgParser.UNIQUE_FLAGS);
		CommandLineArgParser.FLAGS.putAll(CommandLineArgParser.NON_UNIQUE_FLAGS);
	}

	private final String[] args;
	private final Map<CommandLine.ArgFlag, String[]> parsed = new HashMap<>();
	
	public CommandLineArgParser(String[] args)
	{
		this.args = args;
		parseArgs();
	}		
	
	public Map<CommandLine.ArgFlag, String[]> getArgs()
	{
		return this.parsed;
	}
	
	private void parseArgs()
	{
		for (int i = 0; i < this.args.length; i++)
		{
			String arg = args[i];
			if (CommandLineArgParser.FLAGS.containsKey(arg))
			{
				i = this.parseFlag(i);
			}
			else
			{
				if (this.parsed.containsKey(CommandLine.ArgFlag.MAIN))
				{
					// Could be the second bad argument -- we didn't validating the value of the main arg
					throw new RuntimeException("Duplicate main module arg: " + arg);
				}
				parseMain(i);
			}
		}
	}

	// Pre: i is the index of the current flag to parse
	// Post: i is the index of the last argument parsed -- parseArgs does the index increment to the next current flag
	// Currently allows repeat flag decls: next overrides previous
	private int parseFlag(int i)
	{
		String flag = this.args[i];
		switch (flag)
		{
			case CommandLineArgParser.VERBOSE_FLAG:
			{
				this.parsed.put(CommandLine.ArgFlag.VERBOSE, new String[0]);
				return i;
			}
			case CommandLineArgParser.PATH_FLAG:
			{
				return parsePath(i);
			}
			case CommandLineArgParser.PROJECT_FLAG:
			{
				return parseProject(i);
			}
			case CommandLineArgParser.FSM_FLAG:
			{
				return parseFsm(i);
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
			case CommandLineArgParser.OUTPUT_FLAG:
			{
				return parseOutput(i);
			}
			case CommandLineArgParser.STATECHANSUBTYPES_FLAG:
			{
				this.parsed.put(CommandLineArgParser.FLAGS.get(CommandLineArgParser.STATECHANSUBTYPES_FLAG), new String[0]);
				return ++i;
			}
			default:
			{
				throw new RuntimeException(flag);
			}
		}
	}

	private void parseMain(int i)
	{
		String main = args[i];
		if (!CommandLineArgParser.validateModuleName(main))
		{
			throw new RuntimeException("Bad: " + main);
		}
		this.parsed.put(CommandLine.ArgFlag.MAIN, new String[] { main } );
	}

	private int parsePath(int i)
	{
		if ((i + 1) >= this.args.length)
		{
			throw new RuntimeException("Missing path argument");
		}
		String path = this.args[++i];
		if (!validatePaths(path))
		{
			throw new RuntimeException("Module path '"+ path +"' is not valid\r\n");
		}
		this.parsed.put(CommandLineArgParser.FLAGS.get(CommandLineArgParser.PATH_FLAG), new String[] { path });
		return i;
	}
	
	private int parseProject(int i)
	{
		if ((i + 2) >= this.args.length)
		{
			throw new RuntimeException("Missing protocol/role arguments");
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
	
	private int parseFsm(int i)  // Almost same as parseProject -- could factor out, but code less clear and more awkward error reporting
	{
		if ((i + 2) >= this.args.length)
		{
			throw new RuntimeException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.FSM_FLAG), proto, role);
		return i;
	}

	private int parseSession(int i)  // Almost same as parseProject
	{
		if ((i + 1) >= this.args.length)
		{
			throw new RuntimeException("Missing protocol argument");
		}
		String proto = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.SESSION_FLAG), proto);
		return i;
	}

	private int parseStateChannels(int i)  // Almost same as parseProject
	{
		if ((i + 2) >= this.args.length)
		{
			throw new RuntimeException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.STATECHAN_FLAG), proto, role);
		return i;
	}

	private int parseApi(int i)  // Almost same as parseProject
	{
		if ((i + 2) >= this.args.length)
		{
			throw new RuntimeException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		concatArgs(CommandLineArgParser.FLAGS.get(CommandLineArgParser.API_FLAG), proto, role);
		return i;
	}

	private int parseOutput(int i)  // Almost same as parseProject
	{
		if ((i + 1) >= this.args.length)
		{
			throw new RuntimeException("Missing directory argument");
		}
		String dir = this.args[++i];
		this.parsed.put(CommandLineArgParser.FLAGS.get(CommandLineArgParser.OUTPUT_FLAG), new String[] { dir } );
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

	private static boolean validateModuleName(String module)
	{
		for (String part : module.split("."))
		{
			for (int i = 0; i < part.length(); i++)
			{
				if (!Character.isLetterOrDigit(part.charAt(i)))
				{
					if (part.charAt(i) != '_')
					{
						return false;
					}
				}
			}
		}
		return true;
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

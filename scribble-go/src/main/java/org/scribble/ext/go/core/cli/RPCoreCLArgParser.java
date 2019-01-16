package org.scribble.ext.go.core.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.scribble.cli.CLArgParser;
import org.scribble.cli.CommandLineException;

public class RPCoreCLArgParser extends CLArgParser
{
	// Unique flags
	public static final String RPCORE_PARAM_FLAG         = "-param";
	public static final String RPCORE_SELECT_BRANCH_FLAG = "-param-select";
	public static final String RPCORE_NOCOPY_FLAG        = "-nocopy";
	public static final String RPCORE_PARFOREACH_FLAG    = "-parforeach";
	public static final String RPCORE_DOTAPI_FLAG        = "-dotapi";

	// Non-unique flags
	public static final String RPCORE_EFSM_FLAG     = "-param-fsm";
	public static final String RPCORE_EFSM_PNG_FLAG = "-param-fsmpng";
	public static final String RPCORE_API_GEN_FLAG  = "-param-api";
	
	private static final Map<String, RPCoreCLArgFlag> RPCORE_UNIQUE_FLAGS = new HashMap<>();
	{
		RPCoreCLArgParser.RPCORE_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_PARAM_FLAG, RPCoreCLArgFlag.RPCORE_PARAM);
		RPCoreCLArgParser.RPCORE_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_SELECT_BRANCH_FLAG, RPCoreCLArgFlag.RPCORE_SELECT_BRANCH);
		RPCoreCLArgParser.RPCORE_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_NOCOPY_FLAG, RPCoreCLArgFlag.RPCORE_NO_COPY);
		RPCoreCLArgParser.RPCORE_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_PARFOREACH_FLAG, RPCoreCLArgFlag.RPCORE_PARFOREACH);
		RPCoreCLArgParser.RPCORE_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_DOTAPI_FLAG, RPCoreCLArgFlag.RPCORE_DOTAPI);
	}

	private static final Map<String, RPCoreCLArgFlag> RPCORE_NON_UNIQUE_FLAGS = new HashMap<>();
	{
		RPCoreCLArgParser.RPCORE_NON_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_EFSM_FLAG, RPCoreCLArgFlag.RPCORE_EFSM);
		RPCoreCLArgParser.RPCORE_NON_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_EFSM_PNG_FLAG, RPCoreCLArgFlag.RPCORE_EFSM_PNG);
		RPCoreCLArgParser.RPCORE_NON_UNIQUE_FLAGS.put(RPCoreCLArgParser.RPCORE_API_GEN_FLAG, RPCoreCLArgFlag.RPCORE_API_GEN);
	}

	private static final Map<String, RPCoreCLArgFlag> RPCORE_FLAGS = new HashMap<>();
	{
		RPCoreCLArgParser.RPCORE_FLAGS.putAll(RPCoreCLArgParser.RPCORE_UNIQUE_FLAGS);
		RPCoreCLArgParser.RPCORE_FLAGS.putAll(RPCoreCLArgParser.RPCORE_NON_UNIQUE_FLAGS);
	}

	private final Map<RPCoreCLArgFlag, String[]> rpParsed = new HashMap<>();
	
	public RPCoreCLArgParser(String[] args) throws CommandLineException
	{
		super(args);  // Assigns this.args and calls parseArgs
	}		
	
	public Map<RPCoreCLArgFlag, String[]> getParamArgs() throws CommandLineException
	{
		//super.parseArgs();  // Needed
		return this.rpParsed;
	}
	
	@Override
	protected boolean isFlag(String arg)
	{
		return RPCoreCLArgParser.RPCORE_FLAGS.containsKey(arg) || super.isFlag(arg);
	}
	
	// Pre: i is the index of the current flag to parse
	// Post: i is the index of the last argument parsed -- parseArgs does the index increment to the next current flag
	@Override
	protected int parseFlag(int i) throws CommandLineException
	{
		String flag = this.args[i];
		switch (flag)
		{
			// Unique flags
			case RPCoreCLArgParser.RPCORE_PARAM_FLAG:
			{
				if (this.rpParsed.containsKey(RPCoreCLArgFlag.RPCORE_API_GEN)
						|| Arrays.asList(this.args).stream().anyMatch(a -> a.equals(RPCoreCLArgParser.RPCORE_API_GEN_FLAG)) )  // HACK FIXME -- also subsumes above condition
				{
					return rpParseParamAndPackagePath(i);
				}
				else
				{
					return rpParseParam(i);
				}
			}
			case RPCoreCLArgParser.RPCORE_SELECT_BRANCH_FLAG: return rpParseSelectBranch(i);
			case RPCoreCLArgParser.RPCORE_NOCOPY_FLAG:        return rpParseNoCopy(i);
			case RPCoreCLArgParser.RPCORE_PARFOREACH_FLAG:    return rpParseParForeach(i);
			case RPCoreCLArgParser.RPCORE_DOTAPI_FLAG:        return rpParseDotApi(i);
			
			// Non-unique flags
			case RPCoreCLArgParser.RPCORE_EFSM_FLAG:     return rpParseRoleArg(flag, i);
			case RPCoreCLArgParser.RPCORE_EFSM_PNG_FLAG: return rpParseRoleAndFileArgs(flag, i);
			case RPCoreCLArgParser.RPCORE_API_GEN_FLAG:  return rpParseRoleArg(flag, i);
			
			// Base CL
			default:
			{
				return super.parseFlag(i);
			}
		}
	}

	private int rpParseParam(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing simple global protocol name argument.");
		}
		String proto = this.args[++i];
		rpCheckAndAddUniqueFlag(RPCoreCLArgParser.RPCORE_PARAM_FLAG, new String[] { proto });
		return i;
	}

	private int rpParseParamAndPackagePath(int i) throws CommandLineException
	{
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing simple global protocol name and/or Go API package path arguments.");
		}
		String proto = this.args[++i];
		String packpath = this.args[++i];
		rpCheckAndAddUniqueFlag(RPCoreCLArgParser.RPCORE_PARAM_FLAG, new String[] { proto, packpath });
		return i;
	}

	private int rpParseSelectBranch(int i) throws CommandLineException
	{
		rpCheckAndAddUniqueFlag(RPCoreCLArgParser.RPCORE_SELECT_BRANCH_FLAG, new String[] { });
		return i;
	}

	private int rpParseNoCopy(int i) throws CommandLineException
	{
		rpCheckAndAddUniqueFlag(RPCoreCLArgParser.RPCORE_NOCOPY_FLAG, new String[] { });
		return i;
	}

	private int rpParseParForeach(int i) throws CommandLineException
	{
		rpCheckAndAddUniqueFlag(RPCoreCLArgParser.RPCORE_PARFOREACH_FLAG, new String[] { });
		return i;
	}

	private int rpParseDotApi(int i) throws CommandLineException
	{
		rpCheckAndAddUniqueFlag(RPCoreCLArgParser.RPCORE_DOTAPI_FLAG, new String[] { });
		return i;
	}

	private void rpCheckAndAddUniqueFlag(String flag, String[] args) throws CommandLineException
	{
		RPCoreCLArgFlag argFlag = RPCoreCLArgParser.RPCORE_UNIQUE_FLAGS.get(flag);
		if (this.rpParsed.containsKey(argFlag))
		{
			throw new CommandLineException("Duplicate flag: " + flag);
		}
		this.rpParsed.put(argFlag, args);
	}

	private int rpParseRoleArg(String f, int i) throws CommandLineException
	{
		RPCoreCLArgFlag flag = RPCoreCLArgParser.RPCORE_NON_UNIQUE_FLAGS.get(f);
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing role argument");
		}
		String role = this.args[++i];
		goConcatArgs(flag, role);
		return i;
	}

	private int rpParseRoleAndFileArgs(String f, int i) throws CommandLineException
	{
		RPCoreCLArgFlag flag = RPCoreCLArgParser.RPCORE_NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing role/file arguments");
		}
		String role = this.args[++i];
		String png = this.args[++i];
		goConcatArgs(flag, role, png);
		return i;
	}

	/*// path is absolute package path prefix for API imports
	private int rpParsePackagePathAndRoleArgs(String f, int i) throws CommandLineException
	{
		RPCoreCLArgFlag flag = RPCoreCLArgParser.RPCORE_NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing role/path arguments");
		}
		String path = this.args[++i];
		String role = this.args[++i];
		goConcatArgs(flag, role, path);
		return i;
	}*/

	/*// FIXME: factor out with core arg parser -- issue is GoCLArgFlag is currently an unlreated type to CLArgFlag
	private int goParseProtoAndRoleArgs(String f, int i) throws CommandLineException
	{
		ParamCoreCLArgFlag flag = ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		goConcatArgs(flag, proto, role);
		return i;
	}*/
	
	// FIXME: factor out with core arg parser -- issue is GoCLArgFlag is currently an unlreated type to CLArgFlag
	private void goConcatArgs(RPCoreCLArgFlag flag, String... toAdd)
	{
		String[] args = this.rpParsed.get(flag);
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
		this.rpParsed.put(flag, args);
	}
}

package org.scribble.ext.go.core.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.scribble.cli.CLArgParser;
import org.scribble.cli.CommandLineException;

public class ParamCoreCLArgParser extends CLArgParser
{
	// Unique flags
	public static final String PARAM_CORE_PARAM_FLAG = "-param";
	public static final String PARAM_CORE_NOCOPY_FLAG = "-nocopy";

	// Non-unique flags
	public static final String PARAM_CORE_EFSM_FLAG     = "-param-fsm";
	public static final String PARAM_CORE_EFSM_PNG_FLAG = "-param-fsmpng";
	public static final String PARAM_CORE_API_GEN_FLAG  = "-param-api";
	
	private static final Map<String, ParamCoreCLArgFlag> PARAM_UNIQUE_FLAGS = new HashMap<>();
	{
		ParamCoreCLArgParser.PARAM_UNIQUE_FLAGS.put(ParamCoreCLArgParser.PARAM_CORE_PARAM_FLAG, ParamCoreCLArgFlag.PARAM_CORE_PARAM);
		ParamCoreCLArgParser.PARAM_UNIQUE_FLAGS.put(ParamCoreCLArgParser.PARAM_CORE_NOCOPY_FLAG, ParamCoreCLArgFlag.PARAM_CORE_NO_COPY);
	}

	private static final Map<String, ParamCoreCLArgFlag> PARAM_NON_UNIQUE_FLAGS = new HashMap<>();
	{
		ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(ParamCoreCLArgParser.PARAM_CORE_EFSM_FLAG, ParamCoreCLArgFlag.PARAM_CORE_EFSM);
		ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(ParamCoreCLArgParser.PARAM_CORE_EFSM_PNG_FLAG, ParamCoreCLArgFlag.PARAM_CORE_EFSM_PNG);
		ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(ParamCoreCLArgParser.PARAM_CORE_API_GEN_FLAG, ParamCoreCLArgFlag.PARAM_CORE_API_GEN);
	}

	private static final Map<String, ParamCoreCLArgFlag> PARAM_FLAGS = new HashMap<>();
	{
		ParamCoreCLArgParser.PARAM_FLAGS.putAll(ParamCoreCLArgParser.PARAM_UNIQUE_FLAGS);
		ParamCoreCLArgParser.PARAM_FLAGS.putAll(ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS);
	}

	private final Map<ParamCoreCLArgFlag, String[]> paramParsed = new HashMap<>();
	
	public ParamCoreCLArgParser(String[] args) throws CommandLineException
	{
		super(args);  // Assigns this.args and calls parseArgs
	}		
	
	public Map<ParamCoreCLArgFlag, String[]> getParamArgs() throws CommandLineException
	{
		//super.parseArgs();  // Needed
		return this.paramParsed;
	}
	
	@Override
	protected boolean isFlag(String arg)
	{
		return ParamCoreCLArgParser.PARAM_FLAGS.containsKey(arg) || super.isFlag(arg);
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

			case ParamCoreCLArgParser.PARAM_CORE_PARAM_FLAG:
			{
				return paramParseParam(i);
			}
			case ParamCoreCLArgParser.PARAM_CORE_NOCOPY_FLAG: return paramParseNoCopy(i);
			

			// Non-unique flags
			case ParamCoreCLArgParser.PARAM_CORE_EFSM_FLAG:     return paramParseRoleArg(flag, i);
			case ParamCoreCLArgParser.PARAM_CORE_EFSM_PNG_FLAG: return paramParseRoleAndFileArgs(flag, i);
			case ParamCoreCLArgParser.PARAM_CORE_API_GEN_FLAG:  return paramParsePackagePathAndRoleArgs(flag, i);
			

			// Base CL

			default:
			{
				return super.parseFlag(i);
			}
		}
	}

	private int paramParseParam(int i) throws CommandLineException
	{
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing simple global protocol name argument.");
		}
		String proto = this.args[++i];
		paramCheckAndAddUniqueFlag(ParamCoreCLArgParser.PARAM_CORE_PARAM_FLAG, new String[] { proto });
		return i;
	}

	private int paramParseNoCopy(int i) throws CommandLineException
	{
		paramCheckAndAddUniqueFlag(ParamCoreCLArgParser.PARAM_CORE_NOCOPY_FLAG, new String[] { });
		return i;
	}

	private void paramCheckAndAddUniqueFlag(String flag, String[] args) throws CommandLineException
	{
		ParamCoreCLArgFlag argFlag = ParamCoreCLArgParser.PARAM_UNIQUE_FLAGS.get(flag);
		if (this.paramParsed.containsKey(argFlag))
		{
			throw new CommandLineException("Duplicate flag: " + flag);
		}
		this.paramParsed.put(argFlag, args);
	}

	private int paramParseRoleArg(String f, int i) throws CommandLineException
	{
		ParamCoreCLArgFlag flag = ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
		if ((i + 1) >= this.args.length)
		{
			throw new CommandLineException("Missing role argument");
		}
		String role = this.args[++i];
		goConcatArgs(flag, role);
		return i;
	}

	protected int paramParseRoleAndFileArgs(String f, int i) throws CommandLineException
	{
		ParamCoreCLArgFlag flag = ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing role/file arguments");
		}
		String role = this.args[++i];
		String png = this.args[++i];
		goConcatArgs(flag, role, png);
		return i;
	}

	// path is absolute package path prefix for API imports
	protected int paramParsePackagePathAndRoleArgs(String f, int i) throws CommandLineException
	{
		ParamCoreCLArgFlag flag = ParamCoreCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing role/path arguments");
		}
		String path = this.args[++i];
		String role = this.args[++i];
		goConcatArgs(flag, role, path);
		return i;
	}

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
	private void goConcatArgs(ParamCoreCLArgFlag flag, String... toAdd)
	{
		String[] args = this.paramParsed.get(flag);
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
		this.paramParsed.put(flag, args);
	}
}

package org.scribble.ext.go.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.scribble.cli.CLArgParser;
import org.scribble.cli.CommandLineException;

public class ParamCLArgParser extends CLArgParser
{
	// Unique flags
	public static final String PARAM_FLAG = "-param";

	// Non-unique flags
	public static final String GO_API_GEN_FLAG          = "-goapi";

	public static final String PARAM_CORE_EFSM_FLAG     = "-param-fsm";
	public static final String PARAM_CORE_EFSM_PNG_FLAG = "-param-fsmpng";
	public static final String PARAM_CORE_API_GEN_FLAG  = "-param-api";
	
	private static final Map<String, ParamCLArgFlag> PARAM_UNIQUE_FLAGS = new HashMap<>();
	{
		ParamCLArgParser.PARAM_UNIQUE_FLAGS.put(ParamCLArgParser.PARAM_FLAG, ParamCLArgFlag.PARAM);
	}

	private static final Map<String, ParamCLArgFlag> PARAM_NON_UNIQUE_FLAGS = new HashMap<>();
	{
		ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(ParamCLArgParser.PARAM_CORE_EFSM_FLAG, ParamCLArgFlag.PARAM_CORE_EFSM);
		ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(ParamCLArgParser.PARAM_CORE_EFSM_PNG_FLAG, ParamCLArgFlag.PARAM_CORE_EFSM_PNG);
		ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(ParamCLArgParser.GO_API_GEN_FLAG, ParamCLArgFlag.GO_API_GEN);
		ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(ParamCLArgParser.PARAM_CORE_API_GEN_FLAG, ParamCLArgFlag.PARAM_CORE_API_GEN);
	}

	private static final Map<String, ParamCLArgFlag> PARAM_FLAGS = new HashMap<>();
	{
		ParamCLArgParser.PARAM_FLAGS.putAll(ParamCLArgParser.PARAM_UNIQUE_FLAGS);
		ParamCLArgParser.PARAM_FLAGS.putAll(ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS);
	}

	private final Map<ParamCLArgFlag, String[]> paramParsed = new HashMap<>();
	
	public ParamCLArgParser(String[] args) throws CommandLineException
	{
		super(args);  // Assigns this.args and calls parseArgs
	}		
	
	public Map<ParamCLArgFlag, String[]> getParamArgs() throws CommandLineException
	{
		//super.parseArgs();  // Needed
		return this.paramParsed;
	}
	
	@Override
	protected boolean isFlag(String arg)
	{
		return ParamCLArgParser.PARAM_FLAGS.containsKey(arg) || super.isFlag(arg);
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

			case ParamCLArgParser.PARAM_FLAG:
			{
				return paramParseParam(i);
			}
			

			// Non-unique flags
			
			case ParamCLArgParser.GO_API_GEN_FLAG:
			{
				return goParseProtoAndRoleArgs(flag, i);
			}

			case ParamCLArgParser.PARAM_CORE_EFSM_FLAG:     return paramParseRoleArg(flag, i);
			case ParamCLArgParser.PARAM_CORE_EFSM_PNG_FLAG: return paramParseRoleAndFileArgs(flag, i);
			case ParamCLArgParser.PARAM_CORE_API_GEN_FLAG:  return paramParseRoleArg(flag, i);
			

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
		paramCheckAndAddUniqueFlag(ParamCLArgParser.PARAM_FLAG, new String[] { proto });
		return i;
	}

	private void paramCheckAndAddUniqueFlag(String flag, String[] args) throws CommandLineException
	{
		ParamCLArgFlag argFlag = ParamCLArgParser.PARAM_UNIQUE_FLAGS.get(flag);
		if (this.paramParsed.containsKey(argFlag))
		{
			throw new CommandLineException("Duplicate flag: " + flag);
		}
		this.paramParsed.put(argFlag, args);
	}

	private int paramParseRoleArg(String f, int i) throws CommandLineException
	{
		ParamCLArgFlag flag = ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
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
		ParamCLArgFlag flag = ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing role/file arguments");
		}
		String role = this.args[++i];
		String png = this.args[++i];
		goConcatArgs(flag, role, png);
		return i;
	}

	// FIXME: factor out with core arg parser -- issue is GoCLArgFlag is currently an unlreated type to CLArgFlag
	private int goParseProtoAndRoleArgs(String f, int i) throws CommandLineException
	{
		ParamCLArgFlag flag = ParamCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
		if ((i + 2) >= this.args.length)
		{
			throw new CommandLineException("Missing protocol/role arguments");
		}
		String proto = this.args[++i];
		String role = this.args[++i];
		goConcatArgs(flag, proto, role);
		return i;
	}
	
	// FIXME: factor out with core arg parser -- issue is GoCLArgFlag is currently an unlreated type to CLArgFlag
	private void goConcatArgs(ParamCLArgFlag flag, String... toAdd)
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

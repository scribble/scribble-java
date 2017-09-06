package org.scribble.ext.go.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.scribble.cli.CLArgParser;
import org.scribble.cli.CommandLineException;

public class GoCLArgParser extends CLArgParser
{
	// Non-unique flags
	public static final String GO_API_GEN_FLAG          = "-goapi";
	
	//private static final Map<String, ParamCLArgFlag> PARAM_UNIQUE_FLAGS = new HashMap<>();

	private static final Map<String, GoCLArgFlag> PARAM_NON_UNIQUE_FLAGS = new HashMap<>();
	{
		GoCLArgParser.PARAM_NON_UNIQUE_FLAGS.put(GoCLArgParser.GO_API_GEN_FLAG, GoCLArgFlag.GO_API_GEN);
	}

	private static final Map<String, GoCLArgFlag> PARAM_FLAGS = new HashMap<>();
	{
		GoCLArgParser.PARAM_FLAGS.putAll(GoCLArgParser.PARAM_NON_UNIQUE_FLAGS);
	}

	private final Map<GoCLArgFlag, String[]> paramParsed = new HashMap<>();
	
	public GoCLArgParser(String[] args) throws CommandLineException
	{
		super(args);  // Assigns this.args and calls parseArgs
	}		
	
	public Map<GoCLArgFlag, String[]> getParamArgs() throws CommandLineException
	{
		//super.parseArgs();  // Needed
		return this.paramParsed;
	}
	
	@Override
	protected boolean isFlag(String arg)
	{
		return GoCLArgParser.PARAM_FLAGS.containsKey(arg) || super.isFlag(arg);
	}
	
	// Pre: i is the index of the current flag to parse
	// Post: i is the index of the last argument parsed -- parseArgs does the index increment to the next current flag
	@Override
	protected int parseFlag(int i) throws CommandLineException
	{
		String flag = this.args[i];
		switch (flag)
		{
			// Non-unique flags
			
			case GoCLArgParser.GO_API_GEN_FLAG:
			{
				return goParseProtoAndRoleArgs(flag, i);
			}
			

			// Base CL

			default:
			{
				return super.parseFlag(i);
			}
		}
	}

	// FIXME: factor out with core arg parser -- issue is GoCLArgFlag is currently an unlreated type to CLArgFlag
	private int goParseProtoAndRoleArgs(String f, int i) throws CommandLineException
	{
		GoCLArgFlag flag = GoCLArgParser.PARAM_NON_UNIQUE_FLAGS.get(f);
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
	private void goConcatArgs(GoCLArgFlag flag, String... toAdd)
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

package org.scribble.ext.go.cli;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.scribble.cli.CLArgFlag;
import org.scribble.cli.CommandLine;
import org.scribble.cli.CommandLineException;
import org.scribble.ext.go.codegen.statetype.go.GoEndpointApiGenerator;
import org.scribble.ext.go.main.ParamMainContext;
import org.scribble.main.AntlrSourceException;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;
import org.scribble.util.ScribParserException;

public class ParamCommandLine extends CommandLine
{
	protected final Map<ParamCLArgFlag, String[]> paramArgs;  // Maps each flag to list of associated argument values
	
	public ParamCommandLine(String... args) throws CommandLineException
	{
		this(new ParamCLArgParser(args));
	}

	private ParamCommandLine(ParamCLArgParser p) throws CommandLineException
	{
		super(p);  // calls p.parse()
		if (this.args.containsKey(CLArgFlag.INLINE_MAIN_MOD))
		{
			// FIXME: should be fine
			throw new RuntimeException("[param] Inline modules not supported:\n" + this.args.get(CLArgFlag.INLINE_MAIN_MOD));
		}
		// FIXME? Duplicated from core
		if (!this.args.containsKey(CLArgFlag.MAIN_MOD))
		{
			throw new CommandLineException("No main module has been specified\r\n");
		}
		this.paramArgs = p.getParamArgs();
	}
	
	@Override
	protected ParamMainContext newMainContext() throws ScribParserException, ScribbleException
	{
		boolean debug = this.args.containsKey(CLArgFlag.VERBOSE);  // TODO: factor out with CommandLine (cf. MainContext fields)
		boolean useOldWF = this.args.containsKey(CLArgFlag.OLD_WF);
		boolean noLiveness = this.args.containsKey(CLArgFlag.NO_LIVENESS);
		boolean minEfsm = this.args.containsKey(CLArgFlag.LTSCONVERT_MIN);
		boolean fair = this.args.containsKey(CLArgFlag.FAIR);
		boolean noLocalChoiceSubjectCheck = this.args.containsKey(CLArgFlag.NO_LOCAL_CHOICE_SUBJECT_CHECK);
		boolean noAcceptCorrelationCheck = this.args.containsKey(CLArgFlag.NO_ACCEPT_CORRELATION_CHECK);
		boolean noValidation = this.args.containsKey(CLArgFlag.NO_VALIDATION);

		List<Path> impaths = this.args.containsKey(CLArgFlag.IMPORT_PATH)
				? CommandLine.parseImportPaths(this.args.get(CLArgFlag.IMPORT_PATH)[0])
				: Collections.emptyList();
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		if (this.args.containsKey(CLArgFlag.INLINE_MAIN_MOD))
		{
			/*return new ParamMainContext(debug, locator, this.args.get(CLArgFlag.INLINE_MAIN_MOD)[0], useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, solver);*/
			throw new RuntimeException("[param] Shouldn't get in here:\n" + this.args.get(CLArgFlag.INLINE_MAIN_MOD)[0]);  // Checked in constructor
		}
		else
		{
			Path mainpath = CommandLine.parseMainPath(this.args.get(CLArgFlag.MAIN_MOD)[0]);
			return new ParamMainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation);
		}
	}

	@Override
	protected void doNonAttemptableOutputTasks(Job job) throws ScribbleException, CommandLineException
	{		
		if (this.paramArgs.containsKey(ParamCLArgFlag.GO_API_GEN))
		{
			JobContext jcontext = job.getContext();
			String[] args = this.paramArgs.get(ParamCLArgFlag.GO_API_GEN);
			for (int i = 0; i < args.length; i += 2)
			{
				GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
				Role role = checkRoleArg(jcontext, fullname, args[i+1]);
				Map<String, String> goClasses = new GoEndpointApiGenerator(job).generateGoApi(fullname, role);
				outputClasses(goClasses);
			}
		}
		else
		{
			super.doNonAttemptableOutputTasks(job);
		}
	}

	public static void main(String[] args) throws CommandLineException, AntlrSourceException
	{
		new ParamCommandLine(args).run();
	}
}

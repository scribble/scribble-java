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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.MainContext;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.global.SGraph;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribParserException;
import org.scribble.util.ScribUtil;

public class CommandLine
{
	protected enum ArgFlag
	{
		// Unique flags
		JUNIT,  // For internal use (JUnit test harness)
		MAIN_MOD,
		IMPORT_PATH,
		VERBOSE,
		SCHAN_API_SUBTYPES,
		OLD_WF,
		NO_LIVENESS,
		LTSCONVERT_MIN,  // Currently only affects EFSM output (i.e. -fsm..) and API gen -- doesn't affect validation
		FAIR,
		NO_LOCAL_CHOICE_SUBJECT_CHECK,
		NO_ACCEPT_CORRELATION_CHECK,
		DOT,
		AUT,
		NO_VALIDATION,
		INLINE_MAIN_MOD,
		F17,

		// Non-unique flags
		PROJECT,
		API_OUTPUT,
		EFSM,
		VALIDATION_EFSM,
		UNFAIR_EFSM,
		UNFAIR_EFSM_PNG,
		EFSM_PNG,
		VALIDATION_EFSM_PNG,
		SGRAPH,
		UNFAIR_SGRAPH,
		SGRAPH_PNG,
		UNFAIR_SGRAPH_PNG,
		API_GEN,
		SESS_API_GEN,
		SCHAN_API_GEN,
	}
	
	private final Map<ArgFlag, String[]> args;  // Maps each flag to list of associated argument values
	
	public CommandLine(String... args) throws CommandLineException
	{
		this.args = new CommandLineArgParser(args).getArgs();
		if (!this.args.containsKey(ArgFlag.MAIN_MOD) && !this.args.containsKey(ArgFlag.INLINE_MAIN_MOD))
		{
			throw new CommandLineException("No main module has been specified\r\n");
		}
	}

	public static void main(String[] args) throws CommandLineException, ScribbleException
	{
		try
		{
			new CommandLine(args).run();
		}
		catch (ScribParserException | CommandLineException e)
		{
			System.err.println(e.getMessage());  // No need to give full stack trace, even for debug, for command line errors
			System.exit(1);
		}
		catch (RuntimeScribbleException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public void run() throws ScribbleException, CommandLineException, ScribParserException
	{
		try
		{
			Job job = newJob(newMainContext());
			ScribbleException fail = null;
			try
			{
				/*// Scribble extensions (custom Job passes)
				if (this.args.containsKey(ArgFlag.F17))
				{
					GProtocolName simpname = new GProtocolName(this.args.get(ArgFlag.F17)[0]);
					F17Main.parseAndCheckWF(job, simpname);  // Includes base passes
				}

				// Base Scribble
				else*/
				{
					job.checkWellFormedness();
				}
			}
			catch (ScribbleException x)
			{
				fail = x;
			}
			try 
			{
				// Following must be ordered appropriately -- ?
				if (this.args.containsKey(ArgFlag.PROJECT))
				{
					outputProjections(job);
				}
				if (this.args.containsKey(ArgFlag.EFSM))
				{
					outputEGraph(job, true, true);
				}
				if (this.args.containsKey(ArgFlag.VALIDATION_EFSM))
				{
					outputEGraph(job, false, true);
				}
				if (this.args.containsKey(ArgFlag.UNFAIR_EFSM))
				{
					outputEGraph(job, false, false);
				}
				if (this.args.containsKey(ArgFlag.EFSM_PNG))
				{
					drawEGraph(job, true, true);
				}
				if (this.args.containsKey(ArgFlag.VALIDATION_EFSM_PNG))
				{
					drawEGraph(job, false, true);
				}
				if (this.args.containsKey(ArgFlag.UNFAIR_EFSM_PNG))
				{
					drawEGraph(job, false, false);
				}
				if (this.args.containsKey(ArgFlag.SGRAPH) || this.args.containsKey(ArgFlag.SGRAPH_PNG)
						|| this.args.containsKey(ArgFlag.UNFAIR_SGRAPH) || this.args.containsKey(ArgFlag.UNFAIR_SGRAPH_PNG))
				{
					if (job.useOldWf)
					{
						throw new CommandLineException("Global model flag(s) incompatible with: "  + CommandLineArgParser.OLD_WF_FLAG);
					}
					if (this.args.containsKey(ArgFlag.SGRAPH))
					{
						outputSGraph(job, true);
					}
					if (this.args.containsKey(ArgFlag.UNFAIR_SGRAPH))
					{
						outputSGraph(job, false);
					}
					if (this.args.containsKey(ArgFlag.SGRAPH_PNG))
					{
						drawSGraph(job, true);
					}
					if (this.args.containsKey(ArgFlag.UNFAIR_SGRAPH_PNG))
					{
						drawSGraph(job, false);
					}
				}
			}
			catch (ScribbleException x)
			{
				if (fail == null)
				{
					fail = x;
				}
			}
			if (fail != null)
			{
				throw fail;
			}

			if (this.args.containsKey(ArgFlag.SESS_API_GEN))
			{
				outputSessionApi(job);
			}
			if (this.args.containsKey(ArgFlag.SCHAN_API_GEN))
			{
				outputStateChannelApi(job);
			}
			if (this.args.containsKey(ArgFlag.API_GEN))
			{
				outputEndpointApi(job);
			}
		}
		catch (ScribbleException e)  // Wouldn't need to do this if not Runnable (so maybe change)
		{
			if (this.args.containsKey(ArgFlag.JUNIT) || this.args.containsKey(ArgFlag.VERBOSE))
			{
				/*RuntimeScribbleException ee = new RuntimeScribbleException(e.getMessage());
				ee.setStackTrace(e.getStackTrace());
				throw ee;*/
				throw e;
			}
			else
			{
				System.err.println(e.getMessage());  // JUnit harness looks for an exception
				System.exit(1);
			}
		}
	}
	
	// FIXME: option to write to file, like classes
	private void outputProjections(Job job) throws CommandLineException, ScribbleException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.PROJECT);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			Map<LProtocolName, Module> projections = job.getProjections(fullname, role);
			System.out.println("\n" + projections.values().stream().map((p) -> p.toString()).collect(Collectors.joining("\n\n")));
		}
	}

	// dot/aut text output
	// forUser: true means for API gen and general user info (may be minimised), false means for validation (non-minimised, fair or unfair)
	// (forUser && !fair) should not hold, i.e. unfair doesn't make sense if forUser
	private void outputEGraph(Job job, boolean forUser, boolean fair) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = forUser ? this.args.get(ArgFlag.EFSM) : (fair ? this.args.get(ArgFlag.VALIDATION_EFSM) : this.args.get(ArgFlag.UNFAIR_EFSM));
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			EGraph fsm = getEGraph(job, fullname, role, forUser, fair);
			String out = this.args.containsKey(ArgFlag.AUT) ? fsm.toAut() : fsm.toDot();
			System.out.println("\n" + out);  // Endpoint graphs are "inlined" (a single graph is built)
		}
	}

	private void drawEGraph(Job job, boolean forUser, boolean fair) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = forUser ? this.args.get(ArgFlag.EFSM_PNG) : (fair ? this.args.get(ArgFlag.VALIDATION_EFSM_PNG) : this.args.get(ArgFlag.UNFAIR_EFSM_PNG));
		for (int i = 0; i < args.length; i += 3)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			String png = args[i+2];
			EGraph fsm = getEGraph(job, fullname, role, forUser, fair);
			runDot(fsm.toDot(), png);
		}
	}

  // Endpoint graphs are "inlined", so only a single graph is built (cf. projection output)
	private EGraph getEGraph(Job job, GProtocolName fullname, Role role, boolean forUser, boolean fair)
			throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		GProtocolDecl gpd = (GProtocolDecl) jcontext.getMainModule().getProtocolDecl(fullname.getSimpleName());
		if (gpd == null || !gpd.header.roledecls.getRoles().contains(role))
		{
			throw new CommandLineException("Bad FSM construction args: " + Arrays.toString(this.args.get(ArgFlag.DOT)));
		}
		EGraph graph;
		if (forUser)  // The (possibly minimised) user-output EFSM for API gen
		{
			graph = this.args.containsKey(ArgFlag.LTSCONVERT_MIN)
					? jcontext.getMinimisedEGraph(fullname, role) : jcontext.getEGraph(fullname, role);
		}
		else  // The (possibly unfair-transformed) internal EFSM for validation
		{
			graph = //(!this.args.containsKey(ArgFlag.FAIR) && !this.args.containsKey(ArgFlag.NO_LIVENESS))  // Cf. GlobalModelChecker.getEndpointFSMs
					!fair
					? jcontext.getUnfairEGraph(fullname, role) : jcontext.getEGraph(fullname, role);
		}
		if (graph == null)
		{
			throw new RuntimeScribbleException("Shouldn't see this: " + fullname);  // Should be suppressed by an earlier failure
		}
		return graph;
	}

	private void outputSGraph(Job job, boolean fair) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = fair ? this.args.get(ArgFlag.SGRAPH) : this.args.get(ArgFlag.UNFAIR_SGRAPH);
		for (int i = 0; i < args.length; i += 1)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			SGraph model = getSGraph(job, fullname, fair);
			String out = this.args.containsKey(ArgFlag.AUT) ? model.toAut() : model.toDot();
			System.out.println("\n" + out);
		}
	}

	private void drawSGraph(Job job, boolean fair) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = fair ? this.args.get(ArgFlag.SGRAPH_PNG) : this.args.get(ArgFlag.UNFAIR_SGRAPH_PNG);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			String png = args[i+1];
			SGraph model = getSGraph(job, fullname, fair);
			runDot(model.toDot(), png);
		}
	}

	private static SGraph getSGraph(Job job, GProtocolName fullname, boolean fair) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		SGraph model = fair ? jcontext.getSGraph(fullname) : jcontext.getUnfairSGraph(fullname);
		if (model == null)
		{
			throw new RuntimeScribbleException("Shouldn't see this: " + fullname);  // Should be suppressed by an earlier failure
		}
		return model;
	}

	private void outputEndpointApi(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.API_GEN);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Map<String, String> sessClasses = job.generateSessionApi(fullname);
			outputClasses(sessClasses);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			Map<String, String> scClasses = job.generateStateChannelApi(fullname, role, this.args.containsKey(ArgFlag.SCHAN_API_SUBTYPES));
			outputClasses(scClasses);
		}
	}

	private void outputSessionApi(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.SESS_API_GEN);
		for (String fullname : args)
		{
			GProtocolName gpn = checkGlobalProtocolArg(jcontext, fullname);
			Map<String, String> classes = job.generateSessionApi(gpn);
			outputClasses(classes);
		}
	}
	
	private void outputStateChannelApi(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.SCHAN_API_GEN);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			Map<String, String> classes = job.generateStateChannelApi(fullname, role, this.args.containsKey(ArgFlag.SCHAN_API_SUBTYPES));
			outputClasses(classes);
		}
	}

	// filepath -> class source
	private void outputClasses(Map<String, String> classes) throws ScribbleException
	{
		Consumer<String> f;
		if (this.args.containsKey(ArgFlag.API_OUTPUT))
		{
			String dir = this.args.get(ArgFlag.API_OUTPUT)[0];
			f = (path) -> { ScribUtil.handleLambdaScribbleException(() ->
							{
								String tmp = dir + "/" + path;
								if (this.args.containsKey(ArgFlag.VERBOSE))
								{
									System.out.println("\n[DEBUG] Writing to: " + tmp);
								}
								ScribUtil.writeToFile(tmp, classes.get(path)); return null; 
							}); };
		}
		else
		{
			f = (path) -> { System.out.println(path + ":\n" + classes.get(path)); };
		}
		classes.keySet().stream().forEach(f);
	}
	
	private static void runDot(String dot, String png) throws ScribbleException, CommandLineException
	{
		String tmpName = png + ".tmp";
		File tmp = new File(tmpName);
		if (tmp.exists())
		{
			throw new CommandLineException("Cannot overwrite: " + tmpName);
		}
		try
		{
			ScribUtil.writeToFile(tmpName, dot);
			String[] res = ScribUtil.runProcess("dot", "-Tpng", "-o" + png, tmpName);
			System.out.print(!res[1].isEmpty() ? res[1] : res[0]);  // already "\n" terminated
		}
		finally
		{
			tmp.delete();
		}
	}
	
	private Job newJob(MainContext mc)
	{
		//Job job = new Job(cjob);  // Doesn't work due to (recursive) maven dependencies
		return mc.newJob();
	}

	private MainContext newMainContext() throws ScribParserException, ScribbleException
	{
		//boolean jUnit = this.args.containsKey(ArgFlag.JUNIT);
		boolean debug = this.args.containsKey(ArgFlag.VERBOSE);
		boolean useOldWF = this.args.containsKey(ArgFlag.OLD_WF);
		boolean noLiveness = this.args.containsKey(ArgFlag.NO_LIVENESS);
		boolean minEfsm = this.args.containsKey(ArgFlag.LTSCONVERT_MIN);
		boolean fair = this.args.containsKey(ArgFlag.FAIR);
		boolean noLocalChoiceSubjectCheck = this.args.containsKey(ArgFlag.NO_LOCAL_CHOICE_SUBJECT_CHECK);
		boolean noAcceptCorrelationCheck = this.args.containsKey(ArgFlag.NO_ACCEPT_CORRELATION_CHECK);
		boolean noValidation = this.args.containsKey(ArgFlag.NO_VALIDATION);
		boolean f17 = this.args.containsKey(ArgFlag.F17);

		List<Path> impaths = this.args.containsKey(ArgFlag.IMPORT_PATH)
				? CommandLine.parseImportPaths(this.args.get(ArgFlag.IMPORT_PATH)[0])
				: Collections.emptyList();
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		if (this.args.containsKey(ArgFlag.INLINE_MAIN_MOD))
		{
			return new MainContext(debug, locator, this.args.get(ArgFlag.INLINE_MAIN_MOD)[0], useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17);
		}
		else
		{
			Path mainpath = CommandLine.parseMainPath(this.args.get(ArgFlag.MAIN_MOD)[0]);
			//return new MainContext(jUnit, debug, locator, mainpath, useOldWF, noLiveness);
			return new MainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17);
		}
	}
	
	private static Path parseMainPath(String path)
	{
		return Paths.get(path);
	}
	
	private static List<Path> parseImportPaths(String paths)
	{
		return Arrays.stream(paths.split(File.pathSeparator)).map((s) -> Paths.get(s)).collect(Collectors.toList());
	}
	
	private static GProtocolName checkGlobalProtocolArg(JobContext jcontext, String simpname) throws CommandLineException
	{
		GProtocolName simpgpn = new GProtocolName(simpname);
		Module main = jcontext.getMainModule();
		if (!main.hasProtocolDecl(simpgpn))
		{
			throw new CommandLineException("Global protocol not found: " + simpname);
		}
		ProtocolDecl<?> pd = main.getProtocolDecl(simpgpn);
		if (pd == null || !pd.isGlobal())
		{
			throw new CommandLineException("Global protocol not found: " + simpname);
		}
		if (pd.isAuxModifier())  // CHECKME: maybe don't check for all, e.g. -project
		{
			throw new CommandLineException("Invalid aux protocol specified as root: " + simpname);
		}
		return new GProtocolName(jcontext.main, simpgpn);
	}

	private static Role checkRoleArg(JobContext jcontext, GProtocolName fullname, String rolename) throws CommandLineException
	{
		ProtocolDecl<?> pd = jcontext.getMainModule().getProtocolDecl(fullname.getSimpleName());
		Role role = new Role(rolename);
		if (!pd.header.roledecls.getRoles().contains(role))
		{
			throw new CommandLineException("Role not declared for " + fullname + ": " + role);
		}
		return role;
	}
}

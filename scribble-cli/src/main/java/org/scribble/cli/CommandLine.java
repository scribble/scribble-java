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
import org.scribble.codegen.java.JEndpointApiGenerator;
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
	protected //final
	Map<CLArgFlag, String[]> args;  // Maps each flag to list of associated argument values
	
	protected CommandLine()
	{
		
	}
	
	public CommandLine(String... args) throws CommandLineException
	{
		this.args = new CLArgParser(args).getArgs();
		if (!this.args.containsKey(CLArgFlag.MAIN_MOD) && !this.args.containsKey(CLArgFlag.INLINE_MAIN_MOD))
		{
			throw new CommandLineException("No main module has been specified\r\n");
		}
	}
	
	// A Scribble extension should override newMainContext as appropriate.
	protected MainContext newMainContext() throws ScribParserException, ScribbleException
	{
		//boolean jUnit = this.args.containsKey(ArgFlag.JUNIT); 
		boolean debug = this.args.containsKey(CLArgFlag.VERBOSE);  // TODO: factor out (cf. MainContext fields)
		boolean useOldWF = this.args.containsKey(CLArgFlag.OLD_WF);
		boolean noLiveness = this.args.containsKey(CLArgFlag.NO_LIVENESS);
		boolean minEfsm = this.args.containsKey(CLArgFlag.LTSCONVERT_MIN);
		boolean fair = this.args.containsKey(CLArgFlag.FAIR);
		boolean noLocalChoiceSubjectCheck = this.args.containsKey(CLArgFlag.NO_LOCAL_CHOICE_SUBJECT_CHECK);
		boolean noAcceptCorrelationCheck = this.args.containsKey(CLArgFlag.NO_ACCEPT_CORRELATION_CHECK);
		boolean noValidation = this.args.containsKey(CLArgFlag.NO_VALIDATION);
		boolean f17 = this.args.containsKey(CLArgFlag.F17);

		List<Path> impaths = this.args.containsKey(CLArgFlag.IMPORT_PATH)
				? CommandLine.parseImportPaths(this.args.get(CLArgFlag.IMPORT_PATH)[0])
				: Collections.emptyList();
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		if (this.args.containsKey(CLArgFlag.INLINE_MAIN_MOD))
		{
			return new MainContext(debug, locator, this.args.get(CLArgFlag.INLINE_MAIN_MOD)[0], useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17);
		}
		else
		{
			Path mainpath = CommandLine.parseMainPath(this.args.get(CLArgFlag.MAIN_MOD)[0]);
			//return new MainContext(jUnit, debug, locator, mainpath, useOldWF, noLiveness);
			return new MainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17);
		}
	}

	public static void main(String[] args) throws CommandLineException, ScribbleException
	{
		new CommandLine(args).run();
	}

	public void run() throws CommandLineException, ScribbleException  // ScribbleException is for JUnit testing
	{
		try
		{
			try
			{
				tryRun();
			}
			catch (ScribbleException e)  // Wouldn't need to do this if not Runnable (so maybe change)
			{
				if (this.args.containsKey(CLArgFlag.JUNIT) || this.args.containsKey(CLArgFlag.VERBOSE))
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

	protected void tryRun() throws ScribParserException, ScribbleException, CommandLineException
	{
		MainContext mc = newMainContext();
		Job job = mc.newJob();
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
		
		// Attempt certain "output tasks" even if above failed, in case can still do useful output (hacky)
		try 
		{
			tryOutputTasks(job);
		}
		catch (ScribbleException x)
		{
			if (fail == null)
			{
				fail = x;
			}
		}

		if (fail != null)  // Well-formedness check and/or an "attemptable output task" failed
		{
			throw fail;
		}

		// "Non-attemptable" output tasks
		doNonAttemptableOutputTasks(job);
	}
	
	protected void tryOutputTasks(Job job) throws CommandLineException, ScribbleException
	{
		// Following must be ordered appropriately -- ?
		if (this.args.containsKey(CLArgFlag.PROJECT))
		{
			outputProjections(job);
		}
		if (this.args.containsKey(CLArgFlag.EFSM))
		{
			outputEGraph(job, true, true);
		}
		if (this.args.containsKey(CLArgFlag.VALIDATION_EFSM))
		{
			outputEGraph(job, false, true);
		}
		if (this.args.containsKey(CLArgFlag.UNFAIR_EFSM))
		{
			outputEGraph(job, false, false);
		}
		if (this.args.containsKey(CLArgFlag.EFSM_PNG))
		{
			drawEGraph(job, true, true);
		}
		if (this.args.containsKey(CLArgFlag.VALIDATION_EFSM_PNG))
		{
			drawEGraph(job, false, true);
		}
		if (this.args.containsKey(CLArgFlag.UNFAIR_EFSM_PNG))
		{
			drawEGraph(job, false, false);
		}
		if (this.args.containsKey(CLArgFlag.SGRAPH) || this.args.containsKey(CLArgFlag.SGRAPH_PNG)
				|| this.args.containsKey(CLArgFlag.UNFAIR_SGRAPH) || this.args.containsKey(CLArgFlag.UNFAIR_SGRAPH_PNG))
		{
			if (job.useOldWf)
			{
				throw new CommandLineException("Global model flag(s) incompatible with: "  + CLArgParser.OLD_WF_FLAG);
			}
			if (this.args.containsKey(CLArgFlag.SGRAPH))
			{
				outputSGraph(job, true);
			}
			if (this.args.containsKey(CLArgFlag.UNFAIR_SGRAPH))
			{
				outputSGraph(job, false);
			}
			if (this.args.containsKey(CLArgFlag.SGRAPH_PNG))
			{
				drawSGraph(job, true);
			}
			if (this.args.containsKey(CLArgFlag.UNFAIR_SGRAPH_PNG))
			{
				drawSGraph(job, false);
			}
		}
	}
	
	protected void doNonAttemptableOutputTasks(Job job) throws ScribbleException, CommandLineException
	{
		if (this.args.containsKey(CLArgFlag.SESS_API_GEN))
		{
			outputSessionApi(job);
		}
		if (this.args.containsKey(CLArgFlag.SCHAN_API_GEN))
		{
			outputStateChannelApi(job);
		}
		if (this.args.containsKey(CLArgFlag.API_GEN))
		{
			outputEndpointApi(job);
		}
	}
	
	// FIXME: option to write to file, like classes
	private void outputProjections(Job job) throws CommandLineException, ScribbleException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(CLArgFlag.PROJECT);
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
		String[] args = forUser ? this.args.get(CLArgFlag.EFSM) : (fair ? this.args.get(CLArgFlag.VALIDATION_EFSM) : this.args.get(CLArgFlag.UNFAIR_EFSM));
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			EGraph fsm = getEGraph(job, fullname, role, forUser, fair);
			String out = this.args.containsKey(CLArgFlag.AUT) ? fsm.toAut() : fsm.toDot();
			System.out.println("\n" + out);  // Endpoint graphs are "inlined" (a single graph is built)
		}
	}

	private void drawEGraph(Job job, boolean forUser, boolean fair) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = forUser ? this.args.get(CLArgFlag.EFSM_PNG) : (fair ? this.args.get(CLArgFlag.VALIDATION_EFSM_PNG) : this.args.get(CLArgFlag.UNFAIR_EFSM_PNG));
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
			throw new CommandLineException("Bad FSM construction args: " + Arrays.toString(this.args.get(CLArgFlag.DOT)));
		}
		EGraph graph;
		if (forUser)  // The (possibly minimised) user-output EFSM for API gen
		{
			graph = this.args.containsKey(CLArgFlag.LTSCONVERT_MIN)
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
		String[] args = fair ? this.args.get(CLArgFlag.SGRAPH) : this.args.get(CLArgFlag.UNFAIR_SGRAPH);
		for (int i = 0; i < args.length; i += 1)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			SGraph model = getSGraph(job, fullname, fair);
			String out = this.args.containsKey(CLArgFlag.AUT) ? model.toAut() : model.toDot();
			System.out.println("\n" + out);
		}
	}

	private void drawSGraph(Job job, boolean fair) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = fair ? this.args.get(CLArgFlag.SGRAPH_PNG) : this.args.get(CLArgFlag.UNFAIR_SGRAPH_PNG);
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
		String[] args = this.args.get(CLArgFlag.API_GEN);
		JEndpointApiGenerator jgen = new JEndpointApiGenerator(job);  // FIXME: refactor (generalise -- use new API)
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Map<String, String> sessClasses = jgen.generateSessionApi(fullname);
			outputClasses(sessClasses);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			Map<String, String> scClasses = jgen.generateStateChannelApi(fullname, role, this.args.containsKey(CLArgFlag.SCHAN_API_SUBTYPES));
			outputClasses(scClasses);
		}
	}

	private void outputSessionApi(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(CLArgFlag.SESS_API_GEN);
		JEndpointApiGenerator jgen = new JEndpointApiGenerator(job);  // FIXME: refactor (generalise -- use new API)
		for (String fullname : args)
		{
			GProtocolName gpn = checkGlobalProtocolArg(jcontext, fullname);
			Map<String, String> classes = jgen.generateSessionApi(gpn);
			outputClasses(classes);
		}
	}
	
	private void outputStateChannelApi(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(CLArgFlag.SCHAN_API_GEN);
		JEndpointApiGenerator jgen = new JEndpointApiGenerator(job);  // FIXME: refactor (generalise -- use new API)
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			Map<String, String> classes = jgen.generateStateChannelApi(fullname, role, this.args.containsKey(CLArgFlag.SCHAN_API_SUBTYPES));
			outputClasses(classes);
		}
	}

	// filepath -> class source
	protected void outputClasses(Map<String, String> classes) throws ScribbleException
	{
		Consumer<String> f;
		if (this.args.containsKey(CLArgFlag.API_OUTPUT))
		{
			String dir = this.args.get(CLArgFlag.API_OUTPUT)[0];
			f = path -> { ScribUtil.handleLambdaScribbleException(() ->
							{
								String tmp = dir + "/" + path;
								if (this.args.containsKey(CLArgFlag.VERBOSE))
								{
									System.out.println("[DEBUG] Writing to: " + tmp);
								}
								ScribUtil.writeToFile(tmp, classes.get(path)); return null; 
							}); };
		}
		else
		{
			f = path -> { System.out.println(path + ":\n" + classes.get(path)); };
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
	
	protected static Path parseMainPath(String path)
	{
		return Paths.get(path);
	}
	
	protected static List<Path> parseImportPaths(String paths)
	{
		return Arrays.stream(paths.split(File.pathSeparator)).map((s) -> Paths.get(s)).collect(Collectors.toList());
	}
	
	protected static GProtocolName checkGlobalProtocolArg(JobContext jcontext, String simpname) throws CommandLineException
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

	protected static Role checkRoleArg(JobContext jcontext, GProtocolName fullname, String rolename) throws CommandLineException
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

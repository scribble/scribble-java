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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.codegen.java.JEndpointApiGenerator;
import org.scribble.codegen.java.callbackapi.CBEndpointApiGenerator3;
import org.scribble.core.job.Job;
import org.scribble.core.job.JobArgs;
import org.scribble.core.job.JobContext;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.lang.Lang;
import org.scribble.lang.LangContext;
import org.scribble.main.Main;
import org.scribble.main.resource.locator.DirectoryResourceLocator;
import org.scribble.main.resource.locator.ResourceLocator;
import org.scribble.util.AntlrSourceException;
import org.scribble.util.Pair;
import org.scribble.util.RuntimeScribException;
import org.scribble.util.ScribException;
import org.scribble.util.ScribParserException;
import org.scribble.util.ScribUtil;

/**
 * A Scribble extension should override newCLFlags, newCLArgParser, newMain,
 * parseJobArgs, doValidationTasks, tryNonBarrierTask and tryBarrierTask as
 * appropriate
 * 
 * @author rhu1
 */
public class CommandLine
{
	protected final CLFlags flags;
	protected final List<Pair<String, String[]>> args; 
			// left = CLFlags String constant, right = flag args (if any) -- ordered by parsing order

	public CommandLine(String... args)
	{
		this.flags = newCLFlags();
		try
		{
			this.args = newCLArgParser(this.flags, args).getParsed();
		}
		catch (CommandLineException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
			throw new RuntimeException("Dummy");  // Already exited above
		}
	}
	
	private boolean hasFlag(String flag)
	{
		return this.args.stream().anyMatch(x -> x.left.equals(flag));
	}

	private String[] getUniqueFlagArgs(String flag)
	{
		return this.args.stream().filter(x -> x.left.equals(flag)).findAny()
				.get().right;
	}
	
	protected CLFlags newCLFlags()
	{
		return new CLFlags();
	}

	// A Scribble extension should override as appropriate
	protected CLArgParser newCLArgParser(CLFlags flags, String[] args)
	{
		return new CLArgParser(flags, args);
	}

	// A Scribble extension should override as appropriate
	protected Main newMain() throws ScribParserException, ScribException
	{
		Map<JobArgs, Boolean> args = Collections.unmodifiableMap(parseJobArgs());
		if (hasFlag(CLFlags.INLINE_MAIN_MOD_FLAG))
		{
			String inline = getUniqueFlagArgs(CLFlags.INLINE_MAIN_MOD_FLAG)[0];
			return new Main(inline, args);
		}
		else
		{
			List<Path> impaths = hasFlag(CLFlags.IMPORT_PATH_FLAG)
					? CommandLine
							.parseImportPaths(getUniqueFlagArgs(CLFlags.IMPORT_PATH_FLAG)[0])
					: Collections.emptyList();
			ResourceLocator locator = new DirectoryResourceLocator(impaths);
			Path mainpath = CommandLine
					.parseMainPath(getUniqueFlagArgs(CLFlags.MAIN_MOD_FLAG)[0]);
			return new Main(locator, mainpath, args);
		}
	}
	
	// A Scribble extension should override as appropriate
	protected Map<JobArgs, Boolean> parseJobArgs()
	{
		Map<JobArgs, Boolean> args = new HashMap<>();
		args.put(JobArgs.VERBOSE, hasFlag(CLFlags.VERBOSE_FLAG));
		args.put(JobArgs.OLD_WF, hasFlag(CLFlags.OLD_WF_FLAG));
		args.put(JobArgs.NO_PROGRESS, hasFlag(CLFlags.NO_PROGRESS_FLAG));
		args.put(JobArgs.MIN_EFSM, hasFlag(CLFlags.LTSCONVERT_MIN_FLAG));
		args.put(JobArgs.FAIR, hasFlag(CLFlags.FAIR_FLAG));
		args.put(JobArgs.NO_LCHOICE_SUBJ_CHECK,
				hasFlag(CLFlags.NO_LOCAL_CHOICE_SUBJECT_CHECK_FLAG));
		args.put(JobArgs.NO_ACC_CORRELATION_CHECK,
				hasFlag(CLFlags.NO_ACCEPT_CORRELATION_CHECK_FLAG));
		args.put(JobArgs.NO_VALIDATION, hasFlag(CLFlags.NO_VALIDATION_FLAG));
		args.put(JobArgs.SPIN, hasFlag(CLFlags.SPIN_FLAG));
		return args;
	}

	// AntlrSourceException super of ScribbleException -- needed for, e.g., AssrtCoreSyntaxException
	// A Scribble extension should override as appropriate
	protected void doValidationTasks(Lang lang) 
			throws AntlrSourceException, ScribParserException,  // Latter in case needed by subclasses
				CommandLineException
	{
		lang.runPasses();
		lang.getJob().runPasses();
	}

	// A Scribble extension should override as appropriate
	// TODO: rename, barrier misleading (sounds like a sync)
	protected void tryNonBarrierTask(Lang lang, Pair<String, String[]> task)
			throws CommandLineException, ScribException
	{
		switch (task.left)  // Flag lab (CLFlags String constants)
		{
			case CLFlags.PROJECT_FLAG:
				printProjection(lang, task.right);
				break;
			case CLFlags.EFSM_FLAG:
				outputEGraph(lang, task.right, true, true, false);
				break;
			case CLFlags.VALIDATION_EFSM_FLAG:
				outputEGraph(lang, task.right, false, true, false);
				break;
			case CLFlags.UNFAIR_EFSM_FLAG:
				outputEGraph(lang, task.right, false, false, false);
				break;
			case CLFlags.EFSM_PNG_FLAG:
				outputEGraph(lang, task.right, true, true, true);
				break;
			case CLFlags.VALIDATION_EFSM_PNG_FLAG:
				outputEGraph(lang, task.right, false, true, true);
				break;
			case CLFlags.UNFAIR_EFSM_PNG_FLAG:
				outputEGraph(lang, task.right, false, false, true);
				break;
			case CLFlags.SGRAPH_FLAG:
				outputSGraph(lang, task.right, true, false);
				break;
			case CLFlags.UNFAIR_SGRAPH_FLAG:
				outputSGraph(lang, task.right, false, false);
				break;
			case CLFlags.SGRAPH_PNG_FLAG:
				outputSGraph(lang, task.right, true, true);
				break;
			case CLFlags.UNFAIR_SGRAPH_PNG_FLAG:
				outputSGraph(lang, task.right, false, true);
				break;
			default:
				throw new RuntimeException("Shouldn't get here: " + task.left);
				// Bad flag should be caught by CLArgParser
		}
	}

	// A Scribble extension should override as appropriate
	// TODO: rename, barrier misleading (sounds like a sync)
	protected void tryBarrierTask(Lang lang,
			Pair<String, String[]> task) throws ScribException, CommandLineException
	{
		switch (task.left)
		{
			case CLFlags.SESSION_API_GEN_FLAG:
				outputEndpointApi(lang, task.right, true, false, false);
				break;
			case CLFlags.STATECHAN_API_GEN_FLAG:
				outputEndpointApi(lang, task.right, false, true, false);
				break;
			case CLFlags.API_GEN_FLAG:
				outputEndpointApi(lang, task.right, true, true, false);
				break;
			case CLFlags.EVENTDRIVEN_API_GEN_FLAG:
				outputEndpointApi(lang, task.right, false, true, true);  // FIXME: currently need to gen sess API separately?
				break;
			default:
				throw new RuntimeException("Shouldn't get here: " + task.left);
					// Bad flag should be caught by CLArgParser
		}
	}

	public static void main(String[] args)
			throws CommandLineException, AntlrSourceException
	{
		new CommandLine(args).run();
	}

	public void run() throws CommandLineException, 
			AntlrSourceException  // For JUnit harness (ScribException)
	{
		try
		{
			try
			{
				runTasks();
			}
			catch (ScribException e)  // Wouldn't need to do this if not Runnable (so maybe change)
			{
				if (hasFlag(CLFlags.JUNIT_FLAG)  // JUnit harness looks for an exception
						|| hasFlag(CLFlags.VERBOSE_FLAG))  // Also print full trace for -V
				{
					throw e;
				}
				else
				{
					System.err.println(e.getMessage());
					System.exit(1);
				}
			}
		}
		catch (ScribParserException | CommandLineException e)
		{
			System.err.println(e.getMessage());  // No need to give full stack trace, even for debug, for command line errors
			System.exit(1);
		}
		catch (RuntimeScribException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	protected void runTasks()
			throws AntlrSourceException, ScribParserException, CommandLineException
	{
		Main mc = newMain();  // Represents current instance of tooling for given CL args
		Lang lang = mc.newLang();  // A Job is some series of passes performed on each Module in the MainContext (e.g., cf. Job::runVisitorPass)
		ScribException err = null;
		try { doValidationTasks(lang); } catch (ScribException x) { err = x; }
		for (Pair<String, String[]> a : this.args)
		{
			CLFlag flag = this.flags.explicit.get(a.left);  // null for CLFlags.MAIN_MOD_FLAG
			if (a.left.equals(CLFlags.MAIN_MOD_FLAG) || !flag.enact)
			{
				continue;
			}
			if (!flag.barrier)
			{
				try { tryNonBarrierTask(lang, a); }
				catch (ScribException x) { if (err == null) { err = x; } }
			}
			else
			{
				if (err == null)
				{
					try { tryBarrierTask(lang, a); }
					catch (ScribException x) { err = x; }
				}
			}
		}
		if (err != null)
		{
			throw err;
		}
	}

	// TODO: option to write to file, like API classes
	private void printProjection(Lang lang, String[] args)
			throws CommandLineException, ScribException
	{
		LangContext langc = lang.getContext();
		Job job = lang.getJob();
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(langc, args[i]);
			Role role = checkRoleArg(langc, fullname, args[i+1]);
			Map<LProtocolName, LProtocol> projections = job.getProjections(fullname,
					role);  // FIXME: generate and output Module container -- should be done via Lang?
			System.out.println("\n" + projections.values().stream()
					.map(p -> p.toString()).collect(Collectors.joining("\n\n")));
		}
	}

	// dot/aut text output
	// forUser: true means for API gen and general user info (may be minimised), false means for validation (non-minimised, fair or unfair)
	// (forUser && !fair) should not hold, i.e. unfair doesn't make sense if forUser
	private void outputEGraph(Lang lang, String[] args, boolean forUser,
			boolean fair, boolean draw) throws ScribException, CommandLineException
	{
		LangContext langc = lang.getContext();
		
			GProtocolName fullname = checkGlobalProtocolArg(langc, args[0]);
			Role role = checkRoleArg(langc, fullname, args[1]);
			EGraph fsm = getEGraph(lang, fullname, role, forUser, fair);
			if (draw)
			{
				String png = args[2];
				runDot(fsm.toDot(), png);
			}
			else // print
			{
				String out = hasFlag(CLFlags.AUT_FLAG) 
						? fsm.toAut()
						: fsm.toDot();  // default: dot
				System.out.println("\n" + out);  // Endpoint graphs are "inlined" (a single graph is built)
			}
	}

	private void outputSGraph(Lang lang, String[] args, boolean fair,
			boolean draw) throws ScribException, CommandLineException
	{
		LangContext jobc = lang.getContext();
		{
			GProtocolName fullname = checkGlobalProtocolArg(jobc, args[0]);
			SGraph model = getSGraph(lang, fullname, fair);
			if (draw)
			{
				String png = args[1];
				runDot(model.toDot(), png);
			}
			else // print
			{
				String out = hasFlag(CLFlags.AUT_FLAG) 
						? model.toAut()
						: model.toDot();
				System.out.println("\n" + out);
			}
		}
	}

	private void outputEndpointApi(Lang lang, String[] args, boolean sess,
			boolean schan, boolean cb) throws ScribException, CommandLineException
	{
		LangContext langc = lang.getContext();
		JEndpointApiGenerator jgen = new JEndpointApiGenerator(lang);  // FIXME: refactor (generalise -- use new API)
		{
			GProtocolName fullname = checkGlobalProtocolArg(langc, args[0]);
			if (sess)
			{
				Map<String, String> out = jgen.generateSessionApi(fullname);
				outputClasses(out);
			}
			if (schan)  // CHECKME: does not implicitly generate sess API?
			{
				Role self = checkRoleArg(langc, fullname, args[1]);
				if (cb)
				{
					CBEndpointApiGenerator3 cbgen = new CBEndpointApiGenerator3(lang,
							fullname, self, hasFlag(CLFlags.STATECHAN_SUBTYPES_FLAG));
					Map<String, String> out = cbgen.build();
					outputClasses(out);
				}
				else
				{
					Map<String, String> out = jgen.generateStateChannelApi(fullname,
							self, hasFlag(CLFlags.STATECHAN_SUBTYPES_FLAG));
					outputClasses(out);
				}
			}
		}
	}

  // Endpoint graphs are "inlined", so only a single graph is built (cf. projection output)
	private EGraph getEGraph(Lang lang, GProtocolName fullname, Role role,
			boolean forUser, boolean fair)
			throws ScribException, CommandLineException
	{
		LangContext langc = lang.getContext();
		JobContext jobc = lang.getJob().getContext();
		GProtocolDecl gpd = langc.getMainModule()
				.getGProtocolDeclChild(fullname.getSimpleName());
		if (gpd == null || !gpd.getHeaderChild().getRoleDeclListChild().getRoles()
				.contains(role))
		{
			throw new CommandLineException("Bad FSM construction args: "
					+ gpd + ", " + role);
		}
		EGraph graph;
		if (forUser)  // The (possibly minimised) user-output EFSM for API gen
		{
			graph = hasFlag(CLFlags.LTSCONVERT_MIN_FLAG)
					? jobc.getMinimisedEGraph(fullname, role)
					: jobc.getEGraph(fullname, role);
		}
		else  // The (possibly unfair-transformed) internal EFSM for validation
		{
			graph = //(!this.args.containsKey(ArgFlag.FAIR) && !this.args.containsKey(ArgFlag.NO_LIVENESS))  // Cf. GlobalModelChecker.getEndpointFSMs
					!fair
					? jobc.getUnfairEGraph(fullname, role) 
					: jobc.getEGraph(fullname, role);
		}
		if (graph == null)
		{
			throw new RuntimeScribException("Shouldn't see this: " + fullname);
					// Should be caught by some earlier failure
		}
		return graph;
	}

	private SGraph getSGraph(Lang lang, GProtocolName fullname, boolean fair)
			throws ScribException
	{
		JobContext jobc2 = lang.getJob().getContext();
		SGraph model = fair 
				? jobc2.getSGraph(fullname)
				: jobc2.getUnfairSGraph(fullname);
		if (model == null)
		{
			throw new RuntimeScribException("Shouldn't see this: " + fullname);
					// Should be caught by some earlier failure
		}
		return model;
	}

	// classes: filepath -> class source
	protected void outputClasses(Map<String, String> classes)
			throws ScribException
	{
		Consumer<String> f;
		if (hasFlag(CLFlags.API_OUTPUT_DIR_FLAG))
		{
			String dir = getUniqueFlagArgs(CLFlags.API_OUTPUT_DIR_FLAG)[0];
			f = path -> { ScribUtil.handleLambdaScribbleException(() ->
					{
						String tmp = dir + "/" + path;
						if (hasFlag(CLFlags.VERBOSE_FLAG))
						{
							System.out.println("[DEBUG] Writing to: " + tmp);
						}
						ScribUtil.writeToFile(tmp, classes.get(path));
						return null;
					});
			};
		}
		else
		{
			f = path -> { System.out.println(path + ":\n" + classes.get(path)); };
		}
		classes.keySet().stream().forEach(f);
	}

	protected static void runDot(String dot, String png)
			throws ScribException, CommandLineException
	{
		File tmp = null;
		try
		{
			tmp = File.createTempFile(png, ".tmp");
			String tmpName = tmp.getAbsolutePath();				
			ScribUtil.writeToFile(tmpName, dot);
			String[] res = ScribUtil.runProcess("dot", "-Tpng", "-o" + png, tmpName);
			System.out.print(!res[1].isEmpty() ? res[1] : res[0]);  // already "\n" terminated
		}
		catch (IOException e)
		{
			throw new CommandLineException(e);
		}
		finally
		{
			if (tmp != null)
			{
				tmp.delete();
			}
		}
	}

	protected static Path parseMainPath(String path)
	{
		return Paths.get(path);
	}

	protected static List<Path> parseImportPaths(String paths)
	{
		return Arrays.stream(paths.split(File.pathSeparator)).map(s -> Paths.get(s))
				.collect(Collectors.toList());
	}

	protected static GProtocolName checkGlobalProtocolArg(LangContext langc,
			String simpname) throws CommandLineException
	{
		GProtocolName simpgpn = new GProtocolName(simpname);
		Module main = langc.getMainModule();
		if (!main.hasGProtocolDecl(simpgpn))
		{
			throw new CommandLineException("Global protocol not found: " + simpname);
		}
		ProtocolDecl<?> pd = main.getGProtocolDeclChild(simpgpn);
		if (pd == null || !pd.isGlobal())
		{
			throw new CommandLineException("Global protocol not found: " + simpname);
		}
		if (pd.isAux())  // CHECKME: maybe don't check for all, e.g. -project
		{
			throw new CommandLineException(
					"Invalid aux protocol specified as root: " + simpname);
		}
		return new GProtocolName(langc.lang.config.main, simpgpn);  // TODO: take Job param instead of Jobcontext
	}

	protected static Role checkRoleArg(LangContext langc,
			GProtocolName fullname, String rolename) throws CommandLineException
	{
		ProtocolDecl<?> pd = langc.getMainModule()
				.getGProtocolDeclChild(fullname.getSimpleName());
		Role role = new Role(rolename);
		if (!pd.getHeaderChild().getRoleDeclListChild().getRoles().contains(role))
		{
			throw new CommandLineException(
					"Role not declared for " + fullname + ": " + role);
		}
		return role;
	}
}

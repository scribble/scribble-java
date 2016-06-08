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
import org.scribble.main.MainContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.model.local.EndpointGraph;
import org.scribble.model.wf.WFState;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribParserException;
import org.scribble.util.ScribUtil;
import org.scribble.visit.Job;
import org.scribble.visit.JobContext;

// Maybe no point to be a Runnable
public class CommandLine //implements Runnable
{
	protected enum ArgFlag {
		MAIN,
		PATH,
		PROJECT,
		JUNIT,
		VERBOSE,
		FSM,
		FSM_DOT,
		SESS_API,
		SCHAN_API,
		EP_API,
		API_OUTPUT,
		SCHAN_API_SUBTYPES,
		GLOBAL_MODEL,
		GLOBAL_MODEL_DOT,
		OLD_WF,
		NO_LIVENESS,
		MIN_EFSM,  // Currently only affects EFSM output (i.e. -fsm, -dot) and API gen -- doesn't affect model checking
		FAIR,
		//PROJECTED_MODEL
	}
	
	private final Map<ArgFlag, String[]> args;  // Maps each flag to list of associated argument values
	
	public CommandLine(String... args) throws CommandLineException
	{
		this.args = new CommandLineArgParser(args).getArgs();
		if (!this.args.containsKey(ArgFlag.MAIN))
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
	}

	//@Override
	public void run() throws ScribbleException, CommandLineException, ScribParserException
	{
		try
		{
			Job job = newJob(newMainContext());
			ScribbleException fail = null;
			try
			{
				job.checkWellFormedness();
			}
			catch (ScribbleException x)
			{
				fail = x;
			}
			try 
			{
				// Following must be ordered appropriately
				if (this.args.containsKey(ArgFlag.PROJECT))
				{
					outputProjections(job);
				}
				if (this.args.containsKey(ArgFlag.FSM))
				{
					outputGraph(job);
				}
				if (this.args.containsKey(ArgFlag.FSM_DOT))
				{
					drawGraph(job);
				}
				if (this.args.containsKey(ArgFlag.GLOBAL_MODEL) || this.args.containsKey(ArgFlag.GLOBAL_MODEL_DOT))
				{
					if (job.useOldWf)
					{
						throw new CommandLineException("Incompatible flags: " + CommandLineArgParser.GLOBAL_MODEL_FLAG + " and " + CommandLineArgParser.OLD_WF_FLAG);
					}
					if (this.args.containsKey(ArgFlag.GLOBAL_MODEL))
					{
						outputGlobalModel(job);
					}
					if (this.args.containsKey(ArgFlag.GLOBAL_MODEL_DOT))
					{
						drawGlobalModel(job);
					}
				}
				/*if (this.args.containsKey(ArgFlag.PROJECTED_MODEL))
				{
					outputProjectedModel(job);
				}*/
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

			if (this.args.containsKey(ArgFlag.SESS_API))
			{
				outputSessionApi(job);
			}
			if (this.args.containsKey(ArgFlag.SCHAN_API))
			{
				outputStateChannelApi(job);
			}
			if (this.args.containsKey(ArgFlag.EP_API))
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
	private void outputProjections(Job job) throws CommandLineException
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

	private void outputGraph(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.FSM);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			EndpointGraph fsm = getEndointGraph(job, fullname, role);
			//System.out.println("\n" + jcontext.getEndpointGraph(fullname, role));  // Endpoint graphs are "inlined" (a single graph is built)
			System.out.println("\n" + fsm);  // Endpoint graphs are "inlined" (a single graph is built)
		}
	}

	// FIXME: draw graphs once and cache, redrawing gives different state numbers
	private void drawGraph(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.FSM_DOT);
		for (int i = 0; i < args.length; i += 3)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			String png = args[i+2];
			EndpointGraph fsm = getEndointGraph(job, fullname, role);
			//jcontext.getEndpointGraph(fullname, role);
			runDot(fsm.init.toDot(), png);
		}
	}

	private void outputGlobalModel(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.GLOBAL_MODEL);
		for (int i = 0; i < args.length; i += 1)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			WFState model = getGlobalModel(job, fullname);
			System.out.println("\n" + model.toDot());  // FIXME: make a global equiv to EndpointGraph
		}
	}

	private void drawGlobalModel(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.GLOBAL_MODEL_DOT);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			String png = args[i+1];
			WFState model = getGlobalModel(job, fullname);
			runDot(model.toDot(), png);
		}
	}
	
	private static WFState getGlobalModel(Job job, GProtocolName fullname) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		WFState model = jcontext.getGlobalModel(fullname);
		if (model == null)
		{
			throw new ScribbleException("Shouldn't see this: " + fullname);  // Should be suppressed by an earlier failure
		}
		return model;
	}
	
	/*private void outputProjectedModel(Job job) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.PROJECTED_MODEL);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			System.out.println("\n" + jcontext.getGlobalModel(fullname).project(role));
		}
	}*/

	private void outputSessionApi(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.SESS_API);
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
		String[] args = this.args.get(ArgFlag.SCHAN_API);
		for (int i = 0; i < args.length; i += 2)
		{
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
			Role role = checkRoleArg(jcontext, fullname, args[i+1]);
			Map<String, String> classes = job.generateStateChannelApi(fullname, role, this.args.containsKey(ArgFlag.SCHAN_API_SUBTYPES));
			outputClasses(classes);
		}
	}

	private void outputEndpointApi(Job job) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		String[] args = this.args.get(ArgFlag.EP_API);
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

  // Endpoint graphs are "inlined", so only a single graph is built (cf. projection output)
	private EndpointGraph getEndointGraph(Job job, GProtocolName fullname, Role role) throws ScribbleException, CommandLineException
	{
		JobContext jcontext = job.getContext();
		GProtocolDecl gpd = (GProtocolDecl) jcontext.getMainModule().getProtocolDecl(fullname.getSimpleName());
		if (gpd == null || !gpd.header.roledecls.getRoles().contains(role))
		{
			throw new CommandLineException("Bad FSM construction args: " + Arrays.toString(this.args.get(ArgFlag.FSM)));
		}
		//job.buildGraph(fullname, role);  // Already built (if valid) as part of global model checking
		EndpointGraph fsm = this.args.containsKey(ArgFlag.MIN_EFSM)
				? jcontext.getMinimisedEndpointGraph(fullname, role) : jcontext.getEndpointGraph(fullname, role);
		if (fsm == null)
		{
			throw new ScribbleException("Shouldn't see this: " + fullname);  // Should be suppressed by an earlier failure
		}
		return fsm;
	}
	
	private Job newJob(MainContext mc)
	{
		//Job job = new Job(cjob);  // Doesn't work due to (recursive) maven dependencies
		//return new Job(mc.jUnit, mc.debug, mc.getParsedModules(), mc.main, mc.useOldWF, mc.noLiveness);
		return new Job(mc.debug, mc.getParsedModules(), mc.main, mc.useOldWF, mc.noLiveness, mc.minEfsm, mc.fair);
	}

	private MainContext newMainContext() throws ScribParserException
	{
		//boolean jUnit = this.args.containsKey(ArgFlag.JUNIT);
		boolean debug = this.args.containsKey(ArgFlag.VERBOSE);
		boolean useOldWF = this.args.containsKey(ArgFlag.OLD_WF);
		boolean noLiveness = this.args.containsKey(ArgFlag.NO_LIVENESS);
		boolean minEfsm = this.args.containsKey(ArgFlag.MIN_EFSM);
		boolean fair = this.args.containsKey(ArgFlag.FAIR);

		Path mainpath = CommandLine.parseMainPath(this.args.get(ArgFlag.MAIN)[0]);
		List<Path> impaths = this.args.containsKey(ArgFlag.PATH)
				? CommandLine.parseImportPaths(this.args.get(ArgFlag.PATH)[0])
				: Collections.emptyList();
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		//return new MainContext(jUnit, debug, locator, mainpath, useOldWF, noLiveness);
		return new MainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair);
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
		ProtocolDecl<?> pd = jcontext.getMainModule().getProtocolDecl(simpgpn);
		if (pd == null || !pd.isGlobal())
		{
			throw new CommandLineException("Global protocol not found: " + simpname);
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

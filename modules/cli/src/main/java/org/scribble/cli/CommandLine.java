package org.scribble.cli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;
import org.scribble.visit.Job;
import org.scribble.visit.JobContext;

// Maybe no point to be a Runnable
public class CommandLine implements Runnable
{
	protected enum Arg { MAIN, PATH, PROJECT, VERBOSE, FSM, SESS_API, EP_API, OUTPUT }
	
	private final Map<Arg, String[]> args;  // Maps each flag to list of associated argument values
	
	public CommandLine(String... args)
	{
		this.args = new CommandLineArgParser(args).getArgs();
		if (!this.args.containsKey(Arg.MAIN))
		{
			throw new RuntimeException("No main module has been specified\r\n");
		}
	}

	public static void main(String[] args)
	{
		new CommandLine(args).run();
	}

	@Override
	public void run()
	{
		Job job = newJob(newMainContext());
		try
		{
			job.checkWellFormedness();
			if (this.args.containsKey(Arg.PROJECT))
			{
				outputProjection(job);
			}
			if (this.args.containsKey(Arg.FSM))
			{
				outputFsm(job);
			}
			if (this.args.containsKey(Arg.SESS_API))
			{
				outputSessionApi(job);
			}
			if (this.args.containsKey(Arg.EP_API))
			{
				outputEndpointApi(job);
			}
		}
		catch (ScribbleException e)  // Wouldn't need to do this if not Runnable (so maybe change)
		{
			throw new RuntimeScribbleException(e);
		}
	}
	
	private void outputProjection(Job job)
	{
		JobContext jcontext = job.getContext();
		GProtocolName fullname = checkGlobalProtocolArg(jcontext, this.args.get(Arg.PROJECT)[0]);
		Role role = checkRoleArg(jcontext, fullname, this.args.get(Arg.PROJECT)[1]);
		Module proj = jcontext.getProjection(fullname, role);
		System.out.println(proj);
	}

	private void outputFsm(Job job) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		GProtocolName fullname = checkGlobalProtocolArg(jcontext, this.args.get(Arg.FSM)[0]);
		Role role = checkRoleArg(jcontext, fullname, this.args.get(Arg.FSM)[1]);
		buildEndointGraph(job, fullname, role);
		System.out.println(jcontext.getEndointGraph(fullname, role));
	}
	
	private void outputSessionApi(Job job) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		GProtocolName fullname = checkGlobalProtocolArg(jcontext, this.args.get(Arg.SESS_API)[0]);
		Map<String, String> classes = job.generateSessionApi(fullname);
		outputClasses(classes);
	}

	private void outputEndpointApi(Job job) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		GProtocolName fullname = checkGlobalProtocolArg(jcontext, this.args.get(Arg.EP_API)[0]);
		Role role = checkRoleArg(jcontext, fullname, this.args.get(Arg.EP_API)[1]);
		Map<String, String> classes = job.generateEndpointApi(fullname, role);
		outputClasses(classes);
	}

	// filepath -> class source
	private void outputClasses(Map<String, String> classes) throws ScribbleException
	{
		Consumer<String> f;
		if (this.args.containsKey(Arg.OUTPUT))
		{
			String dir = this.args.get(Arg.OUTPUT)[0];
			f = (path) -> { ScribUtil.handleLambdaScribbleException(() ->
							{
								writeToFile(dir + "/" + path, classes.get(path)); return null; 
							}); };
		}
		else
		{
			f = (path) -> { System.out.println(path + ":\n" + classes.get(path)); };
		}
		classes.keySet().stream().forEach(f);
	}

	private void buildEndointGraph(Job job, GProtocolName fullname, Role role) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		GProtocolDecl gpd = (GProtocolDecl) jcontext.getMainModule().getProtocolDecl(fullname.getSimpleName());
		if (gpd == null || !gpd.header.roledecls.getRoles().contains(role))
		{
			throw new RuntimeException("Bad FSM construction args: " + Arrays.toString(this.args.get(Arg.FSM)));
		}
		job.buildFsms(fullname, role);
	}
	
	private Job newJob(MainContext mc)
	{
		//Job job = new Job(cjob);  // Doesn't work due to (recursive) maven dependencies
		return new Job(mc.debug, mc.getParsedModules(), mc.main);
	}

	private MainContext newMainContext()
	{
		boolean debug = this.args.containsKey(Arg.VERBOSE);
		Path mainpath = CommandLine.parseMainPath(this.args.get(Arg.MAIN)[0]);
		List<Path> impaths = this.args.containsKey(Arg.PATH) ? CommandLine.parseImportPaths(this.args.get(Arg.PATH)[0]) : Collections.emptyList();
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		return new MainContext(debug, locator, mainpath);
	}
	
	private static Path parseMainPath(String path)
	{
		return Paths.get(path);
	}
	
	private static List<Path> parseImportPaths(String paths)
	{
		return Arrays.stream(paths.split(File.pathSeparator)).map((s) -> Paths.get(s)).collect(Collectors.toList());
	}
	
	private static void writeToFile(String file, String text) throws ScribbleException
	{
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8")))
		{
			writer.write(text);
		}
		catch (IOException e)
		{
			throw new ScribbleException(e);
		}
	}
	
	private static GProtocolName checkGlobalProtocolArg(JobContext jcontext, String simpname)
	{
		GProtocolName simpgpn = new GProtocolName(simpname);
		ProtocolDecl<?> pd = jcontext.getMainModule().getProtocolDecl(simpgpn);
		if (pd == null || !pd.isGlobal())
		{
			throw new RuntimeException("Global protocol not found: " + simpname);
		}
		return new GProtocolName(jcontext.main, simpgpn);
	}
	
	private static Role checkRoleArg(JobContext jcontext, GProtocolName fullname, String rolename)
	{
		ProtocolDecl<?> pd = jcontext.getMainModule().getProtocolDecl(fullname.getSimpleName());
		Role role = new Role(rolename);
		if (!pd.header.roledecls.getRoles().contains(role))
		{
			throw new RuntimeException("Role not declared for " + fullname + ": " + role);
		}
		return role;
	}
}

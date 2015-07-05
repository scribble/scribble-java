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
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.main.MainContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Job;
import org.scribble.visit.JobContext;
import org.scribble.visit.Projector;

public class CommandLine implements Runnable
{
	protected enum Arg { MAIN, PATH, PROJECT, VERBOSE, FSM, SESS_API, EP_API, OUTPUT }
	
	private final Map<Arg, String[]> args;  // Maps each flag to list of associated argument values
	
	public CommandLine(String[] args)
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
		catch (ScribbleException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private void outputProjection(Job job)
	{
		JobContext jcontext = job.getContext();
		//Map<LProtocolName, Module> projs = jcontext.getProjections();
		GProtocolName gpn = new GProtocolName(this.args.get(Arg.PROJECT)[0]);
		Role role = new Role(this.args.get(Arg.PROJECT)[1]);
		/*LProtocolName proto = getProjectedName(jcontext, gpn, role);
		if (!projs.containsKey(proto))
		{
			throw new RuntimeException("Bad projection args: " + Arrays.toString(this.args.get(Arg.PROJECT)));
		}
		System.out.println(projs.get(proto));*/
		Module proj = jcontext.getProjection(gpn, role);
		if (proj == null)
		{
			throw new RuntimeException("Bad projection args: " + Arrays.toString(this.args.get(Arg.PROJECT)));
		}
		System.out.println(proj);
	}

	private void outputFsm(Job job) throws ScribbleException
	{
		JobContext jc = job.getContext();
		GProtocolName simpname = new GProtocolName(this.args.get(Arg.FSM)[0]);
		Role role = new Role(this.args.get(Arg.FSM)[1]);
		LProtocolName lpn = getProjectedName(jc, simpname, role);
		buildFsm(job, lpn);
		System.out.println(jc.getFsm(lpn));
		/*buildFsm(job, simplename, role);
		System.out.println(jc.getFsm(simplename, role));*/
	}
	
	private void outputSessionApi(Job job) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		GProtocolName gpn = new GProtocolName(this.args.get(Arg.SESS_API)[0]);
		GProtocolName fullname = new GProtocolName(jcontext.main, gpn);
		Map<String, String> map = job.generateSessionApi(fullname);
		for (String path : map.keySet())
		{
			if (this.args.containsKey(Arg.OUTPUT))
			{
				String dir = this.args.get(Arg.OUTPUT)[0];
				writeToFile(dir + "/" + path, map.get(path));
			}
			else
			{
				System.out.println(path + ":\n" + map.get(path));
			}
		}
	}

	private void outputEndpointApi(Job job) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		GProtocolName gpn = new GProtocolName(this.args.get(Arg.EP_API)[0]);
		Role role = new Role(this.args.get(Arg.EP_API)[1]);
		GProtocolName fullname = new GProtocolName(jcontext.main, gpn);
		Map<String, String> classes = job.generateEndpointApi(fullname, role);
		for (String path : classes.keySet())
		{
			if (this.args.containsKey(Arg.OUTPUT))
			{
				String dir = this.args.get(Arg.OUTPUT)[0];
				writeToFile(dir + "/" + path, classes.get(path));
			}
			else
			{
				System.out.println(path + ":\n" + classes.get(path));
			}
		}
	}
	
	private Job newJob(MainContext mc)
	{
		//Job job = new Job(impaths, mainpath, cjob.getModules(), cjob.getModules().get(cjob.main));
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

	private void buildFsm(Job job, LProtocolName lpn) throws ScribbleException
	//private void buildFsm(Job job, GProtocolName simplename, Role role) throws ScribbleException
	{
		JobContext jcontext = job.getContext();
		ModuleName modname = lpn.getPrefix();
		//ModuleName modname = getProjectedName(jcontext, simplename, role).getPrefix();
		if (!jcontext.hasModule(modname))  // Move into Job?  But this is a check on the CL args
		{
			throw new RuntimeException("Bad FSM construction args: " + Arrays.toString(this.args.get(Arg.FSM)));
		}
		job.buildFsms(jcontext.getModule(modname));  // Need Module for context (not just the LProtoDecl) -- builds FSMs for all locals in the module
	}
	
	private static Path parseMainPath(String path)
	{
		return Paths.get(path);
	}
	
	private static List<Path> parseImportPaths(String paths)
	{
		return Arrays.stream(paths.split(File.pathSeparator)).map((s) -> Paths.get(s)).collect(Collectors.toList());
	}
	
	private static LProtocolName getProjectedName(JobContext jc, GProtocolName simpname, Role role)
	{
		return Projector.makeProjectedFullNameNode(new GProtocolName(jc.main, simpname), role).toName();  // FIXME: factor out name projection from name node construction
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
}

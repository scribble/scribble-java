package org.scribble2.cli;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.scribble.resources.DirectoryResourceLocator;
import org.scribble.resources.Resource;
import org.scribble2.model.visit.Job;
import org.scribble2.util.ScribbleException;

public class CommandLine implements Runnable
{
	public static final String PATH_FLAG = "-path";
	
	enum Arg { PATH, MAIN }//, PROJECT }
	
	private static final ArgumentParser ap = new ArgumentParser();
	
	private final Map<Arg, String> args;

	private DirectoryResourceLocator _locator;
	
	public CommandLine(String[] args)
	{
		this.args = parseArgs(args);
	}
	
	private static Map<Arg, String> parseArgs(String[] args)
	{
		Map<Arg, String> parsed = new HashMap<>();
		for (int i = 0; i < args.length; i++)
		{
			if (!CommandLine.ap.FLAGS.containsKey(args[i]) && parsed.containsKey(Arg.MAIN))
			{
				throw new RuntimeException("Bad: " + args[i]);
			}
			if (CommandLine.ap.FLAGS.containsKey(args[i]))
			{
				if (i >= args.length)
				{
					throw new RuntimeException("Bad: " + args[i]);
				}
				parsed.put(CommandLine.ap.FLAGS.get(args[i]), CommandLine.ap.parse(args[i], args[++i]));
			}
			else
			{
				if (!ArgumentParser.validateModuleName(args[i]))
				{
					throw new RuntimeException("Bad: " + args[i]);
				}
				parsed.put(Arg.MAIN, args[i]);
			}
		}
		return parsed;
	}

	public static void main(String[] args)
	{
		new CommandLine(args).run();
	}

	public void run()
	{
		if (!this.args.containsKey(Arg.PATH))
		{
			throw new RuntimeException("ERROR: No path value has been defined\r\n");
		}
		initLoader(this.args.get(Arg.PATH));
		loadModules(this.args.get(Arg.MAIN));
	}

	protected void initLoader(String paths)
	{
		_locator = new DirectoryResourceLocator(paths);
		//_loader = new ProtocolModuleLoader(PARSER, _locator, LOGGER);
	}

	//protected Resource getResource(String moduleName) {
	protected Resource getResource(String filename) {
		//String relativePath = moduleName.replace('.', java.io.File.separatorChar) + ".scr";
		String relativePath = filename;  // RAY
		return (_locator.getResource(relativePath));
	}

	//protected Module loadModule(Resource resource) {
	protected void loadModules(String main)
	{
		{
			Resource resource = getResource(main);
			if (resource == null)
			{
				System.err.println("ERROR: Module name '" + main + "' could not be located\r\n");
			}

			List<String> impath = new LinkedList<String>();
			String mainpath = resource.getPath(); // Needs relative->full path fix in
																						// DirectoryResourceLocator -- but
																						// maybe Resource should abstract
																						// away from file system? Job could
																						// directly use the encaps
																						// inputstream?
			try
			{
				// FIXME: CLiJob and Job
				CliJob cjob = new CliJob(impath, mainpath);
				Job job = new Job(impath, mainpath, cjob.jcontext.getModules(), cjob.jcontext.getModules().get(cjob.jcontext.main));
				job.checkWellFormedness();

				System.out.println("a: " + cjob.jcontext.main);
			}
			catch (IOException | ScribbleException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("a: " + module.getChildren());
		}
		/*else
		{
		System.err.println("ERROR: Failed to parse '" + resource + "': "
				 + "\r\n");
		}*/

		/*} catch (IOException e) {
			System.err.println("ERROR: Failed to parse '" + resource + "': "
					+ e + "\r\n");
		}*/
	}

	/*/**
	 * This method projects the supplied module.
	 * 
	 * @param module
	 *            The module
	 * @param resource
	 *            The resource
	 * /
	protected void project(Module module, Resource resource) {
		String resourceRoot = _locator.getResourceRoot(resource);

		if (resourceRoot == null) {
			System.err.println("Unable to find root location for resource");
			return;
		}

		ModuleContext context = new DefaultModuleContext(resource, module,
				_loader);

		java.util.Set<Module> modules = PROJECTOR.project(context, module,
				LOGGER);

		for (Module m : modules) {
			String name = m.getName().replace('.', java.io.File.separatorChar);

			String path = resourceRoot + java.io.File.separatorChar + name
					+ ".scr";

			try {
				java.io.FileOutputStream fos = new java.io.FileOutputStream(
						path);

				fos.write(m.toString().getBytes());

				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method validates the trace location. The location can either be a
	 * single trace file, or a folder containing one or more trace files.
	 * 
	 * @param location
	 *            The location
	 * @return Whether the location is valid
	 * /
	protected static boolean validateTraceLocation(java.io.File location) {
		boolean ret = false;

		if (location.exists()) {
			if (location.isFile()) {
				ret = location.getName().endsWith(".trace");

			} else if (location.isDirectory()) {
				for (java.io.File child : location.listFiles()) {
					if (validateTraceLocation(child)) {
						ret = true;
						break;
					}
				}
			}
		}

		return (ret);
	}

	/**
	 * This method recursively scans the supplied location to determine if a
	 * trace file is present, and if found, simulates it.
	 * 
	 * @param location
	 *            The location
	 * @return Whether simulation was successful
	 * /
	protected boolean simulate(java.io.File location) {
		boolean ret = true;

		if (location.exists()) {
			if (location.isFile()) {

				if (location.getName().endsWith(".trace")) {
					System.out.println("\r\nSimulate: " + location.getPath());

					try {
						java.io.InputStream is = new java.io.FileInputStream(
								location);

						Trace trace = MAPPER.readValue(is, Trace.class);

						is.close();

						SimulatorContext context = new DefaultSimulatorContext(
								_locator);

						Simulator simulator = new Simulator();

						final java.util.List<Step> failed = new java.util.ArrayList<Step>();

						SimulationListener l = new SimulationListener() {

							public void start(Trace trace) {
							}

							public void start(Trace trace, Step step) {
							}

							public void successful(Trace trace, Step step) {
								System.out.println("\tSUCCESSFUL: " + step);
							}

							public void failed(Trace trace, Step step) {
								System.out.println("\tFAILED: " + step);
								failed.add(step);
							}

							public void stop(Trace trace) {
							}

						};

						simulator.addSimulationListener(l);

						simulator.simulate(context, trace);

						simulator.removeSimulationListener(l);

						if (failed.size() > 0) {
							ret = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
						ret = false;

					}
				}

			} else if (location.isDirectory()) {
				for (java.io.File child : location.listFiles()) {
					ret = simulate(child);

					if (!ret) {
						break;
					}
				}
			}
		}

		return (ret);
	}*/
}

class ArgumentParser
{
	public final Map<String, CommandLine.Arg> FLAGS;

	public ArgumentParser()
	{
		FLAGS = new HashMap<>();
		FLAGS.put(CommandLine.PATH_FLAG, CommandLine.Arg.PATH);
		//FLAGS.put("-validate", CommandLine.Arg.PATH);
	}

	public String parse(String flag, String m)
	{
		switch (flag)
		{
			case CommandLine.PATH_FLAG:
			{
				if (!validatePaths(m))
				{
					throw new RuntimeException("ERROR: Module path '"+ m +"' is not valid\r\n");
				}
				return parsePath(m);
			}
			default:
			{
				throw new RuntimeException(flag);
			}
		}
	}

	private static boolean validatePaths(String paths)
	{
		for (String path : paths.split(":"))
		{
			File f = new File(path);
			if (!f.isDirectory())
			{
				return false;
			}
		}
		return true;
	}

	protected static boolean validateModuleName(String module)
	{
		for (String part : module.split("."))
		{
			for (int i = 0; i < part.length(); i++)
			{
				if (!Character.isLetterOrDigit(part.charAt(i)))
				{
					if (part.charAt(i) != '_')
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	private String parsePath(String m)
	{
		return m;
	}
}

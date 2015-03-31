/*
 * Copyright 2009-11 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble2.cli;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.scribble.resources.DirectoryResourceLocator;
import org.scribble.resources.Resource;
import org.scribble2.foo.ModuleParser;
import org.scribble2.model.Module;
import org.scribble2.model.visit.Job;
import org.scribble2.util.ScribbleException;

/**
 * This class provides the command line interface for the scribble parser.
 *
 */
public class CommandLine {

	private static final String MODULE_PATH = "MODULE_PATH";

	private static final ModuleParser PARSER = new ModuleParser();
	/*private static final ProtocolValidator VALIDATOR = new ProtocolValidator();
	private static final ProtocolProjector PROJECTOR = new ProtocolProjector();
	private static final IssueLogger LOGGER = new ConsoleIssueLogger();

	private static final ObjectMapper MAPPER = new ObjectMapper();*/

	private DirectoryResourceLocator _locator;
	//private ProtocolModuleLoader _loader;

	/**
	 * This is the main method for the Scribble CLI.
	 * 
	 * @param args
	 *            The arguments
	 */
	public static void main(String[] args) {
		CommandLine cli = new CommandLine();

		if (!cli.execute(args)) {
			System.exit(1);
		}
	}

	/**
	 * This method executes the supplied arguments.
	 * 
	 * @param args
	 *            The arguments
	 * @param Whether
	 *            execution was successful
	 */
	public boolean execute(String[] args) {
		boolean f_usageError = false;
		boolean f_error = false;

		if (args.length > 0) {
			// Find the path
			for (int i = 0; !f_usageError && i < args.length - 1; i++) {
				if (args[i].equals("-path")) {

					if (i >= args.length - 2) {
						System.err
								.println("ERROR: No path value has been defined\r\n");
						f_usageError = true;
					} else {
						i++;

						f_usageError = !validatePaths(args[i]);

						if (!f_usageError) {
							initLoader(args[i]);
						} else {
							System.err.println("ERROR: Module path '" + args[i]
									+ "' is not valid\r\n");
							f_usageError = true;
						}
					}
					break;
				}
			}

			// Check whether a locator has been defined
			if (!f_usageError && _locator == null) {
				if (!System.getenv().containsKey(MODULE_PATH)) {
					System.err
							.println("ERROR: MODULE_PATH has not been defined\r\n");
					f_usageError = true;
				} else if (!validatePaths(System.getenv().get(MODULE_PATH))) {
					System.err.println("ERROR: Module path '"
							+ System.getenv().get(MODULE_PATH)
							+ "' is not valid\r\n");
					f_usageError = true;
				} else {
					initLoader(System.getenv().get(MODULE_PATH));
				}
			}

			// Parse all non-path parameters
			for (int i = 0; !f_usageError && i < args.length - 1; i++) {
				if (args[i].equals("-path")) {
					i++;
				} /*else if (args[i].equals("-project")) {

					if (i + 1 >= args.length) {
						System.err
								.println("ERROR: No global module has been defined\r\n");
						f_usageError = true;
					} else {
						i++;

						if (!validateModuleName(args[i])) {
							System.err.println("ERROR: Module name '" + args[i]
									+ "' is not valid\r\n");
							f_usageError = true;
						} else {

							Resource resource = getResource(args[i]);

							if (resource != null) {
								Module module = loadModule(resource);

								if (module != null) {
									project(module, resource);
								}
							} else {
								System.err.println("ERROR: Module name '"
										+ args[i]
										+ "' could not be located\r\n");
							}
						}
					}

				}*/ else if (args[i].equals("-validate")) {

					if (i + 1 >= args.length) {
						System.err
								.println("ERROR: No module has been defined\r\n");
						f_usageError = true;
					} else {
						i++;

						if (!validateModuleName(args[i])) {
							System.err.println("ERROR: Module name '" + args[i]
									+ "' is not valid\r\n");
							f_usageError = true;
						} else {
							Resource resource = getResource(args[i]);
							if (resource != null) {
								loadModule(resource);
							} else {
								System.err.println("ERROR: Module name '"
										+ args[i]
										+ "' could not be located\r\n");
							}
						}
					}

				}/* else if (args[i].equals("-simulate")) {

					if (i + 1 >= args.length) {
						System.err
								.println("ERROR: No trace file has been defined\r\n");
						f_usageError = true;
					} else {
						i++;

						java.io.File location = new java.io.File(args[i]);

						if (!validateTraceLocation(location)) {
							System.err
									.println("ERROR: No trace files could be found at location '"
											+ args[i] + "'\r\n");
							f_usageError = true;
						} else {
							if (!simulate(location)) {
								System.err
										.println("\r\nERROR: Simulation failed\r\n");
								f_error = true;
							}
						}
					}

				} */else {
					System.err.println("ERROR: Unknown option '" + args[i]
							+ "'\r\n");
					f_usageError = true;
				}
			}

		} else {
			f_usageError = true;
		}

		if (f_usageError) {
			System.err
					//.println("Usage: scribble [-path <module-path>] [ -project <module> ] [ -simulate <trace file/dir> ]");
					.println("Usage: scribble [-path <module-path>] [ -validate <module> ]");
			System.err.println("Options:");
			System.err
					.println("\t-path\t\tList of root directories separated by ':'");
			/*System.err.println("\t-project\tProject global protocols to local");
			System.err
					.println("\t-simulate\tSimulate the supplied trace file or files within a directory");*/
		}

		return (!f_usageError && !f_error);
	}

	/**
	 * This method initializes the loader.
	 * 
	 * @param paths
	 *            The paths
	 */
	protected void initLoader(String paths) {
		_locator = new DirectoryResourceLocator(paths);
		//_loader = new ProtocolModuleLoader(PARSER, _locator, LOGGER);
	}

	/**
	 * This method returns the resource associated with the supplied module
	 * name.
	 * 
	 * @param moduleName
	 *            The module name
	 * @return The resource, or null if not found
	 */
	protected Resource getResource(String moduleName) {
		//String relativePath = moduleName.replace('.', java.io.File.separatorChar) + ".scr";
		String relativePath = moduleName;  // RAY
		return (_locator.getResource(relativePath));
	}

	/**
	 * This method determines whether the module associated with the supplied
	 * resource is valid.
	 * 
	 * @param resource
	 *            The resource
	 * @return The module name, if valid, otherwise null
	 */
	protected Module loadModule(Resource resource) {
		Module module = null;
		//CommonTree module = null;

		//try {
			//module = PARSER.parse(resource, _loader, LOGGER);
			//module = PARSER.parse(resource);

			//module = new AntlrModuleParser().parseModuleFromSource("../validation/src/test/scrib/src/Test.scr");
			//module = new AntlrModuleParser().parseModuleFromSource("modules/validation/src/test/scrib/src/Test.scr");
			//module = new AntlrModuleParser().parseModuleFromSource("Test.scr");

			//if (module != null) 
		{

				/*ModuleContext context =
						new DefaultModuleContext(resource, module, _loader);

				VALIDATOR.validate(context, module, LOGGER);*/
				
				List<String> impath = new LinkedList<String>();
				//String mainpath = "../validation/src/test/scrib/src/Test.scr";
				String mainpath = resource.getPath();  // Needs relative->full path fix in DirectoryResourceLocator -- but maybe Resource should abstract away from file system? Job could directly use the encaps inputstream?
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
				
				//System.out.println("a: " + module.getChildren());
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

		//return (module);
		return null;
	}

	/**
	 * This method validates the module name.
	 * 
	 * @param module
	 *            The module name
	 * @return Whether the module name is valid
	 */
	protected static boolean validateModuleName(String module) {

		for (String part : module.split(".")) {

			for (int i = 0; i < part.length(); i++) {
				if (!Character.isLetterOrDigit(part.charAt(i))) {
					if (part.charAt(i) != '_') {
						return (false);
					}
				}
			}
		}

		return (true);
	}

	/**
	 * This method validates the scribble path.
	 * 
	 * @param paths
	 *            The scribble path
	 * @return Whether the path is valid
	 */
	protected static boolean validatePaths(String paths) {
		for (String path : paths.split(":")) {

			java.io.File f = new java.io.File(path);

			if (!f.isDirectory()) {
				return (false);
			}
		}

		return (true);
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

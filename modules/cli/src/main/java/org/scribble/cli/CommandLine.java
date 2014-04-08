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
package org.scribble.cli;

import java.io.IOException;

import org.scribble.context.DefaultModuleContext;
import org.scribble.context.ModuleContext;
import org.scribble.logging.ConsoleIssueLogger;
import org.scribble.logging.IssueLogger;
import org.scribble.model.Module;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;
import org.scribble.projection.ProtocolProjector;
import org.scribble.resources.DirectoryResourceLocator;
import org.scribble.resources.Resource;
import org.scribble.validation.ProtocolValidator;

/**
 * This class provides the command line interface for the
 * scribble parser.
 *
 */
public class CommandLine {
	
	private static final String MODULE_PATH = "MODULE_PATH";
	
	private static final ProtocolParser PARSER=new ProtocolParser();
	private static final ProtocolValidator VALIDATOR=new ProtocolValidator();
	private static final ProtocolProjector PROJECTOR=new ProtocolProjector();
	
	private static final IssueLogger LOGGER=new ConsoleIssueLogger();
	
	private DirectoryResourceLocator _locator;
	private ProtocolModuleLoader _loader;
	
	/**
	 * This is the main method for the Scribble CLI.
	 * 
	 * @param args The arguments
	 */
	public static void main(String[] args) {
		CommandLine cli=new CommandLine();
		
		cli.execute(args);
	}
	
	/**
	 * This method executes the supplied arguments.
	 * 
	 * @param args The arguments
	 * @param Whether execution was successful
	 */
	public boolean execute(String[] args) {
		boolean f_error=false;
		
		if (args.length > 0) {
			// Find the path
			for (int i=0; !f_error && i < args.length-1; i++) {
				if (args[i].equals("-path")) {
					
					if (i >= args.length-2) {
						System.err.println("ERROR: No path value has been defined\r\n");
						f_error = true;
					} else {
						i++;
						
						f_error = !validatePaths(args[i]);
						
						if (!f_error) {
							initLoader(args[i]);
						} else {
							System.err.println("ERROR: Module path '"+args[i]+"' is not valid\r\n");
							f_error = true;
						}
					}
					break;
				}
			}
			
			// Check whether a locator has been defined
			if (!f_error && _locator == null) {
				if (!System.getenv().containsKey(MODULE_PATH)) {
					System.err.println("ERROR: MODULE_PATH has not been defined\r\n");
					f_error = true;
				} else if (!validatePaths(System.getenv().get(MODULE_PATH))) {
					System.err.println("ERROR: Module path '"+System.getenv().get(MODULE_PATH)+"' is not valid\r\n");
					f_error = true;
				} else {
					initLoader(System.getenv().get(MODULE_PATH));
				}
			}
			
			// Parse all non-path parameters
			for (int i=0; !f_error && i < args.length-1; i++) {
				if (args[i].equals("-path")) {					
					i++;
				} else if (args[i].equals("-project")) {
					
					if (i+1 >= args.length) {
						System.err.println("ERROR: No global module has been defined\r\n");
						f_error = true;
					} else {
						i++;
						
						if (!validateModuleName(args[i])) {
							System.err.println("ERROR: Module name '"+args[i]+"' is not valid\r\n");
							f_error = true;
						} else {
							
							Resource resource=getResource(args[i]);
							
							if (resource != null) {
								Module module=loadModule(resource);
								
								if (module != null) {
									project(module, resource);
								}
							} else {
								System.err.println("ERROR: Module name '"+args[i]
										+"' could not be located\r\n");
							}
						}
					}
					
				} else if (args[i].equals("-simulate")) {
					
					if (i+1 >= args.length) {
						System.err.println("ERROR: No trace file has been defined\r\n");
						f_error = true;
					} else {
						
					}

				} else {
					System.err.println("ERROR: Unknown option '"+args[i]+"'\r\n");
					f_error = true;
				}
			}

		} else {
			f_error = true;
		}
		
		if (f_error) {
			System.err.println("Usage: scribble [-path <module-path>] [ -project module ] [ -simulate trace ]");
			System.err.println("Options:");
			System.err.println("\t-path\t\tList of root directories separated by ':'");
			System.err.println("\t-project\tProject global protocols to local");
			System.err.println("\t-simulate\tSimulate the supplied trace file");
		}
		
		return (!f_error);
	}
	
	/**
	 * This method initializes the loader.
	 * 
	 * @param paths The paths
	 */
	protected void initLoader(String paths) {
		_locator = new DirectoryResourceLocator(paths);
		_loader = new ProtocolModuleLoader(PARSER, _locator, LOGGER);
	}
	
	/**
	 * This method returns the resource associated with the supplied
	 * module name.
	 * 
	 * @param moduleName The module name
	 * @return The resource, or null if not found
	 */
	protected Resource getResource(String moduleName) {	
		String relativePath=moduleName.replace('.', java.io.File.separatorChar)+".scr";
		
		return (_locator.getResource(relativePath));
	}
	
	/**
	 * This method determines whether the module associated with the
	 * supplied resource is valid.
	 * 
	 * @param resource The resource
	 * @return The module name, if valid, otherwise null
	 */
	protected Module loadModule(Resource resource) {	
		Module module=null;
		
		try {
			module = PARSER.parse(resource, _loader, LOGGER);
			
			if (module != null) {
				
				ModuleContext context=new DefaultModuleContext(resource, module, _loader);
				
				VALIDATOR.validate(context, module, LOGGER);
			}
			
		} catch (IOException e) {
			System.err.println("ERROR: Failed to parse '"+resource+"': "+e+"\r\n");
		}
		
		return (module);
	}
	
	/**
	 * This method validates the module name.
	 * 
	 * @param module The module name
	 * @return Whether the module name is valid
	 */
	protected static boolean validateModuleName(String module) {
			
		for (String part : module.split(".")) {
			
			for (int i=0; i < part.length();i++) {
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
	 * @param paths The scribble path
	 * @return Whether the path is valid
	 */
	protected static boolean validatePaths(String paths) {
		for (String path : paths.split(":")) {

			java.io.File f=new java.io.File(path);
			
			if (!f.isDirectory()) {
				return (false);
			}
		}
		
		return (true);
	}
	
	/**
	 * This method projects the supplied module.
	 * 
	 * @param module The module
	 * @param resource The resource
	 */
	protected void project(Module module, Resource resource) {
		String resourceRoot=_locator.getResourceRoot(resource);
		
		if (resourceRoot == null) {
			System.err.println("Unable to find root location for resource");
			return;
		}
		
		ModuleContext context=new DefaultModuleContext(resource, module, _loader);
		
		java.util.Set<Module> modules=PROJECTOR.project(context, module, LOGGER);
		
		for (Module m : modules) {
			String path=resourceRoot+java.io.File.separatorChar
						+m.getName().replaceAll("\\.",java.io.File.separator)
						+"@"+m.getLocatedRole().getName()+".scr";
			
			try {
				java.io.FileOutputStream fos=new java.io.FileOutputStream(path);
				
				fos.write(m.toString().getBytes());
				
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

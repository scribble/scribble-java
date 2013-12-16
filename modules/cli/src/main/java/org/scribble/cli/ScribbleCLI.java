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

import org.scribble.common.logging.ConsoleScribbleLogger;
import org.scribble.common.logging.ScribbleLogger;
import org.scribble.common.resources.DirectoryResourceLocator;
import org.scribble.common.resources.Resource;
import org.scribble.common.resources.ResourceLocator;
import org.scribble.model.Module;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;

/**
 * This class provides the command line interface for the
 * scribble parser.
 *
 */
public class ScribbleCLI {
	
	private static final String SCRIBBLE_PATH = "SCRIBBLE_PATH";
	
	private ResourceLocator _locator=null;
	private Module _module=null;
	
	/**
	 * This is the main method for the Scribble CLI.
	 * 
	 * @param args The arguments
	 */
	public static void main(String[] args) {
		ScribbleCLI cli=new ScribbleCLI();
		
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
			// Parse the arguments
			for (int i=0; !f_error && i < args.length-1; i++) {
				if (args[i].equals("-path")) {
					
					if (i >= args.length-2) {
						System.err.println("ERROR: No path value has been defined\r\n");
						f_error = true;
					} else {
						i++;
						
						f_error = !validatePaths(args[i]);
						
						if (!f_error) {
							_locator = new DirectoryResourceLocator(args[i]);
						} else {
							System.err.println("ERROR: Scribble path '"+args[i]+"' is not valid\r\n");
							f_error = true;
						}
					}
				} else if (args[i].equals("-project")) {
				
				} else {
					System.err.println("ERROR: Unknown option '"+args[i]+"'\r\n");
					f_error = true;
				}
			}
			
			// Check whether a locator has been defined
			if (!f_error && _locator == null) {
				if (!System.getenv().containsKey(SCRIBBLE_PATH)) {
					System.err.println("ERROR: SCRIBBLE_PATH has not been defined\r\n");
					f_error = true;
				} else if (!validatePaths(System.getenv().get(SCRIBBLE_PATH))) {
					System.err.println("ERROR: Scribble path '"+System.getenv().get(SCRIBBLE_PATH)+"' is not valid\r\n");
					f_error = true;
				} else {
					_locator = new DirectoryResourceLocator(System.getenv().get(SCRIBBLE_PATH));
				}
			}
				
			if (!f_error) {
				// Check if last argument is a valid module name
				String module=args[args.length-1];
				
				if (validateModule(module)) {
					ProtocolParser pp=new ProtocolParser();
					
					Resource resource=_locator.getResource(module);

					if (resource != null) {
						try {
							ScribbleLogger logger=new ConsoleScribbleLogger();
							 
							ProtocolModuleLoader loader=new ProtocolModuleLoader(pp, _locator, logger);
							
							_module = pp.parse(resource, loader, logger);
	
						} catch (IOException e) {
							System.err.println("ERROR: Failed to parse '"+module+"': "+e+"\r\n");
						}
					} else {
						System.err.println("ERROR: Module name '"+module+"' could not be located\r\n");
						f_error = true;
					}
					
				} else {
					System.err.println("ERROR: Module name '"+module+"' is not valid\r\n");
					f_error = true;
				}
			}

		} else {
			f_error = true;
		}
		
		if (f_error) {
			System.err.println("Usage: scribble [-path <scribble-path>] [-project] module");
			System.err.println("Options:");
			System.err.println("\t-path\t\tlist of root directories separated by ':'");
			System.err.println("\t-project\tProject global protocols to local");
		}
		
		return (!f_error);
	}
	
	/**
	 * This method validates the module path.
	 * 
	 * @param module The module path
	 * @return Whether the module path is valid
	 */
	protected static boolean validateModule(String module) {
			
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
}

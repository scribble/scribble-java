/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.editor.tools.simulation;

import java.io.File;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.IFile;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.scribble.editor.tools.osgi.Activator;
import org.scribble.trace.simulation.junit.JUnitSimulator;

/**
 * This class is responsible for launching a scenario test against
 * a test scenario.
 */
public class SimulationLauncher
			extends AbstractJavaLaunchConfigurationDelegate {
	
	private static final String JUNIT_RESULT_EDITOR = "org.eclipse.jdt.junit.JUnitResultEditor";

	private static Logger logger = Logger.getLogger(SimulationLauncher.class.getName());

	private String _xmlFile=null;
	
	/**
	 * This is the default constructor.
	 *
	 */
	public SimulationLauncher() {
	}
	
	/**
	 * This method launches the scenario test.
	 * 
	 * @param configuration The launch configuration
	 * @param mode The mode (run or debug)
	 * @param launch The launch object
	 * @param monitor The optional progress monitor
	 */
	public void launch(ILaunchConfiguration configuration,
            String mode, ILaunch launch, IProgressMonitor monitor)
						throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		
		monitor.beginTask(MessageFormat.format("{0}...", new Object[]{configuration.getName()}), 3); //$NON-NLS-1$
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		
		monitor.subTask("Verifying launch configuration....");
						
		String mainTypeName = JUnitSimulatorMain.class.getName(); 

		IVMInstall vm = verifyVMInstall(configuration);

		IVMRunner runner = vm.getVMRunner(mode);
		if (runner == null) {
			abort("VM runner does not exist",
					null, IJavaLaunchConfigurationConstants.ERR_VM_RUNNER_DOES_NOT_EXIST); //$NON-NLS-1$
		}

		File workingDir = verifyWorkingDirectory(configuration);
		String workingDirName = null;
		if (workingDir != null) {
			workingDirName = workingDir.getAbsolutePath();
		}
		
		// Environment variables
		String[] envp= DebugPlugin.getDefault().getLaunchManager().getEnvironment(configuration);
		
		String projname = configuration.getAttribute(
				SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		
		IProject project=
				ResourcesPlugin.getWorkspace().getRoot().getProject(projname);
		
		// Program & VM args		
		String simulationPath=configuration.getAttribute(SimulationLaunchConfigurationConstants.ATTR_PATH,
					"");
		
		IResource res=project.findMember(simulationPath);
		
		_xmlFile = project.getLocation().toOSString()+java.io.File.separator+".trace"
					+java.io.File.separator+"results"+System.currentTimeMillis()+".xml";
		
		// Initialize the file
		try {
			JUnitSimulator.initResultsFile(new java.io.File(_xmlFile));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to initialize simulator trace", e);
		}
		
		String pgmArgs="\""+res.getLocation().toOSString()+"\" \""+_xmlFile+"\"";

		logger.fine("Launching scenario test with args: "+pgmArgs);
			
		String vmArgs = getVMArguments(configuration);		
		vmArgs += " -DMODULE_PATH="+project.getLocation().toOSString();
		
		ExecutionArguments execArgs = new ExecutionArguments(vmArgs, pgmArgs);
		
		// VM-specific attributes
		Map<String,Object> vmAttributesMap = getVMSpecificAttributesMap(configuration);
		
		// Classpath
		String[] classpath = getClasspath(configuration);
		
		// Create VM config
		VMRunnerConfiguration runConfig = new VMRunnerConfiguration(mainTypeName, classpath);
		runConfig.setProgramArguments(execArgs.getProgramArgumentsArray());
		runConfig.setEnvironment(envp);
		runConfig.setVMArguments(execArgs.getVMArgumentsArray());
		runConfig.setWorkingDirectory(workingDirName);
		runConfig.setVMSpecificAttributesMap(vmAttributesMap);

		// Bootpath
		runConfig.setBootClassPath(getBootpath(configuration));
				
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}		
		
		// stop in main
		prepareStopInMain(configuration);
		
		// done the verification phase
		monitor.worked(1);
		
		// Launch the configuration - 1 unit of work
		runner.run(runConfig, launch, monitor);
		
		IProcess[] processes=launch.getProcesses();
		if (processes.length > 0) {
			final IFile file=ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new org.eclipse.core.runtime.Path(_xmlFile));
			
			@SuppressWarnings("restriction")
			Display display=org.eclipse.ui.internal.Workbench.getInstance().getDisplay();
			
			while (!processes[0].isTerminated()) {
				try {
					synchronized (this) {
						wait(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			display.syncExec(new Runnable() {
				@SuppressWarnings("restriction")
				public void run() {
					try {
						file.refreshLocal(0, null);
						
						org.eclipse.ui.internal.Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().
								openEditor(new FileEditorInput(file),
								JUNIT_RESULT_EDITOR);
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			});
			
			file.delete(true, null);
 		}
		
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}	
		
		monitor.done();
	}
	
	/**
	 * This method derives the classpath required to run the 
	 * ScenarioTester utility.
	 * 
	 * @param configuration The launch configuation
	 * @return The list of classpath entries
	 */
	public String[] getClasspath(ILaunchConfiguration configuration) {
		String[] ret=null;
		java.util.Vector<String> classpathEntries=new java.util.Vector<String>();
					
		Bundle bundle=Platform.getBundle(Activator.PLUGIN_ID);
		
		buildClassPath(bundle, classpathEntries);
		
		ret = new String[classpathEntries.size()];
		classpathEntries.copyInto(ret);
		
		if (logger.isLoggable(Level.FINEST)) {
			logger.finest("Simulation Classpath:");
			for (int i=0; i < ret.length; i++) {
				logger.finest("    ["+i+"] "+ret[i]);
			}
		}
		
		return(ret);
	}
	
	protected void buildClassPath(Bundle bundle, java.util.List<String> entries) {
		java.net.URL installLocation= bundle.getEntry("/");
		java.net.URL local= null;
		try {
			local= Platform.asLocalURL(installLocation);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		String baseLocation = local.getFile();

		try {
			String projectClassPath=getProjectClasspath(baseLocation);
			
			// TODO: If classpath has been set, but the items are not available,
			// then resort to the .classpath file. Issue - how to resolve variables?
			
			String requires = (String)bundle.getHeaders().get(Constants.BUNDLE_CLASSPATH);
			ManifestElement[] elements = ManifestElement.parseHeader(Constants.BUNDLE_CLASSPATH, requires);
			
			for (int i=0; elements != null && i < elements.length; i++) {
				
				String path=baseLocation+elements[i].getValue();
				
				// Check if path is for a Jar and that the
				// file exists - if not see if a classes
				// directory exists
				if (path.endsWith(".jar")) {
					
					if ((new File(path)).exists() == false) {
						String jarPath=null;
						
						// Check if .classpath file exists - may be running in test workbench
						// and need to access local maven repo
						if (projectClassPath != null) {
							jarPath = getJarPath(projectClassPath, elements[i].getValue());
						}
						
						if (jarPath != null) {
							path = jarPath;
						} else {
							if ((new File(baseLocation+"classes")).exists()) {
								path = baseLocation+"classes"+File.separatorChar+elements[i].getValue();
							} else if ((new File(baseLocation+"target"+File.separatorChar+"classes")).exists()) {
								path = baseLocation+"target"+File.separatorChar+"classes"+File.separatorChar+elements[i].getValue();
							} else if ((new File(baseLocation+"bin")).exists()) {
								path = baseLocation+"bin"+File.separatorChar+elements[i].getValue();
							} else {
								path = baseLocation;
							}
						}
					}
				} else if (elements[i].getValue().equals(".")) {
					if ((new File(baseLocation+"classes")).exists()) {
						path = baseLocation+"classes";
					} else if ((new File(baseLocation+"target"+File.separatorChar+"classes")).exists()) {
						path = baseLocation+"target"+File.separatorChar+"classes";
					} else if ((new File(baseLocation+"bin")).exists()) {
						path = baseLocation+"bin";
					} else {
						path = baseLocation;
					}
				}
				
				if (entries.contains(path) == false) {
					if (logger.isLoggable(Level.FINE)) {
						logger.fine("Adding classpath entry '"+
								path+"'");
					}
					entries.add(path);
					
					if (elements[i].getValue().equals(".")) {
						if ((new File(baseLocation+"classes")).exists()) {
							path = baseLocation+"classes";
							
							entries.add(path);
						}
					}
				}
			}
			
			if (elements == null) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Adding classpath entry '"+
							baseLocation+"'");
				}
				
				if ((new File(baseLocation+"classes")).exists()) {
					entries.add(baseLocation+"classes");
				} else if ((new File(baseLocation+"target"+File.separatorChar+"classes")).exists()) {
					entries.add(baseLocation+"target"+File.separatorChar+"classes");
				} else if ((new File(baseLocation+"bin")).exists()) {
					entries.add(baseLocation+"bin");
				} else {

					entries.add(baseLocation);
				}
			}
			
		} catch(Exception e) {
			logger.severe("Failed to construct classpath: "+e);
			e.printStackTrace();
		}
	}
	
	protected String getProjectClasspath(String baseLocation) {
		String ret=null;
		
		File cppath=new File(baseLocation+".classpath");
		if (cppath.exists()) {
			try {
				java.io.FileInputStream fis=new java.io.FileInputStream(cppath);
				
				byte[] b=new byte[fis.available()];
				
				fis.read(b);
				
				fis.close();
				
				ret = new String(b);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return(ret);
	}
	
	protected String getJarPath(String projectClassPath, String element) {
		String ret=null;
		
		// Extract the jar name (without preceding folders or the file suffix)
		int startindex=element.lastIndexOf('/');
		int endindex=element.lastIndexOf('.');
		
		String jarName=element.substring(startindex+1, endindex);
		
		String locator="/"+jarName+"/";
		
		int index=projectClassPath.indexOf(locator);
		
		if (index != -1) {
			int startpos=index;
			for (; projectClassPath.charAt(startpos) != '"'; startpos--);
			
			int endpos=projectClassPath.indexOf('"', index);
			
			String newpath=projectClassPath.substring(startpos+1, endpos);
			
			ret=newpath.replaceAll("M2_REPO", System.getenv("HOME")+"/.m2/repository");
		}
		
		return(ret);
	}
}
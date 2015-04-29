package org.scribble2.cli;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.scribble.resources.Resource;
import org.scribble2.model.Module;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.util.ScribbleException;

// Immutable (from outside) -- no: projections are added mutably later
// Global "static" context information for a Job
public class CliJobContext
{
	public final List<String> importPath;
	//public final Module main;
	//private Module main;
	//private ModuleName main;
	public final ModuleName main;
	
	//private final CliJob job;
	
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	private final Map<ModuleName, Module> modules = new HashMap<>();
	//private final Map<ModuleName, String> sources = new HashMap<>();
	private final Map<ModuleName, Resource> sources = new HashMap<>();
	
	public CliJobContext(CliJob job, List<String> importPath, String mainpath) throws ScribbleException
	{
		//this.job = job;
		this.importPath = new LinkedList<>(importPath);

		//Module mainmod = job.parser.parseModuleFromSource(mainpath);

		//this.main = mainmod.getFullModuleName();

		//String mainpath = resource.getPath(); // Needs relative->full path fix in DirectoryResourceLocator -- but maybe Resource should abstract away from file system? Job could directly use the encaps inputstream?
		Resource resource = job.getResource(mainpath);
		
		System.out.println("b: " + mainpath + ", " + resource);
		
			if (resource == null)
			{
				System.err.println("ERROR: Module name '" + mainpath + "' could not be located\r\n");
			}
			
			Module mainmod = job.parser.parseModuleFromResource(resource);

		this.main = mainmod.getFullModuleName();

		addModule(resource, mainmod);
		//importModules(mainmod);
	}
	
	/*private void importModules(Module module) throws ScribbleException
	{
		for (ImportDecl id : module.imports)
		{
			if (id instanceof ImportModule)
			{
				ModuleName modname = ((ImportModule) id).modname.toName();
				if (!this.modules.keySet().contains(modname))
				{
					Pair<String, Module> imported;
					/*if (this.projections2.containsKey(fmn.toName()))
					{
						imported = this.projections2.get(fmn.toName());
					}
					else* /
					{
						imported = this.job.parser.importModule(this.importPath, modname.toPath());
						addModule(imported.left, imported.right);
					}
					importModules(imported.right);
				}
			}
		}
	}*/

	// For modules parsed from source (not generated projections)
	//private void addModule(String source, Module module)
	private void addModule(Resource source, Module module)
	//private void addModule(Module module)
	{
		ModuleName fullmodname = module.getFullModuleName();
		this.modules.put(fullmodname, module);
		this.sources.put(fullmodname, source);
	}
	
	/*// Used by Job for pass running, include projections?
	public Set<ModuleName> getFullModuleNames()
	{
		Set<ModuleName> modnames = new HashSet<>();
		modnames.addAll(this.modules.keySet());
		//modnames.addAll(this.projectionsByModules.keySet());
		return modnames;
	}*/
	
	/*public Module getMainModule()
	{
		return getModule(this.main);
	}*/
	
	public Map<ModuleName, Module> getModules()
	{
		return this.modules;
	}
}

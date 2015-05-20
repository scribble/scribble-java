package org.scribble2.model.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.Module;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.Role;

// Immutable (from outside) -- no: projections are added mutably later -- also replaceModule not immutable
// Global "static" context information for a Job
public class JobContext
{
	//public final List<String> importPath;  // Not needed
	//public final Module main;
	//private Module main;
	//private ModuleName main;
	public final ModuleName main;
	
	//private final Job job;
	
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	private final Map<ModuleName, Module> parsed;// = new HashMap<>();
	//private final Map<ModuleName, String> sources = new HashMap<>();
	
	/*// ProtocolName is the full global protocol name
	private final Map<ProtocolName, Map<Role, Module>> projections;// = new HashMap<>();
	private final Map<ModuleName, Module> projections2;// = new HashMap<>();  // FIXME: factor with above*/
	
	// ProtocolName is the full local protocol name
	// FIXME: collapse to one Map (modulename is part of protocol name?) -- debug print after projection to check
	private final Map<LProtocolName, Module> projected = new HashMap<>();
	private final Map<ModuleName, Module> projectionsByModules = new HashMap<>();  // An alternative view of projections

	//public JobContext(Job job, List<String> importPath, String mainpath) throws ScribbleException
	//public JobContext(Job job, List<String> importPath, String mainpath, Map<ModuleName, Module> modules, Module mainmod) throws ScribbleException
	//public JobContext(Job job, List<Path> importPath, Path mainpath, Map<ModuleName, Module> modules, Module mainmod) throws ScribbleException
	public JobContext(Map<ModuleName, Module> parsed, ModuleName main)
	{
		// FIXME: mainpath not used
		
		//this.job = job;
		//this.importPath = new LinkedList<>(importPath);

		//Module mainmod = job.parser.parseModuleFromSource(mainpath);
		//Module mainmod = job.parser.parseModuleFromSource("../validation/src/test/scrib/src/Test.scr");

		this.parsed = new HashMap<ModuleName, Module>(parsed);
		this.main = main;

		//addModule(mainpath, mainmod);
		//importModules(mainmod);
	}
	
	/*// For projection reachability checking (FIXME: factor with above)
	public JobEnv(List<String> importPath, Module module, JobEnv job) throws IOException,
			RecognitionException
	{
		this.projections = job.projections;
		this.projections2 = job.projections2;

		this.importPath = new LinkedList<>(importPath);
		this.main = module;
		loadModuleAndImportedModules(this.main);
	}*/
	
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

	/*// For modules parsed from source (not generated projections)
	private void addModule(String source, Module module)
	//private void addModule(Module module)
	{
		ModuleName fullmodname = module.getFullModuleName();
		this.modules.put(fullmodname, module);
		this.sources.put(fullmodname, source);
	}*/
	
	// Used by Job for pass running, include projections?
	public Set<ModuleName> getFullModuleNames()
	{
		Set<ModuleName> modnames = new HashSet<>();
		modnames.addAll(this.parsed.keySet());
		modnames.addAll(this.projectionsByModules.keySet());
		return modnames;
	}

	public boolean hasModule(ModuleName fullmodname)
	{
		return (this.parsed.containsKey(fullmodname) || this.projectionsByModules.containsKey(fullmodname));
	}

	public Module getModule(ModuleName fullmodname)
	{
		if (this.parsed.containsKey(fullmodname))
		{
			return this.parsed.get(fullmodname);
		}
		if (this.projectionsByModules.containsKey(fullmodname))
		{
			return this.projectionsByModules.get(fullmodname);
		}
		//throw new RuntimeException("Unknown module: " + fullmodname);
		throw new RuntimeException("Unknown module: " + fullmodname + ", " + this.parsed.keySet() + ", " + this.projectionsByModules.keySet());
	}

	//protected void replaceModule(ModuleName fullmodname, Module module)
	protected void replaceModule(Module module)
	{
		ModuleName fullmodname = module.getFullModuleName(); 
		if (this.parsed.containsKey(fullmodname))
		{
			this.parsed.put(fullmodname, module);
		}
		else if (this.projectionsByModules.containsKey(fullmodname)) 
		{
			addProjection(module);
		}
		else
		{
			throw new RuntimeException("Unknown module: " + fullmodname);
		}
	}
	
	// FIXME: make immutable (will need to assign updated context back to Job) -- will also need to do for Module replacing
	//public void addProjections(Map<ProtocolName, Map<Role, Module>> projections)
	public void addProjections(Map<GProtocolName, Map<Role, Module>> projections)
	{
		for (GProtocolName gpn : projections.keySet())
		{
			Map<Role, Module> mods = projections.get(gpn);
			for (Role role : mods.keySet())
			{
				addProjection(mods.get(role));
			}
		}

		/*// Doesn't work for external subprotocols now that Projector doesn't record Module-specific dependencies itself
		try
		{
			ContextBuilder builder = new ContextBuilder(this.job);
			for (ProtocolName lpn : this.projections.keySet())
			{
				Module mod = this.projections.get(lpn);
				mod = (Module) mod.accept(builder);
				replaceModule(mod);
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}*/
	}
	
	/*// To be done as a barrier pass after projection done on all Modules
	protected void buildProjectionContexts()
	{
		try
		{
			ContextBuilder builder = new ContextBuilder(this.job);
			for (ProtocolName lpn : this.projections.keySet())
			{
				Module mod = this.projections.get(lpn);
				mod = (Module) mod.accept(builder);
				replaceModule(mod);
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}
	}*/

	private void addProjection(Module mod)
	{
		LProtocolName lpn = (LProtocolName) mod.protos.get(0).getFullProtocolName(mod);
		this.projected.put(lpn, mod);
		this.projectionsByModules.put(mod.getFullModuleName(), mod);
	}
	
	public Map<LProtocolName, Module> getProjections()
	{
		return this.projected;
	}
	
	public Module getMainModule()
	{
		return getModule(this.main);
	}
}

package org.scribble.ast.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.model.local.ScribFsm;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;

// Immutable (from outside) -- no: projections are added mutably later -- also replaceModule not immutable
// Global "static" context information for a Job
public class JobContext
{
	public final ModuleName main;
	
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	private final Map<ModuleName, Module> parsed;// = new HashMap<>();
	
	// LProtocolName is the full local protocol name
	// FIXME: collapse to one Map (modulename is part of protocol name?) -- debug print after projection to check
	private final Map<LProtocolName, Module> projected = new HashMap<>();
	private final Map<ModuleName, Module> projectionsByModuleNames = new HashMap<>();  // An alternative view of projections

	private final Map<LProtocolName, ScribFsm> fsms = new HashMap<>();
	
	public JobContext(Map<ModuleName, Module> parsed, ModuleName main)
	{
		this.parsed = new HashMap<ModuleName, Module>(parsed);
		this.main = main;
	}
	
	// Used by Job for pass running, include projections?
	public Set<ModuleName> getFullModuleNames()
	{
		Set<ModuleName> modnames = new HashSet<>();
		modnames.addAll(this.parsed.keySet());
		modnames.addAll(this.projectionsByModuleNames.keySet());
		return modnames;
	}

	public boolean hasModule(ModuleName fullmodname)
	{
		return (this.parsed.containsKey(fullmodname) || this.projectionsByModuleNames.containsKey(fullmodname));
	}

	public Module getModule(ModuleName fullmodname)
	{
		if (this.parsed.containsKey(fullmodname))
		{
			return this.parsed.get(fullmodname);
		}
		if (this.projectionsByModuleNames.containsKey(fullmodname))
		{
			return this.projectionsByModuleNames.get(fullmodname);
		}
		//throw new RuntimeException("Unknown module: " + fullmodname);
		throw new RuntimeException("Unknown module: " + fullmodname + ", " + this.parsed.keySet() + ", " + this.projectionsByModuleNames.keySet());
	}

	protected void replaceModule(Module module)
	{
		ModuleName fullmodname = module.getFullModuleName(); 
		if (this.parsed.containsKey(fullmodname))
		{
			this.parsed.put(fullmodname, module);
		}
		else if (this.projectionsByModuleNames.containsKey(fullmodname)) 
		{
			addProjection(module);
		}
		else
		{
			throw new RuntimeException("Unknown module: " + fullmodname);
		}
	}
	
	// Make context immutable? (will need to assign updated context back to Job) -- will also need to do for Module replacing
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

	private void addProjection(Module mod)
	{
		LProtocolName lpn = (LProtocolName) mod.protos.get(0).getFullProtocolName(mod);
		this.projected.put(lpn, mod);
		this.projectionsByModuleNames.put(mod.getFullModuleName(), mod);
	}
	
	public Map<LProtocolName, Module> getProjections()
	{
		return this.projected;
	}
	
	public void addFsm(LProtocolName lpn, ScribFsm fsm)
	{
		this.fsms.put(lpn, fsm);
	}
	
	public ScribFsm getFsm(LProtocolName lpn)
	{
		return this.fsms.get(lpn);
	}
	
	public Module getMainModule()
	{
		return getModule(this.main);
	}
}

/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.job;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.util.ScribException;

// Global "static" context information for a Job -- single instance per Job, should not be shared between Jobs
// Mutable: projections, graphs, etc are added mutably later -- replaceModule also mutable setter -- "users" get this from the Job and expect to setter mutate "in place"
public class JobContext
{
	public final Job job;

	//public final ModuleName main;  // The "main" root module from MainContext
	
	// Modules that were originally parsed (obtained from MainContext), but may be modified during the Job
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	// CHECKME: separate original parsed from "working set"? -- store parsed constants in config?
	private final Map<ModuleName, Module> parsed;// = new HashMap<>();

	// Used by, e.g., SimpleAstVisitor (cf. getModuleContext)
	// Keys are full names
	// Currently assuming ModuleContexts are constant, and considering parsed Modules only (i.e., not generated) -- CHECKME
	private final Map<ModuleName, ModuleContext> modcs;  
			// CHECKME: constant?  depends on adding projections?
	
	protected JobContext(Job job, Map<ModuleName, Module> parsed)
			throws ScribException
	{
		this.job = job;
		this.parsed = new HashMap<ModuleName, Module>(parsed);
		this.modcs = Collections.unmodifiableMap(buildModuleContexts(parsed));
	}

	// Currently assuming ModuleContexts are constant, and considering parsed Modules only (i.e., not generated) -- CHECKME
	protected Map<ModuleName, ModuleContext> buildModuleContexts(
			Map<ModuleName, Module> parsed) throws ScribException
	{
		// CHECKME: how does this relate to the ModuleContextBuilder pass?
		// Job.modcs seems unused?  Lang.modcs is used though, by old AST visitors -- basically old ModuleContextVisitor is redundant?
		// Job.modcs could be used, but disamb already done by Lang
		// Lang/Job modcs should be moved to config/context though
		ModuleContextBuilder b = new ModuleContextBuilder();  // TODO: factor out newModuleContextBuilder
		Map<ModuleName, ModuleContext> modcs = new HashMap<>();
		for (ModuleName fullname : parsed.keySet())
		{
			modcs.put(fullname, b.build(parsed, parsed.get(fullname)));  
					// Throws ScribException
		}
		return modcs;
	}

	public Module getMainModule()
	{
		return getModule(this.job.config.main);
	}
	
	// HACK -- CHECKME: separate original parsed from "working set"?
	public Map<ModuleName, Module> getParsed()
	{
		return Collections.unmodifiableMap(this.parsed);
	}

	public GProtoDecl getParsed(GProtoName fullname)
	{
		return this.parsed.get(fullname.getPrefix())
				.getGProtoDeclChildren().stream().filter(x -> x.getHeaderChild()
						.getDeclName().equals(fullname.getSimpleName()))
				.findAny().get();
	}
	
	public Map<ModuleName, ModuleContext> getModuleContexts()
	{
		return this.modcs;
	}

	public ModuleContext getModuleContext(ModuleName fullname)
	{
		return this.modcs.get(fullname);
	}
	
	
	// The following are used for Visitor pass running

	// Safer to get module names and require user to re-fetch the module by the getter each time (after replacing), to make sure the latest is used
	public Set<ModuleName> getFullModuleNames()
	{
		Set<ModuleName> modnames = new HashSet<>();
		modnames.addAll(getParsedFullModuleNames());
		//modnames.addAll(getProjectedFullModuleNames());
		return modnames;
	}

	public Set<ModuleName> getParsedFullModuleNames()
	{
		return Collections.unmodifiableSet(this.parsed.keySet());
	}

	private boolean isParsedModule(ModuleName fullname)
	{
		return this.parsed.containsKey(fullname);
	}

	public Module getModule(ModuleName fullname)
	{
		if (isParsedModule(fullname))
		{
			return this.parsed.get(fullname);
		}
		/*else if (isProjectedModule(fullname))
		{
			return this.projected.get(this.projected.keySet().stream()
					.filter(lpn -> lpn.getPrefix().equals(fullname))
					.collect(Collectors.toList()).get(0));
		}*/
		else
		{
			throw new RuntimeException("Unknown module: " + fullname);
		}
	}

	protected void replaceModule(Module module)
	{
		ModuleName fullname = module.getFullModuleName(); 
		if (isParsedModule(fullname))
		{
			this.parsed.put(fullname, module);
		}
		/*else if (isProjectedModule(fullname))
		{
			addProjection(module);
		}*/
		else
		{
			throw new RuntimeException("Unknown module: " + fullname);
		}
	}
}

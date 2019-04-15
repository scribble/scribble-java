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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.core.job.Core;
import org.scribble.core.job.CoreArgs;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.name.ModuleName;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.NameDisambiguator;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Job
{
	public final JobConfig config;  // Immutable

	private final JobContext context;  // Mutable: Visitor passes update modules
	
	private Core core;

	// Just take MainContext as arg? -- would need to fix Maven dependencies
	//public Job(boolean jUnit, boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness)
	public Job(ModuleName mainFullname, Map<CoreArgs, Boolean> args,
			Map<ModuleName, Module> parsed, AstFactory af) throws ScribException
	{
		// CHECKME(?): main modname comes from the inlined mod decl -- check for issues if this clashes with an existing file system resource
		this.config = newJobConfig(mainFullname, args, af);
		this.context = newJobContext(this, parsed);  // Single instance per Job, should not be shared between Jobs
	}

	// A Scribble extension should override newJobConfig/Context/Translator and toJob as appropriate
	protected JobConfig newJobConfig(ModuleName mainFullname,
			Map<CoreArgs, Boolean> args, AstFactory af)
	{
		return new JobConfig(mainFullname, args, af);
	}

	// A Scribble extension should override newJobConfig/Context/Translator and toJob as appropriate
	protected JobContext newJobContext(Job job,
			Map<ModuleName, Module> parsed) throws ScribException
	{
		return new JobContext(this, parsed);
	}

	// A Scribble extension should override newJobConfig/Context/Translator and toJob as appropriate
	protected GTypeTranslator newTranslator(ModuleName rootFullname)
	{
		return new GTypeTranslator(this, rootFullname);
	}
	
	// A Scribble extension should override newJobConfig/Context/Translator and toJob as appropriate
	protected Core newJob(ModuleName mainFullname, Map<CoreArgs, Boolean> args,
			//Map<ModuleName, ModuleContext> modcs, 
			Set<GProtocol> imeds)
	{
		return new Core(mainFullname, args, //modcs, 
				imeds);
	}
	
	// First run Visitor passes, then call toJob
	// Base implementation: ambigname disamb pass only
	public void runPasses() throws ScribException
	{
		verbosePrintPass("Starting Job passes on:");
		for (ModuleName fullname : this.context.getFullModuleNames())
		{
			verbosePrintln(this.context.getModule(fullname).toString());
		}
		
		// Disamb is a "leftover" aspect of parsing -- so not in core
		// N.B. disamb is mainly w.r.t. ambignames -- e.g., doesn't fully qualify names (currently mainly done by imed translation)
		// CHECKME: disamb also currently does checks like do-arg kind and arity -- refactor into core? -- also does, e.g., distinct roledecls, protodecls, etc.
		runVisitorPassOnAllModules(new NameDisambiguator(this));  // Includes validating names used in subprotocol calls..
	}
	
	// "Finalises" this Job -- initialises the Job at this point, and cannot run futher Visitor passes on Job
	// So, typically, Job passes should be finished before calling this
	// Core passes may subsequently mutate Core(Context) though
	// CHECKME: revise this pattern? -- maybe fork Core for a snapshot of current Job(Context) -- and, possibly, convert Core back to Job
	public final Core getCore() throws ScribException
	{
		if (this.core == null)
		{
			Map<ModuleName, Module> parsed = this.context.getParsed();
			Set<GProtocol> imeds = new HashSet<>();
			for (ModuleName fullname : parsed.keySet())
			{
				GTypeTranslator t = newTranslator(fullname);
				Module m = parsed.get(fullname);
				for (GProtoDecl ast : m.getGProtoDeclChildren())
				{
					GProtocol imed = (GProtocol) ast.visitWith(t);
					imeds.add(imed);
					verbosePrintPass(
							//"\nParsed:\n" + gpd + 
							"Translated Scribble intermediate: " + ast.getFullMemberName(m)
									+ "\n" + imed);
				}
			}
			this.core = newJob(this.config.main, this.config.args,
					//this.context.getModuleContexts(), 
					imeds);
		}
		return this.core;
	}
	
	public JobContext getContext()
	{
		return this.context;
	}

	public void runVisitorPassOnAllModules(AstVisitor v) throws ScribException
	{
		verbosePrintPass("Running " + v.getClass() + " on all modules...");
		runVisitorPass(v, this.context.getFullModuleNames());
	}

	private void runVisitorPass(AstVisitor v, Set<ModuleName> fullnames)
			throws ScribException
	{
		for (ModuleName fullname : fullnames)
		{
			runVisitorOnModule(fullname, v);
		}
	}

	private void runVisitorOnModule(ModuleName modname, AstVisitor v)
			throws ScribException
	{
		if (this.core != null)
		{
			throw new RuntimeException("toJob already finalised: ");
		}
		verbosePrintPass("Running " + v.getClass() + " on: " + modname);
		Module visited = (Module) this.context.getModule(modname).accept(v);
		verbosePrintln(visited.toString());
		this.context.replaceModule(visited);
	}
	
	public boolean isVerbose()
	{
		return this.config.args.get(CoreArgs.VERBOSE);
	}
	
	public void verbosePrintln(String s)
	{
		if (isVerbose())
		{
			System.out.println(s);
		}
	}
	
	private void verbosePrintPass(String s)
	{
		verbosePrintln("\n[Job] " + s);
	}
	
	public void warningPrintln(String s)
	{
		System.err.println("[Warning] " + s);
	}
}

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
package org.scribble.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactory;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.core.job.Job;
import org.scribble.core.job.JobArgs;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.name.ModuleName;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.context.ModuleContextBuilder;
import org.scribble.visit.wf.NameDisambiguator;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Lang
{
	public final LangConfig config;  // Immutable

	private final LangContext context;  // Mutable: Visitor passes update modules
	
	private Job job;

	// Just take MainContext as arg? -- would need to fix Maven dependencies
	//public Job(boolean jUnit, boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness)
	public Lang(ModuleName mainFullname, Map<JobArgs, Boolean> args,
			Map<ModuleName, Module> parsed) throws ScribException
	{
		// CHECKME(?): main modname comes from the inlined mod decl -- check for issues if this clashes with an existing file system resource
		// FIXME: wrap flags in Map and move Config construction to Lang
		this.config = newLangConfig(mainFullname, args);
		this.context = newLangContext(this, parsed);  // Single instance per Lang, should not be shared between Langs
	}

	// A Scribble extension should override newLangConfig/Context/Translator and toJob as appropriate
	protected LangConfig newLangConfig(ModuleName mainFullname,
			Map<JobArgs, Boolean> args)
	{
		AstFactory af = new AstFactoryImpl();
		return new LangConfig(mainFullname, args, af);
	}

	// A Scribble extension should override newLangConfig/Context/Translator and toJob as appropriate
	protected LangContext newLangContext(Lang lang,
			Map<ModuleName, Module> parsed) throws ScribException
	{
		return new LangContext(this, parsed);
	}

	// A Scribble extension should override newLangConfig/Context/Translator and toJob as appropriate
	protected GTypeTranslator newTranslator(ModuleName rootFullname)
	{
		return new GTypeTranslator(this, rootFullname);
	}
	
	// A Scribble extension should override newLangConfig/Context/Translator and toJob as appropriate
	protected Job newJob(ModuleName mainFullname, Map<JobArgs, Boolean> args,
			Map<ModuleName, ModuleContext> modcs, Set<GProtocol> imeds)
	{
		return new Job(mainFullname, args, modcs, imeds);
	}
	
	// First run Visitor passes, then call toJob
	// Base implementation: name disamb pass only
	public void runPasses() throws ScribException
	{
		// CHECKME: in the end, should these also be refactored into core?  (instead of AST visiting)
		runVisitorPassOnAllModules(ModuleContextBuilder.class);  // Always done first (even if other contexts are built later) so that following passes can use ModuleContextVisitor
		runVisitorPassOnAllModules(NameDisambiguator.class);  // Includes validating names used in subprotocol calls..
	}
	
	// "Finalises" this Lang -- initialises the Job at this point, and cannot run futher Visitor passes on Lang
	// So, typically, Lang passes should be finished before calling this
	// Job passes may subsequently mutate Job(Context) though
	// CHECKME: revise this pattern?
	public final Job toJob() throws ScribException
	{
		if (this.job == null)
		{
			Map<ModuleName, Module> parsed = this.context.getParsed();
			Set<GProtocol> imeds = new HashSet<>();
			for (ModuleName fullname : parsed.keySet())
			{
				GTypeTranslator t = newTranslator(fullname);
				for (GProtocolDecl gpd : parsed.get(fullname).getGProtoDeclChildren())
				{
					GProtocol g = (GProtocol) gpd.visitWith(t);
					imeds.add(g);
					debugPrintln(
							"\nParsed:\n" + gpd + "\n\nScribble intermediate:\n" + g);
				}
			}
			this.job = newJob(this.config.main, this.config.args,
					this.context.getModuleContexts(), imeds);
		}
		return this.job;
	}
	
	public LangContext getContext()
	{
		return this.context;
	}

	public void runVisitorPassOnAllModules(Class<? extends AstVisitor> c)
			throws ScribException
	{
		debugPrintPass("Running " + c + " on all modules:");
		runVisitorPass(c, this.context.getFullModuleNames());
	}

	private void runVisitorPass(Class<? extends AstVisitor> c,
			Set<ModuleName> fullnames) throws ScribException
	{
		try
		{
			Constructor<? extends AstVisitor> cons = c.getConstructor(Lang.class);
			for (ModuleName fullname : fullnames)
			{
				AstVisitor nv = cons.newInstance(this);
				runVisitorOnModule(fullname, nv);
			}
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void runVisitorOnModule(ModuleName modname, AstVisitor nv)
			throws ScribException
	{
		if (this.job != null)
		{
			throw new RuntimeException("toJob already finalised: ");
		}
		Module visited = (Module) this.context.getModule(modname).accept(nv);
		this.context.replaceModule(visited);
	}
	
	public void warningPrintln(String s)
	{
		System.err.println("[Warning] " + s);
	}
	
	public void debugPrintln(String s)
	{
		if (this.config.args.get(JobArgs.DEBUG))
		{
			System.out.println(s);
		}
	}
	
	private void debugPrintPass(String s)
	{
		debugPrintln("\n[Lang] " + s);
	}
}

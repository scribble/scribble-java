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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.core.job.Job;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.name.ModuleName;
import org.scribble.lang.context.ModuleContextMaker;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.context.ModuleContextBuilder;
import org.scribble.visit.wf.NameDisambiguator;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Lang
{
	public final LangConfig config;  // Immutable

	// Keys are full names
	private final Map<ModuleName, ModuleContext> modcs;  // CHECKME: constant?  move to JobConfig?

	private final LangContext context;  // Mutable: Visitor passes update modules
	
	private Job job;
	
	// Just take MainContext as arg? -- would need to fix Maven dependencies
	//public Job(boolean jUnit, boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness)
	public Lang(Map<ModuleName, Module> parsed, LangConfig config)
			throws ScribException
	{
		this.config = config;
		this.context = new LangContext(this, parsed);  // Single instance per Lang, should not be shared between Langs

		// CHECKME: how does this relate to the ModuleContextBuilder pass?
		// Job.modcs seems unused?  Lang.modcs is used though, by old AST visitors -- basically old ModuleContextVisitor is redundant?
		// Job.modcs could be used, but disamb already done by Lang
		// Lang/Job modcs should be moved to config/context though
		ModuleContextMaker maker = new ModuleContextMaker();
		Map<ModuleName, ModuleContext> modcs = new HashMap<>();
		for (ModuleName fullname : parsed.keySet())
		{
			modcs.put(fullname, maker.make(parsed, parsed.get(fullname)));  
					// Throws ScribException
		}
		this.modcs = Collections.unmodifiableMap(modcs);
	}
	
	// "Finalises" this Lang -- caches the Job at this point, and cannot run futher Visitor passes
	// CHECKME: revise this pattern?
	public Job toJob() throws ScribException
	{
		if (this.job == null)
		{
			Set<ModuleName> fullnames = this.context.getFullModuleNames();
			Set<GProtocol> imeds = new HashSet<>();
			for (ModuleName fullname : fullnames)
			{
				Module m = this.context.getModule(fullname);
				GTypeTranslator t = new GTypeTranslator(this, fullname);
				for (GProtocolDecl gpd : m.getGProtoDeclChildren())
				{
					GProtocol g = (GProtocol) gpd.visitWith(t);
					imeds.add(g);
					debugPrintln(
							"\nParsed:\n" + gpd + "\n\nScribble intermediate:\n" + g);
				}
			}
			this.job = new Job(imeds, this.modcs, this.config.toJobConfig());
		}
		return this.job;
	}
	
	public void runDisambPasses() throws ScribException
	{
		// CHECKME: in the end, should these also be refactored into core?  (instead of AST visiting)
		runVisitorPassOnAllModules(ModuleContextBuilder.class);  // Always done first (even if other contexts are built later) so that following passes can use ModuleContextVisitor
		runVisitorPassOnAllModules(NameDisambiguator.class);  // Includes validating names used in subprotocol calls..
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
	
	public LangContext getContext()
	{
		return this.context;
	}
	
	public ModuleContext getModuleContext(ModuleName fullname)
	{
		return this.modcs.get(fullname);
	}
	
	public void warningPrintln(String s)
	{
		System.err.println("[Warning] " + s);
	}
	
	public void debugPrintln(String s)
	{
		if (this.config.debug)
		{
			System.out.println(s);
		}
	}
	
	private void debugPrintPass(String s)
	{
		debugPrintln("\n[Lang] " + s);
	}
}

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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.core.job.Job;
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
	// Keys are full names
	private final Map<ModuleName, ModuleContext> modcs;  // CHECKME: constant?  move to JobConfig?

	public final LangConfig config;  // Immutable

	private final LangContext context;  // Mutable (Visitor passes replace modules)
	//private final SGraphBuilderUtil sgbu;
	
	private Job job2;
	
	// Just take MainContext as arg? -- would need to fix Maven dependencies
	//public Job(boolean jUnit, boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness)
	public Lang(Map<ModuleName, Module> parsed,
			Map<ModuleName, ModuleContext> modcs, LangConfig config)
	{
		this.modcs = Collections.unmodifiableMap(modcs);
		this.config = config;
		//this.sgbu = config.sf.newSGraphBuilderUtil();
		this.context = new LangContext(this, parsed);  // Single instance per Job and should never be shared
	}
	
	public Job toJob() throws ScribException
	{
		if (this.job2 == null)
		{
			Set<ModuleName> fullmodnames = this.context.getFullModuleNames();
			Set<GProtocol> imeds = new HashSet<>();
			for (ModuleName fullmodname : fullmodnames)
			{
				Module mod = this.context.getModule(fullmodname);
				GTypeTranslator t = new GTypeTranslator(this, fullmodname);
				for (GProtocolDecl gpd : mod.getGProtoDeclChildren())
				{
					GProtocol g = (GProtocol) gpd.visitWith(t);
					//this.context.addIntermediate(g.fullname, g);
					imeds.add(g);
					debugPrintln("\nParsed:\n" + gpd + "\n\nScribble intermediate:\n" + g);
				}
			}
			this.job2 = new Job(imeds, this.modcs, this.config.toJobConfig());
		}
		return this.job2;
	}
	
	/*// Scribble extensions should override these "new" methods
	// CHECKME: move to MainContext::newJob?
	public EGraphBuilderUtil newEGraphBuilderUtil()
	{
		return new EGraphBuilderUtil(this.config.ef);
	}
	
	//public SGraphBuilderUtil newSGraphBuilderUtil()  // FIXME TODO global builder util
	public SGraph buildSGraph(GProtocolName fullname, Map<Role, EGraph> egraphs,
			boolean explicit) throws ScribbleException
	{
		debugPrintln("(" + fullname + ") Building global model using:");
		for (Role r : egraphs.keySet())
		{
			// FIXME: refactor
			debugPrintln("-- EFSM for "
					+ r + ":\n" + egraphs.get(r).init.toDot());
		}
		//return SGraph.buildSGraph(this, fullname, createInitialSConfig(this, egraphs, explicit));
		return this.sgbu.buildSGraph(this, fullname, egraphs, explicit);  // FIXME: factor out util
	}*/

	/*public void checkWellFormedness() throws ScribbleException
	{
		runContextBuildingPasses();
		//runUnfoldingPass();
		runWellFormednessPasses();
	}*/
	
	public void runContextBuildingPasses() throws ScribException
	{
		// CHECKME: in the end, should these also be refactored into core?  (instead of AST visiting)
		runVisitorPassOnAllModules(ModuleContextBuilder.class);  // Always done first (even if other contexts are built later) so that following passes can use ModuleContextVisitor
		runVisitorPassOnAllModules(NameDisambiguator.class);  // Includes validating names used in subprotocol calls..
	}

	public void runVisitorPassOnAllModules(Class<? extends AstVisitor> c)
			throws ScribException
	{
		debugPrintPass("Running " + c + " on all modules:");
		runVisitorPass(this.context.getFullModuleNames(), c);
	}

	public void runVisitorPassOnParsedModules(Class<? extends AstVisitor> c)
			throws ScribException
	{
		debugPrintPass("Running " + c + " on parsed modules:");
		runVisitorPass(this.context.getParsedFullModuleNames(), c);
	}

	private void runVisitorPass(Set<ModuleName> modnames,
			Class<? extends AstVisitor> c) throws ScribException
	{
		try
		{
			Constructor<? extends AstVisitor> cons = c.getConstructor(Lang.class);
			for (ModuleName modname : modnames)
			{
				AstVisitor nv = cons.newInstance(this);
				runVisitorOnModule(modname, nv);
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
	
	public boolean isDebug()
	{
		return this.config.debug;
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
		debugPrintln("\n[Job] " + s);
	}
}

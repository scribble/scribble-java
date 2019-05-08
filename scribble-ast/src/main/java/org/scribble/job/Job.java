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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactory;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtoDecl;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.ast.local.LProjectionDecl;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.core.job.Core;
import org.scribble.core.job.CoreArgs;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.session.STypeFactory;
import org.scribble.core.type.session.global.GTypeFactoryImpl;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LTypeFactoryImpl;
import org.scribble.core.visit.gather.NonProtoDepsGatherer;
import org.scribble.core.visit.gather.ProtoDepsCollector;
import org.scribble.del.DelFactory;
import org.scribble.util.LProjectionTranslator;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.VisitorFactory;
import org.scribble.visit.VisitorFactoryImpl;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Job
{
	public final JobConfig config;  // Immutable

	private final JobContext context;  // Mutable: Visitor passes update modules
	
	private Core core;

	public Job(ModuleName mainFullname, Map<CoreArgs, Boolean> args,
			Map<ModuleName, Module> parsed, AstFactory af, DelFactory df)
			throws ScribException
	{
		// CHECKME(?): main modname comes from the inlined mod decl -- check for issues if this clashes with an existing file system resource
		VisitorFactory vf = newVisitorFactory();
		STypeFactory tf = newSTypeFactory();
		this.config = newJobConfig(mainFullname, args, af, df, vf, tf);
		this.context = newJobContext(this, parsed);  // Single instance per Job, should not be shared between Jobs
	}

	protected VisitorFactory newVisitorFactory()
	{
		return new VisitorFactoryImpl();
	}
	
	// Used by GTypeTranslator (cf. getCore)
	protected STypeFactory newSTypeFactory()
	{
		return new STypeFactory(new GTypeFactoryImpl(),
				new LTypeFactoryImpl());
	}

	// A Scribble extension should override newJobConfig/Context/Core as appropriate
	protected JobConfig newJobConfig(ModuleName mainFullname,
			Map<CoreArgs, Boolean> args, AstFactory af, DelFactory df,
			VisitorFactory vf, STypeFactory tf)
	{
		return new JobConfig(mainFullname, args, af, df, vf, tf);
	}

	// A Scribble extension should override newJobConfig/Context/Core as appropriate
	protected JobContext newJobContext(Job job,
			Map<ModuleName, Module> parsed) throws ScribException
	{
		return new JobContext(this, parsed);
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
		runVisitorPassOnAllModules(this.config.vf.DelDecorator(this));  
				// Currently, only for the simple name nodes "constructed directly" by parser, e.g., t=ID -> ID<...Node>[$t] 
				// CHECKME: refactor to construct (and set del) all uniformly via ScribTreeAdaptor, to deprecate DelDecorator? 
		
		runVisitorPassOnAllModules(this.config.vf.NameDisambiguator(this));  // Includes validating names used in subprotocol calls..
	}
	
	// "Finalises" this Job -- initialises the Job at this point, and cannot run futher Visitor passes on Job
	// So, typically, Job passes should be finished before calling this
	// Core passes may subsequently mutate Core(Context) though
	// CHECKME: revise this pattern? -- maybe fork Core for a snapshot of current Job(Context) -- and, possibly, convert Core back to Job
	public final Core getCore()
	{
		if (this.core == null)
		{
			Map<ModuleName, Module> parsed = this.context.getParsed();
			Set<GProtocol> imeds = new HashSet<>();
			for (ModuleName fullname : parsed.keySet())
			{
				GTypeTranslator t = this.config.vf.GTypeTranslator(this, fullname,
						this.config.tf);
						// CHECKME: factor out STypeTranslator, for future local parsing
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
			this.core = newCore(this.config.main, this.config.args,
					//this.context.getModuleContexts(), 
					imeds, this.config.tf);
		}
		return this.core;
	}
	
	// A Scribble extension should override newJobConfig/Context/Core as appropriate
	protected Core newCore(ModuleName mainFullname, Map<CoreArgs, Boolean> args,
			//Map<ModuleName, ModuleContext> modcs, 
			Set<GProtocol> imeds, STypeFactory tf)
	{
		return new Core(mainFullname, args, //modcs, 
				imeds, tf);
	}

	// Returns: fullname -> Module -- CHECKME TODO: refactor local Module creation to Job?
	// CHECKME: generate projection Modules for an *inline* main?
	public Map<ProtoName<Local>, Module> getProjections(GProtoName fullname,
			Role self) throws ScribException
	{
		AstFactory af = this.config.af;
		LProjectionTranslator t = new LProjectionTranslator(this);
		Map<ProtoName<Local>, Module> res = new HashMap<>();

		Map<ProtoName<Local>, LProjection> projs = getCore()
				.getProjections(fullname, self);
		for (ProtoName<Local> pfullname : projs.keySet())
		{
			LProjection proj = projs.get(pfullname);

			List<ImportDecl<?>> imports = new LinkedList<>();
			List<ProtoName<Local>> pdeps = proj.def
					.gather(new ProtoDepsCollector<Local, LSeq>()::visit).distinct()
					.collect(Collectors.toList());  // Gathering only from this proto decl (no subproto visiting)  // Overlaps with Core::getProjections
			pdeps.remove(pfullname);
			for (ProtoName<Local> imp : pdeps)
			{
				ModuleNameNode modnn = af.ModuleNameNode(null,
						IdNode.from(af, imp.getElements()));
				imports.add(af.ImportModule(null, modnn, null));
			}

			LinkedHashSet<MemberName<?>> npdeps = new LinkedHashSet<>();
			proj.def.gather(new NonProtoDepsGatherer<Local, LSeq>()::visit)  // Gathering only from this proto decl (no subproto visiting)
					.forEachOrdered(x -> npdeps.add(x));  // set-ify while preserving order
			List<NonProtoDecl<?>> nonprods = new LinkedList<>();
			for (MemberName<?> nonpro : npdeps)
			{
				Kind kind = nonpro.getKind();
				ModuleName pref = nonpro.getPrefix();
				Module m = this.context
						.getModule(pref.isEmpty() ? fullname.getPrefix() : pref);
						// CHECKME: currently just "copying" decl from source (global) Module, how about importing the Module instead?  (and re-qualifying names as needed)
				if (kind.equals(DataKind.KIND))
				{
					nonprods.add(m.getTypeDeclChild(((DataName) nonpro).getSimpleName()));
				}
				else if (kind.equals(SigKind.KIND))
				{
					nonprods.add(m.getSigDeclChild(((SigName) nonpro).getSimpleName()));
				}
				else
				{
					throw new RuntimeException("Unhandled: " + kind + "\n\t" + nonpro);
				}
			}

			ModuleNameNode modn = af.ModuleNameNode(null,
					IdNode.from(af, pfullname.getPrefix().getElements()));
			ModuleDecl modd = af.ModuleDecl(null, modn);
			List<LProjectionDecl> protos = Arrays.asList(t.translate(proj));
			Module mod = af.Module(null, modd, imports, nonprods, protos);
			res.put(pfullname, mod);
		}

		// CHECKME: nonprotos should refer to source global module?  i.e., projection modules should import globals?  maybe consistent with "projects" clause
		return res;
				// CHECKME: (interleaved) ordering between proto and nonproto (Module) imports -- order by original Global import order?
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
	
	public JobContext getContext()
	{
		return this.context;
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

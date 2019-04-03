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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.del.global.GProtocolDeclDel;
import org.scribble.lang.Projector;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.global.GProtocol;
import org.scribble.lang.global.GType;
import org.scribble.lang.global.GTypeTranslator;
import org.scribble.lang.local.LProtocol;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EGraphBuilderUtil;
import org.scribble.model.global.SGraph;
import org.scribble.model.global.SGraphBuilderUtil;
import org.scribble.type.Arg;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.Local;
import org.scribble.type.kind.NonRoleParamKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.ModuleContextBuilder;
import org.scribble.visit.context.ProjectedChoiceDoPruner;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.ProjectedRoleDeclFixer;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.util.RoleCollector;
import org.scribble.visit.validation.GProtocolValidator;
import org.scribble.visit.wf.NameDisambiguator;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Job
{
	// Keys are full names
	private final Map<ModuleName, ModuleContext> modcs;  // CHECKME: constant?  move to JobConfig?

	public final JobConfig config;  // Immutable

	private final JobContext context;  // Mutable (Visitor passes replace modules)
	private final SGraphBuilderUtil sgbu;
	
	// Just take MainContext as arg? -- would need to fix Maven dependencies
	//public Job(boolean jUnit, boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness)
	public Job(Map<ModuleName, Module> parsed,
			Map<ModuleName, ModuleContext> modcs, JobConfig config)
	{
		this.modcs = Collections.unmodifiableMap(modcs);
		this.config = config;
		this.sgbu = config.sf.newSGraphBuilderUtil();
		this.context = new JobContext(this, parsed);  // Single instance per Job and should never be shared
	}
	
	// Scribble extensions should override these "new" methods
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
	}

	public void checkWellFormedness() throws ScribbleException
	{
		runContextBuildingPasses();
		//runUnfoldingPass();
		runWellFormednessPasses();
	}
	
	public void runContextBuildingPasses() throws ScribbleException
	{
		runVisitorPassOnAllModules(ModuleContextBuilder.class);  // Always done first (even if other contexts are built later) so that following passes can use ModuleContextVisitor
		runVisitorPassOnAllModules(NameDisambiguator.class);  // Includes validating names used in subprotocol calls..

		/*runVisitorPassOnAllModules(ProtocolDeclContextBuilder.class);   //..which this pass depends on.  This pass basically builds protocol dependency info
		runVisitorPassOnAllModules(DelegationProtocolRefChecker.class);  // Must come after ProtocolDeclContextBuilder
		runVisitorPassOnAllModules(RoleCollector.class);  // Actually, this is the second part of protocoldecl context building*/
		//runVisitorPassOnAllModules(ProtocolDefInliner.class);
		
		// FIXME TODO: refactor into a runVisitorPassOnAllModules for SimpleVisitor (and add operation to ModuleDel)
		Set<ModuleName> fullmodnames = this.context.getFullModuleNames();
		for (ModuleName fullmodname : fullmodnames)
		{
			Module mod = this.context.getModule(fullmodname);
			GTypeTranslator t = new GTypeTranslator(this, fullmodname);
			for (GProtocolDecl gpd : mod.getGProtoDeclChildren())
			{
				GProtocol g = (GProtocol) gpd.visitWith(t);
				this.context.addIntermediate(g.fullname, g);
				debugPrintln("\nParsed:\n" + gpd + "\n\nScribble intermediate:\n" + g);
			}
		}
				
		for (GProtocol g : this.context.getIntermediates())
		{
			List<Arg<? extends NonRoleParamKind>> params = new LinkedList<>();
			// Convert MemberName params to Args -- cf. NonRoleArgList::getParamKindArgs
			for (MemberName<? extends NonRoleParamKind> n : g.params)
			{
				if (n instanceof DataType)
				{
					params.add((DataType) n);
				}
				else if (n instanceof MessageSigName)
				{
					params.add((MessageSigName) n);
				}
				else
				{
					throw new RuntimeException("TODO: " + n);
				}
			}
			SubprotoSig sig = new SubprotoSig(g.fullname, g.roles, params);
			//Deque<SubprotoSig> stack = new LinkedList<>();
			STypeInliner i = new STypeInliner(this);
			i.pushSig(sig);  // TODO: factor into constructor
			GProtocol inlined = g.getInlined(i);
			debugPrintln("\nSubprotocols inlined:\n" + inlined);
			this.context.addInlined(g.fullname, inlined);
		}
				
		//runUnfoldingPass();
		for (GProtocol inlined : this.context.getInlined())
		{
				STypeUnfolder<Global> unf1 = new STypeUnfolder<>();
				//GTypeUnfolder unf2 = new GTypeUnfolder();
				GType unf = (GType) inlined.unfoldAllOnce(unf1);//.unfoldAllOnce(unf2);  CHECKME: twice unfolding? instead of "unguarded"-unfolding?
				debugPrintln("\nAll recursions unfolded once:\n" + unf);
		}
		
		runProjectionPasses();
				
		for (Entry<LProtocolName, LProtocol> e : 
				this.context.getInlinedProjections().entrySet())
		{
			//LProtocolName lname = e.getKey();
			LProtocol proj = e.getValue();
			EGraph graph = proj.toEGraph(this);
			this.context.addEGraph(proj.fullname, graph);
			debugPrintln("\nEFSM for " + proj.fullname + ":\n" + graph.toDot());
		}
	}
		
	// "Second part" of context building (separated for extensions to work on non-unfolded protos -- e.g., Assrt/F17CommandLine)
	public void runUnfoldingPass() throws ScribbleException
	{
		runVisitorPassOnAllModules(InlinedProtocolUnfolder.class);
	}

	public void runWellFormednessPasses() throws ScribbleException
	{
		/*if (!this.config.noValidation)
		{
			runVisitorPassOnAllModules(WFChoiceChecker.class);  // For enabled roles and disjoint enabling messages -- includes connectedness checks
			runProjectionPasses();
			runVisitorPassOnAllModules(ReachabilityChecker.class);  // Moved before GlobalModelChecker.class, OK?
			if (!this.config.useOldWf)
			{
				runVisitorPassOnAllModules(GProtocolValidator.class);
			}
		}*/

		//HERE WF (unfolding? unguarded-unfolder -- and lazy-unfolder-with-choice-pruning, e.g., bad.wfchoice.enabling.threeparty.Test02), projection, validation
		for (GProtocol inlined : this.context.getInlined())
		{
			//TODO: relegate to "warning"
			// Check unused roles
			Set<Role> used = inlined.def.getRoles();
			Set<Role> unused = inlined.roles.stream()
					.filter(x -> !used.contains(x))
					.collect(Collectors.toSet());
			if (!unused.isEmpty())
			{
				throw new ScribbleException(
						"Unused roles in " + inlined.fullname + ": " + unused);
			}
			
			if (inlined.isAux())
			{
				continue;
			}
			STypeUnfolder<Global> unf = new STypeUnfolder<>();  
					//e.g., C->D captured under an A->B choice after unfolding, cf. bad.wfchoice.enabling.twoparty.Test01b;
			inlined.unfoldAllOnce(unf).checkRoleEnabling();
			inlined.checkExtChoiceConsistency();
		}
		
		for (LProtocol proj : this.context.getInlinedProjections().values())
		{
			if (proj.isAux())  // CHECKME? e.g., bad.reach.globals.gdo.Test01b 
			{
				continue;
			}
			proj.checkReachability();
		}

		GProtocolValidator checker = new GProtocolValidator(this);
		//runVisitorPassOnAllModules(GProtocolValidator.class);
		for (Module mod : this.context.getParsed().values())
		{
			// FIXME: refactor validation into lang.GProtocol
			for (GProtocolDecl gpd : mod.getGProtoDeclChildren())
			{
				if (gpd.isAux())
				{
					return;
				}

				GProtocolName fullname = gpd.getFullMemberName(mod);

				debugPrintln("\nValidating " + fullname + ":");

				if (checker.job.config.spin)
				{
					if (checker.job.config.fair)
					{
						throw new RuntimeException(
								"[TODO]: -spin currently does not support fair ouput choices.");
					}
					GProtocolDeclDel.validateBySpin(checker.job, fullname);
				}
				else
				{
					GProtocolDeclDel.validateByScribble(checker.job, fullname, true);
					if (!checker.job.config.fair)
					{
						debugPrintln(
								"(" + fullname + ") Validating with \"unfair\" output choices.. ");
						GProtocolDeclDel.validateByScribble(checker.job, fullname, false);  // TODO: only need to check progress, not full validation
					}
				}
			}
		}
	}

	// Due to Projector not being a subprotocol visitor, so "external" subprotocols may not be visible in ModuleContext building for the projections of the current root Module
	// SubprotocolVisitor it doesn't visit the target Module/ProtocolDecls -- that's why the old Projector maintained its own dependencies and created the projection modules after leaving a Do separately from SubprotocolVisiting
	// So Projection should not be an "inlining" SubprotocolVisitor, it would need to be more a "DependencyVisitor"
	protected void runProjectionPasses() throws ScribbleException
	{
		/*runVisitorPassOnAllModules(Projector.class);
		runProjectionContextBuildingPasses();
		runProjectionUnfoldingPass();
		if (!this.config.noAcceptCorrelationCheck)
		{
			runVisitorPassOnParsedModules(ExplicitCorrelationChecker.class);
		}*/
				
		for (GProtocol g : this.context.getIntermediates())
		{
			for (Role self : g.roles)
			{
				GProtocol inlined = this.context.getInlined(g.fullname);
				LProtocol iproj = inlined.projectInlined(self);  // CHECKME: projection and inling commutative?
				this.context.addInlinedProjection(iproj.fullname, iproj);
				debugPrintln("\nProjected inlined onto " + self + ":\n" + iproj);
			}
		}

		// Pre: inlined already projected -- used for Do projection
		for (GProtocol g : this.context.getIntermediates())
		{
			for (Role self : g.roles)
			{
				LProtocol proj = g.project(new Projector(this, self));
				this.context.addProjection(proj);
				debugPrintln("\nProjected onto " + self + ":\n" + proj);
			}
		}
	}

  // To be done as a barrier pass after projection done on all Modules -- N.B. Module context building, no other validation (so "fixing" can be done in following passes) 
	// Also does projection "fixing" (choice subjects, subprotocol roledecls)
	protected void runProjectionContextBuildingPasses() throws ScribbleException
	{
		runVisitorPassOnProjectedModules(ModuleContextBuilder.class);
		runVisitorPassOnProjectedModules(ProtocolDeclContextBuilder.class);
		runVisitorPassOnProjectedModules(RoleCollector.class);  // NOTE: doesn't collect from choice subjects (may be invalid until projected choice subjs fixed)
		runVisitorPassOnProjectedModules(ProjectedChoiceDoPruner.class);
		if (!this.config.noLocalChoiceSubjectCheck)
		{
			// Disabling ProjectedChoiceSubjectFixer (local choice subject inference) goes towards a general global WF, but is currently unsound
			runVisitorPassOnProjectedModules(ProjectedChoiceSubjectFixer.class);  // Must come before other passes that need DUMMY role occurrences to be fixed
		}
		runVisitorPassOnProjectedModules(ProjectedRoleDeclFixer.class);  // Possibly could do after inlining, and do role collection on the inlined version
		runVisitorPassOnProjectedModules(ProtocolDefInliner.class);
	}

	// Cf. runUnfoldingPass
	protected void runProjectionUnfoldingPass() throws ScribbleException
	{
		runVisitorPassOnProjectedModules(InlinedProtocolUnfolder.class);
	}

	// Pre: checkWellFormedness 
	// Returns: fullname -> Module
	public Map<LProtocolName, Module> getProjections(GProtocolName fullname,
			Role role) throws ScribbleException
	{
		//Module root = 
		LProtocol proj =
				this.context.getProjection(fullname, role);
		
		System.out.println(proj);
		List<ProtocolName<Local>> ps = proj.getProtoDependencies();
		for (ProtocolName<Local> p : ps)
		{
			System.out.println("\n" + this.context.getProjection((LProtocolName) p));
		}

		List<MemberName<?>> ns = proj.getNonProtoDependencies();

		warningPrintln("\n[TODO] Full module projection and imports: "
				+ fullname + "@" + role);
		
		return Collections.emptyMap();
				//FIXME: build output Modules
				//FIXME: (interleaved) ordering between proto and nonproto (Module) imports -- order by original Global import order?
	}

	/*public Map<String, String> generateSessionApi(GProtocolName fullname) throws ScribbleException
	{
		debugPrintPass("Running " + SessionApiGenerator.class + " for " + fullname);
		SessionApiGenerator sg = new SessionApiGenerator(this, fullname);
		Map<String, String> map = sg.generateApi();  // filepath -> class source
		return map;
	}
	
	// FIXME: refactor an EndpointApiGenerator -- ?
	public Map<String, String> generateStateChannelApi(GProtocolName fullname, Role self, boolean subtypes) throws ScribbleException
	{
		/*if (this.jcontext.getEndpointGraph(fullname, self) == null)
		{
			buildGraph(fullname, self);
		}* /
		debugPrintPass("Running " + StateChannelApiGenerator.class + " for " + fullname + "@" + self);
		StateChannelApiGenerator apigen = new StateChannelApiGenerator(this, fullname, self);
		IOInterfacesGenerator iogen = null;
		try
		{
			iogen = new IOInterfacesGenerator(apigen, subtypes);
		}
		catch (RuntimeScribbleException e)  // FIXME: use IOInterfacesGenerator.skipIOInterfacesGeneration
		{
			//System.err.println("[Warning] Skipping I/O Interface generation for protocol featuring: " + fullname);
			warningPrintln("Skipping I/O Interface generation for: " + fullname + "\n  Cause: " + e.getMessage());
		}
		// Construct the Generators first, to build all the types -- then call generate to "compile" all Builders to text (further building changes will not be output)
		Map<String, String> api = new HashMap<>(); // filepath -> class source  // Store results?
		api.putAll(apigen.generateApi());
		if (iogen != null)
		{
			api.putAll(iogen.generateApi());
		}
		return api;
	}*/

	public void runVisitorPassOnAllModules(Class<? extends AstVisitor> c)
			throws ScribbleException
	{
		debugPrintPass("Running " + c + " on all modules:");
		runVisitorPass(this.context.getFullModuleNames(), c);
	}

	public void runVisitorPassOnParsedModules(Class<? extends AstVisitor> c)
			throws ScribbleException
	{
		debugPrintPass("Running " + c + " on parsed modules:");
		runVisitorPass(this.context.getParsedFullModuleNames(), c);
	}

	public void runVisitorPassOnProjectedModules(Class<? extends AstVisitor> c)
			throws ScribbleException
	{
		debugPrintPass("Running " + c + " on projected modules:");
		runVisitorPass(this.context.getProjectedFullModuleNames(), c);
	}

	private void runVisitorPass(Set<ModuleName> modnames,
			Class<? extends AstVisitor> c) throws ScribbleException
	{
		try
		{
			Constructor<? extends AstVisitor> cons = c.getConstructor(Job.class);
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
			throws ScribbleException
	{
		Module visited = (Module) this.context.getModule(modname).accept(nv);
		this.context.replaceModule(visited);
	}
	
	public JobContext getContext()
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

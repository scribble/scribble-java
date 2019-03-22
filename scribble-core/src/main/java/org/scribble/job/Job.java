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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.del.global.GProtocolDeclDel;
import org.scribble.del.local.LProtocolDeclDel;
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
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.Local;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.LProtocolName;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.ModuleContextBuilder;
import org.scribble.visit.context.ProjectedChoiceDoPruner;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.ProjectedRoleDeclFixer;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.util.RoleCollector;
import org.scribble.visit.validation.GProtocolValidator;
import org.scribble.visit.wf.ExplicitCorrelationChecker;
import org.scribble.visit.wf.NameDisambiguator;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Job
{
	// Keys are full names
	private final Map<ModuleName, ModuleContext> mctxts;  // CHECKME: constant?  move to JobConfig?

	public final JobConfig config;  // Immutable

	private final JobContext jctxt;  // Mutable (Visitor passes replace modules)
	private final SGraphBuilderUtil sgbu;
	
	// Just take MainContext as arg? -- would need to fix Maven dependencies
	//public Job(boolean jUnit, boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness)
	public Job(Map<ModuleName, Module> parsed,
			Map<ModuleName, ModuleContext> mctxts, JobConfig config)
	{
		this.mctxts = Collections.unmodifiableMap(mctxts);
		this.config = config;
		this.sgbu = config.sf.newSGraphBuilderUtil();
		this.jctxt = new JobContext(this, parsed);  // Single instance per Job and should never be shared
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
		for (Role r : egraphs.keySet())
		{
			// FIXME: refactor
			debugPrintln("(" + fullname + ") Building global model using EFSM for "
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
		Set<ModuleName> fullmodnames = this.jctxt.getFullModuleNames();
		for (ModuleName fullmodname : fullmodnames)
		{
			Module mod = this.jctxt.getModule(fullmodname);
			GTypeTranslator t = new GTypeTranslator(this, fullmodname);
			for (GProtocolDecl gpd : mod.getGProtoDeclChildren())
			{
				GProtocol g = (GProtocol) gpd.visitWith(t);
				this.jctxt.addIntermediate(g.fullname, g);
				System.out.println("\nparsed:\n" + gpd + "\n\nintermed:\n" + g);
			}
		}
				
		for (GProtocol g : this.jctxt.getIntermediates())
		{
			SubprotoSig sig = new SubprotoSig(g.fullname, g.roles, 
					Collections.emptyList());  // FIXME
			//Deque<SubprotoSig> stack = new LinkedList<>();
			STypeInliner i = new STypeInliner(this);
			i.pushSig(sig);  // TODO: factor into constructor
			GProtocol inlined = g.getInlined(i);
			System.out.println("\ninlined:\n" + inlined);
			this.jctxt.addInlined(g.fullname, inlined);
		}
				
		//runUnfoldingPass();
		for (GProtocol inlined : this.jctxt.getInlined())
		{
				STypeUnfolder<Global> unf1 = new STypeUnfolder<>();
				//GTypeUnfolder unf2 = new GTypeUnfolder();
				GType unf = (GType) inlined.unfoldAllOnce(unf1);//.unfoldAllOnce(unf2);  CHECKME: twice unfolding? instead of "unguarded"-unfolding?
				System.out.println("\nunfolded:\n" + unf);
		}
				
		for (GProtocol g : this.jctxt.getIntermediates())
		{
			for (Role self : g.roles)
			{
				GProtocol inlined = this.jctxt.getInlined(g.fullname);
				LProtocol proj = inlined.project(self);  // Projection and inling commutative?
				STypeUnfolder<Local> unf = new STypeUnfolder<>();
				//proj = proj.unfoldAllOnce(unf);
				this.jctxt.addProjected(proj.fullname, proj);
				System.out.println("\nprojected onto " + self + ":\n" + proj);
			}
		}
				
		for (Entry<LProtocolName, LProtocol> e : 
				this.jctxt.getProjections().entrySet())
		{
			//LProtocolName lname = e.getKey();
			LProtocol proj = e.getValue();
			
			System.out.println("sss1: " + proj);
			
			EGraph graph = proj.toEGraph(this);
			this.jctxt.addEGraph(proj.fullname, graph);
			System.out.println("\ngraph for " + proj.fullname + ":\n" + graph.toDot());
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
		
		for (GProtocol inlined : this.jctxt.getInlined())
		{
			/* TODO: relegate to "warning"
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
			*/
			if (inlined.isAux())
			{
				continue;
			}
			inlined.checkRoleEnabling();
			inlined.checkExtChoiceConsistency();
		}
		
		for (LProtocol proj : this.jctxt.getProjections().values())
		{
			if (proj.isAux())  // CHECKME?
			{
				continue;
			}
			proj.checkReachability();
		}

		GProtocolValidator checker = new GProtocolValidator(this);
		//runVisitorPassOnAllModules(GProtocolValidator.class);
		for (Module mod : this.jctxt.getParsed().values())
		{
			// FIXME: refactor validation into lang.GProtocol
			for (GProtocolDecl gpd : mod.getGProtoDeclChildren())
			{
				if (gpd.isAux())
				{
					return;
				}

				GProtocolName fullname = gpd.getFullMemberName(mod);

				System.out.println("\nvalidating " + fullname + ":");

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
					
					System.out.println("ppp1: " + checker.job.config.fair);
					
					if (!checker.job.config.fair)
					{
						checker.job.debugPrintln(
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
		runVisitorPassOnAllModules(Projector.class);
		runProjectionContextBuildingPasses();
		runProjectionUnfoldingPass();
		if (!this.config.noAcceptCorrelationCheck)
		{
			runVisitorPassOnParsedModules(ExplicitCorrelationChecker.class);
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
	// Returns: full proto name -> Module
	public Map<LProtocolName, Module> getProjections(GProtocolName fullname,
			Role role) throws ScribbleException
	{
		Module root = this.jctxt.getProjection(fullname, role);
		Map<LProtocolName, Set<Role>> dependencies = ((LProtocolDeclDel) root
				.getLProtoDeclChildren().get(0).del()).getProtocolDeclContext()
						.getDependencyMap().getDependencies().get(role);
		// Can ignore Set<Role> for projections (is singleton), as each projected proto is a dependency only for self (implicit in the protocoldecl)
		return dependencies.keySet().stream().collect(
				Collectors.toMap(lpn -> lpn,
						lpn -> this.jctxt.getModule(lpn.getPrefix())));
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
		runVisitorPass(this.jctxt.getFullModuleNames(), c);
	}

	public void runVisitorPassOnParsedModules(Class<? extends AstVisitor> c)
			throws ScribbleException
	{
		debugPrintPass("Running " + c + " on parsed modules:");
		runVisitorPass(this.jctxt.getParsedFullModuleNames(), c);
	}

	public void runVisitorPassOnProjectedModules(Class<? extends AstVisitor> c)
			throws ScribbleException
	{
		debugPrintPass("Running " + c + " on projected modules:");
		runVisitorPass(this.jctxt.getProjectedFullModuleNames(), c);
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
		Module visited = (Module) this.jctxt.getModule(modname).accept(nv);
		this.jctxt.replaceModule(visited);
	}
	
	public JobContext getJobContext()
	{
		return this.jctxt;
	}
	
	public ModuleContext getModuleContext(ModuleName fullname)
	{
		return this.mctxts.get(fullname);
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

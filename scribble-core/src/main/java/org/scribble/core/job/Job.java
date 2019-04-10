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
package org.scribble.core.job;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.ModelFactoryImpl;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.endpoint.EGraphBuilderUtil;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.model.global.SGraphBuilderUtil;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.NonProtoDepsGatherer;
import org.scribble.core.visit.ProtoDepsCollector;
import org.scribble.core.visit.RoleGatherer;
import org.scribble.core.visit.global.GTypeInliner;
import org.scribble.core.visit.global.GTypeUnfolder;
import org.scribble.util.ScribException;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Job
{
	public final JobConfig config;  // Immutable

	private final JobContext context;  // Mutable (Visitor passes replace modules)
	private final SGraphBuilderUtil sgraphb;
	
	public Job(ModuleName mainFullname, Map<JobArgs, Boolean> args,
			//Map<ModuleName, ModuleContext> modcs, 
			Set<GProtocol> imeds)
	{
		this.config = newJobConfig(mainFullname, args);
		this.context = newJobContext(//modcs, 
				imeds);  // Single instance per Job and should never be shared
		this.sgraphb = newSGraphBuilderUtil();
	}

	// A Scribble extension should override newJobConfig/Context/etc as appropriate
	protected JobConfig newJobConfig(ModuleName mainFullname,
			Map<JobArgs, Boolean> args)
	{
		ModelFactory mf = new ModelFactoryImpl();
		return new JobConfig(mainFullname, args, mf); 
				// CHECKME: combine E/SModelFactory?
	}

	// A Scribble extension should override newJobConfig/Context/etc as appropriate
	protected JobContext newJobContext(//Map<ModuleName, ModuleContext> modcs,
			Set<GProtocol> imeds)
	{
		return new JobContext(this, //modcs, 
				imeds);
	}
	
	// TODO: deprecate, caller should go through config
	// A Scribble extension should override newJobConfig/Context/etc as appropriate
	public SGraphBuilderUtil newSGraphBuilderUtil()
	{
		return this.config.mf.newSGraphBuilderUtil();
	}

	// TODO: deprecate, caller should go through config
	// A Scribble extension should override newJobConfig/Context/etc as appropriate
	public EGraphBuilderUtil newEGraphBuilderUtil()
	{
		return new EGraphBuilderUtil(this.config.mf);
	}
	
	//public SGraphBuilderUtil newSGraphBuilderUtil()  // FIXME TODO global builder util
	public SGraph buildSGraph(GProtocolName fullname, Map<Role, EGraph> egraphs,
			boolean explicit) throws ScribException
	{
		verbosePrintln("(" + fullname + ") Building global model using:");
		for (Role r : egraphs.keySet())
		{
			// FIXME: refactor
			verbosePrintln("-- EFSM for "
					+ r + ":\n" + egraphs.get(r).init.toDot());
		}
		//return SGraph.buildSGraph(this, fullname, createInitialSConfig(this, egraphs, explicit));
		return this.sgraphb.buildSGraph(this, fullname, egraphs, explicit);  // FIXME: factor out util
	}

	public void runPasses() throws ScribException
	{
		runContextBuildingPasses();
		//runUnfoldingPass();
		runValidationPasses();
	}
	
	public void runContextBuildingPasses() throws ScribException
	{
		/*// FIXME TODO: refactor into a runVisitorPassOnAllModules for SimpleVisitor (and add operation to ModuleDel)
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
		}*/
				
		for (GProtocol g : this.context.getIntermediates())
		{
			/*SubprotoSig sig = new SubprotoSig(g.fullname, g.roles, params);
			//Deque<SubprotoSig> stack = new LinkedList<>();
			STypeInliner i = new STypeInliner(this);
			i.pushSig(sig);  // TODO: factor into constructor*/
			GTypeInliner v = new GTypeInliner(this);
			GProtocol inlined = g.getInlined(v);  // Protocol.getInlined does pruneRecs
			verbosePrintln("\nSubprotocols inlined:\n" + inlined);
			this.context.addInlined(g.fullname, inlined);
		}
				
		//runUnfoldingPass();
		for (GProtocol inlined : this.context.getInlined())
		{
				//STypeUnfolder<Global> unf1 = new STypeUnfolder<>();
				////GTypeUnfolder unf2 = new GTypeUnfolder();
				GTypeUnfolder v = new GTypeUnfolder();
				GProtocol unf = (GProtocol) inlined.unfoldAllOnce(v);//.unfoldAllOnce(unf2);  CHECKME: twice unfolding? instead of "unguarded"-unfolding?
				verbosePrintln("\nAll recursions unfolded once:\n" + unf);
		}
		
		runProjectionPasses();
				
		for (Entry<LProtocolName, LProtocol> e : 
				this.context.getInlinedProjections().entrySet())
		{
			//LProtocolName lname = e.getKey();
			LProtocol proj = e.getValue();
			EGraph graph = proj.toEGraph(this);
			this.context.addEGraph(proj.fullname, graph);
			verbosePrintln("\nEFSM for " + proj.fullname + ":\n" + graph.toDot());
		}
	}
	
	public void runValidationPasses() throws ScribException
	{
		for (GProtocol inlined : this.context.getInlined())
		{
			// CHECKME: relegate to "warning" ? -- some downsteam operations may depend on this though (e.g., graph building?)
			// Check unused roles
			Set<Role> used = inlined.def
					.gather(new RoleGatherer<Global, GSeq>()::visit)
					.collect(Collectors.toSet());
			Set<Role> unused = this.context.getIntermediate(inlined.fullname).roles
							// imeds have original role decls (inlined's are pruned)
					.stream().filter(x -> !used.contains(x)).collect(Collectors.toSet());
			if (!unused.isEmpty())
			{
				throw new ScribException(
						"Unused roles in " + inlined.fullname + ": " + unused);
			}

			if (inlined.isAux())
			{
				continue;
			}
			//STypeUnfolder<Global> u = new STypeUnfolder<>();  
			GTypeUnfolder v = new GTypeUnfolder();
					//e.g., C->D captured under an A->B choice after unfolding, cf. bad.wfchoice.enabling.twoparty.Test01b;
			inlined.unfoldAllOnce(v).checkRoleEnabling();
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

		//runVisitorPassOnAllModules(GProtocolValidator.class);
		//for (Module mod : this.context.getParsed().values())
		{
			// FIXME: refactor validation into lang.GProtocol
			//for (GProtocolDecl gpd : mod.getGProtoDeclChildren())
			for (GProtocol gpd : this.context.getInlined()) //mod.getGProtoDeclChildren())
			{
				if (gpd.isAux())
				{
					continue;
				}

				GProtocolName fullname = gpd.fullname;//.getFullMemberName(mod);

				verbosePrintln("\nValidating " + fullname + ":");

				if (this.config.args.get(JobArgs.SPIN))
				{
					if (this.config.args.get(JobArgs.FAIR))
					{
						throw new RuntimeException(
								"[TODO]: -spin currently does not support fair ouput choices.");
					}
					GProtocol.validateBySpin(this, fullname);
				}
				else
				{
					GProtocol.validateByScribble(this, fullname, true);
					if (!this.config.args.get(JobArgs.FAIR))
					{
						verbosePrintln(
								"(" + fullname + ") Validating with \"unfair\" output choices.. ");
						GProtocol.validateByScribble(this, fullname, false);  // TODO: only need to check progress, not full validation
					}
				}
			}
		}
	}

	// Due to Projector not being a subprotocol visitor, so "external" subprotocols may not be visible in ModuleContext building for the projections of the current root Module
	// SubprotocolVisitor it doesn't visit the target Module/ProtocolDecls -- that's why the old Projector maintained its own dependencies and created the projection modules after leaving a Do separately from SubprotocolVisiting
	// So Projection should not be an "inlining" SubprotocolVisitor, it would need to be more a "DependencyVisitor"
	protected void runProjectionPasses() throws ScribException
	{
		/*runVisitorPassOnAllModules(Projector.class);
		runProjectionContextBuildingPasses();
		runProjectionUnfoldingPass();
		if (!this.config.noAcceptCorrelationCheck)
		{
			runVisitorPassOnParsedModules(ExplicitCorrelationChecker.class);
		}*/
				
		for (GProtocol g : this.context.getInlined())
		{
			for (Role self : g.roles)
			{
				GProtocol inlined = this.context.getInlined(g.fullname);  // pruneRecs already done for pruneRecs (cf. runContextBuildingPasses)
				LProtocol iproj = inlined.projectInlined(this, self);  // CHECKME: projection and inling commutative?
				this.context.addInlinedProjection(iproj.fullname, iproj);
				verbosePrintln("\nProjected inlined onto " + self + ":\n" + iproj);
			}
		}

		// Pre: inlined already projected -- used for Do projection
		for (GProtocol g : this.context.getIntermediates())
		{
			for (Role self : g.roles)
			{
				LProtocol proj = g.project(this, self);  // Does pruneRecs
				this.context.addProjection(proj);
				verbosePrintln("\nProjected onto " + self + ":\n" + proj);
			}
		}
	}

	// Pre: checkWellFormedness 
	// Returns: fullname -> Module -- CHECKME TODO: refactor local Module creation to Lang?
	// CHECKME: generate projection Modules for an inline main?
	public Map<LProtocolName, LProtocol> getProjections(GProtocolName fullname,
			Role role) throws ScribException
	{
		//Module root = 
		LProtocol proj =
				this.context.getProjection(fullname, role);
		
		List<ProtocolName<Local>> ps = proj.def
				.gather(new ProtoDepsCollector<Local, LSeq>()::visit)
				.collect(Collectors.toList());
		for (ProtocolName<Local> p : ps)
		{
			System.out.println("\n" + this.context.getProjection((LProtocolName) p));
		}
		if (!ps.contains(proj.fullname))
		{
			System.out.println("\n" + proj);
		}

		List<MemberName<?>> ns = proj.def
				.gather(new NonProtoDepsGatherer<Local, LSeq>()::visit)
				.collect(Collectors.toList());

		warningPrintln("");
		warningPrintln("[TODO] Full module projection and imports: "
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
	
	public JobContext getContext()
	{
		return this.context;
	}
	
	public boolean isVerbose()
	{
		return this.config.args.get(JobArgs.VERBOSE);
	}
	
	public void warningPrintln(String s)
	{
		System.err.println("[Warning] " + s);
	}
	
	public void verbosePrintln(String s)
	{
		if (this.config.args.get(JobArgs.VERBOSE))
		{
			System.out.println(s);
		}
	}
	
	/*private void debugPrintPass(String s)
	{
		debugPrintln("\n[Job] " + s);
	}*/
}

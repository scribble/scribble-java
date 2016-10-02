package org.scribble.visit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.endpointapi.ioifaces.IOInterfacesGenerator;
import org.scribble.del.local.LProtocolDeclDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;

// A "compiler job" front-end that supports operations comprising one or more visitor passes over the AST
public class Job
{
	// FIXME: verbose/debug printing parameter: should be in MainContext, but currently cannot access that class directly from here
	//public final boolean jUnit;
	public final boolean debug;
	public final boolean useOldWf;
	public final boolean noLiveness;
	public final boolean minEfsm;  // Currently only affects EFSM output (i.e. -fsm, -dot) and API gen -- doesn't affect model checking
	public final boolean fair;
	public final boolean noLocalChoiceSubjectCheck;
	
	private final JobContext jcontext;  // Mutable (Visitor passes replace modules)
	
	// Just take MainContext as arg? -- would need to fix Maven dependencies
	//public Job(boolean jUnit, boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness)
	public Job(boolean debug, Map<ModuleName, Module> parsed, ModuleName main, boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair, boolean noLocalChoiceSubjectCheck)
	{
		//this.jUnit = jUnit;
		this.debug = debug;
		this.useOldWf = useOldWF;
		this.noLiveness = noLiveness;
		this.minEfsm = minEfsm;
		this.fair = fair;
		this.noLocalChoiceSubjectCheck = noLocalChoiceSubjectCheck;

		this.jcontext = new JobContext(this, parsed, main);  // Single instance per Job and should never be shared
	}

	public ScribbleException testWellFormednessCheck()
	{
		try
		{
			checkWellFormedness();
		}
		catch (ScribbleException x)
		{
			return x;
		}
		return null;
	}

	public void checkWellFormedness() throws ScribbleException
	{
		runContextBuildingPasses();
		runVisitorPassOnAllModules(WFChoiceChecker.class);  // For enabled roles and disjoint enabling messages -- includes connectedness checks
		////runVisitorPassOnAllModules(WFChoicePathChecker.class);
		runProjectionPasses();
		runVisitorPassOnAllModules(ReachabilityChecker.class);  // Moved before GlobalModelChecker.class, OK?
		if (!this.useOldWf)
		{
			runVisitorPassOnAllModules(GlobalModelChecker.class);
		}
		//runVisitorPassOnAllModules(ReachabilityChecker.class);
	}
	
	private void runContextBuildingPasses() throws ScribbleException
	{
		runVisitorPassOnAllModules(ModuleContextBuilder.class);  // Always done first (even if other contexts are built later) so that following passes can use ModuleContextVisitor
		runVisitorPassOnAllModules(NameDisambiguator.class);  // Includes validating names used in subprotocol calls..
		runVisitorPassOnAllModules(ProtocolDeclContextBuilder.class);   //..which this pass depends on.  This pass basically builds protocol dependency info
		runVisitorPassOnAllModules(DelegationProtocolRefChecker.class);  // Must come after ProtocolDeclContextBuilder
		runVisitorPassOnAllModules(RoleCollector.class);  // Actually, this is the second part of protocoldecl context building
		runVisitorPassOnAllModules(ProtocolDefInliner.class);
		runVisitorPassOnAllModules(InlinedProtocolUnfolder.class);
		//runVisitorPassOnAllModules(GlobalModelBuilder.class);
	}

	// Due to Projector not being a subprotocol visitor, so "external" subprotocols may not be visible in ModuleContext building for the projections of the current root Module
	// SubprotocolVisitor it doesn't visit the target Module/ProtocolDecls -- that's why the old Projector maintained its own dependencies and created the projection modules after leaving a Do separately from SubprotocolVisiting
	// So Projection should not be an "inlining" SubprotocolVisitor, it would need to be more a "DependencyVisitor"
	private void runProjectionPasses() throws ScribbleException
	{
		runVisitorPassOnAllModules(Projector.class);
		runProjectionContextBuildingPasses();
		runVisitorPassOnParsedModules(ExplicitCorrelationChecker.class);
	}

  // To be done as a barrier pass after projection done on all Modules -- N.B. Module context building, no other validation (so "fixing" can be done in following passes) 
	// Also does projection "fixing" (choice subjects, subprotocol roledecls)
	private void runProjectionContextBuildingPasses() throws ScribbleException
	{
		runVisitorPassOnProjectedModules(ModuleContextBuilder.class);
		runVisitorPassOnProjectedModules(ProtocolDeclContextBuilder.class);
		runVisitorPassOnProjectedModules(RoleCollector.class);  // NOTE: doesn't collect from choice subjects (may be invalid until projected choice subjs fixed)
		runVisitorPassOnProjectedModules(ProjectedChoiceDoPruner.class);
		if (!this.noLocalChoiceSubjectCheck)
		{
			// Disabling ProjectedChoiceSubjectFixer (local choice subject inference) goes towards a general global WF, but is currently unsound
			runVisitorPassOnProjectedModules(ProjectedChoiceSubjectFixer.class);  // Must come before other passes that need DUMMY role occurrences to be fixed
		}
		runVisitorPassOnProjectedModules(ProjectedRoleDeclFixer.class);  // Possibly could do after inlining, and do role collection on the inlined version
		runVisitorPassOnProjectedModules(ProtocolDefInliner.class);
		runVisitorPassOnProjectedModules(InlinedProtocolUnfolder.class);
	}

	// Pre: checkWellFormedness 
	// Returns: full proto name -> Module
	public Map<LProtocolName, Module> getProjections(GProtocolName fullname, Role role) throws ScribbleException
	{
		Module root = this.jcontext.getProjection(fullname, role);
		Map<LProtocolName, Set<Role>> dependencies =
				((LProtocolDeclDel) root.getLocalProtocolDecls().get(0).del())
						.getProtocolDeclContext().getDependencyMap().getDependencies().get(role);
		// Can ignore Set<Role> for projections (is singleton), as each projected proto is a dependency only for self (implicit in the protocoldecl)
		return dependencies.keySet().stream().collect(
				Collectors.toMap((lpn) -> lpn, (lpn) -> this.jcontext.getModule(lpn.getPrefix())));
	}
	
  /*// Endpoint graphs are "inlined", so only a single graph is built (cf. projection output)
	public void buildGraph(GProtocolName fullname, Role role) throws ScribbleException  // Need to visit from Module for visitor context
	{
		debugPrintPass("Running " + EndpointGraphBuilder.class + " for " + fullname + "@" + role);
		// Visit Module for context (not just the protodecl) -- builds FSMs for all locals in the module
		
		//System.out.println("AAA: " + this.jcontext.getProjection(fullname, role));

		Module proj = this.jcontext.getProjection(fullname, role);
		if (proj == null)
		{
			throw new ScribbleException("Shouldn't see this: " + fullname);  // Should be suppressed by an earlier failure
		}
		proj.accept(new EndpointGraphBuilder(this)); 
			// Builds FSMs for all local protocols in this module as root (though each projected module contains a single local protocol)
			// Subprotocols "inlined" by FsmBuilder (scoped subprotocols not supported)
	}*/

	public Map<String, String> generateSessionApi(GProtocolName fullname) throws ScribbleException
	{
		debugPrintPass("Running " + SessionApiGenerator.class + " for " + fullname);
		SessionApiGenerator sg = new SessionApiGenerator(this, fullname);
		Map<String, String> map = sg.generateApi();  // filepath -> class source
		return map;
	}
	
	// FIXME: refactor an EndpointApiGenerator
	public Map<String, String> generateStateChannelApi(GProtocolName fullname, Role self, boolean subtypes) throws ScribbleException
	{
		/*if (this.jcontext.getEndpointGraph(fullname, self) == null)
		{
			buildGraph(fullname, self);
		}*/
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
			System.err.println("[Warning] Skipping I/O Interface generation for: " + fullname + "\n  Cause: " + e.getMessage());
		}
		// Construct the Generators first, to build all the types -- then call generate to "compile" all Builders to text (further building changes will not be output)
		Map<String, String> api = new HashMap<>(); // filepath -> class source  // Store results?
		api.putAll(apigen.generateApi());
		if (iogen != null)
		{
			api.putAll(iogen.generateApi());
		}
		return api;
	}
	
	private void runVisitorPassOnAllModules(Class<? extends AstVisitor> c) throws ScribbleException
	{
		debugPrintPass("Running " + c + " on all modules:");
		runVisitorPass(this.jcontext.getFullModuleNames(), c);
	}

	private void runVisitorPassOnParsedModules(Class<? extends AstVisitor> c) throws ScribbleException
	{
		debugPrintPass("Running " + c + " on parsed modules:");
		runVisitorPass(this.jcontext.getParsedFullModuleNames(), c);
	}

	private void runVisitorPassOnProjectedModules(Class<? extends AstVisitor> c) throws ScribbleException
	{
		debugPrintPass("Running " + c + " on projected modules:");
		runVisitorPass(this.jcontext.getProjectedFullModuleNames(), c);
	}

	private void runVisitorPass(Set<ModuleName> modnames, Class<? extends AstVisitor> c) throws ScribbleException
	{
		try
		{
			Constructor<? extends AstVisitor> cons = c.getConstructor(Job.class);
			for (ModuleName modname : modnames)
			{
				AstVisitor nv = cons.newInstance(this);
				Module visited = (Module) this.jcontext.getModule(modname).accept(nv);
				this.jcontext.replaceModule(visited);
			}
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public JobContext getContext()
	{
		return this.jcontext;
	}
	
	public boolean isDebug()
	{
		return this.debug;
	}
	
	public void debugPrintln(String s)
	{
		if (this.debug)
		{
			System.out.println(s);
		}
	}
	
	private void debugPrintPass(String s)
	{
		debugPrintln("\n[DEBUG] " + s);
	}
}

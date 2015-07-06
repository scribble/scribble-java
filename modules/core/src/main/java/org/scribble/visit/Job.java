package org.scribble.visit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointApiGenerator;
import org.scribble.net.session.SessionApiGenerator;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;


// A "compiler job" front-end that supports operations comprising one or more visitor passes over the AST
public class Job
{
	// FIXME: verbose/debug printing parameter: should be in MainContext, but currently cannot access that class directly from here
	public final boolean debug;
	
	private final JobContext jcontext;  // Mutable (Visitor passes replace modules)
	
	// Just take MainContext as arg? -- would need to fix Maven dependencies
	public Job(boolean debug, Map<ModuleName, Module> parsed, ModuleName main)
	{
		this.debug = debug;
		this.jcontext = new JobContext(parsed, main);
	}

	public void checkWellFormedness() throws ScribbleException
	{
		runVisitorPassOnAllModules(ContextBuilder.class);
		runVisitorPassOnAllModules(NameDisambiguator.class);
		//runNodeVisitorPass(ModelBuilder.class);
		runVisitorPassOnAllModules(ProtocolDefInliner.class);
		runVisitorPassOnAllModules(InlinedProtocolUnfolder.class);
		//runNodeVisitorPass(WFChoiceChecker.class);
		runVisitorPassOnAllModules(InlinedWFChoiceChecker.class);
		runProjectionPasses();
		runVisitorPassOnAllModules(ReachabilityChecker.class);
	}

	private void runProjectionPasses() throws ScribbleException
	{
		runVisitorPassOnAllModules(Projector.class);
		// Due to Projector not being a subprotocol visitor, so "external" subprotocols may not be visible in ModuleContext building for the projections of the current root Module
		// SubprotocolVisitor it doesn't visit the target Module/ProtocolDecls -- that's why the old Projector maintained its own dependencies and created the projection modules after leaving a Do separately from SubprotocolVisiting
		// So Projection should not be an "inlining" SubprotocolVisitor, it would need to be more a "DependencyVisitor"
		runVisitorPassOnProjectedModules(ContextBuilder.class);  // To be done as a barrier pass after projection done on all Modules
		runVisitorPassOnProjectedModules(ProjectedChoiceSubjectFixer.class);
		runVisitorPassOnProjectedModules(ProtocolDefInliner.class);
		runVisitorPassOnProjectedModules(InlinedProtocolUnfolder.class);
	}
	
	public void buildFsms(GProtocolName fullname, Role role) throws ScribbleException  // Need to visit from Module for visitor context
	{
		debugPrintPass("Running " + FsmBuilder.class + " for " + fullname + "@" + role);
		// Visit Module for context (not just the protodecl) -- builds FSMs for all locals in the module
		this.jcontext.getProjection(fullname, role).accept(new FsmBuilder(this)); 
			// Builds FSMs for all local protocols in this module as root (though each projected module contains a single local protocol)
			// Subprotocols "inlined" by FsmBuilder (scoped subprotocols not supported)
	}
	
	public Map<String, String> generateSessionApi(GProtocolName fullname) throws ScribbleException
	{
		debugPrintPass("Running " + SessionApiGenerator.class + " for " + fullname);
		SessionApiGenerator sg = new SessionApiGenerator(this, fullname);
		Map<String, String> map = sg.getSessionClass();  // filepath -> class source
		return map;
	}
	
	public Map<String, String> generateEndpointApi(GProtocolName fullname, Role role) throws ScribbleException
	{
		if (this.jcontext.getFsm(fullname, role) == null)
		{
			buildFsms(fullname, role);
		}
		debugPrintPass("Running " + EndpointApiGenerator.class + " for " + fullname + "@" + role);
		return new EndpointApiGenerator(this, fullname, role).getClasses(); // filepath -> class source  // Store results?
	}

	private void runVisitorPassOnAllModules(Class<? extends AstVisitor> c) throws ScribbleException
	{
		debugPrintPass("Running " + c + " on all modules:");
		runVisitorPass(this.jcontext.getFullModuleNames(), c);
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

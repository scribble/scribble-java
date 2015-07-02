package org.scribble.visit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.scribble.ast.Module;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointApiGenerator;
import org.scribble.net.session.SessionApiGenerator;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;


// A "compiler job" that supports operations comprising one or more visitor passes over the AST
public class Job
{
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
		debugPrintln("\n--- Context building --- ");
		runNodeVisitorPass(ContextBuilder.class);

		debugPrintln("\n--- Name disambigiation --- ");  // FIXME: verbose/debug printing parameter: should be in MainContext, but currently cannot access that class directly from here
		runNodeVisitorPass(NameDisambiguator.class);
		
		/*debugPrintln("\n--- Model building --- ");
		runNodeVisitorPass(ModelBuilder.class);*/

		debugPrintln("\n--- Subprotocol inlining --- ");
		runNodeVisitorPass(ProtocolDefInliner.class);

		debugPrintln("\n--- Inlined protocol unfolding --- ");
		runNodeVisitorPass(InlinedProtocolUnfolder.class);

		/*debugPrintln("\n--- Well-formed choice check --- ");
		runNodeVisitorPass(WFChoiceChecker.class);*/

		debugPrintln("\n--- Inlined well-formed choice check --- ");
		runNodeVisitorPass(InlinedWFChoiceChecker.class);

		debugPrintln("\n--- Projection --- ");
		runNodeVisitorPass(Projector.class);
		//this.jcontext.buildProjectionContexts();  // Hacky? -- due to Projector not being a subprotocol visitor, so "external" subprotocols may not be visible in ModuleContext building for the projections of the current root Module
		// No: SubprotocolVisitor is an "inlining" step, it doesn't visit the target Module/ProtocolDecls -- that's why the old Projector maintained its own dependencies and created the projection modules after leaving a Do separately from SubprotocolVisiting
		// So Projection should not be an "inlining" SubprotocolVisitor, it would need to be more a "DependencyVisitor"
		buildProjectionContexts();
		runNodeVisitorPass(ProjectedChoiceSubjectFixer.class);
		inlineProjections();

		debugPrintln("\n--- Reachability check --- ");
		runNodeVisitorPass(ReachabilityChecker.class);
	}
	
	// To be done as a barrier pass after projection done on all Modules
	private void buildProjectionContexts()
	{
		Map<LProtocolName, Module> projections = this.jcontext.getProjections();
		try
		{
			ContextBuilder builder = new ContextBuilder(this);
			for (LProtocolName lpn : projections.keySet())
			{
				Module mod = projections.get(lpn);
				mod = (Module) mod.accept(builder);
				this.jcontext.replaceModule(mod);
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}
	}

	// FIXME: factor out with buildProjectionContexts and runNodeVisitorPass
	private void inlineProjections()
	{
		try
		{
			Map<LProtocolName, Module> projections = this.jcontext.getProjections();
			ProtocolDefInliner inliner = new ProtocolDefInliner(this);
			for (LProtocolName lpn : projections.keySet())
			{
				Module mod = projections.get(lpn);
				mod = (Module) mod.accept(inliner);
				this.jcontext.replaceModule(mod);
			}

			projections = this.jcontext.getProjections();
			InlinedProtocolUnfolder unfolder = new InlinedProtocolUnfolder(this);
			for (LProtocolName lpn : projections.keySet())
			{
				Module mod = projections.get(lpn);
				mod = (Module) mod.accept(unfolder);
				this.jcontext.replaceModule(mod);
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}
	}
	
	public void constructFsms(Module mod) throws ScribbleException  // Need to visit from Module for visitor context
	{
		debugPrintln("\n--- FSM construction --- ");
		mod.accept(new FsmBuilder(this)); 
			// Constructs FSMs from all local protocols in this module (projected modules contain a single local protocol)
			// Subprotocols "inlined" (scoped subprotocols not supported)
	}
	
	public Map<String, String> generateSessionApi(GProtocolName gpn) throws ScribbleException
	{
		debugPrintln("\n--- Session API generation --- ");
		// FIXME: check gpn is valid
		SessionApiGenerator sg = new SessionApiGenerator(this, gpn);
		Map<String, String> map = sg.getSessionClass();  // filepath -> class source
		return map;
	}
	
	public Map<String, String> generateEndpointApi(GProtocolName gpn, Role role) throws ScribbleException
	{
		LProtocolName lpn = Projector.makeProjectedFullNameNode(new GProtocolName(this.jcontext.main, gpn), role).toName();
		if (this.jcontext.getFsm(lpn) == null)  // FIXME: null hack
		{
			Module mod = this.jcontext.getModule(lpn.getPrefix());
			constructFsms(mod);
		}
		debugPrintln("\n--- Endpoint API generation --- ");
		return new EndpointApiGenerator(this, gpn, role).getClasses(); // filepath -> class source  // FIXME: store results?
	}

	private void runNodeVisitorPass(Class<? extends AstVisitor> c) throws ScribbleException
	{
		try
		{
			Constructor<? extends AstVisitor> cons = c.getConstructor(Job.class);
			for (ModuleName modname : this.jcontext.getFullModuleNames())
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
}

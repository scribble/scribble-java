package org.scribble2.model.visit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.scribble2.fsm.ApiGenerator;
import org.scribble2.model.Module;
import org.scribble2.net.session.SessionGenerator;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

	// TODO: deadlock analysis: for parallel, and even just choice if one process will play multiple roles (e.g. choice at A { A->B; A->C } or { A->C; A->B })
	// FIXME: api generation for parallel/interruptible -- branch needs to report on op and role (depending on input queue semantics)

	// FIXME: do-call argument kinding (sig/type args/params), arity, etc

	//.. do projection should filter unused roles -- but scoped subprotocols may need extra name mangling
	//.. both projector and graphbuilder are env visitors but not subprotocol visitors now, so swap visitor hierarchy?
	// dels should be kinded as well?

	// visitchildren shouldn't use check class on visited nodes so strictly, e.g. name disambiguation changes from ambiguousnodes

	//.. factor out main module resource loading in front end from main context -- front end should take main argument, check existence, and pass MainContext the abstract resource identifier to load the main
	//.. ^^ alternatively keep ResourceLocator specific to file systems -- "DirectoryResourceLocator" just uses the import paths
	//.. check use of generics
	//.. consider refactoring all uses of AbstractProtocolDecl to be able to get global/local directly (would need global/local as a generic parameter) -- e.g. Do.getTargetProtocolDecl
	//.. fix parameterdecllist generics (not fixed to one kind)

	//.. do call type checking as well as basic name binding
	//.. guarded recursive subprotocols -- guarded recursion vars not needed? handled by projection
	//.. make headerparamdecl into paramdecl directly, i.e. and then role is a specialised param kind
	//.. maybe make an UnkindedName superclass of Name, use for e.g. parameters or ambiguous

		//.. sepatate protocol names into global/local -- use generic parameter for name kinds rather than subclasses
		//.. fix projection env to take projection output type as Parameter
		//.. fix global/local do delegate context build loop -- use lambda
		//.. get simple/compound name node and name classes into shape
		//.. fix del parameterized return type (take class as arg) -- maybe also copy
		//.. fix modelfactory simple name node parameterization (take class instead of enum)
		//.. remove scoped subprotocols for now

// - perhaps refactor to have choice/recursion/etc as packages with global/local/del/etc in each

// - visitor pattern, delegates, envs (root, creating and assigning, merging, super calls), subprotocol visiting
// - streamline visitor pattern calls (e.g. accept)
// - streamline vistitor/del env references -- and del enter/leave env setter on visitors

//...HERE: fix ReachabilityEnv merge; do enter/leave reachability check for recursion/continue/parallel/etc; check reachability pass visits all projected modules
//... check delegates for local nodes; check reachability visiting for (local) interaction sequence (and delegate)

// Done
// - wf-choice: a role should be enabled by the same role in all blocks
// - get rid of argument instantiation -- renamed, but otherwise structurally the same (unlike name/param decls, arg nodes are not kinded)
// - refactor simple/compound names to just names; and simple name nodes to be subtypes of compound -- simple/compound distinction only relevant to name nodes (i.e. syntax); type names are all uniform (compound)
// - generalise dependency building to support local protocols -- though only global dependencies used so far, for projection
// - make module/protocol delegate state (module context, protocol dependencies) setting uniform -- related to (non-)immutablity of delegates (where to store "context" state)
// - remove job/module contexts from Envs (refer from visitor -- can be updated during visitor pass and reassigned to root module on leave)
// - enter doesn't need to return visitor, not using visitor immutability? (or visitor replacement flexibility)
// - use Path API (though path separators not taken from nio api)
// - import path should be a CL parameter, not MainContext

// Not done
// - ArgumentNode is not kinded -- argument interface is about not knowing what kind of argument it is; e.g. AmbiguousNameNode has both DataType and Sig kind interfaces
// - FIXME: factor out a project method (like a reconstruct) to GlobalModelNode (and use the below for recording/assembling the projections) -- no, leave in delegate
// - change InteractionNode interface to a base class -- no, better for interaction nodes to extend simple/compound as base
// - make a createDelegate method in ModelNode -- no, leave association of delegates to model nodes in factory -- then replacing a delegate requires changing the factory only
// - substitute to delegate? -- no, better to have as a simple node operation that uses the protected reconstruct pattern directly (a del operation is more indirect with no advantages)
// - fix instanceof in projector and reachability checker -- only partly: moved main code to delegates but the "root" instanceof needs to stay inside the visitors to "override" the base subprotocol visitInSubprotocols pattern
// - override del in each ModelNode to cast -- no: leave as base del for most flexibility in case of replacement
// - Job takes MainContext as argument -- no: recursive maven dependencies between cli-core-parser

public class Job
{
	public final boolean debug;
	
	/*private static final AntlrModuleParser PARSER = new AntlrModuleParser();
	public final AntlrModuleParser parser = Job.PARSER;*/
	
	private final JobContext jcontext;  // Mutable (Visitor passes replace modules)
	
	// FIXME: just take MainContext as arg
	//public Job(List<String> importPath, String mainpath) throws IOException, ScribbleException
	//public Job(List<String> importPath, String mainpath, Map<ModuleName, Module> modules, Module mainmodule) throws IOException, ScribbleException
	//public Job(List<Path> importPath, Path mainpath, Map<ModuleName, Module> modules, Module mainmodule) throws IOException, ScribbleException
	//public Job(MainContext mc) throws IOException, ScribbleException  // FIXME: can't take MainContext because in CLI and MainContext needs parser stuff from there (moving to core makes a recursive maven dependency, because parser needs core)
	public Job(boolean debug, Map<ModuleName, Module> parsed, ModuleName main)
	{
		this.debug = debug;
		
		//this.jcontext = new JobContext(this, importPath, mainpath, modules, mainmodule);
		this.jcontext = new JobContext(parsed, main);
	}

	public void checkWellFormedness() throws ScribbleException
	{
		debugPrintln("\n--- Context building --- ");
		runNodeVisitorPass(ContextBuilder.class);

		debugPrintln("\n--- Name disambigiation --- ");  // FIXME: verbose/debug printing parameter -- should be in MainContext, but cannot access directly from here
		runNodeVisitorPass(NameDisambiguator.class);

		/*debugPrintln("\n--- Bound name checking --- ");
		runNodeVisitorPass(BoundNameChecker.class);*/
		
		/*debugPrintln("\n--- Model building --- ");
		runNodeVisitorPass(ModelBuilder.class);*/

		debugPrintln("\n--- Well-formed choice check --- ");
		runNodeVisitorPass(WellFormedChoiceChecker.class);

		debugPrintln("\n--- Projection --- ");
		runNodeVisitorPass(Projector.class);
		//this.jcontext.buildProjectionContexts();  // Hacky? -- due to Projector not being a subprotocol visitor, so "external" subprotocols may not be visible in ModuleContext building for the projections of the current root Module
		// No: SubprotocolVisitor is an "inlining" step, it doesn't visit the target Module/ProtocolDecls -- that's why the old Projector maintained its own dependencies and created the projection modules after leaving a Do separately from SubprotocolVisiting
		// So Projection should not be an "inlining" SubprotocolVisitor, it would need to be more a "DependencyVisitor"
		buildProjectionContexts();

		//System.out.println("\n--- Reachability check --- ");
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
	
	//public void buildFsm(LProtocolDecl lpd) throws ScribbleException
	public void buildFsm(Module mod) throws ScribbleException  // Need Module for context
	{
		debugPrintln("\n--- Convert to FSMs --- ");
		mod.accept(new FsmConstructor(this));
	}
	
	//public Map<String, String> generateApi(LProtocolName lpn) throws ScribbleException
	public Map<String, String> generateApi(GProtocolName gpn, Role role) throws ScribbleException
	{
		LProtocolName lpn = Projector.makeProjectedProtocolNameNode(new GProtocolName(this.jcontext.main, gpn), role).toName();
		if (this.jcontext.getFsm(lpn) == null)  // FIXME: null hack
		{
			Module mod = this.jcontext.getModule(lpn.getPrefix());
			buildFsm(mod);
		}
		return new ApiGenerator(this, gpn, role).getClasses();  // FIXME: store results?
	}
	
	//public String generateSession(GProtocolName gpn) throws ScribbleException
	//public Set<String> generateSession(GProtocolName gpn) throws ScribbleException
	public Map<String, String> generateSession(GProtocolName gpn) throws ScribbleException
	{
		// FIXME: check gpn is valid
		//return new SessionGenerator(this, gpn).getSessionClass();
		SessionGenerator sg = new SessionGenerator(this, gpn);
		/*Set<String> classes = new HashSet<>();
		classes.add(sg.getSessionClass());
		//classes.addAll(sg.getOpClasses().values());*/
		Map<String, String> map = sg.getSessionClass();
		return map;
	}

	private void runNodeVisitorPass(Class<? extends ModelVisitor> c) throws ScribbleException
	{
		try
		{
			Constructor<? extends ModelVisitor> cons = c.getConstructor(Job.class);
			for (ModuleName modname : this.jcontext.getFullModuleNames())
			{
				ModelVisitor nv = cons.newInstance(this);
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

	/*public void runNodeEnvVisitorPass(Class<? extends EnvVisitor> c) throws ScribbleException
	{
		Constructor<? extends EnvVisitor> cons;
		try
		{
			cons = c.getConstructor(Job.class, Env.class);
			for (ModuleName mn : this.modules.keySet())
			{
				Module m = this.modules.get(mn);
				Env env = new Env(this, m);
				EnvVisitor nv = cons.newInstance(this, env);
				Module visited = (Module) nv.visit(m);
				this.modules.put(mn, visited);
			}
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}*/
	
	public JobContext getContext()
	{
		return this.jcontext;
	}

	/*private static void checkWellFormedness(JobEnv job) throws ScribbleException
	{
		job.runNodeVisitorPass(ScopeInserter.class);
		job.runNodeVisitorPass(PayloadTypeOrParameterDisambiguator.class);
		job.runNodeEnvVisitorPass(WellFormednessChecker.class);
		job.runNodeEnvVisitorPass(Projector.class);
		job.runNodeEnvVisitorPass(ReachabilityChecker.class);
	}
	
	// e.g. src = "test/src/Test.scr"
	public static JobEnv isWellFormed(List<String> importPath, String src) throws ScribbleException, IOException, RecognitionException
	{
	  JobEnv job = new JobEnv(importPath, src);
	  checkWellFormedness(job);
		return job;
	}

	// Projection is currently per module (all protocols, all roles)
	//public static Set<Module> project(List<String> importPath, String src, ProtocolName gpn, Role role) throws ScribbleException, IOException, RecognitionException
	public static Map<ProtocolName, Map<Role, Module>> project(List<String> importPath, String src) throws ScribbleException, IOException, RecognitionException
	{
		JobEnv job = isWellFormed(importPath, src);
		return job.getProjections();
	}

	// gpn is the full global protocol name
	// Doesn't collect any dependencies
	public static LocalProtocolDecl project(List<String> importPath, String src, ProtocolName gpn, Role role) throws ScribbleException, IOException, RecognitionException
	{
		JobEnv job = isWellFormed(importPath, src);
		//ProtocolName pn = Projector.getProjectedFullProtocolName(gpn, role);
		return (LocalProtocolDecl) job.getProjection(gpn, role).pds.get(0);
	}

	// gpn is the full global protocol name
	public Map<ProtocolName, State> generateGraphs(ProtocolName gpn, Role role) throws ScribbleException
	{
		checkWellFormedness(this);
		
		//Map<ProtocolName, Map<Role, Module>> ps = getProjections();
		//LocalProtocolDecl lpd = (LocalProtocolDecl) ps.get(gpn).get(role).pds.get(0);
		LocalProtocolDecl lpd = (LocalProtocolDecl) getProjection(gpn, role).pds.get(0);
		/*State root = new State();
		State term = new State();
		lpd.toGraph(new GraphBuilder(this, root, term));
		//ScribbleFSM fsm = new ScribbleFSM(Scope.ROOT_SCOPE, root);*
		Map<ProtocolName, State> graphs = new GraphBuilder(this).makeGraphs(lpd);
		//ScribbleFSM fsm = new ScribbleFSM(root);
		
		//System.out.println("FSM generated: " + graphs.get(GraphBuilder.ROOT_PROTOCOL).toDot());
		
		return graphs;
	}*/
	
	public void debugPrintln(String s)
	{
		if (this.debug)
		{
			System.out.println(s);
		}
	}
}

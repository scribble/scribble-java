package org.scribble2.model.visit;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.scribble2.model.Module;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.util.ScribbleException;

// - remove job/module contexts from Envs (refer from visitor -- can be updated during visitor pass and reassigned to root module on leave)
// - visitor pattern, delegates, envs (root, creating and assigning, merging, super calls), subprotocol visiting

// perhaps refactor to have choice/recursion/etc as packages with global/local/del/etc in each

// - streamline visitor pattern calls (e.g. accept)
// - streamline vistitor/del env references -- and del enter/leave env setter on visitors
public class Job
{
	/*private static final AntlrModuleParser PARSER = new AntlrModuleParser();
	public final AntlrModuleParser parser = Job.PARSER;*/
	
	private final JobContext jcontext;  // Mutable (Visitor passes replace modules)
	
	//public Job(List<String> importPath, String mainpath) throws IOException, ScribbleException
	public Job(List<String> importPath, String mainpath, Map<ModuleName, Module> modules, Module mainmodule) throws IOException, ScribbleException
	{
		this.jcontext = new JobContext(this, importPath, mainpath, modules, mainmodule);
	}

	public void checkWellFormedness() throws ScribbleException
	{
		System.out.println("\n--- Name disambigiation --- ");
		runNodeVisitorPass(NameDisambiguator.class);
						
		System.out.println("\n--- Context building --- ");
		runNodeVisitorPass(ContextBuilder.class);

		System.out.println("\n--- Well-formed choice check --- ");
		runNodeVisitorPass(WellFormedChoiceChecker.class);

		System.out.println("\n--- Projection --- ");
		runNodeVisitorPass(Projector.class);

		System.out.println("\n--- Reachability check --- ");
		runNodeVisitorPass(ReachabilityChecker.class);
	}

	//... duplicate job/jobcontext in cli; pass core DS to core job/jobcontext; finish name dismabiguation and other visitors ...

	public void runNodeVisitorPass(Class<? extends ModelVisitor> c) throws ScribbleException
	{
		try
		{
			Constructor<? extends ModelVisitor> cons = c.getConstructor(Job.class);
			for (ModuleName modname : this.jcontext.getFullModuleNames())
			{
				ModelVisitor nv = cons.newInstance(this);
				Module visited = (Module) this.jcontext.getModule(modname).visit(nv);
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
}

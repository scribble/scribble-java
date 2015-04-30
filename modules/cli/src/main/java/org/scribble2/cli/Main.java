// java -cp bin';'lib/antlr-3.5.2-complete.jar';'parser scribble.main.Main2 test/src/test/Test7.scr
 
package org.scribble2.cli;

import java.util.Arrays;
import java.util.List;

import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.util.ScribbleException;

public class Main
{
	public Main()
	{
	}

	public static void main(String[] args) throws Exception
	{
		//new AntlrTreeParser().parseModuleFromSource(args[0])
		List<String> importPath = Arrays.asList(new String[] {"test/src/"});
		MainContext job = new MainContext(importPath, args[0]);
		//System.out.println("1: " + new NodeVisitor(job).visit(n));
		//job.runNodeVisitorPass(NodeVisitor.class);
		
		//.. specification of session id message in protocols
		//.. rename scoped/unscoped message/protocol sigs; move scope into ScribbleMessage (uniform with sigs); organise static/dynamic scoped/unscoped sig/value classes
		
		//.. project imports according to dependencies
		//.. factor net/session packages to reflect transport-independent session resources, and transport/protocol -dependent layer
		
		//.. revisit sesstype apis, clarify better type system view of the "scribble generic parameter" kinds of types from "concrete value" kinds, decoupled from mirroring the AST hierarchy -- also revisit scoped type entities (message and protocol sigs)
		//.. maybe devolve env delegation pattern back to routines in nodes (can keep delegation for base push/pop routines and also incorporate merge pattern into Env base) -- the reason for delegation to env is convenient field access
		//.. swap env and subprotocol visitor order in hierarchy
		//.. integrate dependency building into context building (or a subsequent pass)
		//.. some uses of env setting are not immutable for the nodes
		// .. factor out WF choice and reach env visiting (env merging, env delegation methods)
		
		//.. root env not needed as special feature; protocol decl context building pattern inconsistent with other contexts? (build on enter or leave? -- or maybe Module context is inconsistent)
		//.. node reconstruct pattern doesn't clone context/env objects (mutable for convenience -- alternative is build full context/env for every node, e.g. interaction sequence)
		//.. proper context building (for all ContextNodes) -- make node (e.g. interaction node) and context objects (e.g. compound interaction contexts) consistent
		//.. integrate context potenital and par env? (e.g. merge) (integrate context and env more generally? -- current difference: contexts are built only by context builder pass and done directly inside each node class; several env passes are done with delegation of env building by node classes into the Envs) 
		//.. scopes (distinguish scope element from full scope?) -- scoped vs unscoped messages and sigs can be improved?
		//.. remove syntactic parameter distinction from type objects
		//.. check visitor->node->env call pattern (also parent merge vs non-merge env node visiting)
		//.. check node constructors vs node factory (for context/env decoration)
		//.. remove env root?
		//.. remove NodeVisitor return from node enter (or else do NodeVisitor copying)
		//.. make visitChildren override (cancel/replace) feature, e.g. project, buildgraph
		//.. job context is mutable -- refactor job/jobcontexts?

		/*try
		{
			System.out.println("\n--- Name disambigiation --- ");
			job.runNodeVisitorPass(NameDisambiguator.class);
			
			System.out.println("\n--- Node context building --- ");
			job.runNodeVisitorPass(NodeContextBuilder.class);

			System.out.println("\n--- Well-formed choice check --- ");
			job.runNodeVisitorPass(WellFormedChoiceChecker.class);

			System.out.println("\n--- Project all --- ");  // Record projections in contexts and use in reachability WF pass
			job.runNodeVisitorPass(Projector.class);

			System.out.println("\n--- Reachability check --- ");  // Record projections in contexts and use in reachability WF pass
			job.runNodeVisitorPass(ReachabilityChecker.class);
		}
		catch (ScribbleException e)
		{
			System.out.println(e.getMessage());
		}*/
	}
}

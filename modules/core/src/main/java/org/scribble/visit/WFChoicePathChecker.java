package org.scribble.visit;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.main.ScribbleException;

// Duplicated from WFChoiceChecker
// Maybe refactor as PathVisitor (extended by WF checker)
//public class WFChoicePathChecker extends UnfoldingVisitor<WFChoicePathEnv> //PathCollectionVisitor
////public class WFChoicePathChecker extends InlinedProtocolVisitor<Env<?>>  // FIXME: should be unfolding visitor? GlobalModelBuilder should be too (need a correspondence between syntax nodes and model nodes)

// ** The issue of path/trace based checking is the usual trace-equivalence vs bisimulation issue (set of traces conflates nested choice structures)
// Possibly could address (in an ad hoc way) by inductively checking for e.g. trace set equivalence after each step
@Deprecated
public class WFChoicePathChecker extends ModuleContextVisitor
{
	// N.B. using pointer equality for checking if choice previously visited
	// So UnfoldingVisitor cannot visit a clone
	// equals method identity not suitable unless Ast nodes record additional info like syntactic position
	//private Set<Choice<?>> visited = new HashSet<>();	
	
	public WFChoicePathChecker(Job job)
	{
		super(job);
	}

	@Override
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		//child.del().enterWFChoicePathCheck(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		//visited = visited.del().leaveWFChoicePathCheck(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}

	/*@Override
	protected WFChoicePathEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new WFChoicePathEnv();
	}*/

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		/*if (child instanceof Choice<?>)
		{
			return visitOverrideForChoice((InteractionSeq<?>) parent, (Choice<?>) child);
		}*/
		if (child instanceof ProtocolDecl<?>)
		{
			if (child instanceof GProtocolDecl)
			{
				return visitOverrideForGProtocolDecl((Module) parent, (GProtocolDecl) child);
			}
			else
			{
				return child;
			}
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	private GProtocolDecl visitOverrideForGProtocolDecl(Module parent, GProtocolDecl child) throws ScribbleException
	{
		/*GProtocolDecl gpd = (GProtocolDecl) child;
		GModel model = null;// getJobContext().getGlobalModel(gpd.getFullMemberName(parent));

		//System.out.println("aaa: " + model);

		HashMap<GModelState, Set<GModelState>> reach = new HashMap<>();
		HashMap<GModelState, Set<GModelState>> invreach = new HashMap<>();
		getReachability(new HashSet<>(), model.init, reach, invreach);
		
		//System.out.println("bbb1: " + reach);
		//System.out.println("bbb2: " + invreach);
		
		checkWF(new HashSet<>(), model.init, model, reach);*/
		
		return child;
	}
	
	/*private static void checkWF(Set<GModelState> seen, GModelState curr, GModel model, Map<GModelState, Set<GModelState>> reach)
	{
		if (seen.contains(curr))
		{
			return;
		}
		Collection<GModelState> succs = curr.getSuccessors();

		if (curr.isChoice())  // ** Identifying choice states and their exits is determined by syntax, not model structure 
		{
			List<GModelPath> paths = new LinkedList<>();
			getPaths(paths, new GModelPath(), curr, curr.getChoiceExit(), reach);

			System.out.println("ccc: " + curr + ", " + curr.getChoiceExit() + "\n" + paths);
			
			//...for each role, project each path (can be empty projection)
			//...check consistent enablers -- maybe not needed if enough recursion paths checked?
			//...except subject, check non-uniform continuation implies distinct (input) enabling -- this should also support non-subject output
			
			Set<Role> roles = paths.stream().flatMap((p) -> p.getRoles().stream()).collect(Collectors.toSet());

			System.out.println("ddd: " + roles);
			
			for (Role r : roles)
			{
				List<IOTrace> projs = paths.stream().map((p) -> p.project(r)).collect(Collectors.toList());

				System.out.println("eee: " + r);
				System.out.println(projs);
			}
		}

		seen.add(curr);
		for (GModelState succ : succs)
		{
			checkWF(seen, succ, model, reach);
		}
	}

	// Pre: curr is state at end of path
	private static void getPaths(List<GModelPath> paths, GModelPath path, GModelState curr, GModelState dest, Map<GModelState, Set<GModelState>> reach)
	{
		for (GModelAction a : curr.getTakeable())
		{
			GModelState succ = curr.take(a);
			if (succ.equals(dest))
			{
				paths.add(path.append(a));
			}
			else
			{
				if (reach.get(succ).contains(dest))
				{
					if (!path.containsEdge(a))
					{
						GModelPath tmp = path.append(a);
						getPaths(paths, tmp, succ, dest, reach);
					}
				}
			}
		}
	}*/
	
	/*private static void getReachability(Set<GModelState> seen, GModelState curr, Map<GModelState, Set<GModelState>> reach, Map<GModelState, Set<GModelState>> invreach)
	{
		if (seen.contains(curr))
		{
			return;
		}
		Collection<GModelState> succs = curr.getSuccessors();
		for (GModelState succ : succs)
		{
			Set<GModelState> tmp1 = reach.get(curr);
			if (tmp1 == null)
			{
				tmp1 = new HashSet<>();
				reach.put(curr, tmp1);
			}
			tmp1.add(succ);

			Set<GModelState> tmp2 = invreach.get(succ);
			if (tmp2 == null)
			{
				tmp2 = new HashSet<>();
				invreach.put(succ, tmp2);
			}
			tmp2.add(curr);
			
			Set<GModelState> tmp3 = invreach.get(curr);
			if (tmp3 != null)
			{
				for (GModelState s : tmp3)
				{
					tmp2.add(s);
					
					Set<GModelState> tmp4 = reach.get(s);
					if (tmp4 == null)  // Not needed?
					{
						tmp4 = new HashSet<>();
						reach.put(s, tmp4);
					}
					tmp4.add(succ);
				}
			}
			HashSet<GModelState> bar = new HashSet<>(seen);
			bar.add(curr);
			getReachability(bar, succ, reach, invreach);
		}
	}*/
	
	/*private ScribNode visitOverrideForChoice(InteractionSeq<?> parent, Choice<?> child) throws ScribbleException
	{
		if (child instanceof Choice<?>)
		{
			Choice<?> cho = (Choice<?>) child;
			if (!this.visited.contains(cho))
			{
				this.visited.add(cho);
				//ScribNode n = cho.visitChildren(this);
				ScribNode n = super.visit(parent, child);
				this.visited.remove(cho);
				return n;
			}
			else
			{
				return cho;
			}
		}
		else
		{
			return super.visit(parent, child);
		}
	}
	
	@Override
	protected void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	//protected void pathCollectionEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.unfoldingEnter(parent, child);
		//super.pathCollectionEnter(parent, child);
		child.del().enterWFChoicePathCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	//protected ScribNode pathCollectionLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveWFChoicePathCheck(parent, child, this, visited);
		return super.unfoldingLeave(parent, child, visited);
		//return super.pathCollectionLeave(parent, child, visited);
		/* // NOTE: deviates from standard Visitor pattern: calls super first to collect paths -- maybe refactor more "standard way" by collecting paths in a prior pass and save them in context
		visited = super.pathCollectionLeave(parent, child, visited);
		return visited.del().leaveWFChoicePathCheck(parent, child, this, visited);* /
	}*/
}

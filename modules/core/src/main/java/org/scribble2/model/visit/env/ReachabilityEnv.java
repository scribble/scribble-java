package org.scribble2.model.visit.env;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.scribble2.sesstype.name.RecursionVar;

// FIXME: nested subprotocol reachability (e.g. subprotocol cycle contained within a choice block)
public class ReachabilityEnv extends Env
{
	private final Set<RecursionVar> contlabs;  // Used to check non-tail recursion (in the presence of sequencing)
	private boolean contExitable;  // false after a continue; true if choice has an exit (false inherited for all other constructs)
	private boolean doExitable;
	
	//public ReachabilityEnv(JobContext jcontext, ModuleDelegate mcontext)
	public ReachabilityEnv()
	{
		/*super(jcontext, mcontext);
		this.contlabs = new HashSet<>();*/
		//this(jcontext, mcontext, Collections.emptySet(), true, true);
		this(Collections.emptySet(), true, true);
	}
	
	//protected ReachabilityEnv(JobContext jcontext, ModuleDelegate mcontext, ReachabilityEnv root, ReachabilityEnv parent, Set<RecursionVar> contlabs, boolean contExitable, boolean doExitable)
	//protected ReachabilityEnv(JobContext jcontext, ModuleDelegate mcontext, Set<RecursionVar> contlabs, boolean contExitable, boolean doExitable)
	protected ReachabilityEnv(Set<RecursionVar> contlabs, boolean contExitable, boolean doExitable)
	{
		//super(jcontext, mcontext, root, parent);
		//super(jcontext, mcontext);//, root, parent);
		this.contlabs = new HashSet<RecursionVar>(contlabs);
		this.contExitable = contExitable;
		this.doExitable = doExitable;
	}
	
	public boolean isExitable()
	{
		return this.contlabs.isEmpty() && this.contExitable && this.doExitable;
	}

	@Override
	public ReachabilityEnv copy()
	{
		//return new ReachabilityEnv(getJobContext(), getModuleDelegate(), getProtocolDeclEnv(), getParent(), this.contlabs, this.contExitable, this.doExitable);
		//return new ReachabilityEnv(getJobContext(), getModuleDelegate(), this.contlabs, this.contExitable, this.doExitable);
		return new ReachabilityEnv(this.contlabs, this.contExitable, this.doExitable);
	}

	@Override
	public ReachabilityEnv push()
	{
		//return (ReachabilityEnv) super.push();
		//return new ReachabilityEnv(getJobContext(), getModuleDelegate(), this.contlabs, this.contExitable, this.doExitable);
		return copy();
	}

	// Add merge to Env interface? Or make a compound Env subclass? Maybe not if merge is node type dependent (or could incorporate node type into interface)
	//public ReachabilityEnv merge(ModelNode n, ReachabilityEnv child)
	public ReachabilityEnv merge(ReachabilityEnv child)
	{
		//return merge(n, Arrays.asList(child));
		return merge(false, Arrays.asList(child));
	}

	// Shouldn't be used for Choice
	public ReachabilityEnv merge(List<ReachabilityEnv> children)
	{
		return merge(false, children);
	}

	public ReachabilityEnv mergeForChoice(List<ReachabilityEnv> children)
	{
		return merge(true, children);
	}

	// Does merge depend on choice/par etc?
	//public ReachabilityEnv merge(ModelNode n, List<ReachabilityEnv> children)
	private ReachabilityEnv merge(boolean isChoice, List<ReachabilityEnv> children)
	{
		ReachabilityEnv copy = copy();
		//boolean contExitable = (n instanceof Choice) ? false : true;
		boolean contExitable = !isChoice;
		boolean doExitable = true;
		for (ReachabilityEnv re : children)
		{
			copy.contlabs.addAll(re.contlabs);
			if (isChoice)
			{
				if (re.contExitable)
				{
					contExitable = true;
				}
			}
			else
			{
				if (!re.contExitable)
				{
					contExitable = false;
				}
			}
			if (!re.doExitable)
			{
				doExitable = false;
			}
		}
		copy.contExitable = contExitable;
		copy.doExitable = doExitable;   // FIXME: need to determine if subproto cycle is fully contained within the child context (then exitable is ok, like rec-choice case)
		return copy;
	}
	
	//public ReachabilityEnv addContinueLabel(RecursionVar recvar)
	public ReachabilityEnv enterContinue(RecursionVar recvar)
	{
		ReachabilityEnv copy = copy();
		copy.contlabs.add(recvar);
		copy.contExitable = false;
		return copy;
	}
	
	public ReachabilityEnv removeContinueLabel(RecursionVar recvar)
	{
		ReachabilityEnv copy = copy();
		copy.contlabs.remove(recvar);
		return copy;
	}
	
	/*public void enter(EnvDelegationNode en, ReachabilityChecker checker)
	{
		checker.setEnv(push());
	}

	public void leave(EnvDelegationNode en, ReachabilityChecker checker)
	{
		en.setEnv(this);
		checker.setEnv(pop());
		//en.setEnv(copy());
		//ReachabilityEnv parent = pop();
		//parent = parent.merge(en, this);  // No: blocks don't merge themselves into parent, parents take blocks and merge
		//checker.setEnv(parent);
	}

	public void leave(Choice<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> cho,
			ReachabilityChecker checker)
	{
		List<ReachabilityEnv> benvs =
				cho.blocks.stream().map((b) -> (ReachabilityEnv) b.getEnv()).collect(Collectors.toList());
		ReachabilityEnv merged = merge(cho, benvs);
		cho.setEnv(merged);
		ReachabilityEnv parent = pop();
		parent = parent.merge(cho, merged);
		checker.setEnv(parent);
	}*/
	
	/*public void enter(Recursion<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> rec,
			ReachabilityChecker checker)
	{
		ReachabilityEnv env = push();
		checker.setEnv(env);
	}*/

	/*// FIXME: check bound recvars and no continue of outer recvars in par/interruptible -- to be checked in the global nodes
	public void leave(Recursion<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> rec,
			ReachabilityChecker checker)
	{
		ReachabilityEnv merged = merge(rec, (ReachabilityEnv) rec.block.getEnv());
		merged.contlabs.remove(rec.recvar.toName());
		//merged.contExitable = this.contExitable;
		rec.setEnv(merged);
		ReachabilityEnv parent = pop();
		parent = parent.merge(rec, merged);
		checker.setEnv(parent);
	}

	public void leave(Continue cont, ReachabilityChecker checker)
	{
		ReachabilityEnv copy = checker.getEnv().copy();
		copy.contlabs.add(cont.recvar.toName());
		copy.contExitable = false;
		checker.setEnv(copy);
	}

	public void leave(Parallel<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> par,
			ReachabilityChecker checker)
	{
		List<ReachabilityEnv> benvs =
				par.blocks.stream().map((b) -> (ReachabilityEnv) b.getEnv()).collect(Collectors.toList());
		ReachabilityEnv merged = merge(par, benvs);
		par.setEnv(merged);
		ReachabilityEnv parent = pop();
		parent = parent.merge(par, merged);
		checker.setEnv(parent);
	}

	public void leave(Interruptible<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>, ? extends Interrupt> intt,
			ReachabilityChecker checker)
	{
		ReachabilityEnv merged = merge(intt, (ReachabilityEnv) intt.block.getEnv());
		intt.setEnv(merged);
		ReachabilityEnv parent = pop();
		parent.merge(intt, merged);
		checker.setEnv(parent);
	}

	public void leave(Do doo, ReachabilityChecker checker)
	{
		//leave((EnvDelegationNode) doo, checker);
		
		// FIXME: doExitable based on stack cycles

		ReachabilityEnv copy = checker.getEnv().copy();
		if (checker.isCycle())
		{
			copy.doExitable = false;
		}
		checker.setEnv(copy);
	}
	
	@Override
	public ReachabilityEnv getProtocolDeclEnv()
	{
		return (ReachabilityEnv) super.getProtocolDeclEnv();
	}*/
	
	/*@Override
	public ReachabilityEnv pop()
	{
		return (ReachabilityEnv) super.pop();
	}
	
	@Override
	public ReachabilityEnv getParent()
	{
		return (ReachabilityEnv) super.getParent();
	}*/

	/*@Override
	public String toString()
	{
	}*/
}

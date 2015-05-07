package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.util.ScribbleException;

//public abstract class Choice<T extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
public abstract class Choice<K extends Kind>
		extends CompoundInteractionNode<K>
{
	public final RoleNode subj;
	//public final List<T> blocks;
	public final List<? extends ProtocolBlock<K>> blocks;

	// All constructors being protected is a kind of "abstract class"
	/*protected Choice(CommonTree ct, RoleNode subj, List<T> blocks)
	{
		this(ct, subj, blocks, null, null);
	}

	protected Choice(CommonTree ct, RoleNode subj, List<T> blocks, CompoundInteractionNodeContext cicontext)
	{
		this(ct, subj, blocks, cicontext, null);
	}*/

	//protected Choice(RoleNode subj, List<T> blocks)//, CompoundInteractionNodeContext cicontext, Env env)
	protected Choice(RoleNode subj, List<? extends ProtocolBlock<K>> blocks)//, CompoundInteractionNodeContext cicontext, Env env)
	{
		super();//, cicontext, env);
		this.subj = subj;
		this.blocks = new LinkedList<>(blocks);
	}
	
	//protected abstract Choice<T> reconstruct(RoleNode subj, List<T> blocks);//, CompoundInteractionNodeContext cicontext, Env env);
	protected abstract Choice<K> reconstruct(RoleNode subj, List<? extends ProtocolBlock<K>> blocks);//, CompoundInteractionNodeContext cicontext, Env env);
	
	@Override
	//public Choice<T> visitChildren(ModelVisitor nv) throws ScribbleException
	public Choice<K> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		RoleNode subj = (RoleNode) visitChild(this.subj, nv);
		//List<T> blocks = visitChildListWithClassCheck(this, this.blocks, nv);
		List<? extends ProtocolBlock<K>> blocks = visitChildListWithClassCheck(this, this.blocks, nv);
		//return new Choice<T>(this.ct, subj, blocks, getContext(), getEnv());
		return reconstruct(subj, blocks);//, getContext(), getEnv());  // OK to alias the same context/env objects here? because leave should take care of making new ones as necessary -- now immutable anyway
	}

	/*@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.pushContext(new CompoundInteractionNodeContext());
		return builder;
	}

	@Override
	public Choice<T> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		CompoundInteractionNodeContext ccontext = (CompoundInteractionNodeContext) builder.popContext();
		List<ProtocolBlockContext> bcontexts =
				this.blocks.stream().map((b) -> b.getContext()).collect(Collectors.toList());
		ccontext = (CompoundInteractionNodeContext) ccontext.merge(bcontexts);
		builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).merge(ccontext));
		//return new Choice<>(this.ct, this.subj, this.blocks, ccontext);
		return reconstruct(this.ct, this.subj, this.blocks, ccontext, getEnv());
	}

	// Make abstract in AbstractCompoundInteractionNode (or AbstractEnvDelegationNode?) to force override? for Env delegation pattern (delegation methods can be specified in Env interface)
	@Override
	public WellFormedChoiceChecker enterWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().enter(this, checker);  // Using static types to do overloading -- same as using different method names, but should there be base routines in the Env class?
		return checker;
	}

	@Override
	public Choice<T> leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;  // reconstruct?
	}

	/*@Override
	public ReachabilityChecker enterReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().enter(this, checker);
		return checker;
	}*

	@Override
	public Choice<T> leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}

	/*@Override
	public Choice substitute(Substitutor subs) throws ScribbleException
	{
		@SuppressWarnings("unchecked")
		List<ProtocolBlock> tmp = (List<ProtocolBlock>) this.blocks;
		List<ProtocolBlock> blocks = subs.<ProtocolBlock>visitAll(tmp);
		RoleNode subj = subs.substituteRole(this.subj.toName());
		return new Choice(this.ct, subj, blocks);
	}
	
	@Override
	public Env enter(EnvVisitor nv) throws ScribbleException
	{
		return super.enter(nv).pushChoice(this);
	}
	
	@Override
	public Choice leave(EnvVisitor nv) throws ScribbleException
	{
		Choice gc = (Choice) super.leave(nv);  // returns this..
		nv.setEnv(nv.getEnv().popChoice(gc));
		return gc;  // ..so Global/LocalChoice is returned directly (no need to override)
	}

	@Override
	public Choice checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		return visitWithEnv(wfc);
	}

	@Override
	//public Choice project(Projector proj) throws ScribbleException
	public Node project(Projector proj) throws ScribbleException
	{
		return visitWithEnv(proj);
	}

	@Override
	public Choice checkReachability(ReachabilityChecker rc) throws ScribbleException
	{
		return visitWithEnv(rc);
	}

	// This pattern should be used by all EnvVisitors (or else Env merging won't be correct)
	protected Choice visitWithEnv(EnvVisitor nv) throws ScribbleException
	{
		Env env = nv.getEnv();
		List<ProtocolBlock> blocks = new LinkedList<>();
		for (ProtocolBlock block : this.blocks)
		{
			nv.setEnv(new Env(env));
			ProtocolBlock visited = (ProtocolBlock) nv.visit(block);
			blocks.add(visited);
		}
		nv.setEnv(env);
		return new Choice(this.ct, this.subj, blocks);
	}*
	
	@Override
	public Choice<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		RoleNode subj = (RoleNode) visitChild(this.subj, nv);
		List<T> blocks = visitChildListWithClassCheck(this, this.blocks, nv);
		//return new Choice<T>(this.ct, subj, blocks, getContext(), getEnv());
		return reconstruct(this.ct, subj, blocks, getContext(), getEnv());  // OK to alias the same context/env objects here? because leave should take care of making new ones as necessary -- now immutable anyway
	}*/
	
	//protected static Choice reconstruct(RoleNode subj, List... blocks)
	
	@Override
	public String toString()
	{
		String s = Constants.CHOICE_KW + " " + Constants.AT_KW + " " + this.subj + this.blocks.get(0);
		//for (T block : this.blocks.subList(1, this.blocks.size()))
		for (ProtocolBlock<K> block : this.blocks.subList(1, this.blocks.size()))
		{
			s += " " + Constants.OR_KW + " " + block;
		}
		return s;
	}
}

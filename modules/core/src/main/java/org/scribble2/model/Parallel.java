package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.util.ScribbleException;

public abstract class Parallel<T extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
		extends CompoundInteractionNode
{
	public final List<T> blocks;

	/*protected Parallel(CommonTree ct, List<T> blocks)
	{
		this(ct, blocks, null, null);
	}

	protected Parallel(CommonTree ct, List<T> blocks, CompoundInteractionNodeContext cicontext)
	{
		this(ct, blocks, cicontext, null);
	}*/

	protected Parallel(List<T> blocks)//, CompoundInteractionNodeContext cicontext, Env env)
	{
		this.blocks = new LinkedList<>(blocks);
	}

	protected abstract Parallel<T> reconstruct(List<T> blocks);

	@Override
	public Parallel<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		List<T> blocks = visitChildListWithClassCheck(this, this.blocks, nv);
		//return new Parallel<>(this.ct, blocks, getContext(), getEnv());
		return reconstruct(blocks);
	}

	/*@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.pushContext(new CompoundInteractionNodeContext());
		return builder;
	}

	@Override
	public Parallel<T> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		CompoundInteractionNodeContext pcontext = (CompoundInteractionNodeContext) builder.popContext();
		List<ProtocolBlockContext> bcontexts =
				this.blocks.stream().map((b) -> b.getContext()).collect(Collectors.toList());
		pcontext = (CompoundInteractionNodeContext) pcontext.merge(bcontexts);
		builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).merge(pcontext));
		//return new Parallel<>(this.ct, this.blocks, pcontext);
		return reconstruct(this.ct, this.blocks, pcontext, getEnv());
	}

	@Override
	public Parallel<T> leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}

	@Override
	public Parallel<T> leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}
	
	/*@Override
	public Parallel checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{k
		return visitWithEnv(wfc);
	}

	@Override
	public Parallel project(Projector proj) throws ScribbleException
	{
		return visitWithEnv(proj);
	}

	@Override
	public Parallel checkReachability(ReachabilityChecker rc) throws ScribbleException
	{
		return visitWithEnv(rc);
	}

	public Parallel visitWithEnv(EnvVisitor nv) throws ScribbleException
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
		return new Parallel(this.ct, blocks);
	}*/

	@Override
	public String toString()
	{
		String s = Constants.PAR_KW + " " + this.blocks.get(0);
		for (T block : this.blocks.subList(1, this.blocks.size()))
		{
			s += " " + Constants.AND_KW + " " + block;
		}
		return s;
	}
}

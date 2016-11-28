package org.scribble.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class Parallel<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	private final List<? extends ProtocolBlock<K>> blocks;

	protected Parallel(List<? extends ProtocolBlock<K>> blocks)
	{
		this.blocks = new LinkedList<>(blocks);
	}

	public abstract Parallel<K> reconstruct(List<? extends ProtocolBlock<K>> blocks);

	@Override
	public Parallel<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<? extends ProtocolBlock<K>> blocks = visitChildListWithClassEqualityCheck(this, this.blocks, nv);
		return reconstruct(blocks);
	}
	
	public List<? extends ProtocolBlock<K>> getBlocks()
	{
		return Collections.unmodifiableList(this.blocks);
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
		String sep = " " + Constants.AND_KW + " ";
		return Constants.PAR_KW + " "
					+ this.blocks.stream().map((block) -> block.toString()).collect(Collectors.joining(sep));
	}
}

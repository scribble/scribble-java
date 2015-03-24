package scribble2.ast.local;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.Interruptible;
import scribble2.ast.context.CompoundInteractionNodeContext;
import scribble2.ast.name.ScopeNode;
import scribble2.visit.GraphBuilder;
import scribble2.visit.env.Env;

public class LocalInterruptible extends Interruptible<LocalProtocolBlock, LocalInterrupt> implements LocalInteraction
{
	public LocalInterruptible(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, LocalThrows thro, List<LocalCatches> cats)
	{
		this(ct, scope, block, compileInterrupts(thro, cats), null, null);
	}

	/*public LocalInterruptible(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, List<LocalInterrupt> interrs, CompoundInteractionNodeContext icontext)
	{
		super(ct, scope, block, interrs, icontext);
	}*/

	protected LocalInterruptible(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, List<LocalInterrupt> interrs, CompoundInteractionNodeContext icontext, Env env)
	{
		super(ct, scope, block, interrs, icontext, env);
	}
	
	private static List<LocalInterrupt> compileInterrupts(LocalThrows thro, List<LocalCatches> cats)
	{
		List<LocalInterrupt> interrs = new LinkedList<>();
		if (thro != null)
		{
			interrs.add(thro);
		}
		interrs.addAll(cats);
		return interrs;
	}

	@Override
	protected LocalInterruptible reconstruct(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, List<LocalInterrupt> interrs, CompoundInteractionNodeContext icontext, Env env)
	{
		return new LocalInterruptible(ct, scope, block, interrs, icontext, env);
	}
	
	@Override
	public LocalInterruptible leaveGraphBuilding(GraphBuilder builder)
	{
		throw new RuntimeException("TODO: ");
	}

	/*@Override
	public LocalInterruptible leaveContextBuilding(Node parent, NodeContextBuilder builder) throws ScribbleException
	{
		Interruptible<LocalProtocolBlock, LocalInterrupt> intt = super.leaveContextBuilding(parent, builder);
		return new LocalInterruptible(intt.ct, intt.scope, intt.block, interrs, (CompoundInteractionNodeContext) intt.getContext());
	}

	@Override
	public LocalInterruptible leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Interruptible<LocalProtocolBlock, LocalInterrupt> intt = super.leaveWFChoiceCheck(checker);
		return new LocalInterruptible(intt.ct, intt.scope, intt.block, intt.interrs, (CompoundInteractionNodeContext) intt.getContext(), intt.getEnv());
	}

	@Override
	public LocalInterruptible visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Interruptible<LocalProtocolBlock, LocalInterrupt> intt = super.visitChildren(nv);
		return new LocalInterruptible(intt.ct, intt.scope, intt.block, intt.interrs, intt.getContext(), intt.getEnv());
	}*/
}

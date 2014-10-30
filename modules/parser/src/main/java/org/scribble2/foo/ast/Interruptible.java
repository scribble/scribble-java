package org.scribble2.foo.ast;

import java.util.List;

import org.antlr.runtime.Token;
import org.scribble2.foo.AntlrConstants;
import org.scribble2.foo.ast.name.simple.ScopeNode;

public abstract class Interruptible<
				T1 extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>, T2 extends Interrupt>
		extends ScribbleASTBase implements CompoundInteractionNode
{
	public final ScopeNode scope;
	public final T1 block;
	public final List<T2> interrs;

	/*protected Interruptible(CommonTree ct, T1 block, List<T2> interrs)
	{
		this(ct, null, block, interrs, null, null);
	}

	protected Interruptible(CommonTree ct, ScopeNode scope, T1 block, List<T2> interrs)
	{
		this(ct, scope, block, interrs, null, null);
	}*/

	/*protected Interruptible(CommonTree ct, ScopeNode scope, T1 block, List<T2> interrs, CompoundInteractionNodeContext icontext)
	{
		this(ct, scope, block, interrs, icontext, null);
	}*/

	protected Interruptible(Token t, ScopeNode scope, T1 block, List<T2> interrs)//, CompoundInteractionNodeContext icontext, Env env)
	{
		super(t);//, icontext, env);
		this.scope = scope;
		this.block = block;
		this.interrs = interrs;
	}
	
	/*protected abstract Interruptible<T1, T2> reconstruct(CommonTree ct, ScopeNode scope, T1 block, List<T2> interrs, CompoundInteractionNodeContext icontext, Env env);

	@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.pushContext(new CompoundInteractionNodeContext());
		return builder;
	}

	@Override
	public Interruptible<T1, T2> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		CompoundInteractionNodeContext icontext = (CompoundInteractionNodeContext) builder.popContext();
		builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).merge(icontext));
		icontext = (CompoundInteractionNodeContext) icontext.merge(this.block.getContext());
		//return new Interruptible<T1, T2>(this.ct, this.scope, this.block, this.interrs, icontext);
		return reconstruct(this.ct, this.scope, this.block, this.interrs, icontext, getEnv());
	}

	@Override
	public Interruptible<T1, T2> leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;

		/*Role src = this.src.toName();
		for (Message msg : this.msgs.stream().map((mn) -> mn.toMessage()).collect(Collectors.toList()))
		{
			for (Role dest : ((GlobalInterruptContext) getContext()).getDestinations())
			{
				//checker.getEnv().addInterrupt(src, dest, msg);  // No: src must be enabled, and if receiver may be enabled by this interrupt then it must also be receiving normally inside the interruptible block
				
				System.out.println("2a: " + src + ", " + dest + ", " + msg);
				
				checker.getEnv().addMessage(src, dest, msg);
			}
		}* /
	}

	@Override
	public Interruptible<T1, T2> leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}
	
	@Override
	public Interruptible<T1, T2> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ScopeNode scope = this.scope;
		if (scope != null)
		{
			scope = (ScopeNode) visitChild(this.scope, nv);
		}
		//ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>> block = (ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>) nv.visit(this.block);
		T1 block = visitChildWithClassCheck(this, this.block, nv);
		List<T2> interrs = visitChildListWithClassCheck(this, this.interrs, nv);
		//return new Interruptible<>(this.ct, scope, block, interrs, getContext(), getEnv());
		return reconstruct(this.ct, scope, block, interrs, getContext(), getEnv());
	}
	
	@Override
	public boolean isEmptyScope()
	{
		return false;
	}

	@Override
	//public Scope getScope()
	public SimpleName getScopeElement()
	{
		return this.scope.toName();
	}*/

	public boolean isScopeNodeImplicit()
	{
		return this.scope == null;
	}
	
	@Override
	public String toString()
	{
		String s = AntlrConstants.INTERRUPTIBLE_KW + " ";
		if (!isScopeNodeImplicit())
		{
			s += this.scope + " ";
		}
		s += this.block + " " + AntlrConstants.WITH_KW + " {";
		for (Interrupt interr : this.interrs)
		{
			s += "\n" + interr;
		}
		return s + "\n}";
	}
}

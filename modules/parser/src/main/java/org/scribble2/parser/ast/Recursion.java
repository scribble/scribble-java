package org.scribble2.parser.ast;

import org.antlr.runtime.Token;
import org.scribble2.parser.AntlrConstants;
import org.scribble2.parser.ast.name.simple.RecursionVarNode;

public abstract class Recursion<T extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>
		extends ScribbleASTBase implements CompoundInteractionNode
{
	public final RecursionVarNode recvar;
	public final T block;

	/*protected Recursion(CommonTree ct, RecursionVarNode recvar, T block)
	{
		this(ct, recvar, block, null, null);
	}

	protected Recursion(CommonTree ct, RecursionVarNode recvar, T block, CompoundInteractionNodeContext cicontext)
	{
		this(ct, recvar, block, cicontext, null);
	}*/

	protected Recursion(Token t, RecursionVarNode recvar, T block)//, CompoundInteractionNodeContext cicontext, Env env)
	{
		super(t);//, cicontext, env);
		this.recvar = recvar;
		this.block = block;
	}

	/*protected abstract Recursion<T> reconstruct(CommonTree ct, RecursionVarNode recvar, T block, CompoundInteractionNodeContext cicontext, Env env);

	@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.pushContext(new CompoundInteractionNodeContext());
		return builder;
	}

	@Override
	public Recursion<T> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		CompoundInteractionNodeContext rcontext = (CompoundInteractionNodeContext) builder.popContext();
		rcontext = (CompoundInteractionNodeContext) rcontext.merge(this.block.getContext());
		builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).merge(rcontext));
		//return new Recursion<>(this.ct, this.recvar, this.block, rcontext);
		return reconstruct(this.ct, this.recvar, this.block, rcontext, getEnv());
	}

	@Override
	public Recursion<T> leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}

	@Override
	public ReachabilityChecker enterReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().enter(this, checker);
		return checker;
	}

	@Override
	public Recursion<T> leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}*/
	
	/*@Override
	public Env enter(EnvVisitor nv) throws ScribbleException
	{
		Env env = new Env(super.enter(nv));
		RecursionLabel lab = this.lab.toName();
		env.labs.add(lab);

		
		env.roles.enterNonEnablingContext();
		

		return env;
	}
	
	@Override
	public Recursion leave(EnvVisitor nv) throws ScribbleException
	{
		Recursion gr = (Recursion) super.leave(nv);
		Env env = new Env(nv.getEnv());
		env.labs.remove(this.lab.toName());
		nv.setEnv(env);
		return gr;
	}*/
	
	/*@Override
	public Recursion<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		RecursionVarNode recvar = (RecursionVarNode) visitChild(this.recvar, nv);
		T block = visitChildWithClassCheck(this, this.block, nv);
		//return new Recursion<>(this.ct, recvar, block, getContext(), getEnv());
		return reconstruct(this.ct, recvar, block, getContext(), getEnv());
	}*/

	@Override
	public String toString()
	{
		return AntlrConstants.REC_KW + " " + this.recvar + " " + block;
	}
}

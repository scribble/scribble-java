package org.scribble2.foo.ast.global;

import java.util.List;

import org.antlr.runtime.Token;
import org.scribble2.foo.ast.Interruptible;
import org.scribble2.foo.ast.name.simple.ScopeNode;

public class GlobalInterruptible extends Interruptible<GlobalProtocolBlock, GlobalInterrupt> implements GlobalInteraction
{
	/*public static final Function<Interrupt, GlobalInterrupt> toGlobalInterrupt =
			(Interrupt interr)
					-> (GlobalInterrupt) interr;
					
	public static final Function<List<? extends Interrupt>, List<GlobalInterrupt>> toGlobalInterruptList =
			(List<? extends Interrupt> interrs)
					-> interrs.stream().map(GlobalInterruptible.toGlobalInterrupt).collect(Collectors.toList());*/

	/*public GlobalInterruptible(Token t, GlobalProtocolBlock block, List<GlobalInterrupt> interrs)
	{
		//this(t, null, block, interrs, null, null);
	}*/

	public GlobalInterruptible(Token t, ScopeNode scope, GlobalProtocolBlock block, List<GlobalInterrupt> interrs)
	{
		//this(ct, scope, block, interrs, null, null);
		super(t, scope, block, interrs);
	}

	/*protected GlobalInterruptible(CommonTree ct, ScopeNode scope, GlobalProtocolBlock block, List<GlobalInterrupt> interrs, CompoundInteractionNodeContext icontext)
	{
		super(ct, scope, block, interrs, icontext);
	}* /

	protected GlobalInterruptible(CommonTree ct, ScopeNode scope, GlobalProtocolBlock block, List<GlobalInterrupt> interrs, CompoundInteractionNodeContext icontext, Env env)
	{
		super(ct, scope, block, interrs, icontext, env);
	}

	@Override
	protected GlobalInterruptible reconstruct(CommonTree ct, ScopeNode scope, GlobalProtocolBlock block, List<GlobalInterrupt> interrs, CompoundInteractionNodeContext icontext, Env env)
	{
		return new GlobalInterruptible(ct, scope, block, interrs, icontext, env);
	}
	
	@Override 
	public GlobalInterruptible leaveDisambiguation(NameDisambiguator disamb) throws ScribbleException
	{
		ScopeNode scope = this.scope;
		if (scope == null)
		{
			scope = disamb.getFreshScope(); 
		}
		//return new GlobalInterruptible(this.ct, scope, this.block, this.interrs);
		return reconstruct(this.ct, scope, this.block, this.interrs, getContext(), getEnv());
	}

	@Override
	public GlobalInterruptible leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		Interruptible<GlobalProtocolBlock, GlobalInterrupt> intt = super.leaveContextBuilding(builder);
		ProtocolBlockContext bcontext = intt.block.getContext();
		// Interrupt dests building done here (in the parent) because visitor pattern for context building doesn't easily allow children (block and interrupts) to see each other's contexts
		List<GlobalInterrupt> interrs = new LinkedList<GlobalInterrupt>();
		//ProtocolDeclContext pdcontext = builder.getProtocolDeclContext();
		for (GlobalInterrupt interr : intt.interrs)
		{
			Role src = interr.src.toName();
			List<Role> dests = new LinkedList<>(bcontext.getRoles());
			// FIXME: also include other interrupt sources?
			dests.remove(src);
			GlobalInterruptContext icontext = new GlobalInterruptContext(dests);
			interrs.add(new GlobalInterrupt(interr.ct, interr.src, interr.msgs, icontext, interr.getEnv()));
			CompoundInteractionContext cicontext = (CompoundInteractionContext) builder.peekContext();
			for (Role dest : dests) // Cannot do in super Interruptible because dest info not available yet
			{
				for (MessageNode mn : interr.msgs)
				{
					Message msg = mn.toMessage();
					//((CompoundInteractionContext) builder.peekContext()).addInterrupt(src, dest, msg);
					//builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).addInterrupt(src, dest, msg));
					cicontext = cicontext.addInterrupt(src, dest, msg);
				}
			}
			builder.replaceContext(cicontext);
		}
		//return new GlobalInterruptible(intt.ct, intt.scope, intt.block, interrs, intt.getContext());
		return reconstruct(intt.ct, intt.scope, intt.block, interrs, intt.getContext(), getEnv());
	}
	
	@Override
	public GlobalInterruptible leaveProjection(Projector proj) //throws ScribbleException
	{
		ScopeNode scope = new ScopeNode(null, this.scope.toName().toString());  // Inconsistent to copy role nodes manually, but do via children visiting for other children
		LocalProtocolBlock block = (LocalProtocolBlock) ((ProjectionEnv) this.block.getEnv()).getProjection();	
		/*List<LocalInterrupt> interrs =
				this.interrs.stream().map(() -> (LocalInterrupt) ((ProjectionEnv) .getEnv()).getProjection()).collect(Collectors.toList());* /
		LocalThrows thro = null;
		List<LocalCatches> cats = new LinkedList<>();
		for (GlobalInterrupt gi : this.interrs)
		{
			LocalInterrupt li = (LocalInterrupt) ((ProjectionEnv) gi.getEnv()).getProjection();
			if (li != null)
			{
				if (li instanceof LocalThrows)	
				{
					thro = (LocalThrows) li;
				}
				else
				{
					cats.add((LocalCatches) li);
				}
			}
		}
		LocalInterruptible projection = null;
		if (!block.isEmpty() || thro != null)
		{
			projection = new LocalInterruptible(ct, scope, block, thro, cats);
		}
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	/*@Override
	public GlobalInterruptible leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Interruptible<GlobalProtocolBlock, GlobalInterrupt> intt = super.leaveWFChoiceCheck(checker);
		return new GlobalInterruptible(intt.ct, intt.scope, intt.block, intt.interrs, intt.getContext(), intt.getEnv());
	}*/

	/*@Override
	public Env enter(EnvVisitor nv) throws ScribbleException
	{
		Env env = nv.getEnv();
		if (nv instanceof WellFormednessChecker)  // Hacky
		{
			Scope scope = this.scope.toName();
			if (env.scopes.getScopes().contains(scope))
			{
				throw new ScribbleException("Duplicate scope: " + scope);
			}
		}
		return super.enter(nv);
	}
	
	@Override
	public GlobalInterruptible checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		GlobalInterruptible gi = (GlobalInterruptible) super.checkWellFormedness(wfc);  // visitChildren
		Env env = gi.block.getEnv();
		Set<Role> roles = new HashSet<>();
		for (Interrupt interr : gi.interrs)
		{
			//GlobalInterrupt interr = (GlobalInterrupt) foo;
			Role src = interr.src.toName();
			if (roles.contains(src))
			{
				throw new ScribbleException("Duplicate interrupt source role: " + src);
			}
			roles.add(src);
			if (env.ops.getSources().contains(src))
			{
				Set<Operator> ops = new HashSet<>(env.ops.getAll(src));
				List<Operator> tmp = interr.getOperators(env);
				ops.retainAll(tmp);
				if (!ops.isEmpty())
				{
					throw new ScribbleException("Bad interrupt operators: " + tmp);
				}
			}
		}
		return gi;
	}
	
	@Override
	public LocalInterruptible project(Projector proj) throws ScribbleException
	{
		// Not calling super method (doing child visiting manually)
		LocalProtocolBlock block = (LocalProtocolBlock) proj.visit(this.block);
		LocalThrows thro = null;
		List<LocalCatches> cats = new LinkedList<>();
		for (Interrupt interr : this.interrs)
		{
			if (!interr.dests.isEmpty())  // HACK: make more proper -- if no one to throw interr to, cannot throw (so don't project)
			{
				LocalNode ln = (LocalNode) proj.visit(interr);
				if (ln != null)
				{
					if (block == null)
					{
						LocalInteractionSequence lis = new LocalInteractionSequence(null, Collections.<LocalInteraction>emptyList());
						block = new LocalProtocolBlock(null, lis);
					}
	
					if (ln instanceof LocalThrows)
					{
						if (thro != null)
						{
							throw new RuntimeException("Shouldn't get in here: " + this);
						}
						thro = (LocalThrows) ln;
					}
					else
					{
						cats.add((LocalCatches) ln);
					}
				}
			}
		}
		if (block == null && thro == null && cats.isEmpty())
		{
			return null;
		}
		return new LocalInterruptible(null, this.scope, block, thro, cats);
	}*/
	
	/*@Override
	public GlobalInterruptible visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Interruptible<GlobalProtocolBlock, GlobalInterrupt> intt = super.visitChildren(nv);
		//List<GlobalInterrupt> interrs = GlobalInterruptible.toGlobalInterruptList.apply(intt.interrs);
		return new GlobalInterruptible(intt.ct, intt.scope, intt.block, intt.interrs, intt.getContext(), intt.getEnv());
	}*/
}
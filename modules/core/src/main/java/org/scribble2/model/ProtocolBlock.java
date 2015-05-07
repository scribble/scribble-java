package org.scribble2.model;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.util.ScribbleException;


//public class ProtocolBlock extends AbstractNode
//public abstract class ProtocolBlock<T extends InteractionSequence<? extends InteractionNode>> extends CompoundInteraction //extends AbstractEnvDelegationNode
public abstract class ProtocolBlock<K extends ProtocolKind> extends CompoundInteraction //extends AbstractEnvDelegationNode
{
	public final InteractionSequence<K> seq;
	//public final T seq;
	
	//private final Env env;  // Could save env on all "statements"

	public ProtocolBlock(InteractionSequence<K> seq)
	//protected ProtocolBlock(T seq)
	{
		//this(ct, is, null);
		this.seq = seq;
		//this(ct, seq, null, null);
	}

	/*protected ProtocolBlock(CommonTree ct, T seq, ProtocolBlockContext bcontext)
	{
		this(ct, seq, bcontext, null);
	}

	//protected ProtocolBlock(CommonTree ct, T seq, ProtocolBlockContext bcontext, Env env)
	protected ProtocolBlock()//, T seq)
	{
		//this(ct, is, null);
		//this.seq = seq;
	}*/
	
	//protected abstract ProtocolBlock<T> reconstruct(T seq);//, ProtocolBlockContext bcontext, Env env);
	protected abstract ProtocolBlock<K> reconstruct(InteractionSequence<K> seq);//, ProtocolBlockContext bcontext, Env env);

	@Override
	public ProtocolBlock<K> visitChildren(ModelVisitor nv) throws ScribbleException
	//public ProtocolBlock<T> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//T seq = visitChildWithClassCheck(this, this.seq, nv);
		InteractionSequence<K> seq = visitChildWithClassCheck(this, this.seq, nv);
		//return new ProtocolBlock<>(this.ct, seq, getContext(), getEnv());
		return reconstruct(seq);//, getContext(), getEnv());
	}
	
	public boolean isEmpty()
	{
		return this.seq.isEmpty();
	}
	
	/*@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.pushContext(new ProtocolBlockContext());
		return builder;
	}

	// Unlike for CompoundInteractionNodes, context is not merged into parent (e.g. for a choice with multiple blocks, don't want to merge directly from each block)
	@Override
	public ProtocolBlock<T> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		/*this.setContext(builder.popContext());
		return this;*
		//return new ProtocolBlock<T>(this.ct, this.seq, (ProtocolBlockContext) builder.popContext(), null);
		return reconstruct(this.ct, this.seq, (ProtocolBlockContext) builder.popContext(), getEnv());
	}
	
	public boolean isEmpty()
	{
		return this.seq.isEmpty();
	}
	
	/* //protected ProtocolBlock(CommonTree ct, InteractionSequence<T> is, VisitorEnv env)
	protected ProtocolBlock(CommonTree ct, InteractionSequence is, Env env)
	{
		super(ct);
		this.seq = is;
		this.env = env;
	}*/
	
	/*@Override
	//public ProtocolBlock<T> leave(NodeEnvVisitor nv) throws ScribbleException
	public ProtocolBlock leave(EnvVisitor nv) throws ScribbleException
	{
		ProtocolBlock block = (ProtocolBlock) super.leave(nv);
		/*this.env = new VisitorEnv(nv.getVisitorEnv());
		return block;*
		return new ProtocolBlock(block.ct, block.seq, new Env(nv.getEnv()));
	}*
	
	@Override
	public ProtocolBlockContext getContext()
	{
		return (ProtocolBlockContext) super.getContext();
	}*/
	
	/*public void setEnv(VisitorEnv env)
	{
		this.env = env;
	}*/

	/*public Env getEnv()
	{
		return this.env;
	}*/

	@Override
	public String toString()
	{
		return "{\n" + this.seq + "\n}";
	}
}

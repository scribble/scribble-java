package org.scribble2.model.global;

import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.kind.GlobalKind;

//public class GlobalProtocolBlock extends ProtocolBlock<GlobalInteractionSequence> implements GlobalNode
public class GlobalProtocolBlock extends ProtocolBlock<GlobalKind> implements GlobalNode
{
	/*public static final Function<ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>, GlobalProtocolBlock>
			toGlobalProtocolBlock =
					(ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>> block)
							-> (GlobalProtocolBlock) block;*/

	/*public static final Function<List<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>, List<GlobalProtocolBlock>>
			toGlobalProtocolBlockList =
					(List<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> blocks)
							//-> blocks.stream().map(GlobalChoice.toGlobalProtocolBlock).collect(Collectors.toList());
							-> Util.listCast(blocks, toGlobalProtocolBlock);*/

	//public GlobalProtocolBlock(GlobalInteractionSequence seq)
	public GlobalProtocolBlock(InteractionSequence<GlobalKind> seq)
	{
		//this(t, gis, null, null);
		super(seq);
		//super(ct, gis);
	}

	@Override
	//protected ProtocolBlock<GlobalInteractionSequence> reconstruct(GlobalInteractionSequence seq)
	protected GlobalProtocolBlock reconstruct(InteractionSequence<GlobalKind> seq)
	{
		ModelDelegate del = del();
		//GlobalProtocolBlock gpb = new GlobalProtocolBlock(seq);
		GlobalProtocolBlock gpb = new GlobalProtocolBlock(seq);
		gpb = (GlobalProtocolBlock) gpb.del(del);
		return gpb;
	}

	@Override
	protected GlobalProtocolBlock copy()
	{
		return new GlobalProtocolBlock(this.seq);
	}

	/*public GlobalProtocolBlock(CommonTree ct, GlobalInteractionSequence gis, ProtocolBlockContext bcontext)
	{
		super(ct, gis, bcontext);
	}*/

	/*protected GlobalProtocolBlock(CommonTree ct, GlobalInteractionSequence gis, ProtocolBlockContext bcontext, Env env)
	{
		super(ct, gis, bcontext, env);
	}

	@Override
	protected GlobalProtocolBlock reconstruct(CommonTree ct, GlobalInteractionSequence seq, ProtocolBlockContext bcontext, Env env)
	{
		return new GlobalProtocolBlock(ct, seq, bcontext, env);
	}
	
	@Override
	public GlobalProtocolBlock leaveProjection(Projector proj) //throws ScribbleException
	{
		LocalInteractionSequence lis = (LocalInteractionSequence) ((ProjectionEnv) this.seq.getEnv()).getProjection();
		LocalProtocolBlock projection = new LocalProtocolBlock(null, lis);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	/*@Override
	public GlobalProtocolBlock leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		ProtocolBlock<GlobalInteractionSequence> block = super.leaveContextBuilding(builder);
		return new GlobalProtocolBlock(block.ct, block.seq, block.getContext());
	}*/
	
	/*@Override
	public LocalProtocolBlock project(Projector proj) throws ScribbleException
	{
		/*RoleCollector rc = new RoleCollector(proj.job, proj.getEnv());  // env only used for subprotocol stack
		rc.visit(this.seq);
		if (!rc.getRoles().contains(proj.getRole())) // Handles projection of continue
		{
			return null;
		}*
		LocalInteractionSequence seq = (LocalInteractionSequence) proj.visit(this.seq);
		if (seq == null)
		{
			return null;
		}
		return new LocalProtocolBlock(null, seq);
	}*/
	
	/*@Override
	public GlobalProtocolBlock visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ProtocolBlock<GlobalInteractionSequence> block = super.visitChildren(nv);
		return new GlobalProtocolBlock(block.ct, (GlobalInteractionSequence) block.seq, block.getContext(), block.getEnv());
	}*/
}

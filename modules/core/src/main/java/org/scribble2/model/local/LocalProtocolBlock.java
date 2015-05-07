package org.scribble2.model.local;

import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.kind.Local;

//public class LocalProtocolBlock extends ProtocolBlock<LocalInteractionSequence> implements LocalNode
public class LocalProtocolBlock extends ProtocolBlock<Local> implements LocalNode
{
	//public LocalProtocolBlock(LocalInteractionSequence seq)
	public LocalProtocolBlock(InteractionSequence<Local> seq)
	{
		super(seq);
	}

	@Override
	//protected LocalProtocolBlock reconstruct(LocalInteractionSequence seq)
	protected LocalProtocolBlock reconstruct(InteractionSequence<Local> seq)
	{
		ModelDelegate del = del();
		LocalProtocolBlock lpb = new LocalProtocolBlock(seq);
		lpb = (LocalProtocolBlock) lpb.del(del);
		return lpb;
	}

	@Override
	protected LocalProtocolBlock copy()
	{
		return new LocalProtocolBlock(this.seq);
	}

	/*@Override
	public LocalProtocolBlock leaveContextBuilding(Node parent, NodeContextBuilder builder) throws ScribbleException
	{
		ProtocolBlock<LocalInteractionSequence> block = super.leaveContextBuilding(parent, builder);
		return new LocalProtocolBlock(block.ct, block.seq, (CompoundInteractionNodeContext) block.getContext(), null);
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
	public LocalProtocolBlock visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ProtocolBlock<LocalInteractionSequence> block = super.visitChildren(nv);
		return new LocalProtocolBlock(block.ct, (LocalInteractionSequence) block.seq, (CompoundInteractionNodeContext) block.getContext(), getEnv());
	}*/
}

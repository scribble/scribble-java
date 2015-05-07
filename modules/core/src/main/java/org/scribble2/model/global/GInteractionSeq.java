package org.scribble2.model.global;

import java.util.List;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSeq;
import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.kind.Global;

//public class GlobalInteractionSequence extends InteractionSequence<GlobalInteraction>
public class GInteractionSeq extends InteractionSeq<Global> implements GlobalNode
{
	//public GlobalInteractionSequence(List<GlobalInteractionNode> actions)
	public GInteractionSeq(List<? extends InteractionNode<Global>> actions)
	{
		super(actions);
	}

	@Override
	protected GInteractionSeq reconstruct(List<? extends InteractionNode<Global>> ins)
	{
		ModelDel del = del();
		GInteractionSeq gis = new GInteractionSeq(ins);
		gis = (GInteractionSeq) gis.del(del);
		return gis;
	}

	@Override
	protected GInteractionSeq copy()
	{
		return new GInteractionSeq(this.actions);
	}

	/*@Override
	protected GlobalInteractionSequence reconstruct(CommonTree ct, List<GlobalInteraction> gis)
	{
		return new GlobalInteractionSequence(ct, gis);
	}
	
	@Override
	public GlobalInteractionSequence leaveProjection(Projector proj) //throws ScribbleException
	{
		List<LocalInteraction> lis = new LinkedList<>();
			//this.actions.stream().map((action) -> (LocalInteraction) ((ProjectionEnv) ((LocalNode) action).getEnv()).getProjection()).collect(Collectors.toList());	
		for (GlobalInteraction gi : this.actions)
		{
			LocalNode ln = (LocalNode) ((ProjectionEnv) ((GlobalNode) gi).getEnv()).getProjection();
			if (ln instanceof LocalInteractionSequence)
			{
				lis.addAll(((LocalInteractionSequence) ln).actions);
			}
			else if (ln != null)
			{
				lis.add((LocalInteraction) ln);
			}
		}
		LocalInteractionSequence projection = new LocalInteractionSequence(null, lis);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/
	
	/*@Override
	public LocalInteractionSequence project(Projector proj) throws ScribbleException
	{
		List<LocalInteraction> lis = new LinkedList<>();
		for (InteractionNode in : this.ins)
		{
			LocalNode visited = (LocalNode) proj.visit(in);
			if (visited != null)
			{
				if (visited instanceof LocalInteractionSequence)  // "Merged" global choice and LocalSend/Receive (self-comm)
				{
					LocalInteractionSequence seq = (LocalInteractionSequence) visited;
					for (InteractionNode tmp : seq.ins)
					{
						lis.add((LocalInteraction) tmp);
					}
				}
				else
				{
					lis.add((LocalInteraction) visited);
				}
			}
		}
		if (lis.isEmpty())
		{
			return null;
		}
		return new LocalInteractionSequence(null, lis);
	}*/

	/*@Override
	public GlobalInteractionSequence visitChildren(NodeVisitor nv) throws ScribbleException
	{
		/*@SuppressWarnings("unchecked")
		List<GlobalInteraction> tmp = (List<GlobalInteraction>) this.ins;
		List<GlobalInteraction> gis = nv.<GlobalInteraction>visitAll(tmp);
		return new GlobalInteractionSequence(this.ct, gis);*
		InteractionSequence<GlobalInteraction> is = super.visitChildren(nv);
		return new GlobalInteractionSequence(this.ct, is.actions);
	}*/
}

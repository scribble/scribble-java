package org.scribble2.model.local;

import java.util.List;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.kind.LocalKind;

//public class GlobalInteractionSequence extends InteractionSequence<GlobalInteraction>
public class LocalInteractionSequence extends InteractionSequence<LocalKind> implements LocalNode
{
	//public LocalInteractionSequence(List<LocalInteractionNode> lis)
	public LocalInteractionSequence(List<? extends InteractionNode<LocalKind>> lis)
	{
		super(lis);
	}

	@Override
	protected LocalInteractionSequence reconstruct(List<? extends InteractionNode<LocalKind>> actions)
	{
		ModelDelegate del = del();
		LocalInteractionSequence lis = new LocalInteractionSequence(actions);
		lis = (LocalInteractionSequence) lis.del(del);
		return lis;
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LocalInteractionSequence(this.actions);
	}

	/*// Alternative to overriding visit(Children) is to set Env copies on every interaction node and go through the sequence after visiting all children in leave
	@Override
	public LocalInteractionSequence visitForReachabilityChecking(ReachabilityChecker checker) throws ScribbleException
	{
		//List<T> actions = visitChildListWithClassCheck(this, this.actions, nv);  // OK to require all nodes to keep the same class? Maybe better to leave abstract and implement in the global/local subclasses
		List<LocalInteraction> visited = new LinkedList<>();
		for (LocalInteraction li : this.actions)
		{
			ReachabilityEnv re = checker.getEnv();
			if (!re.isExitable())
			{
				throw new ScribbleException("Bad sequence to: " + li);
			}
			visited.add((LocalInteraction) li.visit(checker));
		}
		return reconstruct(this.ct, actions);
	}*/

	/*@Override
	public LocalInteractionSequence leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		return (LocalInteractionSequence) super.leaveReachabilityCheck(checker);
	}*/
	
	/*@Override
	public LocalInteractionSequence visitForGraphBuilding(GraphBuilder builder)
	{
		ProtocolState entry = builder.getEntry();
		ProtocolState exit = builder.getExit();
		// Backwards for "tau-less" continue
		for (int i = this.actions.size() - 1; i >= 0; i--)
		{
			try
			{
				if (i > 0)
				{
						ProtocolState tmp = new ProtocolState();
						builder.setEntry(tmp);
						this.actions.get(i).visit(builder);
						builder.setExit(builder.getEntry());  // entry may not be tmp, entry/exit can be modified, e.g. continue
				}
				else
				{
					builder.setEntry(entry);
					this.actions.get(i).visit(builder);
				}
			}
			catch (ScribbleException e)
			{
				throw new RuntimeException("Shouldn't get in here: " + e);
			}
		}
		builder.setExit(exit);
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
	public LocalInteractionSequence visitChildren(NodeVisitor nv) throws ScribbleException
	{
		/*@SuppressWarnings("unchecked")
		List<GlobalInteraction> tmp = (List<GlobalInteraction>) this.ins;
		List<GlobalInteraction> gis = nv.<GlobalInteraction>visitAll(tmp);
		return new GlobalInteractionSequence(this.ct, gis);*
		InteractionSequence<LocalInteraction> is = super.visitChildren(nv);
		return new LocalInteractionSequence(this.ct, is.actions);
	}*/
}

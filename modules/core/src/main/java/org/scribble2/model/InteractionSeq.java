package org.scribble2.model;

import java.util.List;

import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.util.ScribbleException;


//public class InteractionSequence extends AbstractNode
public abstract class InteractionSeq<K extends ProtocolKind> extends ModelNodeBase
{
	//public final List<? extends InteractionNode> actions;
	public final List<? extends InteractionNode<K>> actions;

	//public InteractionSequence(CommonTree ct, List<? extends InteractionNode> ins)
	protected InteractionSeq(List<? extends InteractionNode<K>> actions)
	{
		this.actions = actions;
	}
	
	protected abstract InteractionSeq<K> reconstruct(List<? extends InteractionNode<K>> ins);
	
	@Override
	//public InteractionSequence visitChildren(NodeVisitor nv) throws ScribbleException
	public InteractionSeq<K> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		List<? extends InteractionNode<K>> actions = visitChildListWithClassCheck(this, this.actions, nv);  // OK to require all nodes to keep the same class? Maybe better to leave abstract and implement in the global/local subclasses
		//return new InteractionSequence<T>(this.ct, actions);
		return reconstruct(actions);
	}
	
	public boolean isEmpty()
	{
		return this.actions.isEmpty();
	}
	
	@Override
	public String toString()
	{
		//List<? extends InteractionNode> ins = getSequence();
		if (this.actions.isEmpty())
		{
			return "";
		}
		String s = actions.get(0).toString();
		for (InteractionNode<K> in : actions.subList(1, actions.size()))
		{
			s += "\n" + in;
		}
		return s;
	}
}

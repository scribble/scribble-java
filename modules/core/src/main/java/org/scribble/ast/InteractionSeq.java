package org.scribble.ast;

import java.util.List;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;


public abstract class InteractionSeq<K extends ProtocolKind> extends ScribNodeBase
{
	public final List<? extends InteractionNode<K>> actions;

	protected InteractionSeq(List<? extends InteractionNode<K>> actions)
	{
		this.actions = actions;
	}
	
	protected abstract InteractionSeq<K> reconstruct(List<? extends InteractionNode<K>> ins);
	
	@Override
	public InteractionSeq<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<? extends InteractionNode<K>> actions = visitChildListWithClassCheck(this, this.actions, nv);
		return reconstruct(actions);
	}
	
	public abstract List<? extends InteractionNode<K>> getNodes();
	
	public boolean isEmpty()
	{
		return this.actions.isEmpty();
	}
	
	@Override
	public String toString()
	{
		if (this.actions.isEmpty())
		{
			return "";
		}
		String s = this.actions.get(0).toString();
		for (InteractionNode<K> in : this.actions.subList(1, this.actions.size()))
		{
			s += "\n" + in;
		}
		return s;
	}
}

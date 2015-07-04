package org.scribble.ast;

import java.util.List;
import java.util.function.Function;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;


public abstract class InteractionSeq<K extends ProtocolKind> extends ScribNodeBase implements ProtocolKindNode<K>
{
	public final List<? extends InteractionNode<K>> actions;

	protected InteractionSeq(List<? extends InteractionNode<K>> actions)
	{
		this.actions = actions;
	}
	
	public abstract InteractionSeq<K> reconstruct(List<? extends InteractionNode<K>> ins);
	
	@Override
	public ScribNode visitChildren(AstVisitor nv) throws ScribbleException
	{
		//List<? extends InteractionNode<K>> actions = visitChildListWithStrictClassCheck(this, this.actions, nv);
		List<InteractionNode<K>> actions = visitChildListWithCastCheck(this, this.actions, nv, InteractionNode.class, getKind(), this.cast);
		return reconstruct(actions);
	}
	
	@SuppressWarnings("unchecked")
	private Function<ScribNode, InteractionNode<K>> cast = (n) -> (InteractionNode<K>) n;
	
	public abstract List<? extends InteractionNode<K>> getActions();
	
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

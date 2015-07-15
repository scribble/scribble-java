package org.scribble.ast;

import java.util.List;
import java.util.function.Function;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class InteractionSeq<K extends ProtocolKind> extends ScribNodeBase implements ProtocolKindNode<K>
{
	private final List<? extends InteractionNode<K>> inters;
	
	@SuppressWarnings("unchecked")
	private final Function<ScribNode, InteractionNode<K>> cast = (n) -> (InteractionNode<K>) n;

	protected InteractionSeq(List<? extends InteractionNode<K>> inters)
	{
		this.inters = inters;
	}
	
	public abstract InteractionSeq<K> reconstruct(List<? extends InteractionNode<K>> ins);
	
	@Override
	public ScribNode visitChildren(AstVisitor nv) throws ScribbleException
	{
		//List<? extends InteractionNode<K>> actions = visitChildListWithStrictClassCheck(this, this.actions, nv);
		List<InteractionNode<K>> actions = visitChildListWithCastCheck(this, this.inters, nv, InteractionNode.class, getKind(), this.cast);
		return reconstruct(actions);
	}
	
	public List<? extends InteractionNode<K>> getInteractions()
	{
		return this.inters;
	}
	
	public boolean isEmpty()
	{
		return this.inters.isEmpty();
	}
	
	@Override
	public String toString()
	{
		if (this.inters.isEmpty())
		{
			return "";
		}
		StringBuilder sb = new StringBuilder(this.inters.get(0).toString());
		this.inters.subList(1, this.inters.size()).stream().forEach((in) ->
				{
					sb.append("\n" + in);
				});
		return sb.toString();
	}
}

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
		List<InteractionNode<K>> actions = visitChildListWithCastCheck(InteractionNode.class, getKind(), this.cast, this, this.actions, nv);
		return reconstruct(actions);
	}
	
	private Function<ScribNode, InteractionNode<K>> cast =
			new Function<ScribNode, InteractionNode<K>>()
			{
				@Override
				public InteractionNode<K> apply(ScribNode t)
				{
					@SuppressWarnings("unchecked")
					InteractionNode<K> tmp = (InteractionNode<K>) t;
					return tmp;
				}
			};
	
	/*@SuppressWarnings("unchecked")
	private List<InteractionNode<K>> castNodes(@SuppressWarnings("rawtypes") List<InteractionNode> ins)
	{
		if (!ins.isEmpty())
		{
			InteractionNode<?> in = ins.get(0);
			if ((isGlobal() && !in.isGlobal()) || (isLocal() && !in.isLocal()))  // Didn't check every element
			{
				throw new RuntimeException("Shouldn't get in here: " + ins);
			}
		}
		return ins.stream().map((in) -> (InteractionNode<K>) in).collect(Collectors.toList());
	}*/
	
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

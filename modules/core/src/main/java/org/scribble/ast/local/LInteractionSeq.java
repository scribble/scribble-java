package org.scribble.ast.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.util.ScribUtil;


public class LInteractionSeq extends InteractionSeq<Local> implements LNode
{
	public LInteractionSeq(List<LInteractionNode> lis)
	{
		super(lis);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LInteractionSeq(getActions());
	}
	
	@Override
	public LInteractionSeq clone()
	{
		List<LInteractionNode> lis = ScribUtil.cloneList(getActions());
		return AstFactoryImpl.FACTORY.LInteractionSeq(lis);
	}

	@Override
	public LInteractionSeq reconstruct(List<? extends InteractionNode<Local>> actions)
	{
		ScribDel del = del();
		LInteractionSeq lis = new LInteractionSeq(castNodes(actions));
		lis = (LInteractionSeq) lis.del(del);
		return lis;
	}
	
	@Override
	public List<LInteractionNode> getActions()
	{
		return castNodes(this.actions);
	}

	@Override	
	public boolean isLocal()
	{
		return true;
	}
	
	private static List<LInteractionNode> castNodes(List<? extends InteractionNode<Local>> nodes)
	{
		return nodes.stream().map((n) -> (LInteractionNode) n).collect(Collectors.toList());
	}
}

package org.scribble.ast.local;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.Message;
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
		return new LInteractionSeq(getInteractions());
	}
	
	@Override
	public LInteractionSeq clone()
	{
		List<LInteractionNode> lis = ScribUtil.cloneList(getInteractions());
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
	public List<LInteractionNode> getInteractions()
	{
		return castNodes(super.getInteractions());
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
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LNode.super.getKind();
	}

	public Set<Message> getEnabling()
	{
		if (!this.isEmpty())
		{
			for (LInteractionNode ln : getInteractions())
			{
				Set<Message> enab = ln.getEnabling();
				if (!enab.isEmpty())
				{
					return enab;
				}
			}
		}
		return Collections.emptySet();
	}
}

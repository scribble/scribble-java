package org.scribble.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.InteractionSeq;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.util.ScribUtil;

public class GInteractionSeq extends InteractionSeq<Global> implements GNode
{
	public GInteractionSeq(List<GInteractionNode> actions)
	{
		super(actions);
	}

	@Override
	protected GInteractionSeq copy()
	{
		return new GInteractionSeq(getActions());
	}
	
	@Override
	public GInteractionSeq clone()
	{
		List<GInteractionNode> gis = ScribUtil.cloneList(getActions());
		return AstFactoryImpl.FACTORY.GInteractionSeq(gis);
	}

	@Override
	public GInteractionSeq reconstruct(List<? extends InteractionNode<Global>> ins)
	{
		ScribDel del = del();
		GInteractionSeq gis = new GInteractionSeq(castNodes(ins));
		gis = (GInteractionSeq) gis.del(del);
		return gis;
	}
	
	@Override
	public List<GInteractionNode> getActions()
	{
		return castNodes(this.actions);
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return Global.KIND;
	}
	
	private static List<GInteractionNode> castNodes(List<? extends InteractionNode<Global>> nodes)
	{
		return nodes.stream().map((n) -> (GInteractionNode) n).collect(Collectors.toList());
	}
}

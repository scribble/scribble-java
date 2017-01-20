package org.scribble.ext.f17.ast.global;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.f17.ast.AnnotNode;
import org.scribble.ext.f17.ast.ScribAnnot;
import org.scribble.util.ScribUtil;

public class AnnotGMessageTransfer extends GMessageTransfer implements AnnotNode
{
	public final ScribAnnot annot;  // null if none  // FIXME: refactor properly
	
	public AnnotGMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		//super(source, src, msg, dests);
		this(source, src, msg, dests, null);
	}

	// FIXME: make an empty annot? (avoid null)
	public AnnotGMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests, ScribAnnot annot)
	{
		super(source, src, msg, dests);
		this.annot = annot;
	}

	@Override
	public boolean isAnnotated()
	{
		return this.annot != null;
	}

	@Override
	public ScribAnnot getAnnotation()
	{
		return this.annot;
	}

	/*public LNode project(Role self)
	{
		Role srcrole = this.src.toName();
		List<Role> destroles = this.getDestinationRoles();
		LNode projection = null;
		if (srcrole.equals(self) || destroles.contains(self))
		{
			RoleNode src = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.src.getSource(), RoleKind.KIND, this.src.toName().toString());  // clone?
			MessageNode msg = (MessageNode) this.msg.project();  // FIXME: need namespace prefix update?
			List<RoleNode> dests =
					this.getDestinations().stream().map((rn) ->
							(RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(rn.getSource(), RoleKind.KIND, rn.toName().toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LSend(this.source, src, msg, dests);
			}
			if (destroles.contains(self))
			{
				if (projection == null)
				{
					projection = AstFactoryImpl.FACTORY.LReceive(this.source, src, msg, dests);
				}
				else
				{
					LReceive lr = AstFactoryImpl.FACTORY.LReceive(this.source, src, msg, dests);
					List<LInteractionNode> lis = Arrays.asList(new LInteractionNode[]{(LInteractionNode) projection, lr});
					projection = AstFactoryImpl.FACTORY.LInteractionSeq(this.source, lis);
				}
			}
		}
		return projection;
	}*/

	@Override
	protected AnnotGMessageTransfer copy()
	{
		return new AnnotGMessageTransfer(this.source, this.src, this.msg, getDestinations(), this.annot);
	}
	
	@Override
	public AnnotGMessageTransfer clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		List<RoleNode> dests = ScribUtil.cloneList(getDestinations());
		return AstFactoryImpl.FACTORY.GMessageTransfer(this.source, src, msg, dests, this.annot);  // Not cloning String annot
	}

	@Override
	public AnnotGMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		AnnotGMessageTransfer gmt = new AnnotGMessageTransfer(this.source, src, msg, dests, this.annot);
		gmt = (AnnotGMessageTransfer) gmt.del(del);
		return gmt;
	}

	/*// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}*/

	@Override
	public String toString()
	{
		return super.toString() + (isAnnotated() ? this.annot : "");
	}
}

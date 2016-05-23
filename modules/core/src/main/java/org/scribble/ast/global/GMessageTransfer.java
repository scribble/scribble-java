package org.scribble.ast.global;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.local.LReceive;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class GMessageTransfer extends MessageTransfer<Global> implements GSimpleInteractionNode
{
	public GMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(src, msg, dests);
	}

	public LNode project(Role self)
	{
		Role srcrole = this.src.toName();
		List<Role> destroles = this.getDestinationRoles();
		LNode projection = null;
		if (srcrole.equals(self) || destroles.contains(self))
		{
			RoleNode src = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.src.toName().toString());
			MessageNode msg = (MessageNode) this.msg;  // FIXME: need namespace prefix update?
			List<RoleNode> dests =
					destroles.stream().map((d) ->
							(RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, d.toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LSend(src, msg, dests);
			}
			if (destroles.contains(self))
			{
				if (projection == null)
				{
					projection = AstFactoryImpl.FACTORY.LReceive(src, msg, dests);
				}
				else
				{
					LReceive lr = AstFactoryImpl.FACTORY.LReceive(src, msg, dests);
					List<LInteractionNode> lis = Arrays.asList(new LInteractionNode[]{(LInteractionNode) projection, lr});
					projection = AstFactoryImpl.FACTORY.LInteractionSeq(lis);
				}
			}
		}
		return projection;
	}


	@Override
	protected GMessageTransfer copy()
	{
		return new GMessageTransfer(this.src, this.msg, getDestinations());
	}
	
	@Override
	public GMessageTransfer clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		List<RoleNode> dests = ScribUtil.cloneList(getDestinations());
		return AstFactoryImpl.FACTORY.GMessageTransfer(src, msg, dests);
	}

	@Override
	public GMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		GMessageTransfer gmt = new GMessageTransfer(src, msg, dests);
		gmt = (GMessageTransfer) gmt.del(del);
		return gmt;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + this.src + " " + Constants.TO_KW + " "
					+ getDestinations().stream().map((dest) -> dest.toString()).collect(Collectors.joining(", ")) + ";";
	}
}

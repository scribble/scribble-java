package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.AstVisitor;


public abstract class MessageTransfer<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	public final RoleNode src;
	public final MessageNode msg;
	public final List<RoleNode> dests;

	protected MessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		this.src = src;
		this.msg = msg;
		this.dests = new LinkedList<>(dests);
	}

	protected abstract MessageTransfer<K> reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests);

	@Override
	public MessageTransfer<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		List<RoleNode> dests = visitChildListWithClassCheck(this, this.dests, nv);
		return reconstruct(src, msg, dests);
	}
	
	public List<Role> getDestinationRoles()
	{
		return this.dests.stream().map((rn) -> rn.toName()).collect(Collectors.toList());
	}
}
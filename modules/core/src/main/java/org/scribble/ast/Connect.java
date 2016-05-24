package org.scribble.ast;

import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

// FIXME: factor with MessageTransfer
public abstract class Connect<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	public final RoleNode src;
	//public final MessageNode msg;
	public final RoleNode dest;

	//protected Connect(RoleNode src, MessageNode msg, RoleNode dest)
	protected Connect(RoleNode src, RoleNode dest)
	{
		this.src = src;
		//this.msg = msg;
		this.dest = dest;
	}

	//public abstract Connect<K> reconstruct(RoleNode src, MessageNode msg, RoleNode dest);
	public abstract Connect<K> reconstruct(RoleNode src, RoleNode dest);

	@Override
	public Connect<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		//MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		RoleNode dest = (RoleNode) visitChild(this.dest, nv);
		//return reconstruct(src, msg, dest);
		return reconstruct(src, dest);
	}
	
	@Override
	public abstract String toString();
}

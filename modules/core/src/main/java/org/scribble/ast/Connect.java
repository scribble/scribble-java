package org.scribble.ast;

import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class Connect<K extends ProtocolKind> extends SimpleInteractionNode<K>
{
	public final RoleNode src;
	public final RoleNode dest;

	protected Connect(RoleNode src, RoleNode dest)
	{
		this.src = src;
		this.dest = dest;
	}

	public abstract Connect<K> reconstruct(RoleNode src, RoleNode dest);

	@Override
	public Connect<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		RoleNode dest = (RoleNode) visitChild(this.dest, nv);
		return reconstruct(src, dest);
	}
	
	@Override
	public abstract String toString();
}

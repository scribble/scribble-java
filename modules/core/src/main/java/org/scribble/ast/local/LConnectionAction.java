package org.scribble.ast.local;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ConnectionAction;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.kind.Local;

public abstract class LConnectionAction extends ConnectionAction<Local> implements LSimpleInteractionNode
{
	public LConnectionAction(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	{
		super(source, src, msg, dest);
	}
}

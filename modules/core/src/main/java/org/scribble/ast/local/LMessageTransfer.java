package org.scribble.ast.local;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.kind.Local;

public abstract class LMessageTransfer extends MessageTransfer<Local> implements LSimpleInteractionNode
{
	public LMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source, src, msg, dests);
	}
}

package org.scribble.ast.name.qualified;

import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;

public abstract class ProtocolNameNode<K extends ProtocolKind> extends MemberNameNode<K>
{
	public ProtocolNameNode(String... ns)
	{
		super(ns);
	}

	public abstract ProtocolName<K> toName();
}

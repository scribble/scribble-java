package org.scribble.ast.name.qualified;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;

public abstract class ProtocolNameNode<K extends ProtocolKind> extends MemberNameNode<K>
{
	public ProtocolNameNode(CommonTree source, String... ns)
	{
		super(source, ns);
	}

	public abstract ProtocolName<K> toName();
}

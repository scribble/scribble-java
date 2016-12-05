package org.scribble.ast.name.simple;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.NameNode;
import org.scribble.sesstype.kind.Kind;

// Parser Identifier
public abstract class SimpleNameNode<K extends Kind> extends NameNode<K>
{
	public SimpleNameNode(CommonTree source, String identifier)
	{
		super(source, new String[] { identifier });
	}
	
	public String getIdentifier()
	{
		return getLastElement();
	}
}

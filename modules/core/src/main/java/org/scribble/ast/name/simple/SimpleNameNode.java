package org.scribble.ast.name.simple;

import org.scribble.ast.name.NameNode;
import org.scribble.sesstype.kind.Kind;

// Parser Identifier
public abstract class SimpleNameNode<K extends Kind> extends NameNode<K>
{
	public SimpleNameNode(String identifier)
	{
		super(new String[] { identifier });
	}
	
	public String getIdentifier()
	{
		return getLastElement();
	}
}

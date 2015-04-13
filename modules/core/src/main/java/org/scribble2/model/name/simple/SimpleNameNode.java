package org.scribble2.model.name.simple;

import org.scribble2.model.name.NameNode;
import org.scribble2.sesstype.name.SimpleName;


// Parser Identifier
// The various kinds of names cannot be distinguished from parameter names at the parser level -- maybe no point to distinguish in the AST?
// Could make a subclass of CompoundNameNode (but not very convenient, and wouldn't match grammar def)
public abstract class SimpleNameNode<T extends SimpleName> extends NameNode<T>
{
	public final String identifier;

	public SimpleNameNode(String identifier)
	{
		this.identifier = identifier;
	}

	/*@Override
	public Name toName()
	{
		return new SimpleName(Kind.AMBIGUOUS, this.identifier);
	}*/

	protected abstract SimpleNameNode<T> reconstruct(String identifier);

	@Override
	public String toString()
	{
		//return this.identifier;
		return toName().toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || this.getClass() != o.getClass())
		{
			return false;
		}
		//return this.identifier.equals(((SimpleNameNode) o).identifier);
		return this.identifier.equals(SimpleNameNode.class.cast(o).identifier);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 313;
		hash = 31 * hash + this.identifier.hashCode();
		return hash;
	}
}
